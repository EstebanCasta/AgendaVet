package co.edu.autonoma.agendavetapi.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

/** Profesional. La relación N:M con Servicio se mapea sobre la tabla profesional_servicio. */
@Entity
@Table(name = "profesional")
@Getter
@Setter
@NoArgsConstructor
public class ProfesionalEntity {

    /** Identificador del profesional. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Nombre del profesional. */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /** Apellido del profesional. */
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    /** Área o especialidad profesional, si se informa. */
    @Column(name = "especialidad", length = 100)
    private String especialidad;

    /** Correo de contacto, único para cada profesional. */
    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String correo;

    /** Permite desactivar al profesional sin eliminar su ficha. */
    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    /** Servicios que este profesional está habilitado para prestar. */
    @ManyToMany
    @JoinTable(name = "profesional_servicio",
            joinColumns = @JoinColumn(name = "profesional_id"),
            inverseJoinColumns = @JoinColumn(name = "servicio_id"))
    private Set<ServicioEntity> servicios = new HashSet<>();


}
