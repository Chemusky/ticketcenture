package es.ticketcenture.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ticketcenture.daos.EventoDAO;
import es.ticketcenture.daos.IEventoDAO;
import es.ticketcenture.entities.Entrada;
import es.ticketcenture.entities.Evento;
import es.ticketcenture.entities.TipoAsiento;
import es.ticketcenture.entities.TipoEntrada;
import es.ticketcenture.entities.Usuario;
import es.ticketcenture.utilities.Acciones;
import es.ticketcenture.utilities.MensajesError;
import es.ticketcenture.utilities.ValidarEvento;

/**
 *
 */
@Service
@Transactional
public class EventoService {

	private final IEventoDAO eventoDAO;
	private final ActividadService actividadService;
	private final SessionFactory sessionFactory;

	/**
	 * @param eventoDAO
	 * @param actividadService
	 * @param sessionFactory
	 */
	@Autowired
	public EventoService(EventoDAO eventoDAO, ActividadService actividadService, SessionFactory sessionFactory) {
		this.eventoDAO = eventoDAO;
		this.actividadService = actividadService;
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @param evento
	 * @param usuarioAdmin
	 * @param request
	 */
	@Transactional
	public void insertarEvento(Evento evento, Usuario usuarioAdmin, HttpServletRequest request) {

		if (!eventoDAO.buscarPorNombre(evento.getNombreEvento()).isEmpty()) {
			throw new IllegalArgumentException(MensajesError.EVENTO_YA_EXISTE);
		}
		eventoDAO.insertar(evento);

		actividadService.insertarActividad(usuarioAdmin, Acciones.CREAR_EVENTO, request.getRemoteAddr(),
				"Evento creado: " + evento.getNombreEvento());
	}

	/**
	 * @param evento
	 * @param regenerarEntradas
	 * @param usuarioAdmin
	 * @param request
	 */
	@Transactional
	public void actualizarEvento(Evento evento, boolean regenerarEntradas, Usuario usuarioAdmin,
			HttpServletRequest request) {

		// Si se quiere regenerar entradas, comprobar que no hay vendidas
		if (regenerarEntradas && ValidarEvento.existenEntradasVendidas(evento)) {
			throw new IllegalStateException(MensajesError.EVENTO_NO_MODIFICABLE);
		}

		// Regenerar entradas si el admin lo solicita con un checkbox
		if (regenerarEntradas) {
			// Eliminar entradas antiguas
			evento.getEntradas().clear();
			// Generar nuevas
			List<Entrada> nuevas = new ValidarEvento().generarEntradas(evento);
			// Asignarlas
			evento.setEntradas(nuevas);
		}
		eventoDAO.actualizar(evento);

		actividadService.insertarActividad(usuarioAdmin, Acciones.EDITAR_EVENTO, request.getRemoteAddr(),
				"Evento actualizado: " + evento.getNombreEvento());
	}

	/**
	 * @param idEvento
	 * @param usuarioAdmin
	 * @param request
	 */
	@Transactional
	public void eliminarEvento(Integer idEvento, Usuario usuarioAdmin, HttpServletRequest request) {
		Evento evento = eventoDAO.buscarPorId(idEvento);
		if (evento == null) {
			throw new IllegalArgumentException(MensajesError.EVENTO_ID_NO_EXISTE);
		}

		if (ValidarEvento.existenEntradasVendidas(evento)) {
			throw new IllegalStateException(MensajesError.EVENTO_NO_ELIMINABLE);
		}

		eventoDAO.eliminar(evento);

		actividadService.insertarActividad(usuarioAdmin, Acciones.ELIMINAR_EVENTO, request.getRemoteAddr(),
				"Evento eliminado: " + evento.getNombreEvento());
	}

	/*
	 * Este metodo es para cambiar el estado de activo a inactivo (y viceversa).
	 * Queda comentado porque en un principio llamaremos con un check desde el
	 * propio formulario, que llama al post de Editar Evento.
	 * 
	 * @Transactional public void cambiarEstadoEvento (Integer idEvento, boolean
	 * activo, Usuario usuarioAdmin, HttpServletRequest request) {
	 * 
	 * Evento evento = eventoDAO.buscarPorId(idEvento); if (evento == null) { throw
	 * new IllegalArgumentException(MensajesError.EVENTO_ID_NO_EXISTE); }
	 * 
	 * evento.setActivo(activo); eventoDAO.actualizar(evento);
	 * actividadService.insertarActividad( usuarioAdmin, activo ?
	 * Acciones.ACTIVAR_EVENTO: Acciones.DESACTIVAR_EVENTO, request.getRemoteAddr(),
	 * (activo ? "Evento activado: " : "Evento desactivado: ") +
	 * evento.getNombreEvento() ); }
	 */

	/**
	 * @param idEvento
	 * @return
	 */
	@Transactional(readOnly = true)
	public Evento buscarPorId(Integer idEvento) {

		Evento evento = eventoDAO.buscarPorId(idEvento);

		if (evento != null) {
			// Fuerza la carga de las colecciones LAZY dentro de la sesión
			evento.getEntradas().size();
			evento.getGaleria().size();
			evento.getButacas().size();
		}

		return evento;
	}

	/**
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Evento> buscarTodos() {
		return eventoDAO.buscarTodos();
	}

	/**
	 * @param offset
	 * @param limit
	 * @return
	 */
	@Transactional
	public List<Evento> buscarPaginado(int offset, int limit) {
		return eventoDAO.buscarPaginado(offset, limit);
	}

	/**
	 * @return
	 */
	@Transactional
	public long contarTotal() {
		return eventoDAO.contarTotal();
	}

	/**
	 * @param municipio
	 * @param grupo
	 * @param fecha
	 * @param activo
	 * @param offset
	 * @param limit
	 * @return
	 */
	@Transactional
	public List<Evento> buscarConFiltros(Integer municipio, Integer grupo, String fecha, Boolean activo, int offset,
			int limit) {
		return eventoDAO.buscarConFiltros(municipio, grupo, fecha, activo, offset, limit);
	}

	/**
	 * @param municipio
	 * @param grupo
	 * @param fecha
	 * @param activo
	 * @return
	 */
	@Transactional
	public long contarConFiltros(Integer municipio, Integer grupo, String fecha, Boolean activo) {
		return eventoDAO.contarConFiltros(municipio, grupo, fecha, activo);
	}

	/**
	 * Metodo que calcula el stock actual de entradas por tipo
	 * 
	 * @param evento
	 * @return
	 */
	@Transactional(readOnly = true)
	public Map<TipoAsiento, Integer> calcularStockEntradasPorTipo(Evento evento) {

		evento.getEntradas().size(); // forzar la carga de entradas

		int minutosReserva = 10;
		LocalDateTime limiteEnCarrito = LocalDateTime.now().minusMinutes(minutosReserva);

		Map<TipoAsiento, Integer> stockPorTipo = new HashMap<>();

		for (Entrada e : evento.getEntradas()) {
			boolean disponible = false;

			// Entradas que directamente son tipo = disponible
			if (e.getEstado() == TipoEntrada.DISPONIBLE) {
				disponible = true;

				// Entradas que llevan mas de 10 minutos en un carrito
			} else if (e.getEstado() == TipoEntrada.RESERVADA_CARRITO) {
				if (e.getReservadaCarrito() != null && e.getReservadaCarrito().isBefore(limiteEnCarrito)) {
					disponible = true;
				}
			}

			// Y si esta disponible la sumamos por tipo
			if (disponible) {
				TipoAsiento tipo = e.getTipoAsiento();
				stockPorTipo.merge(tipo, 1, Integer::sum);
			}
		}

		return stockPorTipo;
	}
	
	/**
	 * @param limite
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Evento> buscarProximosEventos(int limite) {
	    return eventoDAO.buscarProximosEventos(limite);
	}
	/**
	 * @param idGrupo
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Evento> obtenerEventosFuturosPorGrupo(Integer idGrupo) {
	    return eventoDAO.buscarEventosFuturosPorGrupo(idGrupo);
	}
	
}
