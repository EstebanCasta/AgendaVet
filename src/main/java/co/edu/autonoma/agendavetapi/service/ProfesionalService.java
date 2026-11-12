package co.edu.autonoma.agendavetapi.service;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

/** Administra profesionales y su relación con los servicios que ofrecen. */
@Service
@Transactional
public class ProfesionalService {
    private final ProfesionalRepository profesionalRepository;
    private final ServicioRepository servicioRepository;

    public ProfesionalService(ProfesionalRepository profesionalRepository,
            ServicioRepository servicioRepository) {
        this.profesionalRepository = profesionalRepository;
        this.servicioRepository = servicioRepository;
    }

    /** Lista profesionales y permite filtrar por activo/inactivo. */
    @Transactional(readOnly = true)
    public List<ProfesionalResponse> listar(Boolean activo) {
        List<ProfesionalEntity> profesionales = activo == null
                ? profesionalRepository.findAll() : profesionalRepository.findByActivo(activo);
        List<ProfesionalResponse> respuestas = new ArrayList<>();
        for (ProfesionalEntity profesional : profesionales) {
            respuestas.add(ProfesionalResponse.desde(profesional));
        }
        return respuestas;
    }

    /** Consulta un profesional por ID. */
    @Transactional(readOnly = true)
    public ProfesionalResponse obtener(Integer id) { return ProfesionalResponse.desde(buscar(id)); }

    /** Crea un profesional y verifica que su correo no esté duplicado. */
    public ProfesionalResponse crear(ProfesionalRequest solicitud) {
        if (profesionalRepository.existsByCorreo(solicitud.getCorreo()))
            throw new ConflictoException("Ya existe un profesional con ese correo.");
        ProfesionalEntity profesional = new ProfesionalEntity();
        aplicar(profesional, solicitud);
        profesional.setActivo(solicitud.getActivo() == null || solicitud.getActivo());
        return ProfesionalResponse.desde(profesionalRepository.saveAndFlush(profesional));
    }

    /** Reemplaza los datos y asociaciones del profesional, validando su correo. */
    public ProfesionalResponse actualizar(Integer id, ProfesionalRequest solicitud) {
        ProfesionalEntity profesional = buscar(id);
        if (profesionalRepository.existsByCorreoAndIdNot(solicitud.getCorreo(), id))
            throw new ConflictoException("Ya existe otro profesional con ese correo.");
        aplicar(profesional, solicitud);
        if (solicitud.getActivo() != null) profesional.setActivo(solicitud.getActivo());
        return ProfesionalResponse.desde(profesionalRepository.saveAndFlush(profesional));
    }

    /** Elimina el profesional si la base no encuentra registros dependientes. */
    public void eliminar(Integer id) {
        profesionalRepository.delete(buscar(id));
        profesionalRepository.flush();
    }

    /** Copia datos simples y carga las asociaciones válidas de servicios. */
    private void aplicar(ProfesionalEntity profesional, ProfesionalRequest solicitud) {
        profesional.setNombre(solicitud.getNombre());
        profesional.setApellido(solicitud.getApellido());
        profesional.setEspecialidad(solicitud.getEspecialidad());
        profesional.setCorreo(solicitud.getCorreo());
        profesional.setServicios(cargarServicios(solicitud.getServiciosIds()));
    }

    /** Convierte IDs de servicio en entidades y detecta IDs que no existen. */
    private Set<ServicioEntity> cargarServicios(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return new HashSet<>();
        Set<Integer> idsUnicos = new HashSet<>(ids);
        List<ServicioEntity> servicios = servicioRepository.findAllById(idsUnicos);
        if (servicios.size() != idsUnicos.size()) {
            Set<Integer> encontrados = new HashSet<>();
            for (ServicioEntity servicio : servicios) {
                encontrados.add(servicio.getId());
            }
            idsUnicos.removeAll(encontrados);
            throw new RecursoNoEncontradoException("No se encontraron los servicios con ids " + idsUnicos + ".");
        }
        return new HashSet<>(servicios);
    }

    /** Busca un profesional o genera el error de recurso no encontrado. */
    private ProfesionalEntity buscar(Integer id) {
        Optional<ProfesionalEntity> resultado = profesionalRepository.findById(id);
        if (resultado.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontró el profesional con id " + id + ".");
        }
        return resultado.get();
    }
}
