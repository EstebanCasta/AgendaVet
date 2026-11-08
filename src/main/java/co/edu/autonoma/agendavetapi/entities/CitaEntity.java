package co.edu.autonoma.agendavetapi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

/** Cita. fecha_fin la recalcula el trigger de la BD a partir del servicio (RN-02). */
@Entity
@Table(name = "cita")
@Getter
@Setter
@NoArgsConstructor
public class CitaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mascota_id", nullable = false)
    private MascotaEntity mascota;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profesional_id", nullable = false)
    private ProfesionalEntity profesional;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "servicio_id", nullable = false)
    private ServicioEntity servicio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "disponibilidad_id", nullable = false)
    private DisponibilidadEntity disponibilidad;

    @Column(name = "fecha_inicio", nullable = false)
    private OffsetDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private OffsetDateTime fechaFin;

    @Column(name = "estado", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoCita estado = EstadoCita.PROGRAMADA;

    @Column(name = "notas", length = 255)
    private String notas;

    @Column(name = "fecha_creacion", nullable = false)
    @Generated(event = EventType.INSERT)
    private OffsetDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = false)
    @Generated(event = {EventType.INSERT, EventType.UPDATE})
    private OffsetDateTime fechaActualizacion;

public void setId(Integer id) {
        this.id = id;
    }

public void setMascota(MascotaEntity mascota) {
        this.mascota = mascota;
    }

public void setProfesional(ProfesionalEntity profesional) {
        this.profesional = profesional;
    }

public void setServicio(ServicioEntity servicio) {
        this.servicio = servicio;
    }

public void setDisponibilidad(DisponibilidadEntity disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

public void setFechaInicio(OffsetDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

public void setFechaFin(OffsetDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

public void setNotas(String notas) {
        this.notas = notas;
    }

public void setFechaCreacion(OffsetDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

public void setFechaActualizacion(OffsetDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}