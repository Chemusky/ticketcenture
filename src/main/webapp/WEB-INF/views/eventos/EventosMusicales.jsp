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
<title>Listado de Eventos Musicales 📜</title>

<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/eventosMusicalesStyle.css'/>">

</head>
<body>

	<!-- Botón para volver atrás -->
	<a href="${pageContext.request.contextPath}/principal"
		class="btn-back-link"> ← Volver a principal </a>

	<h2 class="title">Listado de Eventos Musicales 📜</h2>

	<!-- Mensajes de exito y error -->
	<c:if test="${not empty mensajeError}">
		<div class="error-message">${mensajeError}</div>
	</c:if>

	<c:if test="${not empty mensajeExito}">
		<div class="success-message">${mensajeExito}</div>
	</c:if>


	<!-- =============================== -->
	<!-- PANEL DE FILTROS               -->
	<!-- =============================== -->
	<div class="filter-panel">

		<form
			action="${pageContext.request.contextPath}/eventos/EventosMusicales"
			method="get" class="filters">


			<div class="filter-row">

				<!-- Localidad -->
				<select name="municipio">
					<option value="">Localidad</option>
					<c:forEach var="m" items="${listaMunicipios}">
						<option value="${m.idMunicipio}"
							<c:if test="${municipio != null and municipio == m.idMunicipio}">selected</c:if>>
							${m.nombreMunicipio}</option>
					</c:forEach>
				</select>

				<!-- Grupo -->
				<select name="grupo">
					<option value="">Grupo</option>
					<c:forEach var="g" items="${listaGrupos}">
						<option value="${g.idGrupo}"
							<c:if test="${grupo != null and grupo == g.idGrupo}">selected</c:if>>
							${g.nombreGrupo}</option>
					</c:forEach>
				</select>

				<!-- Fecha -->
				<div class="filter-item">
					<label>Fecha:</label> <input type="date" name="fecha"
						value="${fecha}">
				</div>

				<!-- Estado (solo admin) -->
				<c:if test="${usuarioSesion.admin}">
					<div class="filter-item">
						<label>Activo:</label> <select name="activo">
							<option value="">Todos</option>
							<option value="true"
								<c:if test="${activo != null and activo}">selected</c:if>>
								Activos</option>
							<option value="false"
								<c:if test="${activo != null and not activo}">selected</c:if>>
								Inactivos</option>
						</select>
					</div>
				</c:if>

				<!-- Botón buscar -->
				<div class="filter-item">
					<button type="submit" class="search-btn">Buscar 🔍</button>
				</div>

				<!-- Botón crear evento -->
				<c:if test="${usuarioSesion.admin}">
					<div class="filter-item">
						<a href="${pageContext.request.contextPath}/eventos/AltaEvento"
							class="add-btn">➕ Crear nuevo evento</a>
					</div>
				</c:if>

			</div>

		</form>

	</div>



	<!-- =============================== -->
	<!-- LISTADO DE EVENTOS             -->
	<!-- =============================== -->
	<div class="event-list">

		<!-- En caso de que no haya eventos -->
		<c:if test="${empty eventos}">
			<p class="no-results">No se han encontrado eventos con los
				filtros aplicados 😢</p>
		</c:if>

		<c:forEach var="evento" items="${eventos}">

			<div class="event-card">

				<!-- Miniatura -->
				<img class="event-thumb"
					src="${pageContext.request.contextPath}/eventos/imagen/${evento.idEvento}"
					alt="Imagen del evento">

				<div class="event-info">

					<!-- Nombre -->
					<h3 class="event-title">${evento.nombreEvento}</h3>

					<!-- Grupo -->
					<p class="event-group">Grupo: ${evento.grupo.nombreGrupo}</p>

					<!-- Lugar -->
					<p class="event-location">${evento.municipio.nombreMunicipio}</p>


					<!-- Fecha y hora -->
					<p class="event-date">${evento.fechaEvento.toLocalDate()}-
						${evento.fechaEvento.toLocalTime()}</p>

					<!-- Estado (solo admin) -->
					<c:if test="${usuarioSesion.admin}">
						<p class="event-status">
							Estado: <span
								class="${evento.activo ? 'status-active' : 'status-inactive'}">
								${evento.activo ? 'Activo' : 'Inactivo'} </span>
						</p>
					</c:if>
				</div>
				
				<div class="event-actions">
					<a
						href="${pageContext.request.contextPath}/eventos/DetalleEvento?idEvento=${evento.idEvento}"
						class="btn-action">Ver ficha</a>

					<c:if test="${usuarioSesion.admin}">
						<a
							href="${pageContext.request.contextPath}/eventos/EditarEvento?idEvento=${evento.idEvento}&origen=listado"
							class="btn-action">📝 Modificar</a>
					</c:if>
				</div>



			</div>

		</c:forEach>

	</div>


	<!-- =============================== -->
	<!-- PAGINACIÓN POR NÚMEROS         -->
	<!-- =============================== -->
	<div class="pagination">

		<c:forEach begin="1" end="${totalPaginas}" var="p">

			<a
				href="?pagina=${p}&tam=${tam}<c:if test='${municipio != null}'>&municipio=${municipio}</c:if><c:if test='${grupo != null}'>&grupo=${grupo}</c:if><c:if test='${not empty fecha}'>&fecha=${fecha}</c:if><c:if test='${activo != null}'>&activo=${activo}</c:if>"
				class="page-btn ${p == pagina ? 'active' : ''}"> ${p} </a>


		</c:forEach>

	</div>

</body>
</html>
