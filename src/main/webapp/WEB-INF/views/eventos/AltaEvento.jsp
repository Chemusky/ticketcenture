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
    <title>Crear Evento Musical</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/eventosAltaStyle.css?v=4">
</head>

<body>

<a href="${pageContext.request.contextPath}/eventos/EventosMusicales"
   class="link-volver">← Volver</a>

<h2 class="title">Crear nuevo evento musical</h2>

<c:if test="${not empty mensajeExito}">
    <div class="mensaje-exito" style="text-align:center;">${mensajeExito}</div>
</c:if>

<c:if test="${not empty mensajeError}">
    <div class="mensaje-error" style="text-align:center;">${mensajeError}</div>
</c:if>

<div class="container">

    <form:form modelAttribute="evento"
               action="${pageContext.request.contextPath}/eventos/AltaEvento"
               method="post"
               enctype="multipart/form-data"
               class="form">

        <!-- Nombre_evento -->
        <label>Nombre del evento</label>
        <form:input path="nombreEvento"/>
        <form:errors path="nombreEvento" cssClass="mensaje-error"/>

        <!-- Imagen_principal -->
        <label>Imagen principal</label>
        <input type="file" name="imagenPrincipal" accept="image/*">
        <small>Máximo 5MB</small>

        <!-- Fecha_evento -->
        <label>Fecha del evento</label>
        <form:input path="fechaEvento" type="datetime-local"/>
        <form:errors path="fechaEvento" cssClass="mensaje-error"/>

        <!-- Descripcion_evento -->
        <label>Descripción del evento</label>
        <form:textarea path="descripcionEvento" rows="4"/>
        <form:errors path="descripcionEvento" cssClass="mensaje-error"/>

        <!-- Municipio -->
        <label>Municipio</label>
        <form:select path="municipio.idMunicipio">
            <form:option value="" disabled="true" selected="true">Seleccione un municipio</form:option>
            <form:options items="${municipios}" itemValue="idMunicipio" itemLabel="nombreMunicipio"/>
        </form:select>
        <form:errors path="municipio" cssClass="mensaje-error"/>

        <!-- Grupo musical -->
        <label>Grupo musical</label>
        <form:select path="grupo.idGrupo">
            <form:option value="" disabled="true" selected="true">Seleccione un grupo</form:option>
            <form:options items="${grupos}" itemValue="idGrupo" itemLabel="nombreGrupo"/>
        </form:select>
        <form:errors path="grupo" cssClass="mensaje-error"/>

        <!-- Galería opcional -->
        <label>Galería de imágenes (opcional)</label>
        <input type="file" name="imagenesGaleria" accept="image/*" multiple>
        <small>Puedes seleccionar varias imágenes</small>
        
        
        <!--  AQUI METEMOS LAS BUTACAS. DE MOMENTO CON 3 OPCIONES
              SE PUEDEN AGREGAR MAS SI HACE FALTA  -->
        
        <h3>Configuración de butacas</h3>

<!-- BUTACA 1 -->
<div class="evento-info-box">

    <h4>Tipo de butaca 1</h4>

    <label>Tipo de asiento</label>
    <select name="tipoAsiento_1" class="input-text">
        <option value="">Seleccione un tipo</option>
        <c:forEach var="t" items="${tiposAsiento}">
            <option value="${t.idTipo}">${t.nombreTipo}</option>
        </c:forEach>
    </select>

    <label>Número de filas</label>
    <input type="number" name="filas_1" min="1" class="input-text">

    <label>Asientos por fila</label>
    <input type="number" name="asientos_1" min="1" class="input-text">

    <label>Precio</label>
    <input type="number" step="0.01" name="precio_1" class="input-text">

</div>

<!-- BUTACA 2 -->
<div class="evento-info-box">

    <h4>Tipo de butaca 2</h4>

    <label>Tipo de asiento</label>
    <select name="tipoAsiento_2" class="input-text">
        <option value="">Seleccione un tipo</option>
        <c:forEach var="t" items="${tiposAsiento}">
            <option value="${t.idTipo}">${t.nombreTipo}</option>
        </c:forEach>
    </select>

    <label>Número de filas</label>
    <input type="number" name="filas_2" min="1" class="input-text">

    <label>Asientos por fila</label>
    <input type="number" name="asientos_2" min="1" class="input-text">

    <label>Precio</label>
    <input type="number" step="0.01" name="precio_2" class="input-text">

</div>

<!-- BUTACA 3 -->
<div class="evento-info-box">

    <h4>Tipo de butaca 3</h4>

    <label>Tipo de asiento</label>
    <select name="tipoAsiento_3" class="input-text">
        <option value="">Seleccione un tipo</option>
        <c:forEach var="t" items="${tiposAsiento}">
            <option value="${t.idTipo}">${t.nombreTipo}</option>
        </c:forEach>
    </select>

    <label>Número de filas</label>
    <input type="number" name="filas_3" min="1" class="input-text">

    <label>Asientos por fila</label>
    <input type="number" name="asientos_3" min="1" class="input-text">

    <label>Precio</label>
    <input type="number" step="0.01" name="precio_3" class="input-text">
</div>
	    
        <label style="margin-top: 10px;">
            <input type="checkbox" name="activo" checked/>
            Activo
        </label>
	    
	    <!-- Botón -->
	    <button type="submit" class="btn-submit">Guardar evento</button>

    </form:form>
<!-- Botón Cancelar -->
<button type="button"
        class="btn-submit"
        style="margin-top:10px;"
        onclick="window.location='${pageContext.request.contextPath}/eventos/EventosMusicales'">
    Cancelar
</button>
</div>

</body>
</html>
