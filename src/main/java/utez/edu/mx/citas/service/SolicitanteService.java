package utez.edu.mx.citas.service;

import java.util.List;

import utez.edu.mx.citas.model.Solicitante;

public interface SolicitanteService {
	
	boolean guardar(Solicitante solicitante);
    List<Solicitante> listar();
    Solicitante obtenerSolicitante(long id);
    boolean eliminar(Long id);
    Solicitante mostrarSolicitante(long id);

}
