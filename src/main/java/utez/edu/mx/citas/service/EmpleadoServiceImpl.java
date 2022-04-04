package utez.edu.mx.citas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utez.edu.mx.citas.model.Empleado;
import utez.edu.mx.citas.repository.EmpleadoRepository;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    
    @Override
    public boolean guardar(Empleado empleado) {
        try {
			empleadoRepository.save(empleado);
            return true;
        }catch (Exception a){
            return false;
        }
    }

    @Override
    public List<Empleado> listar() {
        return empleadoRepository.findAll();

    }

    @Override
    public Empleado obtenerEmpleado(long id) {
        Optional<Empleado> optional = empleadoRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
    }

    @Override
    public boolean eliminar(Long id) {
        boolean exists = empleadoRepository.existsById(id);
        if (exists){
        	empleadoRepository.deleteById(id);
            return !empleadoRepository.existsById(id);
        }
        return false;
    }

    @Override
    public Empleado mostrarEmpleado(long id) {
        Optional<Empleado> optional = empleadoRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
    }
    
}
