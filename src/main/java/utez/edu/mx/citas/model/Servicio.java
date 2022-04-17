package utez.edu.mx.citas.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Servicios")
public class Servicio {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicios", nullable = false)
	private Long id;

	@NotBlank(message = "El nombre no puede estar en blanco")
	@Column(nullable = false, length = 45)
	private String nombre;

	@NotBlank(message = "La descripción no puede estar en blanco")
    @Column(nullable = false, length = 100)
	private String descripcion;

	@NotNull(message = "Valor no valido")
	@Min(0)
	@Max(5000)
    @Column(nullable = false)
    private double costo;
    
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "servicios_has_documentos", joinColumns = @JoinColumn(name = "idServicios"), inverseJoinColumns = @JoinColumn(name = "idDocumentos"))
    private Set<Documento> documentos;

    public void agregarDocumento(Documento documento) {
		if (documentos == null) {
			documentos = new HashSet<Documento>();
		}
		documentos.add(documento);
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

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public Set<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}

}
