package utez.edu.mx.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.citas.model.Empleado;

public interface EmpleadoRepository extends JpaRepository <Empleado, Long> {
    
    
}
