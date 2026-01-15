CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(120) NOT NULL,
                       role VARCHAR(20) NOT NULL -- ROLE_STUDENT, ROLE_INSTRUCTOR, ROLE_ADMIN
);

-- Inserăm un Admin default (Parola este 'admin123' criptată cu BCrypt)
INSERT INTO users (username, email, password, role)
VALUES ('admin', 'admin@univ.ro', '$2a$10$r.5cgXjQz.dJj.X/5.5.5O5.5.5.5.5.5.5.5.5.5.5.5.5.5', 'ROLE_ADMIN');

