package co.edu.autonoma.agendavetapi.dto;

import java.time.OffsetDateTime;
import co.edu.autonoma.agendavetapi.entities.CitaEntity;
import co.edu.autonoma.agendavetapi.entities.EstadoCita;

/** Datos de una cita que la API devuelve al cliente. */
public class CitaResponse {

    /** Identificador generado para la cita. */
    private Integer id;
    /** Identificadores de los registros relacionados con la cita. */
    private Integer mascotaId;
    private Integer profesionalId;
    private Integer servicioId;
    private Integer disponibilidadId;
    /** Inicio y fin calculado de la atención. */
    private OffsetDateTime fechaInicio;
    private OffsetDateTime fechaFin;
    /** Estado vigente y nota breve asociada a la cita. */
    private EstadoCita estado;
    private String notas;
    /** Fechas de creación y última actualización del registro. */
    private OffsetDateTime fechaCreacion;
    private OffsetDateTime fechaActualizacion;

    public CitaResponse() {
    }

    public CitaResponse(Integer id, Integer mascotaId, Integer profesionalId, Integer servicioId, Integer disponibilidadId, OffsetDateTime fechaInicio, OffsetDateTime fechaFin, EstadoCita estado, String notas, OffsetDateTime fechaCreacion, OffsetDateTime fechaActualizacion) {
        this.id = id;
        this.mascotaId = mascotaId;
        this.profesionalId = profesionalId;
        this.servicioId = servicioId;
        this.disponibilidadId = disponibilidadId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.notas = notas;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public OffsetDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(OffsetDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public OffsetDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(OffsetDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public OffsetDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(OffsetDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /** Convierte la entidad persistida al DTO que se serializa como JSON. */
    public static CitaResponse desde(CitaEntity cita) {
        return new CitaResponse(cita.getId(), cita.getMascota().getId(), cita.getProfesional().getId(),
                cita.getServicio().getId(), cita.getDisponibilidad().getId(), cita.getFechaInicio(),
                cita.getFechaFin(), cita.getEstado(), cita.getNotas(), cita.getFechaCreacion(),
                cita.getFechaActualizacion());
    }

}
