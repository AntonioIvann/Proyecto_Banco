package DAO;

import Model.Clientes;
import Config.JDBC;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Cliente
 */
public class ClienteDAO {

    /**
     * Guarda un nuevo cliente en la base de datos
     */
    public boolean guardar(Clientes cliente) throws SQLException {
        String sql = "INSERT INTO Clientes (apellido_paterno, apellido_materno, nombre) VALUES (?, ?, ?)";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, cliente.getApellidoPaterno());
            pstmt.setString(2, cliente.getApellidoMaterno());
            pstmt.setString(3, cliente.getNombre());
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        cliente.setIdCliente(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Busca un cliente por su ID
     */
    public Clientes obtenerPorId(int idCliente) throws SQLException {
        String sql = "SELECT * FROM Clientes WHERE idc = ?";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Clientes(
                            rs.getInt("idc"),
                            rs.getString("apellido_paterno"),
                            rs.getString("apellido_materno"),
                            rs.getString("nombre")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Busca clientes por nombre
     */
    public List<Clientes> obtenerPorNombre(String nombre) throws SQLException {
        List<Clientes> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes WHERE nombre ILIKE ?";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + nombre + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    clientes.add(new Clientes(
                            rs.getInt("idc"),
                            rs.getString("apellido_paterno"),
                            rs.getString("apellido_materno"),
                            rs.getString("nombre")
                    ));
                }
            }
        }
        return clientes;
    }

    /**
     * Obtiene todos los clientes
     */
    public List<Clientes> obtenerTodos() throws SQLException {
        List<Clientes> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes ORDER BY nombre";
        try (Connection conn = JDBC.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clientes.add(new Clientes(
                        rs.getInt("idc"),
                        rs.getString("apellido_paterno"),
                        rs.getString("apellido_materno"),
                        rs.getString("nombre")
                ));
            }
        }
        return clientes;
    }

    /**
     * Actualiza un cliente existente
     */
    public boolean actualizar(Clientes cliente) throws SQLException {
        String sql = "UPDATE Clientes SET apellido_paterno = ?, apellido_materno = ?, nombre = ? WHERE idc = ?";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getApellidoPaterno());
            pstmt.setString(2, cliente.getApellidoMaterno());
            pstmt.setString(3, cliente.getNombre());
            pstmt.setInt(4, cliente.getIdCliente());
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un cliente
     */
    public boolean eliminar(int idCliente) throws SQLException {
        String sql = "DELETE FROM Clientes WHERE idc = ?";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            return pstmt.executeUpdate() > 0;
        }
    }
}