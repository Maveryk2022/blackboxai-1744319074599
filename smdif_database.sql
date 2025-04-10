-- SMDIF Database Schema Implementation
-- Version 1.0

CREATE DATABASE IF NOT EXISTS smdif;
USE smdif;

-- Common citizen information table (base table)
CREATE TABLE ciudadanos (
    curp VARCHAR(18) PRIMARY KEY,
    fecha_registro DATE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100),
    edad INT CHECK (edad > 0),
    sexo ENUM('M','F','O') NOT NULL,
    direccion TEXT NOT NULL,
    telefono VARCHAR(15),
    email VARCHAR(100),
    INDEX idx_ciudadano (curp, nombre, apellido_paterno)
);

-- Procuraduría Table
CREATE TABLE procuraduria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    curp VARCHAR(18) NOT NULL,
    folio VARCHAR(20) UNIQUE,
    recepcion_reporte ENUM('presencial','oficio','telefónica','email','otro') NOT NULL,
    -- Additional fields would be added here
    FOREIGN KEY (curp) REFERENCES ciudadanos(curp) ON DELETE CASCADE
);

-- Programas Sociales Table
CREATE TABLE programas_sociales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    curp VARCHAR(18) NOT NULL,
    puntaje_total INT DEFAULT 0,
    nivel_socioeconomico VARCHAR(2),
    -- Additional fields would be added here
    FOREIGN KEY (curp) REFERENCES ciudadanos(curp) ON DELETE CASCADE
);

-- Adulto Mayor Table
CREATE TABLE adulto_mayor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    curp VARCHAR(18) NOT NULL,
    grupo VARCHAR(100),
    fecha_nacimiento DATE,
    -- Additional fields would be added here
    FOREIGN KEY (curp) REFERENCES ciudadanos(curp) ON DELETE CASCADE
);

-- Sample Data Insertion
INSERT INTO ciudadanos (curp, fecha_registro, nombre, apellido_paterno, apellido_materno, edad, sexo, direccion, telefono, email)
VALUES 
('CURPEXAMPLE001', '2023-01-15', 'Juan', 'Perez', 'Gomez', 35, 'M', 'Calle 123, Colonia Centro', '5512345678', 'juan@example.com'),
('CURPEXAMPLE002', '2023-01-16', 'Maria', 'Lopez', 'Garcia', 28, 'F', 'Avenida 456, Colonia Norte', '5598765432', 'maria@example.com');

-- Views for Reporting
CREATE VIEW reporte_ciudadano_completo AS
SELECT c.*, p.folio, ps.puntaje_total, am.grupo
FROM ciudadanos c
LEFT JOIN procuraduria p ON c.curp = p.curp
LEFT JOIN programas_sociales ps ON c.curp = ps.curp
LEFT JOIN adulto_mayor am ON c.curp = am.curp;

-- Stored Procedures
DELIMITER //
CREATE PROCEDURE obtener_historial_ciudadano(IN p_curp VARCHAR(18))
BEGIN
    SELECT * FROM reporte_ciudadano_completo WHERE curp = p_curp;
END //
DELIMITER ;
