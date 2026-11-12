package co.edu.autonoma.agendavetapi.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;

/** Dueño de la mascota. Solo datos de contacto (RN-06). */
@Entity
@Table(name = "responsable", uniqueConstraints = @UniqueConstraint(columnNames = {"tipo_documento", "numero_documento"}))
@Getter
@Setter
@NoArgsConstructor
public class ResponsableEntity {

    /** Identificador del responsable. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Nombre y apellido usados para identificar al responsable. */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    /** Tipo y número de documento forman una combinación única. */
    @Column(name = "tipo_documento", nullable = false, length = 10)
    private String tipoDocumento;

    @Column(name = "numero_documento", nullable = false, length = 30)
    private String numeroDocumento;

    /** Datos de contacto opcionales. */
    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "correo", length = 150)
    private String correo;

    /** Fecha asignada por el servicio cuando se crea el responsable. */
    @Column(name = "fecha_registro", nullable = false)
    private OffsetDateTime fechaRegistro;


}
