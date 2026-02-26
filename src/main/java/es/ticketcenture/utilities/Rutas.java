package es.ticketcenture.utilities;

/**
 * 
 * Esta clase contendra las constantes utilizadas para vistas
 *
 */
public class Rutas {

	/**
	 * Previene instanciado de Rutas
	 */
	private Rutas() {

	}

	// PRINCIPAL

	// USUARIOS
	public static final String BASE_USUARIOS = "/usuarios";
	public static final String FORMULARIO_REGISTRO_USUARIOS = "/formulario";
	public static final String REGISTRO_USUARIOS = "/registro";
	public static final String FORMULARIO_LOGIN = "/formularioLogin";
	public static final String LOGIN = "/login";
	public static final String LOGOUT = "/logout";
	public static final String RECUPERAR_PASSWORD = "/recuperarPassword";
	public static final String BAJA = "/baja";
	public static final String PERFIL_USUARIO = "/perfilUsuario";
	public static final String MODIFICAR_USUARIO = "/modificarUsuario";
	public static final String MOSTRAR_HISTORICO = "/mostrarHistorico";
	
	//GESTION DE USUARIOS
	public static final String LISTADO_USUARIOS = "/listado";
	public static final String MODIFICAR_ADMIN = "/modificar";
	public static final String BLOQUEAR_USUARIO = "/bloquear";
	public static final String DESBLOQUEAR_USUARIO = "/desbloquear";
	public static final String ELIMINAR_USUARIO = "/eliminar";

	// VISTAS ------------------------------------------------------
	public static final String A_FORM_LOGIN = "usuarios/InicioSesionUsuarios";

	public static final String REDIRIGIR_A_PRINCIPAL = "redirect:/" + Vistas.PRINCIPAL;
	public static final String PRINCIPAL = "principal/principal";

	public static final String REDIRIGIR_A_LOGIN = "redirect:/login";

	public static final String FORMULARIO_MODIFICAR = "usuarios/PerfilUsuario";
	public static final String CAMBIO_PASSWORD = "usuarios/CambiarPassword";

	// GRUPOS
	public static final String BASE_GRUPOS = "/grupos";
	public static final String LISTADO_GRUPOS_MUSICALES = "/gruposMusicales";
	public static final String CREAR_GRUPO = "/AltaGrupo";
	public static final String EDITAR_GRUPO = "/EditarGrupo";
	public static final String ELIMINAR_GRUPO = "/EliminarGrupo";
	public static final String DESACTIVAR_GRUPO = "/DesactivarGrupo";
	public static final String ACTIVAR_GRUPO = "/ActivarGrupo";
	public static final String DETALLE_GRUPO = "/detalleGrupo";
	
	
	public static final String IMAGEN_PRINCIPAL = "/imagen/{id}";

	// GALERIAS
	public static final String GALERIA_AGREGAR = "/galeria/agregar";
	public static final String GALERIA_ELIMINAR = "/galeria/eliminar";
	public static final String GALERIA_IMAGEN = "/galeria/imagen/{id}";

	// FAVORITOS
	public static final String MARCAR_FAVORITOS = "/marcarFavorito";
	public static final String DESMARCAR_FAVORITOS = "/desmarcarFavorito";
	public static final String REDIRECT = "redirect:";
	public static final String PARAM_ID = "?id=";

	// EVENTOS
	public static final String BASE_EVENTOS = "/eventos";
	public static final String LISTADO_EVENTOS_MUSICALES = "/EventosMusicales";
	public static final String CREAR_EVENTO = "/AltaEvento";
	public static final String EDITAR_EVENTO = "/EditarEvento";
	public static final String ELIMINAR_EVENTO = "/EliminarEvento";
	public static final String DETALLE_EVENTO = "/DetalleEvento";
	public static final String EVENTO_COMPRADORES = "/{idEvento}/compradores";
	
	
	// PRINCIPAL
	public static final String BASE_PRINCIPAL = "/principal";
	
	//PANEL DE CONTROL
	public static final String BASE_PANEL = "/panel";
	public static final String PANEL_CONTROL = "PanelControl";
	
	// DESCUENTO
	
	public static final String BASE_DESCUENTO = "/descuento";
	public static final String LISTADO_DESCUENTOS = "/DescuentoUsuario";
	public static final String CREAR_DESCUENTO = "/AltaDescuento";
	public static final String EDITAR_DESCUENTO = "/EditarDescuento";
	public static final String ELIMINAR_DESCUENTO = "/EliminarDescuento";
	
	// PROMOCION
	public static final String BASE_PROMOCION = "/promocion";
	public static final String LISTADO_PROMOCIONES = "/PromocionEvento";
	public static final String CREAR_PROMOCION = "/AltaPromocion";
	public static final String EDITAR_PROMOCION = "/EditarPromocion";
	public static final String ELIMINAR_PROMOCION = "/EliminarPromocion";
	
	// CARRITO
	public static final String BASE_CARRITO = "/carrito";
	public static final String REDIRIGIR_A_EVENTO = "redirect:/eventos/DetalleEvento?idEvento=";
	public static final String ANADIR_CARRITO = "/anadir";
	public static final String ELIMINAR_ITEM = "/EliminarItem";
	public static final String MOSTRAR_CARRITO = "/MostrarCarrito";
	public static final String CONFIRMAR_CARRITO = "/confirmar";
	public static final String CONFIRMACION_COMPRA = "/confirmacion";
	
	// GENERO
	public static final String BASE_GENERO = "/genero";
	public static final String LISTADO_GENEROS = "/GeneroMusical";
	public static final String CREAR_GENERO = "/AltaGenero";
	public static final String EDITAR_GENERO = "/EditarGenero";
	public static final String ELIMINAR_GENERO = "/EliminarGenero";
	
	// NOTICIAS
	public static final String BASE_NOTICIA = "/noticias";
	public static final String LISTADO_NOTICIAS = "/NoticiasMusicales";
	public static final String ALTA_NOTICIA = "/AltaNoticia";
	public static final String EDITAR_NOTICIA = "/EditarNoticia";
	
	public static final String ELIMINAR_NOTICIA = "/EliminarNoticia";
	public static final String IMAGEN_NOTICIA = "/imagen/{id}";
	public static final String DETALLE_NOTICIA = "/DetalleNoticia";
	public static final String PARAM_ID_NOTICIA = "?idNoticia=";
	public static final String LISTADO_NOTICIAS_PUBLICAS = "/NoticiasPublicas";

	public static final String FICHA_NOTICIA = "/FichaNoticia";

		
}
