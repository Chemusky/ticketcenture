package es.ticketcenture.controller;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.ticketcenture.entities.PromocionEvento;
import es.ticketcenture.entities.Evento;
import es.ticketcenture.service.PromocionEventoService;
import es.ticketcenture.utilities.MensajesError;
import es.ticketcenture.utilities.MensajesExito;
import es.ticketcenture.utilities.MessageManager;
import es.ticketcenture.utilities.Rutas;
import es.ticketcenture.utilities.Vistas;
import es.ticketcenture.service.EventoService;

@Controller
@RequestMapping(Rutas.BASE_PROMOCION)
public class PromocionController {

	private final PromocionEventoService promocionService;
	private final EventoService eventoService;

	@Autowired
	public PromocionController(PromocionEventoService promocionService, EventoService eventoService) {
		this.promocionService = promocionService;
		this.eventoService = eventoService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				if (text == null || text.isBlank()) {
					setValue(null);
				} else {
					setValue(LocalDate.parse(text));
				}
			}
		});
	}

	// LISTADO + FORMULARIO (ALTA)
	@GetMapping(Rutas.LISTADO_PROMOCIONES)
	public String mostrarPromocionEvento(Model model) {

		model.addAttribute("listaPromociones", promocionService.buscarTodos());
		model.addAttribute("listaEventos", eventoService.buscarTodos());

		// Si no viene una promoción desde edición, ponemos null para alta
		if (!model.containsAttribute("promocion")) {
			model.addAttribute("promocion", null);
		}

		return Vistas.PROMOCION_EVENTO;
	}

	// CREAR
	@PostMapping(Rutas.CREAR_PROMOCION)
	public String crearPromocion(@ModelAttribute("promocion") PromocionEvento promocion, @RequestParam Integer idEvento,
			RedirectAttributes redirect) {

		try {
			// Asignar evento manualmente (viene como idEvento en el form)
			Evento e = eventoService.buscarPorId(idEvento);
			promocion.setEvento(e);

			promocionService.insertar(promocion);
			redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.PROMOCION_CREADA));

		} catch (Exception e) {
			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
			redirect.addFlashAttribute("promocion", promocion);
		}

		return "redirect:" + Rutas.BASE_PROMOCION + Rutas.LISTADO_PROMOCIONES;
	}

	// CARGAR FORMULARIO DE EDICIÓN (MISMA JSP)
	@GetMapping(Rutas.EDITAR_PROMOCION)
	public String mostrarFormularioEditar(@RequestParam Integer idPromocion, RedirectAttributes redirect) {

		PromocionEvento p = promocionService.buscarPorId(idPromocion);

		if (p == null) {
			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.ERROR_PROMOCION_NO_EXISTE));
		} else {
			redirect.addFlashAttribute("promocion", p);
		}

		return "redirect:" + Rutas.BASE_PROMOCION + Rutas.LISTADO_PROMOCIONES;
	}

	// EDITAR
	@PostMapping(Rutas.EDITAR_PROMOCION)
	public String editarPromocion(@ModelAttribute("promocion") PromocionEvento promocion,
			@RequestParam Integer idEvento, RedirectAttributes redirect) {

		try {
			Evento e = eventoService.buscarPorId(idEvento);
			promocion.setEvento(e);

			promocionService.actualizar(promocion);
			redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.PROMOCION_ACTUALIZADA));

		} catch (Exception e) {
			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
			redirect.addFlashAttribute("promocion", promocion);
		}

		return "redirect:" + Rutas.BASE_PROMOCION + Rutas.LISTADO_PROMOCIONES;
	}

	// ELIMINAR
	@PostMapping(Rutas.ELIMINAR_PROMOCION)
	public String eliminarPromocion(@RequestParam Integer idPromocion, RedirectAttributes redirect) {

		PromocionEvento p = promocionService.buscarPorId(idPromocion);

		if (p == null) {
			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.ERROR_PROMOCION_NO_EXISTE));
		} else {
			promocionService.eliminar(p);
			redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.PROMOCION_ELIMINADA));
		}

		return "redirect:" + Rutas.BASE_PROMOCION + Rutas.LISTADO_PROMOCIONES;
	}
}
