package es.ticketcenture.controller;

import java.util.Arrays;
import java.util.List;

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

import es.ticketcenture.entities.Evento;
import es.ticketcenture.entities.GaleriaGrupo;
import es.ticketcenture.entities.Grupo;
import es.ticketcenture.entities.Usuario;
import es.ticketcenture.service.EventoService;
import es.ticketcenture.service.GaleriaService;
import es.ticketcenture.service.GeneroService;
import es.ticketcenture.service.GrupoService;
import es.ticketcenture.utilities.MensajesError;
import es.ticketcenture.utilities.MensajesExito;
import es.ticketcenture.utilities.MessageManager;
import es.ticketcenture.utilities.Rutas;
import es.ticketcenture.utilities.ValidarAdmin;
import es.ticketcenture.utilities.ValidarImagenes;
import es.ticketcenture.utilities.Vistas;

@Controller
@RequestMapping(Rutas.BASE_GRUPOS)
public class GrupoController {

	private GrupoService grupoService;
	private GeneroService generoService;
	private GaleriaService galeriaService;
	private EventoService eventoService;


	@Autowired
	public GrupoController(GrupoService grupoService, GeneroService generoService, 
			GaleriaService galeriaService,EventoService eventoService) {
		this.grupoService = grupoService;
		this.generoService = generoService;
		this.galeriaService = galeriaService;
		this.eventoService = eventoService;
	}

	@GetMapping(Rutas.LISTADO_GRUPOS_MUSICALES)
	public String mostrarGruposMusicales(@RequestParam(required = false) String nombre,
	        @RequestParam(required = false) Integer genero, 
	        @RequestParam(required = false) Boolean favoritos,
	        @RequestParam(required = false) Boolean activo, 
	        @RequestParam(defaultValue = "1") int pagina,
	        @RequestParam(defaultValue = "10") int tam, 
	        HttpSession session, 
	        Model model) {

	    Usuario u = (Usuario) session.getAttribute("usuarioSesion");

	    // Favoritos: si está marcado y hay usuario - filtrar por su ID
	    Integer idUsuarioFavoritos = (favoritos != null && favoritos && u != null) ? u.getIdUsuario() : null;

	    // Activo: admin puede filtrar, usuario normal solo ve activos
	    Boolean filtroActivo;
	    if (u != null && u.isAdmin()) {
	        filtroActivo = activo; // puede ser null, true o false
	    } else {
	        filtroActivo = true;
	    }

	    // Detectar si realmente hay filtros
	    boolean hayFiltros = (nombre != null && !nombre.isBlank()) 
	            || (genero != null && genero != 0)
	            || idUsuarioFavoritos != null 
	            || (u != null && u.isAdmin() && activo != null);

	    int offset = (pagina - 1) * tam;

	    List<Grupo> grupos;
	    long total;

	    if (hayFiltros) {
	        grupos = grupoService.buscarConFiltros(nombre, genero, idUsuarioFavoritos, filtroActivo, offset, tam);
	        total = grupoService.contarConFiltros(nombre, genero, idUsuarioFavoritos, filtroActivo);

	    } else {
	        if (u != null && u.isAdmin()) {
	            grupos = grupoService.buscarPaginado(offset, tam);
	            total = grupoService.contarTotalGrupos();
	        } else {
	            grupos = grupoService.buscarConFiltros(null, null, null, true, offset, tam);
	            total = grupoService.contarConFiltros(null, null, null, true);
	        }
	    }

	    // Calcular total de páginas
	    long totalPaginas = (long) Math.ceil((double) total / tam);

	    // Añadir datos al modelo
	    model.addAttribute("grupos", grupos);
	    model.addAttribute("pagina", pagina);
	    model.addAttribute("tam", tam);
	    model.addAttribute("total", total);
	    model.addAttribute("totalPaginas", totalPaginas);

	    // Reenviar los filtros a la JSP
	    model.addAttribute("nombre", nombre);
	    model.addAttribute("genero", genero);
	    model.addAttribute("favoritos", favoritos);
	    model.addAttribute("activo", activo);

	    // Cargar géneros para el select
	    model.addAttribute("listaGeneros", generoService.buscarTodos());

	    return Vistas.LISTADO_GRUPOS;
	}



