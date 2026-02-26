package es.ticketcenture.utilities;

public class MensajesError {

	public static final String MENSAJE_ERROR = "mensajeError";

	// ============================
	// LOGIN
	// ============================
	public static final String USUARIO_INCORRECTO = "ERROR_USUARIO_INCORRECTO";
	public static final String LOGIN_FALLIDO_MAIL_INEXISTENTE = "ERROR_LOGIN_FALLIDO_MAIL_INEXISTENTE";
	public static final String LOGIN_FALLIDO_PASSWORD = "ERROR_LOGIN_FALLIDO_PASSWORD";
	public static final String LOGIN_BLOQUEADO = "ERROR_LOGIN_BLOQUEADO";

	// ============================
	// REGISTRO
	// ============================
	public static final String EMAIL_YA_EXISTE = "ERROR_EMAIL_YA_EXISTE";
	public static final String USERNAME_YA_EXISTE = "ERROR_USERNAME_YA_EXISTE";

	// ============================
	// RECUPERAR PASSWORD
	// ============================
	public static final String PASSWORD_VACIA = "ERROR_PASSWORD_VACIA";
	public static final String PASSWORD_CORTA = "ERROR_PASSWORD_CORTA";
	public static final String PASSWORD_NO_COINCIDEN = "ERROR_PASSWORD_NO_COINCIDEN";
	public static final String VALIDAR_CODIGO = "ERROR_VALIDAR_CODIGO";
	public static final String PASSWORD_ACTUAL_INCORRECTA = "ERROR_PASSWORD_ACTUAL_INCORRECTA";
	public static final String EMAIL_NO_EXISTE = "ERROR_EMAIL_NO_EXISTE";

    // ============================
    // PERFIL / ACTIVIDADES
    // ============================
    public static final String FECHA_FIN_DEBE_SER_MAYOR = "ERROR_FECHA_FIN_MAYOR";
    public static final String NO_SE_ENCONTRARON_ACTIVIDADES = "ERROR_SIN_ACTIVIDADES";
    public static final String ERROR_FORMATO_FECHA= "ERROR_FORMATO_FECHA_INVALIDO";
    public static final String ERROR_FECHAS_VACIAS= "ERROR_SIN_FECHAS";
    		

	// ============================
	// USUARIO
	// ============================
	public static final String USUARIO_NO_ENCONTRADO = "ERROR_USUARIO_NO_ENCONTRADO";
	public static final String USUARIO_ID_NO_EXISTE = "ERROR_USUARIO_ID_NO_EXISTE";
	public static final String NO_SE_ENCUENTRA = "ERROR_NO_SE_ENCUENTRA";
	
	// ============================
	// GESTION DE USUARIOS
	// ============================
	public static final String ERROR_ACTUALIZAR_USUARIO = "ERROR_ACTUALIZAR_USUARIO";
	public static final String ERROR_USUARIO_INEXISTENTE = "ERROR_USUARIO_INEXISTENTE";
	public static final String ERROR_USUARIO_CON_COMPRAS = "ERROR_USUARIO_CON_COMPRAS";
	
	

	// ============================
	// GRUPO
	// ============================
	public static final String GRUPO_INCORRECTO = "ERROR_GRUPO_INCORRECTO";
	public static final String GRUPO_ID_NO_EXISTE = "ERROR_GRUPO_ID_NO_EXISTE";
	public static final String GRUPO_YA_EXISTE = "ERROR_GRUPO_YA_EXISTE";
	public static final String GRUPO_NO_ENCONTRADO = "ERROR_GRUPO_NO_ENCONTRADO";
	public static final String GRUPO_NO_ELIMINABLE = "ERROR_GRUPO_NO_ELIMINABLE"; 
	public static final String GRUPO_SIN_PERMISOS = "ERROR_GRUPO_SIN_PERMISOS";
	public static final String GRUPO_NOMBRE_VACIO = "ERROR_GRUPO_NOMBRE_VACIO";

	// ============================
	// GENERO
	// ============================
	public static final String GENERO_NULO = "ERROR_GENERO_NULO";
	public static final String GENERO_NOMBRE_VACIO = "ERROR_GENERO_NOMBRE_VACIO";
	public static final String GENERO_NOMBRE_CORTO = "ERROR_GENERO_NOMBRE_CORTO";
	public static final String GENERO_NOMBRE_LARGO = "ERROR_GENERO_NOMBRE_LARGO";
	public static final String GENERO_DUPLICADO = "ERROR_GENERO_DUPLICADO";
	public static final String GENERO_NO_ENCONTRADO = "ERROR_GENERO_NO_ENCONTRADO";
	public static final String GENERO_NO_ELIMINABLE = "ERROR_GENERO_NO_ELIMINABLE";


	// ============================
	// FAVORITOS
	// ============================
	public static final String FAVORITO_YA_EXISTE = "ERROR_FAVORITO_YA_EXISTE";
	public static final String FAVORITO_NO_EXISTE = "ERROR_FAVORITO_NO_EXISTE";

