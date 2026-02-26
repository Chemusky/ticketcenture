package es.ticketcenture.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ticketcenture.entities.Direccion;

/**
 * En esta clase gestionaremos las operaciones contra la bbdd
 *
 */
@Repository
public class DireccionDAO implements IDireccionDAO {

	private final SessionFactory sessionFactory;

	/**
	 * @param sessionFactory
	 */
	@Autowired
	public DireccionDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Gestiona la sesion
	 * 
	 * @return sesion
	 */
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Metodo para insertar el registro de direccion en la BBDD
	 */
	@Override
	public void insertarDireccion(Direccion direccion) {
		getSession().save(direccion);
	}

}
