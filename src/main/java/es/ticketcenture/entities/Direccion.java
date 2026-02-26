package es.ticketcenture.entities;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "direcciones")
public class Direccion {
	
	/**
	 * Declaracion de atributos
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_direccion")
	private Integer idDireccion;

	@NotBlank(message = "{direccion.calle.notblank}")
	@Size(max = 100, message = "{direccion.calle.size}")
	@Column(name = "calle", nullable = false)
	private String calle;

	@NotBlank(message = "{direccion.numero.notblank}")
	@Column(name = "numero", nullable = false)
	private String numero;

	@Column(name = "piso")
	private String piso;

	@Column(name = "escalera")
	private String escalera;

	@Column(name = "2linea_direccion")
	private String segundaLineaDireccion;

	@NotNull(message = "{direccion.codigoPostal.notnull}")
	@Min(value = 1000, message = "{direccion.codigoPostal.min}")  
	@Max(value = 52999, message = "{direccion.codigoPostal.max}") 
	@Column(name = "codigo_postal", nullable = false)
	private Integer codigoPostal;

    @NotBlank(message = "{direccion.localidad.notblank}")
	@Column(name = "localidad", nullable = false)
	private String localidad;

    @NotBlank(message = "{direccion.provincia.notblank}")
	@Column(name = "provincia", nullable = false)
	private String provincia;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_direccion")
	private TipoDireccion tipoDireccion;

	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;

	/**
	 * Constructor vacio
	 */
	public Direccion() {

	}

	/**
	 * Constructor de parametros
	 * 
	 * @param idDireccion
	 * @param calle
	 * @param numero
	 * @param piso
	 * @param escalera
	 * @param segundaLineaDireccion
	 * @param codigoPostal
	 * @param localidad
	 * @param provincia
	 * @param tipoDireccion
	 * @param usuario
	 */
	public Direccion(Integer idDireccion, String calle, String numero, String piso, String escalera,
			String segundaLineaDireccion, Integer codigoPostal, String localidad, String provincia,
			TipoDireccion tipoDireccion, Usuario usuario) {
		this.idDireccion = idDireccion;
		this.calle = calle;
		this.numero = numero;
		this.piso = piso;
		this.escalera = escalera;
		this.segundaLineaDireccion = segundaLineaDireccion;
		this.codigoPostal = codigoPostal;
		this.localidad = localidad;
		this.provincia = provincia;
		this.tipoDireccion = tipoDireccion;
		this.usuario = usuario;
	}
	/**
	 * Getters y Setters
	 * 
	 */
	
	/**
	 * @return idDireccion
	 */
	public Integer getIdDireccion() {
		return idDireccion;
	}

	/**
	 * @param idDireccion
	 */
	public void setIdDireccion(Integer idDireccion) {
		this.idDireccion = idDireccion;
	}

	/**
	 * @return calle
	 */
	public String getCalle() {
		return calle;
	}

	/**
	 * @param calle
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}

	/**
	 * @return numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return piso
	 */
	public String getPiso() {
		return piso;
	}

	/**
	 * @param piso
	 */
	public void setPiso(String piso) {
		this.piso = piso;
	}

	/**
	 * @return escalera
	 */
	public String getEscalera() {
		return escalera;
	}

	/**
	 * @param escalera
	 */
	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	/**
	 * @return segundaLineaDireccion
	 */
	public String getSegundaLineaDireccion() {
		return segundaLineaDireccion;
	}

	/**
	 * @param segundaLineaDireccion
	 */
	public void setSegundaLineaDireccion(String segundaLineaDireccion) {
		this.segundaLineaDireccion = segundaLineaDireccion;
	}

	/**
	 * @return codigoPostal
	 */
	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal
	 */
	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * @return localidad
	 */
	public String getLocalidad() {
		return localidad;
	}

	/**
	 * @param localidad
	 */
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	/**
	 * @return provincia
	 */
	public String getProvincia() {
		return provincia;
	}

	/**
	 * @param provincia
	 */
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	/**
	 * @return tipoDireccion
	 */
	public TipoDireccion getTipoDireccion() {
		return tipoDireccion;
	}

	/**
	 * @param tipoDireccion
	 */
	public void setTipoDireccion(TipoDireccion tipoDireccion) {
		this.tipoDireccion = tipoDireccion;
	}

	/**
	 * @return usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Transient
	public String getTexto() {
	    StringBuilder sb = new StringBuilder();

	    sb.append(calle).append(" ").append(numero);

	    if (piso != null && !piso.isBlank()) {
	        sb.append(", Piso ").append(piso);
	    }

	    if (escalera != null && !escalera.isBlank()) {
	        sb.append(", Escalera ").append(escalera);
	    }

	    if (segundaLineaDireccion != null && !segundaLineaDireccion.isBlank()) {
	        sb.append(", ").append(segundaLineaDireccion);
	    }

	    sb.append(", ").append(codigoPostal)
	      .append(" ").append(localidad)
	      .append(" (").append(provincia).append(")");

	    return sb.toString();
	}


}
