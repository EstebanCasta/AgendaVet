package co.edu.autonoma.agendavetapi.service;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.autonoma.agendavetapi.dto.CambioEstadoCitaRequest;
import co.edu.autonoma.agendavetapi.dto.CitaRequest;
import co.edu.autonoma.agendavetapi.dto.CitaResponse;
import co.edu.autonoma.agendavetapi.dto.HistorialEstadoCitaResponse;
import co.edu.autonoma.agendavetapi.entities.CitaEntity;
import co.edu.autonoma.agendavetapi.entities.DisponibilidadEntity;
import co.edu.autonoma.agendavetapi.entities.EstadoCita;
import co.edu.autonoma.agendavetapi.entities.HistorialEstadoCitaEntity;
import co.edu.autonoma.agendavetapi.entities.MascotaEntity;
import co.edu.autonoma.agendavetapi.entities.ProfesionalEntity;
import co.edu.autonoma.agendavetapi.entities.ServicioEntity;
import co.edu.autonoma.agendavetapi.exception.ConflictoException;
import co.edu.autonoma.agendavetapi.exception.RecursoNoEncontradoException;
import co.edu.autonoma.agendavetapi.repository.CitaRepository;
import co.edu.autonoma.agendavetapi.repository.DisponibilidadRepository;
import co.edu.autonoma.agendavetapi.repository.HistorialEstadoCitaRepository;
import co.edu.autonoma.agendavetapi.repository.MascotaRepository;
import co.edu.autonoma.agendavetapi.repository.ProfesionalRepository;
import co.edu.autonoma.agendavetapi.repository.ServicioRepository;

@Service
@Transactional
public class CitaService {
    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;
    private final ProfesionalRepository profesionalRepository;
    private final ServicioRepository servicioRepository;
    private final DisponibilidadRepository disponibilidadRepository;
    private final HistorialEstadoCitaRepository historialRepository;

    public CitaService(CitaRepository citaRepository, MascotaRepository mascotaRepository,
            ProfesionalRepository profesionalRepository, ServicioRepository servicioRepository,
            DisponibilidadRepository disponibilidadRepository,
            HistorialEstadoCitaRepository historialRepository) {
        this.citaRepository = citaRepository;
        this.mascotaRepository = mascotaRepository;
        this.profesionalRepository = profesionalRepository;
        this.servicioRepository = servicioRepository;
        this.disponibilidadRepository = disponibilidadRepository;
        this.historialRepository = historialRepository;
    }

    @Transactional(readOnly = true)
    public List<CitaResponse> listar(EstadoCita estado, Integer mascotaId, Integer profesionalId,
            OffsetDateTime desde, OffsetDateTime hasta) {
        if (desde != null && hasta != null && desde.isAfter(hasta)) {
            throw new ConflictoException("La fecha desde no puede ser posterior a la fecha hasta.");
        }
        return citaRepository.buscarConFiltros(estado, mascotaId, profesionalId, desde, hasta)
                .stream().map(CitaResponse::desde).toList();
    }

    @Transactional(readOnly = true)
    public CitaResponse obtener(Integer id) { return CitaResponse.desde(buscar(id)); }

    @Transactional(readOnly = true)
    public List<HistorialEstadoCitaResponse> listarHistorial(Integer citaId) {
        buscar(citaId);
        return historialRepository.findByCita_IdOrderByFechaCambioAsc(citaId)
                .stream().map(HistorialEstadoCitaResponse::desde).toList();
    }

    public CitaResponse crear(CitaRequest solicitud) {
        ReferenciasCita referencias = validarSolicitud(solicitud, null);
        CitaEntity cita = new CitaEntity();
        aplicar(cita, solicitud, referencias);
        cita.setEstado(EstadoCita.PROGRAMADA);
        return CitaResponse.desde(citaRepository.saveAndFlush(cita));
    }

