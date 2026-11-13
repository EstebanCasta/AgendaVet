package co.edu.autonoma.agendavetapi.entities;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import co.edu.autonoma.agendavetapi.dto.CitaResponse;

class CitaEntityTest {

    @Test
    void convertirCitaARespuesta_copiaSusDatos() {
        // Arrange: preparar la cita y los objetos relacionados.
        MascotaEntity mascota = new MascotaEntity();
        mascota.setId(10);

        ProfesionalEntity profesional = new ProfesionalEntity();
        profesional.setId(20);

        ServicioEntity servicio = new ServicioEntity();
        servicio.setId(30);

        DisponibilidadEntity disponibilidad = new DisponibilidadEntity();
        disponibilidad.setId(40);

        OffsetDateTime inicio = OffsetDateTime.parse("2026-11-20T09:00:00-05:00");
        OffsetDateTime fin = OffsetDateTime.parse("2026-11-20T09:30:00-05:00");
        OffsetDateTime creacion = OffsetDateTime.parse("2026-11-19T15:00:00-05:00");
        OffsetDateTime actualizacion = OffsetDateTime.parse("2026-11-19T16:00:00-05:00");

        CitaEntity cita = new CitaEntity();
        cita.setId(1);
        cita.setMascota(mascota);
        cita.setProfesional(profesional);
        cita.setServicio(servicio);
        cita.setDisponibilidad(disponibilidad);
        cita.setFechaInicio(inicio);
        cita.setFechaFin(fin);
        cita.setEstado(EstadoCita.PROGRAMADA);
        cita.setNotas("Primera consulta");
        cita.setFechaCreacion(creacion);
        cita.setFechaActualizacion(actualizacion);

        // Act: ejecutar el método que convierte la cita a su respuesta.
        CitaResponse respuesta = CitaResponse.desde(cita);

        // Assert: revisar que la respuesta tenga los datos esperados.
        assertThat(respuesta.getId()).isEqualTo(1);
        assertThat(respuesta.getMascotaId()).isEqualTo(10);
        assertThat(respuesta.getProfesionalId()).isEqualTo(20);
        assertThat(respuesta.getServicioId()).isEqualTo(30);
        assertThat(respuesta.getDisponibilidadId()).isEqualTo(40);
        assertThat(respuesta.getFechaInicio()).isEqualTo(inicio);
        assertThat(respuesta.getFechaFin()).isEqualTo(fin);
        assertThat(respuesta.getEstado()).isEqualTo(EstadoCita.PROGRAMADA);
        assertThat(respuesta.getNotas()).isEqualTo("Primera consulta");
        assertThat(respuesta.getFechaCreacion()).isEqualTo(creacion);
        assertThat(respuesta.getFechaActualizacion()).isEqualTo(actualizacion);
    }
}
