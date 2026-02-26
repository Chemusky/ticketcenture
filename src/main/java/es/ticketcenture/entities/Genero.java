package es.ticketcenture.entities;

import javax.persistence.*;

/**
 * Entidad que representa un género musical en la BBDD.
 */
@Entity
@Table(name = "generos")
public class Genero {

    /**
     * Declaración de atributos.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero")
    private Integer idGenero;

    @Column(name = "nombre_genero", nullable = false, length = 50)
    private String nombreGenero;


    /**
     * Constructor vacío
     */
    public Genero() {}


    /**
     * Constructor de parámetros
     * 
     * @param idGenero ID del género
     * @param nombreGenero Nombre del género musical
     */
    public Genero(Integer idGenero, String nombreGenero) {
        this.idGenero = idGenero;
        this.nombreGenero = nombreGenero;
    }


    /**
     * Getters y Setters
     */

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }


    /**
     * Método toString
     */
    @Override
    public String toString() {
        return "Genero [idGenero=" + idGenero + ", nombreGenero=" + nombreGenero + "]";
    }
}
