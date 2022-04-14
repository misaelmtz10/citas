package utez.edu.mx.citas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import utez.edu.mx.citas.model.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    
    Usuario findByUsername(String username);
    
    @Query(value = "SELECT * FROM users u INNER JOIN user_role ur ON u.id = ur.user_id WHERE u.enabled = 0 AND ur.role_id = 2", nativeQuery = true)
    List <Usuario> findByEnabledFalseAndRole();
}
