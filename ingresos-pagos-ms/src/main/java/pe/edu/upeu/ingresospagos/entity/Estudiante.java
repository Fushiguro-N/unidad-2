package pe.edu.upeu.ingresospagos.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estudiantes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String nombres;

    @Column(length = 150)
    private String email;

    @Column(name = "id_usuario_auth")
    private Long idUsuarioAuth;
}