	@GetMapping(Rutas.DETALLE_GRUPO)
	public String mostrarDetalleGrupo(@RequestParam("id") Integer idGrupo, HttpSession session, Model model) {

		Grupo grupo = grupoService.buscarPorIdConFavoritos(idGrupo);

		// VALIDACIÓN DE EXISTENCIA
		if (grupo == null) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.GRUPO_ID_NO_EXISTE));
			return "redirect:" + Rutas.BASE_GRUPOS + Rutas.LISTADO_GRUPOS_MUSICALES;
		}
		grupo.setGaleria(galeriaService.buscarPorGrupo(idGrupo));
		model.addAttribute("grupo", grupo);
		// Cargar eventos futuros del grupo 
		List<Evento> eventosFuturos = eventoService.obtenerEventosFuturosPorGrupo(idGrupo);
		model.addAttribute("eventosFuturos", eventosFuturos);

		// Marcar/Desmarcar favoritos
		Usuario usuario = (Usuario) session.getAttribute("usuarioSesion");
		boolean esFavorito = false;

		if (usuario != null && grupo.getFavoritos() != null) {
			esFavorito = grupo.getFavoritos().stream()
					.anyMatch(f -> f.getUsuario().getIdUsuario().equals(usuario.getIdUsuario()));
		}
		model.addAttribute("esFavorito", esFavorito);

		return Vistas.DETALLE_GRUPO;
	}

	@GetMapping(Rutas.CREAR_GRUPO)
	public String mostrarFormularioAltaGrupo(HttpSession session, Model model) {

		// VALIDACIÓN DE ADMIN
		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");
		if (!ValidarAdmin.esAdmin(admin)) {

			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return "redirect:" + Rutas.BASE_GRUPOS + Rutas.LISTADO_GRUPOS_MUSICALES;
		}
		model.addAttribute("isAdmin", true);// Evita el nullpointer
		model.addAttribute("grupo", new Grupo());
		model.addAttribute("generos", generoService.buscarTodos());

		return Vistas.FORMULARIO_ALTA_GRUPO;
	}

	@PostMapping(Rutas.CREAR_GRUPO)
	public String crearGrupo(@Valid @ModelAttribute("grupo") Grupo grupo, BindingResult bindingResult,
			@RequestParam("imagenPrincipal") MultipartFile imagen,
			@RequestParam(value = "imagenesGaleria", required = false) MultipartFile[] imagenesGaleria,
			HttpSession session, HttpServletRequest request, Model model, RedirectAttributes redirectAttrs) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");

		// Validación de admin
		if (!ValidarAdmin.esAdmin(admin)) {
			redirectAttrs.addFlashAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return "redirect:" + Rutas.BASE_GRUPOS + Rutas.LISTADO_GRUPOS_MUSICALES;
		}

		// Validación automática de campos
		if (bindingResult.hasErrors()) {
			model.addAttribute("generos", generoService.buscarTodos());
			model.addAttribute("isAdmin", ValidarAdmin.esAdmin(admin));
			return Vistas.FORMULARIO_ALTA_GRUPO;
		}

		try {
			int maxSize = 5 * 1024 * 1024;

			// Validar imagen principal
			if (!ValidarImagenes.imagenPrincipalValida(imagen)) {
				throw new IllegalArgumentException(MensajesError.IMAGEN_VACIA);
			}
			if (!ValidarImagenes.esImagenValida(imagen)) {
				throw new IllegalArgumentException(MensajesError.IMAGEN_FORMATO);
			}
			if (!ValidarImagenes.tamanoValido(imagen, maxSize)) {
				throw new IllegalArgumentException(MensajesError.IMAGEN_TAMANO_EXCESIVO);
			}

			grupo.setImagenPrincipal(imagen.getBytes());

			// Galería
			if (imagenesGaleria != null) {
				for (MultipartFile img : imagenesGaleria) {
					if (!img.isEmpty()) {

						if (!ValidarImagenes.esImagenValida(img)) {
							throw new IllegalArgumentException(MensajesError.IMAGEN_FORMATO);
						}
						if (!ValidarImagenes.tamanoValido(img, maxSize)) {
							throw new IllegalArgumentException(MensajesError.IMAGEN_TAMANO_EXCESIVO);
						}

						grupo.getGaleria().add(new GaleriaGrupo(grupo, img.getBytes()));
					}
				}
			}

			// Guardar grupo
			grupoService.insertarGrupo(grupo, admin, request);

			redirectAttrs.addFlashAttribute(MensajesExito.MENSAJE_EXITO,
					MessageManager.get(MensajesExito.GRUPO_CREADO));

			return "redirect:" + Rutas.BASE_GRUPOS + Rutas.LISTADO_GRUPOS_MUSICALES;

		} catch (Exception e) {

			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
			model.addAttribute("generos", generoService.buscarTodos());
			model.addAttribute("isAdmin", ValidarAdmin.esAdmin(admin));
			return Vistas.FORMULARIO_ALTA_GRUPO;
		}
	}

	@GetMapping(Rutas.EDITAR_GRUPO)
	public String mostrarFormularioEditar(@RequestParam("id") Integer idGrupo, HttpSession session, Model model) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");

		if (!ValidarAdmin.esAdmin(admin)) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return "redirect:" + Rutas.BASE_GRUPOS + Rutas.LISTADO_GRUPOS_MUSICALES;
		}

		Grupo grupo = grupoService.buscarPorId(idGrupo);

		if (grupo == null) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.GRUPO_ID_NO_EXISTE));
			return "redirect:" + Rutas.BASE_GRUPOS + Rutas.LISTADO_GRUPOS_MUSICALES;
		}

		// Se carga la galería explícitamente y la pasamos aparte
		List<GaleriaGrupo> galeria = galeriaService.buscarPorGrupo(idGrupo);

		model.addAttribute("grupo", grupo);
		model.addAttribute("galeria", galeria);
		model.addAttribute("generos", generoService.buscarTodos());

		return Vistas.FORMULARIO_EDITAR_GRUPO;
	}

	@InitBinder("grupo")
	public void initBinder(WebDataBinder binder) {
		// Impide que se enlace (bind) el campo "imagenPrincipal" al objeto Grupo
		// LO HE TENIDO QUE CAMBIAR binder.setDisallowedFields("imagenPrincipal",
		// "galeria");
		binder.setDisallowedFields("imagenPrincipal", "galeria");
	}

	@PostMapping(Rutas.EDITAR_GRUPO)
	public String editarGrupo(@Valid @ModelAttribute("grupo") Grupo grupoForm, BindingResult bindingResult,
			@RequestParam("imagenPrincipal") MultipartFile imagen,
			@RequestParam(value = "imagenesGaleria", required = false) MultipartFile[] imagenesGaleria,
			@RequestParam(value = "origen", required = false) String origen, HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttrs, Model model) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");

		if (!ValidarAdmin.esAdmin(admin)) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return "redirect:" + Rutas.BASE_GRUPOS + Rutas.LISTADO_GRUPOS_MUSICALES;
		}
		// Recuperar el grupo real desde BD
