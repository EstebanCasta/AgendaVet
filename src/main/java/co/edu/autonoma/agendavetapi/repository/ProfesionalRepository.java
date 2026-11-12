package co.edu.autonoma.agendavetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import co.edu.autonoma.agendavetapi.entities.ProfesionalEntity;

/** Operaciones de base de datos para profesionales. */
public interface ProfesionalRepository extends JpaRepository<ProfesionalEntity, Integer> {

    /** Comprueba si el correo ya está registrado. */
    boolean existsByCorreo(String correo);

    /** Comprueba si otro profesional usa el correo indicado. */
    boolean existsByCorreoAndIdNot(String correo, Integer id);

    /** Filtra profesionales por su estado activo. */
    List<ProfesionalEntity> findByActivo(boolean activo);
}
