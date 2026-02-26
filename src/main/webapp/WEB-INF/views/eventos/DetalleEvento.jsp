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

<title>Ficha del evento</title>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/DetalleEventoStyle.css">

</head>

<body>

	<!-- ESTO LO USAMOS PARA DAR DINAMISMO A LA SELECCION DE ENTRADAS -->
	<script>
    // Construimos un mapa con la info de cada tipo de asiento
    const butacasData = {
        <c:forEach var="b" items="${evento.butacas}">
            "${b.tipoAsiento.idTipo}": {
                filas: ${b.numeroFilas},
                asientos: ${b.numeroAsientos}
            },
        </c:forEach>
    };
	</script>

	<script
		src="${pageContext.request.contextPath}/resources/js/detalleEvento.js"></script>


	<!-- Botones volver -->
	<a href="${pageContext.request.contextPath}/principal"
		class="btn-back-link">← Volver a principal</a>
	<a href="${pageContext.request.contextPath}/eventos/EventosMusicales"
		class="btn-back-link">← Volver</a>

	<!-- Mensajes de exito y error -->
	<c:if test="${not empty mensajeError}">
		<div class="error-message">${mensajeError}</div>
	</c:if>

	<c:if test="${not empty mensajeExito}">
		<div class="success-message">${mensajeExito}</div>
	</c:if>

	<div class="container">

		<!-- CABECERA: nombre + imagen -->
		<div class="header-group">
			<div class="band-title">${evento.nombreEvento}</div>

			<img
				src="${pageContext.request.contextPath}/eventos/imagen/${evento.idEvento}"
				alt="Imagen del evento" class="band-image">
		</div>

		<div class="content-wrapper">

			<!-- COLUMNA IZQUIERDA -->
			<div class="main-content">

				<h3>Descripción</h3>
				<p class="texto-multilinea">${evento.descripcionEvento}</p>

				<h3>Grupo musical</h3>
				<p>${evento.grupo.nombreGrupo}</p>

				<h3>Tipos de asiento y disponibilidad</h3>

				<table class="tabla-stock">
					<thead>
						<tr>
							<th>Tipo de asiento</th>
							<th>Precio</th>
							<th>Stock disponible</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="entry" items="${stockPorTipo}">
							<tr>
								<td>${entry.key.nombreTipo}</td>
								<td><c:forEach var="b" items="${evento.butacas}">
										<c:if test="${b.tipoAsiento.idTipo == entry.key.idTipo}">
                                            ${b.precio} €
                                        </c:if>
									</c:forEach></td>
								<td>${entry.value}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<!-- FORMULARIO DE SELECCION DE ENTRADAS -->
				<!-- SI EL EVENTO YA HA PASADO --> 
				<c:if test="${evento.fechaEvento.isBefore(now)}"> 
					<div class="error-message" style="margin-top:20px;"> 
						No se pueden comprar entradas para un evento pasado. 
					</div> 
				</c:if>
				<c:if test="${!evento.fechaEvento.isBefore(now)}">
					<h3>Seleccionar entradas</h3>
					<form action="${pageContext.request.contextPath}/carrito/anadir"
						method="post">
	
						<input type="hidden" name="idEvento" value="${evento.idEvento}">
						<input type="hidden" name="idUsuario"
							value="${sessionScope['usuarioSesion'].idUsuario}">
	
						<table class="tabla-entradas">
							<thead>
								<tr>
									<th>Tipo de asiento</th>
									<th>Fila</th>
									<th>Asiento</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="i" begin="1" end="5">
									<tr>
										<!-- SELECT Tipo de asiento -->
										<td><select name="tipoAsiento${i}" class="select-tipo"
											data-index="${i}">
												<option value="">Selecciona el tipo de asiento</option>
												<c:forEach var="b" items="${evento.butacas}">
													<option value="${b.tipoAsiento.idTipo}">
														${b.tipoAsiento.nombreTipo} - ${b.precio} €</option>
												</c:forEach>
										</select></td>
	
										<!-- SELECT fila -->
										<td><select name="fila${i}" class="select-fila"
											data-index="${i}">
												<option value="">Selecciona la fila</option>
												<c:forEach var="b" items="${evento.butacas}">
													<c:forEach var="f" begin="1" end="${b.numeroFilas}">
														<option value="${f}">${f}</option>
													</c:forEach>
												</c:forEach>
										</select></td>
	
										<!-- SELECT asiento -->
										<td><select name="asiento${i}" class="select-asiento"
											data-index="${i}">
												<option value="">Selecciona el asiento</option>
												<c:forEach var="b" items="${evento.butacas}">
													<c:forEach var="a" begin="1" end="${b.numeroAsientos}">
														<option value="${a}">${a}</option>
													</c:forEach>
												</c:forEach>
										</select></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
	
						<!-- Código promocional -->
						<div class="promo-container">
							<label for="codigoPromocional">Código promocional:</label> <input
								type="text" id="codigoPromocional" name="codigoPromocional">
						</div>
	
						<div class="promo-boton">
							<button type="submit" class="btn-panel">Añadir al carrito
								🛒</button>
						</div>
	
					</form>
				</c:if>

			</div>

			<!-- COLUMNA DERECHA -->
			<div class="sidebar">

				<!-- INFO DEL EVENTO: lugar, fecha, hora -->
				<div class="evento-info-box">
					<h4>Información del evento</h4>

					<p>
						<strong>Lugar:</strong> ${evento.municipio.nombreMunicipio}
					</p>
					<p>
						<strong>Fecha:</strong> ${evento.fechaEvento.toLocalDate()}
					</p>
					<p>
						<strong>Hora:</strong> ${evento.fechaEvento.toLocalTime()}
					</p>
				</div>

				<!-- BOTONES -->
				<div class="evento-botones-box">
				
					<%--
					SE QUEDA COMENTADO POR SI SE NECESITA MÁS ADELANTE
					<form method="get"
						action="${pageContext.request.contextPath}/entradas/comprar">
						<input type="hidden" name="idEvento" value="${evento.idEvento}">
						<button type="submit" class="btn-accion comprar">🎟️
							Comprar entradas</button>
					</form> --%>

					<!-- Solo admins -->
					<c:if test="${sessionScope['usuarioSesion'].admin}">
						<form method="get"
							action="${pageContext.request.contextPath}/eventos/EditarEvento?idEvento=${evento.idEvento}">
							<input type="hidden" name="idEvento" value="${evento.idEvento}">
							<input type="hidden" name="origen" value="ficha">
							<button type="submit" class="btn-accion editar">Editar</button>
						</form>

						<form method="post"
							action="${pageContext.request.contextPath}/eventos/EliminarEvento">
							<input type="hidden" name="idEvento" value="${evento.idEvento}">
							<button type="submit" class="btn-accion eliminar"
								style="background-color: #ffcccc;">🗑️ Eliminar</button>
						</form>
						<form method="get"
					      	action="${pageContext.request.contextPath}/eventos/${evento.idEvento}/compradores">
						    <button type="submit" class="btn-accion listar">
						        📄 Ver listado de compradores
						    </button>
						</form>
						
					</c:if>

				</div>

				<!-- GALERÍA -->
				<div class="galeria">
					<h3>Galería</h3>

					<c:forEach var="img" items="${galeria}">
						<img
							src="${pageContext.request.contextPath}/eventos/galeria/imagen/${img.idImagen}"
							class="foto-galeria"
							alt="Imagen del evento ${evento.nombreEvento}">
					</c:forEach>
				</div>

			</div>
		</div>
	</div>

</body>
</html>
