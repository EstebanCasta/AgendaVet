package co.edu.autonoma.agendavetapi.dto;

import co.edu.autonoma.agendavetapi.entities.ServicioEntity;

public record ServicioResponse(Integer id, String nombre, String descripcion,
        Integer duracionMinutos, boolean activo) {

    public static ServicioResponse desde(ServicioEntity servicio) {
        return new ServicioResponse(servicio.getId(), servicio.getNombre(), servicio.getDescripcion(),
                servicio.getDuracionMinutos(), servicio.isActivo());
    }
}
