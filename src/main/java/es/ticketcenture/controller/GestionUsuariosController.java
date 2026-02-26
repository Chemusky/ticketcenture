package es.ticketcenture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.ticketcenture.entities.Usuario;
import es.ticketcenture.service.GestionUsuariosService;
import es.ticketcenture.utilities.MensajesError;
import es.ticketcenture.utilities.MensajesExito;
import es.ticketcenture.utilities.MessageManager;
import es.ticketcenture.utilities.Rutas;
import es.ticketcenture.utilities.Vistas;

@Controller
@RequestMapping(Rutas.BASE_USUARIOS)
public class GestionUsuariosController {

	private final GestionUsuariosService gestionUsuariosService;

	@Autowired
	public GestionUsuariosController(GestionUsuariosService gestionUsuariosService) {
		this.gestionUsuariosService = gestionUsuariosService;
	}

	/**
	 * Listado de usuarios con filtros
	 */
	@GetMapping(Rutas.LISTADO_USUARIOS)
	public String listado(@RequestParam(required = false) String nombre, @RequestParam(required = false) String email,
			@RequestParam(required = false) String rol, @RequestParam(required = false) String tipoCliente,
			@RequestParam(required = false) String estado, Model model) {

		model.addAttribute("listaUsuarios",
				gestionUsuariosService.listarConFiltros(nombre, email, rol, tipoCliente, estado));

		return Vistas.LISTADO_USUARIOS;
	}

	/**
	 * Mostrar formulario de modificación
	 */
	@GetMapping(Rutas.MODIFICAR_ADMIN)
	public String mostrarModificar(@RequestParam Integer id, Model model, RedirectAttributes redirect) {

		Usuario usuario = gestionUsuariosService.buscarPorId(id);

		if (usuario == null) {
			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.ERROR_USUARIO_INEXISTENTE));
			return "redirect:" + Rutas.BASE_USUARIOS + Rutas.LISTADO_USUARIOS;
		}

		model.addAttribute("usuario", usuario);
		return Vistas.MODIFICAR_PERFIL;
	}

	/**
	 * Guardar cambios del usuario (ADMIN)
	 */
	@PostMapping(Rutas.MODIFICAR_ADMIN)
	public String modificar(@RequestParam Integer idUsuario, @RequestParam String nombre,
			@RequestParam String apellidos, @RequestParam Integer telefono, @RequestParam String dni,
			@RequestParam String username, @RequestParam String email, @RequestParam(required = false) String admin,
			@RequestParam(required = false) String tipoUsuario, RedirectAttributes redirect) {

		try {
			gestionUsuariosService.actualizarUsuarioDesdeAdmin(idUsuario, nombre, apellidos, telefono, dni, username,
					email, admin, tipoUsuario);

			redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO,
					MessageManager.get(MensajesExito.EXITO_USUARIO_ACTUALIZADO));

		} catch (IllegalArgumentException e) {

			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));

		} catch (Exception e) {

			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.ERROR_ACTUALIZAR_USUARIO));
		}

		return "redirect:" + Rutas.BASE_USUARIOS + Rutas.LISTADO_USUARIOS;
	}

	/**
	 * Bloquear usuario
	 */
	@GetMapping(Rutas.BLOQUEAR_USUARIO)
	public String bloquear(@RequestParam Integer id, RedirectAttributes redirect) {

		try {
			gestionUsuariosService.bloquearUsuario(id);

			redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO,
					MessageManager.get(MensajesExito.EXITO_USUARIO_BLOQUEADO));

		} catch (Exception e) {
			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.ERROR_ACTUALIZAR_USUARIO));
		}

		return "redirect:" + Rutas.BASE_USUARIOS + Rutas.LISTADO_USUARIOS;
	}

	/**
	 * Desbloquear usuario
	 */
	@GetMapping(Rutas.DESBLOQUEAR_USUARIO)
	public String desbloquear(@RequestParam Integer id, RedirectAttributes redirect) {

		try {
			gestionUsuariosService.desbloquearUsuario(id);

			redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO,
					MessageManager.get(MensajesExito.EXITO_USUARIO_DESBLOQUEADO));

		} catch (Exception e) {
			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.ERROR_ACTUALIZAR_USUARIO));
		}

		return "redirect:" + Rutas.BASE_USUARIOS + Rutas.LISTADO_USUARIOS;
	}

	/**
	 * Eliminar usuario
	 */
	@GetMapping(Rutas.ELIMINAR_USUARIO)
	public String eliminar(@RequestParam Integer id, RedirectAttributes redirect) {

		try {
			gestionUsuariosService.eliminarUsuario(id);

			redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO,
					MessageManager.get(MensajesExito.EXITO_USUARIO_ELIMINADO));

		} catch (IllegalArgumentException e) {

			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));

		} catch (Exception e) {

			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.ERROR_USUARIO_CON_COMPRAS));
		}

		return "redirect:" + Rutas.BASE_USUARIOS + Rutas.LISTADO_USUARIOS;
	}
}
