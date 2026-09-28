package co.edu.autonoma.agendavetapi.service;

import org.springframework.stereotype.Service;

import co.edu.autonoma.agendavetapi.dto.EstadoResponse;

@Service
public class EstadoService {

    public EstadoResponse consultarEstado() {
        return new EstadoResponse(
                "agenda-vet-api",
                "disponible"
        );
    }
}