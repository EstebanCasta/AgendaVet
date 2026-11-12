package co.edu.autonoma.agendavetapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/** Datos recibidos para crear o reemplazar un servicio del catálogo. */
public class ServicioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;
    @Size(max = 255, message = "La descripción no puede superar 255 caracteres")
    private String descripcion;
    @Positive(message = "La duración debe ser mayor que cero")
    /** Tiempo en minutos usado para calcular la duración de la cita. */
    private Integer duracionMinutos;
    private Boolean activo;

    public ServicioRequest() {
    }

    public ServicioRequest(String nombre, String descripcion, Integer duracionMinutos, Boolean activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMinutos = duracionMinutos;
        this.activo = activo;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}
