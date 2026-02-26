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
<title>Perfil del usuario</title>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/perfilUsuarioStyle.css">

</head>
<body>
	<!-- Botón para volver atrás -->
	<a href="${pageContext.request.contextPath}/principal"
		class="btn-back-link"> ← Volver a principal </a>

	<div class="container">

		<!-- Formulario editable -->
		<div class="form-container">
			<h2>Perfil del usuario</h2>

			<!-- BOTON PARA EL HISTORICO DE COMPRAS -->
			<div class="form-row center-row">
				<form method="get"
					action="${pageContext.request.contextPath}/usuarios/mostrarHistorico">
					<button type="submit" class="btn btn-historial">Historial
						de compras</button>
				</form>
			</div>

			<form
				action="${pageContext.request.contextPath}/usuarios/modificarUsuario"
				method="post">
				<input type="hidden" name="idUsuario" value="${usuario.idUsuario}" />
				<input type="hidden" name="email" value="${usuario.email}" />
				<!-- <input type="hidden" name="password" value="${usuario.password}" />--->

				<div class="form-row">
					<div class="form-group">
						<label for="nombre">Nombre:</label> <input type="text" id="nombre"
							name="nombre" value="${usuario.nombre}" />
					</div>

					<div class="form-group">
						<label for="apellidos">Apellidos:</label> <input type="text"
							id="apellidos" name="apellidos" value="${usuario.apellidos}" />
					</div>
				</div>

				<div class="form-row">
					<div class="form-group">
						<label for="telefono">Teléfono:</label> <input type="text"
							id="telefono" name="telefono" value="${usuario.telefono}" />
					</div>

					<div class="form-group">
						<label for="dni">DNI:</label> <input type="text" id="dni"
							name="dni" value="${usuario.dni}" readonly />
					</div>
				</div>

				<div class="form-row">
					<div class="form-group">
						<label for="username">Usuario:</label> <input type="text"
							id="username" name="username" value="${usuario.username}" />
					</div>

					<div class="form-group">
						<!-- Campo vacío para mantener la estructura de 2 columnas -->
					</div>
				</div>

				<!-- Direcciones de envío -->
				<h3>Dirección de envío</h3>

				<c:forEach var="direccion" items="${usuario.direcciones}"
					varStatus="i">
					<c:if test="${direccion.tipoDireccion == 'ENVIO'}">

						<!-- Campos ocultos necesarios -->
						<input type="hidden" name="direcciones[${i.index}].idDireccion"
							value="${direccion.idDireccion}" />
						<input type="hidden" name="direcciones[${i.index}].tipoDireccion"
							value="${direccion.tipoDireccion}" />

						<div class="form-row">
							<div class="form-group">
								<label>Calle:</label> <input type="text"
									name="direcciones[${i.index}].calle" value="${direccion.calle}" />
							</div>
							<div class="form-group">
								<label>2ª línea:</label> <input type="text"
									name="direcciones[${i.index}].segundaLineaDireccion"
									value="${direccion.segundaLineaDireccion}" />
							</div>
						</div>

						<div class="form-row">
							<div class="form-group">
								<label>Número:</label> <input type="text"
									name="direcciones[${i.index}].numero"
									value="${direccion.numero}" />
							</div>
							<div class="form-group">
								<label>Piso:</label> <input type="text"
									name="direcciones[${i.index}].piso" value="${direccion.piso}" />
							</div>
						</div>

						<div class="form-row">
							<div class="form-group">
								<label>Escalera:</label> <input type="text"
									name="direcciones[${i.index}].escalera"
									value="${direccion.escalera}" />
							</div>
							<div class="form-group">
								<label>Localidad:</label> <input type="text"
									name="direcciones[${i.index}].localidad"
									value="${direccion.localidad}" />
							</div>
						</div>

						<div class="form-row">
							<div class="form-group">
								<label>Provincia:</label> <input type="text"
									name="direcciones[${i.index}].provincia"
									value="${direccion.provincia}" />
							</div>
							<div class="form-group">
								<label>Código Postal:</label> <input type="text"
									name="direcciones[${i.index}].codigoPostal"
									value="${direccion.codigoPostal}" />
							</div>
						</div>



					</c:if>
				</c:forEach>

				<!-- Dirección de Facturación -->
				<h3>Dirección de Facturación</h3>
				<c:forEach var="direccion" items="${usuario.direcciones}"
					varStatus="i">
					<c:if test="${direccion.tipoDireccion == 'FACTURACION'}">
						<input type="hidden" name="direcciones[${i.index}].idDireccion"
							value="${direccion.idDireccion}" />
						<input type="hidden" name="direcciones[${i.index}].tipoDireccion"
							value="${direccion.tipoDireccion}" />
						<div class="form-row">
							<div class="form-group">
								<label>Calle:</label> <input type="text"
									name="direcciones[${i.index}].calle" value="${direccion.calle}" />
							</div>
							<div class="form-group">
								<label>2ª línea:</label> <input type="text"
									name="direcciones[${i.index}].segundaLineaDireccion"
									value="${direccion.segundaLineaDireccion}" />
							</div>
						</div>
						<div class="form-row">
							<div class="form-group">
								<label>Número:</label> <input type="text"
									name="direcciones[${i.index}].numero"
									value="${direccion.numero}" />
							</div>
							<div class="form-group">
								<label>Piso:</label> <input type="text"
									name="direcciones[${i.index}].piso" value="${direccion.piso}" />
							</div>
						</div>
						<div class="form-row">
							<div class="form-group">
								<label>Escalera:</label> <input type="text"
									name="direcciones[${i.index}].escalera"
									value="${direccion.escalera}" />
							</div>
							<div class="form-group">
								<label>Localidad:</label> <input type="text"
									name="direcciones[${i.index}].localidad"
									value="${direccion.localidad}" />
							</div>
						</div>
						<div class="form-row">
							<div class="form-group">
								<label>Provincia:</label> <input type="text"
									name="direcciones[${i.index}].provincia"
									value="${direccion.provincia}" />
							</div>
							<div class="form-group">
								<label>Código Postal:</label> <input type="text"
									name="direcciones[${i.index}].codigoPostal"
									value="${direccion.codigoPostal}" />
							</div>
						</div>
						<hr />
					</c:if>
				</c:forEach>

				<div class="button-container">
					<button type="submit" class="btn">Guardar cambios</button>
				</div>
			</form>
		</div>

		<!-- Acciones -->
		<div class="actions">
			<a href="${pageContext.request.contextPath}/usuarios/cambiarPassword"
				class="btn"> Cambiar contraseña </a>
			<form id="formBaja"
				action="${pageContext.request.contextPath}/usuarios/baja"
				method="post" style="display: inline;">
				<input type="hidden" name="idUsuario" value="${usuario.idUsuario}" />
				<button type="button" class="btn btn-danger"
					style="cursor: pointer;" onclick="confirmarBaja()">Dar de
					baja</button>
			</form>


		</div>

		<!-- Listado de actividad -->
		<h3>Actividad del usuario</h3>

		<!--  Mostrar errores -->
		<c:if test="${not empty mensajeError}">
			<div class="error-message">${mensajeError}</div>
		</c:if>

		<c:if test="${not empty mensajeExito}">
			<div class="success-message">${mensajeExito}</div>
		</c:if>


		<!-- Listado de actividad -->
		<form
			action="${pageContext.request.contextPath}/usuarios/perfilUsuario"
			method="post">
			<div class="form-group">
				<label for="fechaInicio">Desde:</label> <input type="date"
					id="fechaInicio" name="fechaInicio" value="${fechaInicio}" />
			</div>

			<div class="form-group">
				<label for="fechaFin">Hasta:</label> <input type="date"
					id="fechaFin" name="fechaFin" value="${fechaFin}" />
			</div>

			<div class="button-container">
				<button type="submit" class="btn">Filtrar</button>
			</div>

		</form>

		<!--  Tabla solo si hay actividades -->
		<c:if test="${not empty actividades}">
			<table>
				<thead>
					<tr>
						<th>Fecha</th>
						<th>Acción</th>
						<th>IP</th>
						<th>Detalles</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="actividad" items="${actividades}">
						<tr>
							<td>${actividad.fechaActividad}</td>
							<td>${actividad.accion}</td>
							<td>${actividad.ipActividad}</td>
							<td>${actividad.detalles}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>

	</div>

	<script>
		function confirmarBaja() {
			if (confirm("¿Estás seguro de que quieres dar de baja tu cuenta? Esta acción no se puede deshacer.")) {
				document.getElementById("formBaja").submit();
			}
		}
	</script>

</body>
</html>
