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

<title>Inicio de sesión</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/InicioSesionUsuariosStyle.css">
</head>
<body>
	<!-- Botón para volver atrás -->
	<a href="${pageContext.request.contextPath}/principal"
		class="btn-back-link"> ← Volver a principal </a>

	<!-- Título del jsp -->
	<h2 class="form-title">Inicio de Sesión</h2>
		
	<!-- Mensajes de exito y error -->
		<c:if test="${not empty mensajeError}">
    		<div class="error-message">${mensajeError}</div>
		</c:if>

		<c:if test="${not empty mensajeExito}">
   			 <div class="success-message">${mensajeExito}</div>
		</c:if>

		
	<!-- Contenedero que almacena el formulario -->
	<div class="form-container">
		<!-- Formulario para iniciar sesión -->

	<form class="form" action="${pageContext.request.contextPath}/usuarios/login" method="post">
	
		<!-- Contenedor del email -->
		<div class="email-container">
			<label>Email</label> 
			<input name="email" type="email" required="required">
		</div>
		
		<!-- Contenedor de contraseña -->
		<div class="password-container">
			<label>Contraseña</label> 
			<input name="password" type="password" required="required">
		</div>
		
		<div class="button-container">
			<button type="submit">Iniciar Sesión</button>
			<a class="remember-password-link" href="${pageContext.request.contextPath}/usuarios/recuperarPassword">¿Olvidaste tu contraseña? Vamos a recuperarla</a>
		</div>
		
	</form>

	</div>


</body>
</html>