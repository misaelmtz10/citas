package utez.edu.mx.citas.service;

import java.util.List;
import utez.edu.mx.citas.model.Cita;

public interface CitaService {
	
	boolean guardar(Cita cita);
    List<Cita> listar();
    Cita obtenerCita(long id);
    boolean eliminar(Long id);
    Cita mostrarCita(long id);

}
