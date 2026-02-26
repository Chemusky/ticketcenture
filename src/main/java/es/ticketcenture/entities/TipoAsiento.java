package es.ticketcenture.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Entidad que representa un tipo de asiento disponible en un evento musical.
 */
@Entity
@Table(name = "tipo_asientos")
public class TipoAsiento {

    //Declaración de atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo")
    private Integer idTipo;

    @NotBlank(message = "El nombre del tipo de asiento es obligatorio")
    @Size(max = 100, message = "El nombre del tipo no puede superar los 100 caracteres")
    @Column(name = "nombre_tipo", nullable = false, length = 100)
    private String nombreTipo;

    /**
     * Constructor vacío
     */
    public TipoAsiento() {}

    /**
     * Constructor de parámetros
     */
    public TipoAsiento(Integer idTipo, String nombreTipo) {
        this.idTipo = idTipo;
        this.nombreTipo = nombreTipo;
    }

    /**
     * Getters y Setters
     */

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    /**
     * Método toString
     */
    @Override
    public String toString() {
        return "TipoAsiento [idTipo=" + idTipo +
                ", nombreTipo=" + nombreTipo + "]";
    }
}
