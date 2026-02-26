package es.ticketcenture.controller;
 
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
import es.ticketcenture.entities.Carrito;
import es.ticketcenture.entities.DescuentoUsuario;
import es.ticketcenture.entities.Usuario;
import es.ticketcenture.service.CarritoService;
import es.ticketcenture.utilities.MensajesError;
import es.ticketcenture.utilities.MensajesExito;
import es.ticketcenture.utilities.MessageManager;
import es.ticketcenture.utilities.Rutas;
import es.ticketcenture.utilities.Vistas;
 
@Controller
@RequestMapping(Rutas.BASE_CARRITO)
public class CarritoController {
 
	@Autowired
	private CarritoService carritoService;
 
	@PostMapping(Rutas.ANADIR_CARRITO)
	public String anadirAlCarrito(@RequestParam("idEvento") Integer idEvento,
			@RequestParam(value = "codigoPromocional", required = false) String codigoPromocional,
			HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
 
		Usuario usuarioSesion = (Usuario) session.getAttribute("usuarioSesion");


		if (usuarioSesion == null) {
			redirectAttributes.addFlashAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.ERROR_SESION_REQUERIDA));
			return "redirect:" + Rutas.BASE_USUARIOS + Rutas.FORMULARIO_LOGIN;
		}

		Integer idUsuario = usuarioSesion.getIdUsuario();
 
		try {
			boolean seAnadio = carritoService.anadirEntradasDesdeFormulario(idUsuario, idEvento, request,
					codigoPromocional);
 
			if (!seAnadio) {
				redirectAttributes.addFlashAttribute(MensajesError.MENSAJE_ERROR,
						MessageManager.get(MensajesError.CARRITO_SIN_ENTRADAS));
			} else {
				redirectAttributes.addFlashAttribute(MensajesExito.MENSAJE_EXITO,
						MessageManager.get(MensajesExito.CARRITO_ITEM_ANADIDO));
			}
 

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
		}
 
		return Rutas.REDIRIGIR_A_EVENTO + idEvento;
	}
 
	@PostMapping(Rutas.ELIMINAR_ITEM)
	public String eliminarItemDelCarrito(@RequestParam Integer idItem, RedirectAttributes redirect) {


		try {
			carritoService.eliminarItemDelCarrito(idItem);

			redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO,
					MessageManager.get(MensajesExito.CARRITO_ITEM_ELIMINADO));

		} catch (Exception e) {
			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
		}

		return "redirect:" + Rutas.BASE_CARRITO + Rutas.MOSTRAR_CARRITO;
	}
 
	@GetMapping(Rutas.MOSTRAR_CARRITO)
	public String mostrarCarrito(HttpSession session, Model model) {
 
		Usuario usuarioSesion = (Usuario) session.getAttribute("usuarioSesion");
 
		if (usuarioSesion == null) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.ERROR_SESION_REQUERIDA));
			return Vistas.PRINCIPAL;
		}
 
		Integer idUsuario = usuarioSesion.getIdUsuario();
 
		Carrito carrito = carritoService.obtenerCarritoActivoConTodo(idUsuario);
 
		DescuentoUsuario descuento = carritoService.obtenerDescuentoUsuario(usuarioSesion);
		
		BigDecimal totalConDescuento = carritoService.calcularTotalConDescuento(carrito, descuento);
		
		model.addAttribute("carrito", carrito);
		model.addAttribute("descuentoUsuario", descuento); 
		model.addAttribute("totalConDescuento", totalConDescuento);
 
		return Vistas.CARRITO;
	}
	
	@PostMapping(Rutas.CONFIRMAR_CARRITO)
	public String confirmarCompra(@RequestParam("direccionEnvio") String direccionEnvio,
									@RequestParam("direccionFacturacion") String direccionFacturacion,
									HttpSession session, 
									RedirectAttributes redirectAttributes) {

	    // 1. Validar sesión
	    Usuario usuarioSesion = (Usuario) session.getAttribute("usuarioSesion");

	    if (usuarioSesion == null) {
	        redirectAttributes.addFlashAttribute(
	            MensajesError.MENSAJE_ERROR,
	            MessageManager.get(MensajesError.ERROR_SESION_REQUERIDA)
	        );
	        return "redirect:" + Rutas.BASE_USUARIOS + Rutas.FORMULARIO_LOGIN;
	    }

	    Integer idUsuario = usuarioSesion.getIdUsuario();

	    try {
	        // 2. Confirmar compra en el servicio
	        carritoService.confirmarCompra(idUsuario, direccionEnvio, direccionFacturacion);

	        // 3. Mensaje de éxito
	        redirectAttributes.addFlashAttribute(
	            MensajesExito.MENSAJE_EXITO,
	            MessageManager.get(MensajesExito.COMPRA_REALIZADA)
	        );
	        return "redirect:" + Rutas.BASE_CARRITO + Rutas.CONFIRMACION_COMPRA;

	    } catch (Exception e) {
	        // 4. Error → mostrar mensaje
	        redirectAttributes.addFlashAttribute(
	            MensajesError.MENSAJE_ERROR,
	            MessageManager.get(e.getMessage())
	        );
	    }

	    // 5. Redirigir al carrito
	    return "redirect:" + Rutas.BASE_CARRITO + Rutas.MOSTRAR_CARRITO;
	}
	
	
	
	
	@GetMapping(Rutas.CONFIRMACION_COMPRA)
	public String mostrarConfirmacionCompra() {
	    return Vistas.CONFIRMACION_COMPRA;
	}

 
}