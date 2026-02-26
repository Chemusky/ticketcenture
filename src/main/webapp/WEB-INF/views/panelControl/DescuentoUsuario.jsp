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
<title>Gestión de Descuentos</title>

<link rel="stylesheet" type="text/css"
      href="<c:url value='/resources/css/eventosMusicalesStyle.css'/>">

</head>
<body>

<!-- Botón volver -->
<a href="${pageContext.request.contextPath}/panel/PanelControl"
   class="btn-back-link"> ← Volver al panel </a>

<h2 class="title">Gestión de Descuentos</h2>

<!-- Mensajes -->
<c:if test="${not empty mensajeError}">
    <div class="error-message">${mensajeError}</div>
</c:if>

<c:if test="${not empty mensajeExito}">
    <div class="success-message">${mensajeExito}</div>
</c:if>

<!-- TOP BAR -->
<div class="filter-panel">
    <div class="filter-row">
        <a href="${pageContext.request.contextPath}/descuento/DescuentoUsuario"
           class="add-btn">
            Limpiar formulario
        </a>
    </div>
</div>
<!-- =============================== -->
<!-- FORMULARIO CREAR / EDITAR -->
<!-- =============================== -->
<div class="form-card">

    <h3>${descuento != null ? "Modificar Descuento" : "Crear Nuevo Descuento"}</h3>

    <form action="${pageContext.request.contextPath}/descuento/${descuento != null ? 'EditarDescuento' : 'AltaDescuento'}"
          method="post">

        <c:if test="${descuento != null}">
            <input type="hidden" name="idDescuento" value="${descuento.idDescuento}">
        </c:if>

        <label>Tipo Usuario:</label>
        <select name="tipoUsuario" class="input-field">
            <option value="NORMAL" ${descuento.tipoUsuario == 'NORMAL' ? 'selected' : ''}>NORMAL</option>
            <option value="PREMIUM" ${descuento.tipoUsuario == 'PREMIUM' ? 'selected' : ''}>PREMIUM</option>
            <option value="VIP" ${descuento.tipoUsuario == 'VIP' ? 'selected' : ''}>VIP</option>
        </select>

        <label>Porcentaje:</label>
        <input type="number" name="descuentoPorcentaje" class="input-field"
               value="${descuento != null ? descuento.descuentoPorcentaje : ''}"
               min="1" max="100" required>

        <label>Válido Desde:</label>
        <input type="date" name="validoDesde" class="input-field"
               value="${descuento != null ? descuento.validoDesde : ''}" required>

        <label>Válido Hasta:</label>
        <input type="date" name="validoHasta" class="input-field"
               value="${descuento != null ? descuento.validoHasta : ''}" required>

        <button type="submit" class="add-btn" style="margin-top:1rem;">
            ${descuento != null ? "Guardar Cambios" : "Crear Descuento"}
        </button>
    </form>

</div>

<!-- =============================== -->
<!-- LISTADO DE DESCUENTOS (ESTILO EVENTOS) -->
<!-- =============================== -->
<div class="event-list">

    <c:if test="${empty listaDescuentos}">
        <p class="no-results">No hay descuentos registrados 😢</p>
    </c:if>

    <c:forEach var="d" items="${listaDescuentos}">

        <div class="event-card">

            <!-- INFORMACIÓN DEL DESCUENTO -->
            <div class="event-info">

                <h3 class="event-title">${d.tipoUsuario}</h3>

                <p class="event-group">
                    Descuento: ${d.descuentoPorcentaje}%
                </p>

                <p class="event-date">
                    Desde: ${d.validoDesde} — Hasta: ${d.validoHasta}
                </p>

            </div>

            <!-- ACCIONES -->
            <div class="event-actions">

                <a href="${pageContext.request.contextPath}/descuento/EditarDescuento?idDescuento=${d.idDescuento}"
                   class="btn-action edit">
                    📝 Modificar
                </a>

                <form action="${pageContext.request.contextPath}/descuento/EliminarDescuento"
                      method="post">
                    <input type="hidden" name="idDescuento" value="${d.idDescuento}">
                    <button type="submit" class="btn-action"
                            style="background-color:#ff4d4d; color:white;"
                            onclick="return confirm('¿Eliminar descuento?')">
                        🗑 Eliminar
                    </button>
                </form>

            </div>

        </div>

    </c:forEach>

</div>



</body>
</html>
