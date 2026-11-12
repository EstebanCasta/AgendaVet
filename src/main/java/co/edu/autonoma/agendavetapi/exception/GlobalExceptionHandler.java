package co.edu.autonoma.agendavetapi.exception;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Convierte errores de servicios, validación y base de datos en respuestas HTTP. */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Responde 404 cuando el servicio no encuentra el recurso. */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ProblemDetail manejarNoEncontrado(RecursoNoEncontradoException excepcion) {
        return crearProblema(HttpStatus.NOT_FOUND, "Recurso no encontrado", excepcion.getMessage());
    }

    /** Responde 409 cuando una regla de negocio rechaza la operación. */
    @ExceptionHandler(ConflictoException.class)
    public ProblemDetail manejarConflicto(ConflictoException excepcion) {
        return crearProblema(HttpStatus.CONFLICT, "Conflicto", excepcion.getMessage());
    }

    /** Traduce restricciones de integridad y relaciones a un conflicto 409. */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail manejarIntegridad(DataIntegrityViolationException excepcion) {
        String detalle = "La operación entra en conflicto con un registro existente o relacionado.";
        return crearProblema(HttpStatus.CONFLICT, "Conflicto de datos", detalle);
    }

    /** Devuelve 400 y detalla los campos que no pasaron la validación. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail manejarValidacion(MethodArgumentNotValidException excepcion) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError error : excepcion.getBindingResult().getFieldErrors()) {
            if (!errores.containsKey(error.getField())) {
                String mensaje = error.getDefaultMessage();
                if (mensaje == null) {
                    mensaje = "Valor inválido";
                }
                errores.put(error.getField(), mensaje);
            }
        }

        ProblemDetail problema = crearProblema(HttpStatus.BAD_REQUEST, "Solicitud inválida",
                "Uno o más campos no cumplen las reglas de validación.");
        problema.setProperty("errores", errores);
        return problema;
    }

    /** Crea el formato ProblemDetail común para los errores de la API. */
    private ProblemDetail crearProblema(HttpStatus estado, String titulo, String detalle) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(estado, detalle);
        problema.setTitle(titulo);
        problema.setType(URI.create("about:blank"));
        return problema;
    }
}
