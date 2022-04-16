package utez.edu.mx.citas.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import utez.edu.mx.citas.model.Empleado;

public interface EmpleadoService {

    boolean guardar(Empleado empleado);
    List <Empleado> listar();
    List <Empleado> listarActivos();
    Empleado obtenerEmpleado(long id);
    boolean eliminar(Long id);
    Empleado mostrarEmpleado(long id);
    Long findEmpleadoByUser(long idUser);

}
