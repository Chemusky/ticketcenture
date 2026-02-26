package es.ticketcenture.daos;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ticketcenture.entities.Actividad;

/**
 * En esta clase gestionaremos las operaciones contra la bbdd
 *
 */
@Repository 
public class ActividadDAO implements IActividadDAO {	
	
	@Autowired  
    private SessionFactory sessionFactory;

	/**
	 * Gestiona la sesion
	 * @return sesion 
	 */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Metodo para insertar el registro de Actividad en la BBDD
     */
    public void insertar(Actividad actividad) {
        getSession().save(actividad);
    }
    
    /**
     * Metodo para buscar las actividades realizadas por un usuario entre dos fechas.
     */
    @Override
	public List<Actividad> buscarPorUsuarioYFechas(Integer idUsuario, LocalDateTime inicio, LocalDateTime fin) {
		String consulta = "FROM Actividad actividad WHERE actividad.usuario.idUsuario = :idUsuario"
				+ " AND actividad.fechaActividad BETWEEN :inicio AND :fin"
				+ " ORDER BY actividad.fechaActividad DESC";
		Query<Actividad> query = getSession().createQuery(consulta, Actividad.class);
		query.setParameter("idUsuario", idUsuario);
		query.setParameter("inicio", inicio);
		query.setParameter("fin", fin);

		return query.getResultList();
    }
    
    public List<Actividad> recuperarActividades(Integer idUsuario) {
    	String consulta = "FROM Actividad actividad WHERE actividad.usuario.idUsuario = :idUsuario";
    	Query<Actividad> query = getSession().createQuery(consulta, Actividad.class);
		query.setParameter("idUsuario", idUsuario);
    	
		return query.getResultList();
    		
    }
}
