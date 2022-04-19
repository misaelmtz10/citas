package utez.edu.mx.citas.service;

import java.util.List;

import utez.edu.mx.citas.model.Ventanilla;

public interface VentanillaService {
	
	boolean guardar(Ventanilla ventanilla);
    List<Ventanilla> listar();
    List<Ventanilla> listarActivas();

    Ventanilla obtenerVentanilla(long id);
    boolean eliminar(Long id);
    Ventanilla mostrar(long id);
    List<Ventanilla> listarPorVentanillaEmpleado();

}
