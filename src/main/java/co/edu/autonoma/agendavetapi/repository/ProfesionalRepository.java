package co.edu.autonoma.agendavetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import co.edu.autonoma.   agendavetapi.entities.ProfesionalEntity;

public interface ProfesionalRepository extends JpaRepository<ProfesionalEntity, Integer> {

    boolean existsByCorreo(String correo);

    boolean existsByCorreoAndIdNot(String correo, Integer id);

    List<ProfesionalEntity> findByActivo(boolean activo);
}
