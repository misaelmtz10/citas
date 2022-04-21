package utez.edu.mx.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import utez.edu.mx.citas.model.Bitacora;

public interface BitacoraRepository extends JpaRepository<Bitacora, Long> {
	
	@Procedure(procedureName = "registro")
    void registro(@Param("idUser") long idUser, @Param("idAfectado") long idAfectado, @Param("descripcion") String descripcion);
    
    @Procedure(procedureName = "eliminar")
    void eliminar(@Param("idUser") long idUser, @Param("idAfectado") long idAfectado, @Param("descripcion") String descripcion);
    
    @Procedure(procedureName = "actualizar")
    void actualizar(@Param("idUser") long idUser, @Param("idAfectado") long idAfectado, @Param("descripcion") String descripcion);

    @Procedure(procedureName = "solicitarServicio")
    void solicitarServicio(@Param("idUser") long idUser, @Param("idAfectado") long idAfectado, @Param("descripcion") String descripcion);

    @Procedure(procedureName = "terminarCita")
    void terminarCita(@Param("idUser") long idUser, @Param("idAfectado") long idAfectado, @Param("descripcion") String descripcion);

}
