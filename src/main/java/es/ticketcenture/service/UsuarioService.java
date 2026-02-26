package es.ticketcenture.service;

import es.ticketcenture.entities.Direccion;
import es.ticketcenture.entities.TipoDireccion;
import es.ticketcenture.entities.Usuario;
import es.ticketcenture.utilities.Acciones;
import es.ticketcenture.utilities.MensajesError;
import es.ticketcenture.daos.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Servicio que implementa la logica de negocio de Usuario.
 */
@Service
public class UsuarioService {

	// Estas constantes se usan para el login, en el metodo buscarUsuario
	private static final int MAX_INTENTOS_FALLIDOS = 3;

	/** El DAO de usuario */
	private final UsuarioDAO usuarioDAO;

	/** Encriptado de password */
	private final BCryptPasswordEncoder passwordEncoder;

	/**
	 * Dependencia con esta clase para registrarla al mismo tiempo en la bbdd
	 */
	private final ActividadService actividadService;

	/**
	 * Instancia un nuevo Servicio de Usuario.
	 *
	 * @param usuarioDAO DAO del usuario
	 */
	@Autowired
	public UsuarioService(UsuarioDAO usuarioDAO, BCryptPasswordEncoder passwordEncoder,
			ActividadService actividadService, DireccionService direccionService) {
		this.usuarioDAO = usuarioDAO;
		this.passwordEncoder = passwordEncoder;
		this.actividadService = actividadService;
	}

	/**
	 * Registrar usuario llamando al DAO.
	 *
	 * @param usuario    Usuario a registrar
	 * @param igualEnvio Checkbox que indica si la direcci�n de facturaci�n es
	 *                   igual a la de env�o
	 * @param request    Petici�n HTTP para obtener la IP
	 */
	@Transactional
	public void registrarUsuario(Usuario usuario, String igualEnvio, HttpServletRequest request) {

		if (usuarioDAO.buscarPorEmail(usuario.getEmail()) != null) {
			throw new IllegalArgumentException(MensajesError.EMAIL_YA_EXISTE);
		}

		if (usuarioDAO.buscarPorUsername(usuario.getUsername()) != null) {
			throw new IllegalArgumentException(MensajesError.USERNAME_YA_EXISTE);
		}

		// Encriptar antes de guardar
		String hash = passwordEncoder.encode(usuario.getPassword());
		usuario.setPassword(hash);
		usuario.setActivo(true);

		List<Direccion> direcciones = usuario.getDirecciones();
		if (direcciones != null && direcciones.size() >= 2) {
			Direccion envio = direcciones.get(0);
			Direccion facturacion = direcciones.get(1);

			if (igualEnvio != null) {
				copiarDireccion(envio, facturacion);
			}

			// Relacion bidireccional
			envio.setUsuario(usuario);
			envio.setTipoDireccion(TipoDireccion.ENVIO);

			facturacion.setUsuario(usuario);
			facturacion.setTipoDireccion(TipoDireccion.FACTURACION);

			// Aseguramos que est�n en la lista del usuario
			usuario.getDirecciones().clear();
			usuario.getDirecciones().add(envio);
			usuario.getDirecciones().add(facturacion);
		}

		// Guardar usuario y direcciones en cascada
		usuarioDAO.insertar(usuario);

		// Registrar actividad
		String ip = request.getRemoteAddr();
		actividadService.insertarActividad(usuario, Acciones.REGISTRO, ip);
	}

	/**
	 * Copiar datos de una direccion origen a otra destino.
	 * 
	 * @param origen
	 * @param destino
	 */
	private void copiarDireccion(Direccion origen, Direccion destino) {
		destino.setCalle(origen.getCalle());
		destino.setNumero(origen.getNumero());
		destino.setPiso(origen.getPiso());
		destino.setEscalera(origen.getEscalera());
		destino.setSegundaLineaDireccion(origen.getSegundaLineaDireccion());
		destino.setCodigoPostal(origen.getCodigoPostal());
		destino.setLocalidad(origen.getLocalidad());
		destino.setProvincia(origen.getProvincia());
	}

	/** Buscar usuario por email */
	@Transactional(readOnly = true)
	public Usuario buscarPorEmail(String email) {
		return usuarioDAO.buscarPorEmail(email);
	}

