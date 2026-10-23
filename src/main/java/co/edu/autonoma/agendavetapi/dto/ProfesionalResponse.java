package co.edu.autonoma.agendavetapi.dto;

import java.util.List;
import co.edu.autonoma.agendavetapi.entities.ProfesionalEntity;

public record ProfesionalResponse(Integer id, String nombre, String apellido, String especialidad,
        String correo, boolean activo, List<Integer> serviciosIds) {

    public static ProfesionalResponse desde(ProfesionalEntity profesional) {
        List<Integer> serviciosIds = profesional.getServicios().stream()
                .map(servicio -> servicio.getId()).sorted().toList();
        return new ProfesionalResponse(profesional.getId(), profesional.getNombre(), profesional.getApellido(),
                profesional.getEspecialidad(), profesional.getCorreo(), profesional.isActivo(), serviciosIds);
    }
}