//		Grupo grupo = grupoService.buscarPorId(grupoForm.getIdGrupo());

		// Inicializar la galería ANTES de cualquier uso
		// if (grupo.getGaleria() != null) { // grupo.getGaleria().size(); // }

		Grupo grupo = grupoService.buscarPorIdConGaleria(grupoForm.getIdGrupo());

		if (grupo == null) {
			redirectAttrs.addFlashAttribute(MensajesError.MENSAJE_ERROR,
					MessageManager.get(MensajesError.GRUPO_ID_NO_EXISTE));
			return "redirect:" + Rutas.BASE_GRUPOS + Rutas.LISTADO_GRUPOS_MUSICALES;
		}

		// ============================
		// ERRORES DE VALIDACIÓN
		// ============================
		if (bindingResult.hasErrors()) {
			model.addAttribute("mensajeError", bindingResult.getFieldError().getDefaultMessage());
			model.addAttribute("grupo", grupoForm);
			model.addAttribute("galeria", grupo.getGaleria());
			model.addAttribute("generos", generoService.buscarTodos());
			return Vistas.FORMULARIO_EDITAR_GRUPO;
		}

		try {
			int maxSize = 5 * 1024 * 1024;

			// ============================
			// COPIAR CAMPOS EDITABLES
			// ============================
			grupo.setNombreGrupo(grupoForm.getNombreGrupo());
			grupo.setPaisOrigen(grupoForm.getPaisOrigen());
			grupo.setAnoCreacion(grupoForm.getAnoCreacion());
			grupo.setGenero(grupoForm.getGenero());
			grupo.setBiografia(grupoForm.getBiografia());
			grupo.setDiscografia(grupoForm.getDiscografia());
			grupo.setComponentes(grupoForm.getComponentes());
			grupo.setActivo(grupoForm.isActivo());

			// ============================
			// IMAGEN PRINCIPAL
			// ============================
			if (ValidarImagenes.imagenPrincipalValida(imagen)) {

				if (!ValidarImagenes.esImagenValida(imagen)) {
					throw new IllegalArgumentException(MensajesError.IMAGEN_FORMATO);
				}
				if (!ValidarImagenes.tamanoValido(imagen, maxSize)) {
					throw new IllegalArgumentException(MensajesError.IMAGEN_TAMANO_EXCESIVO);
				}

				grupo.setImagenPrincipal(imagen.getBytes());
			}

			// ============================
			// GALERÍA
			// ============================
			if (imagenesGaleria != null && Arrays.stream(imagenesGaleria).anyMatch(file -> !file.isEmpty())) {

				for (MultipartFile imagenGaleria : imagenesGaleria) {
					if (!imagenGaleria.isEmpty()) {

						if (!ValidarImagenes.esImagenValida(imagenGaleria)) {
							throw new IllegalArgumentException(MensajesError.IMAGEN_FORMATO);
						}
						if (!ValidarImagenes.tamanoValido(imagenGaleria, maxSize)) {
							throw new IllegalArgumentException(MensajesError.IMAGEN_TAMANO_EXCESIVO);
						}

						grupo.getGaleria().add(new GaleriaGrupo(grupo, imagenGaleria.getBytes()));
					}
				}
			}

			// ============================
			// ACTUALIZAR GRUPO
			// ============================
			grupoService.actualizarGrupo(grupo, admin, request);

			redirectAttrs.addFlashAttribute(MensajesExito.MENSAJE_EXITO,
					MessageManager.get(MensajesExito.GRUPO_ACTUALIZADO));

			if ("ficha".equals(origen)) {
				return "redirect:" + Rutas.BASE_GRUPOS + Rutas.DETALLE_GRUPO + "?id=" + grupo.getIdGrupo();
			} else {
				return "redirect:" + Rutas.BASE_GRUPOS + Rutas.LISTADO_GRUPOS_MUSICALES;
			}

		} catch (Exception e) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
			model.addAttribute("generos", generoService.buscarTodos());
			return Vistas.FORMULARIO_EDITAR_GRUPO;
		}
	}

	@PostMapping(Rutas.DESACTIVAR_GRUPO)
	public String desactivarGrupo(@RequestParam("id") Integer idGrupo, HttpSession session, HttpServletRequest request,
			Model model) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");
		if (!ValidarAdmin.esAdmin(admin)) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return "redirect:" + Rutas.BASE_GRUPOS + Rutas.LISTADO_GRUPOS_MUSICALES;
		}

		try {
			grupoService.cambiarEstadoGrupo(idGrupo, false, admin, request);
			model.addAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.GRUPO_DESACTIVADO));

		} catch (IllegalArgumentException e) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
		}

		return "redirect:" + Rutas.BASE_GRUPOS + Rutas.LISTADO_GRUPOS_MUSICALES;
	}

	@PostMapping(Rutas.ACTIVAR_GRUPO)
	public String activarGrupo(@RequestParam("id") Integer idGrupo, HttpSession session, HttpServletRequest request,
			Model model) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");
		if (!ValidarAdmin.esAdmin(admin)) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return "redirect:" + Rutas.BASE_GRUPOS + Rutas.LISTADO_GRUPOS_MUSICALES;
		}

		try {
			grupoService.cambiarEstadoGrupo(idGrupo, true, admin, request);
			model.addAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.GRUPO_ACTIVADO));

		} catch (IllegalArgumentException e) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
		}

		return "redirect:" + Rutas.BASE_GRUPOS + Rutas.LISTADO_GRUPOS_MUSICALES;
	}

	@PostMapping(Rutas.ELIMINAR_GRUPO)
	public String eliminarGrupo(@RequestParam("id") Integer idGrupo, HttpSession session, HttpServletRequest request,
			RedirectAttributes redirectAttrs, Model model) {

		Usuario admin = (Usuario) session.getAttribute("usuarioSesion");
		if (!ValidarAdmin.esAdmin(admin)) {
			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.PERMISO_ADMINISTRADOR));
			return "redirect:" + Rutas.BASE_GRUPOS + Rutas.LISTADO_GRUPOS_MUSICALES;
		}

		try {
			grupoService.eliminarGrupo(idGrupo, admin, request);
			redirectAttrs.addFlashAttribute(MensajesExito.MENSAJE_EXITO,
					MessageManager.get(MensajesExito.GRUPO_ELIMINADO));

		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
		}

		return "redirect:" + Rutas.BASE_GRUPOS + Rutas.LISTADO_GRUPOS_MUSICALES;
	}

	@GetMapping(Rutas.IMAGEN_PRINCIPAL)
	public ResponseEntity<byte[]> mostrarImagenPrincipal(@PathVariable Integer id) {

		Grupo grupo = grupoService.buscarPorId(id);

		if (grupo == null || grupo.getImagenPrincipal() == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(grupo.getImagenPrincipal());
	}

	// Estos metodos no son necesarios por la relacion - PDTE DE PROBAR
//	@PostMapping(Rutas.GALERIA_AGREGAR)
//	public String agregarImagenGaleria(@RequestParam("idGrupo") Integer idGrupo,
//			@RequestParam("imagen") MultipartFile imagen, HttpSession session, HttpServletRequest request,
//			Model model) {
//
//		if (!ValidarAdmin.esAdmin(session)) {
//			return Rutas.REDIRIGIR_A_LOGIN;
//		}
//
//		try {
//			// VALIDACIÓN DE IMAGEN
//			if (!ValidarImagenes.esImagenValida(imagen)) {
//				model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(MensajesError.IMAGEN_NO_ENCONTRADA));
//				return "redirect:" + Rutas.BASE_GRUPOS + Rutas.EDITAR_GRUPO + "?id=" + idGrupo;
//			}
//
//			Usuario usuario = (Usuario) session.getAttribute("usuarioSesion");
//
//			galeriaService.agregarImagen(idGrupo, imagen.getBytes(), usuario, request);
//
//			model.addAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.IMAGEN_GALERIA_AGREGADA));
//
//		} catch (Exception e) {
//			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
//		}
//
//		return "redirect:" + Rutas.BASE_GRUPOS + Rutas.EDITAR_GRUPO + "?id=" + idGrupo;
//	}
//
//	@PostMapping(Rutas.GALERIA_ELIMINAR)
//	public String eliminarImagenGaleria(@RequestParam("idImagen") Integer idImagen,
//			@RequestParam("idGrupo") Integer idGrupo, HttpSession session, HttpServletRequest request, Model model) {
//
//		if (!ValidarAdmin.esAdmin(session)) {
//			return Rutas.REDIRIGIR_A_LOGIN;
//		}
//
//		try {
//			Usuario usuario = (Usuario) session.getAttribute("usuarioSesion");
//
//			galeriaService.eliminarImagen(idImagen, usuario, request);
//
//			model.addAttribute(MensajesExito.MENSAJE_EXITO, MessageManager.get(MensajesExito.IMAGEN_GALERIA_ELIMINADA));
//
//		} catch (Exception e) {
//			model.addAttribute(MensajesError.MENSAJE_ERROR, MessageManager.get(e.getMessage()));
//		}
//
//		return "redirect:" + Rutas.BASE_GRUPOS + Rutas.EDITAR_GRUPO + "?id=" + idGrupo;
//	}
//
//	@GetMapping(Rutas.GALERIA_IMAGEN)
//	public ResponseEntity<byte[]> mostrarImagenGaleria(@PathVariable Integer id) {
//
//		GaleriaGrupo imagen = galeriaService.buscarPorId(id);
//
//		if (imagen == null || imagen.getImagen() == null) {
//			return ResponseEntity.notFound().build();
//		}
//
//		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen.getImagen());
//	}

	@GetMapping(value = Rutas.GALERIA_IMAGEN, produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE,
			MediaType.IMAGE_GIF_VALUE })
	public ResponseEntity<byte[]> mostrarImagenGaleria(@PathVariable("id") Integer idImagen) {

		GaleriaGrupo img = galeriaService.buscarPorId(idImagen);
		if (img == null || img.getImagen() == null) {
			return ResponseEntity.notFound().build();
		}

		// (Opcional) Detectar el content-type a partir de los bytes
		MediaType contentType = MediaType.IMAGE_JPEG; // Por defecto
		try (var is = new java.io.ByteArrayInputStream(img.getImagen())) {
			String guessed = java.net.URLConnection.guessContentTypeFromStream(is);
			if (guessed != null) {
				contentType = MediaType.parseMediaType(guessed);
			}
		} catch (Exception ignore) {
		}

		return ResponseEntity.ok().contentType(contentType).body(img.getImagen());
	}

}
