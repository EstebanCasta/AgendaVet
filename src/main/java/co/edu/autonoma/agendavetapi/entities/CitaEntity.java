package co.edu.autonoma.agendavetapi.entities;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Guarda la información de una cita. */
@Entity
@Table(name = "cita")
@Getter
@Setter
@NoArgsConstructor
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

}