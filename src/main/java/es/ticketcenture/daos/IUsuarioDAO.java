package es.ticketcenture.daos;

import java.util.List;

import es.ticketcenture.entities.Usuario;

// TODO: Auto-generated Javadoc
/**
 * Interfaz IUSuario para implementar el UsuarioDao.
 */
public interface IUsuarioDAO {


	/**
	 * Insertar un usuario en la base de datos.
	 *
	 * @param usuario a insertar
	 */
	void insertar(Usuario usuario);

	/**
	 * Busca un usuario por su email.
	 *
	 * @param email Email del usuario
	 * @return Usuario encontrado o null si no existe
	 */
	Usuario buscarPorEmail(String email);
	
	/**
	 * Busca un usuario por su username en la bbdd.
	 *
	 * @param username Username del usuario
	 * @return Usuario encontrado o null si no existe
	 */
	Usuario buscarPorUsername(String username);


	/**
	 * Actualiza un usuario existente en la BBDD.
	 *
	 * @param usuario Usuario a actualizar
	 */
	void actualizar(Usuario usuario);

    
    
    /**
     * Este metodo nos guardara los intentos fallidos de login en la bbdd.
     *
     * @param idUsuario the id usuario
     * @param intentos the intentos
     */
    void actualizarIntentosFallidos(Integer idUsuario, int intentos);
    
    /**
     * Busca un usuario por su ID.
     * 
     * @param idUsuario Identificador del usuario
     * @return Usuario encontrado o null si no existe
     */
	Usuario buscarPorId(Integer idUsuario);
	
	/**
	 * Busca un usuario por su ID y carga sus direcciones.
	 *
	 * @param idUsuario Identificador del usuario
	 * @return Usuario con direcciones inicializadas
	 */
	Usuario buscarPorIdConDirecciones(Integer idUsuario);
	
	/**
	 * Mira si el usuario tiene un carrito con estado activo
	 * 
	 * @param idUsuario
	 * @return boolean  TRUE si hay carrito activo // FALSE si NO hay carrito activo
	 */
	boolean tieneCarritoActivo(Integer idUsuario);
	
	/**
	 * Buscar los usuarios en la base de datos al realizar un filtrado
	 * @param nombre
	 * @param email
	 * @param admin
	 * @param tipoUsuario
	 * @param activo
	 * @return
	 */
	List<Usuario> listarConFiltros(String nombre, String email, String admin, String tipoUsuario, String activo);

	/**
	 * Bloquear un usuario
	 * @param idUsuario
	 */
	void bloquear(Integer idUsuario);

	/**
	 * Desbloquear un usuario
	 * @param idUsuario
	 */
	void desbloquear(Integer idUsuario);
	
	/**
	 * Comprueba si tiene actividades relacionadas
	 * @param idUsuario
	 * @return
	 */
	boolean tieneActividades(Integer idUsuario);

	
	/**
	 * Comprueba si tiene compras el usuario
	 * @param idUsuario
	 * @return
	 */
	boolean tieneCompras(Integer idUsuario);


	/**
	 * Eliminar un usuario
	 * @param idUsuario
	 */
	void eliminar(Integer idUsuario);


	

}
