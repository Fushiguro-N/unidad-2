package pe.edu.upeu.lms.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadoCuentaResponse {
    private Long idEstudiante;
    private Boolean tieneDeudaPendiente;
}
