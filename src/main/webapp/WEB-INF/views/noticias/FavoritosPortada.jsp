<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Seccion de favoritos para portada -->
<section class="grupos-favoritos">
    <h2>Favoritos</h2>

	<!-- SI NO HAY FAVORITOS MARCADOS TODAVIA ------------------------->
	<c:if test="${empty favoritos}"> 
	<p>No tienes grupos marcados como favoritos.</p> 
	</c:if>
	
	<!-- SI YA HAY FAVORITOS------------------------------------------>
	<c:forEach var="grupo" items="${favoritos}"> 
		<div class="grupo"> 
			<div class="grupo-imagen"> 
				<img src="${pageContext.request.contextPath}/grupos/imagen/${grupo.idGrupo}" 
					alt="${grupo.nombreGrupo}" /> 
			</div>
			
			<div class="grupo-contenido"> 
				<h3>${grupo.nombreGrupo}</h3> 
				<p>${grupo.biografia}</p> 
				<a href="${pageContext.request.contextPath}/grupos/detalleGrupo?id=${grupo.idGrupo}" 
				class="leer-mas"> Leer más </a> 
			</div>
			
		</div> 
	</c:forEach>

<!-- LO QUE HABIA ANTES---------------------------------------------------
    <div class="grupo">
        <div class="grupo-imagen">
            <img src="https://placehold.co/150x100?text=Grupo+1&font=roboto" alt="Imagen del grupo favorito" />
        </div>
        <div class="grupo-contenido">
            <h3>Nombre del Grupo Favorito</h3>
            <p>Noticias recientes relacionadas con este grupo aparecerán aquí.</p>
            <a href="#" class="leer-mas">Leer más</a>
        </div>
    </div>

    <div class="grupo">
        <div class="grupo-imagen">
            <img src="https://placehold.co/150x100?text=Grupo+2&font=roboto" alt="Imagen del grupo favorito" />
        </div>
        <div class="grupo-contenido">
            <h3>Nombre del Grupo Favorito</h3>
            <p>Noticias recientes relacionadas con este grupo aparecerán aquí.</p>
            <a href="#" class="leer-mas">Leer más</a>
        </div>
    </div>
    
    HASTA AQUI---------------------->
</section>