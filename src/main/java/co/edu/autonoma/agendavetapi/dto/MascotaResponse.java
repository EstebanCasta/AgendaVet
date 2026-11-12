package co.edu.autonoma.agendavetapi.dto;

import java.time.LocalDate;
import co.edu.autonoma.agendavetapi.entities.MascotaEntity;

/** Datos básicos de la mascota que se envían en las respuestas HTTP. */
public class MascotaResponse {

    private Integer id;
    private Integer responsableId;
    private String nombre;
    private String especie;
    private String raza;
    private String sexo;
    private LocalDate fechaNacimiento;
    private boolean activa;

    public MascotaResponse() {
    }

    public MascotaResponse(Integer id, Integer responsableId, String nombre, String especie, String raza, String sexo, LocalDate fechaNacimiento, boolean activa) {
        this.id = id;
        this.responsableId = responsableId;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.activa = activa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean getActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    /** Convierte la entidad a DTO y expone al responsable solo por su ID. */
    public static MascotaResponse desde(MascotaEntity mascota) {
        return new MascotaResponse(mascota.getId(), mascota.getResponsable().getId(), mascota.getNombre(),
                mascota.getEspecie(), mascota.getRaza(), mascota.getSexo(), mascota.getFechaNacimiento(),
                mascota.isActiva());
    }

}
