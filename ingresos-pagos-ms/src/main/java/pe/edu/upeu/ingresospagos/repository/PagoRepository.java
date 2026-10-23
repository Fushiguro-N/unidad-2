package pe.edu.upeu.ingresospagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.ingresospagos.entity.Pago;

import java.util.List;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByIdEstudianteAndEstadoNot(String idEstudiante, pe.edu.upeu.ingresospagos.entity.EstadoPago estado);

    Optional<Pago> findByMatriculaId(Long matriculaId);
}
