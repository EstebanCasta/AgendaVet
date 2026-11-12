package co.edu.autonoma.agendavetapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.autonoma.agendavetapi.dto.MascotaRequest;
import co.edu.autonoma.agendavetapi.dto.MascotaResponse;
import co.edu.autonoma.agendavetapi.entities.MascotaEntity;
import co.edu.autonoma.agendavetapi.entities.ResponsableEntity;
import co.edu.autonoma.agendavetapi.exception.RecursoNoEncontradoException;
import co.edu.autonoma.agendavetapi.repository.MascotaRepository;
import co.edu.autonoma.agendavetapi.repository.ResponsableRepository;

/** Administra mascotas y comprueba que cada una tenga un responsable existente. */
@Service
@Transactional
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final ResponsableRepository responsableRepository;

    public MascotaService(MascotaRepository mascotaRepository,
            ResponsableRepository responsableRepository) {
        this.mascotaRepository = mascotaRepository;
        this.responsableRepository = responsableRepository;
    }

    /** Lista las mascotas, con filtro opcional por responsable. */
    @Transactional(readOnly = true)
    public List<MascotaResponse> listar(Integer responsableId) {
        List<MascotaEntity> mascotas;
        if (responsableId == null) {
            mascotas = mascotaRepository.findAll();
        } else {
            mascotas = mascotaRepository.findByResponsable_Id(responsableId);
        }

        List<MascotaResponse> respuestas = new ArrayList<>();
        for (MascotaEntity mascota : mascotas) {
            respuestas.add(MascotaResponse.desde(mascota));
        }
        return respuestas;
    }

    /** Busca una mascota por ID y convierte el resultado a DTO. */
    @Transactional(readOnly = true)
    public MascotaResponse obtener(Integer id) {
        return MascotaResponse.desde(buscar(id));
    }

    /** Crea una mascota y usa activa=true cuando el request no indica el valor. */
    public MascotaResponse crear(MascotaRequest solicitud) {
        MascotaEntity mascota = new MascotaEntity();
        aplicar(mascota, solicitud);
        mascota.setActiva(solicitud.getActiva() == null || solicitud.getActiva());
        return MascotaResponse.desde(mascotaRepository.saveAndFlush(mascota));
    }

    /** Reemplaza los datos enviados y conserva el valor activo si se omite. */
    public MascotaResponse actualizar(Integer id, MascotaRequest solicitud) {
        MascotaEntity mascota = buscar(id);
        aplicar(mascota, solicitud);
        if (solicitud.getActiva() != null) {
            mascota.setActiva(solicitud.getActiva());
        }
        return MascotaResponse.desde(mascotaRepository.saveAndFlush(mascota));
    }

    /** Elimina una mascota; las claves foráneas pueden impedirlo si tiene citas. */
    public void eliminar(Integer id) {
        mascotaRepository.delete(buscar(id));
        mascotaRepository.flush();
    }

    /** Busca al responsable y copia los campos editables del request. */
    private void aplicar(MascotaEntity mascota, MascotaRequest solicitud) {
        Optional<ResponsableEntity> resultado = responsableRepository.findById(solicitud.getResponsableId());
        if (resultado.isEmpty()) {
            throw new RecursoNoEncontradoException(
                    "No se encontro el responsable con id " + solicitud.getResponsableId() + ".");
        }

        mascota.setResponsable(resultado.get());
        mascota.setNombre(solicitud.getNombre());
        mascota.setEspecie(solicitud.getEspecie());
        mascota.setRaza(solicitud.getRaza());
        mascota.setSexo(solicitud.getSexo());
        mascota.setFechaNacimiento(solicitud.getFechaNacimiento());
    }

    /** Busca una mascota o genera el error de recurso no encontrado. */
    private MascotaEntity buscar(Integer id) {
        Optional<MascotaEntity> resultado = mascotaRepository.findById(id);
        if (resultado.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontro la mascota con id " + id + ".");
        }
        return resultado.get();
    }
}
