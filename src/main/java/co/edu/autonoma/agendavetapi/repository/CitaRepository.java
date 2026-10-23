package co.edu.autonoma.agendavetapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.autonoma.agendavetapi.entities.CitaEntity;
import co.edu.autonoma.agendavetapi.entities.EstadoCita;

public interface CitaRepository extends JpaRepository<CitaEntity, Integer> {

    List<CitaEntity> findByEstado(EstadoCita estado);

    long countByEstado(EstadoCita estado);

    @Query("select c from CitaEntity c "
            + "where (:estado is null or c.estado = :estado) "
            + "and (:mascotaId is null or c.mascota.id = :mascotaId) "
            + "and (:profesionalId is null or c.profesional.id = :profesionalId) "
            + "and (:desde is null or c.fechaFin >= :desde) "
            + "and (:hasta is null or c.fechaInicio <= :hasta) order by c.fechaInicio")
    List<CitaEntity> buscarConFiltros(@Param("estado") EstadoCita estado,
            @Param("mascotaId") Integer mascotaId, @Param("profesionalId") Integer profesionalId,
            @Param("desde") java.time.OffsetDateTime desde, @Param("hasta") java.time.OffsetDateTime hasta);

    @Query("select case when count(c) > 0 then true else false end from CitaEntity c "
            + "where c.estado = :estado and (c.profesional.id = :profesionalId or c.mascota.id = :mascotaId) "
            + "and c.fechaInicio < :fechaFin and c.fechaFin > :fechaInicio "
            + "and (:id is null or c.id <> :id)")
    boolean existeSolapamiento(@Param("profesionalId") Integer profesionalId,
            @Param("mascotaId") Integer mascotaId, @Param("fechaInicio") java.time.OffsetDateTime fechaInicio,
            @Param("fechaFin") java.time.OffsetDateTime fechaFin, @Param("id") Integer id,
            @Param("estado") EstadoCita estado);
}
