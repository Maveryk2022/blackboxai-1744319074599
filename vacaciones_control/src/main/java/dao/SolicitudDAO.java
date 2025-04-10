package dao;

import model.SolicitudVacaciones;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitudDAO {
    private Connection connection;

    public SolicitudDAO() {
        connection = DBConnection.getConnection();
    }

    public boolean crearSolicitud(SolicitudVacaciones solicitud) {
        String sql = "INSERT INTO solicitudes_vacaciones (numero_empleado, fecha_solicitud, fecha_inicio, fecha_fin, dias_solicitados, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, solicitud.getNumeroEmpleado());
            stmt.setDate(2, new java.sql.Date(solicitud.getFechaSolicitud().getTime()));
            stmt.setDate(3, new java.sql.Date(solicitud.getFechaInicio().getTime()));
            stmt.setDate(4, new java.sql.Date(solicitud.getFechaFin().getTime()));
            stmt.setInt(5, solicitud.getDiasSolicitados());
            stmt.setString(6, solicitud.getEstado());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        solicitud.setIdSolicitud(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al crear solicitud: " + e.getMessage());
            return false;
        }
    }

    public List<SolicitudVacaciones> buscarPorEmpleado(int numeroEmpleado) {
        List<SolicitudVacaciones> solicitudes = new ArrayList<>();
        String sql = "SELECT * FROM solicitudes_vacaciones WHERE numero_empleado = ? ORDER BY fecha_solicitud DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numeroEmpleado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SolicitudVacaciones s = new SolicitudVacaciones();
                s.setIdSolicitud(rs.getInt("id_solicitud"));
                s.setNumeroEmpleado(rs.getInt("numero_empleado"));
                s.setFechaSolicitud(rs.getDate("fecha_solicitud"));
                s.setFechaInicio(rs.getDate("fecha_inicio"));
                s.setFechaFin(rs.getDate("fecha_fin"));
                s.setDiasSolicitados(rs.getInt("dias_solicitados"));
                s.setEstado(rs.getString("estado"));
                solicitudes.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar solicitudes: " + e.getMessage());
        }
        return solicitudes;
    }

    public boolean cambiarEstadoSolicitud(int idSolicitud, String estado) {
        String sql = "UPDATE solicitudes_vacaciones SET estado = ? WHERE id_solicitud = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, idSolicitud);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al cambiar estado de solicitud: " + e.getMessage());
            return false;
        }
    }

    public int calcularDiasDisponibles(int numeroEmpleado) {
        String sql = "SELECT dias_disponibles FROM saldo_vacaciones WHERE numero_empleado = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numeroEmpleado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("dias_disponibles");
            }
        } catch (SQLException e) {
            System.err.println("Error al calcular días disponibles: " + e.getMessage());
        }
        return 0;
    }

    public List<SolicitudVacaciones> listarTodas() {
        List<SolicitudVacaciones> solicitudes = new ArrayList<>();
        String sql = "SELECT * FROM solicitudes_vacaciones ORDER BY fecha_solicitud DESC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                SolicitudVacaciones s = new SolicitudVacaciones();
                s.setIdSolicitud(rs.getInt("id_solicitud"));
                s.setNumeroEmpleado(rs.getInt("numero_empleado"));
                s.setFechaSolicitud(rs.getDate("fecha_solicitud"));
                s.setFechaInicio(rs.getDate("fecha_inicio"));
                s.setFechaFin(rs.getDate("fecha_fin"));
                s.setDiasSolicitados(rs.getInt("dias_solicitados"));
                s.setEstado(rs.getString("estado"));
                solicitudes.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar solicitudes: " + e.getMessage());
        }
        return solicitudes;
    }
}
