<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Pragma" content="no-cache" />
<title>TicketCenture G2</title>

<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/principalStyle.css'/>">

</head>

<body>
	<header class="header">
		<div class="container">
			<!-- Usuario no autenticado -->
			<c:if test="${empty sessionScope['usuarioSesion']}">
				<div class="login login-right">
					<a
						href="${pageContext.request.contextPath}/usuarios/formularioLogin">Login</a>
					<a href="${pageContext.request.contextPath}/usuarios/formulario">Registro</a>
				</div>
			</c:if>

			<!-- Menú autenticado -->
			<c:if test="${not empty sessionScope['usuarioSesion']}">
				<div class="login login-center">
					<nav class="main-menu">
						<ul>
							<li><a href="${pageContext.request.contextPath}/eventos/EventosMusicales">Eventos</a></li>
							<li><a href="${pageContext.request.contextPath}/grupos/gruposMusicales">Grupos</a></li>
							<li><a href="${pageContext.request.contextPath}/noticias/NoticiasPublicas">Noticias</a></li>
							<li><a href="${pageContext.request.contextPath}/usuarios/perfilUsuario">Acceso Perfil</a></li>
							
							<!-- Solo Admins -->
							<c:if test="${sessionScope['usuarioSesion'].admin}">
								<li><a href="${pageContext.request.contextPath}/panel/PanelControl">Panel de control</a></li>
							</c:if>
							
							<!-- CARRITO // Solo se tiene que ver si está activo -->
							<!--  AÑADIR CONDICIONAL CUANDO TENGAMOS CLARO COMO LO VAMOS A RECUPERAR -->
							<c:if test="${carritoActivo}">
								<li><a href="${pageContext.request.contextPath}/carrito/MostrarCarrito">🛒Carrito</a></li>
							</c:if>
							
							<li><a
								href="${pageContext.request.contextPath}/usuarios/logout">Cerrar
									sesión</a></li>
						</ul>
					</nav>
				</div>
			</c:if>

			<!-- BIENVENIDA SI HAY USUARIO EN SESION -->
			<c:if test="${not empty sessionScope['usuarioSesion']}">
				<h2 class="welcome-message-user">Bienvenid@ ${sessionScope['usuarioSesion'].nombre}</h2>
			</c:if>
			
			<!-- Título -->
			<h1>TicketCenture</h1>
			
			</div>
			
	</header>



	<div class="eventos">
		<!-- AQUI METEMOS EL INCLUDE CON LOS EVENTOS -->
		<%@ include file="../eventos/ProximosEventosPortada.jsp"%>
	</div>

	<div class="section-row">

    <!-- COLUMNA IZQUIERDA -->
    <div class="col-izquierda">

        <div class="noticias">
            <%@ include file="../noticias/NoticiasPortada.jsp"%>
        </div>

        <div class="noticias">
            <%@ include file="../noticias/NoticiasRecientesFavoritos.jsp"%>
        </div>

    </div>

    <!-- COLUMNA DERECHA -->
    <div class="col-derecha">

        <div class="merchan">
            <%@ include file="../merchandising/SeccionMerchandising.jsp"%>
        </div>

        <div class="favoritos">
            <%@ include file="../noticias/FavoritosPortada.jsp"%>
        </div>

    </div>

</div>





</body>
</html>