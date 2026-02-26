<%@ page contentType="text/html; charset=UTF-8" language="java"
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
<title>Listado de Compradores</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/listadoComprasStyle.css">
</head>

<body>

	<!-- Botones de navegación -->
	<a href="${pageContext.request.contextPath}/principal"
		class="btn-back-link">← Volver a principal</a>
	<a
		href="${pageContext.request.contextPath}/eventos/DetalleEvento?idEvento=${evento.idEvento}"
		class="btn-back-link" class="btn-back-link">← Volver al evento</a>

	<div class="container">

		<h2>Listado de compradores</h2>

		<!-- Fecha del informe (LocalDate, no necesita formateo especial) -->
		<c:set var="fechaFmt" value="${fechaInforme}" />

		<!-- Datos del evento -->
		<div class="direccion-linea">
			<strong>Evento:</strong> ${evento.nombreEvento}
		</div>
		<div class="direccion-linea">
			<strong>Fecha del evento:</strong> ${evento.fechaEvento}
		</div>
		<div class="direccion-linea">
			<strong>Informe generado:</strong> ${fechaFmt}
		</div>

		<!-- Si no hay compradores -->
		<c:if test="${empty compradores}">
			<p class="no-compras">No hay compradores registrados para este
				evento.</p>
		</c:if>

		<!-- Listado de compradores -->
		<ul class="lista-compras">

			<c:forEach var="c" items="${compradores}">

				<li class="compra-item">
					<!-- Cabecera verde fuerte -->
					<div class="compra-resumen-fila">
						<span><strong>${c.nombre}</strong></span> <span>Tel:
							${c.telefono}</span> <span>Entradas: ${c.numeroEntradas}</span>
					</div> <!-- Panel verde clarito -->
					<div class="compra-detalle">
						<table class="tabla-entradas">
							<thead>
								<tr>
									<th>Localidad</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="loc" items="${c.localidades}">
									<tr>
										<td>${loc}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

				</li>

			</c:forEach>

		</ul>

	</div>

</body>
</html>
