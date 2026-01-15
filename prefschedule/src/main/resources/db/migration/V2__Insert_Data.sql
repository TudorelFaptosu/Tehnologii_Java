-- 1. Inserăm Instructori (Adăugat password și role obligatorii)
INSERT INTO instructors (name, email, password, role)
VALUES ('Prof. Popescu', 'popescu@univ.ro', '$2a$10$r.5cgXjQz.dJj.X/5.5.5O5.5.5.5.5.5.5.5.5.5.5.5.5.5', 'ROLE_INSTRUCTOR');

INSERT INTO instructors (name, email, password, role)
VALUES ('Prof. Ionescu', 'ionescu@univ.ro', '$2a$10$r.5cgXjQz.dJj.X/5.5.5O5.5.5.5.5.5.5.5.5.5.5.5.5.5', 'ROLE_INSTRUCTOR');

-- 2. Inserăm Pachete
INSERT INTO packs (study_year, semester, name) VALUES (3, 1, 'Optional Pack A');

-- 3. Inserăm Cursuri
-- Folosim sub-cereri (SELECT) pentru a găsi ID-ul corect al instructorului,
-- indiferent dacă secvența a sărit peste numere (evită eroarea de Foreign Key).

INSERT INTO courses (type, code, abbr, name, description, instructor_id, pack_id)
VALUES (
           'OPTIONAL',
           'CS301',
           'Java',
           'Tehnologii Java',
           'Invatam Spring Boot',
           (SELECT id FROM instructors WHERE email = 'popescu@univ.ro'), -- Găsește ID-ul dinamic
           (SELECT id FROM packs WHERE name = 'Optional Pack A')
       );

INSERT INTO courses (type, code, abbr, name, description, instructor_id, pack_id)
VALUES (
           'OPTIONAL',
           'CS302',
           'AI',
           'Inteligenta Artificiala',
           'Retele Neurale',
           (SELECT id FROM instructors WHERE email = 'ionescu@univ.ro'), -- Găsește ID-ul dinamic
           (SELECT id FROM packs WHERE name = 'Optional Pack A')
       );

-- 4. Inserăm un Student (Adăugat password și role obligatorii)
INSERT INTO students (code, name, email, study_year, password, role)
VALUES ('S100', 'Student Exemplu', 'student@univ.ro', 3, '$2a$10$r.5cgXjQz.dJj.X/5.5.5O5.5.5.5.5.5.5.5.5.5.5.5.5.5', 'ROLE_STUDENT');