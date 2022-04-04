package utez.edu.mx.citas.service;

import java.util.List;
import utez.edu.mx.citas.model.Horario;

public interface HorarioService {
	
	boolean guardar(Horario horario);
    List<Horario> listar();
    Horario obtenerHorario(long id);
    boolean eliminar(Long id);
    Horario mostrarHorario(long id);

}
