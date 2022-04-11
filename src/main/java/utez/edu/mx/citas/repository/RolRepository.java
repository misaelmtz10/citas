package utez.edu.mx.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import utez.edu.mx.citas.model.Role;

public interface RolRepository extends JpaRepository <Role, Long> {
    Role findByAuthority(String authority);
}
