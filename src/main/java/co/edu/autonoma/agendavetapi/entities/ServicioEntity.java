package co.edu.autonoma.agendavetapi.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Servicio publicado. La duración define la duración de la cita (RN-02). */
@Entity
@Table(name = "servicio")
@Getter
@Setter
@NoArgsConstructor
public class ServicioEntity {

    /** Identificador del servicio. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Nombre único que se muestra en el catálogo. */
    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    /** Descripción opcional del servicio. */
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    /** Tiempo que ocupa el servicio; se usa para calcular el fin de la cita. */
    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;

    /** Permite ocultar o deshabilitar el servicio sin borrar sus datos. */
    @Column(name = "activo", nullable = false)
    private boolean activo = true;


}
