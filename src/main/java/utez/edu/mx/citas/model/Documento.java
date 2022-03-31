package utez.edu.mx.citas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Documentos")
public class Documento {
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idDocumentos", nullable = false)
	private Long id;
	
	@Column(nullable = false, length = 45)
	private String nombre;

    @Column(nullable = false, length = 100)
	private String descripcion;

    public Documento(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Documento() {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Documento [descripcion=" + descripcion + ", id=" + id + ", nombre=" + nombre + "]";
    }

    

}
