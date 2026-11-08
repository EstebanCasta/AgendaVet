package co.edu.autonoma.agendavetapi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

public void setId(Integer id) {
        this.id = id;
    }

public void setNombre(String nombre) {
        this.nombre = nombre;
    }

public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

public void setDuracionMinutos(Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
