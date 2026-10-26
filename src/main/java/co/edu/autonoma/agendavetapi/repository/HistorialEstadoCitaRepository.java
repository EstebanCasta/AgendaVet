package co.edu.autonoma.agendavetapi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.autonoma.   agendavetapi.entities.HistorialEstadoCitaEntity;

public interface HistorialEstadoCitaRepository extends JpaRepository<HistorialEstadoCitaEntity, Integer> {

    List<HistorialEstadoCitaEntity> findByCita_IdOrderByFechaCambioAsc(Integer citaId);
}
