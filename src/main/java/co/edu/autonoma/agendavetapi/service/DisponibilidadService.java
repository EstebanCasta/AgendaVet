package co.edu.autonoma.agendavetapi.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.autonoma.agendavetapi.dto.DisponibilidadRequest;
import co.edu.autonoma.agendavetapi.dto.DisponibilidadResponse;
import co.edu.autonoma.agendavetapi.entities.DisponibilidadEntity;
import co.edu.autonoma.agendavetapi.entities.ProfesionalEntity;
import co.edu.autonoma.agendavetapi.exception.ConflictoException;
import co.edu.autonoma.agendavetapi.exception.RecursoNoEncontradoException;
import co.edu.autonoma.agendavetapi.repository.DisponibilidadRepository;
import co.edu.autonoma.agendavetapi.repository.ProfesionalRepository;

/** Aplica las reglas de horarios disponibles y coordina su persistencia. */
@Service
@Transactional
public class DisponibilidadService {

    private final DisponibilidadRepository disponibilidadRepository;
    private final ProfesionalRepository profesionalRepository;

    public DisponibilidadService(DisponibilidadRepository disponibilidadRepository,
            ProfesionalRepository profesionalRepository) {
        this.disponibilidadRepository = disponibilidadRepository;
        this.profesionalRepository = profesionalRepository;
    }

    /** Lista disponibilidades y valida que el rango de búsqueda tenga sentido. */
    @Transactional(readOnly = true)
    public List<DisponibilidadResponse> listar(Integer profesionalId, OffsetDateTime desde, OffsetDateTime hasta) {
        if (desde != null && hasta != null && desde.isAfter(hasta)) {
            throw new ConflictoException("La fecha desde no puede ser posterior a la fecha hasta.");
        }

        List<DisponibilidadEntity> disponibilidades =
                disponibilidadRepository.buscarConFiltros(profesionalId, desde, hasta);
        List<DisponibilidadResponse> respuestas = new ArrayList<>();
        for (DisponibilidadEntity disponibilidad : disponibilidades) {
            respuestas.add(DisponibilidadResponse.desde(disponibilidad));
        }
        return respuestas;
    }

    /** Busca una disponibilidad y la convierte al formato de respuesta. */
    @Transactional(readOnly = true)
    public DisponibilidadResponse obtener(Integer id) {
        return DisponibilidadResponse.desde(buscar(id));
    }

    /** Crea un bloque, validando su rango, profesional y posibles cruces. */
    public DisponibilidadResponse crear(DisponibilidadRequest solicitud) {
        validarRango(solicitud.getFechaInicio(), solicitud.getFechaFin());
        ProfesionalEntity profesional = buscarProfesional(solicitud.getProfesionalId());
        validarSolapamiento(profesional.getId(), solicitud.getFechaInicio(), solicitud.getFechaFin(), null);

        DisponibilidadEntity disponibilidad = new DisponibilidadEntity();
        aplicar(disponibilidad, profesional, solicitud);
        return DisponibilidadResponse.desde(disponibilidadRepository.saveAndFlush(disponibilidad));
    }

    /** Actualiza un bloque y excluye su propio ID al buscar solapamientos. */
    public DisponibilidadResponse actualizar(Integer id, DisponibilidadRequest solicitud) {
        validarRango(solicitud.getFechaInicio(), solicitud.getFechaFin());
        DisponibilidadEntity disponibilidad = buscar(id);
        ProfesionalEntity profesional = buscarProfesional(solicitud.getProfesionalId());
        validarSolapamiento(profesional.getId(), solicitud.getFechaInicio(), solicitud.getFechaFin(), id);

        aplicar(disponibilidad, profesional, solicitud);
        return DisponibilidadResponse.desde(disponibilidadRepository.saveAndFlush(disponibilidad));
    }

    /** Elimina el bloque indicado; la base puede impedirlo si está relacionado. */
    public void eliminar(Integer id) {
        disponibilidadRepository.delete(buscar(id));
        disponibilidadRepository.flush();
    }

    /** Copia al entity los datos permitidos del request. */
    private void aplicar(DisponibilidadEntity disponibilidad, ProfesionalEntity profesional,
            DisponibilidadRequest solicitud) {
        disponibilidad.setProfesional(profesional);
        disponibilidad.setFechaInicio(solicitud.getFechaInicio());
        disponibilidad.setFechaFin(solicitud.getFechaFin());
    }

    /** Rechaza intervalos vacíos o donde el fin antecede al inicio. */
    private void validarRango(OffsetDateTime inicio, OffsetDateTime fin) {
        if (!fin.isAfter(inicio)) {
            throw new ConflictoException("La fecha de fin debe ser posterior a la fecha de inicio.");
        }
    }

    /** Evita que dos bloques del mismo profesional ocupen el mismo horario. */
    private void validarSolapamiento(Integer profesionalId, OffsetDateTime inicio,
            OffsetDateTime fin, Integer id) {
        if (disponibilidadRepository.existeSolapamiento(profesionalId, inicio, fin, id)) {
            throw new ConflictoException("El bloque se solapa con otra disponibilidad del profesional.");
        }
    }

    /** Obtiene el profesional relacionado o informa que no existe. */
    private ProfesionalEntity buscarProfesional(Integer id) {
        Optional<ProfesionalEntity> resultado = profesionalRepository.findById(id);
        if (resultado.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontro el profesional con id " + id + ".");
        }
        return resultado.get();
    }

    /** Obtiene la disponibilidad indicada o informa que no existe. */
    private DisponibilidadEntity buscar(Integer id) {
        Optional<DisponibilidadEntity> resultado = disponibilidadRepository.findById(id);
        if (resultado.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontro la disponibilidad con id " + id + ".");
        }
        return resultado.get();
    }
}
