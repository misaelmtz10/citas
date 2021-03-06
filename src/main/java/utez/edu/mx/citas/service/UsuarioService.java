package utez.edu.mx.citas.service;

import java.util.List;

import utez.edu.mx.citas.model.Usuario;

public interface UsuarioService {
    boolean guardar(Usuario usuario);
    List<Usuario> listar();
    Usuario obtenerUsuario(long id);
    boolean eliminar(Long id);
    Usuario mostrar(long id);
    Usuario buscarPorUsername(String username);
    Usuario findLastId(String user_);
    List <Usuario> findByEnabledFalseAndRole(); 
    boolean cambiarContrasena(String password, String username);
    void iniciarSesion(Long user, String descripcion);
    List <Usuario> findUsers(long idUser);
}
