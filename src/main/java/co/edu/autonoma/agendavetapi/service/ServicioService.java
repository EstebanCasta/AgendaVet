package co.edu.autonoma.agendavetapi.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.autonoma.agendavetapi.dto.ServicioRequest;
import co.edu.autonoma.agendavetapi.dto.ServicioResponse;
import co.edu.autonoma.agendavetapi.entities.ServicioEntity;
import co.edu.autonoma.agendavetapi.exception.ConflictoException;
import co.edu.autonoma.agendavetapi.exception.RecursoNoEncontradoException;
import co.edu.autonoma.agendavetapi.repository.ServicioRepository;

@Service
@Transactional
public class ServicioService {
    private final ServicioRepository servicioRepository;
    public ServicioService(ServicioRepository servicioRepository) { this.servicioRepository = servicioRepository; }

    @Transactional(readOnly = true)
    public List<ServicioResponse> listar() {
        return servicioRepository.findAll().stream().map(ServicioResponse::desde).toList();
    }
    @Transactional(readOnly = true)
    public ServicioResponse obtener(Integer id) { return ServicioResponse.desde(buscar(id)); }

    public ServicioResponse crear(ServicioRequest solicitud) {
        if (servicioRepository.existsByNombre(solicitud.nombre()))
            throw new ConflictoException("Ya existe un servicio con ese nombre.");
        ServicioEntity servicio = new ServicioEntity();
        servicio.setNombre(solicitud.nombre());
        servicio.setDescripcion(solicitud.descripcion());
        servicio.setDuracionMinutos(solicitud.duracionMinutos());
        servicio.setActivo(solicitud.activo() == null || solicitud.activo());
        return ServicioResponse.desde(servicioRepository.saveAndFlush(servicio));
    }

    public ServicioResponse reemplazar(Integer id, ServicioRequest solicitud) {
        ServicioEntity servicio = buscar(id);
        if (servicioRepository.existsByNombreAndIdNot(solicitud.nombre(), id))
            throw new ConflictoException("Ya existe otro servicio con ese nombre.");
        servicio.setNombre(solicitud.nombre());
        servicio.setDescripcion(solicitud.descripcion());
        servicio.setDuracionMinutos(solicitud.duracionMinutos());
        if (solicitud.activo() != null) servicio.setActivo(solicitud.activo());
        return ServicioResponse.desde(servicioRepository.saveAndFlush(servicio));
    }

    public void eliminar(Integer id) {
        servicioRepository.delete(buscar(id));
        servicioRepository.flush();
    }
    private ServicioEntity buscar(Integer id) {
        return servicioRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("No se encontró el servicio con id " + id + "."));
    }
}
