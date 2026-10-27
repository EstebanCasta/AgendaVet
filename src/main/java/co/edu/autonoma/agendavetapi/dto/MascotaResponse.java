package co.edu.autonoma.agendavetapi.dto;

import java.time.LocalDate;
import co.edu.autonoma.agendavetapi.entities.MascotaEntity;

public record MascotaResponse(Integer id, Integer responsableId, String nombre, String especie,
        String raza, String sexo, LocalDate fechaNacimiento, boolean activa) {

    public static MascotaResponse desde(MascotaEntity mascota) {
        return new MascotaResponse(mascota.getId(), mascota.getResponsable().getId(), mascota.getNombre(),
                mascota.getEspecie(), mascota.getRaza(), mascota.getSexo(), mascota.getFechaNacimiento(),
                mascota.isActiva());
    }
}
