package utez.edu.mx.citas.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import utez.edu.mx.citas.model.VentanillaEmpleado;
public interface VentanillaEmpleadoRepository extends JpaRepository <VentanillaEmpleado, Long> {
    List<VentanillaEmpleado> findByEstatus (int estatus);
    
    @Query(value="SELECT id_ventanilla FROM ventanilla_has_empleado WHERE estatus = 1 AND id_empleado = :idEmpleado", nativeQuery = true)
    Long findVentanillaByEmpleado(@Param("idEmpleado") long idEmpleado);
}
