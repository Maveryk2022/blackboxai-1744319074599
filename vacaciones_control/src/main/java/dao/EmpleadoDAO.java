package dao;

import model.Empleado;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {
    private Connection connection;

    public EmpleadoDAO() {
        connection = DBConnection.getConnection();
    }

    public boolean insertarEmpleado(Empleado empleado) {
        String sql = "INSERT INTO empleados (numero_empleado, nombre_completo, fecha_ingreso, departamento) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, empleado.getNumeroEmpleado());
            stmt.setString(2, empleado.getNombreCompleto());
            stmt.setDate(3, new java.sql.Date(empleado.getFechaIngreso().getTime()));
            stmt.setString(4, empleado.getDepartamento());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar empleado: " + e.getMessage());
            return false;
        }
    }

    public Empleado buscarPorNumero(int numeroEmpleado) {
        String sql = "SELECT * FROM empleados WHERE numero_empleado = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numeroEmpleado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Empleado(
                    rs.getInt("numero_empleado"),
                    rs.getString("nombre_completo"),
                    rs.getDate("fecha_ingreso"),
                    rs.getString("departamento")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado: " + e.getMessage());
        }
        return null;
    }

    public List<Empleado> listarTodos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                empleados.add(new Empleado(
                    rs.getInt("numero_empleado"),
                    rs.getString("nombre_completo"),
                    rs.getDate("fecha_ingreso"),
                    rs.getString("departamento")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
        }
        return empleados;
    }

    public boolean actualizarEmpleado(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre_completo = ?, fecha_ingreso = ?, departamento = ? WHERE numero_empleado = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, empleado.getNombreCompleto());
            stmt.setDate(2, new java.sql.Date(empleado.getFechaIngreso().getTime()));
            stmt.setString(3, empleado.getDepartamento());
            stmt.setInt(4, empleado.getNumeroEmpleado());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarEmpleado(int numeroEmpleado) {
        String sql = "DELETE FROM empleados WHERE numero_empleado = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numeroEmpleado);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }
}
