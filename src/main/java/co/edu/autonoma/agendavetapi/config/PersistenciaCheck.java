package co.edu.autonoma.agendavetapi.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import co.edu.autonoma.agendavetapi.entities.EstadoCita;
import co.edu.autonoma.agendavetapi.entities.ServicioEntity;
import co.edu.autonoma.agendavetapi.repository.CitaRepository;
import co.edu.autonoma.agendavetapi.repository.ServicioRepository;

/**
 * Prueba TEMPORAL de persistencia: se ejecuta una vez al iniciar la aplicación.
 * Se retira cuando existan los endpoints GET de la próxima clase.
 */
@Component
public class PersistenciaCheck implements CommandLineRunner {

    private static final String SERVICIO_PRUEBA = "Prueba de conexion";

    private final DataSource dataSource;
    private final ServicioRepository servicioRepository;
    private final CitaRepository citaRepository;

    public PersistenciaCheck(DataSource dataSource,
                            ServicioRepository servicioRepository,
                            CitaRepository citaRepository) {
        this.dataSource = dataSource;
        this.servicioRepository = servicioRepository;
        this.citaRepository = citaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. ¿Con qué base de datos habla realmente la aplicación?
        try (Connection conexion = dataSource.getConnection()) {
            DatabaseMetaData meta = conexion.getMetaData();
            System.out.println("Conectado a: " + meta.getDatabaseProductName()
                    + " " + meta.getDatabaseProductVersion());
            System.out.println("URL: " + meta.getURL());
        }

        // 2. save(): inserta un servicio de prueba (solo si no existe, para no duplicar al reiniciar)
        if (!servicioRepository.existsByNombre(SERVICIO_PRUEBA)) {
            ServicioEntity servicio = new ServicioEntity();
            servicio.setNombre(SERVICIO_PRUEBA);
            servicio.setDescripcion("Registro creado por PersistenciaCheck");
            servicio.setDuracionMinutos(30);
            servicioRepository.save(servicio);
        }

        // 3. count() y findAll()
        System.out.println("Total servicios: " + servicioRepository.count());
        servicioRepository.findAll().forEach(s ->
                System.out.println(s.getId() + " · " + s.getNombre()
                        + " · " + s.getDuracionMinutos() + " min"));

        // 4. Query method propio: countByEstado
        for (EstadoCita estado : EstadoCita.values()) {
            System.out.println("Citas " + estado + ": " + citaRepository.countByEstado(estado));
        }
    }
}