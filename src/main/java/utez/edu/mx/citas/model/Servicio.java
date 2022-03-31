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
@Table(name = "Servicios")
public class Servicio {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idServicos", nullable = false)
	private Long id;

	@Column(nullable = false, length = 45)
	private String nombre;

    @Column(nullable = false, length = 100)
	private String descripcion;

    @ManyToOne
	@JoinColumn(name = "idDocumento", nullable = false)
	private Documento documento;
    
    public Servicio(Long id, String nombre, String descripcion, Documento documento) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.documento = documento;
    }

    public Servicio() {
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

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    

}
