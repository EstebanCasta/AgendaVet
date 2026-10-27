package co.edu.autonoma.agendavetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import co.edu.autonoma.   agendavetapi.entities.MascotaEntity;

public interface MascotaRepository extends JpaRepository<MascotaEntity, Integer> {

    List<MascotaEntity> findByResponsable_Id(Integer responsableId);
}
