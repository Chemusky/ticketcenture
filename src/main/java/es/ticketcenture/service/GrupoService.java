package es.ticketcenture.service;

import es.ticketcenture.daos.GrupoDAO;
import es.ticketcenture.daos.IGrupoDAO;
import es.ticketcenture.entities.Grupo;
import es.ticketcenture.entities.Usuario;
import es.ticketcenture.utilities.Acciones;
import es.ticketcenture.utilities.MensajesError;
import es.ticketcenture.validations.ValidarGrupoEntradas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional
public class GrupoService {

	private final IGrupoDAO grupoDAO;
	private final ActividadService actividadService;

	@Autowired
	public GrupoService(GrupoDAO grupoDAO, ActividadService actividadService) {
		this.grupoDAO = grupoDAO;
		this.actividadService = actividadService;
	}

	@Transactional
	public void insertarGrupo(Grupo grupo, Usuario usuarioAdmin, HttpServletRequest request) {

		if (!grupoDAO.buscarPorNombre(grupo.getNombreGrupo()).isEmpty()) {
			throw new IllegalArgumentException(MensajesError.GRUPO_YA_EXISTE);
		}

		grupoDAO.insertar(grupo);

		actividadService.insertarActividad(usuarioAdmin, Acciones.CREAR_GRUPO, request.getRemoteAddr(),
				"Grupo creado: " + grupo.getNombreGrupo());
	}

	@Transactional
	public void actualizarGrupo(Grupo grupo, Usuario usuarioAdmin, HttpServletRequest request) {

		grupoDAO.actualizar(grupo);

		actividadService.insertarActividad(usuarioAdmin, Acciones.EDITAR_GRUPO, request.getRemoteAddr(),
				"Grupo actualizado: " + grupo.getNombreGrupo());
	}

	@Transactional
	public void eliminarGrupo(Integer idGrupo, Usuario usuarioAdmin, HttpServletRequest request) {

		Grupo grupo = grupoDAO.buscarPorId(idGrupo);
		if (grupo == null) {
			throw new IllegalArgumentException(MensajesError.GRUPO_ID_NO_EXISTE);
		}

		// Validacion de entradas vendidas para un evento del grupo
		if (ValidarGrupoEntradas.existenEntradasVendidas(grupo)) {
			throw new IllegalStateException(MensajesError.GRUPO_NO_ELIMINABLE); 
		}

		grupoDAO.eliminar(grupo);

		actividadService.insertarActividad(usuarioAdmin, Acciones.ELIMINAR_GRUPO, request.getRemoteAddr(),
				"Grupo eliminado: " + grupo.getNombreGrupo());
	}

	@Transactional
	public void cambiarEstadoGrupo(Integer idGrupo, boolean activo, Usuario usuarioAdmin, HttpServletRequest request) {

		Grupo grupo = grupoDAO.buscarPorId(idGrupo);
		if (grupo == null) {
			throw new IllegalArgumentException(MensajesError.GRUPO_ID_NO_EXISTE);
		}

		grupo.setActivo(activo);
		grupoDAO.actualizar(grupo);

		actividadService.insertarActividad(usuarioAdmin, activo ? Acciones.ACTIVAR_GRUPO : Acciones.DESACTIVAR_GRUPO,
				request.getRemoteAddr(),
				(activo ? "Grupo activado: " : "Grupo desactivado: ") + grupo.getNombreGrupo());
	}

	@Transactional(readOnly = true)
	public Grupo buscarPorId(Integer idGrupo) {
		return grupoDAO.buscarPorId(idGrupo);
	}

	@Transactional(readOnly = true)
	public Grupo buscarPorIdConGaleria(Integer idGrupo) {
		return grupoDAO.buscarPorIdConGaleria(idGrupo);
	}

	@Transactional(readOnly = true)
	public List<Grupo> buscarTodos() {
		return grupoDAO.buscarTodos();
	}

	@Transactional(readOnly = true)
	public List<Grupo> buscarActivos() {
		return grupoDAO.buscarActivos();
	}

	@Transactional(readOnly = true)
	public List<Grupo> buscarPorNombre(String nombre) {
		return grupoDAO.buscarPorNombre(nombre);
	}

	@Transactional(readOnly = true)
	public List<Grupo> buscarPorGenero(Integer idGenero) {
		return grupoDAO.buscarPorGenero(idGenero);
	}

	@Transactional(readOnly = true)
	public List<Grupo> buscarPaginado(int offset, int size) {
	    return grupoDAO.buscarPaginado(offset, size);
	}


	/**
	 * Lo usamos para la paginacion
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public long contarTotalGrupos() {
		return grupoDAO.contarTotal();
	}

	@Transactional(readOnly = true)
	public List<Grupo> buscarConFiltros(String nombre, Integer idGenero, Integer idUsuarioFavoritos, Boolean activo,
			int offset, int limit) {
		return grupoDAO.buscarConFiltros(nombre, idGenero, idUsuarioFavoritos, activo, offset, limit);
	}

	@Transactional(readOnly = true)
	public long contarConFiltros(String nombre, Integer idGenero, Integer idUsuarioFavoritos, Boolean activo) {
		return grupoDAO.contarConFiltros(nombre, idGenero, idUsuarioFavoritos, activo);
	}

	@Transactional(readOnly = true)
	public Grupo buscarPorIdConFavoritos(Integer idGrupo) {
		return grupoDAO.buscarPorIdConFavoritos(idGrupo);
	}

	@Transactional(readOnly = true)
	public List<Grupo> obtenerFavoritosPorUsuario(Integer idUsuario) {

		return grupoDAO.obtenerFavoritosPorUsuario(idUsuario);
	}

}
