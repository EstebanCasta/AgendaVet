package co.edu.autonoma.agendavetapi.dto;

import java.time.OffsetDateTime;
import co.edu.autonoma.agendavetapi.entities.EstadoCita;
import co.edu.autonoma.agendavetapi.entities.HistorialEstadoCitaEntity;

public record HistorialEstadoCitaResponse(Integer id, Integer citaId, EstadoCita estadoAnterior,
        EstadoCita estadoNuevo, OffsetDateTime fechaCambio) {

    public static HistorialEstadoCitaResponse desde(HistorialEstadoCitaEntity historial) {
        return new HistorialEstadoCitaResponse(historial.getId(), historial.getCita().getId(),
                historial.getEstadoAnterior(), historial.getEstadoNuevo(), historial.getFechaCambio());
    }
}
