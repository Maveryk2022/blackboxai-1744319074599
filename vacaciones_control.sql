-- Vacation Control System for SMDIF
-- Database Schema Implementation

CREATE DATABASE IF NOT EXISTS control_vacaciones;
USE control_vacaciones;

-- Main Employees Table
CREATE TABLE empleados (
    numero_empleado INT PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    fecha_ingreso DATE NOT NULL,
    departamento VARCHAR(100) NOT NULL,
    dias_adicionales INT DEFAULT 0,
    INDEX idx_empleado (numero_empleado, nombre_completo)
);

-- Vacation Periods Definition
CREATE TABLE periodos_vacacionales (
    id_periodo INT AUTO_INCREMENT PRIMARY KEY,
    nombre_periodo VARCHAR(50) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    dias_base INT DEFAULT 10
);

-- Vacation Requests
CREATE TABLE solicitudes_vacaciones (
    id_solicitud INT AUTO_INCREMENT PRIMARY KEY,
    numero_empleado INT NOT NULL,
    fecha_solicitud DATE NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    dias_solicitados INT NOT NULL,
    estado ENUM('pendiente','aprobada','rechazada') DEFAULT 'pendiente',
    FOREIGN KEY (numero_empleado) REFERENCES empleados(numero_empleado)
);

-- Vacation Balance View
CREATE VIEW saldo_vacaciones AS
SELECT 
    e.numero_empleado,
    e.nombre_completo,
    e.fecha_ingreso,
    e.departamento,
    -- Calculate base vacation days (10 per 6 month period)
    (SELECT SUM(pv.dias_base) 
     FROM periodos_vacacionales pv
     WHERE pv.fecha_inicio >= e.fecha_ingreso) AS dias_base,
    -- Calculate additional days (2 every 5 years)
    FLOOR(DATEDIFF(CURDATE(), e.fecha_ingreso)/1825) * 2 AS dias_antiguedad,
    -- Calculate used days
    (SELECT COALESCE(SUM(sv.dias_solicitados), 0) 
     FROM solicitudes_vacaciones sv 
     WHERE sv.numero_empleado = e.numero_empleado 
     AND sv.estado = 'aprobada') AS dias_usados,
    -- Calculate available days
    ((SELECT SUM(pv.dias_base) 
      FROM periodos_vacacionales pv
      WHERE pv.fecha_inicio >= e.fecha_ingreso) +
     (FLOOR(DATEDIFF(CURDATE(), e.fecha_ingreso)/1825) * 2) -
     (SELECT COALESCE(SUM(sv.dias_solicitados), 0) 
      FROM solicitudes_vacaciones sv 
      WHERE sv.numero_empleado = e.numero_empleado 
      AND sv.estado = 'aprobada')) AS dias_disponibles
FROM empleados e;

-- Department Tables (one per department)
CREATE TABLE direccion_general LIKE empleados;
CREATE TABLE planeacion_proyectos_estrategicos LIKE empleados;
CREATE TABLE programas_sociales LIKE empleados;
CREATE TABLE desarrollo_comunitario LIKE empleados;
CREATE TABLE asistencia_social LIKE empleados;
CREATE TABLE adulto_mayor LIKE empleados;
CREATE TABLE programas_alimentarios LIKE empleados;
CREATE TABLE unidad_rehabilitacion LIKE empleados;
CREATE TABLE administracion_finanzas LIKE empleados;
CREATE TABLE contraloria LIKE empleados;
CREATE TABLE procuraduria_proteccion LIKE empleados;
CREATE TABLE proteccion_grupos_vulnerables LIKE empleados;
CREATE TABLE accesibilidad_inclusion LIKE empleados;

-- Insert Sample Data
INSERT INTO empleados VALUES
(7027, 'GONZALEZ ALMANZA ULISES', '2024-10-02', 'ADMINISTRACION Y FINANZAS', 0),
(7196, 'MENESES HERNANDEZ JORGE ARTURO', '2024-12-23', 'PLANEACION Y PROYECTOS ESTRATEGICOS', 0),
(6398, 'PERALES GONZALEZ FLAVIO ERICK', '2022-09-01', 'DIRECCION GENERAL', 0);

-- Insert Vacation Periods (2024-2035)
INSERT INTO periodos_vacacionales (nombre_periodo, fecha_inicio, fecha_fin) VALUES
('Primer Periodo 2024', '2024-01-01', '2024-06-30'),
('Segundo Periodo 2024', '2024-07-01', '2024-12-31'),
('Primer Periodo 2025', '2025-01-01', '2025-06-30'),
('Segundo Periodo 2025', '2025-07-01', '2025-12-31');
-- Continue for all years until 2035...

-- Sample Vacation Request
INSERT INTO solicitudes_vacaciones VALUES
(NULL, 6398, '2023-11-15', '2023-12-01', '2023-12-05', 5, 'aprobada');

-- Stored Procedure to Calculate Vacation Days
DELIMITER //
CREATE PROCEDURE calcular_dias_vacaciones(IN emp_id INT)
BEGIN
    SELECT * FROM saldo_vacaciones WHERE numero_empleado = emp_id;
END //
DELIMITER ;