	/** Flujo completo de recuperacion de password */
	@Transactional
	public void recuperarPassword(String email, String nuevaPassword, HttpServletRequest request) {
		// Validacion rapida de contrase�a
		if (nuevaPassword == null || nuevaPassword.isBlank()) {
			throw new IllegalArgumentException(MensajesError.PASSWORD_VACIA);
			}
		if (nuevaPassword.length() < 8) {
			throw new IllegalArgumentException(MensajesError.PASSWORD_CORTA);
			}
		// Validacion de usuario con mail exsistente
		Usuario usuario = usuarioDAO.buscarPorEmail(email);
		if (usuario == null) {
			throw new IllegalArgumentException(MensajesError.EMAIL_NO_EXISTE);
			}

		String hash = passwordEncoder.encode(nuevaPassword);
		usuario.setPassword(hash);
		usuarioDAO.actualizar(usuario);

		String ip = request.getRemoteAddr();
		actividadService.insertarActividad(usuario, Acciones.RECUPERAR_PASSWORD, ip);
	}

	/**
	 * Metodo que busca al usuario-password en la bbdd cuando le das al boton de
	 * Login
	 * 
	 * @param email    email del usuario a buscar
	 * @param password la password del usuario a buscar
	 * @param request
	 * @return
	 */
	@Transactional(noRollbackFor = IllegalArgumentException.class)
	public Usuario buscaUsuario(String email, String password, HttpServletRequest request) {

		Usuario usuario = usuarioDAO.buscarPorEmail(email);

		if (usuario == null) {
			actividadService.insertarActividad(null, Acciones.LOGIN_FALLIDO, request.getRemoteAddr(), email); 
			throw new IllegalArgumentException(MensajesError.USUARIO_INCORRECTO); 
			}
		// Cuando ya tenemos el usuario miramos el campo activo, y si esta a false
		if (!usuario.isActivo()) {
			actividadService.insertarActividad(usuario, Acciones.LOGIN_BLOQUEADO, request.getRemoteAddr()); 
			throw new IllegalArgumentException(MensajesError.LOGIN_BLOQUEADO);
			}

		// Aqui comprobamos si las contrase�as coinciden
		boolean passIgual = passwordEncoder.matches(password, usuario.getPassword());
		if (!passIgual) {
			// Si la pass no es valida, vamos sumando al contador
			int intentos = usuario.getIntentosFallidos() + 1;
			usuarioDAO.actualizarIntentosFallidos(usuario.getIdUsuario(), intentos);

			// Si llega a los 3 intentos fallidos, lo boqueamos
			if (intentos >= MAX_INTENTOS_FALLIDOS) {
				usuario.setActivo(false);

				usuarioDAO.actualizarIntentosFallidos(usuario.getIdUsuario(), intentos);
				usuarioDAO.actualizar(usuario);

				// registramos la actividad
				actividadService.insertarActividad(usuario, Acciones.LOGIN_FALLIDO, request.getRemoteAddr());
				actividadService.insertarActividad(usuario, Acciones.LOGIN_BLOQUEADO, request.getRemoteAddr());

				throw new IllegalArgumentException(MensajesError.LOGIN_BLOQUEADO);
			} else {
				// Si aun no llega al maximo, solo guardamos mensaje de login fallido
				// usuarioDAO.insertar(usuario);
				actividadService.insertarActividad(usuario, Acciones.LOGIN_FALLIDO, request.getRemoteAddr());

				throw new IllegalArgumentException(MensajesError.USUARIO_INCORRECTO);
				}
		}

		// Si la password esta bien, reiniciamos el contador
		usuario.setIntentosFallidos(0);
		usuarioDAO.actualizar(usuario);

		actividadService.insertarActividad(usuario, Acciones.LOGIN, request.getRemoteAddr());

		
		return usuario;

	}

