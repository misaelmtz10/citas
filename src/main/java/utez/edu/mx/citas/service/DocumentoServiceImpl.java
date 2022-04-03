package utez.edu.mx.citas.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utez.edu.mx.citas.model.Documento;
import utez.edu.mx.citas.repository.DocumentoRepository;

@Service
public class DocumentoServiceImpl implements DocumentoService {
	
	@Autowired
    private DocumentoRepository docRepository;

	@Override
	public boolean guardar(Documento documento) {
		try {
			docRepository.save(documento);
            return true;
        }catch (Exception a){
            return false;
        }
	}

	@Override
	public List<Documento> listar() {
		return docRepository.findAll();
	}

	@Override
	public Documento obtenerDocumento(long id) {
		Optional<Documento> optional = docRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
	}

	@Override
	public boolean eliminar(Long id) {
		boolean exists = docRepository.existsById(id);
        if (exists){
        	docRepository.deleteById(id);
            return !docRepository.existsById(id);
        }
		return false;
	}

	@Override
	public Documento mostrarDocumento(long id) {
		Optional<Documento> optional = docRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
		return null;
	}

}
