package utez.edu.mx.citas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Solicitantes")
public class Solicitante {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSolicitantes", nullable = false)
	private Long id;

	@Column(nullable = false, length = 30)
	private String nombre;

    @Column(nullable = false, length = 60)
	private String apellidos;

    @Column(nullable = false, length = 15)
	private String matricula;

    @ManyToOne
	@JoinColumn(name = "idCarrera", nullable = false)
	private Carrera carrera;

    @Column(nullable = false, length = 45)
	private String telefono;

    @Column(nullable = false, length = 45)
	private String correo;

    @ManyToOne
	@JoinColumn(name = "idRole", nullable = false)
	private Rol role;

    public Solicitante(Long id, String nombre, String apellidos, String matricula, Carrera carrera, String telefono,
            String correo, Rol role) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.matricula = matricula;
        this.carrera = carrera;
        this.telefono = telefono;
        this.correo = correo;
        this.role = role;
    }

    public Solicitante() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Rol getRole() {
        return role;
    }

    public void setRole(Rol role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Solicitantes [apellidos=" + apellidos + ", carrera=" + carrera + ", correo=" + correo + ", id=" + id
                + ", matricula=" + matricula + ", nombre=" + nombre + ", role=" + role + ", telefono=" + telefono + "]";
    }
    
}

