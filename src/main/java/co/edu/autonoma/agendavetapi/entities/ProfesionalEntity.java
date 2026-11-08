package co.edu.autonoma.   agendavetapi.entities;

import lombok.NoArgsConstructor;

import lombok.Setter;

import lombok.Getter;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/** Profesional. La relación N:M con Servicio se mapea sobre la tabla profesional_servicio. */
@Entity
@Table(name = "profesional")
@Getter
@Setter
@NoArgsConstructor
public class ProfesionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
}