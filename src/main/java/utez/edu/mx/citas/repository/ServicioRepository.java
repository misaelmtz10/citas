package utez.edu.mx.citas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import utez.edu.mx.citas.model.Servicio;

public interface ServicioRepository extends JpaRepository <Servicio, Long> {

    @Query(value="SELECT d.nombre  FROM servicios_has_documentos shd INNER JOIN documentos d ON d.id_documentos = shd.id_documentos WHERE shd.id_servicios = :idServicio", nativeQuery = true)
    List<String> findServicioDocumentoByServicio(@Param("idServicio") long idServicio);
}
