package utez.edu.mx.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.citas.model.Cita;

public interface CitaRepository extends JpaRepository <Cita, Long> {

}
