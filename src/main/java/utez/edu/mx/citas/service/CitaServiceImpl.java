package utez.edu.mx.citas.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utez.edu.mx.citas.model.Cita;
import utez.edu.mx.citas.repository.CitaRepository;

@Service
public class CitaServiceImpl implements CitaService {
	
	@Autowired
    private CitaRepository citaRepository;

	@Override
	public boolean guardar(Cita cita) {
		try {
			citaRepository.save(cita);
            return true;
        }catch (Exception a){
            return false;
        }
	}

	@Override
	public List<Cita> listar() {
		return citaRepository.findAll();
	}

	@Override
	public Cita obtenerCita(long id) {
		Optional<Cita> optional = citaRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
	}

	@Override
	public boolean eliminar(Long id) {
		boolean exists = citaRepository.existsById(id);
        if (exists){
        	citaRepository.deleteById(id);
            return !citaRepository.existsById(id);
        }
		return false;
	}

	@Override
	public Cita mostrarCita(long id) {
		Optional<Cita> optional = citaRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
	}

}
