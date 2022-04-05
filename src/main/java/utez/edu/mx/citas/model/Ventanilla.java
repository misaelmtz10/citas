package utez.edu.mx.citas.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ventanillas")
public class Ventanilla {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ventanilla", nullable = false)
	private Long id;

	@Column(name = "nombre_ventanilla", nullable = false, length = 45)
	private String nombreVentanilla;

    @Column(name = "estatus", columnDefinition = "tinyint not null")
	private Integer estatus;

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

}
