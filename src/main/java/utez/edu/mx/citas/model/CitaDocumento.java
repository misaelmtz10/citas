package utez.edu.mx.citas.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "citas_has_documentos")
public class CitaDocumento {
	
	@Id
    @GeneratedValue
    private long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCitas", nullable = false)
    private Cita cita;
	
	/*@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idServicioDocumento", nullable = false)
    private ServicioDocumento servicio_documento;*/
	@Column(columnDefinition = "tinyint not null")
	private Integer estatus;
	
	@NotBlank(message = "El valor no puede estar en blanco")
	@Column(nullable = true, length = 100)
	private String archivo;

	public CitaDocumento() {
		super();
	}

	public CitaDocumento(long id, Cita cita, ServicioDocumento servicio_documento, Integer estatus, String archivo) {
		super();
		this.id = id;
		this.cita = cita;
		/*this.servicio_documento = servicio_documento;*/
		this.estatus = estatus;
		this.archivo = archivo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Cita getCita() {
		return cita;
	}

	public void setCita(Cita cita) {
		this.cita = cita;
	}

	/*public ServicioDocumento getServicio_documento() {
		return servicio_documento;
	}

	public void setServicio_documento(ServicioDocumento servicio_documento) {
		this.servicio_documento = servicio_documento;
	}*/

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

}
