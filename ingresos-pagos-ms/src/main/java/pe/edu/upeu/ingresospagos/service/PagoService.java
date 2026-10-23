package pe.edu.upeu.ingresospagos.service;

import pe.edu.upeu.ingresospagos.dto.PagoResponse;
import pe.edu.upeu.ingresospagos.event.MatriculaCreadaEvento;

import java.util.List;
import java.util.Optional;

public interface PagoService {
    void procesar(MatriculaCreadaEvento matricula);

    List<PagoResponse> listar();

    PagoResponse obtener(Long id);

    Optional<PagoResponse> buscarPorMatricula(Long matriculaId);
}
