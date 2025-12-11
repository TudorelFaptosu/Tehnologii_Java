
CREATE TABLE instructors (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE students (
                          id SERIAL PRIMARY KEY,
                          code VARCHAR(50) UNIQUE NOT NULL,
                          name VARCHAR(100) NOT NULL,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          study_year INT NOT NULL
);

CREATE TABLE packs (
                       id SERIAL PRIMARY KEY,
                       study_year INT NOT NULL,
                       semester INT NOT NULL,
                       name VARCHAR(100) NOT NULL
);

CREATE TABLE courses (
                         id SERIAL PRIMARY KEY,
                         type VARCHAR(20) NOT NULL, -- 'OPTIONAL' or 'COMPULSORY'
                         code VARCHAR(20) UNIQUE NOT NULL,
                         abbr VARCHAR(10),
                         name VARCHAR(150) NOT NULL,
                         description TEXT,
                         group_count INT DEFAULT 1,
                         instructor_id INT REFERENCES instructors(id),
                         pack_id INT REFERENCES packs(id)
);
