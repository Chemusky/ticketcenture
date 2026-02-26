<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>

<!DOCTYPE html>
<html>
<head>
<title>Modificar Grupo Musical</title>

<!-- CSS nuevo -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/gruposAltaStyle.css">
</head>

<body>

	<a href="${pageContext.request.contextPath}/principal"
		class="btn-back-link"> ← Volver a principal </a>
		
	<!-- Botón volver -->
	<a href="${pageContext.request.contextPath}/grupos/gruposMusicales"
		class="btn-back-link">← Volver</a>	

	<h2 class="title">Modificar grupo musical</h2>

	<!-- Mensajes -->
	<c:if test="${not empty mensajeError}">
		<div class="mensaje-error">${mensajeError}</div>
	</c:if>

	<c:if test="${not empty mensajeExito}">
		<div class="mensaje-exito">${mensajeExito}</div>
	</c:if>

	<div class="container">

		<form action="${pageContext.request.contextPath}/grupos/EditarGrupo"
			method="post" enctype="multipart/form-data" class="form">

			<input type="hidden" name="origen" value="${param.origen}"> 
			<input type="hidden" name="idGrupo" value="${grupo.idGrupo}"> 
			
				<label>Nombre del grupo</label> 
				<input type="text" name="nombreGrupo" value="${grupo.nombreGrupo}" required> 
				
				<label>País de origen</label> 
				<input type="text" name="paisOrigen" value="${grupo.paisOrigen}" required> 
				
				<label>Año de creación</label> 
				<input type="number" name="anoCreacion" min="1900" max="2100" value="${grupo.anoCreacion}" required> 
				
				
				<label>Género musical</label> 
				
				<select name="genero.idGenero" required>
				<option value="">Seleccione un género</option>
				<c:forEach var="g" items="${generos}">
					<option value="${g.idGenero}"
						<c:if test="${grupo.genero != null && g.idGenero == grupo.genero.idGenero}"> 
                	selected 
                	</c:if>>
						${g.nombreGenero}</option>
				</c:forEach>
			</select> <label>Imagen principal</label>
			<c:if test="${grupo.imagenPrincipal != null}">
				<img
					src="${pageContext.request.contextPath}/grupos/imagen/${grupo.idGrupo}"
					alt="Imagen actual"
					style="max-width: 200px; display: block; margin-bottom: 10px;">
			</c:if>

			<input type="file" name="imagenPrincipal" accept="image/*">
			<small>Máximo 5MB</small>


			<!-- Galería de imágenes -->
			<label>Galería de imágenes (opcional)</label>
			<div class="galeria">
			<c:if test="${not empty galeria}">
					<c:forEach var="img" items="${galeria}">
						<img
							src="${pageContext.request.contextPath}/grupos/galeria/imagen/${img.idImagen}"
							class="foto-galeria"
							alt="${grupo.nombreGrupo} - imagen ${img.idImagen}">
					</c:forEach>
				</c:if>
			</div>
			<input type="file" name="imagenesGaleria" accept="image/*" multiple>
			<small>Puedes seleccionar varias imágenes</small> <label>Biografía</label>
			<textarea name="biografia" rows="3">${grupo.biografia}</textarea>

			<label>Discografía</label>
			<textarea name="discografia" rows="3">${grupo.discografia}</textarea>

			<label>Componentes</label>
			<textarea name="componentes" rows="3">${grupo.componentes}</textarea>

			<c:if test="${usuarioSesion.admin}">
				<label style="margin-top: 10px;"> 
					<input type="checkbox" name="activo" value="true"
						<c:if test="${grupo.activo}">checked</c:if>> Activo
				</label>
			</c:if>

			<button type="submit" class="btn-submit">Guardar grupo</button>
			
			<!-- Botón volver -->
			<a href="${pageContext.request.contextPath}/grupos/gruposMusicales">
				<button class="btn-submit">Cancelar</button></a>

		</form>

	</div>

</body>
</html>
