package co.edu.autonoma.agendavetapi.dto;

import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/** Datos recibidos para crear o actualizar un profesional y sus servicios. */
public class ProfesionalRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede superar 100 caracteres")
    private String apellido;
    @Size(max = 100, message = "La especialidad no puede superar 100 caracteres")
    private String especialidad;
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    @Size(max = 150, message = "El correo no puede superar 150 caracteres")
    private String correo;
    private Boolean activo;
    /** IDs de los servicios que el profesional ofrece. */
    private List<@Positive(message = "Cada id de servicio debe ser positivo") Integer> serviciosIds;

    public ProfesionalRequest() {
    }

    public ProfesionalRequest(String nombre, String apellido, String especialidad, String correo, Boolean activo, List<@Positive(message = "Cada id de servicio debe ser positivo") Integer> serviciosIds) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.correo = correo;
        this.activo = activo;
        this.serviciosIds = serviciosIds;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<@Positive(message = "Cada id de servicio debe ser positivo") Integer> getServiciosIds() {
        return serviciosIds;
    }

    public void setServiciosIds(List<@Positive(message = "Cada id de servicio debe ser positivo") Integer> serviciosIds) {
        this.serviciosIds = serviciosIds;
    }

}
