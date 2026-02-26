package es.ticketcenture.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa un noticia en la BBDD.
 */
@Entity
@Table(name = "noticias")
public class Noticia {

	/**
	 * Declaracion de atributos
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_noticia")
	private Integer idNoticia;

	@NotBlank(message = "{noticia.titulo.obligatorio}")
	@Size(max = 255, message = "{noticia.titulo.max}")
	private String titulo;

	@NotBlank(message = "{noticia.cuerpo.obligatorio}")
	@Lob
	private String cuerpo;

	@Size(max = 500, message = "{noticia.enlace.max}")
	private String enlace;

	@Lob
	private byte[] imagen; // NO obligatoria según la BD

	@NotNull(message = "{noticia.publicado.obligatorio}")
	private Boolean publicado;

	@Column(name = "fecha_publicacion")
	private LocalDateTime fechaPublicacion;

	@Column(name = "fecha_creacion", updatable = false)
	private LocalDateTime fechaCreacion;

	// RELACIÓN CON GRUPO
	@ManyToOne
	@JoinColumn(name = "id_grupo")
	private Grupo grupo;

	// RELACIÓN CON EVENTO
	@ManyToOne
	@JoinColumn(name = "id_evento")
	private Evento evento;

	/**
	 * Constructor vacio
	 */
	public Noticia() {
	}

	/**
	 * Constructor de parametros para crear nuevas noticias
	 *
	 * @param titulo    the titulo
	 * @param cuerpo    the cuerpo
	 * @param publicado the publicado
	 */
	public Noticia(String titulo, String cuerpo, Boolean publicado) {
		this.titulo = titulo;
		this.cuerpo = cuerpo;
		this.publicado = publicado;
		this.fechaCreacion = LocalDateTime.now();
	}

	// GETTERS Y SETTERS
	/**
	 * @return
	 */
	public Integer getIdNoticia() {
		return idNoticia;
	}

	/**
	 * @param idNoticia
	 */
	public void setIdNoticia(Integer idNoticia) {
		this.idNoticia = idNoticia;
	}

	/**
	 * @return
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return
	 */
	public String getCuerpo() {
		return cuerpo;
	}

	/**
	 * @param cuerpo
	 */
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	/**
	 * @return
	 */
	public String getEnlace() {
		return enlace;
	}

	/**
	 * @param enlace
	 */
	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}

	/**
	 * @return
	 */
	public byte[] getImagen() {
		return imagen;
	}

	/**
	 * @param imagen
	 */
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	/**
	 * @return
	 */
	public Boolean getPublicado() {
		return publicado;
	}

	/**
	 * @param publicado
	 */
	public void setPublicado(Boolean publicado) {
		this.publicado = publicado;
	}

	/**
	 * @return
	 */
	public LocalDateTime getFechaPublicacion() {
		return fechaPublicacion;
	}

	/**
	 * @param fechaPublicacion
	 */
	public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	/**
	 * @return
	 */
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion
	 */
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return
	 */
	public Grupo getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo
	 */
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return
	 */
	public Evento getEvento() {
		return evento;
	}

	/**
	 * @param evento
	 */
	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	/**
	 * Método toString
	 */
	@Override
	public String toString() {
		return "Noticia [titulo=" + titulo + "enlace=" + enlace + ", publicado=" + publicado + ", fechaPublicacion="
				+ fechaPublicacion + ", fechaCreacion=" + fechaCreacion + ", grupo=" + grupo + ", evento=" + evento
				+ "]";
	}
}
