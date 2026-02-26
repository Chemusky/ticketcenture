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
<title>Recuperar Contraseña</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/RecuperarPasswordStyle.css">
</head>
<body>

	<!-- Titulo del jsp -->
	<c:choose>
		<c:when test="${modo == 'cambioPassword'}">
			<h2 class="form-title">Cambiar contraseña</h2>
		</c:when>
		<c:otherwise>
			<h2 class="form-title">Recuperar contraseña</h2>
		</c:otherwise>
	</c:choose>


	<!-- Mensaje de error -->
	<c:if test="${not empty mensajeError}">
		<div class="error-message">${mensajeError}</div>
	</c:if>

	<c:if test="${not empty mensajeExito}">
		<div class="success-message">${mensajeExito}</div>
	</c:if>

	<!-- Acción dinámica del formulario -->
	<c:choose>
		<c:when test="${modo == 'cambioPassword'}">
			<c:set var="accionForm"
				value="${pageContext.request.contextPath}/usuarios/cambiarPassword" />
		</c:when>
		<c:otherwise>
			<c:set var="accionForm"
				value="${pageContext.request.contextPath}/usuarios/recuperarPassword" />
		</c:otherwise>
	</c:choose>

	<!-- Contenedor que almacena el formulario -->
	<div class="form-container">
		<form class="form" action="${accionForm}" method="post">


			<!-- Email -->
			<div class="email-container">
				<label>Email</label> <input name="email" type="email"
					value="${email}" required="required">
			</div>

			<!-- Boton para simular envío de codigo -->
			<div class="button-container">
				<button type="button" onclick="mostrarPopup()">Enviar
					código</button>
			</div>

			<!-- Modal simulado -->
			<div id="popup" class="modal">
				<div class="modal-content">
					<button class="close" onclick="cerrarPopup()">&times;</button>
					<div class="password-container">
						<label>Código recibido (simulado)</label> <input type="text"
							id="codigoSimulado" name="codigoSimulado">
					</div>
					<div class="button-container">
						<button type="button" onclick="validarCodigo()">Validar</button>
					</div>
					<div id="codigoValido"
						style="display: none; color: green; margin-top: 10px;">✅
						Código validado correctamente</div>
				</div>
			</div>

			<!-- Nueva password -->
			<div class="password-container">
				<label>Nueva contraseña</label> <input name="nuevaPassword"
					type="password" required="required">
			</div>

			<!-- Confirmar nueva password -->
			<div class="password-container">
				<label>Confirmar nueva contraseña</label> <input
					name="confirmarPassword" type="password" required="required">
			</div>

			<div class="button-container">
				<button type="submit">Guardar Contraseña</button>
			</div>

		</form>
	</div>

	<script>
		function mostrarPopup() {
			document.getElementById("popup").style.display = "block";
		}

		function cerrarPopup() {
			document.getElementById("popup").style.display = "none";
			document.getElementById("codigoValido").style.display = "none";
		}

		function validarCodigo() {
			// Mostrar el check
			document.getElementById("codigoValido").style.display = "block";

			// Cerrar el modal automaticamente después de 2.0 segundos
			setTimeout(function() {
				cerrarPopup();
			}, 2000);
		}
	</script>


</body>
</html>
