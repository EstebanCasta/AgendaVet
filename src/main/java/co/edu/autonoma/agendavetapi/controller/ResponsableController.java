package co.edu.autonoma.agendavetapi.controller;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.autonoma.agendavetapi.dto.ResponsableRequest;
import co.edu.autonoma.agendavetapi.dto.ResponsableResponse;
import co.edu.autonoma.agendavetapi.service.ResponsableService;

/** Recibe las solicitudes HTTP para administrar responsables de mascotas. */
@RestController
@RequestMapping("/api/responsables")
public class ResponsableController {
    private final ResponsableService responsableService;

    public ResponsableController(ResponsableService responsableService) {
        this.responsableService = responsableService;
    }

    /** Registra un responsable y devuelve la ubicación del recurso creado. */
    @PostMapping
    public ResponseEntity<ResponsableResponse> crear(@Valid @RequestBody ResponsableRequest solicitud) {
        ResponsableResponse respuesta = responsableService.crear(solicitud);
        return ResponseEntity.created(URI.create("/api/responsables/" + respuesta.getId())).body(respuesta);
    }
    /** Lista todos los responsables registrados. */
    @GetMapping
    public List<ResponsableResponse> listar() { return responsableService.listar(); }
    /** Consulta un responsable por identificador. */
    @GetMapping("/{id}")
    public ResponsableResponse obtener(@PathVariable Integer id) { return responsableService.obtener(id); }
    /** Reemplaza los datos de un responsable. */
    @PutMapping("/{id}")
    public ResponsableResponse reemplazar(@PathVariable Integer id, @Valid @RequestBody ResponsableRequest solicitud) {
        return responsableService.reemplazar(id, solicitud);
    }
    /** Elimina un responsable si no hay relaciones que lo impidan. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        responsableService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
