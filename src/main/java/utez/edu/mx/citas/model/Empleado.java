package utez.edu.mx.citas.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.OneToOne;

@Entity
@Table(name = "Empleados")
public class Empleado {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmpleado", nullable = false)
	private Long id;

	@Column(columnDefinition = "tinyint not null")
	private Integer estatus;

    @OneToOne
	@JoinColumn(name = "idUsuario", nullable = false)
	private Usuario usuario;

    public Empleado(Long id, Integer estatus, Usuario usuario) {
        this.id = id;
        this.estatus = estatus;
        this.usuario = usuario;
    }
    
    public Empleado(Integer estatus, Usuario usuario) {
        this.estatus = estatus;
        this.usuario = usuario;
    }

    public Empleado() {
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Integer getEstatus() {
        return estatus;
    }


    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }


    public Usuario getUsuario() {
        return usuario;
    }


    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
