package es.ticketcenture.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ticketcenture.entities.Entrada;

/**
 * En esta clase gestionaremos las operaciones contra la bbdd
 */
@Repository
public class EntradaDAO implements IEntradaDAO {

	private final SessionFactory sessionFactory;

	@Autowired
	public EntradaDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Gestiona la sesión
	 */
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Buscar entrada por id
	 */
	@Override
	public Entrada buscarPorId(Integer idEntrada) {
		return getSession().get(Entrada.class, idEntrada);
	}

	/**
	 * Actualiza una entrada
	 */
	@Override
	public void actualizar(Entrada entrada) {
		getSession().merge(entrada);
	}

	/**
	 * Busca la entrada en función de los parámetros establecidos
	 */
	@Override
	public Entrada buscarPorEventoTipoFilaAsiento(Integer idEvento, Integer idTipo, Integer fila, Integer asiento) {

		String hql = "FROM Entrada e " + "WHERE e.evento.idEvento = :idEvento " + "AND e.tipoAsiento.idTipo = :idTipo "
				+ "AND e.fila = :fila " + "AND e.asiento = :asiento";

		return getSession().createQuery(hql, Entrada.class).setParameter("idEvento", idEvento)
				.setParameter("idTipo", idTipo).setParameter("fila", fila).setParameter("asiento", asiento)
				.uniqueResult();
	}

}
