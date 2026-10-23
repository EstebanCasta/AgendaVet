package co.edu.autonoma.agendavetapi.controller;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.autonoma.agendavetapi.dto.CambioEstadoCitaRequest;
import co.edu.autonoma.agendavetapi.dto.CitaRequest;
import co.edu.autonoma.agendavetapi.dto.CitaResponse;
import co.edu.autonoma.agendavetapi.dto.HistorialEstadoCitaResponse;
import co.edu.autonoma.agendavetapi.entities.EstadoCita;
import co.edu.autonoma.agendavetapi.service.CitaService;

@RestController
@RequestMapping("/api/citas")
public class CitaController {
    private final CitaService citaService;
    public CitaController(CitaService citaService) { this.citaService = citaService; }

    @PostMapping
    public ResponseEntity<CitaResponse> crear(@Valid @RequestBody CitaRequest solicitud) {
        CitaResponse respuesta = citaService.crear(solicitud);
        return ResponseEntity.created(URI.create("/api/citas/" + respuesta.id())).body(respuesta);
    }
    @GetMapping
    public List<CitaResponse> listar(@RequestParam(required = false) EstadoCita estado,
            @RequestParam(required = false) Integer mascotaId,
            @RequestParam(required = false) Integer profesionalId,
            @RequestParam(required = false) OffsetDateTime desde,
            @RequestParam(required = false) OffsetDateTime hasta) {
        return citaService.listar(estado, mascotaId, profesionalId, desde, hasta);
    }
    @GetMapping("/{id}")
    public CitaResponse obtener(@PathVariable Integer id) { return citaService.obtener(id); }
    @PutMapping("/{id}")
    public CitaResponse reprogramar(@PathVariable Integer id, @Valid @RequestBody CitaRequest solicitud) {
        return citaService.reprogramar(id, solicitud);
    }
    @PatchMapping("/{id}/estado")
    public CitaResponse cambiarEstado(@PathVariable Integer id,
            @Valid @RequestBody CambioEstadoCitaRequest solicitud) {
        return citaService.cambiarEstado(id, solicitud);
    }
    @GetMapping("/{id}/historial-estados")
    public List<HistorialEstadoCitaResponse> historial(@PathVariable Integer id) {
        return citaService.listarHistorial(id);
    }
}
