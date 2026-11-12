package co.edu.autonoma.agendavetapi.controller;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.autonoma.agendavetapi.dto.ServicioRequest;
import co.edu.autonoma.agendavetapi.dto.ServicioResponse;
import co.edu.autonoma.agendavetapi.service.ServicioService;

/** Recibe las solicitudes HTTP para administrar los servicios de la clínica. */
@RestController
@RequestMapping("/api/servicios")
public class ServicioController {
    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    /** Crea un servicio y devuelve la ubicación del nuevo recurso. */
    @PostMapping
    public ResponseEntity<ServicioResponse> crear(@Valid @RequestBody ServicioRequest solicitud) {
        ServicioResponse respuesta = servicioService.crear(solicitud);
        return ResponseEntity.created(URI.create("/api/servicios/" + respuesta.getId())).body(respuesta);
    }
    /** Lista todos los servicios registrados. */
    @GetMapping
    public List<ServicioResponse> listar() { return servicioService.listar(); }
    /** Consulta un servicio por identificador. */
    @GetMapping("/{id}")
    public ServicioResponse obtener(@PathVariable Integer id) { return servicioService.obtener(id); }
    /** Reemplaza los datos de un servicio. */
    @PutMapping("/{id}")
    public ServicioResponse reemplazar(@PathVariable Integer id, @Valid @RequestBody ServicioRequest solicitud) {
        return servicioService.reemplazar(id, solicitud);
    }
    /** Elimina un servicio si no hay relaciones que lo impidan. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
