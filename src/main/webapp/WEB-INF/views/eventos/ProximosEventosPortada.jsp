<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="eventos">
	<h2>Próximos eventos</h2>

	<!-- Si no hay eventos -->
	<c:if test="${empty proximosEventos}">
		<p>No hay eventos próximos disponibles.</p>
	</c:if>

	<!-- Listado dinámico -->
	<c:forEach var="evento" items="${proximosEventos}">
		<div class="evento">
			<img
				src="${pageContext.request.contextPath}/eventos/imagen/${evento.idEvento}"
				alt="Imagen del evento" />

			<h3>${evento.nombreEvento}</h3>
			<p>Grupo: ${evento.grupo.nombreGrupo}</p>

			<p>${evento.fechaEvento.toLocalDate()}-
				${evento.fechaEvento.toLocalTime()}</p>

			<a class="event-link"
				href="${pageContext.request.contextPath}/eventos/DetalleEvento?idEvento=${evento.idEvento}">
				Ver evento </a>
		</div>
	</c:forEach>
</section>
