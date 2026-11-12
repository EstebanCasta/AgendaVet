package co.edu.autonoma.agendavetapi.dto;

import java.time.OffsetDateTime;
import co.edu.autonoma.agendavetapi.entities.CitaEntity;
import co.edu.autonoma.agendavetapi.entities.EstadoCita;

public record CitaResponse(Integer id, Integer mascotaId, Integer profesionalId, Integer servicioId,
        Integer disponibilidadId, OffsetDateTime fechaInicio, OffsetDateTime fechaFin,
        EstadoCita estado, String notas, OffsetDateTime fechaCreacion, OffsetDateTime fechaActualizacion) {

    public static CitaResponse desde(CitaEntity cita) {
        return new CitaResponse(cita.getId(), cita.getMascota().getId(), cita.getProfesional().getId(),
                cita.getServicio().getId(), cita.getDisponibilidad().getId(), cita.getFechaInicio(),
                cita.getFechaFin(), cita.getEstado(), cita.getNotas(), cita.getFechaCreacion(),
                cita.getFechaActualizacion());
    }
}
