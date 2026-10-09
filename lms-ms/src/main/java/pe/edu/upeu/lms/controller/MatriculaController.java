package pe.edu.upeu.lms.controller;

import pe.edu.upeu.lms.dto.MatriculaRequest;
import pe.edu.upeu.lms.dto.MatriculaResponse;
import pe.edu.upeu.lms.service.MatriculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaService matriculaService;

    @PostMapping("/enrolar")
    @ResponseStatus(HttpStatus.CREATED)
    public MatriculaResponse enrolar(@Valid @RequestBody MatriculaRequest request,
                                      @AuthenticationPrincipal Jwt jwt) {
        Number claimIdEstudiante = jwt.getClaim("idCliente");
        if (claimIdEstudiante == null) {
            throw new IllegalArgumentException("Token sin idCliente: solo un estudiante autenticado puede matricularse");
        }
        return matriculaService.crear(request, claimIdEstudiante.longValue());
    }

    @GetMapping
    public List<MatriculaResponse> listar() {
        return matriculaService.listar();
    }

    @GetMapping("/{id}")
    public MatriculaResponse findById(@PathVariable Long id) {
        return matriculaService.findById(id);
    }
}
