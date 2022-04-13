package utez.edu.mx.citas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.citas.model.Empleado;
import utez.edu.mx.citas.model.Ventanilla;

public interface EmpleadoRepository extends JpaRepository <Empleado, Long> {
    
    List<Empleado> findByEstatus (int estatus);
 //   List<Ventanilla> findByVentanilla (int estatus);

}
