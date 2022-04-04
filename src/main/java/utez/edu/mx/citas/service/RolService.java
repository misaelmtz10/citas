package utez.edu.mx.citas.service;

import java.util.List;

import utez.edu.mx.citas.model.Rol;

public interface RolService {
	
	boolean guardar(Rol rol);
    List<Rol> listar();
    Rol obtenerRol(long id);
    boolean eliminar(Long id);
    Rol mostrarRol(long id);

}
