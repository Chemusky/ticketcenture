package es.ticketcenture.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ticketcenture.entities.TipoAsiento;

@Repository
public class TipoAsientoDAO implements ITipoAsientoDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public TipoAsientoDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<TipoAsiento> buscarTodos() {
        return getSession()
                .createQuery("FROM TipoAsiento ORDER BY nombreTipo ASC", TipoAsiento.class)
                .getResultList();
    }

    @Override
    public TipoAsiento buscarPorId(Integer idTipoAsiento) {
        return getSession().get(TipoAsiento.class, idTipoAsiento);
    }
}
