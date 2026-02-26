
INSERT INTO `grupos_ticket`
(`nombre_grupo`, `pais_origen`, `ano_creacion`, `id_genero`,
 `img_principal`, `biografia`, `discografia`, `componentes`, `activo`)
VALUES
-- Evanescence (EE. UU., 1994) – Metal sinfónico – Activo
('Evanescence', 'Estados Unidos', 1994,
 (SELECT id_genero FROM generos WHERE nombre_genero = 'Metal sinfónico'),
 NULL,
 'Banda de Little Rock fundada por Amy Lee y Ben Moody; saltaron a la fama con "Fallen" y han mezclado metal gótico/sinfónico con tintes electrónicos.',
 'Fallen (2003); The Open Door (2006); The Bitter Truth (2021)',
 'Amy Lee (voz, teclados), Troy McLawhorn (guitarra), Tim McCord (guitarra), Will Hunt (batería), Emma Anzai (bajo)',
 b'1'),

-- Within Temptation (Países Bajos, 1996) – Metal sinfónico – Activo
('Within Temptation', 'Países Bajos', 1996,
 (SELECT id_genero FROM generos WHERE nombre_genero = 'Metal sinfónico'),
 NULL,
 'Formados por Sharon den Adel y Robert Westerholt; referentes del metal sinfónico neerlandés con evolución hacia sonidos alternativos.',
 'Mother Earth (2000); The Silent Force (2004); Bleed Out (2023)',
 'Sharon den Adel (voz), Robert Westerholt (guitarra, estudio), Ruud Jolie (guitarra), Jeroen van Veen (bajo), Mike Coolen (batería), Stefan Helleblad (guitarra), Vikram Shankar (teclados)',
 b'1'),

-- La Raíz (España, 2005) – Rock – Activo (regreso en 2024)
('La Raíz', 'España', 2005,
 (SELECT id_genero FROM generos WHERE nombre_genero = 'Rock'),
 NULL,
 'Colectivo de Gandía que fusiona rock con mestizaje y letras combativas; tras su parón, anunciaron regreso en 2024.',
 'Guerra al Silencio (2009); Así en el cielo como en la selva (2013); Entre poetas y presos (2016)',
 'Pablo Sánchez (voz), Julio Maloa (voz), Josep Panxo (voz), Edu Soldevila (guitarra), Juan Zanza (guitarra), Adri Faus (bajo), Pipe Torres (batería), Carles Gertrudis (trompeta), Xavi Banyuls (trombón), DJ Jano',
 b'1'),

-- Marea (España, 1997) – Rock – Activo
('Marea', 'España', 1997,
 (SELECT id_genero FROM generos WHERE nombre_genero = 'Rock'),
 NULL,
 'Quinteto navarro de rock urbano y poético liderado por Kutxi Romero; referentes del rock estatal.',
 'La Patera (1999); Besos de Perro (2002); Los Potros del Tiempo (2022)',
 'Kutxi Romero (voz), David "Kolibrí" Díaz (guitarra), César Ramallo (guitarra), Eduardo "Piñas" Beaumont (bajo), Alén Ayerdi (batería)',
 b'1'),

-- Ginebras (España, 2018) – Pop rock – Activo
('Ginebras', 'España', 2018,
 (SELECT id_genero FROM generos WHERE nombre_genero = 'Pop rock'),
 NULL,
 'Cuarteto madrileño de indie pop/rock de tono desenfadado y letras cotidianas; una de las bandas emergentes del pop alternativo español.',
 'Ya dormiré cuando me muera (2020); ¿Quién es Billie Max? (2023)',
 'Magüi Berto (voz, guitarra), Sandra Sabater (guitarra), Raquel López (bajo), Juls Acosta (batería)',
 b'1'),

-- Hozier (Irlanda, 2008) – Rock indie – Activo
('Hozier', 'Irlanda', 2008,
 (SELECT id_genero FROM generos WHERE nombre_genero = 'Rock indie'),
 NULL,
 'Cantautor irlandés de soul/blues/indie cuya proyección mundial llegó con "Take Me to Church" y un discurso lírico social y literario.',
 'Hozier (2014); Wasteland, Baby! (2019); Unreal Unearth (2023)',
 'Andrew Hozier-Byrne (voz, guitarra)',
 b'1'),

-- Adele (Reino Unido, 2006) – Soul – Activo
('Adele', 'Reino Unido', 2006,
 (SELECT id_genero FROM generos WHERE nombre_genero = 'Soul'),
 NULL,
 'Voz icónica del pop-soul británico del siglo XXI; récords de ventas y premios con álbumes conceptuales por edades.',
 '19 (2008); 21 (2011); 30 (2021)',
 'Adele Laurie Blue Adkins (voz)',
 b'1'),

-- Florence + The Machine (Reino Unido, 2007) – Rock indie – Activo
('Florence + The Machine', 'Reino Unido', 2007,
 (SELECT id_genero FROM generos WHERE nombre_genero = 'Rock indie'),
 NULL,
 'Proyecto londinense liderado por Florence Welch: indie/art pop de gran dramatismo vocal y producción orquestal.',
 'Lungs (2009); Ceremonials (2011); Dance Fever (2022)',
 'Florence Welch (voz), Isabella Summers (teclados), Rob Ackroyd (guitarra), Tom Monger (arpa) y colaboradores',
 b'1'),

