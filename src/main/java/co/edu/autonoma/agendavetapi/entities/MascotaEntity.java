package co.edu.autonoma.   agendavetapi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDate;

/** Mascota. Sin datos clínicos (RN-06). */
@Entity
@Table(name = "mascota")
@Getter
@Setter
@NoArgsConstructor
public class MascotaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "responsable_id", nullable = false)
    private ResponsableEntity responsable;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "especie", nullable = false, length = 50)
    private String especie;

    @Column(name = "raza", length = 80)
    private String raza;

    @Column(name = "sexo", length = 10)
    private String sexo;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "activa", nullable = false)
    private boolean activa = true;

public void setId(Integer id) {
        this.id = id;
    }

public void setResponsable(ResponsableEntity responsable) {
        this.responsable = responsable;
    }

public void setNombre(String nombre) {
        this.nombre = nombre;
    }

public void setEspecie(String especie) {
        this.especie = especie;
    }

public void setRaza(String raza) {
        this.raza = raza;
    }

public void setSexo(String sexo) {
        this.sexo = sexo;
    }

public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

public void setActiva(boolean activa) {
        this.activa = activa;
    }
}