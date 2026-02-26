package es.ticketcenture.entities;

import javax.persistence.*;

/**
 * Entidad que representa una imagen de la galería asociada a un grupo musical.
 */
@Entity
@Table(name = "galeria_grupos")
public class GaleriaGrupo {

    /**
     * Declaración de atributos
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen")
    private Integer idImagen;

    /**
     * Relación con el grupo al que pertenece la imagen
     */
    @ManyToOne
    @JoinColumn(name = "id_grupo", nullable = false)
    private Grupo grupo;

    /**
     * Imagen almacenada en la BBDD
     */
    @Lob
    @Column(nullable = false)
    private byte[] imagen;


    /**
     * Constructor vacío
     */
    public GaleriaGrupo() {}


    /**
     * Constructor de parámetros
     *
     * @param grupo Grupo al que pertenece la imagen
     * @param imagen Contenido binario de la imagen
     */
    public GaleriaGrupo(Grupo grupo, byte[] imagen) {
        this.grupo = grupo;
        this.imagen = imagen;
    }


    /**
     * Getters y Setters
     */

    public Integer getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Integer idImagen) {
        this.idImagen = idImagen;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }


    /**
     * Método toString
     */
    @Override
    public String toString() {
        return "GaleriaGrupo [idImagen=" + idImagen + ", grupo=" +
                (grupo != null ? grupo.getIdGrupo() : null) + "]";
    }
}
