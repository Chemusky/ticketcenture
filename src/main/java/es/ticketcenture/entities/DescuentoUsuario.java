package es.ticketcenture.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Entidad que representa un descuento por tipo de usuario.
 */
@Entity
@Table(name = "descuentos_usuario")
public class DescuentoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_descuento")
    private Integer idDescuento;

    @NotNull(message = "Debe seleccionar un tipo de usuario")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false, length = 255)
    private TipoUsuario tipoUsuario;

    @NotNull(message = "El porcentaje de descuento es obligatorio")
    @Min(value = 0, message = "El descuento no puede ser negativo")
    @Max(value = 100, message = "El descuento no puede superar el 100%")
    @Column(name = "descuento_porcentaje", nullable = false)
    private Integer descuentoPorcentaje;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDate fechaCreacion;

    @NotNull(message = "Debe indicar la fecha de inicio de validez")
    @Column(name = "valido_desde", nullable = false)
    private LocalDate validoDesde;

    @NotNull(message = "Debe indicar la fecha de fin de validez")
    @Column(name = "valido_hasta", nullable = false)
    private LocalDate validoHasta;

    /**
     * Constructor vacío.
     */
    public DescuentoUsuario() {}

    /**
     * Constructor de parámetros.
     *
     * @param idDescuento
     * @param tipoUsuario
     * @param descuentoPorcentaje
     * @param fechaCreacion
     * @param validoDesde
     * @param validoHasta
     */
    public DescuentoUsuario(Integer idDescuento, TipoUsuario tipoUsuario, Integer descuentoPorcentaje,
                             LocalDate fechaCreacion, LocalDate validoDesde, LocalDate validoHasta) {

        this.idDescuento = idDescuento;
        this.tipoUsuario = tipoUsuario;
        this.descuentoPorcentaje = descuentoPorcentaje;
        this.fechaCreacion = fechaCreacion;
        this.validoDesde = validoDesde;
        this.validoHasta = validoHasta;
    }

    // Getters y Setters
    
    public Integer getIdDescuento() {
        return idDescuento;
    }

    public void setIdDescuento(Integer idDescuento) {
        this.idDescuento = idDescuento;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getDescuentoPorcentaje() {
        return descuentoPorcentaje;
    }

    public void setDescuentoPorcentaje(Integer descuentoPorcentaje) {
        this.descuentoPorcentaje = descuentoPorcentaje;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "DescuentosUsuario [idDescuento=" + idDescuento +
                ", tipoUsuario=" + tipoUsuario +
                ", descuentoPorcentaje=" + descuentoPorcentaje + "]";
    }
}
