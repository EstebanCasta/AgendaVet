package co.edu.autonoma.   agendavetapi.entities;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

/** Bloque de atención del profesional (RN-03). */
@Entity
@Table(name = "disponibilidad")
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

    public DisponibilidadEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProfesionalEntity getProfesional() {
        return profesional;
    }

    public void setProfesional(ProfesionalEntity profesional) {
        this.profesional = profesional;
    }

    public OffsetDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(OffsetDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public OffsetDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(OffsetDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
}