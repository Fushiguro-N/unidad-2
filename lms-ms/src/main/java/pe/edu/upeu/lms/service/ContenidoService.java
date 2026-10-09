package pe.edu.upeu.lms.service;

import pe.edu.upeu.lms.dto.ContenidoRequest;
import pe.edu.upeu.lms.dto.ContenidoResponse;
import pe.edu.upeu.lms.entity.Curso;
import pe.edu.upeu.lms.entity.Contenido;
import pe.edu.upeu.lms.exception.ResourceNotFoundException;
import pe.edu.upeu.lms.mapper.ContenidoMapper;
import pe.edu.upeu.lms.repository.CursoRepository;
import pe.edu.upeu.lms.repository.ContenidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContenidoService {

    private final ContenidoRepository contenidoRepository;
    private final ContenidoMapper contenidoMapper;
    private final CursoRepository cursoRepository;

    public List<ContenidoResponse> listar() {
        return contenidoRepository.findAllConCurso().stream()
                .map(contenidoMapper::toResponse)
                .toList();
    }

    public ContenidoResponse obtener(Long id) {
        return contenidoMapper.toResponse(buscarOFallar(id));
    }

    public ContenidoResponse crear(ContenidoRequest request) {
        Contenido contenido = contenidoMapper.toEntity(request);
        contenido.setCurso(buscarCursoOFallar(request.getCursoId()));
        return contenidoMapper.toResponse(contenidoRepository.save(contenido));
    }

    public ContenidoResponse actualizar(Long id, ContenidoRequest request) {
        Contenido contenido = buscarOFallar(id);
        contenido.setNombre(request.getNombre());
        contenido.setDescripcion(request.getDescripcion());
        contenido.setPrecio(request.getPrecio());
        contenido.setActivo(request.getActivo());
        contenido.setStock(request.getStock());
        contenido.setCurso(buscarCursoOFallar(request.getCursoId()));
        return contenidoMapper.toResponse(contenidoRepository.save(contenido));
    }

    public void eliminar(Long id) {
        contenidoRepository.delete(buscarOFallar(id));
    }

    private Contenido buscarOFallar(Long id) {
        return contenidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contenido no encontrado: " + id));
    }

    private Curso buscarCursoOFallar(Long cursoId) {
        return cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado: " + cursoId));
    }
}
