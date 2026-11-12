package co.edu.autonoma.agendavetapi.service;

import org.springframework.stereotype.Service;

import co.edu.autonoma.agendavetapi.dto.EstadoResponse;

/** Prepara la respuesta sencilla de disponibilidad del backend. */
@Service
public class EstadoService {

    /** Devuelve el nombre de la aplicación y su estado básico. */
    public EstadoResponse consultarEstado() {
        return new EstadoResponse(
                "agenda-vet-api",
                "disponible"
        );
    }
}
