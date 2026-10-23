package co.edu.autonoma.agendavetapi.controller;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.autonoma.agendavetapi.dto.ProfesionalRequest;
import co.edu.autonoma.agendavetapi.dto.ProfesionalResponse;
import co.edu.autonoma.agendavetapi.service.ProfesionalService;

@RestController
@RequestMapping("/api/profesionales")
public class ProfesionalController {
    private final ProfesionalService profesionalService;
    public ProfesionalController(ProfesionalService profesionalService) { this.profesionalService = profesionalService; }

    @PostMapping
    public ResponseEntity<ProfesionalResponse> crear(@Valid @RequestBody ProfesionalRequest solicitud) {
        ProfesionalResponse respuesta = profesionalService.crear(solicitud);
        return ResponseEntity.created(URI.create("/api/profesionales/" + respuesta.id())).body(respuesta);
    }
    @GetMapping
    public List<ProfesionalResponse> listar(@RequestParam(required = false) Boolean activo) {
        return profesionalService.listar(activo);
    }
    @GetMapping("/{id}")
    public ProfesionalResponse obtener(@PathVariable Integer id) { return profesionalService.obtener(id); }
    @PutMapping("/{id}")
    public ProfesionalResponse actualizar(@PathVariable Integer id,
            @Valid @RequestBody ProfesionalRequest solicitud) {
        return profesionalService.actualizar(id, solicitud);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        profesionalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
