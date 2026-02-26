package es.ticketcenture.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa una entrada individual para un evento musical.
 */
@Entity
@Table(name = "entradas")
public class Entrada {

    //Declaración de atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrada")
    private Integer idEntrada;

    // Relación con el evento musical
    @NotNull(message = "La entrada debe pertenecer a un evento")
    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    //Relación con el usuario que compra la entrada
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    //Relación con el tipo de asiento
    @NotNull(message = "Debe seleccionar un tipo de asiento")
    @ManyToOne
    @JoinColumn(name = "id_tipo", nullable = false)
    private TipoAsiento tipoAsiento;

    @Column(name = "fecha_compra")
    private LocalDateTime fechaCompra;

    @NotNull(message = "La fila es obligatoria")
    @Min(value = 1, message = "La fila debe ser mayor o igual a 1")
    @Column(nullable = false)
    private int fila;

    @NotNull(message = "El número de asiento es obligatorio")
    @Min(value = 1, message = "El asiento debe ser mayor o igual a 1")
    @Column(nullable = false)
    private int asiento;

    @NotNull(message = "El estado de la entrada es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoEntrada estado;

    // Marca temporal cuando la entrada está reservada en carrito
    @Column(name = "reservada_carrito")
    private LocalDateTime reservadaCarrito;


    /**
     * Constructor vacío
     */
    public Entrada() {}


    /**
     * Constructor de parámetros
     *
     * @param idEntrada
     * @param evento
     * @param usuario
     * @param tipoAsiento
     * @param fila
     * @param asiento
     * @param estado
     * @param reservadaCarrito
     */
    public Entrada(Integer idEntrada, Evento evento, Usuario usuario,
                   TipoAsiento tipoAsiento, int fila, int asiento,
                   TipoEntrada estado, LocalDateTime reservadaCarrito) {

        this.idEntrada = idEntrada;
        this.evento = evento;
        this.usuario = usuario;
        this.tipoAsiento = tipoAsiento;
        this.fila = fila;
        this.asiento = asiento;
        this.estado = estado;
        this.reservadaCarrito = reservadaCarrito;
    }

    /**
     * Getters y Setters
     */

    public Integer getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(Integer idEntrada) {
        this.idEntrada = idEntrada;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }

    public TipoEntrada getEstado() {
        return estado;
    }

    public void setEstado(TipoEntrada estado) {
        this.estado = estado;
    }

    public LocalDateTime getReservadaCarrito() {
        return reservadaCarrito;
    }

    public void setReservadaCarrito(LocalDateTime reservadaCarrito) {
        this.reservadaCarrito = reservadaCarrito;
    }


    /**
     * Método toString
     */
    @Override
    public String toString() {
        return "Entrada [idEntrada=" + idEntrada +
                ", evento=" + (evento != null ? evento.getIdEvento() : null) +
                ", usuario=" + (usuario != null ? usuario.getIdUsuario() : null) +
                ", tipoAsiento=" + (tipoAsiento != null ? tipoAsiento.getIdTipo() : null) +
                ", fila=" + fila +
                ", asiento=" + asiento +
                ", estado=" + estado + "]";
    }
}