-- Imagine Dragons (Estados Unidos, 2008) – Pop rock – Activo
('Imagine Dragons', 'Estados Unidos', 2008,
 (SELECT id_genero FROM generos WHERE nombre_genero = 'Pop rock'),
 NULL,
 'Banda de Las Vegas de pop/alt rock con himnos de estadio y elementos electrónicos; gran impacto global desde "Night Visions".',
 'Night Visions (2012); Evolve (2017); Mercury – Act 1 (2021)',
 'Dan Reynolds (voz), Wayne Sermon (guitarra), Ben McKee (bajo)',
 b'1'),

-- Daft Punk (Francia, 1993) – Electrónica – Inactivo (disueltos 2021)
('Daft Punk', 'Francia', 1993,
 (SELECT id_genero FROM generos WHERE nombre_genero = 'Electrónica'),
 NULL,
 'Dúo francés clave del french house y la electrónica moderna; estética robótica y producción influyente hasta su separación en 2021.',
 'Homework (1997); Discovery (2001); Random Access Memories (2013)',
 'Thomas Bangalter, Guy-Manuel de Homem-Christo',
 b'0'),

-- Extremoduro (España, 1987) – Rock – Inactivo (disueltos)
('Extremoduro', 'España', 1987,
 (SELECT id_genero FROM generos WHERE nombre_genero = 'Rock'),
 NULL,
 'Histórico grupo de rock transgresivo de Plasencia liderado por Robe Iniesta; letras poético–crudas y gran impacto en el rock español.',
 'Agila (1996); La ley innata (2008); Para todos los públicos (2013)',
 'Robe Iniesta (voz, guitarra), Iñaki "Uoho" Antón (guitarra), Miguel Colino (bajo), José Ignacio Cantera (batería)',
 b'0'),

-- Muse (Reino Unido, 1994) – Rock – Activo
('Muse', 'Reino Unido', 1994,
 (SELECT id_genero FROM generos WHERE nombre_genero = 'Rock'),
 NULL,
 'Trío de Teignmouth con mezcla de rock alternativo/progresivo y electrónica; directos de gran formato y carrera multipremiada.',
 'Origin of Symmetry (2001); Absolution (2003); Will of the People (2022)',
 'Matt Bellamy (voz, guitarra, teclados), Chris Wolstenholme (bajo), Dominic Howard (batería)',
 b'1'),

-- The Killers (Estados Unidos, 2001) – Rock indie – Activo
('The Killers', 'Estados Unidos', 2001,
 (SELECT id_genero FROM generos WHERE nombre_genero = 'Rock indie'),
 NULL,
 'Banda de Las Vegas que unió new wave y rock alternativo; siete #1 en UK y éxitos como "Mr. Brightside".',
 'Hot Fuss (2004); Sam''s Town (2006); Pressure Machine (2021)',
 'Brandon Flowers (voz, teclados), Dave Keuning (guitarra), Mark Stoermer (bajo), Ronnie Vannucci Jr. (batería)',
 b'1'),

-- Nirvana (Estados Unidos, 1987) – Grunge – Inactivo (disueltos en 1994)
('Nirvana', 'Estados Unidos', 1987,
 (SELECT id_genero FROM generos WHERE nombre_genero = 'Grunge'),
 NULL,
 'Trío de Aberdeen que popularizó el grunge mundialmente; su final llegó tras la muerte de Kurt Cobain en 1994.',
 'Bleach (1989); Nevermind (1991); In Utero (1993)',
 'Kurt Cobain (voz, guitarra), Krist Novoselic (bajo), Dave Grohl (batería)',
 b'0'),

-- Queens of the Stone Age (Estados Unidos, 1996) – Rock – Activo
('Queens of the Stone Age', 'Estados Unidos', 1996,
 (SELECT id_genero FROM generos WHERE nombre_genero = 'Rock'),
 NULL,
 'Proyecto de Josh Homme tras Kyuss; stoner/desert rock con rotación de colaboradores y sólida formación desde 2013.',
 'Rated R (2000); Songs for the Deaf (2002); In Times New Roman... (2023)',
 'Josh Homme (voz, guitarra), Troy Van Leeuwen (guitarra), Michael Shuman (bajo), Dean Fertita (teclados/guitarra), Jon Theodore (batería)',
 b'1'),

-- Portishead (Reino Unido, 1991) – Electrónica (trip hop) – Activo (intermitente)
('Portishead', 'Reino Unido', 1991,
 (SELECT id_genero FROM generos WHERE nombre_genero = 'Electrónica'),
 NULL,
 'Trío de Bristol esencial del trip hop; atmósferas cinematográficas y la voz de Beth Gibbons en discos de culto.',
 'Dummy (1994); Portishead (1997); Third (2008)',
 'Beth Gibbons (voz), Geoff Barrow (producción/instrumentos), Adrian Utley (guitarra)',
 b'1');
