-- Script de creacion de tablas para BBDD: edciones_java_2_loc (grupo2)

-- Creacion de la base de datos con la query CREATE DATABASE ediciones_java_2_loc;

-- Creacion de tabla 'usuarios'

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `telefono` int NOT NULL,
  `dni` varchar(9) NOT NULL,
  `admin` bit(1) NOT NULL,
  `username` varchar(30) NOT NULL,
  `email` varchar(200) NOT NULL,
  `password` varchar(255) NOT NULL,
  `tipo_usuario` varchar(255) NOT NULL DEFAULT 'NORMAL',
  `intentos_fallidos` int NOT NULL DEFAULT '0',
  `activo` bit(1) NOT NULL,
  `fecha_alta` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_baja` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `bloqueado_hasta` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Creacion de tabla 'actividad_usuarios'

DROP TABLE IF EXISTS `actividad_usuarios`;
CREATE TABLE `actividad_usuarios` (
  `id_actividad` int unsigned NOT NULL AUTO_INCREMENT,
  `id_usuario` int DEFAULT NULL,
  `fecha_actividad` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip_actividad` varchar(45) NOT NULL,
  `accion` varchar(255) NOT NULL,
  `detalles` text,
  PRIMARY KEY (`id_actividad`),
  KEY `FK_actividad` (`id_usuario`),
  CONSTRAINT `FK_actividad` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Creacion de tabla 'direcciones'
