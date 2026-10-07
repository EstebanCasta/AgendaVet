package co.edu.autonoma.agendavetapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.autonoma.agendavetapi.domain.CitaEntity;
import co.edu.autonoma.agendavetapi.domain.EstadoCita;

public interface CitaRepository extends JpaRepository<CitaEntity, Long> {

    List<CitaEntity> findByEstado(EstadoCita estado);

    long countByEstado(EstadoCita estado);
}