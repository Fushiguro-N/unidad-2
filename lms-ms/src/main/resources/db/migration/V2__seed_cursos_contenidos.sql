INSERT INTO cursos (nombre, descripcion) VALUES ('Desarrollo de Aplicaciones Distribuidas', 'Curso de la carrera de Ingenieria de Sistemas');
INSERT INTO cursos (nombre, descripcion) VALUES ('Marketing Digital', 'Curso de la carrera de Administracion');
INSERT INTO cursos (nombre, descripcion) VALUES ('Diplomado en Desarrollo Web', 'Programa de especializacion');
INSERT INTO cursos (nombre, descripcion) VALUES ('Ingles Basico', 'Curso de idiomas');

INSERT INTO contenidos (nombre, descripcion, precio, activo, stock, id_curso)
VALUES ('Certificado de especializacion', 'Certificado opcional al finalizar el curso', 89.90, true, 25, 1);

INSERT INTO contenidos (nombre, descripcion, precio, activo, stock, id_curso)
VALUES ('Material de clase impreso', 'Separata impresa del curso', 39.90, true, 40, 1);

INSERT INTO contenidos (nombre, descripcion, precio, activo, stock, id_curso)
VALUES ('Certificado de participacion', 'Certificado al completar el taller', 129.90, true, 15, 2);

INSERT INTO contenidos (nombre, descripcion, precio, activo, stock, id_curso)
VALUES ('Modulo de certificacion', 'Certificado digital verificable', 29.90, true, 50, 3);

INSERT INTO contenidos (nombre, descripcion, precio, activo, stock, id_curso)
VALUES ('Examen de nivelacion', 'Evaluacion de nivel de entrada', 199.90, true, 20, 4);

INSERT INTO contenidos (nombre, descripcion, precio, activo, stock, id_curso)
VALUES ('Kit de materiales', 'Cuaderno y utiles del curso', 79.90, true, 30, 4);

INSERT INTO contenidos (nombre, descripcion, precio, activo, stock, id_curso)
VALUES ('Certificado internacional', 'Certificado con validez internacional', 249.90, true, 10, 1);
