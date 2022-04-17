package utez.edu.mx.citas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utez.edu.mx.citas.model.Usuario;
import utez.edu.mx.citas.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UsuarioServiceImpl implements UsuarioService{
    Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class); 

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean guardar(Usuario usuario) {
        try {
			usuarioRepository.save(usuario);
            return true;
        }catch (Exception a){
            return false;
        }
    }

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerUsuario(long id) {
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public boolean eliminar(Long id) {
        boolean exists = usuarioRepository.existsById(id);
        if (exists){
        	usuarioRepository.deleteById(id);
            return !usuarioRepository.existsById(id);
        }
        return false;
    }

    @Override
    public Usuario mostrar(long id) {
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

	@Override
	public List<Usuario> findByEnabledFalseAndRole() {
		return usuarioRepository.findByEnabledFalseAndRole();
	}

    @Override
    public boolean cambiarContrasena(String password, String username) {
        try {
            usuarioRepository.updatePassword(password, username);
            return true;
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            exception.printStackTrace();
            return false;
        }
    }

	@Override
	public Usuario findLastId(String user_) {
		return usuarioRepository.findLastId(user_);
	}
    
}
