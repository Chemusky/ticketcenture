package es.ticketcenture.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Entidad que representa la configuración de butacas para un evento musical.
 */
@Entity
@Table(name = "butacas")
public class Butaca {

    //Declaración de atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_butaca")
    private Integer idButaca;

    //Relación con el evento musical
    @NotNull(message = "La butaca debe pertenecer a un evento")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    //Relación con el tipo de asiento
    @NotNull(message = "Debe seleccionar un tipo de asiento")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo", nullable = false)
    private TipoAsiento tipoAsiento;

    @NotNull(message = "El número de filas es obligatorio")
    @Min(value = 1, message = "El número de filas debe ser mayor o igual a 1")
    @Column(name = "numero_filas", nullable = false)
    private int numeroFilas;

    @NotNull(message = "El número de asientos es obligatorio")
    @Min(value = 1, message = "El número de asientos debe ser mayor o igual a 1")
    @Column(name = "numero_asientos", nullable = false)
    private int numeroAsientos;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor que 0")
    @Column(name = "precio", nullable = false)
    private BigDecimal precio;


    /**
     * Constructor vacío
     */
    public Butaca() {}


    /**
     * Constructor de parámetros
     *
     * @param idButaca
     * @param evento
     * @param tipoAsiento
     * @param numeroFilas
     * @param numeroAsientos
     * @param precio
     */
    public Butaca(Integer idButaca, Evento evento, TipoAsiento tipoAsiento,
                  int numeroFilas, int numeroAsientos, BigDecimal precio) {

        this.idButaca = idButaca;
        this.evento = evento;
        this.tipoAsiento = tipoAsiento;
        this.numeroFilas = numeroFilas;
        this.numeroAsientos = numeroAsientos;
        this.precio = precio;
    }


    /**
     * Getters y Setters
     */

    public Integer getIdButaca() {
        return idButaca;
    }

    public void setIdButaca(Integer idButaca) {
        this.idButaca = idButaca;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public int getNumeroFilas() {
        return numeroFilas;
    }

    public void setNumeroFilas(int numeroFilas) {
        this.numeroFilas = numeroFilas;
    }

    public int getNumeroAsientos() {
        return numeroAsientos;
    }

    public void setNumeroAsientos(int numeroAsientos) {
        this.numeroAsientos = numeroAsientos;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }


    /**
     * Método toString
     */
    @Override
    public String toString() {
        return "Butaca [idButaca=" + idButaca +
                ", evento=" + (evento != null ? evento.getIdEvento() : null) +
                ", tipoAsiento=" + (tipoAsiento != null ? tipoAsiento.getIdTipo() : null) +
                ", numeroFilas=" + numeroFilas +
                ", numeroAsientos=" + numeroAsientos +
                ", precio=" + precio + "]";
    }
}
