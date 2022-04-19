package utez.edu.mx.citas.service;

import java.util.List;

import utez.edu.mx.citas.model.Servicio;

public interface ServicioService {
	
	boolean guardar(Servicio servicio);
    List<Servicio> listar();
    Servicio obtenerServicio(long id);
    boolean eliminar(Long id);
    Servicio mostrarServicio(long id);
    List<String> obtenerServicioDocumento(long idServicio);
}
