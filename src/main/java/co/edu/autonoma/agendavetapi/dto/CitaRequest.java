package co.edu.autonoma.agendavetapi.dto;

import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CitaRequest(
        @NotNull(message = "La mascota es obligatoria") @Positive(message = "El id debe ser positivo")
        Integer mascotaId,
        @NotNull(message = "El profesional es obligatorio") @Positive(message = "El id debe ser positivo")
        Integer profesionalId,
        @NotNull(message = "El servicio es obligatorio") @Positive(message = "El id debe ser positivo")
        Integer servicioId,
        @NotNull(message = "La disponibilidad es obligatoria") @Positive(message = "El id debe ser positivo")
        Integer disponibilidadId,
        @NotNull(message = "La fecha de inicio es obligatoria")
        OffsetDateTime fechaInicio,
        @Size(max = 255, message = "Las notas no pueden superar 255 caracteres")
        String notas) {
}
