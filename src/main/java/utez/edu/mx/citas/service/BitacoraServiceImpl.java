package utez.edu.mx.citas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.citas.repository.BitacoraRepository;

@Service
public class BitacoraServiceImpl implements BitacoraService {
	
	@Autowired
	private BitacoraRepository bitacoraRepository;

	@Override
	public void registro(long idUser, long idAfectado, String descripcion) {
		bitacoraRepository.registro(idUser, idAfectado, descripcion);
	}

	@Override
	public void eliminar(long idUser, long idAfectado, String descripcion) {
		bitacoraRepository.eliminar(idUser, idAfectado, descripcion);
	}

	@Override
	public void actualizar(long idUser, long idAfectado, String descripcion) {
		bitacoraRepository.actualizar(idUser, idAfectado, descripcion);
	}

	@Override
	public void solicitarServicio(long idUser, long idAfectado, String descripcion) {
		bitacoraRepository.solicitarServicio(idUser, idAfectado, descripcion);
	}

	@Override
	public void terminarCita(long idUser, long idAfectado, String descripcion) {
		bitacoraRepository.terminarCita(idUser, idAfectado, descripcion);
	}

}
