package utez.edu.mx.citas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utez.edu.mx.citas.model.Usuario;
import utez.edu.mx.citas.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{

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
    
}