# Ticketcenture – Aplicación de Gestión y Procesamiento de Datos

Ticketcenture es una aplicación Java desarrollada en el marco de un proyecto formativo y profesional dentro de **Accenture**, orientada a la gestión integral de proveedores y al procesamiento de datos provenientes de sistemas externos. La solución se construyó siguiendo los estándares y buenas prácticas utilizadas en entornos empresariales reales, aplicando una arquitectura en capas clara y modular que facilita su mantenimiento, escalabilidad y despliegue en distintos entornos corporativos, junto al uso de **metodologías ágiles**, destacando **Scrum**.

El proyecto combina tecnologías ampliamente utilizadas en Accenture para el desarrollo de aplicaciones empresariales —como Spring MVC, Hibernate y MySQL— con patrones de diseño que permiten separar responsabilidades, mejorar la trazabilidad del código y garantizar la extensibilidad del sistema. Esta estructura modular permite incorporar nuevas funcionalidades sin afectar a los componentes existentes, lo que convierte la aplicación en una base sólida para futuros desarrollos, integraciones o ampliaciones dentro de un ecosistema empresarial.

Además, el proyecto pone especial énfasis en la configuración externa mediante archivos independientes, como database.properties, siguiendo prácticas recomendadas para entornos profesionales donde la seguridad, la portabilidad y la separación entre código y credenciales son fundamentales. El resultado es una aplicación robusta, mantenible y alineada con los estándares de calidad propios de **Accenture**.

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

- **JDK 17 o superior**  
- **MySQL 8**  
- **Apache Tomcat 9**  
- **Eclipse o IntelliJ (opcional)**

---

## Calidad del código y análisis estático (SonarQube)

El proyecto incorpora prácticas de calidad utilizadas en entornos profesionales, incluyendo análisis estático mediante **SonarQube**, una herramienta ampliamente empleada en Accenture para garantizar la mantenibilidad, seguridad y fiabilidad del código.

El análisis con **SonarQube** permite:

- Detectar vulnerabilidades y problemas de seguridad.
- Identificar código duplicado o de baja mantenibilidad.
- Aplicar estándares de calidad y buenas prácticas de desarrollo.
- Controlar la deuda técnica del proyecto.
- Garantizar que el código cumple con reglas de estilo y patrones recomendados.


## Configuración del proyecto

El archivo `database.properties` contiene la configuración de conexión a la base de datos y **no se incluye en el repositorio** por motivos de seguridad, estando incluido en el archivo .gitignore para evitar exponer credenciales sensibles y así no subir dicho archivo al repositorio remoto.
Debe crearse manualmente en: **src/main/resources/database.properties** con el siguiente formato:

	jdbc.url=jdbc:mysql://localhost:3306/tu_base_datos?useSSL=true&requireSSL=true&verifyServerCertificate=false&serverTimezone=UTC
	jdbc.username=usuario
	jdbc.password=contraseña

Por otro lado, en la ruta **src/main/resources/** se encuentra el script de la base de datos llamado `ediciones_java_2_loc.sql` **(con la extensión .sql)**. Seguir los siguientes pasos:
1. Crear la base de datos en MySql Workbench o similar, ejecutando el comando `CREATE DATABASE ediciones_java_2_loc;`
2. Copiar el contenido del archivo **ediciones_java_2_loc.sql** y ejecutarlo en MySqlWorkbench.
3. Refrescar el squema para que aparezcan todas las tablas (estarán vacías sin información).

---

## Aplicación en funcionamiento

Para visualizar la aplicación en el navegador, es necesario levantar el **servidor Tomcat**, siendo la URL para visualizar la aplicación: `http://localhost:8080/ticketcenture/principal` 
De primeras, hay que registrar usuarios para poder navegar y utilizar las funcionalidades del proyecto, destacando que, al registrar el primer usuario, para hacerlo administrador, lanzar la siguiente **query**: 

		UPDATE usuarios SET admin = 1 WHERE id_usuario = 1;
		
Una vez establecido el dicho usuario como administrador, ya se podrá realizar cualquier función como administrador dentro de la web.

---

## Objetivo del Proyecto
El objetivo de **Ticketcenture** es desarrollar una aplicación empresarial capaz de gestionar de forma eficiente la información de proveedores y procesar datos provenientes de sistemas externos, reproduciendo un entorno de trabajo real utilizado en proyectos corporativos dentro de Accenture. La aplicación se concibió como un proyecto formativo-profesional orientado a aplicar tecnologías del ecosistema Java, reforzar buenas prácticas de arquitectura en capas y trabajar bajo metodologías ágiles como Scrum, simulando el ciclo completo de desarrollo de un producto software en un contexto empresarial.

El proyecto busca ofrecer una solución que permita:

- Centralizar y gestionar datos de proveedores de manera estructurada.

- Procesar información externa en formato **JSON** para integrarla en el sistema.

- Garantizar la **trazabilidad y consistencia** de los datos mediante **Hibernate y MySQL**.

- Aplicar **patrones de diseño** que faciliten la mantenibilidad, escalabilidad y extensibilidad del sistema.

- Reproducir dinámicas reales de trabajo en equipo, planificación, revisión y mejora continua propias de **Scrum**.

- Implementar estándares de calidad y análisis estático mediante herramientas como **SonarQube**.
