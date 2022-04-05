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

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "Citas")
public class Cita {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCitas", nullable = false)
	private Long id;

	@Column(name = "title", nullable = false, length = 255)
	private String title;

	@Column(name = "start", columnDefinition = "DATETIME NOT NULL", nullable = false)
	private String start;

	@Column(name = "end", columnDefinition = "DATETIME NOT NULL", nullable = false)
	private String end;

    @Column(name = "regitered", nullable = false)
	@CreationTimestamp
	private Date registered;

	@Column(columnDefinition = "tinyint not null")
	private Integer estatus;

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


	public Cita(Long id, String title, String start, String end, Date registered, Integer estatus,
			Solicitante solicitante, Ventanilla ventanilla, Servicio servicio) {
		this.id = id;
		this.title = title;
		this.start = start;
		this.end = end;
		this.registered = registered;
		this.estatus = estatus;
		this.solicitante = solicitante;
		this.ventanilla = ventanilla;
		this.servicio = servicio;
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
