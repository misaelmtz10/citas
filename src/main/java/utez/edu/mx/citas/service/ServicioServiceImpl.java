package utez.edu.mx.citas.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utez.edu.mx.citas.model.Servicio;
import utez.edu.mx.citas.repository.ServicioRepository;

@Service
public class ServicioServiceImpl implements ServicioService {
	
	@Autowired
    private ServicioRepository servicioRepository;

	@Override
	public boolean guardar(Servicio servicio) {
		try {
			servicioRepository.save(servicio);
            return true;
        }catch (Exception a){
            return false;
        }
	}

	@Override
	public List<Servicio> listar() {
		return servicioRepository.findAll();
	}

	@Override
	public Servicio obtenerServicio(long id) {
		Optional<Servicio> optional = servicioRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
	}

	@Override
	public boolean eliminar(Long id) {
		boolean exists = servicioRepository.existsById(id);
        if (exists){
        	servicioRepository.deleteById(id);
            return !servicioRepository.existsById(id);
        }
        return false;
	}

	@Override
	public Servicio mostrarServicio(long id) {
		Optional<Servicio> optional = servicioRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
	}

}
