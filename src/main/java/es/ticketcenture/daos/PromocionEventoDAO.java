package es.ticketcenture.daos;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ticketcenture.entities.PromocionEvento;

@Repository
public class PromocionEventoDAO implements IPromocionEventoDAO {

	private final SessionFactory sessionFactory;

	@Autowired
	public PromocionEventoDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Inserta una promoción.
	 */
	@Override
	public void insertar(PromocionEvento promocion) {
		getSession().persist(promocion);
	}

	/**
	 * Actualiza una promoción.
	 */
	@Override
	public void actualizar(PromocionEvento promocion) {
		getSession().merge(promocion);
	}

	/**
	 * Elimina una promoción.
	 */
	@Override
	public void eliminar(PromocionEvento promocion) {
		getSession().remove(promocion);
	}

	/**
	 * Busca una promoción por ID.
	 */
	@Override
	public PromocionEvento buscarPorId(Integer id) {
		return getSession().get(PromocionEvento.class, id);
	}

	/**
	 * Lista todas las promociones ordenadas por ID descendente.
	 */
	@Override
	public List<PromocionEvento> buscarTodos() {
		String hql = "FROM PromocionEvento p ORDER BY p.idPromocion DESC";
		return getSession().createQuery(hql, PromocionEvento.class).getResultList();
	}

	/**
	 * Busca promociones por código exacto.
	 */
	@Override
	public List<PromocionEvento> buscarPorCodigo(String codigo) {
		String hql = "FROM PromocionEvento p WHERE p.codigo = :codigo";
		return getSession().createQuery(hql, PromocionEvento.class).setParameter("codigo", codigo).getResultList();
	}
	
	/**
	 * Busca promociones por evento y codigo.
	 */
	@Override
	public List<PromocionEvento> buscarPorEventoYCodigo(Integer idEvento, String codigo) {
	    String hql = "FROM PromocionEvento p WHERE p.evento.idEvento = :idEvento AND p.codigo = :codigo";
	    return getSession()
	            .createQuery(hql, PromocionEvento.class)
	            .setParameter("idEvento", idEvento)
	            .setParameter("codigo", codigo)
	            .getResultList();
	}


	/**
	 * Comprueba si existe solapamiento de promociones para el mismo evento.
	 */
	@Override
	public boolean existeSolapamientoPromocion(PromocionEvento promo) {

		Integer idEvento = promo.getEvento().getIdEvento();
		LocalDate desde = promo.getValidoDesde();
		LocalDate hasta = promo.getValidoHasta();
		Integer idActual = promo.getIdPromocion(); // null si es nueva

		String hql = "SELECT COUNT(p) " + "FROM PromocionEvento p " + "WHERE p.evento.idEvento = :idEvento "
				+ "  AND (:idActual IS NULL OR p.idPromocion <> :idActual) " + "  AND ( "
				+ "        (p.validoDesde BETWEEN :desde AND :hasta) "
				+ "     OR (p.validoHasta BETWEEN :desde AND :hasta) "
				+ "     OR (:desde BETWEEN p.validoDesde AND p.validoHasta) "
				+ "     OR (:hasta BETWEEN p.validoDesde AND p.validoHasta) " + "      )";

		Long count = getSession().createQuery(hql, Long.class).setParameter("idEvento", idEvento)
				.setParameter("idActual", idActual).setParameter("desde", desde).setParameter("hasta", hasta)
				.uniqueResult();

		return count != null && count > 0;
	}
}
