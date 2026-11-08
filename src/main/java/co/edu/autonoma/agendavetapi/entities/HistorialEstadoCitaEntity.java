package co.edu.autonoma.   agendavetapi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.Immutable;
import org.hibernate.generator.EventType;

/** Trazabilidad. Solo lectura: la llena un trigger de la BD al crear/cambiar el estado de la cita. */
@Immutable
@Entity
@Table(name = "historial_estado_cita")
@Getter
@Setter
@NoArgsConstructor
public class HistorialEstadoCitaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cita_id", nullable = false)
    private CitaEntity cita;

    @Column(name = "estado_anterior", length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoCita estadoAnterior;

    @Column(name = "estado_nuevo", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoCita estadoNuevo;

    @Column(name = "fecha_cambio", nullable = false)
    @Generated(event = EventType.INSERT)
    private OffsetDateTime fechaCambio;

public void setId(Integer id) {
        this.id = id;
    }

public void setCita(CitaEntity cita) {
        this.cita = cita;
    }

public void setEstadoAnterior(EstadoCita estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

public void setEstadoNuevo(EstadoCita estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

public void setFechaCambio(OffsetDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }
}