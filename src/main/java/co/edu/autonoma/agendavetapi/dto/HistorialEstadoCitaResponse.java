package co.edu.autonoma.agendavetapi.dto;

import java.time.OffsetDateTime;
import co.edu.autonoma.agendavetapi.entities.EstadoCita;
import co.edu.autonoma.agendavetapi.entities.HistorialEstadoCitaEntity;

/** Cambio de estado de una cita que se devuelve al consultar su historial. */
public class HistorialEstadoCitaResponse {

    private Integer id;
    private Integer citaId;
    private EstadoCita estadoAnterior;
    private EstadoCita estadoNuevo;
    private OffsetDateTime fechaCambio;

    public HistorialEstadoCitaResponse() {
    }

    public HistorialEstadoCitaResponse(Integer id, Integer citaId, EstadoCita estadoAnterior, EstadoCita estadoNuevo, OffsetDateTime fechaCambio) {
        this.id = id;
        this.citaId = citaId;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.fechaCambio = fechaCambio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCitaId() {
        return citaId;
    }

    public void setCitaId(Integer citaId) {
        this.citaId = citaId;
    }

    public EstadoCita getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(EstadoCita estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public EstadoCita getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(EstadoCita estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public OffsetDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(OffsetDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    /** Convierte el movimiento persistido en un DTO sin exponer la entidad. */
    public static HistorialEstadoCitaResponse desde(HistorialEstadoCitaEntity historial) {
        return new HistorialEstadoCitaResponse(historial.getId(), historial.getCita().getId(),
                historial.getEstadoAnterior(), historial.getEstadoNuevo(), historial.getFechaCambio());
    }

}
