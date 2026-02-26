package es.ticketcenture.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ticketcenture.daos.ActividadDAO;
import es.ticketcenture.entities.Actividad;
import es.ticketcenture.entities.Usuario;

/**
 * Servicio que implementa la logica de negocio de Actividad.
 *
 */

@Service  
public class ActividadService {

	/** El DAO de Actividad */
	private ActividadDAO actividadDAO;	
	
	/**
	 * Creamos una instancia de ActividadService
	 * 
	 * @param actividadDAO
	 */
	@Autowired
	public ActividadService(ActividadDAO actividadDAO) {
		this.actividadDAO = actividadDAO;
	}
	
	/**
	 * Inserta una actividad en la bbdd
	 * 
	 * @param actividad
	 */
	@Transactional
    public void insertarActividad(Usuario usuario, String accion, String ipActividad) {
        Actividad actividad = new Actividad();
       
        actividad.setUsuario(usuario);
        actividad.setAccion(accion);
        actividad.setIpActividad(ipActividad);
        
		actividadDAO.insertar(actividad);
    }
	
	/**
	 * Inserta una actividad en la bbdd. Esta sobreescrito con un parametro mas para los detalles
	 * @param actividad
	 */
	@Transactional
    public void insertarActividad(Usuario usuario, String accion, String ipActividad, String detalles) {
        Actividad actividad = new Actividad();
       
        actividad.setUsuario(usuario);
        actividad.setAccion(accion);
        actividad.setIpActividad(ipActividad);
        actividad.setDetalles(detalles);
        
        
		actividadDAO.insertar(actividad);
    }
	
	@Transactional (readOnly=true)
    public List<Actividad> obtenerActividades(Usuario usuario, LocalDateTime inicio, LocalDateTime fin) {
        return actividadDAO.buscarPorUsuarioYFechas(usuario.getIdUsuario(), inicio, fin);
    }
	
	@Transactional (readOnly=true)
    public List<Actividad> obtenerActividades(Usuario usuario) {
        return actividadDAO.recuperarActividades(usuario.getIdUsuario());
    }

    
}

