package co.edu.autonoma.agendavetapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.autonoma.agendavetapi.entities.CitaEntity;
import co.edu.autonoma.agendavetapi.entities.EstadoCita;

public interface CitaRepository extends JpaRepository<CitaEntity, Integer> {

    List<CitaEntity> findByEstado(EstadoCita estado);

    long countByEstado(EstadoCita estado);
}