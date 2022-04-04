package utez.edu.mx.citas.service;

import java.util.List;
import utez.edu.mx.citas.model.Carrera;

public interface CarreraService {

	boolean guardar(Carrera carrera);
    List<Carrera> listar();
    Carrera obtenerCarrera(long id);
    boolean eliminarCarrera(Long id);
    Carrera mostrarCarrera(long id);
	
}
