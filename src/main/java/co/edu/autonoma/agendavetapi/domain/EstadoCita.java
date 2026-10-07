package co.edu.autonoma.agendavetapi.domain;

/** Estados permitidos de una cita (RN-04). */
public enum EstadoCita {
    PROGRAMADA,
    ATENDIDA,
    CANCELADA;

    /** RN-05: solo PROGRAMADA puede pasar a ATENDIDA o CANCELADA; ATENDIDA nunca vuelve a PROGRAMADA. */
    public boolean puedeTransicionarA(EstadoCita nuevo) {
        return this == PROGRAMADA && (nuevo == ATENDIDA || nuevo == CANCELADA);
    }
}