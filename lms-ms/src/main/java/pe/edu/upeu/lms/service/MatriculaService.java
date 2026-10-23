package pe.edu.upeu.lms.service;

import pe.edu.upeu.lms.dto.MatriculaRequest;
import pe.edu.upeu.lms.dto.MatriculaResponse;
import java.util.List;

public interface MatriculaService {
    MatriculaResponse crear(MatriculaRequest request, String idEstudiante);
    void marcarConfirmada(Long matriculaId);
    MatriculaResponse findById(Long id);
    List<MatriculaResponse> listar();
}
