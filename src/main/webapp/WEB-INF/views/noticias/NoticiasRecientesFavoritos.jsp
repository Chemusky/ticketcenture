<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- SECCION DE NOTICIAS DE GRUPOS FAVORITOS -->
<section class="noticias-recientes">
    <h2>Noticias de tus grupos favoritos (últimas 24h)</h2>
    
    <!-- SI NO HAY NOTICIAS -->
    <c:if test="${empty noticiasRecientesFavoritos}">
        <p>No hay noticias recientes de tus grupos favoritos.</p>
    </c:if>

    <!-- LISTADO DINAMICO DE NOTICIAS -->
    <c:forEach var="noticia" items="${noticiasRecientesFavoritos}">
        <div class="noticia">

            <!-- IMAGEN DEL GRUPO -->
            <div class="noticia-imagen">
                <img src="${pageContext.request.contextPath}/grupos/imagen/${noticia.grupo.idGrupo}"
                     alt="Imagen del grupo ${noticia.grupo.nombreGrupo}" />
            </div>

            <div class="noticia-contenido">

                <!-- NOMBRE DEL GRUPO -->
                <h3>${noticia.grupo.nombreGrupo}</h3>

                <!-- PRIMEROS CARACTERES DEL CUERPO -->
                <p>${fn:substring(noticia.cuerpo, 0, 120)}...</p>

                <!-- ENLACE A LA FICHA DE LA NOTICIA -->
                <a href="${pageContext.request.contextPath}/noticias/FichaNoticia?idNoticia=${noticia.idNoticia}"
                   class="leer-mas">Ver más →</a>
            </div>

        </div>
    </c:forEach>
</section>
