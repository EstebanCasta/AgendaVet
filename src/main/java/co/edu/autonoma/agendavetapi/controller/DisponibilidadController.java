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

/** Expone las operaciones HTTP para crear y consultar horarios disponibles. */
@RestController
@RequestMapping("/api/disponibilidades")
public class DisponibilidadController {
    private final DisponibilidadService disponibilidadService;

    public DisponibilidadController(DisponibilidadService disponibilidadService) {
        this.disponibilidadService = disponibilidadService;
    }

    /** Crea un bloque de disponibilidad para un profesional. */
    @PostMapping
    public ResponseEntity<DisponibilidadResponse> crear(@Valid @RequestBody DisponibilidadRequest solicitud) {
        DisponibilidadResponse respuesta = disponibilidadService.crear(solicitud);
        return ResponseEntity.created(URI.create("/api/disponibilidades/" + respuesta.getId())).body(respuesta);
    }
    /** Lista bloques y permite filtrar por profesional y rango de fechas. */
    @GetMapping
    public List<DisponibilidadResponse> listar(@RequestParam(required = false) Integer profesionalId,
            @RequestParam(required = false) OffsetDateTime desde,
            @RequestParam(required = false) OffsetDateTime hasta) {
        return disponibilidadService.listar(profesionalId, desde, hasta);
    }
    /** Busca un bloque por su identificador. */
    @GetMapping("/{id}")
    public DisponibilidadResponse obtener(@PathVariable Integer id) { return disponibilidadService.obtener(id); }
    /** Reemplaza los datos de un bloque de disponibilidad. */
    @PutMapping("/{id}")
    public DisponibilidadResponse actualizar(@PathVariable Integer id,
            @Valid @RequestBody DisponibilidadRequest solicitud) {
        return disponibilidadService.actualizar(id, solicitud);
    }
    /** Elimina un bloque y responde sin contenido si la operación funciona. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        disponibilidadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
