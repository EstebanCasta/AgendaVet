package co.edu.autonoma.agendavetapi.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MascotaRequest(
        @NotNull(message = "El responsable es obligatorio")
        @Positive(message = "El id del responsable debe ser positivo")
        Integer responsableId,
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String nombre,
        @NotBlank(message = "La especie es obligatoria")
        @Size(max = 50, message = "La especie no puede superar 50 caracteres")
        String especie,
        @Size(max = 80, message = "La raza no puede superar 80 caracteres")
        String raza,
        @Size(max = 10, message = "El sexo no puede superar 10 caracteres")
        String sexo,
        LocalDate fechaNacimiento,
        Boolean activa) {
}
