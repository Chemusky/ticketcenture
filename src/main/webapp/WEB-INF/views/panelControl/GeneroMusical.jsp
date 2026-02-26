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
<title>Gestión de Géneros</title>

<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/eventosMusicalesStyle.css'/>">

</head>
<body>

	<!-- Botón volver -->
	<a href="${pageContext.request.contextPath}/panel/PanelControl"
		class="btn-back-link"> ← Volver al panel </a>

	<h2 class="title">Gestión de Géneros musicales</h2>

	<!-- Mensajes -->
	<c:if test="${not empty mensajeError}">
		<div class="error-message">${mensajeError}</div>
	</c:if>

	<c:if test="${not empty mensajeExito}">
		<div class="success-message">${mensajeExito}</div>
	</c:if>

	<!-- TOP BAR -->
	<div class="filter-panel">
		<div class="filter-row">
			<a href="${pageContext.request.contextPath}/genero/GeneroMusical"
				class="add-btn"> Limpiar formulario </a>
		</div>
	</div>



	<!-- =============================== -->
	<!-- FORMULARIO CREAR / EDITAR -->
	<!-- =============================== -->
	<div class="form-card">

		<h3>${genero != null ? "Modificar Género" : "Crear Nuevo Género"}</h3>

		<form
			action="${pageContext.request.contextPath}/genero/${genero != null ? 'EditarGenero' : 'AltaGenero'}"
			method="post">


			<c:if test="${genero != null}">
				<input type="hidden" name="idGenero" value="${genero.idGenero}">
			</c:if>

			<label>Género:</label> <input type="text" name="nombreGenero"
				class="input-field"
				value="${genero != null ? genero.nombreGenero : ''}" required>


			<button type="submit" class="add-btn" style="margin-top: 1rem;">
				${genero != null ? "Guardar Cambios" : "Crear Género"}</button>
		</form>

	</div>


	<!-- =============================== -->
	<!-- LISTADO DE GENEROS (ESTILO EVENTOS) -->
	<!-- =============================== -->
	<div class="event-list">

		<c:if test="${empty listaGeneros}">
			<p class="no-results">No hay géneros registrados 😢</p>
		</c:if>

		<c:forEach var="g" items="${listaGeneros}">

			<div class="event-card">

				<!-- INFORMACIÓN DEL GENERO -->
				<div class="event-info">

					<p class="event-group">${g.nombreGenero}</p>

				</div>

				<!-- ACCIONES -->
				<div class="event-actions">

					<a
						href="${pageContext.request.contextPath}/genero/EditarGenero?idGenero=${g.idGenero}"
						class="btn-action edit"> 📝 Modificar </a>

					<form
						action="${pageContext.request.contextPath}/genero/EliminarGenero"
						method="post">
						<input type="hidden" name="idGenero" value="${g.idGenero}">
						<button type="submit" class="btn-action"
							style="background-color: #ff4d4d; color: white;"
							onclick="return confirm('¿Eliminar genero?')">🗑
							Eliminar</button>
					</form>

				</div>

			</div>

		</c:forEach>

	</div>

</body>
</html>
