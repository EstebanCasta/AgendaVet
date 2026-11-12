package co.edu.autonoma.agendavetapi.dto;

import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/** Datos enviados para registrar o reemplazar un bloque de disponibilidad. */
public class DisponibilidadRequest {

    /** ID del profesional propietario del bloque. */
    @NotNull(message = "El profesional es obligatorio")
    @Positive(message = "El id del profesional debe ser positivo")
    private Integer profesionalId;
    /** Inicio y fin del rango disponible; deben incluir zona horaria. */
    @NotNull(message = "La fecha de inicio es obligatoria")
    private OffsetDateTime fechaInicio;
    @NotNull(message = "La fecha de fin es obligatoria")
    private OffsetDateTime fechaFin;

    public DisponibilidadRequest() {
    }

    public DisponibilidadRequest(Integer profesionalId, OffsetDateTime fechaInicio, OffsetDateTime fechaFin) {
        this.profesionalId = profesionalId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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

}
