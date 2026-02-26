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
    <title>Crear Noticia Musical</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/eventosAltaStyle.css?v=4">
</head>

<body>

<a href="javascript:history.back()"
   class="link-volver">← Volver</a>

<h2 class="title">Crear nueva noticia musical</h2>

<c:if test="${not empty mensajeExito}">
    <div class="mensaje-exito" style="text-align:center;">${mensajeExito}</div>
</c:if>

<c:if test="${not empty mensajeError}">
    <div class="mensaje-error" style="text-align:center;">${mensajeError}</div>
</c:if>

<div class="container">

    <form:form modelAttribute="noticia"
               action="${pageContext.request.contextPath}/noticias/EditarNoticia"
               method="post"
               enctype="multipart/form-data"
               class="form">
		<form:hidden path="idNoticia"/>

		<h3>Datos de la noticia</h3>

        <!-- TITULO DE LA NOTICIA -->
        <label>Titulo</label>
        	<form:input path="titulo" />
			<form:errors path="titulo" cssClass="mensaje-error"/>
           
        <!-- IMAGEN -->        
        <c:if test="${noticia.imagen != null}">
			<img
				src="${pageContext.request.contextPath}/noticias/imagen/${noticia.idNoticia}"
				alt="Imagen actual"
				style="max-width: 200px; display: block; margin-bottom: 10px;">
		</c:if>
		<input type="file" name="imagen" accept="image/*">
		<small>Máximo 5MB</small>
    

        <!-- CUERPO DE LA NOTICIA -->
        <label>Texto de la noticia</label>
        <form:textarea path="cuerpo" rows="3"/> 
        <form:errors path="cuerpo" cssClass="mensaje-error"/>
       
        <!-- GRUPO AL QUE HACE REFERENCIA -->
        <label>Grupo musical</label>
        <form:select path="grupo.idGrupo">
            <form:option value="" disabled="true" selected="true">Seleccione un grupo</form:option>
            <form:options items="${grupos}" itemValue="idGrupo" itemLabel="nombreGrupo"/>
        </form:select>
        <form:errors path="grupo" cssClass="mensaje-error"/>


		<!-- EVENTO AL QUE HACE REFERENCIA -->
        <label>Evento musical</label>
        <form:select path="evento.idEvento">
            <form:option value="" disabled="true" selected="true">Seleccione un evento</form:option>
            <form:options items="${eventos}" itemValue="idEvento" itemLabel="nombreEvento"/>
        </form:select>
        <form:errors path="grupo" cssClass="mensaje-error"/>        
             
	    
	    <!-- CHECK DE PUBLICADA O NO -->
        <label style="margin-top: 10px;">
			<form:checkbox path="publicado"/> Publicada
        </label>
	    
	    <!-- Botón -->
	    <button type="submit" class="btn-submit">Guardar noticia</button>

    </form:form>
<!-- Botón Cancelar -->
<button type="button"
        class="btn-submit"
        style="margin-top:10px;"
        onclick="window.location='${pageContext.request.contextPath}/noticias/NoticiasMusicales'">
    Cancelar
</button>
</div>

</body>
</html>
