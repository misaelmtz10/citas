package utez.edu.mx.citas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import utez.edu.mx.citas.model.Role;

public interface RolRepository extends JpaRepository <Role, Long> {
    Role findByAuthority(String authority);
    
    @Query(value = "SELECT role_id FROM user_role WHERE user_id = :idUser", nativeQuery = true)
    List <Integer> findRolesByUser(long idUser);
}
