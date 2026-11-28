package co.edu.autonoma.agendavetapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/** Define la autenticación y los permisos de los endpoints de la API. */
@Configuration
@EnableWebSecurity
public class SegurityConfig {

    /** Configura qué rutas son públicas y cuáles requieren un usuario autenticado. */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // La API no usa sesiones de navegador ni formularios CSRF.
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(autorizacion -> autorizacion
                // Esta ruta permite comprobar que la API está disponible.
                .requestMatchers("/api/estado").permitAll()
                // Los dos tipos de usuario pueden consultar los datos.
                .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER", "ADMIN")
                // Crear, actualizar y eliminar requiere el rol de administrador.
                .requestMatchers("/api/**").hasRole("ADMIN")
                // Cualquier otra ruta también requiere autenticación.
                .anyRequest().authenticated())
            // Postman puede enviar las credenciales con HTTP Basic Auth.
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /** Codifica las claves antes de entregárselas a Spring Security. */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** Crea dos usuarios temporales en memoria usando valores del entorno. */
    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder,
            @Value("${SEGURIDAD_ADMIN_USUARIO}") String nombreAdmin,
            @Value("${SEGURIDAD_ADMIN_CLAVE}") String claveAdmin,
            @Value("${SEGURIDAD_USUARIO}") String nombreUsuario,
            @Value("${SEGURIDAD_USUARIO_CLAVE}") String claveUsuario) {

        UserDetails administrador = User.withUsername(nombreAdmin)
            .password(passwordEncoder.encode(claveAdmin))
            .roles("ADMIN")
            .build();

        UserDetails usuario = User.withUsername(nombreUsuario)
            .password(passwordEncoder.encode(claveUsuario))
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(administrador, usuario);
    }
}
