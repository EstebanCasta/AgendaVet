package co.edu.autonoma.agendavetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.autonoma.agendavetapi.entities.ServicioEntity;

/** Operaciones de base de datos para el catálogo de servicios. */
public interface ServicioRepository extends JpaRepository<ServicioEntity, Integer> {

    /** Comprueba si un servicio ya usa el nombre indicado. */
    boolean existsByNombre(String nombre);

    /** Comprueba si otro servicio usa el nombre indicado. */
    boolean existsByNombreAndIdNot(String nombre, Integer id);
}
