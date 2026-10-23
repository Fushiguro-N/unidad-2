package pe.edu.upeu.lms.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import pe.edu.upeu.lms.client.EstadoCuentaClient;
import pe.edu.upeu.lms.dto.EstadoCuentaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstadoCuentaConsultaService {

    private final EstadoCuentaClient estadoCuentaClient;

    @CircuitBreaker(name = "ingresosPagos", fallbackMethod = "fallbackEstadoCuenta")
    public EstadoCuentaResponse consultarEstadoCuenta(String idEstudiante) {
        return estadoCuentaClient.obtenerEstadoCuenta(idEstudiante);
    }

    public EstadoCuentaResponse fallbackEstadoCuenta(String idEstudiante, Throwable ex) {
        log.warn("[INGRESOS-PAGOS] Fallback activado para idEstudiante {}. Motivo: {}", idEstudiante, ex.getMessage());
        return null;
    }
}
