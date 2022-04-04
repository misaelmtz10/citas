package utez.edu.mx.citas.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Usuario {
    
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario", nullable = false)
	private Long id;

	@Column(nullable = false, length = 30)
	private String nombre;

    @Column(nullable = false, length = 60)
	private String apellidos;

    @Column(nullable = false, length = 45)
	private String telefono;

    @Column(nullable = false, length = 45)
	private String correo;

	@Column(columnDefinition = "tinyint not null")
	private Integer intentos;

    @Column(nullable = false, length = 45)
	private String contrasena;

    @Column(columnDefinition = "tinyint not null")
	private Integer estatus;

    @ManyToOne
	@JoinColumn(name = "idRole", nullable = false)
	private Rol role;

    public Usuario(Long id, String nombre, String apellidos, String telefono, String correo, Integer intentos,
            String contrasena, Integer estatus, Rol role) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.intentos = intentos;
        this.contrasena = contrasena;
        this.estatus = estatus;
        this.role = role;
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

    public Rol getRole() {
        return role;
    }

    public void setRole(Rol role) {
        this.role = role;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "Usuario [apellidos=" + apellidos + ", contrasena=" + contrasena + ", correo=" + correo + ", estatus="
                + estatus + ", id=" + id + ", intentos=" + intentos + ", nombre=" + nombre + ", role=" + role
                + ", telefono=" + telefono + "]";
    }
}
