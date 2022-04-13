package utez.edu.mx.citas.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utez.edu.mx.citas.model.Solicitante;
import utez.edu.mx.citas.repository.SolicitanteRepository;

@Service
public class SolicitanteServiceImpl implements SolicitanteService {
	
	@Autowired
    private SolicitanteRepository soliciRepository;

	@Override
	public boolean guardar(Solicitante solicitante) {
		try {
			soliciRepository.save(solicitante);
            return true;
        }catch (Exception a){
            return false;
        }
	}

	@Override
	public List<Solicitante> listar() {
		return soliciRepository.findAll();
	}

	@Override
	public Solicitante obtenerSolicitante(long id) {
		Optional<Solicitante> optional = soliciRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
	}

	@Override
	public boolean eliminar(Long id) {
		boolean exists = soliciRepository.existsById(id);
        if (exists){
        	soliciRepository.deleteById(id);
            return !soliciRepository.existsById(id);
        }
        return false;
	}

	@Override
	public Solicitante mostrarSolicitante(long id) {
		Optional<Solicitante> optional = soliciRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
	}

	@Override
	public Solicitante buscarPorIdUsuario(long idUsuario) {
		return soliciRepository.findByIdUsuario(idUsuario);
	}



}
