package es.ticketcenture.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Entidad que representa un municipio donde se celebra un evento musical.
 */
@Entity
@Table(name = "municipios")
public class Municipio {
	
    //Declaración de atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipio")
    private Integer idMunicipio;

    @NotBlank(message = "El nombre del municipio es obligatorio")
    @Size(max = 100, message = "El nombre del municipio no puede superar los 100 caracteres")
    @Column(name = "nombre_municipio", nullable = false, length = 100)
    private String nombreMunicipio;
    
    /**
     * Constructor vacío
     */
    public Municipio() {}

    /** 
     * Constructor de parámetros
     * 
     * @param idMunicipio
     * @param nombreMunicipio
     */
    public Municipio(Integer idMunicipio, String nombreMunicipio) {
        this.idMunicipio = idMunicipio;
        this.nombreMunicipio = nombreMunicipio;
    }
    
    /**
     * Getters y Setters
     */
    
    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }
    
    /**
     * Método toString
     */
    @Override
    public String toString() {
        return "Municipio [idMunicipio=" + idMunicipio +
                ", nombreMunicipio=" + nombreMunicipio + "]";
    }
}
