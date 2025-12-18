-- TABELA INSTRUCTORS (Actualizată cu password și role)
CREATE TABLE instructors (
                             id BIGSERIAL PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             email VARCHAR(100) UNIQUE NOT NULL,
                             password VARCHAR(255) NOT NULL,  -- Parola criptată (BCrypt)
                             role VARCHAR(50) NOT NULL        -- Ex: 'ROLE_INSTRUCTOR'
);

-- TABELA STUDENTS (Actualizată cu password și role)
CREATE TABLE students (
                          id BIGSERIAL PRIMARY KEY,
                          code VARCHAR(50) UNIQUE NOT NULL,
                          name VARCHAR(100) NOT NULL,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          study_year INT NOT NULL,
                          password VARCHAR(255) NOT NULL,  -- Parola criptată
                          role VARCHAR(50) NOT NULL        -- Ex: 'ROLE_STUDENT'
);

-- TABELA ADMINS (Opțional, pentru utilizatori care sunt doar admini)
CREATE TABLE admins (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(100) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        role VARCHAR(50) NOT NULL
);

-- TABELA PACKS (Neschimbată)
CREATE TABLE packs (
                       id BIGSERIAL PRIMARY KEY,
                       study_year INT NOT NULL,
                       semester INT NOT NULL,
                       name VARCHAR(100) NOT NULL
);

-- TABELA COURSES (Neschimbată)
CREATE TABLE courses (
                         id BIGSERIAL PRIMARY KEY,
                         type VARCHAR(20) NOT NULL, -- 'OPTIONAL' or 'COMPULSORY'
                         code VARCHAR(20) UNIQUE NOT NULL,
                         abbr VARCHAR(10),
                         name VARCHAR(150) NOT NULL,
                         description TEXT,
                         group_count INTEGER DEFAULT 1,
                         instructor_id BIGINT REFERENCES instructors(id),
                         pack_id BIGINT REFERENCES packs(id)
);

-- TABELA PREFERENCES (Neschimbată)
CREATE TABLE preferences (
                             id BIGSERIAL PRIMARY KEY,
                             student_id BIGINT NOT NULL REFERENCES students(id),
                             course_id BIGINT NOT NULL REFERENCES courses(id),
                             rank INTEGER NOT NULL,
                             UNIQUE (student_id, course_id)
);