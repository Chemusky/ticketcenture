package es.ticketcenture.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ticketcenture.daos.DireccionDAO;
import es.ticketcenture.entities.Direccion;
import es.ticketcenture.entities.TipoDireccion;
import es.ticketcenture.entities.Usuario;

/**
 * Servicio que implementa la logica de negocio de Direccion.
 *
 */
@Service
public class DireccionService {
	
	/** El DAO de Direccion */
	private final DireccionDAO direccionDAO;

	/**
	 * Creamos una instancia de DireccionService
	 * @param direccionDAO
	 */
	@Autowired
	public DireccionService(DireccionDAO direccionDAO) {
		this.direccionDAO = direccionDAO;
	}

	/**
	 * Inserta una direccion en la BBDD
	 * 
	 * @param usuario
	 * @param direccion
	 * @param tipo
	 */
	@Transactional
	public void insertarDireccion(Usuario usuario, Direccion direccion, TipoDireccion tipo) {
		direccion.setUsuario(usuario);
		direccion.setTipoDireccion(tipo);
		direccionDAO.insertarDireccion(direccion);
	}
}
