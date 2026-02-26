package es.ticketcenture.controller;

import es.ticketcenture.entities.Actividad;
import es.ticketcenture.entities.Carrito;
import es.ticketcenture.entities.Direccion;
import es.ticketcenture.entities.Usuario;
import es.ticketcenture.service.ActividadService;
import es.ticketcenture.service.CarritoService;
import es.ticketcenture.service.UsuarioService;
import es.ticketcenture.utilities.Constantes;
import es.ticketcenture.utilities.MensajesError;
import es.ticketcenture.utilities.MensajesExito;
import es.ticketcenture.utilities.MessageManager;
import es.ticketcenture.utilities.Rutas;
import es.ticketcenture.utilities.Vistas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Clase controladora de usuarios. Llama al servicio correspondiente.
 * 
 */
@Controller
@RequestMapping(Rutas.BASE_USUARIOS)
public class UsuarioController {

	/** Servicio de usuario */
	private UsuarioService usuarioService;

	/**
	 * Servicio de actividad
	 */
	private ActividadService actividadService;

	/**
	 * Para el historico de compras
	 */
	private CarritoService carritoService;
	
	/**
	 * Instancia un nuevo servicio de usuario.
	 *
	 * @param usuarioService Servicio de usuario
	 */
	@Autowired
	public UsuarioController(UsuarioService usuarioService, ActividadService actividadService, CarritoService carritoService) {
		this.usuarioService = usuarioService;
		this.actividadService = actividadService;
		this.carritoService = carritoService;
	}

	/**
	 * Mostrar formulario.
	 *
	 * @param model Modelo
	 * @return Jsp de destino
	 */
	@GetMapping(Rutas.FORMULARIO_REGISTRO_USUARIOS)
	public String mostrarFormulario(Model model) {
		model.addAttribute(Constantes.USUARIO, new Usuario());
		model.addAttribute(Constantes.DIRECCION_ENVIO, new Direccion());
		model.addAttribute(Constantes.DIRECCION_FACTURACION, new Direccion());

		return Vistas.REGISTRO_USUARIOS;

	}

	/**
	 * Registrar un usuario en la BBDD
	 *
	 * @param usuario Usuario a registrar
	 * @param model   Modelo
	 * @return JSP con mensaje
	 */

	@PostMapping(Rutas.REGISTRO_USUARIOS)
	public String registrar(@Valid @ModelAttribute(Constantes.USUARIO) Usuario usuario, BindingResult bindingResult,
			@RequestParam(value = Constantes.IGUAL_ENVIO, required = false) String igualEnvio, Model model,
			HttpServletRequest request) {

		if (bindingResult.hasErrors()) {

			// Si hay errores, volvemos al formulario mostrando mensajes
			return Vistas.REGISTRO_USUARIOS;
		}
		try {

			// Registrar usuario
			usuarioService.registrarUsuario(usuario, igualEnvio, request);
			// Login tras registro
			request.getSession().setAttribute("usuarioSesion", usuario);
			model.addAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.USUARIO_REGISTRADO));

