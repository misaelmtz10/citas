package utez.edu.mx.citas.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import utez.edu.mx.citas.model.Horario;
import utez.edu.mx.citas.repository.HorarioRepository;

public class HorarioServiceImpl implements HorarioService {
	
	@Autowired
    private HorarioRepository horarioRepository;

	@Override
	public boolean guardar(Horario horario) {
		try {
			horarioRepository.save(horario);
            return true;
        }catch (Exception a){
            return false;
        }
	}

	@Override
	public List<Horario> listar() {
		return horarioRepository.findAll();
	}

	@Override
	public Horario obtenerHorario(long id) {
		Optional<Horario> optional = horarioRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
	}

	@Override
	public boolean eliminar(Long id) {
		boolean exists = horarioRepository.existsById(id);
        if (exists){
        	horarioRepository.deleteById(id);
            return !horarioRepository.existsById(id);
        }
        return false;
	}

	@Override
	public Horario mostrarHorario(long id) {
		Optional<Horario> optional = horarioRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
	}

}
