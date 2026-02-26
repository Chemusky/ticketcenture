package es.ticketcenture.utilities;

import java.time.format.DateTimeFormatter;

/**
 * Aqui meteremos todos los hardcodeados sueltos que necesitemos 
 * 
 *
 */
public class Constantes {

	//----UsuarioController--------
	public static final String USUARIO = "usuario";
	public static final String IGUAL_ENVIO = "igualEnvio";
	public static final String MENSAJE = "mensaje";
	public static final String DIRECCION_ENVIO = "direccionEnvio";
	public static final String DIRECCION_FACTURACION = "direccionFacturacion";
	public static final String FECHA_INICIO = "fechaInicio";
	public static final String FECHA_FIN = "fechaFin";
	public static final String ACTIVIDADES = "actividades";
	public static final String URL_DESTINO = "urlDestino";
	public static final DateTimeFormatter FORMATTER_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	
	//---Metodo procesarRecuperarPassword---
	public static final String EMAIL ="email";
	public static final String CODIGO_SIMULADO = "codigoSimulado";
	public static final String NUEVA_PASSWORD = "nuevaPassword";
	public static final String CONFIRMAR_PASSWORD = "confirmarPassword";
	public static final String PASSWORD = "password";
	
	//--Metodo darDeBajaUsuario --
	public static final String IDUSUARIO = "idUsuario";
	
}
