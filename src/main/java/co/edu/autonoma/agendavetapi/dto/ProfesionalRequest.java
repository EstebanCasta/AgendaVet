package co.edu.autonoma.agendavetapi.dto;

import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProfesionalRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String nombre,
        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 100, message = "El apellido no puede superar 100 caracteres")
        String apellido,
        @Size(max = 100, message = "La especialidad no puede superar 100 caracteres")
        String especialidad,
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo debe tener un formato válido")
        @Size(max = 150, message = "El correo no puede superar 150 caracteres")
        String correo,
        Boolean activo,
        List<@Positive(message = "Cada id de servicio debe ser positivo") Integer> serviciosIds) {
}