    public CitaResponse reprogramar(Integer id, CitaRequest solicitud) {
        CitaEntity cita = buscar(id);
        if (cita.getEstado() != EstadoCita.PROGRAMADA) {
            throw new ConflictoException("Solo se pueden modificar citas en estado PROGRAMADA.");
        }
        ReferenciasCita referencias = validarSolicitud(solicitud, id);
        aplicar(cita, solicitud, referencias);
        return CitaResponse.desde(citaRepository.saveAndFlush(cita));
    }

    public CitaResponse cambiarEstado(Integer id, CambioEstadoCitaRequest solicitud) {
        CitaEntity cita = buscar(id);
        if (!cita.getEstado().puedeTransicionarA(solicitud.estado())) {
            throw new ConflictoException("Transición de estado no permitida: "
                    + cita.getEstado() + " → " + solicitud.estado() + ".");
        }
        cita.setEstado(solicitud.estado());
        return CitaResponse.desde(citaRepository.saveAndFlush(cita));
    }

    private ReferenciasCita validarSolicitud(CitaRequest solicitud, Integer citaId) {
        MascotaEntity mascota = mascotaRepository.findById(solicitud.mascotaId()).orElseThrow(
                () -> new RecursoNoEncontradoException("No se encontró la mascota con id " + solicitud.mascotaId() + "."));
        ProfesionalEntity profesional = profesionalRepository.findById(solicitud.profesionalId()).orElseThrow(
                () -> new RecursoNoEncontradoException("No se encontró el profesional con id " + solicitud.profesionalId() + "."));
        ServicioEntity servicio = servicioRepository.findById(solicitud.servicioId()).orElseThrow(
                () -> new RecursoNoEncontradoException("No se encontró el servicio con id " + solicitud.servicioId() + "."));
        DisponibilidadEntity disponibilidad = disponibilidadRepository.findById(solicitud.disponibilidadId()).orElseThrow(
                () -> new RecursoNoEncontradoException("No se encontró la disponibilidad con id " + solicitud.disponibilidadId() + "."));

        if (!mascota.isActiva()) throw new ConflictoException("No se pueden programar citas para una mascota inactiva.");
        if (!profesional.isActivo()) throw new ConflictoException("No se pueden asignar citas a un profesional inactivo.");
        if (!servicio.isActivo()) throw new ConflictoException("No se pueden programar citas para un servicio inactivo.");
        if (!disponibilidad.getProfesional().getId().equals(profesional.getId())) {
            throw new ConflictoException("La disponibilidad no pertenece al profesional indicado.");
        }
        if (profesional.getServicios().stream().noneMatch(s -> s.getId().equals(servicio.getId()))) {
            throw new ConflictoException("El profesional no presta el servicio seleccionado.");
        }

        OffsetDateTime fechaFin = solicitud.fechaInicio().plusMinutes(servicio.getDuracionMinutos());
        if (solicitud.fechaInicio().isBefore(disponibilidad.getFechaInicio())
                || fechaFin.isAfter(disponibilidad.getFechaFin())) {
            throw new ConflictoException("La cita debe quedar dentro del bloque de disponibilidad.");
        }
        if (citaRepository.existeSolapamiento(profesional.getId(), mascota.getId(), solicitud.fechaInicio(),
                fechaFin, citaId, EstadoCita.PROGRAMADA)) {
            throw new ConflictoException("La mascota o el profesional ya tiene una cita programada en ese horario.");
        }
        return new ReferenciasCita(mascota, profesional, servicio, disponibilidad);
    }

    private void aplicar(CitaEntity cita, CitaRequest solicitud, ReferenciasCita referencias) {
        cita.setMascota(referencias.mascota());
        cita.setProfesional(referencias.profesional());
        cita.setServicio(referencias.servicio());
        cita.setDisponibilidad(referencias.disponibilidad());
        cita.setFechaInicio(solicitud.fechaInicio());
        cita.setNotas(solicitud.notas());
    }

    private CitaEntity buscar(Integer id) {
        return citaRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("No se encontró la cita con id " + id + "."));
    }

    private record ReferenciasCita(MascotaEntity mascota, ProfesionalEntity profesional,
            ServicioEntity servicio, DisponibilidadEntity disponibilidad) { }
}
