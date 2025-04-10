package dao;

import model.PeriodoVacacional;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeriodoDAO {
    private Connection connection;

    public PeriodoDAO() {
        connection = DBConnection.getConnection();
    }

    public boolean insertarPeriodo(PeriodoVacacional periodo) {
        String sql = "INSERT INTO periodos_vacacionales (nombre_periodo, fecha_inicio, fecha_fin, dias_base) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, periodo.getNombrePeriodo());
            stmt.setDate(2, new java.sql.Date(periodo.getFechaInicio().getTime()));
            stmt.setDate(3, new java.sql.Date(periodo.getFechaFin().getTime()));
            stmt.setInt(4, periodo.getDiasBase());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar periodo: " + e.getMessage());
            return false;
        }
    }

    public List<PeriodoVacacional> listarTodos() {
        List<PeriodoVacacional> periodos = new ArrayList<>();
        String sql = "SELECT * FROM periodos_vacacionales ORDER BY fecha_inicio";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PeriodoVacacional p = new PeriodoVacacional();
                p.setIdPeriodo(rs.getInt("id_periodo"));
                p.setNombrePeriodo(rs.getString("nombre_periodo"));
                p.setFechaInicio(rs.getDate("fecha_inicio"));
                p.setFechaFin(rs.getDate("fecha_fin"));
                p.setDiasBase(rs.getInt("dias_base"));
                periodos.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar periodos: " + e.getMessage());
        }
        return periodos;
    }

    public List<PeriodoVacacional> buscarPorRangoFechas(Date desde, Date hasta) {
        List<PeriodoVacacional> periodos = new ArrayList<>();
        String sql = "SELECT * FROM periodos_vacacionales WHERE fecha_inicio >= ? AND fecha_fin <= ? ORDER BY fecha_inicio";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(desde.getTime()));
            stmt.setDate(2, new java.sql.Date(hasta.getTime()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PeriodoVacacional p = new PeriodoVacacional();
                p.setIdPeriodo(rs.getInt("id_periodo"));
                p.setNombrePeriodo(rs.getString("nombre_periodo"));
                p.setFechaInicio(rs.getDate("fecha_inicio"));
                p.setFechaFin(rs.getDate("fecha_fin"));
                p.setDiasBase(rs.getInt("dias_base"));
                periodos.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar periodos por fecha: " + e.getMessage());
        }
        return periodos;
    }

    public boolean actualizarPeriodo(PeriodoVacacional periodo) {
        String sql = "UPDATE periodos_vacacionales SET nombre_periodo = ?, fecha_inicio = ?, fecha_fin = ?, dias_base = ? WHERE id_periodo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, periodo.getNombrePeriodo());
            stmt.setDate(2, new java.sql.Date(periodo.getFechaInicio().getTime()));
            stmt.setDate(3, new java.sql.Date(periodo.getFechaFin().getTime()));
            stmt.setInt(4, periodo.getDiasBase());
            stmt.setInt(5, periodo.getIdPeriodo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar periodo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPeriodo(int idPeriodo) {
        String sql = "DELETE FROM periodos_vacacionales WHERE id_periodo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPeriodo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar periodo: " + e.getMessage());
            return false;
        }
    }
}
