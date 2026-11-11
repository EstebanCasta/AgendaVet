package co.edu.autonoma.agendavetapi.dto;

import java.time.OffsetDateTime;
import co.edu.autonoma.agendavetapi.entities.ResponsableEntity;

public record ResponsableResponse(Integer id, String nombre, String apellido, String tipoDocumento,
        String numeroDocumento, String telefono, String correo, OffsetDateTime fechaRegistro) {

    public static ResponsableResponse desde(ResponsableEntity responsable) {
        return new ResponsableResponse(responsable.getId(), responsable.getNombre(), responsable.getApellido(),
                responsable.getTipoDocumento(), responsable.getNumeroDocumento(), responsable.getTelefono(),
                responsable.getCorreo(), responsable.getFechaRegistro());
    }
}
