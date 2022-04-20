package utez.edu.mx.citas.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utez.edu.mx.citas.model.Ventanilla;
import utez.edu.mx.citas.repository.VentanillaRepository;

@Service
public class VentanillaServiceImpl implements VentanillaService {
	
	@Autowired
    private VentanillaRepository ventanillaRepository;

	@Override
	public boolean guardar(Ventanilla ventanilla) {
		try {
			ventanillaRepository.save(ventanilla);
            return true;
        }catch (Exception a){
            return false;
        }
	}

	@Override
	public List<Ventanilla> listar() {
		return ventanillaRepository.findAll();
	}

	@Override
	public List<Ventanilla> listarActivas() {
		return ventanillaRepository.findByEstatus(1);
	}

	@Override
	public Ventanilla obtenerVentanilla(long id) {
		Optional<Ventanilla> optional = ventanillaRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
	}

	@Override
	public boolean eliminar(Long id) {
		boolean exists = ventanillaRepository.existsById(id);
        if (exists){
        	ventanillaRepository.deleteById(id);
            return !ventanillaRepository.existsById(id);
        }
        return false;
	}

	@Override
	public Ventanilla mostrar(long id) {
		Optional<Ventanilla> optional = ventanillaRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
	}

	@Override
	public List<Ventanilla> listarPorVentanillaEmpleado() {
		return ventanillaRepository.findByVentanillaEmpleado();
	}

}
