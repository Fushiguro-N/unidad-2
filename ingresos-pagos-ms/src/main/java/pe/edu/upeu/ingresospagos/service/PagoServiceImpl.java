package pe.edu.upeu.ingresospagos.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.ingresospagos.dto.PagoResponse;
import pe.edu.upeu.ingresospagos.entity.EstadoPago;
import pe.edu.upeu.ingresospagos.entity.Pago;
import pe.edu.upeu.ingresospagos.event.MatriculaCreadaEvento;
import pe.edu.upeu.ingresospagos.event.PagoValidadoEvento;
import pe.edu.upeu.ingresospagos.exception.ResourceNotFoundException;
import pe.edu.upeu.ingresospagos.mapper.PagoMapper;
import pe.edu.upeu.ingresospagos.messaging.PagoEventosProducer;
import pe.edu.upeu.ingresospagos.repository.PagoRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private static final String PAGO_VALIDADO = "pago.validado";

    private final PagoRepository pagoRepository;
    private final PagoEventosProducer producer;
    private final PagoMapper pagoMapper;

    @Value("${spring.application.name}")
    private String nombreServicio;

    @Override
    @Transactional
    public void procesar(MatriculaCreadaEvento matricula) {
        Pago pago = pagoRepository.save(Pago.builder()
                .matriculaId(matricula.getMatriculaId())
                .idEstudiante(matricula.getIdEstudiante())
                .monto(matricula.getTotal())
                .metodoPago(matricula.getMetodoPago())
                .estado(EstadoPago.VALIDADO)
                .fechaPago(LocalDateTime.now())
                .build());

        producer.publicarTrasCommit(PagoValidadoEvento.builder()
                .tipoEvento(PAGO_VALIDADO)
                .matriculaId(pago.getMatriculaId())
                .monto(pago.getMonto())
                .estado(pago.getEstado().name())
                .origen(nombreServicio)
                .timestamp(Instant.now().toEpochMilli())
                .build());

        log.info("component=processor matriculaId={} estado={} status=processed", pago.getMatriculaId(), pago.getEstado());
    }

    @Override
    public List<PagoResponse> listar() {
        return pagoRepository.findAll().stream()
                .map(pagoMapper::toResponse)
                .toList();
    }

    @Override
    public PagoResponse obtener(Long id) {
        return pagoMapper.toResponse(buscarOFallar(id));
    }

    @Override
    public Optional<PagoResponse> buscarPorMatricula(Long matriculaId) {
        return pagoRepository.findByMatriculaId(matriculaId).map(pagoMapper::toResponse);
    }

    private Pago buscarOFallar(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado: " + id));
    }
}
