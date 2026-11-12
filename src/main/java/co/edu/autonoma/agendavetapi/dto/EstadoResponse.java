package co.edu.autonoma.agendavetapi.dto;

/** Respuesta simple del endpoint que comprueba el estado de la API. */
public class EstadoResponse {

    private String servicio;
    private String estado;

    public EstadoResponse() {
    }

    public EstadoResponse(String servicio, String estado) {
        this.servicio = servicio;
        this.estado = estado;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
