package co.edu.autonoma.agendavetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.autonoma.agendavetapi.entities.ResponsableEntity;

public interface ResponsableRepository extends JpaRepository<ResponsableEntity, Integer> {

    boolean existsByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento);

    boolean existsByTipoDocumentoAndNumeroDocumentoAndIdNot(
            String tipoDocumento, String numeroDocumento, Integer id);
}
