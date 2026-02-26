<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Histórico de Compras</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/historicoComprasStyle.css">
</head>

<body>

<a href="${pageContext.request.contextPath}/usuarios/perfilUsuario"
   class="btn-back-link"> ← Volver al perfil</a>

<div class="container">

    <h2>Histórico de compras</h2>

    <c:if test="${empty compras}">
        <p class="no-compras">Todavía no has realizado ninguna compra.</p>
    </c:if>

    <ul class="lista-compras">

        <c:forEach var="c" items="${compras}">

            <!-- Formateo de fecha -->
            <c:set var="fechaCompraFmt" value="${fn:substring(fn:replace(c.fechaCompra, 'T', ' '), 0, 16)}" />

            <li class="compra-item">

                <!-- PRIMERA FILA RESUMEN -->
                <div class="compra-resumen-fila">
                    <span><strong>Carrito</strong></span>
                    <span>${fechaCompraFmt}</span>
                    <span>Total: <strong>${c.total} €</strong></span>

                    <c:if test="${c.descuentoAplicado != null && c.descuentoAplicado gt 0}">
                        <span>Descuento: -${c.descuentoAplicado} €</span>
                    </c:if>
                </div>

                <!-- PANEL VERDE CLARITO -->
                <div class="compra-detalle">

                    <!-- TABLA DE ENTRADAS -->
                    <table class="tabla-entradas">
                        <thead>
                            <tr>
                                <th>Evento</th>
                                <th>Tipo</th>
                                <th>Fila</th>
                                <th>Asiento</th>
                                <th>Promo</th>
                                <th>Precio</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="item" items="${c.items}">
                                <tr>
                                    <td>${item.entrada.evento.nombreEvento}</td>
                                    <td>${item.entrada.tipoAsiento.nombreTipo}</td>
                                    <td>${item.entrada.fila}</td>
                                    <td>${item.entrada.asiento}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${item.descuentoPromocion != null && item.descuentoPromocion gt 0}">
                                                -${item.descuentoPromocion} €
                                            </c:when>
                                            <c:otherwise>—</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${item.precioTotalEntrada} €</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- DIRECCIONES (DEL CARRITO, NO DEL USUARIO) -->
                    <div class="direccion-linea">
                        <strong>Envío:</strong> ${c.direccionEnvio}
                    </div>

                    <div class="direccion-linea">
                        <strong>Facturación:</strong> ${c.direccionFacturacion}
                    </div>

                </div>

            </li>

        </c:forEach>

    </ul>

</div>

</body>
</html>
