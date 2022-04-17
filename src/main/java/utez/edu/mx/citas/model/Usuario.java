package utez.edu.mx.citas.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class Usuario {
    
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotBlank(message = "El nombre de usuario no puede estar en blanco")
    @Column(nullable = false, length = 100, unique = true)
	private String username;

    @NotBlank(message = "La contraseña no puede estar en blanco")
	@Column(nullable = false, length = 255)
	private String password;

    @NotBlank(message = "El nombre no puede estar en blanco")
	@Column(nullable = false, length = 30)
	private String nombre;

    @NotBlank(message = "Los apellidos no pueden estar en blanco")
    @Column(nullable = false, length = 60)
	private String apellidos;

    @NotBlank(message = "El teléfono no puede estar en blanco")
    @Column(nullable = false, length = 45)
	private String telefono;

    @NotBlank(message = "El correo no puede estar en blanco")
    @Column(nullable = false, length = 45)
	private String correo;

	@Column(columnDefinition = "tinyint not null")
	private Integer intentos;

    @Column(columnDefinition = "tinyint not null")
	private boolean enabled;
    
    @ManyToOne(fetch=FetchType.LAZY)
    private Empleado empleado;

    @ManyToOne( fetch=FetchType.LAZY)
    private Solicitante solicitante;

   	@ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public void agregarRol(Role role) {
		if (roles == null) {
			roles = new HashSet<Role>();
		}
		roles.add(role);
 	}
    
    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Solicitante getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Solicitante solicitante) {
        this.solicitante = solicitante;
    }

    public Usuario() {
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
