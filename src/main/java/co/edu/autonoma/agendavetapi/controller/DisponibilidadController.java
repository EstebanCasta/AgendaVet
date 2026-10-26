package co.edu.autonoma.agendavetapi.controller;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.autonoma.agendavetapi.dto.DisponibilidadRequest;
import co.edu.autonoma.agendavetapi.dto.DisponibilidadResponse;
import co.edu.autonoma.agendavetapi.service.DisponibilidadService;

@RestController
@RequestMapping("/api/disponibilidades")
public class DisponibilidadController {
    private final DisponibilidadService disponibilidadService;
    public DisponibilidadController(DisponibilidadService disponibilidadService) {
        this.disponibilidadService = disponibilidadService;
    }

    @PostMapping
    public ResponseEntity<DisponibilidadResponse> crear(@Valid @RequestBody DisponibilidadRequest solicitud) {
        DisponibilidadResponse respuesta = disponibilidadService.crear(solicitud);
        return ResponseEntity.created(URI.create("/api/disponibilidades/" + respuesta.id())).body(respuesta);
    }
    @GetMapping
    public List<DisponibilidadResponse> listar(@RequestParam(required = false) Integer profesionalId,
            @RequestParam(required = false) OffsetDateTime desde,
            @RequestParam(required = false) OffsetDateTime hasta) {
        return disponibilidadService.listar(profesionalId, desde, hasta);
    }
    @GetMapping("/{id}")
    public DisponibilidadResponse obtener(@PathVariable Integer id) { return disponibilidadService.obtener(id); }
    @PutMapping("/{id}")
    public DisponibilidadResponse actualizar(@PathVariable Integer id,
            @Valid @RequestBody DisponibilidadRequest solicitud) {
        return disponibilidadService.actualizar(id, solicitud);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        disponibilidadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
