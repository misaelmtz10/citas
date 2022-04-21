package utez.edu.mx.citas.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import utez.edu.mx.citas.model.Role;
import utez.edu.mx.citas.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService {
	
	@Autowired
    private RolRepository rolRepository;

	@Override
	public boolean guardar(Role rol) {
		try {
			rolRepository.save(rol);
            return true;
        }catch (Exception a){
            return false;
        }
	}

	@Override
	public List<Role> listar() {
		List <Long> list1= new ArrayList();  
		list1.add(1L);
		list1.add(2L);
		return rolRepository.findByIdIn(list1);
	}

	@Override
	public Role obtenerRol(long id) {
		Optional<Role> optional = rolRepository.findById(id);
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
	public Role mostrarRol(long id) {
		Optional<Role> optional = rolRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
	}

	@Override
	public Role buscarPorAuthority(String authority) {
		return rolRepository.findByAuthority(authority);
	}

	@Override
	public List<Integer> findRolesByUser(long idUser) {
		return rolRepository.findRolesByUser(idUser);
	}

}
