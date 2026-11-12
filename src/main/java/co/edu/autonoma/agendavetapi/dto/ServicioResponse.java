package co.edu.autonoma.agendavetapi.dto;

import co.edu.autonoma.agendavetapi.entities.ServicioEntity;

/** Datos del servicio que se devuelven en las respuestas HTTP. */
public class ServicioResponse {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer duracionMinutos;
    private boolean activo;

    public ServicioResponse() {
    }

    public ServicioResponse(Integer id, String nombre, String descripcion, Integer duracionMinutos, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMinutos = duracionMinutos;
        this.activo = activo;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /** Convierte una entidad de servicio al formato de salida de la API. */
    public static ServicioResponse desde(ServicioEntity servicio) {
        return new ServicioResponse(servicio.getId(), servicio.getNombre(), servicio.getDescripcion(),
                servicio.getDuracionMinutos(), servicio.isActivo());
    }

}
