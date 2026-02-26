package es.ticketcenture.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ticketcenture.entities.Butaca;

/**
 * En esta clase gestionaremos las operaciones contra la bbdd
 *
 */
@Repository
public class ButacaDAO implements IButacaDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Gestiona la sesion
     * @return
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Busca por evento y tipo 
     */
    @Override
    public Butaca buscarPorEventoYTipo(Integer idEvento, Integer idTipo) {
        String hql = "FROM Butaca b WHERE b.evento.idEvento = :idEvento AND b.tipoAsiento.idTipo = :idTipo";
        return getSession()
                .createQuery(hql, Butaca.class)
                .setParameter("idEvento", idEvento)
                .setParameter("idTipo", idTipo)
                .uniqueResult();
    }
}

