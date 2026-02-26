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

import es.ticketcenture.entities.DescuentoUsuario;
import es.ticketcenture.service.DescuentoUsuarioService;
import es.ticketcenture.utilities.MensajesError;
import es.ticketcenture.utilities.MensajesExito;
import es.ticketcenture.utilities.MessageManager;
import es.ticketcenture.utilities.Rutas;
import es.ticketcenture.utilities.Vistas;

@Controller
@RequestMapping(Rutas.BASE_DESCUENTO)
public class DescuentoController {

    private final DescuentoUsuarioService descuentoService;

    @Autowired
    public DescuentoController(DescuentoUsuarioService descuentoService) {
        this.descuentoService = descuentoService;
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
    @GetMapping(Rutas.LISTADO_DESCUENTOS)
    public String mostrarDescuentoUsuario(Model model) {

        model.addAttribute("listaDescuentos", descuentoService.buscarTodos());

        // Si no viene un descuento desde edición, ponemos uno vacío para alta
        if (!model.containsAttribute("descuento")) {
            model.addAttribute("descuento", null);
        }

        return Vistas.DESCUENTO_USUARIO;
    }

    // CREAR
    @PostMapping(Rutas.CREAR_DESCUENTO)
    public String crearDescuento(
            @ModelAttribute("descuento") DescuentoUsuario descuento,
            RedirectAttributes redirect) {

        try {
            descuentoService.insertar(descuento);
            redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.DESCUENTO_CREADO));
        } catch (Exception e) {
            redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
            redirect.addFlashAttribute("descuento", descuento);
        }

        return "redirect:" + Rutas.BASE_DESCUENTO + Rutas.LISTADO_DESCUENTOS;
    }

    // CARGAR FORMULARIO DE EDICIÓN (MISMA JSP)
    @GetMapping(Rutas.EDITAR_DESCUENTO)
    public String mostrarFormularioEditar(
            @RequestParam Integer idDescuento,
            RedirectAttributes redirect) {

        DescuentoUsuario d = descuentoService.buscarPorId(idDescuento);

        if (d == null) {
            redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.ERROR_DESCUENTO_NO_EXISTE));
        } else {
            redirect.addFlashAttribute("descuento", d);
        }

        return "redirect:" + Rutas.BASE_DESCUENTO + Rutas.LISTADO_DESCUENTOS;
    }

    // EDITAR
    @PostMapping(Rutas.EDITAR_DESCUENTO)
    public String editarDescuento(
            @ModelAttribute("descuento") DescuentoUsuario descuento,
            RedirectAttributes redirect) {

        try {
            descuentoService.actualizar(descuento);
            redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.DESCUENTO_ACTUALIZADO));
        } catch (Exception e) {
            redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
            redirect.addFlashAttribute("descuento", descuento);
        }

        return "redirect:" + Rutas.BASE_DESCUENTO + Rutas.LISTADO_DESCUENTOS;
    }

    // ELIMINAR
    @PostMapping(Rutas.ELIMINAR_DESCUENTO)
    public String eliminarDescuento(
            @RequestParam Integer idDescuento,
            RedirectAttributes redirect) {

        DescuentoUsuario d = descuentoService.buscarPorId(idDescuento);

        if (d == null) {
            redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.ERROR_DESCUENTO_NO_EXISTE));
        } else {
            descuentoService.eliminar(d);
            redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.DESCUENTO_ELIMINADO));
        }

        return "redirect:" + Rutas.BASE_DESCUENTO + Rutas.LISTADO_DESCUENTOS;
    }
}

