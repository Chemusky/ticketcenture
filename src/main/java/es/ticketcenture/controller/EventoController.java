package es.ticketcenture.controller;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.ticketcenture.dto.CompradorEventoDTO;
import es.ticketcenture.entities.Butaca;
import es.ticketcenture.entities.Evento;
import es.ticketcenture.entities.GaleriaEvento;
import es.ticketcenture.entities.Grupo;
import es.ticketcenture.entities.Municipio;
import es.ticketcenture.entities.TipoAsiento;
import es.ticketcenture.entities.Usuario;
import es.ticketcenture.service.CarritoService;
import es.ticketcenture.service.EventoService;
import es.ticketcenture.service.GaleriaEventoService;
import es.ticketcenture.service.GrupoService;
import es.ticketcenture.service.MunicipioService;
import es.ticketcenture.service.TipoAsientoService;
import es.ticketcenture.utilities.MensajesError;
import es.ticketcenture.utilities.MensajesExito;
import es.ticketcenture.utilities.MessageManager;
import es.ticketcenture.utilities.Rutas;
import es.ticketcenture.utilities.ValidarAdmin;
import es.ticketcenture.utilities.ValidarEvento;
import es.ticketcenture.utilities.ValidarImagenes;
import es.ticketcenture.utilities.Vistas;

/**
 * Controlador de Eventos
 */
@Controller
@RequestMapping(Rutas.BASE_EVENTOS)
public class EventoController {

	private final EventoService eventoService;
	private final GrupoService grupoService;
	private final MunicipioService municipioService;
	private final TipoAsientoService tipoAsientoService;
	private final GaleriaEventoService galeriaEventoService;
	private final CarritoService carritoService;

	/**
	 * Instancia el controlador de eventos
	 *
	 * @param eventoService        Servicio de eventos
	 * @param grupoService         Servicio de grupos
	 * @param municipioService     Servicio de municipios
	 * @param tipoAsientoService   Servicio de tipos de asiento
	 * @param galeriaEventoService Servicio de galeria de eventos
	 */
	@Autowired
	public EventoController(EventoService eventoService, GrupoService grupoService, MunicipioService municipioService,
			TipoAsientoService tipoAsientoService, GaleriaEventoService galeriaEventoService, CarritoService carritoService) {

		this.eventoService = eventoService;
		this.grupoService = grupoService;
		this.municipioService = municipioService;
		this.tipoAsientoService = tipoAsientoService;
		this.galeriaEventoService = galeriaEventoService;
		this.carritoService = carritoService;
	}

	/**
	 * Metodo para datetime-local → LocalDateTime
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

		binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				if (text == null || text.isEmpty()) {
					setValue(null);
				} else {
					setValue(LocalDateTime.parse(text, formatter));
				}
			}
		});
	}

	@InitBinder("evento")
	public void initBinderEvento(WebDataBinder binder) {

		// Mantener esto
		binder.setDisallowedFields("imagenPrincipal", "galeria", "butacas", "entradas");

		// Añadir solo el editor de Municipio
		binder.registerCustomEditor(Municipio.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String id) {
				setValue(municipioService.buscarPorId(Integer.parseInt(id)));
			}
		});
		binder.registerCustomEditor(Grupo.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String id) {
				setValue(grupoService.buscarPorId(Integer.parseInt(id)));
			}
		});

	}

	/**
	 * Metodo para mostrar el listado de eventos completo.
	 *
	 * @param model Modelo
	 * @return Lista de eventos
	 */
	// LISTADO
	@GetMapping(Rutas.LISTADO_EVENTOS_MUSICALES)
	public String mostrarEventos(@RequestParam(required = false) Integer municipio,
			@RequestParam(required = false) Integer grupo, @RequestParam(required = false) String fecha,
			@RequestParam(required = false) Boolean activo, @RequestParam(defaultValue = "1") int pagina,
			@RequestParam(defaultValue = "10") int tam, HttpSession session, Model model) {

		System.out.println(">>> ENTRANDO EN editarEvento <<<");

		Usuario u = (Usuario) session.getAttribute("usuarioSesion");

		Boolean filtroActivo = (u != null && u.isAdmin()) ? activo : Boolean.TRUE;

		boolean hayFiltros = (municipio != null && municipio != 0) || (grupo != null && grupo != 0)
				|| (fecha != null && !fecha.isBlank()) || (activo != null);

		int offset = (pagina - 1) * tam;

		List<Evento> eventos;
		long total;

		if (hayFiltros) {
			eventos = eventoService.buscarConFiltros(municipio, grupo, fecha, filtroActivo, offset, tam);
			total = eventoService.contarConFiltros(municipio, grupo, fecha, filtroActivo);
		} else {
			if (u != null && u.isAdmin()) {
				eventos = eventoService.buscarPaginado(offset, tam);
				total = eventoService.contarTotal();
			} else {
				eventos = eventoService.buscarConFiltros(null, null, null, true, offset, tam);
				total = eventoService.contarConFiltros(null, null, null, true);
			}
		}

		long totalPaginas = (long) Math.ceil((double) total / tam);

		model.addAttribute("eventos", eventos);
		model.addAttribute("pagina", pagina);
		model.addAttribute("tam", tam);
		model.addAttribute("total", total);
		model.addAttribute("totalPaginas", totalPaginas);

		// reenviamos filtros
		model.addAttribute("municipio", municipio);
		model.addAttribute("grupo", grupo);
		model.addAttribute("fecha", fecha);
		model.addAttribute("activo", activo);

		model.addAttribute("listaMunicipios", municipioService.buscarTodos());
		model.addAttribute("listaGrupos", grupoService.buscarTodos());

		return Vistas.LISTADO_EVENTOS;
	}

