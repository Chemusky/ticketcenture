package es.ticketcenture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.ticketcenture.entities.Genero;
import es.ticketcenture.service.GeneroService;
import es.ticketcenture.utilities.MensajesError;
import es.ticketcenture.utilities.MensajesExito;
import es.ticketcenture.utilities.MessageManager;
import es.ticketcenture.utilities.Rutas;
import es.ticketcenture.utilities.Vistas;

@Controller
@RequestMapping(Rutas.BASE_GENERO)
public class GeneroController {

    private final GeneroService generoService;

    @Autowired
    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @GetMapping(Rutas.LISTADO_GENEROS)
    public String mostrarGeneros(Model model) {

        model.addAttribute("listaGeneros", generoService.buscarTodos());

        // Si no viene un género desde edición, ponemos uno vacío
        if (!model.containsAttribute("genero")) {
            model.addAttribute("genero", null);
        }

        return Vistas.GENEROS;
    }

    @PostMapping(Rutas.CREAR_GENERO)
    public String crearGenero(
            @ModelAttribute("genero") Genero genero,
            RedirectAttributes redirect) {

        try {
            generoService.insertar(genero);
            redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO,
                    MessageManager.get(MensajesExito.GENERO_CREADO));

        } catch (Exception e) {
            redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
                    MessageManager.get(e.getMessage()));
            redirect.addFlashAttribute("genero", genero);
        }

        return "redirect:" + Rutas.BASE_GENERO + Rutas.LISTADO_GENEROS;
    }

    @GetMapping(Rutas.EDITAR_GENERO)
    public String cargarEdicion(
            @RequestParam Integer idGenero,
            RedirectAttributes redirect) {

        Genero g = generoService.buscarPorId(idGenero);

        if (g == null) {
            redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
                    MessageManager.get(MensajesError.GENERO_NO_ENCONTRADO));
        } else {
            redirect.addFlashAttribute("genero", g);
        }

        return "redirect:" + Rutas.BASE_GENERO + Rutas.LISTADO_GENEROS;
    }

    @PostMapping(Rutas.EDITAR_GENERO)
    public String editarGenero(
            @ModelAttribute("genero") Genero genero,
            RedirectAttributes redirect) {

        try {
            generoService.actualizar(genero);
            redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO,
                    MessageManager.get(MensajesExito.GENERO_ACTUALIZADO));

        } catch (Exception e) {
            redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
                    MessageManager.get(e.getMessage()));
            redirect.addFlashAttribute("genero", genero);
        }

        return "redirect:" + Rutas.BASE_GENERO + Rutas.LISTADO_GENEROS;
    }

    @PostMapping(Rutas.ELIMINAR_GENERO)
    public String eliminarGenero(
            @RequestParam Integer idGenero,
            RedirectAttributes redirect) {

        try {
            generoService.eliminar(idGenero);
            redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO,
                    MessageManager.get(MensajesExito.GENERO_ELIMINADO));

        } catch (Exception e) {
            redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
                    MessageManager.get(e.getMessage()));
        }

        return "redirect:" + Rutas.BASE_GENERO + Rutas.LISTADO_GENEROS;
    }
}
