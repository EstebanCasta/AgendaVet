package co.edu.autonoma.agendavetapi.exception;

/** Error del dominio para una operación válida en formato pero incompatible con el estado actual. */
public class ConflictoException extends RuntimeException {

    /** Crea el conflicto con un mensaje que podrá devolverse al cliente. */
    public ConflictoException(String mensaje) {
        super(mensaje);
    }
}
