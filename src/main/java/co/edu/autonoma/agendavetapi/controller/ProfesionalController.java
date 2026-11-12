package co.edu.autonoma.agendavetapi.controller;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.autonoma.agendavetapi.dto.ProfesionalRequest;
import co.edu.autonoma.agendavetapi.dto.ProfesionalResponse;
import co.edu.autonoma.agendavetapi.service.ProfesionalService;

/** Recibe las solicitudes HTTP para administrar profesionales veterinarios. */
@RestController
@RequestMapping("/api/profesionales")
public class ProfesionalController {
    private final ProfesionalService profesionalService;

    public ProfesionalController(ProfesionalService profesionalService) {
        this.profesionalService = profesionalService;
    }

    /** Crea un profesional y asocia los servicios indicados en el request. */
    @PostMapping
    public ResponseEntity<ProfesionalResponse> crear(@Valid @RequestBody ProfesionalRequest solicitud) {
        ProfesionalResponse respuesta = profesionalService.crear(solicitud);
        return ResponseEntity.created(URI.create("/api/profesionales/" + respuesta.getId())).body(respuesta);
    }
    /** Lista profesionales y, si se indica, filtra por estado activo. */
    @GetMapping
    public List<ProfesionalResponse> listar(@RequestParam(required = false) Boolean activo) {
        return profesionalService.listar(activo);
    }
    /** Consulta un profesional por identificador. */
    @GetMapping("/{id}")
    public ProfesionalResponse obtener(@PathVariable Integer id) { return profesionalService.obtener(id); }
    /** Reemplaza los datos del profesional y su lista de servicios. */
    @PutMapping("/{id}")
    public ProfesionalResponse actualizar(@PathVariable Integer id,
            @Valid @RequestBody ProfesionalRequest solicitud) {
        return profesionalService.actualizar(id, solicitud);
    }
    /** Elimina un profesional si no hay relaciones que lo impidan. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        profesionalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
