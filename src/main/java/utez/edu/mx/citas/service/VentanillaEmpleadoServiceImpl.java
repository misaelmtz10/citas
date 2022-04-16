package utez.edu.mx.citas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utez.edu.mx.citas.model.VentanillaEmpleado;
import utez.edu.mx.citas.repository.VentanillaEmpleadoRepository;

@Service
public class VentanillaEmpleadoServiceImpl implements VentanillaEmpleadoService {

    @Autowired
    private VentanillaEmpleadoRepository ventanillaEmpleadoRepo;

    
    @Override
    public boolean guardar(VentanillaEmpleado ventanillaEmpleado) {
        try {
			ventanillaEmpleadoRepo.save(ventanillaEmpleado);
            return true;
        }catch (Exception a){
            return false;
        }
    }

    @Override
    public List<VentanillaEmpleado> listar() {
        return ventanillaEmpleadoRepo.findAll();

    }

    @Override
    public VentanillaEmpleado obtenerRegistro(long id) {
        Optional<VentanillaEmpleado> optional = ventanillaEmpleadoRepo.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
    }

    @Override
    public boolean eliminar(Long id) {
        boolean exists = ventanillaEmpleadoRepo.existsById(id);
        if (exists){
        	ventanillaEmpleadoRepo.deleteById(id);
            return !ventanillaEmpleadoRepo.existsById(id);
        }
        return false;
    }

    @Override
    public List<VentanillaEmpleado> listarActivos() {
        return ventanillaEmpleadoRepo.findByEstatus(1);
    }

	@Override
	public Long findVentanillaByEmpleado(Long idEmpleado) {
		if(ventanillaEmpleadoRepo.findVentanillaByEmpleado(idEmpleado) != null) {
			return ventanillaEmpleadoRepo.findVentanillaByEmpleado(idEmpleado);
		} else {
			return 0L;
		}
	}    
    
    
}
