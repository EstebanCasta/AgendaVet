package co.edu.autonoma.agendavetapi.repository;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.autonoma.agendavetapi.entities.DisponibilidadEntity;

/** Acceso a los bloques de disponibilidad y a sus consultas de rango. */
public interface DisponibilidadRepository extends JpaRepository<DisponibilidadEntity, Integer> {

    /** Busca bloques por profesional y rango, ordenados por fecha de inicio. */
    @Query("select d from DisponibilidadEntity d "
            + "where d.profesional.id = coalesce(:profesionalId, d.profesional.id) "
            + "and d.fechaFin >= coalesce(:desde, d.fechaFin) "
            + "and d.fechaInicio <= coalesce(:hasta, d.fechaInicio) order by d.fechaInicio")
    List<DisponibilidadEntity> buscarConFiltros(@Param("profesionalId") Integer profesionalId,
            @Param("desde") OffsetDateTime desde, @Param("hasta") OffsetDateTime hasta);

    /** Comprueba si el rango se cruza con otro bloque del mismo profesional. */
    @Query("select case when count(d) > 0 then true else false end from DisponibilidadEntity d "
            + "where d.profesional.id = :profesionalId and d.fechaInicio < :fechaFin "
            + "and d.fechaFin > :fechaInicio and (:id is null or d.id <> :id)")
    boolean existeSolapamiento(@Param("profesionalId") Integer profesionalId,
            @Param("fechaInicio") OffsetDateTime fechaInicio, @Param("fechaFin") OffsetDateTime fechaFin,
            @Param("id") Integer id);
}
