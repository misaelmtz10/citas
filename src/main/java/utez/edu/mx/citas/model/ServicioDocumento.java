package utez.edu.mx.citas.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "servicios_has_documentos")
public class ServicioDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idServicios", nullable = false)
    private Servicio servicio;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idDocumentos", nullable = false)
    private Documento documento;

	public ServicioDocumento() {
	}

	public ServicioDocumento(Long id, Servicio servicio, Documento documento) {
		this.id = id;
		this.servicio = servicio;
		this.documento = documento;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

}
