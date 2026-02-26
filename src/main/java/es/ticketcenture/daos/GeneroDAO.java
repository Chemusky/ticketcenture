package es.ticketcenture.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ticketcenture.entities.Genero;

@Repository
public class GeneroDAO implements IGeneroDAO {

	private final SessionFactory sessionFactory;

	@Autowired
	public GeneroDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void insertar(Genero genero) {
		getSession().persist(genero);
	}

	@Override
	public void actualizar(Genero genero) {
		getSession().merge(genero);
	}

	@Override
	public void eliminar(Genero genero) {
		getSession().remove(genero);
	}

	@Override
	public Genero buscarPorId(Integer id) {
		return getSession().get(Genero.class, id);
	}

	@Override
	public List<Genero> buscarTodos() {
		String hql = "FROM Genero g ORDER BY LOWER(g.nombreGenero) ASC";
		Query<Genero> query = getSession().createQuery(hql, Genero.class);
		return query.getResultList();
	}
	
	@Override
	public boolean tieneGruposAsociados(Integer idGenero) {
	    String hql = "SELECT COUNT(g) FROM Grupo g WHERE g.genero.idGenero = :idGenero";
	    Long count = getSession().createQuery(hql, Long.class)
	            .setParameter("idGenero", idGenero)
	            .uniqueResult();
	    return count != null && count > 0;
	}

}
