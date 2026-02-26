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
		<title>Página de confirmación de pedido</title>
		
		<link rel="stylesheet" type="text/css"
				href="<c:url value='/resources/css/principalStyle.css'/>">
		
	</head>
	<body>
	<a href="${pageContext.request.contextPath}/principal"
		class="btn-back-link"> ← Volver a principal </a>
	
	<div class="confirmacion-container">
	
		<h2 class="confirmacion-titulo">Compra realizada satisfactoriamente.</h2>
	
		<h3 class="confirmacion-subtitulo">En su perfil podrá acceder al resumen de su compra.</h3>
	
	</div>

	</body>
</html>