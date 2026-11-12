package co.edu.autonoma.agendavetapi.dto;

import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/** Datos que el cliente envía para crear o reprogramar una cita. */
public class CitaRequest {

    /** ID de la mascota que recibirá la atención. */
    @NotNull(message = "La mascota es obligatoria")
    @Positive(message = "El id debe ser positivo")
    private Integer mascotaId;
    /** ID del profesional que atenderá la cita. */
    @NotNull(message = "El profesional es obligatorio")
    @Positive(message = "El id debe ser positivo")
    private Integer profesionalId;
    /** ID del servicio; su duración determina la fecha final. */
    @NotNull(message = "El servicio es obligatorio")
    @Positive(message = "El id debe ser positivo")
    private Integer servicioId;
    /** ID del bloque de disponibilidad donde se agenda. */
    @NotNull(message = "La disponibilidad es obligatoria")
    @Positive(message = "El id debe ser positivo")
    private Integer disponibilidadId;
    /** Momento inicial de la cita, incluyendo zona horaria. */
    @NotNull(message = "La fecha de inicio es obligatoria")
    private OffsetDateTime fechaInicio;
    /** Nota breve de carácter administrativo; no usar para historia clínica. */
    @Size(max = 255, message = "Las notas no pueden superar 255 caracteres")
    private String notas;

    public CitaRequest() {
    }

    public CitaRequest(Integer mascotaId, Integer profesionalId, Integer servicioId, Integer disponibilidadId, OffsetDateTime fechaInicio, String notas) {
        this.mascotaId = mascotaId;
        this.profesionalId = profesionalId;
        this.servicioId = servicioId;
        this.disponibilidadId = disponibilidadId;
        this.fechaInicio = fechaInicio;
        this.notas = notas;
    }

    public Integer getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Integer mascotaId) {
        this.mascotaId = mascotaId;
    }

    public Integer getProfesionalId() {
        return profesionalId;
    }

    public void setProfesionalId(Integer profesionalId) {
        this.profesionalId = profesionalId;
    }

    public Integer getServicioId() {
        return servicioId;
    }

    public void setServicioId(Integer servicioId) {
        this.servicioId = servicioId;
    }

    public Integer getDisponibilidadId() {
        return disponibilidadId;
    }

    public void setDisponibilidadId(Integer disponibilidadId) {
        this.disponibilidadId = disponibilidadId;
    }

    public OffsetDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(OffsetDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

}
