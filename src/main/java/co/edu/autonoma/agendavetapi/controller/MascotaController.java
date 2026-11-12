package co.edu.autonoma.agendavetapi.controller;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.autonoma.agendavetapi.dto.MascotaRequest;
import co.edu.autonoma.agendavetapi.dto.MascotaResponse;
import co.edu.autonoma.agendavetapi.service.MascotaService;

/** Recibe las solicitudes HTTP para administrar mascotas. */
@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {
    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    /** Registra una mascota vinculada a un responsable existente. */
    @PostMapping
    public ResponseEntity<MascotaResponse> crear(@Valid @RequestBody MascotaRequest solicitud) {
        MascotaResponse respuesta = mascotaService.crear(solicitud);
        return ResponseEntity.created(URI.create("/api/mascotas/" + respuesta.getId())).body(respuesta);
    }
    /** Lista mascotas o filtra las de un responsable. */
    @GetMapping
    public List<MascotaResponse> listar(@RequestParam(required = false) Integer responsableId) {
        return mascotaService.listar(responsableId);
    }
    /** Consulta una mascota por identificador. */
    @GetMapping("/{id}")
    public MascotaResponse obtener(@PathVariable Integer id) { return mascotaService.obtener(id); }
    /** Reemplaza los datos de una mascota. */
    @PutMapping("/{id}")
    public MascotaResponse actualizar(@PathVariable Integer id, @Valid @RequestBody MascotaRequest solicitud) {
        return mascotaService.actualizar(id, solicitud);
    }
    /** Elimina una mascota si no hay relaciones que lo impidan. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
