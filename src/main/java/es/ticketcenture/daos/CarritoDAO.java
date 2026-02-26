package es.ticketcenture.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ticketcenture.entities.Carrito;
import es.ticketcenture.entities.EstadoCarrito;

/**
 * En esta clase gestionaremos las operaciones contra la bbdd
 */
@Repository
public class CarritoDAO implements ICarritoDAO {

	private final SessionFactory sessionFactory;

	/**
	 * @param sessionFactory
	 */
	@Autowired
	public CarritoDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 
	 * Gestiona la sesion
	 * 
	 * @return
	 */
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Busca el carrito activo por usuario
	 */
	@Override
	public Carrito buscarCarritoActivoPorUsuario(Integer idUsuario) {
		String hql = "FROM Carrito c " + "WHERE c.usuario.idUsuario = :idUsuario " + "AND c.estado = :estado";
		return getSession().createQuery(hql, Carrito.class).setParameter("idUsuario", idUsuario)
				.setParameter("estado", EstadoCarrito.ACTIVO).uniqueResult();
	}

	/**
	 * Inserta en el carrito
	 */
	@Override
	public void insertar(Carrito carrito) {
		getSession().persist(carrito);
	}

	/**
	 * Actualiza el carrito
	 */
	@Override
	public void actualizar(Carrito carrito) {
		getSession().merge(carrito);
	}

	@Override
	public Carrito buscarCarritoActivoConTodo(Integer idUsuario) {

		String hql = "SELECT c FROM Carrito c " + "LEFT JOIN FETCH c.items i " + "LEFT JOIN FETCH i.entrada e "
				+ "LEFT JOIN FETCH e.tipoAsiento ta " + "LEFT JOIN FETCH c.usuario u "
				+ "WHERE c.usuario.idUsuario = :idUsuario " + "AND c.estado = :estado";

		return getSession().createQuery(hql, Carrito.class).setParameter("idUsuario", idUsuario)
				.setParameter("estado", EstadoCarrito.ACTIVO).uniqueResult();
	}

	@Override
	public void confirmarCarrito(Carrito carrito) {
		getSession().merge(carrito);
	}

	@Override
	public Carrito buscarPorId(Integer idCarrito) {
		return getSession().get(Carrito.class, idCarrito);
	}

	@Override
	public boolean entradaEnOtroCarritoActivo(Integer idEntrada, Integer idCarritoActual) {

		String hql = "SELECT COUNT(ci) FROM CarritoItem ci " + "WHERE ci.entrada.idEntrada = :idEntrada "
				+ "AND ci.carrito.idCarrito <> :idCarritoActual " + "AND ci.carrito.estado = :estado";

		Long count = getSession().createQuery(hql, Long.class).setParameter("idEntrada", idEntrada)
				.setParameter("idCarritoActual", idCarritoActual).setParameter("estado", EstadoCarrito.ACTIVO)
				.uniqueResult();

		return count > 0;
	}

	@Override
	public void eliminarCarrito(Carrito carrito) {
		getSession().remove(carrito);

	}

	@Override
	public List<Carrito> buscarCarritosConfirmadosPorUsuario(Integer idUsuario) {

		String hql = "SELECT DISTINCT c FROM Carrito c " + "LEFT JOIN FETCH c.items i " + "LEFT JOIN FETCH i.entrada e "
				+ "LEFT JOIN FETCH e.evento ev " + "LEFT JOIN FETCH e.tipoAsiento ta " + "LEFT JOIN FETCH c.usuario u "
				+ "LEFT JOIN FETCH u.direcciones d " + "WHERE c.usuario.idUsuario = :idUsuario "
				+ "AND c.estado = :estado " + "ORDER BY c.fechaCompra DESC";

		return getSession().createQuery(hql, Carrito.class).setParameter("idUsuario", idUsuario)
				.setParameter("estado", EstadoCarrito.CONFIRMADO).getResultList();
	}

	/**
	 * Buscar los usuarios que han comprado eventos
	 */
	@Override
	public List<Object[]> obtenerCompradoresPorEvento(Integer idEvento) {

		String hql = "SELECT u.nombre, u.telefono, e.fila, e.asiento " + "FROM Carrito c " + "JOIN c.items ci "
				+ "JOIN ci.entrada e " + "JOIN c.usuario u " + "WHERE e.evento.idEvento = :idEvento "
				+ "AND c.estado = :estado " + "ORDER BY u.nombre ASC";

		return getSession().createQuery(hql, Object[].class).setParameter("idEvento", idEvento)
				.setParameter("estado", EstadoCarrito.CONFIRMADO).getResultList();

	}

}
