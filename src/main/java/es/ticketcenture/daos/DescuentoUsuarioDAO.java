package es.ticketcenture.daos;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ticketcenture.entities.DescuentoUsuario;
import es.ticketcenture.entities.TipoUsuario;

@Repository
public class DescuentoUsuarioDAO implements IDescuentoUsuarioDAO {

	private final SessionFactory sessionFactory;

	@Autowired
	public DescuentoUsuarioDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Metodo que inserta el descuento.
	 */
	@Override
	public void insertar(DescuentoUsuario descuento) {
		getSession().persist(descuento);
	}

	/**
	 * Metodo que actualiza el descuento.
	 */
	@Override
	public void actualizar(DescuentoUsuario descuento) {
		getSession().merge(descuento);
	}

	/**
	 * Metodo que elimina el descuento
	 */
	@Override
	public void eliminar(DescuentoUsuario descuento) {
		getSession().remove(descuento);
	}

	/**
	 * Metodo que busca el descuento por ID.
	 */
	@Override
	public DescuentoUsuario buscarPorId(Integer id) {
		return getSession().get(DescuentoUsuario.class, id);
	}

	/**
	 * Metodo que lista todos los descuentos por id descendiente.
	 */
	@Override
	public List<DescuentoUsuario> buscarTodos() {
		String hql = "FROM DescuentoUsuario d ORDER BY d.idDescuento DESC";
		return getSession().createQuery(hql, DescuentoUsuario.class).getResultList();
	}

	/**
	 * Metodo que busca el descuento por tipo de usuario.
	 */
	@Override
	public List<DescuentoUsuario> buscarPorTipoUsuario(TipoUsuario tipoUsuario) {
		String hql = "FROM DescuentoUsuario d WHERE d.tipoUsuario = :tipo";
		return getSession().createQuery(hql, DescuentoUsuario.class).setParameter("tipo", tipoUsuario).getResultList();
	}

	/**
	 * Metodo que comprueba si existe solapamiento de descuento.
	 */
	@Override
	public boolean existeSolapamientoDescuento(DescuentoUsuario descuento) {

		TipoUsuario tipoUsuario = descuento.getTipoUsuario();
		LocalDate desde = descuento.getValidoDesde();
		LocalDate hasta = descuento.getValidoHasta();
		Integer idActual = descuento.getIdDescuento();

		String hql = "SELECT COUNT(d) " + "FROM DescuentoUsuario d " + "WHERE d.tipoUsuario = :tipoUsuario "
				+ "  AND (:idActual IS NULL OR d.idDescuento <> :idActual) " + "  AND ( "
				+ "        (d.validoDesde BETWEEN :desde AND :hasta) "
				+ "     OR (d.validoHasta BETWEEN :desde AND :hasta) "
				+ "     OR (:desde BETWEEN d.validoDesde AND d.validoHasta) "
				+ "     OR (:hasta BETWEEN d.validoDesde AND d.validoHasta) " + "      )";

		Long count = getSession().createQuery(hql, Long.class).setParameter("tipoUsuario", tipoUsuario)
				.setParameter("idActual", idActual).setParameter("desde", desde).setParameter("hasta", hasta)
				.uniqueResult();

		return count != null && count > 0;
	}

	/**
	 * Obtiene el descuento válido para un tipo de usuario en la fecha actual.
	 * Si no hay descuento activo, devuelve null.
	 */
	@Override
	public DescuentoUsuario obtenerDescuentoUsuario(TipoUsuario tipoUsuario) {

	    LocalDate hoy = LocalDate.now();

	    String hql = "FROM DescuentoUsuario d "
	               + "WHERE d.tipoUsuario = :tipo "
	               + "AND :hoy BETWEEN d.validoDesde AND d.validoHasta";

	    List<DescuentoUsuario> lista = getSession()
	            .createQuery(hql, DescuentoUsuario.class)
	            .setParameter("tipo", tipoUsuario)
	            .setParameter("hoy", hoy)
	            .getResultList();

	    return lista.isEmpty() ? null : lista.get(0);
	}


}
