package co.edu.autonoma.agendavetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.autonoma.agendavetapi.domain.DisponibilidadEntity;

public interface DisponibilidadRepository extends JpaRepository<DisponibilidadEntity, Long> {
}