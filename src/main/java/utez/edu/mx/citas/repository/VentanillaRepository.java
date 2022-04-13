package utez.edu.mx.citas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import utez.edu.mx.citas.model.Ventanilla;

public interface VentanillaRepository extends JpaRepository <Ventanilla, Long> {
    List<Ventanilla> findByEstatus (int estatus);
}
