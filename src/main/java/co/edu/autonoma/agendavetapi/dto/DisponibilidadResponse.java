package co.edu.autonoma.agendavetapi.dto;

import java.time.OffsetDateTime;
import co.edu.autonoma.agendavetapi.entities.DisponibilidadEntity;

/** Datos públicos de un bloque de disponibilidad. */
public class DisponibilidadResponse {

    private Integer id;
    private Integer profesionalId;
    private OffsetDateTime fechaInicio;
    private OffsetDateTime fechaFin;

    public DisponibilidadResponse() {
    }

    public DisponibilidadResponse(Integer id, Integer profesionalId, OffsetDateTime fechaInicio, OffsetDateTime fechaFin) {
        this.id = id;
        this.profesionalId = profesionalId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProfesionalId() {
        return profesionalId;
    }

    public void setProfesionalId(Integer profesionalId) {
        this.profesionalId = profesionalId;
    }

    public OffsetDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(OffsetDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public OffsetDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(OffsetDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    /** Convierte la entidad y la relación con el profesional a IDs sencillos. */
    public static DisponibilidadResponse desde(DisponibilidadEntity disponibilidad) {
        return new DisponibilidadResponse(disponibilidad.getId(), disponibilidad.getProfesional().getId(),
                disponibilidad.getFechaInicio(), disponibilidad.getFechaFin());
    }

}
