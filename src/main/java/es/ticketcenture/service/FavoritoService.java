package es.ticketcenture.service;

import es.ticketcenture.daos.FavoritoDAO;
import es.ticketcenture.daos.GrupoDAO;
import es.ticketcenture.entities.Favorito;
import es.ticketcenture.entities.Grupo;
import es.ticketcenture.entities.Usuario;
import es.ticketcenture.utilities.Acciones;
import es.ticketcenture.utilities.MensajesError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class FavoritoService {

	private final FavoritoDAO favoritoDAO;
	private final GrupoDAO grupoDAO;
	private final ActividadService actividadService;

	@Autowired
	public FavoritoService(FavoritoDAO favoritoDAO, GrupoDAO grupoDAO, ActividadService actividadService) {
		this.favoritoDAO = favoritoDAO;
		this.grupoDAO = grupoDAO;
		this.actividadService = actividadService;
	}

	@Transactional
	public void agregarFavorito(Integer idGrupo, Usuario usuario, HttpServletRequest request) {

		if (usuario == null) {
			throw new IllegalArgumentException(MensajesError.USUARIO_NO_ENCONTRADO);
		}

		Grupo grupo = grupoDAO.buscarPorId(idGrupo);
		if (grupo == null) {
			throw new IllegalArgumentException(MensajesError.GRUPO_NO_ENCONTRADO);
		}

		// if (!grupo.isActivo()) { // throw new
		// IllegalArgumentException(MensajesError.GRUPO_NO_ENCONTRADO); // }

		Favorito existente = favoritoDAO.buscarPorUsuarioYGrupo(usuario.getIdUsuario(), idGrupo);
		if (existente != null) {
			throw new IllegalArgumentException(MensajesError.FAVORITO_YA_EXISTE);
		}

		Favorito favorito = new Favorito(usuario, grupo);
		favoritoDAO.insertar(favorito);

		actividadService.insertarActividad(usuario, Acciones.AGREGAR_FAVORITO, request.getRemoteAddr(),
				"Añadido a favoritos: " + grupo.getNombreGrupo());
	}

	@Transactional
	public void eliminarFavorito(Integer idGrupo, Usuario usuario, HttpServletRequest request) {

		if (usuario == null) {
			throw new IllegalArgumentException(MensajesError.USUARIO_NO_ENCONTRADO);
		}

		Favorito favorito = favoritoDAO.buscarPorUsuarioYGrupo(usuario.getIdUsuario(), idGrupo);
		if (favorito == null) {
			throw new IllegalArgumentException(MensajesError.FAVORITO_NO_EXISTE);
		}

		favoritoDAO.eliminar(favorito);

		actividadService.insertarActividad(usuario, Acciones.ELIMINAR_FAVORITO, request.getRemoteAddr(),
				"Eliminado de favoritos: " + favorito.getGrupo().getNombreGrupo());
	}

	@Transactional(readOnly = true)
	public List<Favorito> listarFavoritos(Integer idUsuario) {
		return favoritoDAO.buscarPorUsuario(idUsuario);
	}
}
