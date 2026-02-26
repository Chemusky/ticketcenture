package es.ticketcenture.daos;

import java.util.List;
import es.ticketcenture.entities.Favorito;

/**
 * Interfaz con los métodos para la gestión de Favoritos
 * @author jose.m.romero.aja
 *
 */
public interface IFavoritoDAO {

	/**
	 * Insertar favorito en la Base de datos
	 * @param favorito
	 */
    void insertar(Favorito favorito);

    /**
     * Eliminar un favorito de la Base de Datos
     * @param favorito
     */
    void eliminar(Favorito favorito);

    /**
     * Buscar un grupo favorito por su usuario y por grupo
     * @param idUsuario
     * @param idGrupo
     * @return
     */
    Favorito buscarPorUsuarioYGrupo(Integer idUsuario, Integer idGrupo);

    /**
     * Buscar por usuario
     * @param idUsuario
     * @return
     */
    List<Favorito> buscarPorUsuario(Integer idUsuario);
}
