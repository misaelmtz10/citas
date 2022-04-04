package utez.edu.mx.citas.model;
import java.time.LocalDateTime;
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

	@Column(name = "start", nullable = false)
	@CreationTimestamp
	private Date start;

	@Column(name = "end", nullable = false)
	@CreationTimestamp
	private Date end;

    @Column(name = "regitered", nullable = false)
	@CreationTimestamp
	private LocalDateTime registered;

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


	public Date getStart() {
		return start;
	}


	public void setStart(Date start) {
		this.start = start;
	}


	public Date getEnd() {
		return end;
	}


	public void setEnd(Date end) {
		this.end = end;
	}


	public LocalDateTime getRegistered() {
		return registered;
	}


	public void setRegistered(LocalDateTime registered) {
		this.registered = registered;
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

	
}
