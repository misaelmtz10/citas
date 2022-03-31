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
@Table(name = "Horarios")
public class Horario {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHorarios", nullable = false)
	private Long id;

	@Column(nullable = false)
	@CreationTimestamp
	private Date fecha;

    @Column(nullable = false, name = "hora_inicio")
	@CreationTimestamp
	private LocalDateTime horaInicio;

    @Column(nullable = false, name = "hora_fin")
	@CreationTimestamp
	private LocalDateTime horaFin;

    @Column(nullable = false, name = "repeticiones_disponibles")
    private Integer repeticionesDisponibles;

	@ManyToOne
	@JoinColumn(name = "idVentanilla", nullable = false)
	private Ventanilla ventanilla;

    public Horario(Long id, Date fecha, LocalDateTime horaInicio, LocalDateTime horaFin,
            Integer repeticionesDisponibles, Ventanilla ventanilla) {
        this.id = id;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.repeticionesDisponibles = repeticionesDisponibles;
        this.ventanilla = ventanilla;
    }

    public Horario() {
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

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalDateTime horaFin) {
        this.horaFin = horaFin;
    }

    public Integer getRepeticionesDisponibles() {
        return repeticionesDisponibles;
    }

    public void setRepeticionesDisponibles(Integer repeticionesDisponibles) {
        this.repeticionesDisponibles = repeticionesDisponibles;
    }

    public Ventanilla getVentanilla() {
        return ventanilla;
    }

    public void setVentanilla(Ventanilla ventanilla) {
        this.ventanilla = ventanilla;
    }

    
}
