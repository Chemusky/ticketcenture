package es.ticketcenture.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidad que representa un actividad en la BBDD.
 *
 */
@Entity
@Table(name = "actividad_usuarios") 
public class Actividad {
	
	/**
	 * Declaracion de atributos
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_actividad")
	private Integer idActividad;
	

	@ManyToOne	//Aqui establecemos la relacion con Usuario
    @JoinColumn(name = "id_usuario", nullable = true) //Y le decimos con que columna se corresponde

    private Usuario usuario;
	
	@Column(name = "fecha_actividad",insertable = false, updatable = false)
	private LocalDateTime  fechaActividad;
	
	@Column(name = "ip_actividad",nullable = false, length = 45)
	private String ipActividad;
	
	@Column(nullable = false, length = 255)
	private String accion;
	
	@Column(columnDefinition = "TEXT", nullable = true)
	private String detalles;
	
	
	/**
	 * Constructor vacio
	 */
	public Actividad() {}
	
	
	/**
	 * Constructor de copia
	 */
	public Actividad(Actividad actividad) {
		this.idActividad = actividad.idActividad;
		this.usuario = actividad.usuario;
		this.fechaActividad = actividad.fechaActividad;
		this.ipActividad = actividad.ipActividad;
		this.accion = actividad.accion;
		this.detalles = actividad.detalles;
	}
	
	/**
	 * Constructor de parametros SIN id ni fecha_actividad (se rellenan automaticamente en la tabla)
	 * 
	 * @param usuario
	 * @param ipActividad
	 * @param accion
	 * @param detalles
	 */
	public Actividad(Usuario usuario, String ipActividad, String accion, String detalles ) {
		this.usuario = usuario;
		this.ipActividad = ipActividad;
		this.accion = accion;
		this.detalles = detalles;
	}	
	/**
	 * Getters y Setters
	 * 
	 */
	
	/**
	 * @return idActividad
	 */
	public Integer getIdActividad() {
		return idActividad;
	}

	/**
	 * @param id_actividad
	 */
	public void setIdActividad(Integer idActividad) {
		this.idActividad = idActividad;
	}

	/**
	 * @return usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param id_usuario
	 */
	public void setUsuario(Usuario idUsuario) {
		this.usuario = idUsuario;
	}

	/**
	 * @return fechaActividad
	 */
	public LocalDateTime getFechaActividad() {
		return fechaActividad;
	}

	/**
	 * @param fechaActividad
	 */
	public void setFechaActividad(LocalDateTime fechaActividad) {
		this.fechaActividad = fechaActividad;
	}

	/**
	 * @return ipActividad
	 */
	public String getIpActividad() {
		return ipActividad;
	}

	/**
	 * @param ip_actividad
	 */
	public void setIpActividad(String ip_actividad) {
		this.ipActividad = ip_actividad;
	}

	/**
	 * @return accion
	 */
	public String getAccion() {
		return accion;
	}

	/**
	 * @param accion
	 */
	public void setAccion(String accion) {
		this.accion = accion;
	}

	/**
	 * @return detalles
	 */
	public String getDetalles() {
		return detalles;
	}

	/**
	 * @param detalles
	 */
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	
}
