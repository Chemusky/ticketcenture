package es.ticketcenture.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ticketcenture.entities.Grupo;

@Repository
public class GrupoDAO implements IGrupoDAO {

	private final SessionFactory sessionFactory;

	@Autowired
	public GrupoDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void insertar(Grupo grupo) {
		getSession().persist(grupo);
	}

	@Override
	public void actualizar(Grupo grupo) {
		getSession().merge(grupo);
	}

	@Override
	public Grupo buscarPorId(Integer id) {
		return getSession().get(Grupo.class, id);
	}

	@Override
	public void eliminar(Grupo grupo) {
		getSession().remove(grupo);
	}

	@Override
	public Grupo buscarPorIdConGaleria(Integer idGrupo) {
	    String hql = "SELECT g FROM Grupo g " +
	                 "LEFT JOIN FETCH g.galeria " +
	                 "WHERE g.idGrupo = :id";

	    return getSession()
	            .createQuery(hql, Grupo.class)
	            .setParameter("id", idGrupo)
	            .uniqueResult();
	}


	@Override
	public List<Grupo> buscarTodos() {
		String hql = "FROM Grupo g ORDER BY g.idGrupo DESC";
		return getSession().createQuery(hql, Grupo.class).getResultList();
	}

	@Override
	public List<Grupo> buscarActivos() {
		String hql = "FROM Grupo g WHERE g.activo = true ORDER BY g.idGrupo DESC";
		return getSession().createQuery(hql, Grupo.class).getResultList();
	}

	@Override
	public List<Grupo> buscarPorNombre(String nombre) {
		String hql = "FROM Grupo g WHERE LOWER(g.nombreGrupo) LIKE :nombre ORDER BY g.idGrupo DESC";
		return getSession().createQuery(hql, Grupo.class).setParameter("nombre", "%" + nombre.toLowerCase() + "%")
				.getResultList();
	}

	@Override
	public List<Grupo> buscarPorGenero(Integer idGenero) {
		String hql = "FROM Grupo g WHERE g.genero.idGenero = :idGenero ORDER BY g.idGrupo DESC";
		return getSession().createQuery(hql, Grupo.class).setParameter("idGenero", idGenero).getResultList();
	}

	@Override
	public List<Grupo> buscarPaginado(int offset, int limit) {
		String hql = "FROM Grupo g ORDER BY g.idGrupo DESC";
		Query<Grupo> query = getSession().createQuery(hql, Grupo.class);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public long contarTotal() {
		String hql = "SELECT COUNT(g) FROM Grupo g";
		return getSession().createQuery(hql, Long.class).getSingleResult();
	}

	@Override
	public List<Grupo> buscarConFiltros(String nombre, Integer idGenero, Integer idUsuarioFavoritos, Boolean activo,
			int offset, int limit) {

		StringBuilder hql = new StringBuilder("SELECT g FROM Grupo g WHERE 1=1");

		if (nombre != null && !nombre.isBlank()) {
			hql.append(" AND LOWER(g.nombreGrupo) LIKE :nombre");
		}

		if (idGenero != null) {
			hql.append(" AND g.genero.idGenero = :idGenero");
		}

		if (idUsuarioFavoritos != null) {
			hql.append(
					" AND g.idGrupo IN (SELECT f.grupo.idGrupo FROM Favorito f WHERE f.usuario.idUsuario = :idUsuario)");
		}

		if (activo != null) {
			hql.append(" AND g.activo = :activo");
		}
		hql.append(" ORDER BY g.idGrupo DESC");
		Query<Grupo> query = getSession().createQuery(hql.toString(), Grupo.class);

		if (nombre != null && !nombre.isBlank()) {
			query.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
		}

		if (idGenero != null) {
			query.setParameter("idGenero", idGenero);
		}

		if (idUsuarioFavoritos != null) {
			query.setParameter("idUsuario", idUsuarioFavoritos);
		}

		if (activo != null) {
			query.setParameter("activo", activo);
		}

		query.setFirstResult(offset);
		query.setMaxResults(limit);

		return query.list();
	}

	@Override
	public long contarConFiltros(String nombre, Integer idGenero, Integer idUsuarioFavoritos, Boolean activo) {

	    StringBuilder hql = new StringBuilder("SELECT COUNT(*) FROM Grupo g WHERE 1=1");

	    if (nombre != null && !nombre.isBlank()) {
	        hql.append(" AND LOWER(g.nombreGrupo) LIKE :nombre");
	    }

	    if (idGenero != null) {
	        hql.append(" AND g.genero.idGenero = :idGenero");
	    }

	    if (idUsuarioFavoritos != null) {
	        hql.append(" AND g.idGrupo IN (SELECT f.grupo.idGrupo FROM Favorito f WHERE f.usuario.idUsuario = :idUsuario)");
	    }

	    if (activo != null) {
	        hql.append(" AND g.activo = :activo");
	    }

	    Query<Long> query = getSession().createQuery(hql.toString(), Long.class);

	    if (nombre != null && !nombre.isBlank()) {
	        query.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
	    }

	    if (idGenero != null) {
	        query.setParameter("idGenero", idGenero);
	    }

	    if (idUsuarioFavoritos != null) {
	        query.setParameter("idUsuario", idUsuarioFavoritos);
	    }

	    if (activo != null) {
	        query.setParameter("activo", activo);
	    }

	    return query.getSingleResult();
	}


	@Override
	public Grupo buscarPorIdConFavoritos(Integer id) {
		String hql = "SELECT g FROM Grupo g " + "LEFT JOIN FETCH g.favoritos f " + "LEFT JOIN FETCH f.usuario "
				+ "WHERE g.idGrupo = :id";

		return getSession().createQuery(hql, Grupo.class).setParameter("id", id).uniqueResult();
	}

	@Override
	public List<Grupo> obtenerFavoritosPorUsuario(Integer idUsuario) {

		String hql = "SELECT DISTINCT g FROM Grupo g " + "JOIN g.favoritos f " + "JOIN f.usuario u "
				+ "WHERE u.idUsuario = :idUsuario";

		return getSession().createQuery(hql, Grupo.class).setParameter("idUsuario", idUsuario).list();
	}

}
