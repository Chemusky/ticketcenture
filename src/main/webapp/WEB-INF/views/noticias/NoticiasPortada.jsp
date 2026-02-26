<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- SECCION DE NOTICIAS PARA PORTADA -->
<section class="noticias-recientes">
    <h2>Noticias Recientes</h2>
    
    <!-- SI NO HAY NOTICIAS -->
    <c:if test="${empty ultimasNoticias}">
        <p>No hay noticias disponibles.</p>
    </c:if>

    <!-- LISTADO DINAMICO DE NOTICIAS -->
    <c:forEach var="noticia" items="${ultimasNoticias}">
        <div class="noticia">
            
            <div class="noticia-imagen">
                <img src="${pageContext.request.contextPath}/noticias/imagen/${noticia.idNoticia}"
                     alt="Imagen de la noticia" />
            </div>
            
            <div class="noticia-contenido">
                <h3>${noticia.titulo}</h3>
                <p>${fn:substring(noticia.cuerpo, 0, 120)}...</p>

                <a href="${pageContext.request.contextPath}/noticias/FichaNoticia?idNoticia=${noticia.idNoticia}"
                   class="leer-mas">Leer más →</a>
            </div>
            
        </div>
    </c:forEach>
</section>