	// ============================
	// GALERÍA / IMÁGENES
	// ============================
	public static final String IMAGEN_VACIA = "ERROR_IMAGEN_VACIA";
	public static final String IMAGEN_NO_ENCONTRADA = "ERROR_IMAGEN_NO_ENCONTRADA";
	public static final String IMAGEN_FORMATO = "ERROR_IMAGEN_FORMATO";
	public static final String IMAGEN_TAMANO_EXCESIVO = "ERROR_IMAGEN_TAMANO_EXCESIVO";

	// ============================
	// EVENTOS
	// ============================
	
	public static final String EVENTO_YA_EXISTE = "ERROR_EVENTO_YA_EXISTE";
	public static final String EVENTO_INCORRECTO = "ERROR_EVENTO_INCORRECTO";
	public static final String EVENTO_ID_NO_EXISTE = "ERROR_EVENTO_ID_NO_EXISTE";
	public static final String EVENTO_NO_ENCONTRADO = "ERROR_EVENTO_NO_ENCONTRADO";
	public static final String EVENTO_SIN_PERMISOS = "ERROR_EVENTO_SIN_PERMISOS";
	public static final String EVENTO_NOMBRE_VACIO = "ERROR_EVENTO_NOMBRE_VACIO";
	public static final String EVENTO_NO_ELIMINABLE = "ERROR_EVENTO_NO_ELIMINABLE"; // entradas vendidas
	public static final String EVENTO_NO_MODIFICABLE = "ERROR_EVENTO_NO_MODIFICABLE"; // entradas vendidas
	public static final String EVENTO_NO_DESACTIVABLE = "ERROR_EVENTO_NO_DESACTIVABLE"; // entradas vendidas

	public static final String EVENTO_SIN_BUTACAS = "ERROR_EVENTO_SIN_BUTACAS";
	public static final String EVENTO_FILAS_INVALIDAS = "ERROR_EVENTO_FILAS_INVALIDAS";
	public static final String EVENTO_ASIENTOS_INVALIDOS = "ERROR_EVENTO_ASIENTOS_INVALIDOS";
	public static final String EVENTO_TIPO_ASIENTO_NULO = "ERROR_EVENTO_TIPO_ASIENTO_NULO";
	public static final String PERMISO_ADMINISTRADOR = "ERROR_PERMISO_ADMIN";

	
	//===============================
	//----ENTRADAS/CARRITO
	//===============================
	
	public static final String ERROR_FECHAS_NULAS = "ERROR_FECHAS_NULAS";
	public static final String ERROR_ORDEN_FECHAS = "ERROR_ORDEN_FECHAS";
	public static final String ERROR_PROMOCION_COINCIDENTE = "ERROR_PROMOCION_COINCIDENTE";
	public static final String ERROR_CODIGO_UNICO_EVENTO = "ERROR_CODIGO_UNICO_EVENTO";
	public static final String ERROR_DESCUENTO_COINCIDENTE = "ERROR_DESCUENTO_COINCIDENTE";
	
	public static final String EVENTO_CADUCADO = "ERROR_EVENTO_CADUCADO";
	
	public static final String ERROR_PROMOCION_NO_EXISTE = "ERROR_PROMOCION_NO_EXISTE";
	public static final String ERROR_DESCUENTO_NO_EXISTE = "ERROR_DESCUENTO_NO_EXISTE";

	public static final String ENTRADA_NO_ENCONTRADA = "ERROR_ENTRADA_NO_ENCONTRADA";
	public static final String ENTRADA_NO_DISPONIBLE = "ERROR_ENTRADA_NO_DISPONIBLE";
	public static final String BUTACA_NO_CONFIGURADA = "ERROR_BUTACA_NO_CONFIGURADA";
	
	public static final String ERROR_SESION_REQUERIDA = "ERROR_SESION_REQUERIDA";
	public static final String ERROR_ITEM_NO_EXISTE = "ERROR_ITEM_NO_EXISTE";
	public static final String ERROR_CARRITO_VACIO = "ERROR_CARRITO_VACIO";



	public static final String ERROR_CARRITO_NO_ACTIVO = "ERROR_CARRITO_NO_ACTIVO";
	public static final String ERROR_CARRITO_NO_ENCONTRADO = "ERROR_CARRITO_NO_ENCONTRADO";
	public static final String CARRITO_SIN_ENTRADAS = "ERROR_CARRITO_SIN_ENTRADAS";
	
	
	
	// ========================
	// ---- NOTICIAS ----------
	//==========================
	public static final String ERROR_NOTICIA_NO_ENCONTRADA = "ERROR_NOTICIA_NO_ENCONTRADA";

	
	// ============================
	// NOTICIAS
	// ============================
	public static final String NOTICIA_NO_ENCONTRADA = "ERROR_NOTICIA_NO_ENCONTRADA";
	public static final String ERROR_GUARDAR_NOTICIA = "ERROR_GUARDAR_NOTICIA";
	public static final String ERROR_ACTUALIZAR_NOTICIA = "ERROR_ACTUALIZAR_NOTICIA";
	public static final String ERROR_ELIMINAR_NOTICIA = "ERROR_ELIMINAR_NOTICIA";
	public static final String NOTICIA_TITULO_VACIO = "ERROR_NOTICIA_TITULO_VACIO";
	public static final String NOTICIA_CUERPO_VACIO = "ERROR_NOTICIA_CUERPO_VACIO";

	private MensajesError() {
	}
}
