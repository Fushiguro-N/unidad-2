package pe.edu.upeu.lms.service;

import pe.edu.upeu.lms.dto.CursoRequest;
import pe.edu.upeu.lms.dto.CursoResponse;
import pe.edu.upeu.lms.entity.Curso;
import pe.edu.upeu.lms.exception.ResourceNotFoundException;
import pe.edu.upeu.lms.mapper.CursoMapper;
import pe.edu.upeu.lms.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;

    public List<CursoResponse> listar() {
        return cursoRepository.findAll().stream()
                .map(cursoMapper::toResponse)
                .toList();
    }

    public CursoResponse obtener(Long id) {
        return cursoMapper.toResponse(buscarOFallar(id));
    }

    public CursoResponse crear(CursoRequest request) {
        Curso curso = cursoMapper.toEntity(request);
        return cursoMapper.toResponse(cursoRepository.save(curso));
    }

    public CursoResponse actualizar(Long id, CursoRequest request) {
        Curso curso = buscarOFallar(id);
        curso.setNombre(request.getNombre());
        curso.setDescripcion(request.getDescripcion());
        return cursoMapper.toResponse(cursoRepository.save(curso));
    }

    public void eliminar(Long id) {
        cursoRepository.delete(buscarOFallar(id));
    }

    private Curso buscarOFallar(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado: " + id));
    }
}
