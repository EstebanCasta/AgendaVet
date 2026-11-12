package co.edu.autonoma.agendavetapi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.autonoma.agendavetapi.entities.HistorialEstadoCitaEntity;

/** Guarda y consulta los cambios de estado de las citas. */
public interface HistorialEstadoCitaRepository extends JpaRepository<HistorialEstadoCitaEntity, Integer> {

    /** Devuelve el historial de una cita desde el cambio más antiguo. */
    List<HistorialEstadoCitaEntity> findByCita_IdOrderByFechaCambioAsc(Integer citaId);
}
