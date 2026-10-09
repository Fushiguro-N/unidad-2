package pe.edu.upeu.lms.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaRequest {

    private String periodoAcademico;

    @NotBlank
    private String metodoPago;

    @NotEmpty
    @Valid
    private List<DetalleMatriculaRequest> detalles;
}
