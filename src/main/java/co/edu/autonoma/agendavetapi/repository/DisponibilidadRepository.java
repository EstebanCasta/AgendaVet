package co.edu.autonoma.agendavetapi.repository;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.autonoma.   agendavetapi.entities.DisponibilidadEntity;

public interface DisponibilidadRepository extends JpaRepository<DisponibilidadEntity, Integer> {

    @Query("select d from DisponibilidadEntity d "
            + "where (:profesionalId is null or d.profesional.id = :profesionalId) "
            + "and (:desde is null or d.fechaFin >= :desde) "
            + "and (:hasta is null or d.fechaInicio <= :hasta) order by d.fechaInicio")
    List<DisponibilidadEntity> buscarConFiltros(@Param("profesionalId") Integer profesionalId,
            @Param("desde") OffsetDateTime desde, @Param("hasta") OffsetDateTime hasta);

    @Query("select case when count(d) > 0 then true else false end from DisponibilidadEntity d "
            + "where d.profesional.id = :profesionalId and d.fechaInicio < :fechaFin "
            + "and d.fechaFin > :fechaInicio and (:id is null or d.id <> :id)")
    boolean existeSolapamiento(@Param("profesionalId") Integer profesionalId,
            @Param("fechaInicio") OffsetDateTime fechaInicio, @Param("fechaFin") OffsetDateTime fechaFin,
            @Param("id") Integer id);
}
