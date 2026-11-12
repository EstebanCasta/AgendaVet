package co.edu.autonoma.agendavetapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Punto de entrada: configura Spring Boot y levanta la API. */
@SpringBootApplication
public class AgendaVetApiApplication {

	/** Inicia el servidor web y crea los componentes definidos en el proyecto. */
	public static void main(String[] args) {
		SpringApplication.run(AgendaVetApiApplication.class, args);
	}

}
