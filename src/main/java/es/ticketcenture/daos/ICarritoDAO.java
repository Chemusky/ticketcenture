package es.ticketcenture.daos;

import java.util.List;

import es.ticketcenture.entities.Carrito;

/**
 * Interfaz para ICarritoDAO.
 */
public interface ICarritoDAO {

    /**
     * Buscar carrito activo por usuario.
     *
     * @param idUsuario the id usuario
     * @return the carrito
     */
    Carrito buscarCarritoActivoPorUsuario(Integer idUsuario);

    /**
     * Insertar.
     *
     * @param carrito the carrito
     */
    void insertar(Carrito carrito);

    /**
     * Actualizar.
     *
     * @param carrito the carrito
     */
    void actualizar(Carrito carrito);

	Carrito buscarCarritoActivoConTodo(Integer idUsuario);

	void confirmarCarrito(Carrito carrito);
	
	/**
	 * Buscar el carrito por us Id
	 * @param idCarrito
	 * @return
	 */
	Carrito buscarPorId(Integer idCarrito);

	/**
	 * Busca la entrada en un carrito activo
	 * @param idEntrada
	 * @param idCarritoActual
	 * @return
	 */
	boolean entradaEnOtroCarritoActivo(Integer idEntrada, Integer idCarritoActual);
	
	/**
	 * Elimina el carrito de la compra
	 * @param carrito
	 */
	void eliminarCarrito(Carrito carrito);
	
	/**
	 * Buscar el carrito confirmado por el usuario
	 * @param idUsuario
	 * @return
	 */
	List<Carrito> buscarCarritosConfirmadosPorUsuario(Integer idUsuario);
	
	/**
	 * Buscar los usuarios que han comprado el evento
	 * @param idEvento
	 * @return
	 */
	List<Object[]> obtenerCompradoresPorEvento(Integer idEvento);



}
