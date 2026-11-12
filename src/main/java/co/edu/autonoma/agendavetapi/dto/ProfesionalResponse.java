package co.edu.autonoma.agendavetapi.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import co.edu.autonoma.agendavetapi.entities.ProfesionalEntity;
import co.edu.autonoma.agendavetapi.entities.ServicioEntity;

/** Datos de un profesional junto con los IDs de sus servicios asociados. */
public class ProfesionalResponse {

    private Integer id;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String correo;
    private boolean activo;
    private List<Integer> serviciosIds;

    public ProfesionalResponse() {
    }

    public ProfesionalResponse(Integer id, String nombre, String apellido, String especialidad, String correo, boolean activo, List<Integer> serviciosIds) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.correo = correo;
        this.activo = activo;
        this.serviciosIds = serviciosIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Integer> getServiciosIds() {
        return serviciosIds;
    }

    public void setServiciosIds(List<Integer> serviciosIds) {
        this.serviciosIds = serviciosIds;
    }

    /** Convierte la entidad y sus servicios a una respuesta JSON. */
    public static ProfesionalResponse desde(ProfesionalEntity profesional) {
        List<Integer> serviciosIds = new ArrayList<>();
        for (ServicioEntity servicio : profesional.getServicios()) {
            serviciosIds.add(servicio.getId());
        }
        Collections.sort(serviciosIds);
        return new ProfesionalResponse(profesional.getId(), profesional.getNombre(), profesional.getApellido(),
                profesional.getEspecialidad(), profesional.getCorreo(), profesional.isActivo(), serviciosIds);
    }

}
