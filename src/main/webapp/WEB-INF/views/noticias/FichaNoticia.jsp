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
<title>${noticia.titulo}</title>


      <link rel="stylesheet" href="<c:url value='/resources/css/fichaNoticia.css'/>">
      

</head>

<body>

    <!-- Botón volver -->
    <a href="javascript:history.back()"
       class="btn-back-link">← Volver</a>

    <div class="container">

        <!-- CABECERA -->
        <div class="header-group">
            <div class="band-title">${noticia.titulo}</div>

            <c:if test="${noticia.imagen != null}">
                <img src="${pageContext.request.contextPath}/noticias/imagen/${noticia.idNoticia}"
                     alt="Imagen noticia"
                     class="band-image">
            </c:if>
        </div>

        <!-- CONTENIDO -->
        <div class="content-wrapper">

            <!-- COLUMNA IZQUIERDA -->
            <div class="main-content">

                <h3>Contenido</h3>
                <p class="texto-multilinea">${noticia.cuerpo}</p>

            </div>

            <!-- SIDEBAR -->
            <div class="sidebar">

                <div class="bordered">
                    <strong>Fecha publicación:</strong>
                    <p>${noticia.fechaPublicacion}</p>
                </div>

                <div class="bordered">
                    <strong>Grupo asociado:</strong>
                    <p>
                        <c:choose>
                            <c:when test="${noticia.grupo != null}">
                                ${noticia.grupo.nombreGrupo}
                            </c:when>
                            <c:otherwise>No asociado</c:otherwise>
                        </c:choose>
                    </p>
                </div>

                <div class="bordered">
                    <strong>Evento asociado:</strong>
                    <p>
                        <c:choose>
                            <c:when test="${noticia.evento != null}">
                                ${noticia.evento.nombreEvento}
                            </c:when>
                            <c:otherwise>No asociado</c:otherwise>
                        </c:choose>
                    </p>
                </div>

                <c:if test="${noticia.enlace != null}">
                    <div class="bordered">
                        <strong>Enlace externo:</strong>
                        <p><a href="${noticia.enlace}" target="_blank">${noticia.enlace}</a></p>
                    </div>
                </c:if>

                <!-- SOLO ADMIN -->
                <c:if test="${esAdmin}">
                    <form method="get"
                          action="${pageContext.request.contextPath}/noticias/EditarNoticia">
                        <input type="hidden" name="idNoticia" value="${noticia.idNoticia}">
                        <button type="submit" class="favorite">📝 Editar noticia</button>
                    </form>
                </c:if>

            </div>

        </div>

    </div>

</body>
</html>
