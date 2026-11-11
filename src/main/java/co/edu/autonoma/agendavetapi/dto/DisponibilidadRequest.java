package co.edu.autonoma.agendavetapi.dto;

import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DisponibilidadRequest(
        @NotNull(message = "El profesional es obligatorio")
        @Positive(message = "El id del profesional debe ser positivo")
        Integer profesionalId,
        @NotNull(message = "La fecha de inicio es obligatoria")
        OffsetDateTime fechaInicio,
        @NotNull(message = "La fecha de fin es obligatoria")
        OffsetDateTime fechaFin) {
}
