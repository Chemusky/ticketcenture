package es.ticketcenture.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un evento musical en la BBDD.
 */
@Entity
@Table(name = "eventos_musicales")
public class Evento {

    //Declaración de atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Integer idEvento;

    @NotBlank(message = "El nombre del evento es obligatorio")
    @Size(max = 200, message = "El nombre del evento no puede superar los 200 caracteres")
    @Column(name = "nombre_evento", nullable = false, length = 200)
    private String nombreEvento;

    @Column(nullable = false)
    private boolean activo;

    @Lob
    @Column(name = "img_principal")
    private byte[] imagenPrincipal;

    //Relación con municipio
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_municipio")
    @NotNull(message = "Debe seleccionar un municipio")
    private Municipio municipio;

    //Relación con grupo musical
    @NotNull(message = "Debe seleccionar un grupo musical")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_grupo", nullable = false)
    private Grupo grupo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "La fecha del evento es obligatoria")
    @Column(name = "fecha_evento", nullable = false)
    private LocalDateTime fechaEvento;

    @NotBlank(message = "La descripción del evento es obligatoria")
    @Column(name = "descripcion_evento", nullable = false, columnDefinition = "TEXT")
    private String descripcionEvento;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    //Relación con galería de imágenes del evento
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GaleriaEvento> galeria = new ArrayList<>();

    //Relación con configuración de butacas
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Butaca> butacas = new ArrayList<>();

    //Relación con entradas generadas
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entrada> entradas = new ArrayList<>();

    /**
     * Constructor vacío
     */
    public Evento() {}


    /**
     * Constructor de parámetros
     *
     * @param idEvento
     * @param nombreEvento
     * @param activo
     * @param imagenPrincipal
     * @param municipio
     * @param grupo
     * @param fechaEvento
     * @param descripcionEvento
     * @param fechaCreacion
     */
    public Evento(Integer idEvento, String nombreEvento, boolean activo, byte[] imagenPrincipal,
                         Municipio municipio, Grupo grupo, LocalDateTime fechaEvento,
                         String descripcionEvento, LocalDateTime fechaCreacion) {

        this.idEvento = idEvento;
        this.nombreEvento = nombreEvento;
        this.activo = activo;
        this.imagenPrincipal = imagenPrincipal;
        this.municipio = municipio;
        this.grupo = grupo;
        this.fechaEvento = fechaEvento;
        this.descripcionEvento = descripcionEvento;
        this.fechaCreacion = fechaCreacion;
    }


    /**
     * Getters y Setters
     */

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public byte[] getImagenPrincipal() {
        return imagenPrincipal;
    }

    public void setImagenPrincipal(byte[] imagenPrincipal) {
        this.imagenPrincipal = imagenPrincipal;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public LocalDateTime getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDateTime fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<GaleriaEvento> getGaleria() {
        return galeria;
    }

    public void setGaleria(List<GaleriaEvento> galeria) {
        this.galeria = galeria;
    }

    public List<Butaca> getButacas() {
        return butacas;
    }

    public void setButacas(List<Butaca> butacas) {
        this.butacas = butacas;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }


    /**
     * Método toString
     */
    @Override
    public String toString() {
        return "EventoMusical [idEvento=" + idEvento +
                ", nombreEvento=" + nombreEvento +
                ", activo=" + activo +
                ", fechaEvento=" + fechaEvento + "]";
    }
}
