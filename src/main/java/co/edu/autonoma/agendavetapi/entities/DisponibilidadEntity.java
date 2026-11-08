package co.edu.autonoma.   agendavetapi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

/** Bloque de atención del profesional (RN-03). */
@Entity
@Table(name = "disponibilidad")
@Getter
@Setter
@NoArgsConstructor
public class DisponibilidadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profesional_id", nullable = false)
    private ProfesionalEntity profesional;

    @Column(name = "fecha_inicio", nullable = false)
    private OffsetDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private OffsetDateTime fechaFin;

public void setId(Integer id) {
        this.id = id;
    }

public void setProfesional(ProfesionalEntity profesional) {
        this.profesional = profesional;
    }

public void setFechaInicio(OffsetDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

public void setFechaFin(OffsetDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
}