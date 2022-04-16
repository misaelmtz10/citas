package utez.edu.mx.citas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Solicitantes")
public class Solicitante {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSolicitantes", nullable = false)
	private Long id;

    @NotBlank(message = "La matricula no puede estar en blanco")
    @Column(nullable = false, length = 15)
	private String matricula;

    @ManyToOne
	@JoinColumn(name = "idCarrera", nullable = false)
	private Carrera carrera;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
	private Usuario usuario;

   
    public Solicitante(Long id, String matricula, Carrera carrera, Usuario usuario) {
        this.id = id;
        this.matricula = matricula;
        this.carrera = carrera;
        this.usuario = usuario;
    }

    public Solicitante() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
    public String toString() {
        return "Solicitante [carrera=" + carrera + ", id=" + id + ", matricula=" + matricula + "]";
    }
    
}