			return Rutas.REDIRIGIR_A_PRINCIPAL;

		} catch (IllegalArgumentException e) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
			return Vistas.REGISTRO_USUARIOS;
		}

	}

	/**
	 * Metodo que enlaza al inicio de sesion de los usuarios
	 * 
	 * @return JSP de Inicio de Sesion
	 */
	@GetMapping(Rutas.FORMULARIO_LOGIN)
	public String mostrarInicioSesionUsuario() {
		return Vistas.LOGIN;
	}

	/**
	 * Metodo que controla el proceso de login
	 * 
	 * @param email
	 * @param password
	 * @param request
	 * @param model
	 * @return
	 */

	@PostMapping(Rutas.LOGIN)

	public String procesarLogin(@RequestParam(Constantes.EMAIL) String email,
			@RequestParam(Constantes.PASSWORD) String password, HttpServletRequest request, Model model) {
		try {
			Usuario usuario = usuarioService.buscaUsuario(email, password, request);
			// El loguin ha sido correcto
			request.getSession().setAttribute("usuarioSesion", usuario);
			model.addAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.USUARIO_LOGUEADO));

			// Recuperar URL del filtro (LO HE MODIFICADO PORQUE SI NO AL INICIAR SESIÓN ME PONE LA IMAGEN DEL EVENTO)
			String destino = (String) request.getSession().getAttribute(Constantes.URL_DESTINO);
			if (destino == null || destino.isBlank() || destino.contains("/usuarios/formularioLogin")
					|| destino.contains("/usuarios/login")) {

				return Rutas.REDIRIGIR_A_PRINCIPAL;
			}

			request.getSession().removeAttribute(Constantes.URL_DESTINO);
			return "redirect:" + destino;
		} catch (IllegalArgumentException e) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
			// Redirigimos a la pagina que queremos
			return Rutas.A_FORM_LOGIN;
		}

	}

	/**
	 * Método que cierra la sesión del usuario SE ACTIVA LA INYECCION DE
	 * DEPENDENCIAS DEL SERVICIO DE ACTIVIDAD
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(Rutas.LOGOUT)
	public String logout(HttpServletRequest request) {
		// Recuperar la sesión actual
		var session = request.getSession(false);

		if (session != null) {
			// Recuperar el usuario antes de invalidar la sesión
			Usuario usuario = (Usuario) session.getAttribute("usuarioSesion");

			if (usuario != null) {
				// Registrar actividad de cierre de sesión
				actividadService.insertarActividad(usuario, MensajesExito.CIERRE_SESION, request.getRemoteAddr());
			}
			// Invalidar toda la sesión
			session.invalidate();
		}

		// Redirigir a la p�gina principal o al login
		return Rutas.REDIRIGIR_A_PRINCIPAL;

	}

	/**
	 * Metodo que enlaza al formulario de recuperacion de password
	 *
	 * @return JSP de recuperacion de password
	 */
	@GetMapping(Rutas.RECUPERAR_PASSWORD)
	public String mostrarRecuperarPassword() {
		return Vistas.RECUPERAR_PASSWORD;
	}

	/**
	 * Procesa el formulario de recuperacion de password
	 *
	 * @param email             Email del usuario
	 * @param codigoSimulado    Codigo introducido en el popup (simulado)
	 * @param nuevaPassword     Nueva password
	 * @param confirmarPassword Confirmacion de la nueva password
	 * @param model             Modelo
	 * @return JSP de destino
	 */
	@PostMapping(Rutas.RECUPERAR_PASSWORD)
	public String procesarRecuperarPassword(@RequestParam(Constantes.EMAIL) String email,
			@RequestParam(Constantes.CODIGO_SIMULADO) String codigoSimulado,
			@RequestParam(Constantes.NUEVA_PASSWORD) String nuevaPassword,
			@RequestParam(Constantes.CONFIRMAR_PASSWORD) String confirmarPassword, Model model,
			HttpServletRequest request) {

		// Validaciones rapidas
		if (codigoSimulado == null || codigoSimulado.isEmpty()) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.VALIDAR_CODIGO));
			return Vistas.RECUPERAR_PASSWORD;

		}

		if (!nuevaPassword.equals(confirmarPassword)) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.PASSWORD_NO_COINCIDEN));

			return Vistas.RECUPERAR_PASSWORD;

		}

		try {
			// Delegar al servicio
			usuarioService.recuperarPassword(email, nuevaPassword, request);

			model.addAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.PASSWORD_ACTUALIZADA));
			return Vistas.LOGIN;

		} catch (IllegalArgumentException e) {
			// Capturar errores
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
			return Vistas.RECUPERAR_PASSWORD;
		}
	}

	/**
	 * Metodo que modifica los datos del usuario
	 * 
	 * @return
	 */
	@PostMapping(Rutas.MODIFICAR_USUARIO)
	// public String modificarUsuario(@ModelAttribute(Constantes.USUARIO) Usuario
	// usuario, Model model) {
	public String modificarUsuario(HttpServletRequest request, Usuario usuarioFormulario, Model model) {
		try {
			Usuario usuarioSesion = (Usuario) request.getSession().getAttribute("usuarioSesion");
			usuarioService.modificarDatosUsuario(usuarioSesion, usuarioFormulario);
			model.addAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.DATOS_MODIFICADOS));

		} catch (IllegalArgumentException e) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));

		}
		return Vistas.PERFIL_USUARIO;
	}

	/**
	 * Procesa la baja de usuario
	 *
	 * @param idUsuario Identificador
	 * @param request
	 * @param model     Modelo
	 * @return Jsp de destion
	 */
	@PostMapping(Rutas.BAJA)
	public String darDeBajaUsuario(@RequestParam(Constantes.IDUSUARIO) Integer idUsuario, HttpServletRequest request,
			Model model) {
		try {
			usuarioService.darDeBajaUsuario(idUsuario, request);
			model.addAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.BAJA_CORRECTA));
		} catch (IllegalArgumentException e) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
		}
		return Vistas.LOGIN; // JSP de login tras la baja
	}

	/**
	 * M�todo que enlace al perfil del usuario logeado
	 * 
	 * @return JSP para el perfil del usuario
	 */
	@GetMapping(Rutas.PERFIL_USUARIO)
	public String mostrarPerfilUsuario(HttpServletRequest request, Model model) {
		Usuario usuarioSesion = (Usuario) request.getSession().getAttribute("usuarioSesion");

//	    if (usuarioSesion == null) {
//	        return Rutas.REDIRIGIR_A_LOGIN;
//	    }
//	    // Traer el usuario con direcciones inicializadas desde el servicio
//	    Usuario usuario = usuarioService.obtenerUsuarioConDirecciones(usuarioSesion.getIdUsuario());
		model.addAttribute(Constantes.USUARIO, usuarioSesion);

		// LocalDateTime fecha = LocalDateTime.now();
		List<Actividad> actividades = actividadService.obtenerActividades(usuarioSesion);
		model.addAttribute(Constantes.ACTIVIDADES, actividades);
		model.addAttribute(Constantes.FECHA_INICIO, null);
		model.addAttribute(Constantes.FECHA_FIN, null);

		return Vistas.PERFIL_USUARIO;
	}

	/**
	 * Metodo que procesa la actividad del usuario por fechas
	 * 
	 * @param fechaInicioStr
	 * @param fechaFinStr
	 * @param session
	 * @param model
	 * @return
	 */
	@PostMapping(Rutas.PERFIL_USUARIO)
	public String mostrarPerfilUsuario(

			@RequestParam(value = Constantes.FECHA_INICIO, required = false) String fechaInicioStr,
			@RequestParam(value = Constantes.FECHA_FIN, required = false) String fechaFinStr,

			HttpSession session, Model model) {

		Usuario usuarioSesion = (Usuario) session.getAttribute("usuarioSesion");
		model.addAttribute(Constantes.USUARIO, usuarioSesion);

		// Conversión manual
		LocalDate fechaInicio = null;
		LocalDate fechaFin = null;

		DateTimeFormatter formatter = Constantes.FORMATTER_FECHA;

		try {
			if (fechaInicioStr != null && !fechaInicioStr.isBlank()) {
				fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
			}
			if (fechaFinStr != null && !fechaFinStr.isBlank()) {
				fechaFin = LocalDate.parse(fechaFinStr, formatter);
			}
		} catch (Exception e) {
			// MessageManager.get("ERROR_FORMATO_FECHA") se coge la constante de
			// mensajes.properties
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get("ERROR_FORMATO_FECHA"));
			return Vistas.PERFIL_USUARIO;
		}

		// Si ambas fechas están vacías, no filtrar
		if (fechaInicio == null && fechaFin == null) {
			// MessageManager.get("ERROR_FECHAS_VACIAS") se coge la constante de
			// mensajes.properties
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get("ERROR_FECHAS_VACIAS"));
			return Vistas.PERFIL_USUARIO;
		}

		// Si solo hay fechaInicio → fechaFin = hoy
		if (fechaInicio != null && fechaFin == null) {
			fechaFin = LocalDate.now();
		}

		// Si solo hay fechaFin → fechaInicio = hace 1 año (o lo que se quiera)
		if (fechaFin != null && fechaInicio == null) {
			fechaInicio = fechaFin.minusYears(1);
		}

		// Convertir a LocalDateTime
		LocalDateTime inicio = fechaInicio.atStartOfDay();
		LocalDateTime fin = fechaFin.atTime(23, 59, 59);

		// Validación de rango
		if (fin.isBefore(inicio)) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.FECHA_FIN_DEBE_SER_MAYOR));
			return Vistas.PERFIL_USUARIO;
		}

		List<Actividad> actividades = actividadService.obtenerActividades(usuarioSesion, inicio, fin);

		model.addAttribute(Constantes.ACTIVIDADES, actividades);
		model.addAttribute(Constantes.FECHA_INICIO, fechaInicio);
		model.addAttribute(Constantes.FECHA_FIN, fechaFin);

		if (actividades.isEmpty()) {
			model.addAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.NO_SE_ENCONTRARON_ACTIVIDADES));
		}

		return Vistas.PERFIL_USUARIO;
	}

	/**
	 * Metodo que enlaza con el formulario de cambio de password
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping("/cambiarPassword")
	public String mostrarCambiarPassword(HttpSession session, Model model) {
		Usuario usuarioSesion = (Usuario) session.getAttribute("usuarioSesion");
		if (usuarioSesion == null) {
			return Rutas.REDIRIGIR_A_LOGIN;
		}

		// Pasamos email al form
		model.addAttribute("email", usuarioSesion.getEmail());
		// Indicamos que venimos desde el perfil para cambiar textos en el formulario
		model.addAttribute("modo", "cambioPassword");

		return Vistas.RECUPERAR_PASSWORD;
	}

	/**
	 * Metodo que procesa el cambio de password
	 * 
	 * @param passwordActual
	 * @param passwordNueva
	 * @param passwordNueva2
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@PostMapping("/cambiarPassword")
	public String procesarCambiarPassword(@RequestParam("codigoSimulado") String codigoSimulado,
			@RequestParam("nuevaPassword") String nuevaPassword,
			@RequestParam("confirmarPassword") String confirmarPassword, HttpSession session,
			HttpServletRequest request, Model model) {
		Usuario usuarioSesion = (Usuario) session.getAttribute("usuarioSesion");
		if (usuarioSesion == null) {
			return Rutas.REDIRIGIR_A_LOGIN;
		}
		// Validación rápida del código
		if (codigoSimulado == null || codigoSimulado.isBlank()) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.VALIDAR_CODIGO));
			return Vistas.RECUPERAR_PASSWORD;
		}

		// Validar contraseñas
		if (!nuevaPassword.equals(confirmarPassword)) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.PASSWORD_NO_COINCIDEN));
			return Vistas.RECUPERAR_PASSWORD;
		}
		try {
			usuarioService.actualizarPassword(usuarioSesion, nuevaPassword, request);
			model.addAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.PASSWORD_ACTUALIZADA));
			return Rutas.REDIRIGIR_A_PRINCIPAL;
		} catch (IllegalArgumentException e) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
			return Vistas.RECUPERAR_PASSWORD;
		}
	}
	
	@GetMapping(Rutas.MOSTRAR_HISTORICO)
	public String mostrarHistorialCompras(HttpSession session, Model model) {

	    Usuario usuarioSesion = (Usuario) session.getAttribute("usuarioSesion");

	    if (usuarioSesion == null) {
	        return Rutas.REDIRIGIR_A_LOGIN;
	    }

	    // Llamar al servicio para obtener las compras del usuario
	    List<Carrito> compras = carritoService.obtenerHistoricoCompras(usuarioSesion.getIdUsuario());

	    model.addAttribute("compras", compras);

	    return Vistas.HISTORICO_COMPRAS;
	}

}