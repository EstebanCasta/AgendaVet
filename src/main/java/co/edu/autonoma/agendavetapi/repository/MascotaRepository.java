package co.edu.autonoma.agendavetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.autonoma.agendavetapi.domain.MascotaEntity;

public interface MascotaRepository extends JpaRepository<MascotaEntity, Long> {
}