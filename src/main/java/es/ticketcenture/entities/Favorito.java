package es.ticketcenture.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa un favorito marcado por un usuario hacia un grupo musical.
 */
@Entity
@Table(
    name = "favoritos",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_usuario", "id_grupo"})
)
public class Favorito {

    /**
     * Declaración de atributos
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_favorito")
    private Integer idFavorito;

    /**
     * Relación con el usuario que marca el favorito
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Relación con el grupo marcado como favorito
     */
    @ManyToOne
    @JoinColumn(name = "id_grupo", nullable = false)
    private Grupo grupo;

    /**
     * Fecha en la que se marcó como favorito
     * (se rellena automáticamente en la BBDD)
     */
    @Column(name = "fecha", insertable = false, updatable = false)
    private LocalDateTime fecha;


    /**
     * Constructor vacío
     */
    public Favorito() {}


    /**
     * Constructor de parámetros
     *
     * @param usuario Usuario que marca el favorito
     * @param grupo Grupo marcado como favorito
     */
    public Favorito(Usuario usuario, Grupo grupo) {
        this.usuario = usuario;
        this.grupo = grupo;
    }


    /**
     * Getters y Setters
     */

    public Integer getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(Integer idFavorito) {
        this.idFavorito = idFavorito;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }


    /**
     * Método toString
     */
    @Override
    public String toString() {
        return "Favorito [idFavorito=" + idFavorito +
                ", usuario=" + (usuario != null ? usuario.getIdUsuario() : null) +
                ", grupo=" + (grupo != null ? grupo.getIdGrupo() : null) +
                ", fecha=" + fecha + "]";
    }
}
