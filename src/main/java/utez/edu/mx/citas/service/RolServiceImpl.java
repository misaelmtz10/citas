package utez.edu.mx.citas.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import utez.edu.mx.citas.model.Rol;
import utez.edu.mx.citas.repository.RolRepository;

public class RolServiceImpl implements RolService {
	
	@Autowired
    private RolRepository rolRepository;

	@Override
	public boolean guardar(Rol rol) {
		try {
			rolRepository.save(rol);
            return true;
        }catch (Exception a){
            return false;
        }
	}

	@Override
	public List<Rol> listar() {
		return rolRepository.findAll();
	}

	@Override
	public Rol obtenerRol(long id) {
		Optional<Rol> optional = rolRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
	}

	@Override
	public boolean eliminar(Long id) {
		boolean exists = rolRepository.existsById(id);
        if (exists){
        	rolRepository.deleteById(id);
            return !rolRepository.existsById(id);
        }
        return false;
	}

	@Override
	public Rol mostrarRol(long id) {
		Optional<Rol> optional = rolRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
	}

}
