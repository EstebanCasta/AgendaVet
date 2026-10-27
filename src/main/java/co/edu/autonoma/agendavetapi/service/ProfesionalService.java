package co.edu.autonoma.agendavetapi.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.autonoma.agendavetapi.dto.ProfesionalRequest;
import co.edu.autonoma.agendavetapi.dto.ProfesionalResponse;
import co.edu.autonoma.agendavetapi.entities.ProfesionalEntity;
import co.edu.autonoma.agendavetapi.entities.ServicioEntity;
import co.edu.autonoma.agendavetapi.exception.ConflictoException;
import co.edu.autonoma.agendavetapi.exception.RecursoNoEncontradoException;
import co.edu.autonoma.agendavetapi.repository.ProfesionalRepository;
import co.edu.autonoma.agendavetapi.repository.ServicioRepository;

@Service
@Transactional
public class ProfesionalService {
    private final ProfesionalRepository profesionalRepository;
    private final ServicioRepository servicioRepository;

    public ProfesionalService(ProfesionalRepository profesionalRepository, ServicioRepository servicioRepository) {
        this.profesionalRepository = profesionalRepository;
        this.servicioRepository = servicioRepository;
    }

    @Transactional(readOnly = true)
    public List<ProfesionalResponse> listar(Boolean activo) {
        List<ProfesionalEntity> profesionales = activo == null
                ? profesionalRepository.findAll() : profesionalRepository.findByActivo(activo);
        return profesionales.stream().map(ProfesionalResponse::desde).toList();
    }

    @Transactional(readOnly = true)
    public ProfesionalResponse obtener(Integer id) { return ProfesionalResponse.desde(buscar(id)); }

    public ProfesionalResponse crear(ProfesionalRequest solicitud) {
        if (profesionalRepository.existsByCorreo(solicitud.correo()))
            throw new ConflictoException("Ya existe un profesional con ese correo.");
        ProfesionalEntity profesional = new ProfesionalEntity();
        aplicar(profesional, solicitud);
        profesional.setActivo(solicitud.activo() == null || solicitud.activo());
        return ProfesionalResponse.desde(profesionalRepository.saveAndFlush(profesional));
    }

    public ProfesionalResponse actualizar(Integer id, ProfesionalRequest solicitud) {
        ProfesionalEntity profesional = buscar(id);
        if (profesionalRepository.existsByCorreoAndIdNot(solicitud.correo(), id))
            throw new ConflictoException("Ya existe otro profesional con ese correo.");
        aplicar(profesional, solicitud);
        if (solicitud.activo() != null) profesional.setActivo(solicitud.activo());
        return ProfesionalResponse.desde(profesionalRepository.saveAndFlush(profesional));
    }

    public void eliminar(Integer id) {
        profesionalRepository.delete(buscar(id));
        profesionalRepository.flush();
    }

    private void aplicar(ProfesionalEntity profesional, ProfesionalRequest solicitud) {
        profesional.setNombre(solicitud.nombre());
        profesional.setApellido(solicitud.apellido());
        profesional.setEspecialidad(solicitud.especialidad());
        profesional.setCorreo(solicitud.correo());
        profesional.setServicios(cargarServicios(solicitud.serviciosIds()));
    }

    private Set<ServicioEntity> cargarServicios(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return new HashSet<>();
        Set<Integer> idsUnicos = new HashSet<>(ids);
        List<ServicioEntity> servicios = servicioRepository.findAllById(idsUnicos);
        if (servicios.size() != idsUnicos.size()) {
            Set<Integer> encontrados = servicios.stream().map(ServicioEntity::getId)
                    .collect(java.util.stream.Collectors.toSet());
            idsUnicos.removeAll(encontrados);
            throw new RecursoNoEncontradoException("No se encontraron los servicios con ids " + idsUnicos + ".");
        }
        return new HashSet<>(servicios);
    }

    private ProfesionalEntity buscar(Integer id) {
        return profesionalRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("No se encontró el profesional con id " + id + "."));
    }
}
