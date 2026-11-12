package co.edu.autonoma.agendavetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import co.edu.autonoma.agendavetapi.entities.MascotaEntity;

/** Operaciones de base de datos para mascotas. */
public interface MascotaRepository extends JpaRepository<MascotaEntity, Integer> {

    /** Lista las mascotas asociadas a un responsable. */
    List<MascotaEntity> findByResponsable_Id(Integer responsableId);
}
