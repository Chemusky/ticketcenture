package es.ticketcenture.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ticketcenture.entities.GaleriaEvento;

@Repository
public class GaleriaEventoDAO implements IGaleriaEventoDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public GaleriaEventoDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void insertar(GaleriaEvento imagen) {
        getSession().persist(imagen);
    }

    @Override
    public GaleriaEvento buscarPorId(Integer idImagen) {
        return getSession().get(GaleriaEvento.class, idImagen);
    }

    @Override
    public List<GaleriaEvento> buscarPorEvento(Integer idEvento) {
        return getSession()
                .createQuery("FROM GaleriaEvento g WHERE g.evento.idEvento = :id", GaleriaEvento.class)
                .setParameter("id", idEvento)
                .getResultList();
    }
}
