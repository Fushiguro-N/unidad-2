package pe.edu.upeu.lms.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaResponse {
    private Long id;
    private String idEstudiante;
    private String periodoAcademico;
    private LocalDateTime fechaCreacion;
    private String estado;
    private BigDecimal total;
    private List<DetalleMatriculaResponse> detalles;
}
