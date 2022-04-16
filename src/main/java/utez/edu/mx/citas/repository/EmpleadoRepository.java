package utez.edu.mx.citas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import utez.edu.mx.citas.model.Empleado;

public interface EmpleadoRepository extends JpaRepository <Empleado, Long> {
    
    List<Empleado> findByEstatus (int estatus);
 //   List<Ventanilla> findByVentanilla (int estatus);
    
    @Query(value="SELECT id_empleado FROM empleados WHERE id_usuario = :idUser", nativeQuery = true)
    Long findEmpleadoByUser(@Param("idUser") long idUser);

}
