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
<title>Gestión de Noticias Musicales</title>

<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/noticiasStyle.css'/>">

</head>
<body>

	<c:if test="${empty paginaActual}">
		<c:set var="paginaActual" value="1" />
	</c:if>


	<!-- Botón volver -->
	<a href="${pageContext.request.contextPath}/panel/PanelControl"
		class="btn-back-link"> ← Volver al panel </a>

	<h2 class="title">Gestión de Noticias Musicales 📰</h2>

	<!-- Mensajes -->
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
			action="${pageContext.request.contextPath}/noticias/NoticiasMusicales"
			method="get" class="filters">

			<div class="filter-row">

				<!-- Título -->
				<div class="filter-item">
					<label>Título</label> <input type="text" name="titulo"
						value="${titulo}">
				</div>

				<!-- Fecha publicación -->
				<div class="filter-item">
					<label>Fecha publicación</label> <input type="date"
						name="fechaPublicacion" value="${fechaPublicacion}">
				</div>

				<!-- Grupo Musical relacionado -->
				<div class="filter-item">
					<label>Grupo</label> <select name="idGrupo">
						<option value="">-- Todos --</option>

						<c:forEach var="g" items="${listaGrupos}">
							<option value="${g.idGrupo}"
								<c:if test="${idGrupo == g.idGrupo}">selected</c:if>>
								${g.nombreGrupo}</option>
						</c:forEach>
					</select>
				</div>


				<!-- Botón buscar -->
				<div class="filter-item filter-button">
					<label class="invisible-label">Buscar</label>
					<button type="submit" class="search-btn">Buscar 🔍</button>
				</div>

				<!-- Botón crear noticia -->
				<c:if test="${usuarioSesion.admin}">
					<div class="filter-item filter-button">
						<label class="invisible-label">Crear</label> <a
							href="${pageContext.request.contextPath}/noticias/AltaNoticia"
							class="add-btn">➕ Crear nueva noticia</a>
					</div>
				</c:if>


			</div>

		</form>

	</div>

	<!-- =============================== -->
	<!-- LISTADO DE NOTICIAS            -->
	<!-- =============================== -->
	<div class="news-list">

		<c:if test="${empty listaNoticias}">
			<p class="no-results">No se han encontrado noticias 😢</p>
		</c:if>

		<c:forEach var="n" items="${listaNoticias}">

			<div class="news-card">

				<!-- Miniatura -->
				<img class="news-thumb"
					src="${pageContext.request.contextPath}/noticias/imagen/${n.idNoticia}"
					alt="Imagen de la noticia">

				<div class="news-info">

					<!-- Título -->
					<h3 class="news-title">${n.titulo}</h3>

					<!-- Primer párrafo del cuerpo -->
					<p class="news-summary">${fn:substring(n.cuerpo, 0, 150)}...</p>

					<!-- Fecha publicación (solo si está publicada) -->
					<c:if test="${n.publicado}">
						<p class="news-date">Publicada el: ${n.fechaPublicacion}</p>
					</c:if>

					<!-- Estado -->
					<p class="news-status">
						Estado: <span
							class="${n.publicado ? 'status-published' : 'status-draft'}">
							${n.publicado ? 'Publicada' : 'Borrador'} </span>
					</p>

				</div>

				<!-- ACCIONES -->
				<div class="news-actions">

					<a
						href="${pageContext.request.contextPath}/noticias/FichaNoticia?idNoticia=${n.idNoticia}"
						class="btn-action">Ver ficha</a> <a
						href="${pageContext.request.contextPath}/noticias/EditarNoticia?idNoticia=${n.idNoticia}"
						class="btn-action edit">📝 Modificar</a>

					<form
						action="${pageContext.request.contextPath}/noticias/EliminarNoticia"
						method="post">
						<input type="hidden" name="idNoticia" value="${n.idNoticia}">
						<button type="submit" class="btn-action delete"
							onclick="return confirm('¿Eliminar noticia?')">🗑
							Eliminar</button>
					</form>

				</div>

			</div>

		</c:forEach>

		<!-- =============================== -->
		<!-- PAGINACIÓN SOLO CON NÚMEROS    -->
		<!-- =============================== -->
		<c:if test="${totalPaginas > 1}">
			<div class="pagination">

				<c:forEach begin="1" end="${totalPaginas}" var="p">
					<a class="page-number ${p == paginaActual ? 'active' : ''}"
						href="${pageContext.request.contextPath}/noticias/NoticiasMusicales?pagina=${p}&titulo=${titulo}&fechaPublicacion=${fechaPublicacion}&idGrupo=${idGrupo}">
						${p} </a>
				</c:forEach>

			</div>
		</c:if>



	</div>

</body>
</html>

