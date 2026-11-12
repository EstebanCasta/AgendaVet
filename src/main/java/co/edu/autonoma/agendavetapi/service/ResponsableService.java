package co.edu.autonoma.agendavetapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.autonoma.agendavetapi.dto.ResponsableRequest;
import co.edu.autonoma.agendavetapi.dto.ResponsableResponse;
import co.edu.autonoma.agendavetapi.entities.ResponsableEntity;
import co.edu.autonoma.agendavetapi.exception.ConflictoException;
import co.edu.autonoma.agendavetapi.exception.RecursoNoEncontradoException;
import co.edu.autonoma.agendavetapi.repository.ResponsableRepository;

/** Administra responsables y mantiene únicos sus datos de identificación. */
@Service
@Transactional
public class ResponsableService {

    private final ResponsableRepository responsableRepository;

    public ResponsableService(ResponsableRepository responsableRepository) {
        this.responsableRepository = responsableRepository;
    }

    /** Lista los responsables registrados y los convierte a respuestas. */
    @Transactional(readOnly = true)
    public List<ResponsableResponse> listar() {
        List<ResponsableEntity> responsables = responsableRepository.findAll();
        List<ResponsableResponse> respuestas = new ArrayList<>();
        for (ResponsableEntity responsable : responsables) {
            respuestas.add(ResponsableResponse.desde(responsable));
        }
        return respuestas;
    }

    /** Busca un responsable por ID. */
    @Transactional(readOnly = true)
    public ResponsableResponse obtener(Integer id) {
        return ResponsableResponse.desde(buscar(id));
    }

    /** Crea un responsable, evitando duplicar tipo y número de documento. */
    public ResponsableResponse crear(ResponsableRequest solicitud) {
        boolean existe = responsableRepository.existsByTipoDocumentoAndNumeroDocumento(
                solicitud.getTipoDocumento(), solicitud.getNumeroDocumento());
        if (existe) {
            throw new ConflictoException("Ya existe un responsable con ese tipo y numero de documento.");
        }

        ResponsableEntity responsable = new ResponsableEntity();
        aplicar(responsable, solicitud);
        responsable.setFechaRegistro(OffsetDateTime.now());
        return ResponsableResponse.desde(responsableRepository.saveAndFlush(responsable));
    }

    /** Reemplaza los datos y valida que el documento no pertenezca a otra persona. */
    public ResponsableResponse reemplazar(Integer id, ResponsableRequest solicitud) {
        ResponsableEntity responsable = buscar(id);
        boolean existeOtro = responsableRepository.existsByTipoDocumentoAndNumeroDocumentoAndIdNot(
                solicitud.getTipoDocumento(), solicitud.getNumeroDocumento(), id);
        if (existeOtro) {
            throw new ConflictoException("Ya existe otro responsable con ese tipo y numero de documento.");
        }

        aplicar(responsable, solicitud);
        return ResponsableResponse.desde(responsableRepository.saveAndFlush(responsable));
    }

    /** Elimina el responsable si no tiene dependencias que lo referencien. */
    public void eliminar(Integer id) {
        responsableRepository.delete(buscar(id));
        responsableRepository.flush();
    }

    /** Copia al entity los campos recibidos en el request. */
    private void aplicar(ResponsableEntity responsable, ResponsableRequest solicitud) {
        responsable.setNombre(solicitud.getNombre());
        responsable.setApellido(solicitud.getApellido());
        responsable.setTipoDocumento(solicitud.getTipoDocumento());
        responsable.setNumeroDocumento(solicitud.getNumeroDocumento());
        responsable.setTelefono(solicitud.getTelefono());
        responsable.setCorreo(solicitud.getCorreo());
    }

    /** Busca un responsable o genera el error de recurso no encontrado. */
    private ResponsableEntity buscar(Integer id) {
        Optional<ResponsableEntity> resultado = responsableRepository.findById(id);
        if (resultado.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontro el responsable con id " + id + ".");
        }
        return resultado.get();
    }
}
