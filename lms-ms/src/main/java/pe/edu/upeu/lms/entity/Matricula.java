package pe.edu.upeu.lms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matriculas")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_estudiante")
    private Long idEstudiante;

    @Column(name = "periodo_academico", length = 20)
    private String periodoAcademico;

    @Column(name = "fecha_creacion", nullable = false)
    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoMatricula estado = EstadoMatricula.PENDIENTE_VALIDACION;

    @Column(name = "metodo_pago", nullable = false, length = 20)
    private String metodoPago;

    private BigDecimal total;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MatriculaDetalle> detalles = new ArrayList<>();
}