DROP TABLE IF EXISTS `direcciones`;
CREATE TABLE `direcciones` (
  `id_direccion` int unsigned NOT NULL AUTO_INCREMENT,
  `calle` varchar(100) NOT NULL,
  `numero` varchar(10) NOT NULL,
  `piso` varchar(10) DEFAULT NULL,
  `escalera` varchar(10) DEFAULT NULL,
  `2linea_direccion` varchar(100) DEFAULT NULL,
  `codigo_postal` int NOT NULL,
  `localidad` varchar(100) NOT NULL,
  `provincia` varchar(100) NOT NULL,
  `tipo_direccion` varchar(255) NOT NULL DEFAULT 'ENVIO',
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id_direccion`),
  KEY `FK_direccion` (`id_usuario`),
  CONSTRAINT `FK_direccion` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Modificaciones nuevas (renombrar esta linea con la fecha o el sprint)
-- AGREGADO PARA GRUPOS -- SPRINT 3 -- 9/1/2026

DROP TABLE IF EXISTS `generos`;
CREATE TABLE `generos`(
	`id_genero` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    `nombre_genero` varchar(50) NOT NULL,
    PRIMARY KEY (`id_genero`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `grupos_ticket`;
CREATE TABLE `grupos_ticket` (
	`id_grupo` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`nombre_grupo` varchar(70) NOT NULL,
	`pais_origen` varchar(100) NOT NULL,
	`ano_creacion` int(4) NOT NULL,
	`id_genero` int(10) UNSIGNED NULL,
    `img_principal` LONGBLOB,
    `biografia` text NULL,
    `discografia` text NULL,
    `componentes` text NULL,
    `activo` bit NOT NULL,
	PRIMARY KEY (`id_grupo`),
    KEY `FK_grupo_genero` (`id_genero`),
	CONSTRAINT `FK_grupo_genero` FOREIGN KEY (`id_genero`) REFERENCES `generos` (`id_genero`)
    ON DELETE SET NULL ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `galeria_grupos`;
CREATE TABLE `galeria_grupos` (
	`id_imagen` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    `imagen` LONGBLOB NOT NULL,
    `id_grupo` int(10) UNSIGNED NOT NULL,
    PRIMARY KEY (`id_imagen`),
	KEY `FK_imagen` (`id_grupo`),
	CONSTRAINT `FK_imagen` FOREIGN KEY (`id_grupo`) REFERENCES `grupos_ticket` (`id_grupo`)
    ON DELETE CASCADE ON UPDATE CASCADE
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `favoritos`;
CREATE TABLE `favoritos` (
	`id_favorito`int(10) NOT NULL AUTO_INCREMENT,
    `id_grupo` int(10) UNSIGNED NOT NULL,
    `id_usuario` int(10) NOT NULL,
    `fecha` TIMESTAMP DEFAULT current_timestamp not null,
    PRIMARY KEY (`id_favorito`),
    KEY `FK_favoritos_gr` (`id_grupo`),
    CONSTRAINT `FK_favoritos_gr` FOREIGN KEY (`id_grupo`) REFERENCES `grupos_ticket`(`id_grupo`)
    ON DELETE CASCADE ON UPDATE CASCADE,
    KEY `FK_favoritos_us` (`id_usuario`),
    CONSTRAINT `FK_favoritos_us` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios`(`id_usuario`)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `unique_usuario_grupo` UNIQUE (`id_usuario`, `id_grupo`)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- AGREGADO PARA LA FUNCIONALIDAD DE EVENTOS
-- sprint 4  21/1/2026


DROP TABLE IF EXISTS `municipios`;
CREATE TABLE `municipios`(
	`id_municipio` int(10) NOT NULL AUTO_INCREMENT,
    `nombre_municipio` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id_municipio`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO municipios (nombre_municipio) VALUES
('Almería'), ('Roquetas de Mar'), ('Cádiz'), ('Jerez de la Frontera'), ('Córdoba'),
('Lucena'), ('Granada'), ('Motril'), ('Huelva'), ('Lepe'),
('Jaén'), ('Linares'), ('Málaga'), ('Marbella'), ('Sevilla'),
('Dos Hermanas'), ('Huesca'), ('Monzón'), ('Teruel'), ('Alcañiz'),
('Zaragoza'), ('Calatayud'), ('Oviedo'), ('Gijón'), ('Santander'),
('Torrelavega'), ('Albacete'), ('Hellín'), ('Ciudad Real'), ('Puertollano'),
('Cuenca'), ('Tarancón'), ('Guadalajara'), ('Azuqueca de Henares'), ('Toledo'),
('Talavera de la Reina'), ('Ávila'), ('Arévalo'), ('Burgos'), ('Miranda de Ebro'),
('León'), ('Ponferrada'), ('Palencia'), ('Aguilar de Campoo'), ('Salamanca'),
('Béjar'), ('Segovia'), ('Cuéllar'), ('Soria'), ('Almazán'),
('Valladolid'), ('Medina del Campo'), ('Zamora'), ('Benavente'), ('Barcelona'),
('L’Hospitalet de Llobregat'), ('Girona'), ('Figueres'), ('Lleida'), ('Balaguer'),
('Tarragona'), ('Reus'), ('Alicante'), ('Elche'), ('Castellón de la Plana'),
('Villarreal'), ('Valencia'), ('Gandía'), ('Badajoz'), ('Mérida'),
('Cáceres'), ('Plasencia'), ('A Coruña'), ('Santiago de Compostela'), ('Lugo'),
('Monforte de Lemos'), ('Ourense'), ('Verín'), ('Pontevedra'), ('Vigo'),
('Palma'), ('Ibiza'), ('Las Palmas de Gran Canaria'), ('Telde'), ('Santa Cruz de Tenerife'),
('La Laguna'), ('Logroño'), ('Calahorra'), ('Madrid'), ('Móstoles'),
('Murcia'), ('Cartagena'), ('Pamplona'), ('Tudela'), ('Vitoria-Gasteiz'),
('Llodio'), ('San Sebastián'), ('Irún'), ('Bilbao'), ('Barakaldo'),
('Ceuta'), ('Melilla'), ('Algeciras'), ('San Fernando'), ('El Puerto de Santa María'),
('Chiclana de la Frontera'), ('Sanlúcar de Barrameda'), ('Puerto Real'), ('Utrera'), ('Mairena del Aljarafe'),
('Alcalá de Guadaíra'), ('Écija'), ('Benalmádena'), ('Vélez-Málaga'), ('Torremolinos'),
('Fuengirola'), ('Estepona'), ('Rincón de la Victoria'), ('Mijas'), ('Armilla'),
('Granadilla de Abona'), ('Arona'), ('Adeje'), ('Los Realejos'), ('Arrecife'),
('San Bartolomé de Tirajana'), ('Santa Lucía de Tirajana'), ('Arroyomolinos'), ('Fuenlabrada'), ('Getafe'),
('Leganés'), ('Alcorcón'), ('Parla'), ('Torrejón de Ardoz'), ('Alcobendas'),
('San Sebastián de los Reyes'), ('Pozuelo de Alarcón'), ('Majadahonda'), ('Las Rozas de Madrid'), ('Rivas-Vaciamadrid'),
('Pinto'), ('Valdemoro'), ('Coslada'), ('San Fernando de Henares'), ('Boadilla del Monte'),
('Castelldefels'), ('Cornellà de Llobregat'), ('Sant Boi de Llobregat'), ('Viladecans'), ('Badalona'),
('Santa Coloma de Gramenet'), ('Sabadell'), ('Terrassa'), ('Rubí'), ('Cerdanyola del Vallès'),
('Granollers'), ('Mataró'), ('Manresa'), ('Vilanova i la Geltrú'), ('Gavà'),
('El Prat de Llobregat'), ('Mollet del Vallès'), ('Sant Cugat del Vallès'), ('Ferrol'), ('Narón'),
('Vilagarcía de Arousa'), ('Ponteareas'), ('Redondela'), ('Cangas'), ('Lalín'),
('O Barco de Valdeorras'), ('Elda'), ('Petrer'), ('Torrevieja'), ('Benidorm'),
('Denia'), ('Orihuela'), ('San Vicente del Raspeig'), ('Alcoy'), ('Vinaròs'),
('Onda'), ('Almassora'), ('Sagunto'), ('Torrent'), ('Paterna'),
('Mislata'), ('Burjassot'), ('Xirivella'), ('Alzira'), ('Ontinyent'),
('Lorca'), ('Molina de Segura'), ('Alcantarilla'), ('Yecla'), ('Águilas'),
('Avilés'), ('Langreo'), ('Mieres'), ('Siero'), ('Getxo'),
('Portugalete'), ('Santurtzi'), ('Basauri'), ('Erandio'), ('Leioa'),
('Durango'), ('Errenteria'), ('Hernani'), ('Eibar'), ('Zarautz'),
('Arnedo'), ('Burlada'), ('Estella-Lizarra'), ('Aranda de Duero');
    
DROP TABLE IF EXISTS `tipo_asientos`;    
CREATE TABLE `tipo_asientos` (
	`id_tipo` int(10) NOT NULL AUTO_INCREMENT,
    `nombre_tipo` VARCHAR (100) NOT NULL,
    PRIMARY KEY (`id_tipo`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO tipo_asientos (nombre_tipo)
VALUES ('Palco'),('Grada'),('Platea'),('Pista'),('Zona VIP');

DROP TABLE IF EXISTS `eventos_musicales`;
CREATE TABLE `eventos_musicales`(
	`id_evento` int(10) NOT NULL AUTO_INCREMENT,
    `nombre_evento` VARCHAR(200) NOT NULL,
    `activo` bit NOT NULL,
    `img_principal` LONGBLOB,
    `id_municipio` int(10) NULL,
    `id_grupo` int(10) UNSIGNED NOT NULL,
    `fecha_evento` DATETIME NOT NULL,
    `descripcion_evento` TEXT NOT NULL,
    `fecha_creacion` TIMESTAMP DEFAULT current_timestamp NOT NULL,
    PRIMARY KEY (`id_evento`),
    KEY `FK_evento_municipio` (`id_municipio`),
	CONSTRAINT `FK_evento_municipio` FOREIGN KEY (`id_municipio`) REFERENCES `municipios` (`id_municipio`)
    ON DELETE SET NULL ON UPDATE CASCADE,
    KEY `FK_evento_grupo` (`id_grupo`),
    CONSTRAINT `FK_evento_grupo` FOREIGN KEY (`id_grupo`) REFERENCES `grupos_ticket` (`id_grupo`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `entradas`;
CREATE TABLE `entradas`(
	`id_entrada` INT(10) NOT NULL AUTO_INCREMENT,
    `id_evento` INT(10) NOT NULL,
    `id_usuario` INT(10) NULL,
    `id_tipo` INT(10) NOT NULL,
    `fecha_compra` TIMESTAMP NULL,
    `fila` INT(10) NOT NULL,
    `asiento` INT(10) NOT NULL,
    `estado` VARCHAR (20) DEFAULT 'disponible',
    `reservada_carrito` TIMESTAMP NULL,
    PRIMARY KEY (`id_entrada`),
    KEY `FK_entrada_evento` (`id_evento`),
	CONSTRAINT `FK_entrada_evento` FOREIGN KEY (`id_evento`) REFERENCES `eventos_musicales` (`id_evento`)
    ON DELETE CASCADE ON UPDATE CASCADE,
    KEY `FK_entrada_tipo` (`id_tipo`),
    CONSTRAINT `FK_entrada_tipo` FOREIGN KEY (`id_tipo`) REFERENCES `tipo_asientos` (`id_tipo`)
    ON DELETE CASCADE ON UPDATE CASCADE,
    KEY `FK_entrada_usuario` (`id_usuario`), 
    CONSTRAINT `FK_entrada_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) 
    ON DELETE SET NULL ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `galeria_eventos`;
CREATE TABLE `galeria_eventos` (
	`id_imagen` int(10) NOT NULL AUTO_INCREMENT,
    `imagen` LONGBLOB NOT NULL,
    `id_evento` int(10)  NOT NULL,
    PRIMARY KEY (`id_imagen`),
	KEY `FK_imagen_evento` (`id_evento`),
	CONSTRAINT `FK_imagen_evento` FOREIGN KEY (`id_evento`) REFERENCES `eventos_musicales` (`id_evento`)
    ON DELETE CASCADE ON UPDATE CASCADE
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `butacas`;
CREATE TABLE `butacas`(
	`id_butaca` INT(10) NOT NULL AUTO_INCREMENT,
    `id_evento` INT(10) NOT NULL,
    `id_tipo` INT(10) NOT NULL,
    `numero_filas` INT(10) NOT NULL,
    `numero_asientos` INT(10) NOT NULL,
    `precio` DECIMAL (10,2) NOT NULL,
    PRIMARY KEY (`id_butaca`),
    KEY `FK_evento_butaca` (`id_evento`),
    CONSTRAINT `FK_evento_butaca` FOREIGN KEY (`id_evento`) REFERENCES `eventos_musicales` (`id_evento`)
    ON UPDATE CASCADE ON DELETE CASCADE,
    KEY `FK_tipo_butaca` (`id_tipo`),
    CONSTRAINT `FK_tipo_butaca` FOREIGN KEY (`id_tipo`) REFERENCES `tipo_asientos` (`id_tipo`)
    ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- INSERT PARA TABLA GENEROS

INSERT INTO `generos` (`nombre_genero`)
VALUES
('Rock'),
('Pop'),
('Electrónica'),
('Grunge'),
('Rock indie'),
('Pop rock'),
('Soul'),
('Metal sinfónico'),
('Punk');

-- AGREGADO PARA FUNCIONALIDAD DE CARRITO
-- Sprint 5 30/01/2026

-- TABLA CARRITO

DROP TABLE IF EXISTS `carrito`;
CREATE TABLE `carrito` (
    `id_carrito` INT(10) NOT NULL AUTO_INCREMENT,
    `id_usuario` INT(10) NOT NULL,
    `fecha_creacion` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `fecha_compra` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `estado` VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',  -- ACTIVO / CONFIRMADO
    `direccion_envio` VARCHAR(500) NULL,
    `direccion_facturacion` VARCHAR(500) NULL,
    `descuento_aplicado` DECIMAL(10,2) NULL,
    `total` DECIMAL(10,2) NULL,
    PRIMARY KEY (`id_carrito`),
    CONSTRAINT `FK_carrito_usuario` FOREIGN KEY (`id_usuario`)
        REFERENCES `usuarios`(`id_usuario`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- TABLA CARRITO_ITEMS

DROP TABLE IF EXISTS `carrito_items`;
CREATE TABLE `carrito_items` (
    `id_item` INT(10) NOT NULL AUTO_INCREMENT,
    `id_carrito` INT(10) NOT NULL,
    `id_entrada` INT(10) NOT NULL,
    `precio_entrada` DECIMAL(10,2) NOT NULL,
    `promocion_evento` DECIMAL(10,2) NULL,
    `precio_total_entrada` DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (`id_item`),
    CONSTRAINT `FK_item_carrito` FOREIGN KEY (`id_carrito`)
        REFERENCES `carrito`(`id_carrito`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `FK_item_entrada` FOREIGN KEY (`id_entrada`)
        REFERENCES `entradas`(`id_entrada`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- TABLA DESCUENTOS POR TIPO DE USUARIO

DROP TABLE IF EXISTS `descuentos_usuario`;
CREATE TABLE `descuentos_usuario` (
    `id_descuento` INT(10) NOT NULL AUTO_INCREMENT,
    `tipo_usuario` VARCHAR(255) NOT NULL,   -- NORMAL, PREMIUM, VIP
    `descuento_porcentaje` INT(3) NOT NULL,
    `fecha_creacion` DATE NOT NULL DEFAULT (CURRENT_DATE),
    `valido_desde` DATE NOT NULL,
    `valido_hasta` DATE NOT NULL,
    PRIMARY KEY (`id_descuento`),
    INDEX idx_tipo_usuario (`tipo_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- TABLA PROMOCIONES POR EVENTO

DROP TABLE IF EXISTS `promociones_evento`;
CREATE TABLE `promociones_evento` (
    `id_promocion` INT(10) NOT NULL AUTO_INCREMENT,
    `id_evento` INT(10) NOT NULL,
    `codigo` VARCHAR(100) NOT NULL,
    `descuento_euros` DECIMAL(10,2) NOT NULL,
    `fecha_creacion` DATE NOT NULL DEFAULT (CURRENT_DATE),
    `valido_desde` DATE NOT NULL,
    `valido_hasta` DATE NOT NULL,
    PRIMARY KEY (`id_promocion`),
    UNIQUE KEY `unique_codigo` (`codigo`),
    CONSTRAINT `FK_codigo_evento` FOREIGN KEY (`id_evento`)
        REFERENCES `eventos_musicales`(`id_evento`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--  CAMBIO UNIQUE KEY codigo de promoción

ALTER TABLE promociones_evento
DROP INDEX unique_codigo;

ALTER TABLE promociones_evento
ADD CONSTRAINT unique_evento_codigo UNIQUE (id_evento, codigo);

-- Agregado de noticias 11/02/2026
DROP TABLE IF EXISTS `noticias`;
CREATE TABLE `noticias` (
    `id_noticia` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    `titulo` VARCHAR(255) NOT NULL,
    `cuerpo` LONGTEXT NOT NULL,
    `enlace` VARCHAR(500) NULL,
    `imagen` LONGBLOB NULL,
    `publicado` BIT(1) NOT NULL DEFAULT 0,
    `fecha_publicacion` DATETIME NULL,
    `fecha_creacion` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `id_grupo` INT(10) UNSIGNED NULL,
    `id_evento` INT(10) NULL,
    PRIMARY KEY (`id_noticia`),
    KEY `FK_noticia_grupo` (`id_grupo`),
    CONSTRAINT `FK_noticia_grupo` FOREIGN KEY (`id_grupo`)
        REFERENCES `grupos_ticket` (`id_grupo`)
        ON DELETE SET NULL ON UPDATE CASCADE,
    KEY `FK_noticia_evento` (`id_evento`),
    CONSTRAINT `FK_noticia_evento` FOREIGN KEY (`id_evento`)
        REFERENCES `eventos_musicales` (`id_evento`)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
