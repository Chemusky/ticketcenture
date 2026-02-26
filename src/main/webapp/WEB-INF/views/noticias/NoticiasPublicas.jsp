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
<title>Noticias Musicales</title>
 
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/noticiasStyle.css'/>">
 
</head>
<body>
 
	<!-- Botón volver -->
<a href="${pageContext.request.contextPath}/principal"
		class="btn-back-link">← Volver a principal</a>
 
	<h2 class="title">Noticias Musicales 📰</h2>
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
 
		<form action="${pageContext.request.contextPath}/noticias/NoticiasPublicas"
			method="get" class="filters">
 
			<div class="filter-row">
 
				<!-- Título -->
<div class="filter-item">
<label>Título</label> <input type="text" name="titulo"
						value="${titulo}">
</div>
 
				<!-- Grupo -->
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
 
				<!-- Fecha publicación -->
<div class="filter-item">
<label>Fecha publicación</label> <input type="date"
						name="fechaPublicacion" value="${fechaPublicacion}">
</div>
 
				<!-- Botón buscar -->
<div class="filter-item filter-button">
<label class="invisible-label">Buscar</label>
<button type="submit" class="search-btn">Buscar 🔍</button>
</div>
 
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
 
					<!-- Título clicable -->
<h3 class="news-title">
<a
							href="${pageContext.request.contextPath}/noticias/FichaNoticia?idNoticia=${n.idNoticia}">
							${n.titulo} </a>
</h3>
 
					<!-- Resumen -->
<p class="news-summary">${fn:substring(n.cuerpo, 0, 150)}...</p>
 
					<!-- Fecha publicación -->
<p class="news-date">Publicada el: ${n.fechaPublicacion}</p>
 
				</div>
 
				<!-- ACCIONES SOLO PARA ADMIN -->
<c:if test="${usuarioSesion != null && usuarioSesion.admin}">
<div class="news-actions">
<a
							href="${pageContext.request.contextPath}/noticias/EditarNoticia?idNoticia=${n.idNoticia}"
							class="btn-action edit">📝 Editar</a>
</div>
</c:if>
 
			</div>
 
		</c:forEach>
 
		<!-- =============================== -->
<!-- PAGINACIÓN                      -->
<!-- =============================== -->
<c:if test="${totalPaginas > 1}">
<div class="pagination">
 
				<c:forEach begin="1" end="${totalPaginas}" var="p">
<a class="page-number ${p == paginaActual ? 'active' : ''}"
						href="${pageContext.request.contextPath}/noticias/NoticiasPublicas?pagina=${p}&titulo=${titulo}&idGrupo=${idGrupo}&idEvento=${idEvento}&fechaPublicacion=${fechaPublicacion}">
						${p} </a>
</c:forEach>
 
			</div>
</c:if>
 
	</div>
 
</body>
</html>