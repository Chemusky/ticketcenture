package es.ticketcenture.utilities;

import es.ticketcenture.entities.Usuario;

public class ValidarAdmin {

	public static boolean esAdmin(Usuario usuarioSesion) {
		//Usuario usuarioSesion = (Usuario) session.getAttribute("usuarioSesion");

		if (usuarioSesion == null) {
			return false;
		}

		return usuarioSesion.isAdmin();
		
	}

}
