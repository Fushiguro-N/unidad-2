package pe.edu.upeu.ingresospagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.ingresospagos.entity.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
}
