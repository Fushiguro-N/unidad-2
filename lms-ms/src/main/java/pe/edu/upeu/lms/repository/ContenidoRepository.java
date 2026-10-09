package pe.edu.upeu.lms.repository;

import pe.edu.upeu.lms.entity.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContenidoRepository extends JpaRepository<Contenido, Long> {

    @Query("SELECT c FROM Contenido c JOIN FETCH c.curso")
    List<Contenido> findAllConCurso();
}
