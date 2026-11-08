package co.edu.autonoma.agendavetapi.entities;

import lombok.NoArgsConstructor;

import lombok.Setter;

import lombok.Getter;

import jakarta.persistence.*;

/** Servicio publicado. La duración define la duración de la cita (RN-02). */
@Entity
@Table(name = "servicio")
@Getter
@Setter
@NoArgsConstructor
public class ServicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;
}
