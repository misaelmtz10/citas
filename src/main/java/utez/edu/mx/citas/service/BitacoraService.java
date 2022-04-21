package utez.edu.mx.citas.service;

public interface BitacoraService {
	
	void registro(long idUser, long idAfectado, String descripcion);
    void eliminar(long idUser, long idAfectado, String descripcion);
    void actualizar(long idUser, long idAfectado, String descripcion);
    void solicitarServicio(long idUser, long idAfectado, String descripcion);
    void terminarCita(long idUser, long idAfectado, String descripcion);

}
