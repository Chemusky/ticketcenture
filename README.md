# Ticketcenture – Aplicación de Gestión y Procesamiento de Datos

Ticketcenture es una aplicación Java basada en Spring MVC y Hibernate diseñada para gestionar información de proveedores y procesar datos provenientes de sistemas externos. El proyecto sigue una arquitectura en capas clara y modular, facilitando su mantenimiento, escalabilidad y despliegue en distintos entornos.

---

## Características principales

- Gestión de proveedores mediante servicios y DAOs basados en Hibernate.
- Procesamiento de datos JSON provenientes de sistemas externos.
- Arquitectura en capas: Controller → Service → DAO → Entity.
- Integración con MySQL mediante Hibernate y HikariCP.
- Configuración externa mediante `database.properties` (excluido del repositorio).
- Despliegue en Apache Tomcat 9.
- Código organizado y preparado para futuras ampliaciones (importaciones, informes, etc.).

---

## Tecnologías utilizadas

- **Java 17**
- **Spring MVC 5**
- **Hibernate 5.6**
- **MySQL 8**
- **Tomcat 9**
- **JSP / JSTL**
- **HikariCP**

---

## Requisitos previos

- JDK 17 o superior  
- MySQL 8  
- Apache Tomcat 9  
- Eclipse o IntelliJ (opcional)

---

## Configuración del proyecto

El archivo `database.properties` contiene la configuración de conexión a la base de datos y **no se incluye en el repositorio** por motivos de seguridad.  
Debe crearse manualmente en: src/main/resources/database.properties con el siguiente formato:
Este archivo está incluido en .gitignore para evitar exponer credenciales sensibles.

De primeras, al registrar el primer usuario, para hacerlo admin, lanzar la siguiente query: UPDATE usuarios SET admin = 1 WHERE id_usuario = 1;


```properties
jdbc.url=jdbc:mysql://localhost:3306/tu_base
jdbc.username=usuario
jdbc.password=contraseña```


