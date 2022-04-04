package utez.edu.mx.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.citas.model.Documento;

public interface DocumentoRepository extends JpaRepository <Documento, Long> {

}
