package utez.edu.mx.citas.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "bitacora_acciones")
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "registro",
			procedureName = "registro", parameters = {
			        @StoredProcedureParameter(mode = ParameterMode.IN, name = "idUser", type = Long.class),
			        @StoredProcedureParameter(mode = ParameterMode.IN, name = "idAfectado", type = Long.class),
			        @StoredProcedureParameter(mode = ParameterMode.IN, name = "descripcion", type = String.class)
	}),
	
	@NamedStoredProcedureQuery(name = "eliminar",
	procedureName = "eliminar", parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "idUser", type = Long.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "idAfectado", type = Long.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "descripcion", type = String.class)
	}),
	
	@NamedStoredProcedureQuery(name = "actualizar",
	procedureName = "actualizar", parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "idUser", type = Long.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "idAfectado", type = Long.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "descripcion", type = String.class)
	}),

	@NamedStoredProcedureQuery(name = "solicitarServicio",
	procedureName = "solicitarServicio", parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "idUser", type = Long.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "idAfectado", type = Long.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "descripcion", type = String.class)
	}),

	@NamedStoredProcedureQuery(name = "terminarCita",
	procedureName = "terminarCita", parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "idUser", type = Long.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "idAfectado", type = Long.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "descripcion", type = String.class)
	})
})

public class Bitacora {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idBitacora", nullable = false)
	private Long id;
	
    @Column(name = "usuario", nullable = false)
	private Integer usuario;
    
    @Column(name = "afectado", nullable = true)
	private Integer afectado;
    
    @Column(columnDefinition = "tinyint not null")
	private Integer accion;
    
    @Column(nullable = true, length = 70)
	private String descripcion_accion;
    
    @Column(name = "fecha", nullable = false)
    @CreationTimestamp
	private Date fecha;

	public Bitacora() {
	
	}

	public Bitacora(Long id, Integer usuario, Integer afectado, Integer accion, String descripcion_accion, Date fecha) {
		this.id = id;
		this.usuario = usuario;
		this.afectado = afectado;
		this.accion = accion;
		this.descripcion_accion = descripcion_accion;
		this.fecha = fecha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	public Integer getAfectado() {
		return afectado;
	}

	public void setAfectado(Integer afectado) {
		this.afectado = afectado;
	}

	public Integer getAccion() {
		return accion;
	}

	public void setAccion(Integer accion) {
		this.accion = accion;
	}

	public String getDescripcion_accion() {
		return descripcion_accion;
	}

	public void setDescripcion_accion(String descripcion_accion) {
		this.descripcion_accion = descripcion_accion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
