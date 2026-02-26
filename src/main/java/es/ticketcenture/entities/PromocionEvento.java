package es.ticketcenture.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa una promoción asociada a un evento.
 *
 * NOTA IMPORTANTE:
 * La base de datos define una restricción única COMPUESTA:
 *      UNIQUE (id_evento, codigo)
 *
 * Esto significa que:
 *  - Un mismo evento NO puede tener dos promociones con el mismo código.
 *  - Pero el mismo código SÍ puede repetirse en eventos distintos.
 *
 * Esta restricción NO puede ponerse en un atributo individual,
 * porque afecta a DOS columnas a la vez.
 *
 * Por eso se declara en @Table mediante @UniqueConstraint.
 */
@Entity
@Table(
    name = "promociones_evento",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_evento", "codigo"})
)
public class PromocionEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promocion")
    private Integer idPromocion;

    @NotNull(message = "Debe seleccionar un evento")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    @NotBlank(message = "El código promocional es obligatorio")
    @Size(max = 100)
    @Column(name = "codigo", nullable = false, length = 100)
    private String codigo;

    @NotNull(message = "Debe indicar el descuento en euros")
    @DecimalMin(value = "0.0", message = "El descuento no puede ser negativo")
    @Column(name = "descuento_euros", nullable = false)
    private BigDecimal descuentoEuros;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDate fechaCreacion;

    @NotNull(message = "Debe indicar la fecha de inicio de validez")
    @Column(name = "valido_desde", nullable = false)
    private LocalDate validoDesde;

    @NotNull(message = "Debe indicar la fecha de fin de validez")
    @Column(name = "valido_hasta", nullable = false)
    private LocalDate validoHasta;

    /**
     * Constructor vacio
     */
    public PromocionEvento() {}

    /**
     * Constructor parametros 
     * 
     * @param idPromocion
     * @param evento
     * @param codigo
     * @param descuentoEuros
     * @param fechaCreacion
     * @param validoDesde
     * @param validoHasta
     */
    public PromocionEvento(Integer idPromocion, Evento evento, String codigo,
                           BigDecimal descuentoEuros, LocalDate fechaCreacion,
                           LocalDate validoDesde, LocalDate validoHasta) {

        this.idPromocion = idPromocion;
        this.evento = evento;
        this.codigo = codigo;
        this.descuentoEuros = descuentoEuros;
        this.fechaCreacion = fechaCreacion;
        this.validoDesde = validoDesde;
        this.validoHasta = validoHasta;
    }
    /* getters y setters */
    
    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getDescuentoEuros() {
        return descuentoEuros;
    }

    public void setDescuentoEuros(BigDecimal descuentoEuros) {
        this.descuentoEuros = descuentoEuros;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDate getValidoDesde() {
        return validoDesde;
    }

    public void setValidoDesde(LocalDate validoDesde) {
        this.validoDesde = validoDesde;
    }

    public LocalDate getValidoHasta() {
        return validoHasta;
    }

    public void setValidoHasta(LocalDate validoHasta) {
        this.validoHasta = validoHasta;
    }
    
    /**
     * Metodo toString
     */
    @Override
    public String toString() {
        return "PromocionesEvento [idPromocion=" + idPromocion +
                ", codigo=" + codigo +
                ", descuentoEuros=" + descuentoEuros + "]";
    }
}
