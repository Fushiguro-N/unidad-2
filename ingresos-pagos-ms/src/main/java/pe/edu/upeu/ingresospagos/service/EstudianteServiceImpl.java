package pe.edu.upeu.ingresospagos.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.ingresospagos.dto.EstadoCuentaResponse;
import pe.edu.upeu.ingresospagos.entity.EstadoPago;
import pe.edu.upeu.ingresospagos.repository.PagoRepository;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {

    private final PagoRepository pagoRepository;

    @Override
    @Transactional(readOnly = true)
    public EstadoCuentaResponse obtenerEstadoCuenta(String idEstudiante) {
        boolean tieneDeudaPendiente = !pagoRepository
                .findByIdEstudianteAndEstadoNot(idEstudiante, EstadoPago.VALIDADO)
                .isEmpty();

        return EstadoCuentaResponse.builder()
                .idEstudiante(idEstudiante)
                .tieneDeudaPendiente(tieneDeudaPendiente)
                .build();
    }
}