	/**
	 * Mostrar detalle del evento.
	 *
	 * @param idEvento Id del evento
	 * @param model    Modelo
	 * @return Ficha del evento
	 */
	// DETALLE
	@GetMapping(Rutas.DETALLE_EVENTO)
	public String mostrarDetalleEvento(@RequestParam Integer idEvento, Model model) {

		Evento evento = eventoService.buscarPorId(idEvento);
		if (evento == null) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.EVENTO_ID_NO_EXISTE));
			return "redirect:" + Rutas.BASE_EVENTOS + Rutas.LISTADO_EVENTOS_MUSICALES;
		}
		// Agrego tambien el stock de entradas
		Map<TipoAsiento, Integer> stockPorTipo = eventoService.calcularStockEntradasPorTipo(evento);
		model.addAttribute("stockPorTipo", stockPorTipo);

		model.addAttribute("evento", evento);
		model.addAttribute("galeria", evento.getGaleria());
		model.addAttribute("butacas", evento.getButacas());
		model.addAttribute("entradas", evento.getEntradas());

		model.addAttribute("now", LocalDateTime.now()); // Para la fecha actual (no dejar comprar entradas).

		return Vistas.DETALLE_EVENTO;
	}

	/**
	 * Mostrar formulario de alta.
	 *
	 * @param session Sesion
	 * @param model   Modelo
	 * @return Formulario de alta
	 */
	// FORMULARIO ALTA
	@GetMapping(Rutas.CREAR_EVENTO)
	public String mostrarFormularioAlta(HttpSession session, Model model) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");
		if (!ValidarAdmin.esAdmin(admin)) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return "redirect:" + Rutas.BASE_EVENTOS + Rutas.LISTADO_EVENTOS_MUSICALES;
		}

		model.addAttribute("evento", new Evento());
		cargarListas(model);

		return Vistas.FORMULARIO_ALTA_EVENTO;
	}

	/**
	 * Metodo que llama al servicio de creacion de eventos.
	 *
	 * @param evento          Evento a crear
	 * @param result          BindingResult
	 * @param imagenPrincipal Imagen Principal
	 * @param imagenesGaleria Imagenes de la galeria
	 * @param session         Sesion
	 * @param request         Request
	 * @param model           Modelo
	 * @return Listado si se crea correctamente. Vuelta al formulario si no.
	 */
	// CREAR EVENTO
	@PostMapping(Rutas.CREAR_EVENTO)
	public String crearEvento(@Valid @ModelAttribute("evento") Evento evento, BindingResult result,
			@RequestParam("imagenPrincipal") MultipartFile imagenPrincipal,
			@RequestParam(value = "imagenesGaleria", required = false) MultipartFile[] imagenesGaleria,
			HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttrs, Model model) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");

		if (!ValidarAdmin.esAdmin(admin)) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return "redirect:" + Rutas.BASE_EVENTOS + Rutas.LISTADO_EVENTOS_MUSICALES;
		}

		if (result.hasErrors()) {
			cargarListas(model);
			return Vistas.FORMULARIO_ALTA_EVENTO;
		}

		try {
			// Validar imagen principal
			if (!ValidarImagenes.imagenPrincipalValida(imagenPrincipal)) {
				throw new IllegalArgumentException(MensajesError.IMAGEN_VACIA);
			}
			if (!ValidarImagenes.esImagenValida(imagenPrincipal)) {
				throw new IllegalArgumentException(MensajesError.IMAGEN_FORMATO);
			}

			evento.setImagenPrincipal(imagenPrincipal.getBytes());

			// RECOGER BUTACAS DEL FORM

			List<Butaca> butacas = new ArrayList<>();

			for (int i = 1; i <= 3; i++) {

				String tipoStr = request.getParameter("tipoAsiento_" + i);
				String filasStr = request.getParameter("filas_" + i);
				String asientosStr = request.getParameter("asientos_" + i);
				String precioStr = request.getParameter("precio_" + i);

				if (tipoStr == null || tipoStr.isBlank()) {
					continue;
				}

				// Valida que los campos no esten vacios
				ValidarEvento.validarCamposButaca(filasStr, asientosStr, precioStr);

				Butaca b = new Butaca();
				b.setEvento(evento);
				b.setTipoAsiento(tipoAsientoService.buscarPorId(Integer.parseInt(tipoStr)));
				b.setNumeroFilas(Integer.parseInt(filasStr));
				b.setNumeroAsientos(Integer.parseInt(asientosStr));
				b.setPrecio(new BigDecimal(precioStr));

				butacas.add(b);
			}

			// Asignar butacas al evento
			evento.setButacas(butacas);

			// Validar configuración de butacas
			ValidarEvento.validarConfiguracionEntradas(evento, false);

			// Generar entradas
			evento.setEntradas(new ValidarEvento().generarEntradas(evento));

			// Insertar evento
			eventoService.insertarEvento(evento, admin, request);

			// Añadir imágenes de galería
			if (imagenesGaleria != null) {
				for (MultipartFile img : imagenesGaleria) {
					if (!img.isEmpty()) {
						if (!ValidarImagenes.esImagenValida(img)) {
							throw new IllegalArgumentException(MensajesError.IMAGEN_FORMATO);
						}
						galeriaEventoService.insertar(new GaleriaEvento(evento, img.getBytes()));
					}
				}
			}

			redirectAttrs.addFlashAttribute(MensajesExito.MENSAJE_EXITO,
					MessageManager.get(MensajesExito.EVENTO_CREADO));

			return "redirect:" + Rutas.BASE_EVENTOS + Rutas.LISTADO_EVENTOS_MUSICALES;

		} catch (Exception e) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
			cargarListas(model);
			return Vistas.FORMULARIO_ALTA_EVENTO;
		}
	}

	/**
	 * Mostrar formulario editar.
	 *
	 * @param idEvento Id del evento
	 * @param session  Sesion
	 * @param model    Modelo
	 * @return Formulario de edicion de evento
	 */
	// FORMULARIO EDICIÓN
	@GetMapping(Rutas.EDITAR_EVENTO)
	public String mostrarFormularioEditar(@RequestParam Integer idEvento, HttpSession session, Model model) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");
		if (!ValidarAdmin.esAdmin(admin)) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return "redirect:" + Rutas.BASE_EVENTOS + Rutas.LISTADO_EVENTOS_MUSICALES;
		}

		Evento evento = eventoService.buscarPorId(idEvento);
		if (evento == null) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.EVENTO_ID_NO_EXISTE));
			return "redirect:" + Rutas.BASE_EVENTOS + Rutas.LISTADO_EVENTOS_MUSICALES;
		}

		model.addAttribute("evento", evento);
		model.addAttribute("galeria", evento.getGaleria());
		cargarListas(model);

		return Vistas.FORMULARIO_EDITAR_EVENTO;
	}

	/**
	 * Metodo que llama al servicio para actualizar un evento en la bbdd.
	 *
	 * @param evento            Evento a actualizar
	 * @param result            BindingResult
	 * @param imagenPrincipal   Imagen Principal
	 * @param imagenesGaleria   Imagenes Galeria
	 * @param regenerarEntradas Checkbox para regenerar o no
	 * @param session           Sesion
	 * @param request           Request
	 * @param model             Modelo
	 * @return Lista de eventos si se actualiza bien. Vuelta al formulario si no.
	 */
	@PostMapping(Rutas.EDITAR_EVENTO)
	public String editarEvento(@Valid @ModelAttribute("evento") Evento eventoForm, BindingResult result,
			@RequestParam("imagenPrincipal") MultipartFile imagenPrincipal,
			@RequestParam(value = "imagenesGaleria", required = false) MultipartFile[] imagenesGaleria,
			@RequestParam(value = "regenerarEntradas", defaultValue = "false") boolean regenerarEntradas,
			@RequestParam(required = false) String origen, HttpSession session, HttpServletRequest request,
			RedirectAttributes redirectAttrs, Model model) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");

		if (!ValidarAdmin.esAdmin(admin)) {
			redirectAttrs.addFlashAttribute("mensajeError", MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return "redirect:" + Rutas.BASE_EVENTOS + Rutas.LISTADO_EVENTOS_MUSICALES;
		}

		if (result.hasErrors()) {
			model.addAttribute("mensajeError", result.getFieldError().getDefaultMessage());
			cargarListas(model);
			return Vistas.FORMULARIO_EDITAR_EVENTO;
		}

		try {
			// 1. Recuperar evento original desde BD (MANAGED)
			Evento eventoBD = eventoService.buscarPorId(eventoForm.getIdEvento());

			if (eventoBD == null) {
				redirectAttrs.addFlashAttribute("mensajeError", MessageManager.get(MensajesError.EVENTO_ID_NO_EXISTE));
				return "redirect:" + Rutas.BASE_EVENTOS + Rutas.LISTADO_EVENTOS_MUSICALES;
			}

			// 2. Actualizar campos simples
			eventoBD.setNombreEvento(eventoForm.getNombreEvento());
			eventoBD.setDescripcionEvento(eventoForm.getDescripcionEvento());
			eventoBD.setFechaEvento(eventoForm.getFechaEvento());
			eventoBD.setMunicipio(eventoForm.getMunicipio());
			eventoBD.setGrupo(eventoForm.getGrupo());
			eventoBD.setActivo(request.getParameter("activo") != null);

			// 3. Imagen principal (solo si se sube una nueva)
			if (!imagenPrincipal.isEmpty()) {
				if (!ValidarImagenes.esImagenValida(imagenPrincipal)) {
					throw new IllegalArgumentException(MensajesError.IMAGEN_FORMATO);
				}
				eventoBD.setImagenPrincipal(imagenPrincipal.getBytes());
			}

			// 4. Reconstruir butacas SOLO si regenerarEntradas = true
			if (regenerarEntradas) {

				// Guardamos la lista original
				List<Butaca> originales = eventoBD.getButacas();

				// Creamos una lista nueva
				List<Butaca> nuevas = new ArrayList<>();

				// Reconstruimos cada butaca existente
				for (Butaca b : originales) {

					String filas = request.getParameter("filas_" + b.getIdButaca());
					String asientos = request.getParameter("asientos_" + b.getIdButaca());
					String precio = request.getParameter("precio_" + b.getIdButaca());

					Butaca nueva = new Butaca();
					nueva.setIdButaca(b.getIdButaca()); // mantener ID
					nueva.setEvento(eventoBD);
					nueva.setTipoAsiento(b.getTipoAsiento());
					nueva.setNumeroFilas(Integer.parseInt(filas));
					nueva.setNumeroAsientos(Integer.parseInt(asientos));
					nueva.setPrecio(new BigDecimal(precio));

					nuevas.add(nueva);
				}

				// Nueva butaca
				String tipoNuevo = request.getParameter("nuevoTipoAsiento");
				if (tipoNuevo != null && !tipoNuevo.isBlank()) {

					Butaca nueva = new Butaca();
					nueva.setEvento(eventoBD);
					nueva.setTipoAsiento(tipoAsientoService.buscarPorId(Integer.parseInt(tipoNuevo)));
					nueva.setNumeroFilas(Integer.parseInt(request.getParameter("nuevoFilas")));
					nueva.setNumeroAsientos(Integer.parseInt(request.getParameter("nuevoAsientos")));
					nueva.setPrecio(new BigDecimal(request.getParameter("nuevoPrecio")));

					nuevas.add(nueva);
				}

				// MUY IMPORTANTE: limpiar la colección original
				eventoBD.getButacas().clear();

				// Asignar la nueva colección
				eventoBD.setButacas(nuevas);

				// Validar configuración
				ValidarEvento.validarConfiguracionEntradas(eventoBD, true);
			}

			// 5. Actualizar evento (el service regenerará entradas si procede)
			eventoService.actualizarEvento(eventoBD, regenerarEntradas, admin, request);

			// 6. Añadir nuevas imágenes de galería
			if (imagenesGaleria != null) {
				for (MultipartFile img : imagenesGaleria) {
					if (!img.isEmpty()) {
						if (!ValidarImagenes.esImagenValida(img)) {
							throw new IllegalArgumentException(MensajesError.IMAGEN_FORMATO);
						}
						galeriaEventoService.insertar(new GaleriaEvento(eventoBD, img.getBytes()));
					}
				}
			}

			redirectAttrs.addFlashAttribute("mensajeExito", MessageManager.get(MensajesExito.EVENTO_ACTUALIZADO));

			if ("ficha".equals(origen)) {
				return "redirect:" + Rutas.BASE_EVENTOS + Rutas.DETALLE_EVENTO + "?idEvento=" + eventoBD.getIdEvento();
			}

			return "redirect:" + Rutas.BASE_EVENTOS + Rutas.LISTADO_EVENTOS_MUSICALES;

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("mensajeError", MessageManager.get(e.getMessage()));
			cargarListas(model);
			return Vistas.FORMULARIO_EDITAR_EVENTO;
		}
	}

	/**
	 * Eliminar evento.
	 *
	 * @param idEvento Id del evento a eliminar
	 * @param session  Sesion
	 * @param request  Request
	 * @param model    Modelo
	 * @return Lista de eventos
	 */
	// ELIMINAR EVENTO
	@PostMapping(Rutas.ELIMINAR_EVENTO)
	public String eliminarEvento(@RequestParam Integer idEvento, HttpSession session, RedirectAttributes redirectAttrs,
			HttpServletRequest request, Model model) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");

		try {
			eventoService.eliminarEvento(idEvento, admin, request);
			redirectAttrs.addFlashAttribute(MensajesExito.MENSAJE_EXITO,
					MessageManager.get(MensajesExito.EVENTO_ELIMINADO));

		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
		}

		return "redirect:" + Rutas.BASE_EVENTOS + Rutas.LISTADO_EVENTOS_MUSICALES;
	}

	/**
	 * Mostrar imagen principal.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	// MOSTRAR IMAGEN PRINCIPAL
	@GetMapping(value = Rutas.IMAGEN_PRINCIPAL, produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE,
			MediaType.IMAGE_GIF_VALUE })
	public ResponseEntity<byte[]> mostrarImagenPrincipal(@PathVariable Integer id) {

		Evento evento = eventoService.buscarPorId(id);
		if (evento == null || evento.getImagenPrincipal() == null) {
			return ResponseEntity.notFound().build();
		}

		byte[] imagen = evento.getImagenPrincipal();

		MediaType contentType = MediaType.IMAGE_JPEG;
		try (var is = new java.io.ByteArrayInputStream(imagen)) {
			String guessed = java.net.URLConnection.guessContentTypeFromStream(is);
			if (guessed != null) {
				contentType = MediaType.parseMediaType(guessed);
			}
		} catch (Exception ignore) {
		}

		return ResponseEntity.ok().contentType(contentType).body(imagen);
	}

	/**
	 * Mostrar imagen galeria.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	// MOSTRAR IMAGEN DE GALERÍA
	@GetMapping(value = Rutas.GALERIA_IMAGEN, produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE,
			MediaType.IMAGE_GIF_VALUE })
	public ResponseEntity<byte[]> mostrarImagenGaleria(@PathVariable Integer id) {

		GaleriaEvento img = galeriaEventoService.buscarPorId(id);
		if (img == null || img.getImagen() == null) {
			return ResponseEntity.notFound().build();
		}

		byte[] imagen = img.getImagen();

		MediaType contentType = MediaType.IMAGE_JPEG;
		try (var is = new java.io.ByteArrayInputStream(imagen)) {
			String guessed = java.net.URLConnection.guessContentTypeFromStream(is);
			if (guessed != null) {
				contentType = MediaType.parseMediaType(guessed);
			}
		} catch (Exception ignore) {
		}

		return ResponseEntity.ok().contentType(contentType).body(imagen);
	}

	/**
	 * Cargar listas.
	 *
	 * @param model Modelo
	 */
	// METODO AUXILIAR
	private void cargarListas(Model model) {
		model.addAttribute("municipios", municipioService.buscarTodos());
		model.addAttribute("grupos", grupoService.buscarTodos());
		model.addAttribute("tiposAsiento", tipoAsientoService.buscarTodos());
	}

	/**
	 * Muestra el listado de compradores de un evento.
	 *
	 * @param idEvento ID del evento.
	 * @param model    Datos enviados a la vista.
	 * @return Vista con el informe de compradores.
	 */
	@GetMapping(Rutas.EVENTO_COMPRADORES)
	public String mostrarCompradoresEvento(@PathVariable Integer idEvento, Model model) {

		// Datos del evento
		Evento evento = eventoService.buscarPorId(idEvento);

		// Listado de compradores agrupados
		List<CompradorEventoDTO> compradores = carritoService.obtenerCompradoresEvento(idEvento);

		// Fecha del informe
		model.addAttribute("fechaInforme", LocalDate.now());
		model.addAttribute("evento", evento);
		model.addAttribute("compradores", compradores);

		return Vistas.LISTADO_COMPRADORES_EVENTOS;
	}

}
