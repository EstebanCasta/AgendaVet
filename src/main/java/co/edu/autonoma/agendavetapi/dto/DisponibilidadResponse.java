package co.edu.autonoma.agendavetapi.dto;

import java.time.OffsetDateTime;
import co.edu.autonoma.agendavetapi.entities.DisponibilidadEntity;

public record DisponibilidadResponse(Integer id, Integer profesionalId,
        OffsetDateTime fechaInicio, OffsetDateTime fechaFin) {

    public static DisponibilidadResponse desde(DisponibilidadEntity disponibilidad) {
        return new DisponibilidadResponse(disponibilidad.getId(), disponibilidad.getProfesional().getId(),
                disponibilidad.getFechaInicio(), disponibilidad.getFechaFin());
    }
}
