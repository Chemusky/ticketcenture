package es.ticketcenture.utilities;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class FiltroAutenticacion implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		//Obtiene la URL sin el contextPath, que mete luego spring
		String path = req.getRequestURI().substring(req.getContextPath().length());
		String query = req.getQueryString(); 
		String urlCompleta = (query != null) ? path + "?" + query : path;
		
		HttpSession session = req.getSession(false);
		boolean logueado = (session != null && session.getAttribute("usuarioSesion") != null);

		// Rutas publicas
		boolean publicPage = 
				path.equals("/") ||
				path.contains("index.jsp") ||
				path.contains("/principal") || 
				path.contains("/usuarios/formulario") || 
				path.contains("/usuarios/registro") || 
				path.contains("/usuarios/formularioLogin")|| 
				path.contains("/usuarios/login") ||
				path.contains("/resources/") ||
				path.contains("/usuarios/recuperarPassword") ||
				path.contains("/imagen") ||
				path.matches(".*\\.(png|jpg|jpeg|gif|css|js)$");
		
		if (logueado || publicPage) {
			chain.doFilter(request, response);
		} else {
			// Guarda URL que queria visitar
			if (!path.contains("/usuarios/formularioLogin") && 
				!path.contains("/usuarios/login")) {
				req.getSession(true).setAttribute(Constantes.URL_DESTINO, urlCompleta); 
			}
			// Redirigir al login
			res.sendRedirect(req.getContextPath() + "/usuarios/formularioLogin");
		}
	}

}
