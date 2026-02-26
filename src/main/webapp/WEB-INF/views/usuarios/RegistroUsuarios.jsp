<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Pragma" content="no-cache" />
    <title>Registro de Usuario</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/registroUsuariosStyle.css">
</head>
<body>

<div class="father-form-container">

    <a href="${pageContext.request.contextPath}/principal"
		class="btn-back-link"> ← Volver a principal</a>

    <form:form modelAttribute="usuario" method="post" action="${pageContext.request.contextPath}/usuarios/registro" class="form">
        
        <h2 class="form-title">Registro de Usuario</h2>
        
        <c:if test="${not empty mensajeError}">
            <div class="error-message">${mensajeError}</div>
        </c:if>

        <c:if test="${not empty mensajeExito}">
            <div class="success-message">${mensajeExito}</div>
        </c:if>

        <h3>Datos Personales</h3>

        <div class="form-container">
            <div>

                <div class="field-block">
                    <label>Nombre:</label>
                    <form:input path="nombre"/>
                    <form:errors path="nombre" cssClass="error"/>
                </div>

                <div class="field-block">
                    <label>Apellidos:</label>
                    <form:input path="apellidos"/>
                    <form:errors path="apellidos" cssClass="error"/>
                </div>

                <div class="field-block">
                    <label>Nombre de usuario:</label>
                    <form:input path="username"/>
                    <form:errors path="username" cssClass="error"/>
                </div>

                <div class="field-block">
                    <label>DNI:</label>
                    <form:input path="dni"/>
                    <form:errors path="dni" cssClass="error"/>
                </div>

            </div>

            <div>

                <div class="field-block">
                    <label>Email:</label>
                    <form:input path="email"/>
                    <form:errors path="email" cssClass="error"/>
                </div>

                <div class="field-block">
                    <label>Teléfono:</label>
                    <form:input path="telefono" type="tel"/>
                    <form:errors path="telefono" cssClass="error"/>
                </div>

                <div class="field-block">
                    <label>Contraseña:</label>
                    <form:password path="password"/>
                    <form:errors path="password" cssClass="error"/>
                </div>

            </div>
        </div>

        <hr style="border: 0; border-top: 1px solid #eee; margin: 20px 0;">

        <div class="form-container">

            <!-- DIRECCIÓN DE ENVÍO -->
            <div class="adress-container">
                <h3>Dirección de envío</h3>

                <div class="field-block">
                    <label>Calle:</label>
                    <form:input path="direcciones[0].calle"/>
                    <form:errors path="direcciones[0].calle" cssClass="error"/>
                </div>

                <div class="field-block">
                    <label>2ª línea:</label>
                    <form:input path="direcciones[0].segundaLineaDireccion"/>
                </div>

                <div class="field-block">
                    <label>Número:</label>
                    <form:input path="direcciones[0].numero"/>
                    <form:errors path="direcciones[0].numero" cssClass="error"/>
                </div>

                <div class="field-block">
                    <label>Piso:</label>
                    <form:input path="direcciones[0].piso"/>
                </div>

                <div class="field-block">
                    <label>Escalera:</label>
                    <form:input path="direcciones[0].escalera"/>
                </div>

                <div class="field-block">
                    <label>Localidad:</label>
                    <form:input path="direcciones[0].localidad"/>
                    <form:errors path="direcciones[0].localidad" cssClass="error"/>
                </div>

                <div class="field-block">
                    <label>Provincia:</label>
                    <form:input path="direcciones[0].provincia"/>
                    <form:errors path="direcciones[0].provincia" cssClass="error"/>
                </div>

                <div class="field-block">
                    <label>Código Postal:</label>
                    <form:input path="direcciones[0].codigoPostal" type="number"/>
                    <form:errors path="direcciones[0].codigoPostal" cssClass="error"/>
                </div>

                <form:hidden path="direcciones[0].tipoDireccion" value="ENVIO"/>
            </div>

            <!-- DIRECCIÓN DE FACTURACIÓN -->
            <div class="adress-container">
                <h3>Dirección de facturación</h3>

                <div class="checkbox-container" style="margin-bottom: 15px;">
                    <input type="checkbox" name="igualEnvio" id="igualEnvio"/> 
                    <label for="igualEnvio" class="label-spetial-checkbox">Igual que envío</label>
                </div>

                <div class="field-block">
                    <label>Calle:</label>
                    <form:input path="direcciones[1].calle" cssClass="facturacion-field"/>
                    <form:errors path="direcciones[1].calle" cssClass="error"/>
                </div>

                <div class="field-block">
                    <label>2ª línea:</label>
                    <form:input path="direcciones[1].segundaLineaDireccion" cssClass="facturacion-field"/>
                </div>

                <div class="field-block">
                    <label>Número:</label>
                    <form:input path="direcciones[1].numero" cssClass="facturacion-field"/>
                    <form:errors path="direcciones[1].numero" cssClass="error"/>
                </div>

                <div class="field-block">
                    <label>Piso:</label>
                    <form:input path="direcciones[1].piso" cssClass="facturacion-field"/>
                </div>

                <div class="field-block">
                    <label>Escalera:</label>
                    <form:input path="direcciones[1].escalera" cssClass="facturacion-field"/>
                </div>

                <div class="field-block">
                    <label>Localidad:</label>
                    <form:input path="direcciones[1].localidad" cssClass="facturacion-field"/>
                    <form:errors path="direcciones[1].localidad" cssClass="error"/>
                </div>

                <div class="field-block">
                    <label>Provincia:</label>
                    <form:input path="direcciones[1].provincia" cssClass="facturacion-field"/>
                    <form:errors path="direcciones[1].provincia" cssClass="error"/>
                </div>

                <div class="field-block">
                    <label>Código Postal:</label>
                    <form:input path="direcciones[1].codigoPostal" type="number" cssClass="facturacion-field"/>
                    <form:errors path="direcciones[1].codigoPostal" cssClass="error"/>
                </div>

                <form:hidden path="direcciones[1].tipoDireccion" value="FACTURACION"/>
            </div>
        </div>

        <!-- Aceptar términos -->
        <label>
            <input type="checkbox" name="aceptarTerminos" required /> Aceptar términos y condiciones
        </label>

        <!-- Botón de envío -->
        <div class="form-container full-width" style="flex-direction: column; align-items: center;">
            <button type="submit">Registrarse</button>
        </div>

    </form:form>
</div>

<script>
document.getElementById("igualEnvio").addEventListener("change", function() {
    const fields = ["calle","segundaLineaDireccion","numero","piso","escalera","localidad","provincia","codigoPostal"];
    if (this.checked) {
        fields.forEach(f => {
            const envio = document.querySelector("[name='direcciones[0]." + f + "']");
            const facturacion = document.querySelector("[name='direcciones[1]." + f + "']");
            if (envio && facturacion) {
                facturacion.value = envio.value;
                facturacion.setAttribute("readonly", "readonly");
            }
        });
    } else {
        fields.forEach(f => {
            const facturacion = document.querySelector("[name='direcciones[1]." + f + "']");
            if (facturacion) {
                facturacion.removeAttribute("readonly");
                facturacion.value = "";
            }
        });
    }
});
window.addEventListener("DOMContentLoaded", () => {
    document.getElementById("igualEnvio").dispatchEvent(new Event("change"));
});
</script>

</body>
</html>
