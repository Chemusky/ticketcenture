package es.ticketcenture.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entidad que representa una imagen de la galería asociada a un evento musical.
 */
@Entity
@Table(name = "galeria_eventos")
public class GaleriaEvento {

    //Declaración de atributos
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen")
    private Integer idImagen;

    //Relación con el evento al que pertenece la imagen
    @NotNull(message = "La imagen debe pertenecer a un evento")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    //Imagen almacenada en la BBDD
    @NotNull(message = "La imagen no puede estar vacía")
    @Lob
    @Column(name = "imagen", nullable = false)
    private byte[] imagen;


    /**
     * Constructor vacío
     */
    public GaleriaEvento() {}


    /**
     * Constructor de parámetros
     *
     * @param evento
     * @param imagen
     */
    public GaleriaEvento(Evento evento, byte[] imagen) {
        this.evento = evento;
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

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
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
        return "GaleriaEvento [idImagen=" + idImagen +
                ", evento=" + (evento != null ? evento.getIdEvento() : null) + "]";
    }
}
