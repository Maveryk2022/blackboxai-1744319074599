-- Vacation Control System for SMDIF (SQLite Version)
-- Database Schema Implementation

-- Main Employees Table
CREATE TABLE empleados (
    numero_empleado INTEGER PRIMARY KEY,
    nombre_completo TEXT NOT NULL,
    fecha_ingreso TEXT NOT NULL, -- SQLite uses TEXT for dates
    departamento TEXT NOT NULL,
    dias_adicionales INTEGER DEFAULT 0
);

-- Vacation Periods Definition
CREATE TABLE periodos_vacacionales (
    id_periodo INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre_periodo TEXT NOT NULL,
    fecha_inicio TEXT NOT NULL,
    fecha_fin TEXT NOT NULL,
    dias_base INTEGER DEFAULT 10
);

-- Vacation Requests
CREATE TABLE solicitudes_vacaciones (
    id_solicitud INTEGER PRIMARY KEY AUTOINCREMENT,
    numero_empleado INTEGER NOT NULL,
    fecha_solicitud TEXT NOT NULL,
    fecha_inicio TEXT NOT NULL,
    fecha_fin TEXT NOT NULL,
    dias_solicitados INTEGER NOT NULL,
    estado TEXT CHECK(estado IN ('pendiente','aprobada','rechazada')) DEFAULT 'pendiente',
    FOREIGN KEY (numero_empleado) REFERENCES empleados(numero_empleado)
);

-- Insert Sample Data
INSERT INTO empleados VALUES
(7027, 'GONZALEZ ALMANZA ULISES', '2024-10-02', 'ADMINISTRACION Y FINANZAS', 0),
(7196, 'MENESES HERNANDEZ JORGE ARTURO', '2024-12-23', 'PLANEACION Y PROYECTOS ESTRATEGICOS', 0),
(6398, 'PERALES GONZALEZ FLAVIO ERICK', '2022-09-01', 'DIRECCION GENERAL', 0);

-- Insert Vacation Periods (2024-2025)
INSERT INTO periodos_vacacionales (nombre_periodo, fecha_inicio, fecha_fin) VALUES
('Primer Periodo 2024', '2024-01-01', '2024-06-30'),
('Segundo Periodo 2024', '2024-07-01', '2024-12-31'),
('Primer Periodo 2025', '2025-01-01', '2025-06-30'),
('Segundo Periodo 2025', '2025-07-01', '2025-12-31');

-- Sample Vacation Request
INSERT INTO solicitudes_vacaciones VALUES
(NULL, 6398, '2023-11-15', '2023-12-01', '2023-12-05', 5, 'aprobada');

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
     WHERE date(pv.fecha_inicio) >= date(e.fecha_ingreso)) AS dias_base,
    -- Calculate additional days (2 every 5 years)
    (CAST((julianday('now') - julianday(e.fecha_ingreso))/1825 AS INTEGER) * 2) AS dias_antiguedad,
    -- Calculate used days
    (SELECT COALESCE(SUM(sv.dias_solicitados), 0) 
     FROM solicitudes_vacaciones sv 
     WHERE sv.numero_empleado = e.numero_empleado 
     AND sv.estado = 'aprobada') AS dias_usados,
    -- Calculate available days
    ((SELECT SUM(pv.dias_base) 
      FROM periodos_vacacionales pv
      WHERE date(pv.fecha_inicio) >= date(e.fecha_ingreso)) +
     (CAST((julianday('now') - julianday(e.fecha_ingreso))/1825 AS INTEGER) * 2) -
     (SELECT COALESCE(SUM(sv.dias_solicitados), 0) 
      FROM solicitudes_vacaciones sv 
      WHERE sv.numero_empleado = e.numero_empleado 
      AND sv.estado = 'aprobada')) AS dias_disponibles
FROM empleados e;

-- Department Tables (one per department)
CREATE TABLE direccion_general AS SELECT * FROM empleados WHERE 1=0;
CREATE TABLE planeacion_proyectos_estrategicos AS SELECT * FROM empleados WHERE 1=0;
CREATE TABLE programas_sociales AS SELECT * FROM empleados WHERE 1=0;
CREATE TABLE desarrollo_comunitario AS SELECT * FROM empleados WHERE 1=0;
CREATE TABLE asistencia_social AS SELECT * FROM empleados WHERE 1=0;
CREATE TABLE adulto_mayor AS SELECT * FROM empleados WHERE 1=0;
CREATE TABLE programas_alimentarios AS SELECT * FROM empleados WHERE 1=0;
CREATE TABLE unidad_rehabilitacion AS SELECT * FROM empleados WHERE 1=0;
CREATE TABLE administracion_finanzas AS SELECT * FROM empleados WHERE 1=0;
CREATE TABLE contraloria AS SELECT * FROM empleados WHERE 1=0;
CREATE TABLE procuraduria_proteccion AS SELECT * FROM empleados WHERE 1=0;
CREATE TABLE proteccion_grupos_vulnerables AS SELECT * FROM empleados WHERE 1=0;
CREATE TABLE accesibilidad_inclusion AS SELECT * FROM empleados WHERE 1=0;
