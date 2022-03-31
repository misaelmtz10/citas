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

	@Column(nullable = false, length = 45)
	@CreationTimestamp
	private Date fecha;

    @Column(nullable = false, length = 100)
	@CreationTimestamp
	private LocalDateTime fechaRegistro;

    @ManyToOne
	@JoinColumn(name = "idSolicitante", nullable = false)
	private Solicitante solicitante;

	@ManyToOne
	@JoinColumn(name = "idVentanilla", nullable = false)
	private Ventanilla ventanilla;

	@ManyToOne
	@JoinColumn(name = "idServicio", nullable = false)
	private Servicio servicio;

	private Float monto;

	public Cita(Long id, Date fecha, LocalDateTime fechaRegistro, Solicitante solicitante, Ventanilla ventanilla,
			Servicio servicio, Float monto) {
		this.id = id;
		this.fecha = fecha;
		this.fechaRegistro = fechaRegistro;
		this.solicitante = solicitante;
		this.ventanilla = ventanilla;
		this.servicio = servicio;
		this.monto = monto;
	}

	public Cita() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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

	public Float getMonto() {
		return monto;
	}

	public void setMonto(Float monto) {
		this.monto = monto;
	}

	


}
