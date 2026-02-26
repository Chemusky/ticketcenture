<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>
<!-- <%@ page import="java.util.Base64"%> No se usa--> 
<!-- Esto es para rescatar las imgs en binario de la bbdd -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Pragma" content="no-cache" />

<title>Ficha del grupo</title>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/DetalleGrupoStyle.css">

</head>


<body>
	<a href="${pageContext.request.contextPath}/principal"
		class="btn-back-link"> ← Volver a principal </a>
		
	<!-- Botón volver -->
	<a href="${pageContext.request.contextPath}/grupos/gruposMusicales"
		class="btn-back-link">← Volver</a>

	<div class="container">

		<div class="header-group">
			<div class="band-title">${grupo.nombreGrupo}</div>
			<img
				src="${pageContext.request.contextPath}/grupos/imagen/${grupo.idGrupo}"
				alt="Foto del grupo" class="band-image">
		</div>

		<div class="content-wrapper">
			<div class="main-content">
				<h3>Biografía</h3>
				<p>${grupo.paisOrigen}</p>
				<p>${grupo.anoCreacion}</p>
				<p>${grupo.genero != null ? grupo.genero.nombreGenero : 'Sin género'}</p><!-- Controlamos null para que no falle si el grupo no tiene género. -->				
				<p class="texto-multilinea">${grupo.biografia}</p>
				<h3>Componentes</h3>
				<!-- Aquí llamaremos a un método para que nos traiga una lista/algo de los componentes
					El método ya habrá partido la string y nos traerá una lista 
				<ul>
				</ul>
					-->
				<!-- De momento lo pintamos tal cual -->
				<p class="texto-multilinea">${grupo.componentes}</p>
				<h3>Discografía</h3>
				<!-- Aquí llamaremos a un método para que nos traiga una lista/algo de los discos
					El método ya habrá partido la string y nos traerá una lista 
				<ul>
				</ul>
				De momento lo pintamos tal cual con una p
					-->
        		<p class="texto-multilinea">${grupo.discografia}</p>
				<div class="galeria">
				<h3>Galería</h3>
				<!-- Aqui metemos las imagenes con un bucle -->
	
					<c:forEach var="img" items="${grupo.galeria}">
	
						<img
							src="${pageContext.request.contextPath}/grupos/galeria/imagen/${img.idImagen}"
							class="foto-galeria"
							alt="${grupo.nombreGrupo} - imagen ${img.idImagen}">
					</c:forEach>
				</div>
				<section class="eventos">
				<h2>Próximos eventos</h2>
				<c:if test="${empty eventosFuturos}">
				    <p class="texto-multilinea" style="color: gray;">
				        No hay eventos futuros programados para este grupo.
				    </p>
				</c:if>
				
				<c:if test="${not empty eventosFuturos}">
			        <c:forEach var="evento" items="${eventosFuturos}">
			            <div class="evento">
			                <img src="${pageContext.request.contextPath}/eventos/imagen/${evento.idEvento}"
	               alt="Imagen del evento" />
				            <h3>${evento.nombreEvento}</h3>
							<h4>${evento.municipio.nombreMunicipio}</h4>
				            <p>${evento.fechaEvento.toLocalDate()} - ${evento.fechaEvento.toLocalTime()}</p>
				
				            <a href="${pageContext.request.contextPath}/eventos/DetalleEvento?idEvento=${evento.idEvento}">
				                Ver evento
				            </a>
			            </div>
			        </c:forEach>
				</c:if>
				</section>
			</div>
			<div class="sidebar">
				<!-- Botón de favoritos dinámico -->
				<c:choose>
					<c:when test="${esFavorito}">
						<!-- Si YA es favorito -->
						<form method="post"
							action="${pageContext.request.contextPath}/grupos/desmarcarFavorito">
							<input type="hidden" name="idGrupo" value="${grupo.idGrupo}">
							<button type="submit" class="favorite">💔 Quitar de
								favoritos</button>
						</form>
					</c:when>

					<c:otherwise>
						<!-- Si NO es favorito -->
						<form method="post"
							action="${pageContext.request.contextPath}/grupos/marcarFavorito">
							<input type="hidden" name="idGrupo" value="${grupo.idGrupo}">
							<button type="submit" class="favorite">❤️ Marcar como
								favorito</button>
						</form>
					</c:otherwise>
				</c:choose>


				<!-- Solo Admins -->
				<c:if test="${sessionScope['usuarioSesion'].admin}">

					<form method="get"
						action="${pageContext.request.contextPath}/grupos/EditarGrupo">
						<input type="hidden" name="id" value="${grupo.idGrupo}"> <input
							type="hidden" name="origen" value="ficha">
						<button type="submit" class="favorite">Editar</button>
					</form>

					<form method="post"
						action="${pageContext.request.contextPath}/grupos/EliminarGrupo">
						<input type="hidden" name="id" value="${grupo.idGrupo}">
						<button type="submit" class="favorite"
							style="background-color: #ffcccc;">🗑️ Eliminar</button>
					</form>


				</c:if>

			</div>
		</div>
	</div> 


</body>
</html>