	/**
	 * Metodo para modificar los datos del usuario
	 * 
	 * @param usuario
	 */
	@Transactional
	public void modificarDatosUsuario(Usuario usuarioSesion, Usuario usuarioFormulario) {

		if (usuarioSesion == null || usuarioSesion.getIdUsuario() <= 0) {
			throw new IllegalArgumentException(MensajesError.USUARIO_INCORRECTO);
			} 
		
		//Actualizamos los datos que queremos del form
		usuarioSesion.setNombre(usuarioFormulario.getNombre()); 
		usuarioSesion.setApellidos(usuarioFormulario.getApellidos()); 
		usuarioSesion.setTelefono(usuarioFormulario.getTelefono()); 
		usuarioSesion.setUsername(usuarioFormulario.getUsername()); 
		usuarioSesion.setDni(usuarioFormulario.getDni());
		
		//Tambien actualizamos las direcciones
		if (usuarioFormulario.getDirecciones() != null) { 
			//Primero las limpiamos
			usuarioSesion.getDirecciones().clear();  
			usuarioSesion.getDirecciones().addAll(usuarioFormulario.getDirecciones());
			//Establecemos la relacion inversa
			usuarioSesion.getDirecciones().forEach(d -> d.setUsuario(usuarioSesion));
		}
		
		usuarioDAO.actualizar(usuarioSesion);
	}
	
	/**
	 * Dar de baja un usuario (baja logica: activo=false) Lanza con
	 * LocalDateTime.now para evitar algun problema con la BBDD.
	 *
	 * @param idUsuario ID del usuario a dar de baja
	 * @param request   Peticion HTTP
	 */
	@Transactional
	public void darDeBajaUsuario(Integer idUsuario, HttpServletRequest request) {
		Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
		if (usuario == null) {
			throw new IllegalArgumentException(MensajesError.USUARIO_ID_NO_EXISTE);		}

		usuario.setActivo(false);
		usuario.setFechaBaja(LocalDateTime.now());
		usuarioDAO.actualizar(usuario);

		String ip = request.getRemoteAddr();
		actividadService.insertarActividad(usuario, Acciones.BAJA_USUARIO, ip);
	}

	/**
	 * Obtener las direcciones de los usuarios
	 * 
	 * @param idUsuario
	 * @return
	 */
	@Transactional(readOnly = true)
	public Usuario obtenerUsuarioConDirecciones(Integer idUsuario) {
		return usuarioDAO.buscarPorIdConDirecciones(idUsuario);
	}
	
	/**
	 * Metodo que realiza el cambio de password
	 * 
	 * @param usuario
	 * @param passwordActual
	 * @param passwordNueva
	 * @param request
	 */
	@Transactional
	public void cambiarPassword(Usuario usuario, String passwordActual, String passwordNueva, HttpServletRequest request) {

	    boolean coincide = passwordEncoder.matches(passwordActual, usuario.getPassword());
	    if (!coincide) {
	    	throw new IllegalArgumentException(MensajesError.PASSWORD_ACTUAL_INCORRECTA);
	    }

	    validarPasswordSegura(passwordNueva);

	    String hash = passwordEncoder.encode(passwordNueva);
	    usuario.setPassword(hash);

	    usuarioDAO.actualizar(usuario);

	    actividadService.insertarActividad(usuario, Acciones.CAMBIO_PASSWORD, request.getRemoteAddr());
	}
	
	/**
	 * Actualiza la contraseña del usuario sin pedir la contraseña actual.
	 * Se usa en el cambio de contraseña con doble factor simulado.
	 */
	@Transactional
	public void actualizarPassword(Usuario usuario, String nuevaPassword, HttpServletRequest request) {

	    validarPasswordSegura(nuevaPassword);

	    String hash = passwordEncoder.encode(nuevaPassword);
	    usuario.setPassword(hash);

	    usuarioDAO.actualizar(usuario);

	    actividadService.insertarActividad(usuario, Acciones.CAMBIO_PASSWORD, request.getRemoteAddr());
	}

	
	/**
	 * Metodo para que el password tenga mas de 8 letras
	 * 
	 * @param password
	 */
	
	private void validarPasswordSegura(String password) {
	    if (password == null || password.length() < 8) {
	    	throw new IllegalArgumentException(MensajesError.PASSWORD_CORTA);	    }
	}
	
	
	/**
	 * Metodo que nos indica si el usuario tiene un carrito activo
	 * @param idUsuario
	 * @return
	 */
	@Transactional(readOnly = true)
	public boolean usuarioTieneCarritoActivo(Integer idUsuario) {
		return usuarioDAO.tieneCarritoActivo(idUsuario);
	}

}
