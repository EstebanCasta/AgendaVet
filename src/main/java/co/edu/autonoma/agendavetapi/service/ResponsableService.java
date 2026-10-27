package co.edu.autonoma.agendavetapi.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.autonoma.agendavetapi.dto.ResponsableRequest;
import co.edu.autonoma.agendavetapi.dto.ResponsableResponse;
import co.edu.autonoma.agendavetapi.entities.ResponsableEntity;
import co.edu.autonoma.agendavetapi.exception.ConflictoException;
import co.edu.autonoma.agendavetapi.exception.RecursoNoEncontradoException;
import co.edu.autonoma.agendavetapi.repository.ResponsableRepository;

@Service
@Transactional
public class ResponsableService {
    private final ResponsableRepository responsableRepository;
    public ResponsableService(ResponsableRepository responsableRepository) { this.responsableRepository = responsableRepository; }

    @Transactional(readOnly = true)
    public List<ResponsableResponse> listar() {
        return responsableRepository.findAll().stream().map(ResponsableResponse::desde).toList();
    }
    @Transactional(readOnly = true)
    public ResponsableResponse obtener(Integer id) { return ResponsableResponse.desde(buscar(id)); }

    public ResponsableResponse crear(ResponsableRequest solicitud) {
        if (responsableRepository.existsByTipoDocumentoAndNumeroDocumento(
                solicitud.tipoDocumento(), solicitud.numeroDocumento()))
            throw new ConflictoException("Ya existe un responsable con ese tipo y número de documento.");
        ResponsableEntity responsable = new ResponsableEntity();
        aplicar(responsable, solicitud);
        return ResponsableResponse.desde(responsableRepository.saveAndFlush(responsable));
    }

    public ResponsableResponse reemplazar(Integer id, ResponsableRequest solicitud) {
        ResponsableEntity responsable = buscar(id);
        if (responsableRepository.existsByTipoDocumentoAndNumeroDocumentoAndIdNot(
                solicitud.tipoDocumento(), solicitud.numeroDocumento(), id))
            throw new ConflictoException("Ya existe otro responsable con ese tipo y número de documento.");
        aplicar(responsable, solicitud);
        return ResponsableResponse.desde(responsableRepository.saveAndFlush(responsable));
    }

    public void eliminar(Integer id) {
        responsableRepository.delete(buscar(id));
        responsableRepository.flush();
    }
    private void aplicar(ResponsableEntity responsable, ResponsableRequest solicitud) {
        responsable.setNombre(solicitud.nombre());
        responsable.setApellido(solicitud.apellido());
        responsable.setTipoDocumento(solicitud.tipoDocumento());
        responsable.setNumeroDocumento(solicitud.numeroDocumento());
        responsable.setTelefono(solicitud.telefono());
        responsable.setCorreo(solicitud.correo());
    }
    private ResponsableEntity buscar(Integer id) {
        return responsableRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("No se encontró el responsable con id " + id + "."));
    }
}
