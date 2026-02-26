package es.ticketcenture.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ticketcenture.entities.Favorito;

/**
 * Clase que implementa los métodos definidos en la interfaz para gestionar las consultas en la Base de Datos
 * @author jose.m.romero.aja
 *
 */
@Repository
public class FavoritoDAO implements IFavoritoDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public FavoritoDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void insertar(Favorito favorito) {
        getSession().persist(favorito);
    }

    @Override
    public void eliminar(Favorito favorito) {
        getSession().remove(favorito);
    }

    @Override
    public Favorito buscarPorUsuarioYGrupo(Integer idUsuario, Integer idGrupo) {
        String hql = "FROM Favorito f WHERE f.usuario.idUsuario = :idUsuario AND f.grupo.idGrupo = :idGrupo";
        Query<Favorito> query = getSession().createQuery(hql, Favorito.class);
        query.setParameter("idUsuario", idUsuario);
        query.setParameter("idGrupo", idGrupo);
        return query.uniqueResult();
    }

    @Override
    public List<Favorito> buscarPorUsuario(Integer idUsuario) {
        String hql = "FROM Favorito f WHERE f.usuario.idUsuario = :idUsuario";
        return getSession()
                .createQuery(hql, Favorito.class)
                .setParameter("idUsuario", idUsuario)
                .getResultList();
    }
}
