package utez.edu.mx.citas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Carreras")
public class Carrera {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idCarreras", nullable = false)
	private Long id;
	
	@Column(nullable = false, length = 45)
	private String nombre;

    @Column(nullable = false, length = 8)
	private String siglas;

    public Carrera(Long id, String nombre, String siglas) {
        this.id = id;
        this.nombre = nombre;
        this.siglas = siglas;
    }

    public Carrera() {
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

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    @Override
    public String toString() {
        return "Carrera [id=" + id + ", nombre=" + nombre + ", siglas=" + siglas + "]";
    }
}
