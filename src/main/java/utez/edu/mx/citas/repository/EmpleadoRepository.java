package utez.edu.mx.citas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import utez.edu.mx.citas.model.Empleado;

public interface EmpleadoRepository extends JpaRepository <Empleado, Long> {
    
    @Query(value = "SELECT e.id_usuario, e.estatus FROM users u INNER JOIN empleados e ON u.id = e.id_usuario WHERE NOT EXISTS (SELECT NULL FROM ventanilla_has_empleado vhe WHERE vhe.id_empleado = e.id_usuario AND vhe.estatus = 1)", nativeQuery = true)
    List<Empleado> findByEmpleadosDisponibles();
    
    @Query(value="SELECT id_empleado FROM empleados WHERE id_usuario = :idUser", nativeQuery = true)
    Long findEmpleadoByUser(@Param("idUser") long idUser);

}
