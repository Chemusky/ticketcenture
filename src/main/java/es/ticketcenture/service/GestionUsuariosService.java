package es.ticketcenture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ticketcenture.daos.IUsuarioDAO;
import es.ticketcenture.entities.TipoUsuario;
import es.ticketcenture.entities.Usuario;
import es.ticketcenture.utilities.MensajesError;

/**
 * Servicio exclusivo para gestión de los administradores
 *
 */
@Service
@Transactional
public class GestionUsuariosService {

	private final IUsuarioDAO usuarioDAO;

	@Autowired
	public GestionUsuariosService(IUsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	/**
	 * Listado con filtros
	 */
	public List<Usuario> listarConFiltros(String nombre, String email, String rol, String tipoCliente, String estado) {
		return usuarioDAO.listarConFiltros(nombre, email, rol, tipoCliente, estado);
	}

	/**
	 * Buscar usuario por ID
	 */
	public Usuario buscarPorId(Integer idUsuario) {
		return usuarioDAO.buscarPorId(idUsuario);
	}

	/**
	 * Actualizar usuario desde el panel de administración
	 */
	public void actualizarUsuarioDesdeAdmin(Integer idUsuario, String nombre, String apellidos, Integer telefono,
			String dni, String username, String email, String admin, String tipoUsuario) {

		Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

		if (usuario == null) {
			throw new IllegalArgumentException(MensajesError.ERROR_USUARIO_INEXISTENTE);
		}

		// Validar duplicado de username
		Usuario existente = usuarioDAO.buscarPorUsername(username);
		if (existente != null && !existente.getIdUsuario().equals(idUsuario)) {
			throw new IllegalArgumentException(MensajesError.USERNAME_YA_EXISTE);
		}

		usuario.setNombre(nombre);
		usuario.setApellidos(apellidos);
		usuario.setTelefono(telefono);
		usuario.setDni(dni);
		usuario.setUsername(username);
		usuario.setEmail(email);

		usuario.setAdmin(admin != null);

		if (tipoUsuario != null && !tipoUsuario.isEmpty()) {
			usuario.setTipoUsuario(TipoUsuario.valueOf(tipoUsuario));
		}

		usuarioDAO.actualizar(usuario);
	}

	/**
	 * Bloquear usuario
	 */
	public void bloquearUsuario(Integer idUsuario) {
		usuarioDAO.bloquear(idUsuario);
	}

	/**
	 * Desbloquear usuario
	 */
	public void desbloquearUsuario(Integer idUsuario) {
		usuarioDAO.desbloquear(idUsuario);
	}

	/**
	 * Eliminar usuario
	 */
	public void eliminarUsuario(Integer idUsuario) {

		if (usuarioDAO.tieneCompras(idUsuario)) {
			throw new IllegalArgumentException(MensajesError.ERROR_USUARIO_CON_COMPRAS);
		}
		usuarioDAO.eliminar(idUsuario);
	}

}
