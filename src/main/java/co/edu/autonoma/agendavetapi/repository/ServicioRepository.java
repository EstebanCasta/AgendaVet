package co.edu.autonoma.agendavetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.autonoma.agendavetapi.domain.ServicioEntity;

public interface ServicioRepository extends JpaRepository<ServicioEntity, Long> {
}