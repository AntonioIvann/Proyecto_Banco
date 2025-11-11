package DAO;

import Model.Banco;
import Config.JDBC;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Banco
 */
public class BancoDAO {

    /**
     * Guarda un nuevo banco en la base de datos
     */
    public boolean guardar(Banco banco) throws SQLException {
        String sql = "INSERT INTO Bancos (nombre) VALUES (?)";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, banco.getNombre());
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        banco.setIdBanco(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Busca un banco por su ID
     */
    public Banco obtenerPorId(int idBanco) throws SQLException {
        String sql = "SELECT * FROM Bancos WHERE idb = ?";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idBanco);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Banco(rs.getInt("idb"), rs.getString("nombre"));
                }
            }
        }
        return null;
    }

    /**
     * Busca un banco por su nombre
     */
    public Banco obtenerPorNombre(String nombre) throws SQLException {
        String sql = "SELECT * FROM Bancos WHERE nombre = ?";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Banco(rs.getInt("idb"), rs.getString("nombre"));
                }
            }
        }
        return null;
    }

    /**
     * Obtiene todos los bancos
     */
    public List<Banco> obtenerTodos() throws SQLException {
        List<Banco> bancos = new ArrayList<>();
        String sql = "SELECT * FROM Bancos";
        try (Connection conn = JDBC.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                bancos.add(new Banco(rs.getInt("idb"), rs.getString("nombre")));
            }
        }
        return bancos;
    }

    /**
     * Actualiza un banco existente
     */
    public boolean actualizar(Banco banco) throws SQLException {
        String sql = "UPDATE Bancos SET nombre = ? WHERE idb = ?";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, banco.getNombre());
            pstmt.setInt(2, banco.getIdBanco());
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un banco
     */
    public boolean eliminar(int idBanco) throws SQLException {
        String sql = "DELETE FROM Bancos WHERE idb = ?";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idBanco);
            return pstmt.executeUpdate() > 0;
        }
    }
}