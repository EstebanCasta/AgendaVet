package co.edu.autonoma.agendavetapi.entities;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

/** Guarda la información de una cita. */
@Entity
@Table(name = "cita")
public class CitaEntity {

    // Identificador generado por PostgreSQL.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Mascota que será atendida en la cita. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mascota_id", nullable = false)
    private MascotaEntity mascota;

    /** Profesional responsable de atender la cita. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profesional_id", nullable = false)
    private ProfesionalEntity profesional;

    /** Servicio cuya duración determina la hora final de la cita. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "servicio_id", nullable = false)
    private ServicioEntity servicio;

    /** Bloque en el que debe quedar contenida la cita. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "disponibilidad_id", nullable = false)
    private DisponibilidadEntity disponibilidad;

    /** Fecha y hora en que inicia la atención. */
    @Column(name = "fecha_inicio", nullable = false)
    private OffsetDateTime fechaInicio;

    /** Fecha y hora calculada por el servicio según la duración del servicio. */
    @Column(name = "fecha_fin", nullable = false)
    private OffsetDateTime fechaFin;

    /** Situación actual de la cita; inicia como PROGRAMADA. */
    @Column(name = "estado", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoCita estado = EstadoCita.PROGRAMADA;

    /** Nota breve de la cita; no debe usarse para guardar historia clínica. */
    @Column(name = "notas", length = 255)
    private String notas;

    /** Momento en que se creó el registro. */
    @Column(name = "fecha_creacion", nullable = false)
    private OffsetDateTime fechaCreacion;

    /** Momento de la última modificación de la cita. */
    @Column(name = "fecha_actualizacion", nullable = false)
    private OffsetDateTime fechaActualizacion;


    public CitaEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MascotaEntity getMascota() {
        return mascota;
    }

    public void setMascota(MascotaEntity mascota) {
        this.mascota = mascota;
    }

    public ProfesionalEntity getProfesional() {
        return profesional;
    }

    public void setProfesional(ProfesionalEntity profesional) {
        this.profesional = profesional;
    }

    public ServicioEntity getServicio() {
        return servicio;
    }

    public void setServicio(ServicioEntity servicio) {
        this.servicio = servicio;
    }

    public DisponibilidadEntity getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(DisponibilidadEntity disponibilidad) {
        this.disponibilidad = disponibilidad;
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

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public OffsetDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(OffsetDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public OffsetDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(OffsetDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
