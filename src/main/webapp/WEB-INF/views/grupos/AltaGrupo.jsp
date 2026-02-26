<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Crear Grupo Musical</title>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/css/gruposAltaStyle.css?v=4">

</head>

<body>

    <a href="${pageContext.request.contextPath}/grupos/gruposMusicales"
       class="link-volver">← Volver</a>

    <h2 class="title">Crear nuevo grupo musical</h2>

    <c:if test="${not empty mensajeExito}">
        <div class="mensaje-exito" style="text-align:center;">${mensajeExito}</div>
    </c:if>

    <div class="container">

        <form:form modelAttribute="grupo"
                   action="${pageContext.request.contextPath}/grupos/AltaGrupo"
                   method="post" enctype="multipart/form-data" class="form">

            <!-- Nombre -->
            <label>Nombre del grupo</label>
            <form:input path="nombreGrupo"/>
            <form:errors path="nombreGrupo" cssClass="mensaje-error"/>

            <!-- País -->
            <label>País de origen</label>
            <form:input path="paisOrigen"/>
            <form:errors path="paisOrigen" cssClass="mensaje-error"/>

            <!-- Año -->
            <label>Año de creación</label>
            <form:input path="anoCreacion" type="number" min="1900" max="2100"/>
            <form:errors path="anoCreacion" cssClass="mensaje-error"/>

            <!-- Género -->
            <label>Género musical</label>
            <form:select path="genero.idGenero" required="required">
                <form:option value="" disabled="true" selected="true">Seleccione un género</form:option>
                <form:options items="${generos}" itemValue="idGenero" itemLabel="nombreGenero"/>
            </form:select>
            <form:errors path="genero.idGenero" cssClass="mensaje-error"/>

            <!-- Imagen principal -->
            <label>Imagen principal</label>
            <input type="file" name="imagenPrincipal" accept="image/*">
            <small>Máximo 5MB</small>

            <c:if test="${not empty mensajeError}">
                <div class="mensaje-error">${mensajeError}</div>
            </c:if>

            <!-- Galería -->
            <label>Galería de imágenes (opcional)</label>
            <input type="file" name="imagenesGaleria" accept="image/*" multiple>
            <small>Puedes seleccionar varias imágenes</small>

            <!-- Biografía -->
            <label>Biografía</label>
            <form:textarea path="biografia" rows="3"/>

            <!-- Discografía -->
            <label>Discografía</label>
            <form:textarea path="discografia" rows="3"/>

            <!-- Componentes -->
            <label>Componentes</label>
            <form:textarea path="componentes" rows="3"/>

            <!-- Activo (solo admin) -->
            <c:if test="${isAdmin}">
                <label style="margin-top: 10px;">
                    <form:checkbox path="activo" value="true"/>
                    Activo
                </label>
            </c:if>

            <!-- Botón -->
            <button type="submit" class="btn-submit">Guardar grupo</button>

        </form:form>

    </div>

</body>
</html>
