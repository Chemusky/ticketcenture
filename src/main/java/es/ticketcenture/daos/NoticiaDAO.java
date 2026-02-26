package es.ticketcenture.daos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ticketcenture.entities.Noticia;

/**
 * Clase que implementa los métodos definidos en la interfaz para gestionar las
 * consultas en la Base de Datos
 *
 */
@Repository
public class NoticiaDAO implements INoticiaDAO {

	private final SessionFactory sessionFactory;

	/**
	 * @param sessionFactory
	 */
	@Autowired
	public NoticiaDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Gestiona la sesion
	 * 
	 * @return
	 */
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Metodo para insertar la noticia
	 */
	@Override
	public void insertar(Noticia noticia) {
		getSession().persist(noticia);
	}

	/**
	 * Metodo para actualizar la noticia
	 */
	@Override
	public void actualizar(Noticia noticia) {
		getSession().merge(noticia);
	}

	/**
	 * Metodo para eliminar la noticia
	 */
	@Override
	public void eliminar(Noticia noticia) {
		getSession().remove(noticia);
	}

	/**
	 * Metodo para busca por Id
	 */
	@Override
	public Noticia buscarPorId(Integer id) {
		return getSession().get(Noticia.class, id);
	}

	/**
	 * Metodo para buscar todas las noticias
	 */
	@Override
	public List<Noticia> buscarTodos() {
		String hql = "FROM Noticia n ORDER BY n.idNoticia DESC";
		return getSession().createQuery(hql, Noticia.class).getResultList();
	}

	/**
	 * Metodo para buscar el paginado
	 */
	@Override
	public List<Noticia> buscarPaginado(int offset, int limit) {
		String hql = "FROM Noticia n ORDER BY n.idNoticia DESC";
		Query<Noticia> query = getSession().createQuery(hql, Noticia.class);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	/**
	 * Metodo para contar por paginado
	 */
	@Override
	public long contarPaginado() {
		String hql = "SELECT COUNT(n) FROM Noticia n";
		return getSession().createQuery(hql, Long.class).getSingleResult();
	}

	/**
	 * Metodo buscar con filtros por titulo, idGrupo y publicado
	 */
	@Override
	public List<Noticia> buscarConFiltros(String titulo, Integer idGrupo, Boolean publicado,
	                                      String fechaPublicacion, int offset, int limit) {

	    StringBuilder hql = new StringBuilder("SELECT n FROM Noticia n WHERE 1=1");

	    if (titulo != null && !titulo.isBlank()) {
	        hql.append(" AND LOWER(n.titulo) LIKE :titulo");
	    }
	    if (idGrupo != null && idGrupo != 0) {
	        hql.append(" AND n.grupo.idGrupo = :idGrupo");
	    }
	    if (publicado != null) {
	        hql.append(" AND n.publicado = :publicado");
	    }
	    if (fechaPublicacion != null && !fechaPublicacion.isBlank()) {
	        hql.append(" AND n.fechaPublicacion BETWEEN :inicio AND :fin");
	    }

	    hql.append(" ORDER BY n.idNoticia DESC");

	    Query<Noticia> query = getSession().createQuery(hql.toString(), Noticia.class);

	    if (titulo != null && !titulo.isBlank()) {
	        query.setParameter("titulo", "%" + titulo.toLowerCase() + "%");
	    }
	    if (idGrupo != null && idGrupo != 0) {
	        query.setParameter("idGrupo", idGrupo);
	    }
	    if (publicado != null) {
	        query.setParameter("publicado", publicado);
	    }
	    if (fechaPublicacion != null && !fechaPublicacion.isBlank()) {

	        LocalDate date = LocalDate.parse(fechaPublicacion); 
	        LocalDateTime inicio = date.atStartOfDay();
	        LocalDateTime fin = date.atTime(23, 59, 59, 999999999);

	        query.setParameter("inicio", inicio);
	        query.setParameter("fin", fin);
	    }


	    query.setFirstResult(offset);
	    query.setMaxResults(limit);

	    return query.getResultList();
	}


	/**
	 * Metodo para contar con filtros
	 */
	@Override
	public long contarConFiltros(String titulo, Integer idGrupo, Boolean publicado) {

		StringBuilder hql = new StringBuilder("SELECT COUNT(n) FROM Noticia n WHERE 1=1");

		if (titulo != null && !titulo.isBlank()) {
			hql.append(" AND LOWER(n.titulo) LIKE :titulo");
		}
		if (idGrupo != null && idGrupo != 0) {
			hql.append(" AND n.grupo.idGrupo = :idGrupo");
		}
		if (publicado != null) {
			hql.append(" AND n.publicado = :publicado");
		}

		Query<Long> query = getSession().createQuery(hql.toString(), Long.class);

		if (titulo != null && !titulo.isBlank()) {
			query.setParameter("titulo", "%" + titulo.toLowerCase() + "%");
		}
		if (idGrupo != null && idGrupo != 0) {
			query.setParameter("idGrupo", idGrupo);
		}
		if (publicado != null) {
			query.setParameter("publicado", publicado);
		}

		return query.getSingleResult();
	}

	/**
	 * Método para realizar la búsqueda de las últimas 5 noticias
	 */
	@Override
	public List<Noticia> buscarUltimas5() {
		String jpql = "SELECT n FROM Noticia n " + "WHERE n.publicado = true " + "ORDER BY n.fechaPublicacion DESC";

		return getSession().createQuery(jpql, Noticia.class).setMaxResults(5).getResultList();
	}
	
	
	/**
	 * Metodo que nos recupera las noticias publicadas hace menos de 24h de los grupos
	 * favoritos del usuario en sesión
	 * @param idUsuario
	 * @return
	 */
	public List<Noticia> buscarNoticiasRecientesFavoritos(Integer idUsuario) {

	    LocalDateTime hace24h = LocalDateTime.now().minusHours(24);

	    String hql = "FROM Noticia n " +
	                 "WHERE n.publicado = TRUE " +
	                 "AND n.fechaPublicacion >= :hace24h " +
	                 "AND n.grupo.idGrupo IN (" +
	                 "   SELECT f.grupo.idGrupo " +
	                 "   FROM Favorito f " +
	                 "   WHERE f.usuario.idUsuario = :idUsuario" +
	                 ") " +
	                 "ORDER BY n.fechaPublicacion DESC";

	    Query<Noticia> query = getSession().createQuery(hql, Noticia.class);
	    query.setParameter("hace24h", hace24h);
	    query.setParameter("idUsuario", idUsuario);

	    return query.getResultList();
	}

	@Override
	public List<Noticia> buscarTodosPublicados() {

	    String hql = "FROM Noticia n WHERE n.publicado = TRUE ORDER BY n.fechaPublicacion DESC";

	    Query<Noticia> query = getSession().createQuery(hql, Noticia.class);

	    return query.getResultList();
	}



}
