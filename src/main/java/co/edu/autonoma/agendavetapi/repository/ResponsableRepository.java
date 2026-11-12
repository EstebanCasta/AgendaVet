package co.edu.autonoma.agendavetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.autonoma.agendavetapi.entities.ResponsableEntity;

/** Operaciones de base de datos para responsables. */
public interface ResponsableRepository extends JpaRepository<ResponsableEntity, Integer> {

    /** Detecta si ya existe la combinación de tipo y número de documento. */
    boolean existsByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento);

    /** Detecta documentos duplicados excluyendo al responsable que se actualiza. */
    boolean existsByTipoDocumentoAndNumeroDocumentoAndIdNot(
            String tipoDocumento, String numeroDocumento, Integer id);
}
