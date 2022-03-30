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
@Table(name = "Ventanillas")
public class Ventanilla {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVentanilla", nullable = false)
	private Long id;

	@Column(nullable = false, length = 45)
	private String nombre;

    @Column(nullable = false, length = 45)
	private String apellidos;

    @Column(name = "empleado_ventanilla", nullable = false)
	private String empleadoVentanilla;

    @Column(nullable = false, length = 45)
	private String correo;

    @ManyToOne
	@JoinColumn(name = "idRole", nullable = false)
	private Role role;

    public Ventanilla(Long id, String nombre, String apellidos, String empleadoVentanilla, String correo, Role role) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.empleadoVentanilla = empleadoVentanilla;
        this.correo = correo;
        this.role = role;
    }

    public Ventanilla() {
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

    public String getEmpleadoVentanilla() {
        return empleadoVentanilla;
    }

    public void setEmpleadoVentanilla(String empleadoVentanilla) {
        this.empleadoVentanilla = empleadoVentanilla;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    

}
