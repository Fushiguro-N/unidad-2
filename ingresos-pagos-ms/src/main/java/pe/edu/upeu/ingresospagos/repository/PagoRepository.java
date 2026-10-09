package pe.edu.upeu.ingresospagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.ingresospagos.entity.Pago;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByIdEstudianteAndEstadoNot(Long idEstudiante, pe.edu.upeu.ingresospagos.entity.EstadoPago estado);
}
