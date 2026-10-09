package pe.edu.upeu.lms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "matricula_detalles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula matricula;

    @Column(name = "id_contenido", nullable = false)
    private Long idContenido;

    @Column(name = "nombre_contenido", length = 150)
    private String nombreContenido;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;
}
