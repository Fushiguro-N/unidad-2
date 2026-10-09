package pe.edu.upeu.lms.controller;

import pe.edu.upeu.lms.dto.CursoRequest;
import pe.edu.upeu.lms.dto.CursoResponse;
import pe.edu.upeu.lms.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public List<CursoResponse> listar() {
        log.info("Listando cursos");
        return cursoService.listar();
    }

    @GetMapping("/{id}")
    public CursoResponse obtener(@PathVariable Long id) {
        return cursoService.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CursoResponse crear(@Valid @RequestBody CursoRequest request) {
        return cursoService.crear(request);
    }

    @PutMapping("/{id}")
    public CursoResponse actualizar(@PathVariable Long id, @Valid @RequestBody CursoRequest request) {
        return cursoService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        cursoService.eliminar(id);
    }
}
