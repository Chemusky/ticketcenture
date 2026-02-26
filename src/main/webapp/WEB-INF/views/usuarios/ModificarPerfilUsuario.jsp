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
<title>Modificar perfil del usuario</title>

<link rel="stylesheet" type="text/css"
    href="<c:url value='/resources/css/ModificarPerfilUsuario.css'/>">

</head>
<body>

    <!-- Botón para volver al listado -->
    <a href="${pageContext.request.contextPath}/usuarios/listado"
        class="btn-back-link">← Volver al listado</a>

    <h2 class="title">Modificar perfil de usuario</h2>

    <!-- Mensajes de éxito y error -->
    <c:if test="${not empty mensajeError}">
        <div class="error-message">${mensajeError}</div>
    </c:if>

    <c:if test="${not empty mensajeExito}">
        <div class="success-message">${mensajeExito}</div>
    </c:if>

    <!-- Formulario para modificar el perfil del usuario -->
    <div class="form-container">

        <form action="${pageContext.request.contextPath}/usuarios/modificar"
            method="post" class="user-form">

            <!-- ID oculto -->
            <input type="hidden" name="idUsuario" value="${usuario.idUsuario}">

            <div class="form-item">
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" value="${usuario.nombre}">
            </div>

            <div class="form-item">
                <label for="apellidos">Apellidos:</label>
                <input type="text" id="apellidos" name="apellidos" value="${usuario.apellidos}">
            </div>

            <div class="form-item">
                <label for="telefono">Teléfono:</label>
                <input type="number" id="telefono" name="telefono" value="${usuario.telefono}">
            </div>

            <div class="form-item">
                <label for="dni">DNI:</label>
                <input type="text" id="dni" name="dni" value="${usuario.dni}">
            </div>

            <div class="form-item">
                <label for="username">Nombre de usuario:</label>
                <input type="text" id="username" name="username" value="${usuario.username}">
            </div>

            <div class="form-item">
                <label for="email">Email:</label>
                <input type="text" id="email" name="email" value="${usuario.email}">
            </div>

            <!-- Opciones avanzadas solo admin -->
            <c:if test="${sessionScope.usuarioSesion.admin}">
                <h3>Opciones avanzadas (solo administradores)</h3>

                <div class="form-row">

                    <div class="form-group">
                        <label for="tipoUsuario">Tipo de usuario:</label>
                        <select id="tipoUsuario" name="tipoUsuario">
                            <option value="NORMAL"
                                ${usuario.tipoUsuario == 'NORMAL' ? 'selected' : ''}>Normal</option>
                            <option value="PREMIUM"
                                ${usuario.tipoUsuario == 'PREMIUM' ? 'selected' : ''}>Premium</option>
                            <option value="VIP"
                                ${usuario.tipoUsuario == 'VIP' ? 'selected' : ''}>VIP</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="admin">Administrador:</label>
                        <input type="checkbox" id="admin" name="admin"
                            ${usuario.admin ? 'checked' : ''} />
                    </div>

                </div>
            </c:if>

            <button type="submit" class="btn-save">Guardar cambios</button>

        </form>

    </div>

</body>
</html>
