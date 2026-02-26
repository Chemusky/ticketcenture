package es.ticketcenture.daos;

import es.ticketcenture.entities.EstadoCarrito;
import es.ticketcenture.entities.TipoUsuario;
import es.ticketcenture.entities.Usuario;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * En esta clase gestionaremos las operaciones contra la bbdd
 */
@Repository
public class UsuarioDAO implements IUsuarioDAO {

	/** SessionFactory de Hibernate */
	private SessionFactory sessionFactory;

	/**
	 * Instancia la clase.
	 *
	 * @param sessionFactory
	 */
	@Autowired
	public UsuarioDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Obtiene la sesion
	 *
	 * @return Sesion
	 */
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Busca un usuario por su email.
	 *
	 * @param email Email del usuario
	 * @return Usuario encontrado o null si no existe
	 */
	/*
	 * @Override public Usuario buscarPorEmail(String email) { try { Query<Usuario>
	 * query = getSession().createQuery("FROM Usuario u WHERE u.email = :email",
	 * Usuario.class); query.setParameter("email", email); return
	 * query.getSingleResult(); } catch (NoResultException e) { return null; } }
	 */

	/**
	 * Actualiza un usuario existente en la BBDD.
	 *
	 * @param usuario Usuario a actualizar
	 */
	@Override
	public void actualizar(Usuario usuario) {
		getSession().merge(usuario);
	}

	/**
	 * Inserta un usuario en la BBDD gracias al .save de Hibernate.
	 *
	 * @param usuario Usuario a insertar
	 */
	public void insertar(Usuario usuario) {
		getSession().persist(usuario);

	}

	/**
	 * Metodo que busca en la bbdd el usuario por mail
	 */
	@Override
	public Usuario buscarPorEmail(String email) {

		String consulta = "FROM Usuario u WHERE u.email = :email";

		Session session = getSession();

		Query<Usuario> query = session.createQuery(consulta, Usuario.class);

		query.setParameter("email", email);

		Usuario usuario = query.uniqueResult();

		return usuario;
	}

	/**
	 * Busca un usuario por su username en la bbdd.
	 *
	 * @param username Username del usuario
	 * @return Usuario encontrado o null si no existe
	 */
	public Usuario buscarPorUsername(String username) {

		String consulta = "FROM Usuario u WHERE u.username = :username";

		Session session = getSession();

		Query<Usuario> query = session.createQuery(consulta, Usuario.class);

		query.setParameter("username", username);

		return query.uniqueResult();
	}

	@Override
	public void actualizarIntentosFallidos(Integer idUsuario, int intentos) {
		String consulta = "UPDATE Usuario u SET u.intentosFallidos = :intentos WHERE u.idUsuario = :id";
		Query query = getSession().createQuery(consulta);
		query.setParameter("intentos", intentos);
		query.setParameter("id", idUsuario);
		query.executeUpdate();

	}

	/**
	 * Busca un usuario por su ID.
	 * 
	 * @param idUsuario Identificador del usuario
	 * @return Usuario encontrado o null si no existe
	 */
	@Override
	public Usuario buscarPorId(Integer idUsuario) {
		return getSession().get(Usuario.class, idUsuario);
	}

	/**
	 * Buscar las direcciones por el id
	 */
	@Override
	public Usuario buscarPorIdConDirecciones(Integer idUsuario) {
		String hql = "SELECT u FROM Usuario u LEFT JOIN FETCH u.direcciones WHERE u.idUsuario = :id";
		Query<Usuario> query = getSession().createQuery(hql, Usuario.class);
		query.setParameter("id", idUsuario);
		return query.uniqueResult();
	}

	/**
	 * Mira si el usuario tiene carrito activo TRUE si hay carrito activo // FALSE
	 * si NO hay carrito activo
	 */
	@Override
	public boolean tieneCarritoActivo(Integer idUsuario) {

		String hql = "SELECT COUNT(c) " + "FROM Carrito c " + "WHERE c.usuario.idUsuario = :idUsuario "
				+ "AND c.estado = :estadoActivo";

		Long count = getSession().createQuery(hql, Long.class).setParameter("idUsuario", idUsuario)
				.setParameter("estadoActivo", EstadoCarrito.ACTIVO).uniqueResult();

		if (count != null && count > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Obtiene el listado de usuarios en función al filtro establecido
	 */
	@Override
	public List<Usuario> listarConFiltros(String nombre, String email, String rol, String tipoCliente, String estado) {

		StringBuilder hql = new StringBuilder("FROM Usuario u WHERE 1=1 ");

		if (nombre != null && !nombre.isEmpty()) {
			hql.append("AND u.nombre LIKE :nombre ");
		}

		if (email != null && !email.isEmpty()) {
			hql.append("AND u.email LIKE :email ");
		}

		if (rol != null && !rol.isEmpty()) {
			hql.append("AND u.admin = :admin ");
		}

		if (tipoCliente != null && !tipoCliente.isEmpty()) {
			hql.append("AND u.tipoUsuario = :tipoUsuario ");
		}

		if (estado != null && !estado.isEmpty()) {
			hql.append("AND u.activo = :activo ");
		}

		Query<Usuario> query = getSession().createQuery(hql.toString(), Usuario.class);

		if (nombre != null && !nombre.isEmpty()) {
			query.setParameter("nombre", "%" + nombre + "%");
		}

		if (email != null && !email.isEmpty()) {
			query.setParameter("email", "%" + email + "%");
		}

		if (rol != null && !rol.isEmpty()) {
			boolean esAdmin = rol.equals("ADMIN");
			query.setParameter("admin", esAdmin);
		}

		if (tipoCliente != null && !tipoCliente.isEmpty()) {
			query.setParameter("tipoUsuario", TipoUsuario.valueOf(tipoCliente));
		}

		if (estado != null && !estado.isEmpty()) {
			boolean activoBool = estado.equals("ACTIVO");
			query.setParameter("activo", activoBool);
		}

		return query.list();
	}

	/**
	 * Bloquea al usuario en cuestión
	 */
	@Override
	public void bloquear(Integer idUsuario) {
		String hql = "UPDATE Usuario u SET u.activo = false WHERE u.idUsuario = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", idUsuario);
		query.executeUpdate();
	}

	/**
	 * Desbloqueda al usuario en cuestión
	 */
	@Override
	public void desbloquear(Integer idUsuario) {
		String hql = "UPDATE Usuario u SET u.activo = true WHERE u.idUsuario = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", idUsuario);
		query.executeUpdate();
	}

	/**
	 * Comprueba si el usuario tiene actividades relacionadas
	 */
	@Override
	public boolean tieneActividades(Integer idUsuario) {
		String hql = "SELECT COUNT(a) FROM Actividad a WHERE a.usuario.idUsuario = :id";
		Long count = getSession().createQuery(hql, Long.class).setParameter("id", idUsuario).uniqueResult();
		return count != null && count > 0;
	}

	/**
	 * Comprueba si tiene compras el usuario
	 */
	@Override
	public boolean tieneCompras(Integer idUsuario) {
		String hql = "SELECT COUNT(c) FROM Carrito c WHERE c.usuario.idUsuario = :id";
		Long count = getSession().createQuery(hql, Long.class).setParameter("id", idUsuario).uniqueResult();
		return count != null && count > 0;
	}

	/**
	 * Elimina al usuario en cuestión
	 */
	@Override
	public void eliminar(Integer idUsuario) {
		Usuario usuario = getSession().get(Usuario.class, idUsuario);
		if (usuario != null) {
			getSession().remove(usuario);
		}
	}

}
