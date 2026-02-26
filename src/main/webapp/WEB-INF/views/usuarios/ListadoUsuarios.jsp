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
<title>Listado de Usuarios</title>

<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/ListadoUsuarios.css'/>">

</head>
<body>

	<!-- Boón para volver al panel -->
	<a href="${pageContext.request.contextPath}/panel/PanelControl"
		class="btn-back-link">← Volver al Panel de Control</a>

	<h2 class="title">Listado de Usuarios</h2>

	<!-- Mensajes de exito y error -->
	<c:if test="${not empty mensajeError}">
		<div class="error-message">${mensajeError}</div>
	</c:if>

	<c:if test="${not empty mensajeExito}">
		<div class="success-message">${mensajeExito}</div>
	</c:if>

	<!-- Filtro -->
	<div class="filter-container">

		<form action="${pageContext.request.contextPath}/usuarios/listado"
			method="get" class="filter-form">

			<div class="filter-item">
				<label for="nombre">Nombre:</label> <input type="text" id="nombre"
					name="nombre" value="${param.nombre}">
			</div>

			<div class="filter-item">
				<label for="email">Email:</label> <input type="text" id="email"
					name="email" value="${param.email}">
			</div>

			<div class="filter-item">
				<label for="rol">Rol:</label> <select id="rol" name="rol">
					<option value="">Todos</option>
					<option value="ADMIN" ${param.rol == 'ADMIN' ? 'selected' : ''}>Administrador</option>
					<option value="CLIENTE" ${param.rol == 'CLIENTE' ? 'selected' : ''}>Usuario</option>
				</select>
			</div>


			<div class="filter-item">
				<label for="tipoCliente">Tipo de cliente:</label> <select
					id="tipoCliente" name="tipoCliente">
					<option value="">Todos</option>
					<option value="NORMAL"
						${param.tipoCliente == 'NORMAL' ? 'selected' : ''}>Normal</option>
					<option value="PREMIUM"
						${param.tipoCliente == 'PREMIUM' ? 'selected' : ''}>Premium</option>
					<option value="VIP" ${param.tipoCliente == 'VIP' ? 'selected' : ''}>VIP</option>
				</select>
			</div>

			<div class="filter-item">
				<label for="estado">Estado:</label> <select id="estado"
					name="estado">
					<option value="">Todos</option>
					<option value="ACTIVO"
						${param.estado == 'ACTIVO' ? 'selected' : ''}>Activo</option>
					<option value="BLOQUEADO"
						${param.estado == 'BLOQUEADO' ? 'selected' : ''}>Bloqueado</option>
				</select>
			</div>

			<button type="submit" class="btn-filter">Buscar</button>

		</form>
	</div>

	<!-- Listado de usuarios -->
	<div class="table-container">

		<table class="table-users">
			<thead>
				<tr>
					<th>Nombre</th>
					<th>Email</th>
					<th>Rol</th>
					<th>Tipo Cliente</th>
					<th>Estado</th>
					<th>Acciones</th>
				</tr>
			</thead>

			<tbody>

				<!-- Si hay usuarios, los mostramos -->
				<c:if test="${not empty listaUsuarios}">
					<c:forEach var="u" items="${listaUsuarios}">
						<tr>
							<td>${u.nombre}</td>
							<td>${u.email}</td>
							<td>${u.admin ? "Admin" : "Usuario"}</td>
							<td>${u.tipoUsuario}</td>
							<td>${u.activo ? "Activo" : "Bloqueado"}</td>


							<td class="acciones"><c:if
									test="${u.idUsuario != sessionScope.usuarioSesion.idUsuario}">

									<!-- Editar -->
									<a
										href="${pageContext.request.contextPath}/usuarios/modificar?id=${u.idUsuario}">
										Editar </a>

									<!-- Bloquear / Desbloquear -->
									<c:choose>
										<c:when test="${u.activo}">
											<a
												href="${pageContext.request.contextPath}/usuarios/bloquear?id=${u.idUsuario}">
												Bloquear </a>
										</c:when>
										<c:otherwise>
											<a
												href="${pageContext.request.contextPath}/usuarios/desbloquear?id=${u.idUsuario}">
												Desbloquear </a>
										</c:otherwise>
									</c:choose>

									<!-- Eliminar -->
									<button type="button" class="btn-delete"
										onclick="confirmarEliminacion(${u.idUsuario})">
										Eliminar</button>

								</c:if></td>
						</tr>
					</c:forEach>
				</c:if>

				<!-- Si NO hay usuarios -->
				<c:if test="${empty listaUsuarios}">
					<tr>
						<td colspan="6" style="text-align: center; padding: 20px;">
							No hay datos para mostrar</td>
					</tr>
				</c:if>

			</tbody>
		</table>

	</div>

	<c:set var="urlEliminar"
		value="${pageContext.request.contextPath}/usuarios/eliminar" />


	<!-- Script para popup para preguntar si se elimina el usuario -->
	<script>
    function confirmarEliminacion(idUsuario) {
        if (confirm("¿Estás seguro de que deseas eliminar este usuario? Esta acción no se puede deshacer.")) {

            const form = document.createElement("form");
            form.method = "GET";
            form.action = "${urlEliminar}";

            const input = document.createElement("input");
            input.type = "hidden";
            input.name = "id";
            input.value = idUsuario;

            form.appendChild(input);
            document.body.appendChild(form);
            form.submit();
        }
    }
</script>



</body>
</html>
