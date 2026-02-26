package es.ticketcenture.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.ticketcenture.entities.Evento;
import es.ticketcenture.entities.Grupo;
import es.ticketcenture.entities.Noticia;
import es.ticketcenture.entities.Usuario;
import es.ticketcenture.service.EventoService;
import es.ticketcenture.service.UsuarioService;
import es.ticketcenture.service.GrupoService;
import es.ticketcenture.service.NoticiaService;
import es.ticketcenture.utilities.MensajesExito;
import es.ticketcenture.utilities.Rutas;

/**
 * Clase que controla PrincipalController. Llamara al servicio necesario en cada
 * caso
 *
 */
@Controller

public class PrincipalController {

	
	private GrupoService grupoService;
	private EventoService eventoService;
	private UsuarioService usuarioService;
	private NoticiaService noticiaService;
	
	@Autowired
	public PrincipalController(GrupoService grupoService, EventoService eventoService, 
								UsuarioService usuarioService, NoticiaService noticiaService) {
		this.grupoService = grupoService;
		this.eventoService = eventoService;
		this.usuarioService = usuarioService;
		this.noticiaService = noticiaService;
		
	}


	/**
	 * Este metodo nos lleva a la vista principal.
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(Rutas.BASE_PRINCIPAL)
	public String mostrarPrincipal(HttpServletRequest request, Model model) {
		// LO QUE HABIA
		// Usuario usuario = (Usuario)
		// request.getSession().getAttribute(MensajesExito.USUARIO_LOGUEADO);

		// PARA QUE FUNCIONE FAVORITOS EN PRINCIPAL
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioSesion");

		// MODIFICAMOS PARA CARGAR LOS FAVORITOS TAMBIEN
		if (usuario != null) {
			List<Grupo> favoritos = grupoService.obtenerFavoritosPorUsuario(usuario.getIdUsuario());
			model.addAttribute("favoritos", favoritos);
		}

		model.addAttribute("usuario", usuario);

		// MODIFICAMOS PARA CARGAR LOS 5 PRÓXIMOS EVENTOS
		List<Evento> proximosEventos = eventoService.buscarProximosEventos(5);
		model.addAttribute("proximosEventos", proximosEventos);

		// COMPROBAMOS SI EL USUARIO TIENE CARRITO ACTIVO
		boolean carritoActivo = false;
		if (usuario != null) {
			carritoActivo = usuarioService.usuarioTieneCarritoActivo(usuario.getIdUsuario());
		}
		model.addAttribute("carritoActivo", carritoActivo);

		
		
		//MODIFICAMOS PARA CARGAR LAS NOTICIAS DE LAS ULTIMAS 24H DE LOS GRUPOS
		//FAVORITOS DEL USUARIO EN SESION
		if(usuario != null) {
			List<Noticia> noticiasRecientesFavoritos = noticiaService.buscarNoticiasRecientesFavoritos(usuario.getIdUsuario()); 
			model.addAttribute("noticiasRecientesFavoritos", noticiasRecientesFavoritos);
		}
    
		// Últimas 5 noticias
		List<Noticia> ultimasNoticias = noticiaService.obtenerUltimasNoticias();
		model.addAttribute("ultimasNoticias", ultimasNoticias);

		return Rutas.PRINCIPAL; // /WEB-INF/views/principal/principal.jsp
	}

}
