package utez.edu.mx.citas.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import utez.edu.mx.citas.model.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    
    Usuario findByUsername(String username);
    
    @Query(value = "SELECT * FROM users u INNER JOIN user_role ur ON u.id = ur.user_id WHERE u.enabled = 0 AND ur.role_id = 2", nativeQuery = true)
    List <Usuario> findByEnabledFalseAndRole();
    
    @Query(value = "SELECT * FROM users WHERE username = :user_", nativeQuery = true)
    Usuario findLastId(String user_);

    @Transactional
    @Modifying
    @Query(value = "update users u set u.password = :password where u.correo = :correo", nativeQuery = true)
    void updatePassword(@Param("password") String password, @Param("correo") String correo); 
}
