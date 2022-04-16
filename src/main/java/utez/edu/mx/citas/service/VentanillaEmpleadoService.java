package utez.edu.mx.citas.service;

import java.util.List;

import utez.edu.mx.citas.model.VentanillaEmpleado;

public interface VentanillaEmpleadoService {

    boolean guardar(VentanillaEmpleado VentanillaEmpleado);
    List <VentanillaEmpleado> listar();
    VentanillaEmpleado obtenerRegistro(long id);
    boolean eliminar(Long id);
    List <VentanillaEmpleado> listarActivos();
    Long findVentanillaByEmpleado(Long idEmpleado);

}
