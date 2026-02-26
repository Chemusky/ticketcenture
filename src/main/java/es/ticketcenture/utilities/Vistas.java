package es.ticketcenture.utilities;

/**
 * Esta clase contendra las constantes para rutas
 * 
 *
 */
public class Vistas {

	/**
	 * Previene instanciado de Vistas
	 */
	private Vistas() {

	}

	// PRINCIPAL
	public static final String PRINCIPAL = "principal";

	// USUARIOS
	public static final String BASE_USUARIOS = "usuarios/";
	public static final String PAG_CONFIRMACION = BASE_USUARIOS + "Confirmacion";

	public static final String LOGIN = BASE_USUARIOS + "InicioSesionUsuarios";

	public static final String PERFIL_USUARIO = BASE_USUARIOS + "PerfilUsuario";

	public static final String RECUPERAR_PASSWORD = BASE_USUARIOS + "RecuperarPassword";

	public static final String REGISTRO_USUARIOS = BASE_USUARIOS + "RegistroUsuarios";

	public static final String HISTORICO_COMPRAS = BASE_USUARIOS + "HistoricoCompras";

	// GESTION USUARIOS
	public static final String LISTADO_USUARIOS = BASE_USUARIOS + "ListadoUsuarios";
	public static final String MODIFICAR_PERFIL = BASE_USUARIOS + "ModificarPerfilUsuario";

	// GRUPOS
	public static final String BASE_GRUPOS = "grupos/";
	public static final String LISTADO_GRUPOS = BASE_GRUPOS + "GruposMusicales";
	public static final String DETALLE_GRUPO = BASE_GRUPOS + "DetalleGrupo";
	public static final String FORMULARIO_ALTA_GRUPO = BASE_GRUPOS + "AltaGrupo";
	public static final String FORMULARIO_EDITAR_GRUPO = BASE_GRUPOS + "EditarGrupo";

	// EVENTOS
	public static final String BASE_EVENTOS = "eventos/";
	public static final String LISTADO_EVENTOS = BASE_EVENTOS + "EventosMusicales";
	public static final String DETALLE_EVENTO = BASE_EVENTOS + "DetalleEvento";
	public static final String FORMULARIO_ALTA_EVENTO = BASE_EVENTOS + "AltaEvento";
	public static final String FORMULARIO_EDITAR_EVENTO = BASE_EVENTOS + "EditarEvento";
	public static final String LISTADO_COMPRADORES_EVENTOS = BASE_EVENTOS + "ListadoCompradores";

	// REDIRECCIONES
	// public static final String REDIRIGIR_A_PRINCIPAL = "redirect:/" +
	// Vistas.PRINCIPAL;

	// PANEL DE CONTROL ESQUELETO
	public static final String BASE_PANEL_CONTROL = "panelControl/";
	public static final String PANEL_CONTROL = BASE_PANEL_CONTROL + "PanelControl";
	public static final String PROMOCION_EVENTO = BASE_PANEL_CONTROL + "PromocionEvento";
	public static final String DESCUENTO_USUARIO = BASE_PANEL_CONTROL + "DescuentoUsuario";
	public static final String GENEROS = BASE_PANEL_CONTROL + "GeneroMusical";

	// CARRITO
	public static final String CARRITO = "carrito/Carrito";

	public static final String CONFIRMACION_COMPRA = "carrito/Confirmacion";

	// NOTICIAS
	public static final String BASE_NOTICIAS = "noticias/";
	public static final String LISTADO_NOTICIAS = BASE_NOTICIAS + "NoticiasMusicales";
	public static final String FORMULARIO_ALTA_NOTICIA = BASE_NOTICIAS + "AltaNoticia";
	public static final String FORMULARIO_EDITAR_NOTICIA = BASE_NOTICIAS + "EditarNoticia";
	public static final String DETALLE_NOTICIA = BASE_NOTICIAS + "DetalleNoticia";
	public static final String NOTICIAS_PUBLICAS = BASE_NOTICIAS + "NoticiasPublicas";
	
	public static final String FICHA_NOTICIA = BASE_NOTICIAS + "FichaNoticia";
}
