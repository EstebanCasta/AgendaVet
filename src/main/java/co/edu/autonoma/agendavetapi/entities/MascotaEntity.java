package co.edu.autonoma.agendavetapi.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

/** Mascota. Sin datos clínicos (RN-06). */
@Entity
@Table(name = "mascota")
public class MascotaEntity {

    /** Identificador de la mascota. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Responsable que registra y tiene a cargo la mascota. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "responsable_id", nullable = false)
    private ResponsableEntity responsable;

    /** Nombre con el que se identifica a la mascota. */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /** Especie (por ejemplo, canina o felina). */
    @Column(name = "especie", nullable = false, length = 50)
    private String especie;

    /** Raza informada, si se conoce. */
    @Column(name = "raza", length = 80)
    private String raza;

    /** Sexo informado, si se conoce. */
    @Column(name = "sexo", length = 10)
    private String sexo;

    /** Fecha de nacimiento, si se conoce. */
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    /** Permite desactivar una mascota sin eliminar su registro. */
    @Column(name = "activa", nullable = false)
    private boolean activa = true;


    public MascotaEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ResponsableEntity getResponsable() {
        return responsable;
    }

    public void setResponsable(ResponsableEntity responsable) {
        this.responsable = responsable;
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

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
