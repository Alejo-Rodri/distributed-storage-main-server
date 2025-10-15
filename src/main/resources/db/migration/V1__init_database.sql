-- Drop existing tables (solo para desarrollo)
DROP TABLE IF EXISTS shared_file CASCADE;
DROP TABLE IF EXISTS file CASCADE;
DROP TABLE IF EXISTS directory CASCADE;
DROP TABLE IF EXISTS user_account CASCADE;

-- Tabla de usuarios
CREATE TABLE user_account (
    id          BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(100) NOT NULL UNIQUE,
    email       VARCHAR(255),
    full_name   VARCHAR(255),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) /*! ENGINE = InnoDB */;

-- Tabla de directorios (estructura jerárquica)
CREATE TABLE directory (
    id           BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    parent_id    BIGINT NULL,
    owner_id     BIGINT NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_directory_parent FOREIGN KEY (parent_id) REFERENCES directory (id) ON DELETE CASCADE,
    CONSTRAINT fk_directory_owner FOREIGN KEY (owner_id) REFERENCES user_account (id)
) /*! ENGINE = InnoDB */;

-- Tabla de archivos
CREATE TABLE file (
    id           BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    path         VARCHAR(1024) NOT NULL,
    size_bytes   BIGINT DEFAULT 0,
    mime_type    VARCHAR(255),
    owner_id     BIGINT NOT NULL,
    directory_id BIGINT,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_file_owner FOREIGN KEY (owner_id) REFERENCES user_account (id),
    CONSTRAINT fk_file_directory FOREIGN KEY (directory_id) REFERENCES directory (id) ON DELETE CASCADE
) /*! ENGINE = InnoDB */;

-- Tabla de archivos/directorios compartidos
CREATE TABLE shared_file (
    id             BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    file_id        BIGINT NULL,
    directory_id   BIGINT NULL,
    shared_with_id BIGINT NOT NULL,
    permission     ENUM('READ', 'WRITE', 'OWNER') DEFAULT 'READ',
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_shared_file FOREIGN KEY (file_id) REFERENCES file (id) ON DELETE CASCADE,
    CONSTRAINT fk_shared_directory FOREIGN KEY (directory_id) REFERENCES directory (id) ON DELETE CASCADE,
    CONSTRAINT fk_shared_user FOREIGN KEY (shared_with_id) REFERENCES user_account (id)
) /*! ENGINE = InnoDB */;

-- Vista o tabla de reporte de consumo de espacio (puede materializarse más adelante)
-- Aquí solo un ejemplo de vista lógica
CREATE VIEW v_space_usage AS
SELECT
    u.id AS user_id,
    u.username,
    COALESCE(SUM(f.size_bytes), 0) AS total_bytes_used
FROM user_account u
LEFT JOIN file f ON f.owner_id = u.id
GROUP BY u.id, u.username;

-- Datos de ejemplo
INSERT INTO user_account (username, email, full_name) VALUES
('alejandro', 'alejandro@example.com', 'Alejandro Rodríguez'),
('sofia', 'sofia@example.com', 'Sofía Torres');

INSERT INTO directory (name, owner_id) VALUES
('root', (SELECT id FROM user_account WHERE username = 'alejandro')),
('root', (SELECT id FROM user_account WHERE username = 'sofia'));

INSERT INTO file (name, path, size_bytes, mime_type, owner_id, directory_id)
VALUES
('documento.txt', '/root/documento.txt', 2048, 'text/plain',
 (SELECT id FROM user_account WHERE username = 'alejandro'),
 (SELECT id FROM directory WHERE name = 'root' AND owner_id = (SELECT id FROM user_account WHERE username = 'alejandro'))
);

INSERT INTO shared_file (file_id, shared_with_id, permission)
VALUES
((SELECT id FROM file WHERE name = 'documento.txt'), (SELECT id FROM user_account WHERE username = 'sofia'), 'READ');
