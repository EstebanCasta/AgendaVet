package co.edu.autonoma.agendavetapi.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

/** Mascota. Sin datos clínicos (RN-06). */
@Entity
@Table(name = "mascota")
@Getter
@Setter
@NoArgsConstructor
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


}
