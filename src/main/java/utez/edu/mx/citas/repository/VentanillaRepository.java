package utez.edu.mx.citas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import utez.edu.mx.citas.model.Ventanilla;

public interface VentanillaRepository extends JpaRepository <Ventanilla, Long> {
    List<Ventanilla> findByEstatus (int estatus);
    @Query(value = "SELECT v.id_ventanilla, v.estatus, v.nombre_ventanilla FROM ventanillas v INNER JOIN ventanilla_has_empleado vhe ON v.id_ventanilla = vhe.id_ventanilla WHERE vhe.id_ventanilla IS NOT NULL", nativeQuery = true)
    List<Ventanilla> findByVentanillaEmpleado();
}
