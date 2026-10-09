package pe.edu.upeu.lms.service;

import pe.edu.upeu.lms.dto.*;
import pe.edu.upeu.lms.entity.Contenido;
import pe.edu.upeu.lms.entity.EstadoMatricula;
import pe.edu.upeu.lms.entity.Matricula;
import pe.edu.upeu.lms.entity.MatriculaDetalle;
import pe.edu.upeu.lms.event.MatriculaCreadaEvento;
import pe.edu.upeu.lms.messaging.MatriculaEventosProducer;
import pe.edu.upeu.lms.repository.ContenidoRepository;
import pe.edu.upeu.lms.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final ContenidoRepository contenidoRepository;
    private final EstadoCuentaConsultaService estadoCuentaConsultaService;
    private final MatriculaEventosProducer producer;

    @Override
    @Transactional
    public MatriculaResponse crear(MatriculaRequest request, Long idEstudiante) {
        Matricula matricula = Matricula.builder()
                .idEstudiante(idEstudiante)
                .periodoAcademico(request.getPeriodoAcademico())
                .metodoPago(request.getMetodoPago())
                .build();

        EstadoCuentaResponse estadoCuenta = estadoCuentaConsultaService.consultarEstadoCuenta(idEstudiante);

        if (estadoCuenta == null) {
            matricula.setEstado(EstadoMatricula.PENDIENTE_VALIDACION);
            Matricula guardada = matriculaRepository.save(matricula);
            return toResponse(guardada);
        }

        if (Boolean.TRUE.equals(estadoCuenta.getTieneDeudaPendiente())) {
            matricula.setEstado(EstadoMatricula.RECHAZADA);
            Matricula guardada = matriculaRepository.save(matricula);
            return toResponse(guardada);
        }

        List<MatriculaDetalle> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        boolean validacionCompleta = true;

        for (DetalleMatriculaRequest item : request.getDetalles()) {
            Contenido contenido = contenidoRepository.findById(item.getIdContenido()).orElse(null);

            if (contenido == null) {
                validacionCompleta = false;
                detalles.add(MatriculaDetalle.builder()
                        .matricula(matricula)
                        .idContenido(item.getIdContenido())
                        .nombreContenido(null)
                        .cantidad(item.getCantidad())
                        .precioUnitario(null)
                        .build());
                continue;
            }

            BigDecimal subtotal = contenido.getPrecio()
                    .multiply(BigDecimal.valueOf(item.getCantidad()));
            total = total.add(subtotal);

            detalles.add(MatriculaDetalle.builder()
                    .matricula(matricula)
                    .idContenido(item.getIdContenido())
                    .nombreContenido(contenido.getNombre())
                    .cantidad(item.getCantidad())
                    .precioUnitario(contenido.getPrecio())
                    .build());
        }

        matricula.setDetalles(detalles);
        matricula.setTotal(validacionCompleta ? total : null);
        matricula.setEstado(validacionCompleta ? EstadoMatricula.PENDIENTE_PAGO : EstadoMatricula.PENDIENTE_VALIDACION);

        Matricula guardada = matriculaRepository.save(matricula);

        if (guardada.getEstado() == EstadoMatricula.PENDIENTE_PAGO) {
            producer.publicarTrasCommit(MatriculaCreadaEvento.builder()
                    .tipoEvento("matricula.creada")
                    .matriculaId(guardada.getId())
                    .idEstudiante(guardada.getIdEstudiante())
                    .total(guardada.getTotal())
                    .metodoPago(guardada.getMetodoPago())
                    .origen("lms-ms")
                    .timestamp(Instant.now().toEpochMilli())
                    .build());
        }
        return toResponse(guardada);
    }

    @Override
    @Transactional
    public void marcarConfirmada(Long matriculaId) {
        Matricula matricula = matriculaRepository.findById(matriculaId).orElse(null);
        if (matricula == null || matricula.getEstado() != EstadoMatricula.PENDIENTE_PAGO) {
            log.warn("component=processor matriculaId={} status=ignored motivo=\"la matricula no existe o no esta pendiente de pago\"", matriculaId);
            return;
        }
        matricula.setEstado(EstadoMatricula.CONFIRMADA);
        log.info("component=processor matriculaId={} estado={} status=processed", matriculaId, matricula.getEstado());
    }

    @Override
    @Transactional(readOnly = true)
    public MatriculaResponse findById(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matricula no encontrada: " + id));
        return toResponse(matricula);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatriculaResponse> listar() {
        return matriculaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private MatriculaResponse toResponse(Matricula matricula) {
        List<DetalleMatriculaResponse> detalles = matricula.getDetalles().stream()
                .map(d -> DetalleMatriculaResponse.builder()
                        .idContenido(d.getIdContenido())
                        .nombreContenido(d.getNombreContenido())
                        .cantidad(d.getCantidad())
                        .precioUnitario(d.getPrecioUnitario())
                        .subtotal(d.getPrecioUnitario() == null
                                ? null
                                : d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())))
                        .build())
                .collect(Collectors.toList());

        return MatriculaResponse.builder()
                .id(matricula.getId())
                .idEstudiante(matricula.getIdEstudiante())
                .periodoAcademico(matricula.getPeriodoAcademico())
                .fechaCreacion(matricula.getFechaCreacion())
                .estado(matricula.getEstado().name())
                .total(matricula.getTotal())
                .detalles(detalles)
                .build();
    }
}
