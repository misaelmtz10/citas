package utez.edu.mx.citas.service;

import java.util.List;

import utez.edu.mx.citas.model.Documento;

public interface DocumentoService {
	
	boolean guardar(Documento documento);
    List <Documento> listar();
    Documento obtenerDocumento(long id);
    boolean eliminar(Long id);
    Documento mostrarDocumento(long id);

}
