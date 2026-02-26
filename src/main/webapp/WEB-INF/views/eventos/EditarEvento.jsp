<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Pragma" content="no-cache" />
<title>Editar evento</title>

<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/resources/css/DetalleEventoStyle.css">

</head>

<body>

    <!-- Botón volver -->
    <a href="${pageContext.request.contextPath}/eventos/EventosMusicales" class="btn-back-link">← Volver</a>

    <div class="container">
    
    
    <!-- Mensajes de exito y error -->
	<c:if test="${not empty mensajeError}">
		<div class="error-message">${mensajeError}</div>
	</c:if>

	<c:if test="${not empty mensajeExito}">
		<div class="success-message">${mensajeExito}</div>
	</c:if>

        <!-- CABECERA -->
        <div class="header-group">
            <div class="band-title">${evento.nombreEvento}</div>

            <img src="${pageContext.request.contextPath}/eventos/imagen/${evento.idEvento}"
                 alt="Imagen del evento"
                 class="band-image">
        </div>

        <div class="content-wrapper">

            <!-- COLUMNA IZQUIERDA -->
            <div class="main-content">

                <form:form method="post"
                           modelAttribute="evento"
                           action="${pageContext.request.contextPath}/eventos/EditarEvento"
                           enctype="multipart/form-data">

                    <form:hidden path="idEvento"/>
                    <input type="hidden" name="origen" value="${param.origen}">
                    <input type="hidden" name="grupo.idGrupo" value="${evento.grupo.idGrupo}">
                    
                    

                    <h3>Datos del evento</h3>
                    
                    <label>Nombre del evento</label>
					<form:input path="nombreEvento" cssClass="input-text"/>
                    

                    <label>Descripción</label>
                    <form:textarea path="descripcionEvento" cssClass="input-textarea"/>

                    <!-- Fecha_evento -->
        			<label>Fecha del evento</label>
        			<form:input path="fechaEvento" type="datetime-local"/>
        			<form:errors path="fechaEvento" cssClass="mensaje-error"/>

        			
        			<br>
        			<br>

                    <label>Municipio</label> 
                    <form:select path="municipio.idMunicipio" cssClass="input-text"> 
                    <form:options items="${municipios}" itemValue="idMunicipio" itemLabel="nombreMunicipio"/> 
                    </form:select>

                    

                    <label>Imagen principal (opcional)</label>
                    <input type="file" name="imagenPrincipal" class="input-text">


                    <!-- ========================= -->
                    <!--   BUTACAS EXISTENTES      -->
                    <!-- ========================= -->
                    <h3>Configuración de butacas</h3>

                    <c:if test="${not empty evento.butacas}">
                        <c:forEach var="b" items="${evento.butacas}">
                            <div class="evento-info-box">

                                <input type="hidden" name="idButaca" value="${b.idButaca}">

                                <label>Tipo de asiento</label>
                                <input type="text" value="${b.tipoAsiento.nombreTipo}" disabled class="input-text">

                                <label>Número de filas</label>
                                <input type="number" name="filas_${b.idButaca}"
                                       value="${b.numeroFilas}" min="1" class="input-text">

                                <label>Asientos por fila</label>
                                <input type="number" name="asientos_${b.idButaca}"
                                       value="${b.numeroAsientos}" min="1" class="input-text">

                                <label>Precio</label>
                                <input type="number" step="0.01" name="precio_${b.idButaca}"
                                       value="${b.precio}" class="input-text">

                            </div>
                        </c:forEach>
                    </c:if>


                    <!-- ========================= -->
                    <!--   AÑADIR NUEVA BUTACA     -->
                    <!-- ========================= -->
                    <h3>Añadir nuevo tipo de butaca</h3>

                    <div class="evento-info-box">

                        <label>Tipo de asiento</label>
                        <select name="nuevoTipoAsiento" class="input-text">
                            <option value="">Seleccione un tipo</option>
                            <c:forEach var="t" items="${tiposAsiento}">
                                <option value="${t.idTipo}">${t.nombreTipo}</option>
                            </c:forEach>
                        </select>

                        <label>Número de filas</label>
                        <input type="number" name="nuevoFilas" min="1" class="input-text">

                        <label>Asientos por fila</label>
                        <input type="number" name="nuevoAsientos" min="1" class="input-text">

                        <label>Precio</label>
                        <input type="number" step="0.01" name="nuevoPrecio" class="input-text">

                    </div>


                    <!-- ========================= -->
                    <!--   AÑADIR NUEVAS IMÁGENES  -->
                    <!-- ========================= -->
                    <h3>Añadir nuevas imágenes a la galería</h3>

                    <input type="file" name="imagenesGaleria" accept="image/*" multiple class="input-text">
                    <small>Puedes seleccionar varias imágenes</small>
                    
                    <br>
                    <br>
                    <!-- REGENERAR ENTRADAS -->
                    <label style="margin-top:15px;">
                        <input type="checkbox" name="regenerarEntradas">
                        Regenerar entradas (solo si cambias filas/asientos)
                    </label>
                    
                    <!-- ACTIVO -->
                    <c:if test="${sessionScope['usuarioSesion'].admin}">
					    <label style="margin-top: 15px;">
					        <input type="checkbox" name="activo" value="true"
					               <c:if test="${evento.activo}">checked</c:if>>
					        Evento activo
					    </label>
					</c:if>
                    
                    <!-- BOTONES -->
                    <div style="margin-top:20px;">
                        <button type="submit" class="btn-accion editar">Guardar cambios</button>

                        <button type="button"
                                class="btn-accion cancelar"
                                onclick="window.location='${pageContext.request.contextPath}/eventos/EventosMusicales'">
                            Cancelar
                        </button>
                    </div>

                </form:form>

            </div>


            <!-- COLUMNA DERECHA -->
            <div class="sidebar">

                <div class="evento-info-box">
                    <h4>Información no editable</h4>

                    <p><strong>ID del evento:</strong> ${evento.idEvento}</p>
                    <p><strong>Fecha de creación:</strong> ${evento.fechaCreacion}</p>
                </div>

                <div class="galeria">
                    <h3>Galería actual</h3>

                    <c:if test="${not empty galeria}">
                        <c:forEach var="img" items="${galeria}">
                            <img src="${pageContext.request.contextPath}/eventos/galeria/imagen/${img.idImagen}"
                                 class="foto-galeria"
                                 alt="Imagen del evento">
                        </c:forEach>
                    </c:if>
                </div>

            </div>

        </div>
    </div>

</body>
</html>
