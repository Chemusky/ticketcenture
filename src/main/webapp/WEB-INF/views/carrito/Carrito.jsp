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
<meta http-equiv="Pragma" content="no-cache" />
<title>Mi carrito 🛒</title>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/CarritoStyle.css">

</head>
<body>

	<!-- Botón para volver a principal-->
	<a href="${pageContext.request.contextPath}/principal"
		class="btn-back-link">← Volver a principal</a>

	<!-- Mensajes de exito y error -->
	<c:if test="${not empty mensajeError}">
		<div class="error-message">${mensajeError}</div>
	</c:if>

	<c:if test="${not empty mensajeExito}">
		<div class="success-message">${mensajeExito}</div>
	</c:if>

	<h2 class="main-title">Mi carrito 🛒</h2>

	<!-- Si el carrito está vacío -->
	<c:if test="${empty carrito.items}">
		<p class="empty-cart">Tu carrito está vacío.</p>
	</c:if>

	<!-- Si el carrito tiene items -->
	<c:if test="${not empty carrito.items}">
		<table class="table-carrito">
			<thead>
				<tr>
					<th>Evento</th>
					<th>Tipo</th>
					<th>Fila</th>
					<th>Asiento</th>
					<th>Precio</th>
					<th>Descuento</th>
					<th>Precio final</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${carrito.items}">
					<tr>
						<!-- Nombre del evento -->
						<td>${item.entrada.evento.nombreEvento}</td>

						<!-- Datos de la entrada -->
						<td>${item.entrada.tipoAsiento.nombreTipo}</td>
						<td>${item.entrada.fila}</td>
						<td>${item.entrada.asiento}</td>

						<!-- Precios -->
						<td>${item.precioEntrada}€</td>
						<td><c:choose>
								<c:when test="${item.descuentoPromocion != null}"> ${item.descuentoPromocion} € </c:when>
								<c:otherwise> 0 € </c:otherwise>
							</c:choose></td>
						<td>${item.precioTotalEntrada}€</td>

						<!-- Botón eliminar -->
						<td>
							<form
								action="${pageContext.request.contextPath}/carrito/EliminarItem"
								method="post">
								<input type="hidden" name="idItem" value="${item.idItem}">
								<button class="btn-delete">Eliminar 🗑️</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>

	
	<!-- Total -->
	<!--  VAMOS A COMENTAR ESTO PARA AÑADIR LAS OTRAS LINEAS QUE NECESITAMOS PARA VER EL SUBTOTAL
	<h3 class="total">Total: ${carrito.total} €</h3>
	-->
	<!-- Totales del carrito -->
<div class="totales-carrito">

    <!-- Subtotal -->
    <h3 class="subtotal">
        Subtotal: <span>${carrito.total} €</span>
    </h3>

    <!-- Si hay descuento de usuario -->
    <c:if test="${not empty descuentoUsuario}">
        <h3 class="descuento-usuario">
            Descuento por tipo de usuario: 
            <span style="color: green; font-weight: bold;">
                ${descuentoUsuario.descuentoPorcentaje}%
            </span>
        </h3>

        <!-- Total con descuento -->
        <h3 class="total-final">
            Total final: 
            <span style="text-decoration: line-through; color: #888;">
                ${carrito.total} €
            </span>
            <span style="color: green; font-weight: bold; margin-left: 10px;">
                ${totalConDescuento} €
            </span>
        </h3>
    </c:if>

    <!-- Si NO hay descuento -->
    <c:if test="${empty descuentoUsuario}">
        <h3 class="total">
            Total: <span>${carrito.total} €</span>
        </h3>
    </c:if>

</div>
<!-- HASTA AQUI LO NUEVO -->
	

	<!-- Botón procesar compra -->
	<form action="${pageContext.request.contextPath}/carrito/confirmar"
		method="post">
		
		
		<!-- LO DE LAS DIRECCIONES SE PUEDE SACAR A UN METODO AUXILIAR PARA DEJAR MAS LIMPIA LA VISTA -->
		<c:set var="direccionEnvio" value="" /> 
		<c:forEach var="d" items="${carrito.usuario.direcciones}"> 
			<c:if test="${d.tipoDireccion == 'ENVIO'}"> 
			<c:set var="direccionEnvio" value="${d.texto}" /> 
			</c:if> 
		</c:forEach> 
		<h3>Dirección de envío</h3> 
		<p>${direccionEnvio}</p> 
		<input type="hidden" name="direccionEnvio" value="${direccionEnvio}">
		
		<c:set var="direccionFacturacion" value="" />
		<c:forEach var="d" items="${carrito.usuario.direcciones}">
    		<c:if test="${d.tipoDireccion == 'FACTURACION'}">
        	<c:set var="direccionFacturacion" value="${d.texto}" />
    		</c:if>
		</c:forEach>

		<h3>Dirección de facturación</h3>
		<p>${direccionFacturacion}</p>
		<input type="hidden" name="direccionFacturacion" value="${direccionFacturacion}">

		<button class="btn-procesar-compra">Procesar compra ✏️</button>
	</form>



</body>
</html>