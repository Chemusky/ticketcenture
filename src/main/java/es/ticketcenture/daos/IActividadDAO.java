package es.ticketcenture.daos;

import java.time.LocalDateTime;
import java.util.List;

import es.ticketcenture.entities.Actividad;

/**
 * Interfaz IActividadDao para implementar la ActividadDao
 *
 */
public interface IActividadDAO {
	
	/**
	 * Metodo que se implementara para insertar un nuevo registro de Actividad en la bbdd
	 * @param actividad
	 */
	void insertar(Actividad actividad);
	
	/** 
	 * Metodo que implementara la lista de actividad por fechas en la bbdd
	 * @param idUsuario
	 * @param inicio
	 * @param fin
	 * @return
	 */
	public List<Actividad> buscarPorUsuarioYFechas(Integer idUsuario, LocalDateTime inicio, LocalDateTime fin);
}
