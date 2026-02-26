package es.ticketcenture.utilities;

/**
 * Constantes relacionadas con las actividades registradas en la auditoría.
 */
public class Acciones {

	// ============================
	// USUARIO
	// ============================
	public static final String LOGIN = "LOGIN";
	public static final String LOGIN_BLOQUEADO = "LOGIN_BLOQUEADO";
	public static final String LOGIN_FALLIDO = "LOGIN_FALLIDO";

	public static final String REGISTRO = "REGISTRO";
	public static final String CAMBIO_PASSWORD = "CAMBIO_PASSWORD";
	public static final String MODIFICAR_DATOS = "MODIFICAR_DATOS";
	public static final String BAJA_USUARIO = "BAJA_USUARIO";
	public static final String RECUPERAR_PASSWORD = "RECUPERAR_PASSWORD";

	// ============================
	// GRUPO
	// ============================
	public static final String CREAR_GRUPO = "CREAR_GRUPO";
	public static final String EDITAR_GRUPO = "EDITAR_GRUPO";
	public static final String ELIMINAR_GRUPO = "ELIMINAR_GRUPO";
	public static final String ACTIVAR_GRUPO = "ACTIVAR_GRUPO";
	public static final String DESACTIVAR_GRUPO = "DESACTIVAR_GRUPO";

	// ============================
	// GENERO
	// ============================
	public static final String CREAR_GENERO = "CREAR_GENERO";
	public static final String EDITAR_GENERO = "EDITAR_GENERO";
	public static final String ELIMINAR_GENERO = "ELIMINAR_GENERO";

	// ============================
	// FAVORITOS
	// ============================
	public static final String AGREGAR_FAVORITO = "AGREGAR_FAVORITO";
	public static final String ELIMINAR_FAVORITO = "ELIMINAR_FAVORITO";

	// ============================
	// GALERÍA / IMÁGENES
	// ============================
	public static final String GUARDAR_IMAGEN_PRINCIPAL = "GUARDAR_IMAGEN_PRINCIPAL";
	public static final String AGREGAR_IMAGEN_GALERIA = "AGREGAR_IMAGEN_GALERIA";
	public static final String ELIMINAR_IMAGEN_GALERIA = "ELIMINAR_IMAGEN_GALERIA";

	// ============================
	// EVENTOS
	// ============================
	public static final String CREAR_EVENTO = "CREAR_EVENTO";
	public static final String EDITAR_EVENTO = "EDITAR_EVENTO";
	public static final String ELIMINAR_EVENTO = "ELIMINAR_EVENTO";
	public static final String ACTIVAR_EVENTO = "ACTIVAR_EVENTO";
	public static final String DESACTIVAR_EVENTO = "DESACTIVAR_EVENTO";
	
	private Acciones() {
	}
}
