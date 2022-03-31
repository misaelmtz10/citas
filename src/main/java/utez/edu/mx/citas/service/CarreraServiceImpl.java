package utez.edu.mx.citas.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import utez.edu.mx.citas.model.Carrera;
import utez.edu.mx.citas.repository.CarreraRepository;

public class CarreraServiceImpl implements CarreraService {
	
	@Autowired
    private CarreraRepository carreraRepository;

	@Override
	public boolean guardar(Carrera carrera) {
		try {
			carreraRepository.save(carrera);
            return true;
        }catch (Exception a){
            return false;
        }
	}

	@Override
	public List<Carrera> listar() {
		return carreraRepository.findAll();
	}

	@Override
	public Carrera obtenerCarrera(long id) {
		Optional<Carrera> optional = carreraRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
	}

	@Override
	public boolean eliminarCarrera(Long id) {
		boolean exists = carreraRepository.existsById(id);
        if (exists){
        	carreraRepository.deleteById(id);
            return !carreraRepository.existsById(id);
        }
        return false;
	}

	@Override
	public Carrera mostrarCarrera(long id) {
		Optional<Carrera> optional = carreraRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
	}

}
