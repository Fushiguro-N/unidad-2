package pe.edu.upeu.lms.client;

import pe.edu.upeu.lms.dto.EstadoCuentaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ingresos-pagos-ms")
public interface EstadoCuentaClient {

    @GetMapping("/api/v1/estudiantes/{id}/estado-cuenta")
    EstadoCuentaResponse obtenerEstadoCuenta(@PathVariable("id") Long id);
}
