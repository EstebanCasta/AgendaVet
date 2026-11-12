package co.edu.autonoma.agendavetapi.entities;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

/** Guarda los cambios de estado de una cita. */
@Entity
@Table(name = "historial_estado_cita")
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


    public HistorialEstadoCitaEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CitaEntity getCita() {
        return cita;
    }

    public void setCita(CitaEntity cita) {
        this.cita = cita;
    }

    public EstadoCita getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(EstadoCita estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public EstadoCita getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(EstadoCita estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public OffsetDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(OffsetDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }
}
