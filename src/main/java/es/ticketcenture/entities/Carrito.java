package es.ticketcenture.entities;
 
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
 
/**
* Entidad que representa la configuración de butacas para un evento musical.
*
*/
@Entity
@Table(name = "carrito")
public class Carrito {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private Integer idCarrito;
 
    @NotNull(message = "El carrito debe pertenecer a un usuario")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
 
    @NotNull(message = "El estado del carrito es obligatorio")
    @Enumerated(EnumType.STRING) // ← CORRECCIÓN IMPORTANTE
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoCarrito estado;
 
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
 
    @Column(name = "fecha_compra")
    private LocalDateTime fechaCompra;
 
    @Column(name = "direccion_envio", length = 500) // ← CORRECCIÓN
    private String direccionEnvio;
 
    @Column(name = "direccion_facturacion", length = 500) // ← CORRECCIÓN
    private String direccionFacturacion;
 
    @Column(name = "descuento_aplicado")
    private BigDecimal descuentoAplicado;
 
    @Column(name = "total")
    private BigDecimal total;
 
    @OneToMany(
    	    mappedBy = "carrito",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true,
    	    fetch = FetchType.LAZY
    	)
    	private Set<CarritoItem> items = new HashSet<>();
 
 
    /**
     * Constructor vacio
     */
    public Carrito() {}
 
    /**
     * Constructor parametros
     * 
     * @param idCarrito
     * @param usuario
     * @param estado
     * @param fechaCreacion
     * @param fechaCompra
     * @param direccionEnvio
     * @param direccionFacturacion
     * @param descuentoAplicado
     * @param total
     * @param items
     */
    public Carrito(Integer idCarrito, Usuario usuario, EstadoCarrito estado,
                   LocalDateTime fechaCreacion, LocalDateTime fechaCompra,
                   String direccionEnvio, String direccionFacturacion,
                   BigDecimal descuentoAplicado, BigDecimal total,
                   Set<CarritoItem> items) {
 
        this.idCarrito = idCarrito;
        this.usuario = usuario;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaCompra = fechaCompra;
        this.direccionEnvio = direccionEnvio;
        this.direccionFacturacion = direccionFacturacion;
        this.descuentoAplicado = descuentoAplicado;
        this.total = total;
        this.items = items;
    }
 
    // Getters y Setters
 
    public Integer getIdCarrito() {
        return idCarrito;
    }
 
    public void setIdCarrito(Integer idCarrito) {
        this.idCarrito = idCarrito;
    }
 
    public Usuario getUsuario() {
        return usuario;
    }
 
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
 
    public EstadoCarrito getEstado() {
        return estado;
    }
 
    public void setEstado(EstadoCarrito estado) {
        this.estado = estado;
    }
 
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
 
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
 
    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }
 
    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
 
    public String getDireccionEnvio() {
        return direccionEnvio;
    }
 
    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }
 
    public String getDireccionFacturacion() {
        return direccionFacturacion;
    }
 
    public void setDireccionFacturacion(String direccionFacturacion) {
        this.direccionFacturacion = direccionFacturacion;
    }
 
    public BigDecimal getDescuentoAplicado() {
        return descuentoAplicado;
    }
 
    public void setDescuentoAplicado(BigDecimal descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }
 
    public BigDecimal getTotal() {
        return total;
    }
 
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
 
    public Set<CarritoItem> getItems() {
        return items;
    }
 
    public void setItems(Set<CarritoItem> items) {
        this.items = items;
    }
 
    /**
     * Metodo toString
     */
    @Override
    public String toString() {
        return "Carrito [idCarrito=" + idCarrito +
                ", usuario=" + (usuario != null ? usuario.getIdUsuario() : null) +
                ", estado=" + estado +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaCompra=" + fechaCompra +
                ", total=" + total + "]";
    }

    
    public void recalcularTotal() {
        if (items == null || items.isEmpty()) {
            this.total = BigDecimal.ZERO;
            return;
        }

        this.total = items.stream()
                .map(CarritoItem::getPrecioTotalEntrada)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
