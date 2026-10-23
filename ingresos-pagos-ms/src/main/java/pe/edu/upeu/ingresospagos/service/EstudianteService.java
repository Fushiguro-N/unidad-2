package pe.edu.upeu.ingresospagos.service;

import pe.edu.upeu.ingresospagos.dto.EstadoCuentaResponse;

public interface EstudianteService {
    EstadoCuentaResponse obtenerEstadoCuenta(String idEstudiante);
}
