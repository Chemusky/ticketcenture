package es.ticketcenture.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ticketcenture.entities.Municipio;

@Repository
public class MunicipioDAO implements IMunicipioDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public MunicipioDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Municipio> buscarTodos() {
        return getSession()
                .createQuery("FROM Municipio ORDER BY nombreMunicipio ASC", Municipio.class)
                .getResultList();
    }

    @Override
    public Municipio buscarPorId(Integer idMunicipio) {
        return getSession().get(Municipio.class, idMunicipio);
    }
}
