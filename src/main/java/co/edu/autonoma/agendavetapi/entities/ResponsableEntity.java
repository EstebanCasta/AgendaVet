package co.edu.autonoma.   agendavetapi.entities;

import lombok.NoArgsConstructor;

import lombok.Setter;

import lombok.Getter;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

/** Dueño de la mascota. Solo datos de contacto (RN-06). */
@Entity
@Table(name = "responsable", uniqueConstraints = @UniqueConstraint(columnNames = {"tipo_documento", "numero_documento"}))
@Getter
@Setter
@NoArgsConstructor
public class ResponsableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "tipo_documento", nullable = false, length = 10)
    private String tipoDocumento;

    @Column(name = "numero_documento", nullable = false, length = 30)
    private String numeroDocumento;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "correo", length = 150)
    private String correo;

    @Column(name = "fecha_registro", nullable = false)
    @Generated(event = EventType.INSERT)
    private OffsetDateTime fechaRegistro;
}