package es.ticketcenture.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.ticketcenture.entities.Usuario;
import es.ticketcenture.service.FavoritoService;
import es.ticketcenture.utilities.Rutas;

/**
 * Controlador que gestiona la tramitación de favoritos
 * 
 *
 */
@Controller
@RequestMapping(Rutas.BASE_GRUPOS)
public class FavoritoController {

	private final FavoritoService favoritoService;

	@Autowired
	public FavoritoController(FavoritoService favoritoService) {
		this.favoritoService = favoritoService;
	}

	@PostMapping(Rutas.MARCAR_FAVORITOS)
	public String marcarFavorito(@RequestParam Integer idGrupo, HttpSession session, HttpServletRequest request) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioSesion");
		favoritoService.agregarFavorito(idGrupo, usuario, request);
		return Rutas.REDIRECT + Rutas.BASE_GRUPOS + Rutas.DETALLE_GRUPO + Rutas.PARAM_ID + idGrupo;
	}

	@PostMapping(Rutas.DESMARCAR_FAVORITOS)
	public String desmarcarFavorito(@RequestParam Integer idGrupo, HttpSession session, HttpServletRequest request) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioSesion");
		favoritoService.eliminarFavorito(idGrupo, usuario, request);
		return Rutas.REDIRECT + Rutas.BASE_GRUPOS + Rutas.DETALLE_GRUPO + Rutas.PARAM_ID + idGrupo;

	}

}
