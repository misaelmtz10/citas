package utez.edu.mx.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import utez.edu.mx.citas.model.Solicitante;

public interface SolicitanteRepository extends JpaRepository <Solicitante, Long> {

    @Query(value="SELECT * FROM solicitantes WHERE id_usuario = :idUsuario", nativeQuery = true)
    Solicitante findByIdUsuario (@Param("idUsuario") long idUsuario);

    @Query(value="SELECT id_solicitantes FROM solicitantes WHERE id_usuario = :idUser", nativeQuery = true)
    Long findSolicitanteByUser(long idUser);
}
