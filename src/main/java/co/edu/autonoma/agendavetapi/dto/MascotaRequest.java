package co.edu.autonoma.agendavetapi.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/** Datos que el cliente envía para crear o actualizar una mascota. */
public class MascotaRequest {

    @NotNull(message = "El responsable es obligatorio")
    @Positive(message = "El id del responsable debe ser positivo")
    /** ID del responsable al que pertenece la mascota. */
    private Integer responsableId;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;
    @NotBlank(message = "La especie es obligatoria")
    @Size(max = 50, message = "La especie no puede superar 50 caracteres")
    private String especie;
    @Size(max = 80, message = "La raza no puede superar 80 caracteres")
    private String raza;
    @Size(max = 10, message = "El sexo no puede superar 10 caracteres")
    private String sexo;
    private LocalDate fechaNacimiento;
    private Boolean activa;

    public MascotaRequest() {
    }

    public MascotaRequest(Integer responsableId, String nombre, String especie, String raza, String sexo, LocalDate fechaNacimiento, Boolean activa) {
        this.responsableId = responsableId;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.activa = activa;
    }

    public Integer getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(Integer responsableId) {
        this.responsableId = responsableId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

}
