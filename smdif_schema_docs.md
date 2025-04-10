# SMDIF Database Schema Documentation

## Table Relationships
![ER Diagram](./smdif_erd.drawio)

## Common Fields (All Tables)
- Fecha Registro: DATE NOT NULL
- Nombre: VARCHAR(100) NOT NULL
- Apellido Paterno: VARCHAR(100) NOT NULL
- Apellido Materno: VARCHAR(100)
- Edad: INT CHECK (Edad > 0)
- Sexo: ENUM('M','F','O') NOT NULL
- CURP: VARCHAR(18) PRIMARY KEY
- Dirección: TEXT NOT NULL
- Teléfono: VARCHAR(15)
- Email: VARCHAR(100)

## Table-Specific Fields

### Procuraduría
- Folio: VARCHAR(20) UNIQUE
- Recepción Reporte: ENUM('presencial','oficio','telefónica','email','otro')
- Datos Víctima: JSON
- Datos Evento: JSON
- Probable Agresor: JSON
- Tipo Violencia: SET('física','psicológica','sexual','económica','patrimonial','omisión')
- Familiares: JSON
- Acciones Interno: SET('asesoría jurídica','trabajo social','psicología','médica','otro')
- Acciones Externo: SET('PDMF Estatal',...)
- Reportante: JSON
- Persona Llena Formato: VARCHAR(100)

[Additional tables documentation would continue here...]

## Reporting Capabilities
1. Citizen service history across all departments
2. Program eligibility scoring
3. Case status tracking
4. Demographic analysis

## Sample Queries
```sql
-- Get all services for a citizen
SELECT * FROM all_tables WHERE CURP = 'CURP_VALUE';

-- Program eligibility report
SELECT p.*, ps.puntaje_total 
FROM personas p
JOIN programas_sociales ps ON p.CURP = ps.CURP
WHERE ps.puntaje_total > 50;
