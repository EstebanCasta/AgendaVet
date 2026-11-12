package co.edu.autonoma.agendavetapi.exception;

/** Error usado cuando una búsqueda no encuentra el recurso solicitado. */
public class RecursoNoEncontradoException extends RuntimeException {

    /** Crea el error con el detalle que devolverá la API. */
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
