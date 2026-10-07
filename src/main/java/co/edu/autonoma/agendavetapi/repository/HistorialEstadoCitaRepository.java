package co.edu.autonoma.agendavetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.autonoma.agendavetapi.domain.HistorialEstadoCitaEntity;

public interface HistorialEstadoCitaRepository extends JpaRepository<HistorialEstadoCitaEntity, Long> {
}