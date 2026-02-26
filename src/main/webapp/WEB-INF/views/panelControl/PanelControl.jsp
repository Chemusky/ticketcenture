<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Pragma" content="no-cache" />
<title>Panel de Control</title>

<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/PanelControl.css'/>">

</head>
<body>
	<!-- Botón para volver atrás -->
	<a href="${pageContext.request.contextPath}/principal"
		class="btn-back-link"> ← Volver a principal </a>

	<!-- Título principal -->
	<h2 class="title">Panel de Control</h2>

	<!-- Mensajes de exito y error -->
	<c:if test="${not empty mensajeError}">
		<div class="error-message">${mensajeError}</div>
	</c:if>

	<c:if test="${not empty mensajeExito}">
		<div class="success-message">${mensajeExito}</div>
	</c:if>

	<!-- Contenedor principal del panel -->
	<div class="panel-container">

		<!-- Tarjeta: Gestión de Descuentos -->
		<div class="panel-card">
			<h3 class="title-panel-card">Gestión de Descuentos</h3>
			<p>Administrar descuentos por tipo de usuario (Normal, Premium,
				VIP).</p>
			<a
				href="${pageContext.request.contextPath}/descuento/DescuentoUsuario"
				class="btn-panel">Ir a descuentos</a>
		</div>

		<!-- Tarjeta: Gestión de Promociones -->
		<div class="panel-card">
			<h3 class="title-panel-card">Gestión de Promociones</h3>
			<p>Administrar códigos promocionales asociados a eventos.</p>
			<a
				href="${pageContext.request.contextPath}/promocion/PromocionEvento"
				class="btn-panel">Ir a promociones</a>
		</div>

		<!-- Tarjeta: Gestión de Géneros musicales -->
		<div class="panel-card">
			<h3 class="title-panel-card">Gestión de Géneros musicales</h3>
			<p>Administrar géneros musicales asociados a grupos.</p>
			<a href="${pageContext.request.contextPath}/genero/GeneroMusical"
				class="btn-panel">Ir a géneros </a>
		</div>

		<!-- Tarjeta: Gestión de Noticias -->
		<div class="panel-card">
			<h3 class="title-panel-card">Gestión de Noticias</h3>
			<p>Administración completa de las noticias del sistema</p>
			<a
				href="${pageContext.request.contextPath}/noticias/NoticiasMusicales"
				class="btn-panel">Ir a noticias</a>
		</div>

		<!-- Tarjeta: Gestión de Usuarios -->
		<div class="panel-card">
			<h3 class="title-panel-card">Gestión de Usuarios</h3>

			<p>Administrar usuarios del sistema: roles, tipos de cliente y
				estado.</p>


			<a href="${pageContext.request.contextPath}/usuarios/listado"
				class="btn-panel"> Listado de usuarios </a>
		</div>

		<%-- SE DEJA COMENTADO <div class="panel-card">
				<a href="${pageContext.request.contextPath}/usuarios/alta"
					class="btn-panel"> Crear nuevo usuario </a>
			</div> --%>
	</div>






</body>
</html>