package utez.edu.mx.citas.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "Citas")
public class Cita {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCitas", nullable = false)
	private Long id;

	@NotBlank(message = "El título no puede estar en blanco")
	@Column(name = "title", nullable = false, length = 255)
	private String title;

	@NotNull(message = "La fecha de inicio no puede estar vacía")
	@Column(name = "start", columnDefinition = "DATETIME NOT NULL", nullable = false)
	private String start;

	@NotNull(message = "La fecha de término no puede estar vacía")
	@Column(name = "end", columnDefinition = "DATETIME NOT NULL", nullable = false)
	private String end;

    @Column(name = "regitered", nullable = false)
	@CreationTimestamp
	private Date registered;

	@Column(columnDefinition = "tinyint not null")
	private Integer estatus;

	@Column(nullable = true, length = 255)
	private String archivo;

    @ManyToOne
	@JoinColumn(name = "idSolicitante", nullable = false)
	private Solicitante solicitante;

	@ManyToOne
	@JoinColumn(name = "idVentanilla", nullable = false)
	private Ventanilla ventanilla;

	@ManyToOne
	@JoinColumn(name = "idServicio", nullable = false)
	private Servicio servicio;

	public Cita() {
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getStart() {
		return start;
	}


	public void setStart(String start) {
		this.start = start;
	}


	public String getEnd() {
		return end;
	}


	public void setEnd(String end) {
		this.end = end;
	}


	public Date getRegistered() {
		return registered;
	}


	public void setRegistered(Date registered) {
		this.registered = registered;
	}


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

	public Solicitante getSolicitante() {
		return solicitante;
	}


	public void setSolicitante(Solicitante solicitante) {
		this.solicitante = solicitante;
	}


	public Ventanilla getVentanilla() {
		return ventanilla;
	}


	public void setVentanilla(Ventanilla ventanilla) {
		this.ventanilla = ventanilla;
	}


	public Servicio getServicio() {
		return servicio;
	}


	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}


	@Override
	public String toString() {
		return "Cita [end=" + end + ", estatus=" + estatus + ", id=" + id + ", registered=" + registered + ", servicio="
				+ servicio + ", solicitante=" + solicitante + ", start=" + start + ", title=" + title + ", ventanilla="
				+ ventanilla + "]";
	}

	
}
