package co.edu.autonoma.agendavetapi.controller;

import lombok.RequiredArgsConstructor;
import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.autonoma.agendavetapi.dto.ServicioRequest;
import co.edu.autonoma.agendavetapi.dto.ServicioResponse;
import co.edu.autonoma.agendavetapi.service.ServicioService;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioController {
    private final ServicioService servicioService;
    @PostMapping
    public ResponseEntity<ServicioResponse> crear(@Valid @RequestBody ServicioRequest solicitud) {
        ServicioResponse respuesta = servicioService.crear(solicitud);
        return ResponseEntity.created(URI.create("/api/servicios/" + respuesta.id())).body(respuesta);
    }
    @GetMapping
    public List<ServicioResponse> listar() { return servicioService.listar(); }
    @GetMapping("/{id}")
    public ServicioResponse obtener(@PathVariable Integer id) { return servicioService.obtener(id); }
    @PutMapping("/{id}")
    public ServicioResponse reemplazar(@PathVariable Integer id, @Valid @RequestBody ServicioRequest solicitud) {
        return servicioService.reemplazar(id, solicitud);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
