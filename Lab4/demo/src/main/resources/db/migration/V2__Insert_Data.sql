-- Inserăm Instructori
INSERT INTO instructors (name, email) VALUES ('Prof. Popescu', 'popescu@univ.ro');
INSERT INTO instructors (name, email) VALUES ('Prof. Ionescu', 'ionescu@univ.ro');

-- Inserăm Pachete (Optionale An 3)
INSERT INTO packs (study_year, semester, name) VALUES (3, 1, 'Optional Pack A');

-- Inserăm Cursuri
INSERT INTO courses (type, code, abbr, name, description, instructor_id, pack_id)
VALUES ('OPTIONAL', 'CS301', 'Java', 'Tehnologii Java', 'Invatam Spring Boot', 1, 1);

INSERT INTO courses (type, code, abbr, name, description, instructor_id, pack_id)
VALUES ('OPTIONAL', 'CS302', 'AI', 'Inteligenta Artificiala', 'Retele Neurale', 2, 1);

-- Inserăm un Student
INSERT INTO students (code, name, email, study_year)
VALUES ('S100', 'Student Exemplu', 'student@univ.ro', 3);