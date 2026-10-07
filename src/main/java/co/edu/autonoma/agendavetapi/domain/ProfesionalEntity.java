package co.edu.autonoma.agendavetapi.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/** Profesional. La relación N:M con Servicio se mapea sobre la tabla profesional_servicio. */
@Entity
@Table(name = "profesional")
public class ProfesionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "especialidad", length = 100)
    private String especialidad;

    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String correo;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @ManyToMany
    @JoinTable(name = "profesional_servicio",
            joinColumns = @JoinColumn(name = "profesional_id"),
            inverseJoinColumns = @JoinColumn(name = "servicio_id"))
    private Set<ServicioEntity> servicios = new HashSet<>();

    public ProfesionalEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Set<ServicioEntity> getServicios() {
        return servicios;
    }

    public void setServicios(Set<ServicioEntity> servicios) {
        this.servicios = servicios;
    }
}