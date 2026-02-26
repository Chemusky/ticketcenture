package es.ticketcenture.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.ticketcenture.entities.Usuario;
import es.ticketcenture.utilities.MensajesError;
import es.ticketcenture.utilities.MessageManager;
import es.ticketcenture.utilities.Rutas;
import es.ticketcenture.utilities.ValidarAdmin;
import es.ticketcenture.utilities.Vistas;

/**
 * 
 * Clase que gestiona el Panel de Control
 *
 */

@Controller
@RequestMapping(Rutas.BASE_PANEL)
public class PanelControlController {

	@GetMapping(Rutas.PANEL_CONTROL)
	public String mostrarPanelControl(HttpSession session, Model model, RedirectAttributes redirectAttrs) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");

		// Validación de admin
		if (!ValidarAdmin.esAdmin(admin)) {
			redirectAttrs.addFlashAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return "redirect:" + Rutas.BASE_PRINCIPAL;
		} else {
			return Vistas.PANEL_CONTROL;
		}
	}

}
