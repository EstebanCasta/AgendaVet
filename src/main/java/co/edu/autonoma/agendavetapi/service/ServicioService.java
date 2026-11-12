package co.edu.autonoma.agendavetapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.autonoma.agendavetapi.dto.ServicioRequest;
import co.edu.autonoma.agendavetapi.dto.ServicioResponse;
import co.edu.autonoma.agendavetapi.entities.ServicioEntity;
import co.edu.autonoma.agendavetapi.exception.ConflictoException;
import co.edu.autonoma.agendavetapi.exception.RecursoNoEncontradoException;
import co.edu.autonoma.agendavetapi.repository.ServicioRepository;

/** Administra el catálogo de servicios y evita nombres repetidos. */
@Service
@Transactional
public class ServicioService {
    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    /** Lista todos los servicios. */
    @Transactional(readOnly = true)
    public List<ServicioResponse> listar() {
        List<ServicioEntity> servicios = servicioRepository.findAll();
        List<ServicioResponse> respuestas = new ArrayList<>();
        for (ServicioEntity servicio : servicios) {
            respuestas.add(ServicioResponse.desde(servicio));
        }
        return respuestas;
    }
    /** Consulta un servicio por ID. */
    @Transactional(readOnly = true)
    public ServicioResponse obtener(Integer id) { return ServicioResponse.desde(buscar(id)); }

    /** Crea un servicio con nombre único y duración positiva validada en el DTO. */
    public ServicioResponse crear(ServicioRequest solicitud) {
        if (servicioRepository.existsByNombre(solicitud.getNombre())) {
            throw new ConflictoException("Ya existe un servicio con ese nombre.");
        }
        ServicioEntity servicio = new ServicioEntity();
        servicio.setNombre(solicitud.getNombre());
        servicio.setDescripcion(solicitud.getDescripcion());
        servicio.setDuracionMinutos(solicitud.getDuracionMinutos());
        servicio.setActivo(solicitud.getActivo() == null || solicitud.getActivo());
        return ServicioResponse.desde(servicioRepository.saveAndFlush(servicio));
    }

    /** Reemplaza los datos del servicio sin permitir duplicar su nombre. */
    public ServicioResponse reemplazar(Integer id, ServicioRequest solicitud) {
        ServicioEntity servicio = buscar(id);
        if (servicioRepository.existsByNombreAndIdNot(solicitud.getNombre(), id)) {
            throw new ConflictoException("Ya existe otro servicio con ese nombre.");
        }
        servicio.setNombre(solicitud.getNombre());
        servicio.setDescripcion(solicitud.getDescripcion());
        servicio.setDuracionMinutos(solicitud.getDuracionMinutos());
        if (solicitud.getActivo() != null) {
            servicio.setActivo(solicitud.getActivo());
        }
        return ServicioResponse.desde(servicioRepository.saveAndFlush(servicio));
    }

    /** Elimina el servicio si no hay profesionales o citas que lo referencien. */
    public void eliminar(Integer id) {
        servicioRepository.delete(buscar(id));
        servicioRepository.flush();
    }
    /** Busca un servicio o genera el error de recurso no encontrado. */
    private ServicioEntity buscar(Integer id) {
        Optional<ServicioEntity> resultado = servicioRepository.findById(id);
        if (resultado.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontró el servicio con id " + id + ".");
        }
        return resultado.get();
    }
}
