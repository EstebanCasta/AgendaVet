package co.edu.autonoma.agendavetapi.dto;

import co.edu.autonoma.agendavetapi.entities.EstadoCita;
import jakarta.validation.constraints.NotNull;

public record CambioEstadoCitaRequest(
        @NotNull(message = "El nuevo estado es obligatorio")
        EstadoCita estado) {
}
