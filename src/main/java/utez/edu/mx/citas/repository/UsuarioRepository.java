package utez.edu.mx.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import utez.edu.mx.citas.model.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    
}
