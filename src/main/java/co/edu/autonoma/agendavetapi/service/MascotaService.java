package co.edu.autonoma.agendavetapi.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.autonoma.agendavetapi.dto.MascotaRequest;
import co.edu.autonoma.agendavetapi.dto.MascotaResponse;
import co.edu.autonoma.agendavetapi.entities.MascotaEntity;
import co.edu.autonoma.agendavetapi.entities.ResponsableEntity;
import co.edu.autonoma.agendavetapi.exception.RecursoNoEncontradoException;
import co.edu.autonoma.agendavetapi.repository.MascotaRepository;
import co.edu.autonoma.agendavetapi.repository.ResponsableRepository;

@Service
@Transactional
public class MascotaService {
    private final MascotaRepository mascotaRepository;
    private final ResponsableRepository responsableRepository;

    public MascotaService(MascotaRepository mascotaRepository, ResponsableRepository responsableRepository) {
        this.mascotaRepository = mascotaRepository;
        this.responsableRepository = responsableRepository;
    }

    @Transactional(readOnly = true)
    public List<MascotaResponse> listar(Integer responsableId) {
        List<MascotaEntity> mascotas = responsableId == null
                ? mascotaRepository.findAll() : mascotaRepository.findByResponsable_Id(responsableId);
        return mascotas.stream().map(MascotaResponse::desde).toList();
    }

    @Transactional(readOnly = true)
    public MascotaResponse obtener(Integer id) { return MascotaResponse.desde(buscar(id)); }

    public MascotaResponse crear(MascotaRequest solicitud) {
        MascotaEntity mascota = new MascotaEntity();
        aplicar(mascota, solicitud);
        mascota.setActiva(solicitud.activa() == null || solicitud.activa());
        return MascotaResponse.desde(mascotaRepository.saveAndFlush(mascota));
    }

    public MascotaResponse actualizar(Integer id, MascotaRequest solicitud) {
        MascotaEntity mascota = buscar(id);
        aplicar(mascota, solicitud);
        if (solicitud.activa() != null) mascota.setActiva(solicitud.activa());
        return MascotaResponse.desde(mascotaRepository.saveAndFlush(mascota));
    }

    public void eliminar(Integer id) {
        mascotaRepository.delete(buscar(id));
        mascotaRepository.flush();
    }

    private void aplicar(MascotaEntity mascota, MascotaRequest solicitud) {
        ResponsableEntity responsable = responsableRepository.findById(solicitud.responsableId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el responsable con id " + solicitud.responsableId() + "."));
        mascota.setResponsable(responsable);
        mascota.setNombre(solicitud.nombre());
        mascota.setEspecie(solicitud.especie());
        mascota.setRaza(solicitud.raza());
        mascota.setSexo(solicitud.sexo());
        mascota.setFechaNacimiento(solicitud.fechaNacimiento());
    }

    private MascotaEntity buscar(Integer id) {
        return mascotaRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("No se encontró la mascota con id " + id + "."));
    }
}
