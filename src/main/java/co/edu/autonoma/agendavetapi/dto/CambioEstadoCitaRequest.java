package co.edu.autonoma.agendavetapi.dto;

import co.edu.autonoma.agendavetapi.entities.EstadoCita;
import jakarta.validation.constraints.NotNull;

/** JSON de entrada para solicitar un cambio de estado de una cita. */
public class CambioEstadoCitaRequest {

    /** Estado destino; debe ser uno de los valores de EstadoCita. */
    @NotNull(message = "El nuevo estado es obligatorio")
    private EstadoCita estado;

    public CambioEstadoCitaRequest() {
    }

    public CambioEstadoCitaRequest(EstadoCita estado) {
        this.estado = estado;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

}
