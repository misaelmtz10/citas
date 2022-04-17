package utez.edu.mx.citas.model;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.WhereJoinTable;

@Entity
@Table(name = "Ventanillas")
public class Ventanilla {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ventanilla", nullable = false)
	private Long id;

    @NotBlank(message = "El nombre no puede estar en blanco")
	@Column(name = "nombre_ventanilla", nullable = false, length = 45)
	private String nombreVentanilla;

    @Column(name = "estatus", columnDefinition = "tinyint not null")
	private Integer estatus;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "ventanilla_has_empleado", joinColumns = @JoinColumn(name = "idVentanilla"), inverseJoinColumns = @JoinColumn(name = "idEmpleado"))
    @WhereJoinTable(clause = "estatus = 1")
    private List<Empleado> empleados;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Cita> citas;
    
    public Ventanilla() {
    }

    public Ventanilla(Long id, String nombreVentanilla, Integer estatus) {
        this.id = id;
        this.nombreVentanilla = nombreVentanilla;
        this.estatus = estatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreVentanilla() {
        return nombreVentanilla;
    }

    public void setNombreVentanilla(String nombreVentanilla) {
        this.nombreVentanilla = nombreVentanilla;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

}
