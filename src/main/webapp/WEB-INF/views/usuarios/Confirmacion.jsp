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
<title>TicketCenture G2</title>

<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/principalStyle.css'/>">

</head>

<body>
	<header>
		<div class="container">
			
			<!-- Título de la página -->
			<h1>TicketCenture</h1>

		</div>
	</header>


	<h3 class="confirmacion">
		<a href="${pageContext.request.contextPath}/principal"> El
			usuario ha sido correctamente registrado </a>
	</h3>

</body>
</html>