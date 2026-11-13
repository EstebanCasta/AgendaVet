package co.edu.autonoma.agendavetapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.edu.autonoma.agendavetapi.dto.CitaResponse;
import co.edu.autonoma.agendavetapi.dto.CitaRequest;
import co.edu.autonoma.agendavetapi.entities.CitaEntity;
import co.edu.autonoma.agendavetapi.entities.DisponibilidadEntity;
import co.edu.autonoma.agendavetapi.entities.EstadoCita;
import co.edu.autonoma.agendavetapi.entities.MascotaEntity;
import co.edu.autonoma.agendavetapi.entities.ProfesionalEntity;
import co.edu.autonoma.agendavetapi.entities.ServicioEntity;
import co.edu.autonoma.agendavetapi.exception.RecursoNoEncontradoException;
import co.edu.autonoma.agendavetapi.repository.CitaRepository;
import co.edu.autonoma.agendavetapi.repository.DisponibilidadRepository;
import co.edu.autonoma.agendavetapi.repository.HistorialEstadoCitaRepository;
import co.edu.autonoma.agendavetapi.repository.MascotaRepository;
import co.edu.autonoma.agendavetapi.repository.ProfesionalRepository;
import co.edu.autonoma.agendavetapi.repository.ServicioRepository;

/** Pruebas unitarias del servicio de citas, sin conectarse a PostgreSQL. */
@ExtendWith(MockitoExtension.class)
class CitaServiceTest {

    // Dobles de prueba: simulan los repositorios que usa CitaService.
    @Mock
    private CitaRepository citaRepository;

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private ProfesionalRepository profesionalRepository;

    @Mock
    private ServicioRepository servicioRepository;

    @Mock
    private DisponibilidadRepository disponibilidadRepository;

    @Mock
    private HistorialEstadoCitaRepository historialRepository;

    // Mockito crea el servicio real y le entrega los seis repositorios simulados.
    @InjectMocks
    private CitaService citaService;

    @Test
    void obtener_citaExistente_devuelveSuRespuesta() {
        // Arrange: se prepara una cita y se programa la respuesta del repositorio falso.
        CitaEntity cita = new CitaEntity();
        cita.setId(3);

        MascotaEntity mascota = new MascotaEntity();
        mascota.setId(10);
        cita.setMascota(mascota);

        ProfesionalEntity profesional = new ProfesionalEntity();
        profesional.setId(20);
        cita.setProfesional(profesional);

        ServicioEntity servicio = new ServicioEntity();
        servicio.setId(30);
        cita.setServicio(servicio);

        DisponibilidadEntity disponibilidad = new DisponibilidadEntity();
        disponibilidad.setId(40);
        cita.setDisponibilidad(disponibilidad);

        cita.setEstado(EstadoCita.PROGRAMADA);
        cita.setNotas("Primera consulta");

        when(citaRepository.findById(3)).thenReturn(Optional.of(cita));

        // Act: se llama al método real del Service.
        CitaResponse respuesta = citaService.obtener(3);

        // Assert: se comprueba que el Service devolvió los datos de la cita.
        assertThat(respuesta.getId()).isEqualTo(3);
        assertThat(respuesta.getMascotaId()).isEqualTo(10);
        assertThat(respuesta.getProfesionalId()).isEqualTo(20);
        assertThat(respuesta.getServicioId()).isEqualTo(30);
        assertThat(respuesta.getDisponibilidadId()).isEqualTo(40);
        assertThat(respuesta.getEstado()).isEqualTo(EstadoCita.PROGRAMADA);
        assertThat(respuesta.getNotas()).isEqualTo("Primera consulta");

        // verify comprueba que el Service consultó el repositorio con el ID indicado.
        verify(citaRepository).findById(3);
    }

    @Test
    void obtener_citaInexistente_lanzaErrorDeNoEncontrado() {
        // Arrange: el repositorio falso indica que no hay cita con ese ID.
        when(citaRepository.findById(99)).thenReturn(Optional.empty());

        // Act: llamar a obtener debe producir la excepción del proyecto.
        RecursoNoEncontradoException error = assertThrows(
                RecursoNoEncontradoException.class,
                () -> citaService.obtener(99));

        // Assert: el mensaje identifica el recurso que no se encontró.
        assertThat(error.getMessage()).contains("99");
    }

    @Test
    void crear_citaNuevaQuedaProgramada_yCalculaHoraFinal() {
        // Arrange: preparar los registros relacionados y la solicitud.
        MascotaEntity mascota = new MascotaEntity();
        mascota.setId(10);

        ServicioEntity servicio = new ServicioEntity();
        servicio.setId(30);
        servicio.setDuracionMinutos(30);

        ProfesionalEntity profesional = new ProfesionalEntity();
        profesional.setId(20);
        profesional.getServicios().add(servicio);

        DisponibilidadEntity disponibilidad = new DisponibilidadEntity();
        disponibilidad.setId(40);
        disponibilidad.setProfesional(profesional);
        disponibilidad.setFechaInicio(OffsetDateTime.parse("2026-11-20T08:00:00-05:00"));
        disponibilidad.setFechaFin(OffsetDateTime.parse("2026-11-20T12:00:00-05:00"));

        OffsetDateTime inicio = OffsetDateTime.parse("2026-11-20T09:00:00-05:00");
        CitaRequest solicitud = new CitaRequest();
        solicitud.setMascotaId(10);
        solicitud.setProfesionalId(20);
        solicitud.setServicioId(30);
        solicitud.setDisponibilidadId(40);
        solicitud.setFechaInicio(inicio);
        solicitud.setNotas("Primera consulta");

        when(mascotaRepository.findById(10)).thenReturn(Optional.of(mascota));
        when(profesionalRepository.findById(20)).thenReturn(Optional.of(profesional));
        when(servicioRepository.findById(30)).thenReturn(Optional.of(servicio));
        when(disponibilidadRepository.findById(40)).thenReturn(Optional.of(disponibilidad));
        when(citaRepository.existeSolapamiento(20, 10, inicio,
                inicio.plusMinutes(30), null, EstadoCita.PROGRAMADA)).thenReturn(false);
        when(citaRepository.saveAndFlush(any(CitaEntity.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        // Act: crear la cita con el servicio real y los repositorios simulados.
        CitaResponse respuesta = citaService.crear(solicitud);

        // Assert: aplicar el estado inicial y calcular el fin con la duración (30 minutos).
        assertThat(respuesta.getEstado()).isEqualTo(EstadoCita.PROGRAMADA);
        assertThat(respuesta.getFechaInicio()).isEqualTo(inicio);
        assertThat(respuesta.getFechaFin()).isEqualTo(inicio.plusMinutes(30));
    }

    @Test
    void reprogramar_citaInexistente_noIntentaGuardarla() {
        // Arrange: la búsqueda del ID no encuentra ninguna cita.
        when(citaRepository.findById(99)).thenReturn(Optional.empty());
        CitaRequest solicitud = new CitaRequest();

        // Act: reprogramar debe fallar antes de intentar guardar.
        assertThrows(RecursoNoEncontradoException.class,
                () -> citaService.reprogramar(99, solicitud));

        // never comprueba que no se llamó al guardado porque la cita no existe.
        verify(citaRepository, never()).saveAndFlush(any(CitaEntity.class));
    }
}
