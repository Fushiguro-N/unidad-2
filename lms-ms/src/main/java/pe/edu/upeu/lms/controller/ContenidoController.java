package pe.edu.upeu.lms.controller;

import pe.edu.upeu.lms.dto.ContenidoRequest;
import pe.edu.upeu.lms.dto.ContenidoResponse;
import pe.edu.upeu.lms.service.ContenidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contenidos")
@RequiredArgsConstructor
public class ContenidoController {

    private final ContenidoService contenidoService;

    @GetMapping
    public List<ContenidoResponse> listar() {
        return contenidoService.listar();
    }

    @GetMapping("/{id}")
    public ContenidoResponse obtener(@PathVariable Long id) {
        return contenidoService.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContenidoResponse crear(@Valid @RequestBody ContenidoRequest request) {
        return contenidoService.crear(request);
    }

    @PutMapping("/{id}")
    public ContenidoResponse actualizar(@PathVariable Long id, @Valid @RequestBody ContenidoRequest request) {
        return contenidoService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        contenidoService.eliminar(id);
    }
}
