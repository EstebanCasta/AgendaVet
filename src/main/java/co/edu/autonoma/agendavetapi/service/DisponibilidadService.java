package co.edu.autonoma.agendavetapi.service;

import java.time.OffsetDateTime;
import java.util.List;
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

    @Transactional(readOnly = true)
    public List<DisponibilidadResponse> listar(Integer profesionalId, OffsetDateTime desde, OffsetDateTime hasta) {
        if (desde != null && hasta != null && desde.isAfter(hasta)) {
            throw new ConflictoException("La fecha desde no puede ser posterior a la fecha hasta.");
        }
        return disponibilidadRepository.buscarConFiltros(profesionalId, desde, hasta)
                .stream().map(DisponibilidadResponse::desde).toList();
    }

    @Transactional(readOnly = true)
    public DisponibilidadResponse obtener(Integer id) { return DisponibilidadResponse.desde(buscar(id)); }

    public DisponibilidadResponse crear(DisponibilidadRequest solicitud) {
        validarRango(solicitud.fechaInicio(), solicitud.fechaFin());
        ProfesionalEntity profesional = buscarProfesional(solicitud.profesionalId());
        validarSolapamiento(profesional.getId(), solicitud.fechaInicio(), solicitud.fechaFin(), null);
        DisponibilidadEntity disponibilidad = new DisponibilidadEntity();
        aplicar(disponibilidad, profesional, solicitud);
        return DisponibilidadResponse.desde(disponibilidadRepository.saveAndFlush(disponibilidad));
    }

    public DisponibilidadResponse actualizar(Integer id, DisponibilidadRequest solicitud) {
        validarRango(solicitud.fechaInicio(), solicitud.fechaFin());
        DisponibilidadEntity disponibilidad = buscar(id);
        ProfesionalEntity profesional = buscarProfesional(solicitud.profesionalId());
        validarSolapamiento(profesional.getId(), solicitud.fechaInicio(), solicitud.fechaFin(), id);
        aplicar(disponibilidad, profesional, solicitud);
        return DisponibilidadResponse.desde(disponibilidadRepository.saveAndFlush(disponibilidad));
    }

    public void eliminar(Integer id) {
        disponibilidadRepository.delete(buscar(id));
        disponibilidadRepository.flush();
    }

    private void aplicar(DisponibilidadEntity disponibilidad, ProfesionalEntity profesional,
            DisponibilidadRequest solicitud) {
        disponibilidad.setProfesional(profesional);
        disponibilidad.setFechaInicio(solicitud.fechaInicio());
        disponibilidad.setFechaFin(solicitud.fechaFin());
    }

    private void validarRango(OffsetDateTime inicio, OffsetDateTime fin) {
        if (!fin.isAfter(inicio)) {
            throw new ConflictoException("La fecha de fin debe ser posterior a la fecha de inicio.");
        }
    }

    private void validarSolapamiento(Integer profesionalId, OffsetDateTime inicio, OffsetDateTime fin, Integer id) {
        if (disponibilidadRepository.existeSolapamiento(profesionalId, inicio, fin, id)) {
            throw new ConflictoException("El bloque se solapa con otra disponibilidad del profesional.");
        }
    }

    private ProfesionalEntity buscarProfesional(Integer id) {
        return profesionalRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("No se encontró el profesional con id " + id + "."));
    }

    private DisponibilidadEntity buscar(Integer id) {
        return disponibilidadRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("No se encontró la disponibilidad con id " + id + "."));
    }
}
