package utez.edu.mx.citas.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "ventanilla_has_empleado")
public class VentanillaEmpleado {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idEmpleado", nullable = false)
    private Empleado empleado;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idVentanilla", nullable = false)
    private Ventanilla ventanilla;

    @Column(columnDefinition = "tinyint not null")
	private Integer estatus;

    @NotNull(message = "La hora de inicio no puede estar vacía")
    @Column(nullable = false, name = "hora_inicio", columnDefinition = "TIME NOT NULL")
	private String horaInicio;

    @NotNull(message = "La hora de finalización no puede estar vacía")
    @Column(nullable = false, name = "hora_fin", columnDefinition = "TIME NOT NULL")
	private String horaFin;

    @NotNull(message = "La fecha no puede estar vacía")
    @Column(nullable = false, name = "created_at")
	@CreationTimestamp
    private Date createdAt;

    public VentanillaEmpleado() {
    }

    public VentanillaEmpleado(long id, Empleado empleado, Ventanilla ventanilla, Integer estatus, String horaInicio,
            String horaFin, Date createdAt) {
        this.id = id;
        this.empleado = empleado;
        this.ventanilla = ventanilla;
        this.estatus = estatus;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Ventanilla getVentanilla() {
        return ventanilla;
    }

    public void setVentanilla(Ventanilla ventanilla) {
        this.ventanilla = ventanilla;
    }
    
    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "VentanillaEmpleado [createdAt=" + createdAt + ", empleado=" + empleado + ", horaFin=" + horaFin
                + ", horaInicio=" + horaInicio + ", id=" + id + ", ventanilla=" + ventanilla + "]";
    }


}
