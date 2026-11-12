package co.edu.autonoma.agendavetapi.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

/**
 * Contiene las operaciones de citas y las reglas que deben cumplirse antes
 * de guardarlas. El controlador recibe HTTP; este servicio valida relaciones,
 * horarios y estados antes de llamar a los repositorios.
 */
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
        // Rechaza un rango invertido antes de consultar la base de datos.
        if (desde != null && hasta != null && desde.isAfter(hasta)) {
            throw new ConflictoException("La fecha desde no puede ser posterior a la fecha hasta.");
        }

        // El repositorio aplica solo los filtros que llegaron informados.
        List<CitaEntity> citas = citaRepository.buscarConFiltros(estado, mascotaId, profesionalId, desde, hasta);
        List<CitaResponse> respuestas = new ArrayList<>();
        for (CitaEntity cita : citas) {
            respuestas.add(CitaResponse.desde(cita));
        }
        return respuestas;
    }

    @Transactional(readOnly = true)
    public CitaResponse obtener(Integer id) {
        // buscar centraliza el error 404 cuando el identificador no existe.
        return CitaResponse.desde(buscar(id));
    }

    @Transactional(readOnly = true)
    public List<HistorialEstadoCitaResponse> listarHistorial(Integer citaId) {
        // Comprueba primero que la cita exista, incluso si todavía no tiene historial.
        buscar(citaId);
        List<HistorialEstadoCitaResponse> respuestas = new ArrayList<>();
        List<HistorialEstadoCitaEntity> registros = historialRepository.findByCita_IdOrderByFechaCambioAsc(citaId);
        for (HistorialEstadoCitaEntity registro : registros) {
            respuestas.add(HistorialEstadoCitaResponse.desde(registro));
        }
        return respuestas;
    }

    public CitaResponse crear(CitaRequest solicitud) {
        // Una cita se guarda solo si sus referencias y horario pasan las reglas de negocio.
        ReferenciasCita referencias = validarSolicitud(solicitud, null);
        CitaEntity cita = new CitaEntity();
        OffsetDateTime ahora = OffsetDateTime.now();
        aplicar(cita, solicitud, referencias);
        cita.setEstado(EstadoCita.PROGRAMADA);
        // Estos valores se asignan en Java porque la base ya no usa triggers para generarlos.
        cita.setFechaCreacion(ahora);
        cita.setFechaActualizacion(ahora);
        // Primero se guarda la cita para obtener su ID; luego se registra el estado inicial.
        CitaEntity citaGuardada = citaRepository.saveAndFlush(cita);
        registrarCambioEstado(citaGuardada, null, EstadoCita.PROGRAMADA);
        return CitaResponse.desde(citaGuardada);
    }

    public CitaResponse reprogramar(Integer id, CitaRequest solicitud) {
        CitaEntity cita = buscar(id);
        // Solo una cita pendiente puede cambiar de horario o de datos asociados.
        if (cita.getEstado() != EstadoCita.PROGRAMADA) {
            throw new ConflictoException("Solo se pueden modificar citas en estado PROGRAMADA.");
        }

        ReferenciasCita referencias = validarSolicitud(solicitud, id);
        aplicar(cita, solicitud, referencias);
        return CitaResponse.desde(citaRepository.saveAndFlush(cita));
    }

    public CitaResponse cambiarEstado(Integer id, CambioEstadoCitaRequest solicitud) {
        CitaEntity cita = buscar(id);
        // La entidad define qué transiciones son válidas; no se aceptan saltos arbitrarios.
        if (!cita.getEstado().puedeTransicionarA(solicitud.getEstado())) {
            throw new ConflictoException("Transición de estado no permitida: "
                    + cita.getEstado() + " a " + solicitud.getEstado() + ".");
        }

        EstadoCita estadoAnterior = cita.getEstado();
        cita.setEstado(solicitud.getEstado());
        cita.setFechaActualizacion(OffsetDateTime.now());
        // Se persiste el estado actual y su historial dentro de la misma transacción.
        CitaEntity citaGuardada = citaRepository.saveAndFlush(cita);
        registrarCambioEstado(citaGuardada, estadoAnterior, solicitud.getEstado());
        return CitaResponse.desde(citaGuardada);
    }

    private ReferenciasCita validarSolicitud(CitaRequest solicitud, Integer citaId) {
        // Los IDs del request deben apuntar a registros existentes.
        Optional<MascotaEntity> mascotaEncontrada = mascotaRepository.findById(solicitud.getMascotaId());
        if (mascotaEncontrada.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontró la mascota con id " + solicitud.getMascotaId() + ".");
        }
        MascotaEntity mascota = mascotaEncontrada.get();

        Optional<ProfesionalEntity> profesionalEncontrado = profesionalRepository.findById(solicitud.getProfesionalId());
        if (profesionalEncontrado.isEmpty()) {
            throw new RecursoNoEncontradoException(
                    "No se encontró el profesional con id " + solicitud.getProfesionalId() + ".");
        }
        ProfesionalEntity profesional = profesionalEncontrado.get();

        Optional<ServicioEntity> servicioEncontrado = servicioRepository.findById(solicitud.getServicioId());
        if (servicioEncontrado.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontró el servicio con id " + solicitud.getServicioId() + ".");
        }
        ServicioEntity servicio = servicioEncontrado.get();

        Optional<DisponibilidadEntity> disponibilidadEncontrada =
                disponibilidadRepository.findById(solicitud.getDisponibilidadId());
        if (disponibilidadEncontrada.isEmpty()) {
            throw new RecursoNoEncontradoException(
                    "No se encontró la disponibilidad con id " + solicitud.getDisponibilidadId() + ".");
        }
        DisponibilidadEntity disponibilidad = disponibilidadEncontrada.get();

        // No se agenda con recursos que están desactivados.
        if (!mascota.isActiva()) {
            throw new ConflictoException("No se pueden programar citas para una mascota inactiva.");
        }
        if (!profesional.isActivo()) {
            throw new ConflictoException("No se pueden asignar citas a un profesional inactivo.");
        }
        if (!servicio.isActivo()) {
            throw new ConflictoException("No se pueden programar citas para un servicio inactivo.");
        }
        if (!disponibilidad.getProfesional().getId().equals(profesional.getId())) {
            throw new ConflictoException("La disponibilidad no pertenece al profesional indicado.");
        }
        // La relación profesional-servicio limita qué atenciones puede ofrecer cada profesional.
        if (!profesionalOfreceServicio(profesional, servicio)) {
            throw new ConflictoException("El profesional no presta el servicio seleccionado.");
        }

        OffsetDateTime fechaInicio = solicitud.getFechaInicio();
        // La duración final se calcula con la duración configurada para el servicio.
        OffsetDateTime fechaFin = fechaInicio.plusMinutes(servicio.getDuracionMinutos());
        // Toda la cita, no solo su hora inicial, debe caber dentro de la disponibilidad.
        if (fechaInicio.isBefore(disponibilidad.getFechaInicio())
                || fechaFin.isAfter(disponibilidad.getFechaFin())) {
            throw new ConflictoException("La cita debe quedar dentro del bloque de disponibilidad.");
        }
        // Se ignora la cita actual al reprogramar; las canceladas no bloquean el horario.
        if (citaRepository.existeSolapamiento(profesional.getId(), mascota.getId(), fechaInicio,
                fechaFin, citaId, EstadoCita.PROGRAMADA)) {
            throw new ConflictoException("La mascota o el profesional ya tiene una cita programada en ese horario.");
        }

        return new ReferenciasCita(mascota, profesional, servicio, disponibilidad);
    }

    private boolean profesionalOfreceServicio(ProfesionalEntity profesional, ServicioEntity servicio) {
        // Revisa la relación muchos a muchos entre profesionales y servicios.
        for (ServicioEntity servicioProfesional : profesional.getServicios()) {
            if (servicioProfesional.getId().equals(servicio.getId())) {
                return true;
            }
        }
        return false;
    }

    private void aplicar(CitaEntity cita, CitaRequest solicitud, ReferenciasCita referencias) {
        // Copia los datos permitidos del request; fechaFin nunca la decide el cliente.
        cita.setMascota(referencias.getMascota());
        cita.setProfesional(referencias.getProfesional());
        cita.setServicio(referencias.getServicio());
        cita.setDisponibilidad(referencias.getDisponibilidad());
        cita.setFechaInicio(solicitud.getFechaInicio());
        cita.setFechaFin(solicitud.getFechaInicio().plusMinutes(referencias.getServicio().getDuracionMinutos()));
        cita.setFechaActualizacion(OffsetDateTime.now());
        cita.setNotas(solicitud.getNotas());
    }

    private void registrarCambioEstado(CitaEntity cita, EstadoCita anterior, EstadoCita nuevo) {
        // Conserva cada cambio para que se pueda consultar la trazabilidad después.
        HistorialEstadoCitaEntity registro = new HistorialEstadoCitaEntity();
        registro.setCita(cita);
        registro.setEstadoAnterior(anterior);
        registro.setEstadoNuevo(nuevo);
        registro.setFechaCambio(OffsetDateTime.now());
        historialRepository.save(registro);
    }

    private CitaEntity buscar(Integer id) {
        // Convierte un resultado vacío en una respuesta de recurso no encontrado.
        Optional<CitaEntity> citaEncontrada = citaRepository.findById(id);
        if (citaEncontrada.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontró la cita con id " + id + ".");
        }
        return citaEncontrada.get();
    }

    private static class ReferenciasCita {
        // Agrupa las entidades ya buscadas y validadas para no repetir consultas al aplicar el request.
        private final MascotaEntity mascota;
        private final ProfesionalEntity profesional;
        private final ServicioEntity servicio;
        private final DisponibilidadEntity disponibilidad;

        public ReferenciasCita(MascotaEntity mascota, ProfesionalEntity profesional,
                ServicioEntity servicio, DisponibilidadEntity disponibilidad) {
            this.mascota = mascota;
            this.profesional = profesional;
            this.servicio = servicio;
            this.disponibilidad = disponibilidad;
        }

        public MascotaEntity getMascota() { return mascota; }
        public ProfesionalEntity getProfesional() { return profesional; }
        public ServicioEntity getServicio() { return servicio; }
        public DisponibilidadEntity getDisponibilidad() { return disponibilidad; }
    }
}
