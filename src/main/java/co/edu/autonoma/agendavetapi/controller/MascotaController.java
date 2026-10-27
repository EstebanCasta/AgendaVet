package co.edu.autonoma.agendavetapi.controller;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.autonoma.agendavetapi.dto.MascotaRequest;
import co.edu.autonoma.agendavetapi.dto.MascotaResponse;
import co.edu.autonoma.agendavetapi.service.MascotaService;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {
    private final MascotaService mascotaService;
    public MascotaController(MascotaService mascotaService) { this.mascotaService = mascotaService; }

    @PostMapping
    public ResponseEntity<MascotaResponse> crear(@Valid @RequestBody MascotaRequest solicitud) {
        MascotaResponse respuesta = mascotaService.crear(solicitud);
        return ResponseEntity.created(URI.create("/api/mascotas/" + respuesta.id())).body(respuesta);
    }
    @GetMapping
    public List<MascotaResponse> listar(@RequestParam(required = false) Integer responsableId) {
        return mascotaService.listar(responsableId);
    }
    @GetMapping("/{id}")
    public MascotaResponse obtener(@PathVariable Integer id) { return mascotaService.obtener(id); }
    @PutMapping("/{id}")
    public MascotaResponse actualizar(@PathVariable Integer id, @Valid @RequestBody MascotaRequest solicitud) {
        return mascotaService.actualizar(id, solicitud);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
