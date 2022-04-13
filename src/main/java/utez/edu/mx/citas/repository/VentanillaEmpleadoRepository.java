package utez.edu.mx.citas.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import utez.edu.mx.citas.model.VentanillaEmpleado;
public interface VentanillaEmpleadoRepository extends JpaRepository <VentanillaEmpleado, Long> {
    List<VentanillaEmpleado> findByEstatus (int estatus);
}
