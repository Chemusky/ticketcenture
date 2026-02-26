package es.ticketcenture.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ticketcenture.entities.Evento;

@Repository
public class EventoDAO implements IEventoDAO {

	private final SessionFactory sessionFactory;

	@Autowired
	public EventoDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Método que insertar un nuevo evento
	 */
	@Override
	public void insertar(Evento evento) {
		getSession().persist(evento);

	}

	/**
	 * Método que actualiza un evento
	 */
	@Override
	public void actualizar(Evento evento) {
		getSession().merge(evento);

	}

	/**
	 * Método que elimina un evento
	 */
	@Override
	public void eliminar(Evento evento) {
		getSession().remove(evento);

	}

	/**
	 * Método que obtiene un evento por su ID
	 */
	@Override
	public Evento buscarPorId(Integer id) {
		return getSession().get(Evento.class, id);
	}

	/**
	 * Método que obtiene el listado de todos los eventos
	 */
	@Override
	public List<Evento> buscarTodos() {
		String hql = "FROM Evento e ORDER BY e.idEvento DESC";
		return getSession().createQuery(hql, Evento.class).getResultList();
	}

	/**
	 * Método que obtiene los eventos por su nombre
	 */
	@Override
	public List<Evento> buscarPorNombre(String nombre) {
		String hql = "FROM Evento e WHERE LOWER(e.nombreEvento) LIKE :nombre ORDER BY e.idEvento DESC";
		return getSession().createQuery(hql, Evento.class).setParameter("nombre", "%" + nombre.toLowerCase() + "%")
				.getResultList();
	}

	/**
	 * Método que obtiene los eventos al realizar el paginado
	 */
	@Override
	public List<Evento> buscarPaginado(int offset, int limit) {
		String hql = "FROM Evento e ORDER BY e.idEvento DESC";
		Query<Evento> query = getSession().createQuery(hql, Evento.class);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	/**
	 * Método que contabiliza el total de los eventos
	 */
	@Override
	public long contarTotal() {
		String hql = "SELECT COUNT(e) FROM Evento e";
		return getSession().createQuery(hql, Long.class).getSingleResult();
	}

	/**
	 * Método que permite buscar los eventos usando un filtro de búsqueda
	 */
	@Override
	public List<Evento> buscarConFiltros(Integer municipio, Integer grupo, String fecha, Boolean activo, int offset,
			int limit) {
		StringBuilder hql = new StringBuilder("SELECT e FROM Evento e WHERE 1=1");
		if (municipio != null && municipio != 0) {
			hql.append(" AND e.municipio.idMunicipio = :municipio");
		}
		if (grupo != null && grupo != 0) {
			hql.append(" AND e.grupo.idGrupo = :grupo");
		}
		if (fecha != null && !fecha.isBlank()) {
			hql.append(" AND DATE(e.fechaEvento) = :fecha");
		}
		if (activo != null) {
			hql.append(" AND e.activo = :activo");
		}
		hql.append(" ORDER BY e.idEvento DESC");
		Query<Evento> query = getSession().createQuery(hql.toString(), Evento.class);
		if (municipio != null && municipio != 0) {
			query.setParameter("municipio", municipio);
		}
		if (grupo != null && grupo != 0) {
			query.setParameter("grupo", grupo);
		}
		if (fecha != null && !fecha.isBlank()) {
			query.setParameter("fecha", java.sql.Date.valueOf(fecha));
		}
		if (activo != null) {
			query.setParameter("activo", activo);
		}
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.list();
	}

	/**
	 * Método que contabiliza la cantidad de eventos usando un filtro de búsqueda
	 */
	@Override
	public long contarConFiltros(Integer municipio, Integer grupo, String fecha, Boolean activo) {
		StringBuilder hql = new StringBuilder("SELECT COUNT(e) FROM Evento e WHERE 1=1");
		if (municipio != null && municipio != 0) {
			hql.append(" AND e.municipio.idMunicipio = :municipio");
		}
		if (grupo != null && grupo != 0) {
			hql.append(" AND e.grupo.idGrupo = :grupo");
		}
		if (fecha != null && !fecha.isBlank()) {
			hql.append(" AND DATE(e.fechaEvento) = :fecha");
		}
		if (activo != null) {
			hql.append(" AND e.activo = :activo");
		}
		Query<Long> query = getSession().createQuery(hql.toString(), Long.class);
		if (municipio != null && municipio != 0) {
			query.setParameter("municipio", municipio);
		}
		if (grupo != null && grupo != 0) {
			query.setParameter("grupo", grupo);
		}
		if (fecha != null && !fecha.isBlank()) {
			query.setParameter("fecha", java.sql.Date.valueOf(fecha));
		}
		if (activo != null) {
			query.setParameter("activo", activo);
		}
		return query.getSingleResult();
	}
	
	/**
	 * Método que obtiene los eventos más proximos por fecha
	 */
	@Override
	public List<Evento> buscarProximosEventos(int limite) {
	    String hql = "FROM Evento e " +
	                 "WHERE e.fechaEvento > CURRENT_TIMESTAMP " +
	                 "AND e.activo = true " +
	                 "ORDER BY e.fechaEvento ASC";

	    return getSession()
	            .createQuery(hql, Evento.class)
	            .setMaxResults(limite)
	            .getResultList();
	}

	/**
	 * Devuelve los eventos del grupo y solo los eventos a fecha posterior a hoy.
	 *
	 * @param idGrupo the id grupo
	 * @return the list
	 */
	@Override
	public List<Evento> buscarEventosFuturosPorGrupo(Integer idGrupo) {
	    String hql = "FROM Evento evento " +
	                 "WHERE evento.grupo.idGrupo = :idGrupo " +
	                 "AND evento.fechaEvento > CURRENT_TIMESTAMP " +
	                 "AND evento.activo = true " +
	                 "ORDER BY evento.fechaEvento ASC";

	    return getSession()
	            .createQuery(hql, Evento.class)
	            .setParameter("idGrupo", idGrupo)
	            .getResultList();
	}

}


