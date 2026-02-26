package es.ticketcenture.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ticketcenture.entities.GaleriaGrupo;

@Repository
public class GaleriaDAO implements IGaleriaDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public GaleriaDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void insertar(GaleriaGrupo imagen) {
        getSession().persist(imagen);
    }

    @Override
    public void eliminar(GaleriaGrupo imagen) {
        getSession().remove(imagen);
    }

    @Override
    public GaleriaGrupo buscarPorId(Integer id) {
        return getSession().get(GaleriaGrupo.class, id);
    }

    @Override
    public List<GaleriaGrupo> buscarPorGrupo(Integer idGrupo) {
        String hql = "FROM GaleriaGrupo g WHERE g.grupo.idGrupo = :idGrupo";
        return getSession()
                .createQuery(hql, GaleriaGrupo.class)
                .setParameter("idGrupo", idGrupo)
                .getResultList();
    }
}
