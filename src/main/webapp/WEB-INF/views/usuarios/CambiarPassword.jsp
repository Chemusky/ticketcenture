<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cambiar contraseña</title>

<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/resources/css/CambiarPasswordStyle.css">

</head>
<body>

<a href="${pageContext.request.contextPath}/usuarios/perfilUsuario" class="link-volver">
    ← Volver al perfil
</a>

<h2 class="form-title">Cambiar contraseña</h2>

<c:if test="${not empty mensajeError}">
    <div class="mensaje-error">${mensajeError}</div>
</c:if>

<c:if test="${not empty mensajeExito}">
    <div class="mensaje-exito">${mensajeExito}</div>
</c:if>

<div class="form-container">
<form action="${pageContext.request.contextPath}/usuarios/cambiarPassword" method="post" class="form">

    <label>Contraseña actual:</label>
    <input type="password" name="passwordActual" required />

    <label>Nueva contraseña:</label>
    <input type="password" name="passwordNueva" required />
    <small>La contraseña debe tener al menos 8 caracteres.</small>

    <label>Repetir nueva contraseña:</label>
    <input type="password" name="passwordNueva2" required />

    <div class="button-container">
        <button type="submit">Actualizar contraseña</button>
    </div>

</form>
</div>

</body>
</html>
