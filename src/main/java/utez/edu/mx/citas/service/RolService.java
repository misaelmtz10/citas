package utez.edu.mx.citas.service;

import java.util.List;

import utez.edu.mx.citas.model.Role;

public interface RolService {
	
	boolean guardar(Role rol);
    List<Role> listar();
    Role obtenerRol(long id);
    boolean eliminar(Long id);
    Role mostrarRol(long id);
	Role buscarPorAuthority(String authority);

}
