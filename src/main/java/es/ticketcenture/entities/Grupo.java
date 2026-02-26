package es.ticketcenture.entities;


import javax.persistence.*;

import java.util.ArrayList;

import java.util.List;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;

import javax.validation.constraints.Min;

import javax.validation.constraints.Max;

/**
 * 
 * Entidad que representa un grupo musical en la BBDD.
 * 
 */

@Entity
@Table(name = "grupos_ticket")

public class Grupo {

	// Declaración de atributos

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_grupo")
	private Integer idGrupo;

	@NotBlank(message = "El nombre del grupo es obligatorio")
	@Size(max = 70, message = "El nombre no puede superar los 70 caracteres")
	@Column(name = "nombre_grupo", nullable = false, length = 70)
	private String nombreGrupo;

	@NotBlank(message = "El país de origen es obligatorio")
	@Size(max = 100, message = "El país no puede superar los 100 caracteres")
	@Column(name = "pais_origen", nullable = false, length = 100)
	private String paisOrigen;

	@NotNull(message = "El año de creación es obligatorio")
	@Min(value = 1900, message = "El año debe ser mayor o igual a 1900")
	@Max(value = 2100, message = "El año debe ser menor o igual a 2100")
	@Column(name = "ano_creacion", nullable = false)
	private Integer anoCreacion;

	// Imagen principal almacenada en BBDD
	@Lob 
	@Column(name = "img_principal")
	private byte[] imagenPrincipal;// Representa datos binarios para almacenar datos en un BLOB.

	@Column(columnDefinition = "TEXT")
	private String biografia;

	@Column(columnDefinition = "TEXT")
	private String discografia;

	@Column(columnDefinition = "TEXT")
	private String componentes;

	@Column(nullable = false)
	private boolean activo;

	// Relación con género musical
	@NotNull(message = "Debe seleccionar un género musical")
	@ManyToOne
	@JoinColumn(name = "id_genero")
	private Genero genero;

	//Relación con galería de imágenes. 
	@OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GaleriaGrupo> galeria = new ArrayList<>();

	//Relación con favoritos.
	@OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Favorito> favoritos = new ArrayList<>();
	
	// Relación con eventos musicales
	@OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY)
	private List<Evento> eventos = new ArrayList<>();

	/**
	 * Constructor vacío
	 */

	public Grupo() {
	}

	/**
	 * Constructor de parámetros.
	 *
	 * @param idGrupo the id grupo
	 * @param nombreGrupo the nombre grupo
	 * @param paisOrigen the pais origen
	 * @param anoCreacion the ano creacion
	 * @param genero the genero
	 * @param imagenPrincipal the imagen principal
	 * @param biografia the biografia
	 * @param discografia the discografia
	 * @param componentes the componentes
	 * @param activo the activo
	 */

	public Grupo(Integer idGrupo, String nombreGrupo, String paisOrigen, Integer anoCreacion,
			Genero genero, byte[] imagenPrincipal, String biografia,
			String discografia, String componentes, boolean activo) {

		this.idGrupo = idGrupo;
		this.nombreGrupo = nombreGrupo;
		this.paisOrigen = paisOrigen;
		this.anoCreacion = anoCreacion;
		this.genero = genero;
		this.imagenPrincipal = imagenPrincipal;
		this.biografia = biografia;
		this.discografia = discografia;
		this.componentes = componentes;
		this.activo = activo;
	}

	/**
	 * Getters y Setters
	 */

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;

	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public String getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	public Integer getAnoCreacion() {
		return anoCreacion;
	}

	public void setAnoCreacion(Integer anoCreacion) {
		this.anoCreacion = anoCreacion;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public byte[] getImagenPrincipal() {
		return imagenPrincipal;
	}

	public void setImagenPrincipal(byte[] imagenPrincipal) {
		this.imagenPrincipal = imagenPrincipal;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public String getDiscografia() {
		return discografia;
	}

	public void setDiscografia(String discografia) {
		this.discografia = discografia;
	}

	public String getComponentes() {
		return componentes;
	}

	public void setComponentes(String componentes) {
		this.componentes = componentes;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<GaleriaGrupo> getGaleria() {
		return galeria;
	}

	public void setGaleria(List<GaleriaGrupo> galeria) {
		this.galeria = galeria;
	}

	public List<Favorito> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(List<Favorito> favoritos) {
		this.favoritos = favoritos;
	}
	
	public List<Evento> getEventos() {
	    return eventos;
	}

	public void setEventos(List<Evento> eventos) {
	    this.eventos = eventos;
	}

	/**
	 * Método toString
	 */

	@Override
	public String toString() {
		return "Grupo [idGrupo=" + idGrupo + ", nombreGrupo=" + nombreGrupo +
				", paisOrigen=" + paisOrigen + ", anoCreacion=" + anoCreacion +
				", activo=" + activo + "]";
	}

}
