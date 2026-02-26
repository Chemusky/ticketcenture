package es.ticketcenture.controller;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

import es.ticketcenture.entities.Evento;
import es.ticketcenture.entities.Grupo;
import es.ticketcenture.entities.Noticia;
import es.ticketcenture.entities.Usuario;
import es.ticketcenture.service.EventoService;
import es.ticketcenture.service.GrupoService;
import es.ticketcenture.service.NoticiaService;
import es.ticketcenture.utilities.MensajesError;
import es.ticketcenture.utilities.MensajesExito;
import es.ticketcenture.utilities.MessageManager;
import es.ticketcenture.utilities.Rutas;
import es.ticketcenture.utilities.ValidarAdmin;
import es.ticketcenture.utilities.ValidarImagenes;
import es.ticketcenture.utilities.Vistas;

/**
 * Controlador de noticias
 *
 */
@Controller
@RequestMapping(Rutas.BASE_NOTICIA)
public class NoticiaController {

	private NoticiaService noticiaService;
	private GrupoService grupoService;
	private EventoService eventoService;

	/**
	 * Instancia para el controlador de noticias
	 * 
	 * @param noticiaService
	 * @param grupoService
	 * @param eventoService
	 */
	@Autowired
	public NoticiaController(NoticiaService noticiaService, GrupoService grupoService, EventoService eventoService) {
		this.noticiaService = noticiaService;
		this.grupoService = grupoService;
		this.eventoService = eventoService;
	}

