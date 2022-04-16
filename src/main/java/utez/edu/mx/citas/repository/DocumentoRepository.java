package utez.edu.mx.citas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.citas.model.Documento;

public interface DocumentoRepository extends JpaRepository <Documento, Long> {
	
	List <Documento> findByEstatus(Integer estatus);

}
