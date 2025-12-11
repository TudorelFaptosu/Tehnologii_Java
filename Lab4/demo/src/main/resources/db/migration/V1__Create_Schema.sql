
CREATE TABLE instructors (
                             id BIGSERIAL PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE students (
                          id BIGSERIAL PRIMARY KEY,
                          code VARCHAR(50) UNIQUE NOT NULL,
                          name VARCHAR(100) NOT NULL,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          study_year INT NOT NULL
);

CREATE TABLE packs (
                       id BIGSERIAL PRIMARY KEY,
                       study_year INT NOT NULL,
                       semester INT NOT NULL,
                       name VARCHAR(100) NOT NULL
);

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
CREATE TABLE preferences (
                             id BIGSERIAL PRIMARY KEY,
                             student_id BIGINT NOT NULL REFERENCES students(id),
                             course_id BIGINT NOT NULL REFERENCES courses(id),
                             rank INTEGER NOT NULL,
    -- Constrângerea unică din Java: un student nu poate vota același curs de două ori
                             UNIQUE (student_id, course_id)
);
