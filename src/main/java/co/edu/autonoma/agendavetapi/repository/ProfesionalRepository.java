package co.edu.autonoma.agendavetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.autonoma.agendavetapi.domain.ProfesionalEntity;

public interface ProfesionalRepository extends JpaRepository<ProfesionalEntity, Long> {
}