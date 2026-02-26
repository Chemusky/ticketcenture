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
<title>Gestión de Códigos Promocionales</title>

<link rel="stylesheet" type="text/css"
      href="<c:url value='/resources/css/eventosMusicalesStyle.css'/>">

</head>
<body>

<!-- Botón volver -->
<a href="${pageContext.request.contextPath}/panel/PanelControl"
   class="btn-back-link"> ← Volver al panel </a>

<h2 class="title">Gestión de Códigos Promocionales</h2>

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
        <a href="${pageContext.request.contextPath}/promocion/PromocionEvento"
           class="add-btn">
            Limpiar formulario
        </a>
    </div>
</div>
<!-- =============================== -->
<!-- FORMULARIO CREAR / EDITAR -->
<!-- =============================== -->
<div class="form-card">

    <h3>${promocion != null ? "Modificar Promoción" : "Crear Nueva Promoción"}</h3>

    <form action="${pageContext.request.contextPath}/promocion/${promocion != null ? 'EditarPromocion' : 'AltaPromocion'}"
          method="post">

        <c:if test="${promocion != null}">
            <input type="hidden" name="idPromocion" value="${promocion.idPromocion}">
        </c:if>

        <label>Código Promocional:</label>
        <input type="text" name="codigo" class="input-field"
               value="${promocion != null ? promocion.codigo : ''}" required>

        <label>Descuento (€):</label>
        <input type="number" step="0.01" name="descuentoEuros" class="input-field"
               value="${promocion != null ? promocion.descuentoEuros : ''}" required>

        <label>Evento asociado:</label>
        <select name="idEvento" class="input-field" required>
            <option value="">Seleccionar evento</option>
            <c:forEach var="e" items="${listaEventos}">
                <option value="${e.idEvento}"
                    <c:if test="${promocion != null && promocion.evento.idEvento == e.idEvento}">
                        selected
                    </c:if>>
                    ${e.nombreEvento}
                </option>
            </c:forEach>
        </select>

        <label>Válido Desde:</label>
        <input type="date" name="validoDesde" class="input-field"
               value="${promocion != null ? promocion.validoDesde : ''}" required>

        <label>Válido Hasta:</label>
        <input type="date" name="validoHasta" class="input-field"
               value="${promocion != null ? promocion.validoHasta : ''}" required>

        <button type="submit" class="add-btn" style="margin-top:1rem;">
            ${promocion != null ? "Guardar Cambios" : "Crear Promoción"}
        </button>
    </form>

</div>

<!-- =============================== -->
<!-- LISTADO DE PROMOCIONES (ESTILO EVENTOS) -->
<!-- =============================== -->
<div class="event-list">

    <c:if test="${empty listaPromociones}">
        <p class="no-results">No hay promociones registradas 😢</p>
    </c:if>

    <c:forEach var="p" items="${listaPromociones}">

        <div class="event-card">

            <!-- INFORMACIÓN DE LA PROMOCIÓN -->
            <div class="event-info">

                <h3 class="event-title">${p.codigo}</h3>

                <p class="event-group">
                    Evento: ${p.evento.nombreEvento}
                </p>

                <p class="event-location">
                    Descuento: ${p.descuentoEuros} €
                </p>

                <p class="event-date">
                    Desde: ${p.validoDesde} — Hasta: ${p.validoHasta}
                </p>

            </div>

            <!-- ACCIONES -->
            <div class="event-actions">

                <a href="${pageContext.request.contextPath}/promocion/EditarPromocion?idPromocion=${p.idPromocion}"
                   class="btn-action edit">
                    📝 Modificar
                </a>

                <form action="${pageContext.request.contextPath}/promocion/EliminarPromocion"
                      method="post">
                    <input type="hidden" name="idPromocion" value="${p.idPromocion}">
                    <button type="submit" class="btn-action"
                            style="background-color:#ff4d4d; color:white;"
                            onclick="return confirm('¿Eliminar promoción?')">
                        🗑 Eliminar
                    </button>
                </form>

            </div>

        </div>

    </c:forEach>

</div>



</body>
</html>
