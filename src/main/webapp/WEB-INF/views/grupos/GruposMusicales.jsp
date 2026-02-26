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
<title>Listado de Grupos Musicales 🤘</title>

<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/gruposMusicalesStyle.css'/>">

</head>
<body>

	<!-- Botón para volver atrás-->
	<a href="${pageContext.request.contextPath}/principal"
		class="btn-back-link"> ← Volver a principal </a>

	<h2 class="title">Listado de Grupos Musicales 🤘</h2>

	<!-- Mensajes de exito y error -->
	<c:if test="${not empty mensajeError}">
		<div class="error-message">${mensajeError}</div>
	</c:if>

	<c:if test="${not empty mensajeExito}">
		<div class="success-message">${mensajeExito}</div>
	</c:if>

	<div class="container">

		<!-- TOP BAR -->
		<div class="top-bar">

			<!-- Filtros-->
			<form method="get"
				action="${pageContext.request.contextPath}/grupos/gruposMusicales"
				class="filters">

				<!-- Nombre -->
				<input type="text" name="nombre" value="${fn:trim(nombre)}"
					placeholder="Buscar por nombre">


				<!-- Género -->
				<select name="genero">
					<option value="">Género musical</option>
					<c:forEach var="g" items="${listaGeneros}">
						<option value="${g.idGenero}"
							<c:if test="${genero != null and genero == g.idGenero}">
                selected
            </c:if>>
							${g.nombreGenero}</option>
					</c:forEach>
				</select>


				<!-- Favoritos -->
				<c:if test="${not empty usuarioSesion}">
					<label> <input type="checkbox" name="favoritos"
						<c:if test="${favoritos != null}">checked</c:if> /> Solo
						favoritos
					</label>
				</c:if>

				<!-- Activo / Inactivo (solo admin) -->
				<c:if test="${usuarioSesion.admin}">
					<select name="activo">
						<option value="">Estado</option>
						<option value="true"
							<c:if test="${activo != null and activo}">selected</c:if>>
							Activos</option>
						<option value="false"
							<c:if test="${activo != null and not activo}">selected</c:if>>
							Inactivos</option>
					</select>
				</c:if>



				<button type="submit" class="btn search-btn">Buscar 🔍</button>
			</form>

			<!-- Botón para añadir el grupo -->
			<c:if test="${usuarioSesion.admin}">
				<a href="${pageContext.request.contextPath}/grupos/AltaGrupo"
					class="btn add-btn">Añadir grupo 🤘</a>
			</c:if>

		</div>

		<!-- Listado de grupos musicales -->
		<div class="group-list">

			<c:if test="${empty grupos}">
				<p class="no-results">No se encontraron grupos 😢</p>
			</c:if>

			<c:forEach var="grupo" items="${grupos}">
				<div class="group-card">

					<!-- Imagen -->
					<img
						src="${pageContext.request.contextPath}/grupos/imagen/${grupo.idGrupo}"
						alt="Pendiente de imagen" class="group-img">

					<!-- Info -->
					<div class="group-info">
						<h3 class="group-name">${grupo.nombreGrupo}</h3>
						<p class="group-genre">${grupo.genero.nombreGenero}</p>

						<c:if test="${usuarioSesion.admin}">
							<p class="group-status ${grupo.activo ? 'active' : 'inactive'}">
								${grupo.activo ? 'Activo' : 'Inactivo'}</p>
						</c:if>
					</div>

					<!-- Acciones varias -->
					<div class="group-actions">

						<a
							href="${pageContext.request.contextPath}/grupos/detalleGrupo?id=${grupo.idGrupo}"
							class="btn small-btn">Ver ficha</a>

						<c:if test="${usuarioSesion.admin}">
							<a
								href="${pageContext.request.contextPath}/grupos/EditarGrupo?id=${grupo.idGrupo}&origen=lista"
								class="btn small-btn edit-btn">Editar</a>
						</c:if>
					</div>

				</div>
			</c:forEach>

		</div>

		<!-- Paginación -->
		<div class="pagination">
			<c:forEach begin="1" end="${totalPaginas}" var="p">
				<a
					href="?pagina=${p}&tam=${tam} &nombre=${fn:trim(nombre)} &genero=${genero} &favoritos=${favoritos} &activo=${activo}"
					class="page-btn ${p == pagina ? 'active' : ''}"> ${p} </a>
			</c:forEach>
		</div>

	</div>
</body>
</html>