	/**
	 * Init Binder para fechas
	 *
	 * @param binder the binder
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

	/**
	 * Init Binder para noticia. Para las imagenes y conversion de texto
	 * 
	 * @param binder the binder
	 */
	@InitBinder("noticia")
	public void initBinderNoticia(WebDataBinder binder) {

		// Evita que Spring intente convertir MultipartFile → byte[]
		binder.setDisallowedFields("imagen");

		// Conversor para Grupo
		binder.registerCustomEditor(Grupo.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String id) {
				setValue(id == null || id.isEmpty() ? null : grupoService.buscarPorId(Integer.parseInt(id)));
			}
		});

		// Conversor para Evento
		binder.registerCustomEditor(Evento.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String id) {
				setValue(id == null || id.isEmpty() ? null : eventoService.buscarPorId(Integer.parseInt(id)));
			}
		});
	}

	/**
	 * Mostrar noticias musicales.
	 *
	 * @param pagina  the pagina
	 * @param tam     the tam
	 * @param session the session
	 * @param model   the model
	 * @return the string
	 */
	@GetMapping(Rutas.LISTADO_NOTICIAS)
	public String mostrarNoticiasMusicales(@RequestParam(required = false) String titulo,
			@RequestParam(required = false) String fechaPublicacion, @RequestParam(required = false) Integer idGrupo,
			@RequestParam(required = false) Boolean publicado,
			@RequestParam(required = false, defaultValue = "1") Integer pagina,
			@RequestParam(defaultValue = "10") int tam, HttpSession session, Model model) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");
		if (!ValidarAdmin.esAdmin(admin)) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return Rutas.REDIRECT + Rutas.BASE_PANEL + "/" + Rutas.PANEL_CONTROL;
		}

		if (pagina == null || pagina < 1) {
			pagina = 1;
		}

		List<Noticia> noticias = noticiaService.buscarConFiltros(titulo, idGrupo, publicado, fechaPublicacion, pagina,
				tam);

		long total = noticiaService.contarConFiltros(titulo, idGrupo, publicado);
		int totalPaginas = (int) Math.ceil((double) total / tam);

		model.addAttribute("listaNoticias", noticias);
		model.addAttribute("titulo", titulo);
		model.addAttribute("fechaPublicacion", fechaPublicacion);
		model.addAttribute("idGrupo", idGrupo);
		model.addAttribute("publicado", publicado);
		model.addAttribute("paginaActual", pagina);
		model.addAttribute("totalPaginas", totalPaginas);
		model.addAttribute("listaGrupos", grupoService.buscarTodos());

		return Vistas.LISTADO_NOTICIAS;
	}

	/**
	 * Mostrar formulario alta.
	 *
	 * @param session the session
	 * @param model   the model
	 * @return the string
	 */
	@GetMapping(Rutas.ALTA_NOTICIA)
	public String mostrarFormularioAlta(HttpSession session, Model model) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");
		if (!ValidarAdmin.esAdmin(admin)) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return Rutas.REDIRECT + Rutas.BASE_PANEL + "/" + Rutas.PANEL_CONTROL;
		}

		model.addAttribute("noticia", new Noticia());
		model.addAttribute("grupos", grupoService.buscarTodos());
		model.addAttribute("eventos", eventoService.buscarTodos());

		return Vistas.FORMULARIO_ALTA_NOTICIA;
	}

	/**
	 * Guardar noticia.
	 *
	 * @param noticia       the noticia
	 * @param bindingResult the binding result
	 * @param imagen        the imagen
	 * @param session       the session
	 * @param model         the model
	 * @param redirect      the redirect
	 * @return the string
	 */
	@PostMapping(Rutas.ALTA_NOTICIA)
	public String guardarNoticia(@Valid @ModelAttribute("noticia") Noticia noticia, BindingResult bindingResult,
			@RequestParam("imagen") MultipartFile imagen, HttpSession session, Model model,
			RedirectAttributes redirect) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");
		if (!ValidarAdmin.esAdmin(admin)) {
			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return Rutas.REDIRECT + Rutas.BASE_PANEL + "/" + Rutas.PANEL_CONTROL;
		}

		// Validación automática
		if (bindingResult.hasErrors()) {
			model.addAttribute("noticia", noticia);
			model.addAttribute("grupos", grupoService.buscarTodos());
			model.addAttribute("eventos", eventoService.buscarTodos());
			return Vistas.FORMULARIO_ALTA_NOTICIA;
		}

		try {
			int maxSize = 5 * 1024 * 1024;

			// Imagen NO obligatoria, pero si se sube → validar
			if (!imagen.isEmpty()) {

				if (!ValidarImagenes.esImagenValida(imagen)) {
					throw new IllegalArgumentException(MensajesError.IMAGEN_FORMATO);
				}

				if (!ValidarImagenes.tamanoValido(imagen, maxSize)) {
					throw new IllegalArgumentException(MensajesError.IMAGEN_TAMANO_EXCESIVO);
				}

				noticia.setImagen(imagen.getBytes());
			}
			// SIEMPRE guardar fecha de creación
			noticia.setFechaCreacion(LocalDateTime.now());
			if (Boolean.TRUE.equals(noticia.getPublicado())) {
				noticia.setFechaPublicacion(LocalDateTime.now());
			}

			noticiaService.insertar(noticia);

			redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.NOTICIA_CREADA));

		} catch (Exception e) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
			model.addAttribute("noticia", noticia);
			model.addAttribute("grupos", grupoService.buscarTodos());
			model.addAttribute("eventos", eventoService.buscarTodos());
			return Vistas.FORMULARIO_ALTA_NOTICIA;
		}

		return Rutas.REDIRECT + Rutas.BASE_NOTICIA + Rutas.LISTADO_NOTICIAS;
	}

	/**
	 * Mostrar formulario editar.
	 *
	 * @param idNoticia the id noticia
	 * @param session   the session
	 * @param model     the model
	 * @param redirect  the redirect
	 * @return the string
	 */
	@GetMapping(Rutas.EDITAR_NOTICIA)
	public String mostrarFormularioEditar(@RequestParam("idNoticia") Integer idNoticia, HttpSession session,
			Model model, RedirectAttributes redirect) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");
		if (!ValidarAdmin.esAdmin(admin)) {
			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return Rutas.REDIRECT + Rutas.BASE_PANEL + "/" + Rutas.PANEL_CONTROL;
		}

		Noticia noticia = noticiaService.buscarPorId(idNoticia);
		if (noticia == null) {
			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.NOTICIA_NO_ENCONTRADA));
			return Rutas.REDIRECT + Rutas.BASE_NOTICIA + Rutas.LISTADO_NOTICIAS;
		}

		model.addAttribute("noticia", noticia);
		model.addAttribute("grupos", grupoService.buscarTodos());
		model.addAttribute("eventos", eventoService.buscarTodos());

		return Vistas.FORMULARIO_EDITAR_NOTICIA;
	}

	/**
	 * Actualizar noticia.
	 *
	 * @param noticiaForm   the noticia form
	 * @param bindingResult the binding result
	 * @param imagen        the imagen
	 * @param session       the session
	 * @param model         the model
	 * @param redirect      the redirect
	 * @return the string
	 */
	@PostMapping(Rutas.EDITAR_NOTICIA)
	public String actualizarNoticia(@Valid @ModelAttribute("noticia") Noticia noticiaForm, BindingResult bindingResult,
			@RequestParam("imagen") MultipartFile imagen, HttpSession session, Model model,
			RedirectAttributes redirect) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");
		if (!ValidarAdmin.esAdmin(admin)) {
			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return Rutas.REDIRECT + Rutas.BASE_PANEL + "/" + Rutas.PANEL_CONTROL;
		}

		Noticia noticiaBD = noticiaService.buscarPorId(noticiaForm.getIdNoticia());
		if (noticiaBD == null) {
			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.NOTICIA_NO_ENCONTRADA));
			return Rutas.REDIRECT + Rutas.BASE_NOTICIA + Rutas.LISTADO_NOTICIAS;
		}

		// Validación automática
		if (bindingResult.hasErrors()) {
			model.addAttribute("noticia", noticiaForm);
			model.addAttribute("grupos", grupoService.buscarTodos());
			model.addAttribute("eventos", eventoService.buscarTodos());
			return Vistas.FORMULARIO_EDITAR_NOTICIA;
		}

		try {
			int maxSize = 5 * 1024 * 1024;

			// Copiar campos editables
			noticiaBD.setTitulo(noticiaForm.getTitulo());
			noticiaBD.setCuerpo(noticiaForm.getCuerpo());
			noticiaBD.setEnlace(noticiaForm.getEnlace());
			noticiaBD.setGrupo(noticiaForm.getGrupo());
			noticiaBD.setEvento(noticiaForm.getEvento());
			noticiaBD.setPublicado(noticiaForm.getPublicado());

			// Imagen NO obligatoria, pero si se sube validar
			if (!imagen.isEmpty()) {

				if (!ValidarImagenes.esImagenValida(imagen)) {
					throw new IllegalArgumentException(MensajesError.IMAGEN_FORMATO);
				}

				if (!ValidarImagenes.tamanoValido(imagen, maxSize)) {
					throw new IllegalArgumentException(MensajesError.IMAGEN_TAMANO_EXCESIVO);
				}

				noticiaBD.setImagen(imagen.getBytes());
			}
			// SOLO si ahora se publica y antes no tenía fecha
			if (Boolean.TRUE.equals(noticiaForm.getPublicado()) && noticiaBD.getFechaPublicacion() == null) {
				noticiaBD.setFechaPublicacion(LocalDateTime.now());
			}

			noticiaService.actualizar(noticiaBD);

			redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO,
					MessageManager.get(MensajesExito.NOTICIA_ACTUALIZADA));

		} catch (Exception e) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
			model.addAttribute("noticia", noticiaForm);
			model.addAttribute("grupos", grupoService.buscarTodos());
			model.addAttribute("eventos", eventoService.buscarTodos());
			return Vistas.FORMULARIO_EDITAR_NOTICIA;
		}

		return Rutas.REDIRECT + Rutas.BASE_NOTICIA + Rutas.LISTADO_NOTICIAS;
	}

	/**
	 * Elimina la noticia
	 * 
	 * @param idNoticia
	 * @param session
	 * @param redirect
	 * @return
	 */
	@PostMapping(Rutas.ELIMINAR_NOTICIA)
	public String eliminarNoticia(@RequestParam Integer idNoticia, HttpSession session, RedirectAttributes redirect) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");
		if (!ValidarAdmin.esAdmin(admin)) {
			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return Rutas.REDIRECT + Rutas.BASE_PANEL + "/" + Rutas.PANEL_CONTROL;
		}

		try {
			noticiaService.eliminar(idNoticia);

			redirect.addFlashAttribute(MensajesExito.MENSAJE_EXITO,
					MessageManager.get(MensajesExito.NOTICIA_ELIMINADA));

		} catch (Exception e) {
			redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.ERROR_ELIMINAR_NOTICIA));
		}

		return Rutas.REDIRECT + Rutas.BASE_NOTICIA + Rutas.LISTADO_NOTICIAS;
	}


	/**
	 * Devolver la imagen de la noticia al navegador cuando un <img> la solicita
	 *
	 * @param id the id
	 * @return the byte[]
	 */
	@GetMapping(Rutas.IMAGEN_NOTICIA)
	public ResponseEntity<byte[]> mostrarImagen(@PathVariable Integer id) {

		Noticia noticia = noticiaService.buscarPorId(id);

		if (noticia == null || noticia.getImagen() == null) {
			return ResponseEntity.notFound().build();
		}

		// Detectar tipo de imagen automáticamente
		MediaType contentType = MediaType.IMAGE_JPEG;
		try (var is = new java.io.ByteArrayInputStream(noticia.getImagen())) {
			String guessed = java.net.URLConnection.guessContentTypeFromStream(is);
			if (guessed != null) {
				contentType = MediaType.parseMediaType(guessed);
			}
		} catch (Exception ignore) {
		}

		return ResponseEntity.ok().contentType(contentType).body(noticia.getImagen());
	}
	
	
	/**
	 * Mostrar noticias musicales.
	 *
	 * @param pagina  the pagina
	 * @param tam     the tam
	 * @param session the session
	 * @param model   the model
	 * @return the string
	 */
	@GetMapping(Rutas.LISTADO_NOTICIAS_PUBLICAS)
	public String mostrarNoticiasMusicalesPublicadas(
	        							@RequestParam(required = false) String titulo,
	        							@RequestParam(required = false) Integer idGrupo,
	        							@RequestParam(required = false) Integer idEvento,
	        							@RequestParam(required = false) String fechaPublicacion,
	        							@RequestParam(defaultValue = "1") Integer pagina,
	        							Model model) {
	 
	    Boolean publicado = true; // ← única diferencia con admin
	 
	    List<Noticia> noticias = noticiaService.buscarConFiltros(
	            titulo, idGrupo, publicado, fechaPublicacion, pagina, 10);
	 
	    long total = noticiaService.contarConFiltros(titulo, idGrupo, publicado);
	    int totalPaginas = (int) Math.ceil((double) total / 10);
	 
	    model.addAttribute("listaNoticias", noticias);
	    model.addAttribute("paginaActual", pagina);
	    model.addAttribute("totalPaginas", totalPaginas);
	 
	    model.addAttribute("titulo", titulo);
	    model.addAttribute("idGrupo", idGrupo);
	    model.addAttribute("idEvento", idEvento);
	    model.addAttribute("fechaPublicacion", fechaPublicacion);
	 
	    model.addAttribute("listaGrupos", grupoService.buscarTodos());
	    model.addAttribute("listaEventos", eventoService.buscarTodos());
	 
	    return Vistas.NOTICIAS_PUBLICAS;
	}
	@GetMapping(Rutas.FICHA_NOTICIA)
    public String mostrarFichaNoticia(@RequestParam("idNoticia") Integer idNoticia,
                                      HttpSession session,
                                      Model model,
                                      RedirectAttributes redirect) {

        Noticia noticia = noticiaService.buscarPorId(idNoticia);

        if (noticia == null) {
            redirect.addFlashAttribute(MensajesError.MENSAJE_ERROR,
                    MessageManager.get(MensajesError.NOTICIA_NO_ENCONTRADA));
            return Rutas.REDIRECT + Rutas.BASE_NOTICIA + Rutas.LISTADO_NOTICIAS;
        }

        model.addAttribute("noticia", noticia);

        Usuario usuario = (Usuario) session.getAttribute("usuarioSesion");
        model.addAttribute("esAdmin", ValidarAdmin.esAdmin(usuario));

        return Vistas.FICHA_NOTICIA;
    }


}
