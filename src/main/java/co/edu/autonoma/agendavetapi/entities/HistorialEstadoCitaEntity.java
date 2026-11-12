package co.edu.autonoma.agendavetapi.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;

/** Guarda los cambios de estado de una cita. */
@Entity
@Table(name = "historial_estado_cita")
@Getter
@Setter
@NoArgsConstructor
public class HistorialEstadoCitaEntity {

    /** Identificador del movimiento de historial. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Cita a la que pertenece este cambio. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cita_id", nullable = false)
    private CitaEntity cita;

    /** Estado que tenía antes; es null en el registro inicial de una cita. */
    @Column(name = "estado_anterior", length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoCita estadoAnterior;

    /** Estado que quedó vigente después del cambio. */
    @Column(name = "estado_nuevo", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoCita estadoNuevo;

    /** Fecha y hora en que se registró el cambio. */
    @Column(name = "fecha_cambio", nullable = false)
    private OffsetDateTime fechaCambio;


}
