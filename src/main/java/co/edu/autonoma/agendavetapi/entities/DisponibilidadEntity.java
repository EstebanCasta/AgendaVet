package co.edu.autonoma.agendavetapi.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;

/** Bloque de atención del profesional (RN-03). */
@Entity
@Table(name = "disponibilidad")
@Getter
@Setter
@NoArgsConstructor
public class DisponibilidadEntity {

    /** Identificador del bloque de disponibilidad. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Profesional dueño del bloque horario. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profesional_id", nullable = false)
    private ProfesionalEntity profesional;

    /** Inicio del horario que el profesional puede atender. */
    @Column(name = "fecha_inicio", nullable = false)
    private OffsetDateTime fechaInicio;

    /** Fin del horario que el profesional puede atender. */
    @Column(name = "fecha_fin", nullable = false)
    private OffsetDateTime fechaFin;


}
