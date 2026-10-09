package pe.edu.upeu.lms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleMatriculaRequest {

    @NotNull
    private Long idContenido;

    @NotNull
    @Positive
    private Integer cantidad;
}
