package co.edu.autonoma.agendavetapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResponsableRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String nombre,
        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 100, message = "El apellido no puede superar 100 caracteres")
        String apellido,
        @NotBlank(message = "El tipo de documento es obligatorio")
        @Size(max = 10, message = "El tipo de documento no puede superar 10 caracteres")
        String tipoDocumento,
        @NotBlank(message = "El número de documento es obligatorio")
        @Size(max = 30, message = "El número de documento no puede superar 30 caracteres")
        String numeroDocumento,
        @Size(max = 20, message = "El teléfono no puede superar 20 caracteres")
        String telefono,
        @Email(message = "El correo debe tener un formato válido")
        @Size(max = 150, message = "El correo no puede superar 150 caracteres")
        String correo) {
}
