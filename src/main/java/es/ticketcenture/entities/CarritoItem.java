package es.ticketcenture.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Entidad que representa la configuración de butacas para un evento musical.
 *
 */
@Entity
@Table(name = "carrito_items")
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Integer idItem;

    @NotNull(message = "El item debe pertenecer a un carrito")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_carrito", nullable = false)
    private Carrito carrito;

    @NotNull(message = "Debe asociarse una entrada al item")
    @OneToOne(fetch = FetchType.EAGER) // ← CORRECCIÓN IMPORTANTE
    @JoinColumn(name = "id_entrada", nullable = false)
    private Entrada entrada;

    @NotNull(message = "El precio de la entrada es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true) // ← CORRECCIÓN: la BD sí permite 0.00
    @Column(name = "precio_entrada", nullable = false)
    private BigDecimal precioEntrada;

    @Column(name = "promocion_evento") // ← CORRECCIÓN: nombre exacto de la columna
    private BigDecimal descuentoPromocion;

    @NotNull(message = "El precio total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true) // ← CORRECCIÓN: coherente con BD
    @Column(name = "precio_total_entrada", nullable = false)
    private BigDecimal precioTotalEntrada;

    /**
     * Constructor vacios
     */
    public CarritoItem() {}

    /**
     * Constructor parametros
     * 
     * @param idItem
     * @param carrito
     * @param entrada
     * @param precioEntrada
     * @param descuentoPromocion
     * @param precioTotalEntrada
     */
    public CarritoItem(Integer idItem, Carrito carrito, Entrada entrada,
                       BigDecimal precioEntrada, BigDecimal descuentoPromocion,
                       BigDecimal precioTotalEntrada) {

        this.idItem = idItem;
        this.carrito = carrito;
        this.entrada = entrada;
        this.precioEntrada = precioEntrada;
        this.descuentoPromocion = descuentoPromocion;
        this.precioTotalEntrada = precioTotalEntrada;
    }

    // Getters y Setters

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public BigDecimal getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(BigDecimal precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    public BigDecimal getDescuentoPromocion() {
        return descuentoPromocion;
    }

    public void setDescuentoPromocion(BigDecimal descuentoPromocion) {
        this.descuentoPromocion = descuentoPromocion;
    }

    public BigDecimal getPrecioTotalEntrada() {
        return precioTotalEntrada;
    }

    public void setPrecioTotalEntrada(BigDecimal precioTotalEntrada) {
        this.precioTotalEntrada = precioTotalEntrada;
    }

    /**
     * Metodo toString
     */
    @Override
    public String toString() {
        return "CarritoItem [idItem=" + idItem +
                ", entrada=" + (entrada != null ? entrada.getIdEntrada() : null) +
                ", precioTotalEntrada=" + precioTotalEntrada + "]";
    }
}
