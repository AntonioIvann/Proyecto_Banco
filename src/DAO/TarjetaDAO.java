package DAO;

import Model.Tarjetas;
import Config.JDBC;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DAO para la entidad Tarjeta
 */
public class TarjetaDAO {

    /**
     * Guarda una nueva tarjeta en la base de datos
     */
    public boolean guardar(Tarjetas tarjeta) throws SQLException {
        String sql = "INSERT INTO Tarjetas (idc, idb, numero_tarjeta, saldo, activa) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, tarjeta.getIdCliente());
            pstmt.setInt(2, tarjeta.getIdBanco());
            pstmt.setString(3, tarjeta.getNumeroTarjeta());
            pstmt.setBigDecimal(4, tarjeta.getSaldo());
            pstmt.setBoolean(5, tarjeta.isActiva());
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        tarjeta.setIdTarjeta(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Busca una tarjeta por su ID
     */
    public Tarjetas obtenerPorId(int idTarjeta) throws SQLException {
        String sql = "SELECT * FROM Tarjetas WHERE idt = ?";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idTarjeta);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetATarjeta(rs);
                }
            }
        }
        return null;
    }

    /**
     * Busca una tarjeta por su número
     */
    public Tarjetas obtenerPorNumero(String numeroTarjeta) throws SQLException {
        String sql = "SELECT * FROM Tarjetas WHERE numero_tarjeta = ?";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, numeroTarjeta);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetATarjeta(rs);
                }
            }
        }
        return null;
    }

    /**
     * Obtiene todas las tarjetas de un cliente
     */
    public List<Tarjetas> obtenerPorCliente(int idCliente) throws SQLException {
        List<Tarjetas> tarjetas = new ArrayList<>();
        String sql = "SELECT * FROM Tarjetas WHERE idc = ?";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tarjetas.add(mapearResultSetATarjeta(rs));
                }
            }
        }
        return tarjetas;
    }

    /**
     * Obtiene todas las tarjetas de un banco
     */
    public List<Tarjetas> obtenerPorBanco(int idBanco) throws SQLException {
        List<Tarjetas> tarjetas = new ArrayList<>();
        String sql = "SELECT * FROM Tarjetas WHERE idb = ?";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idBanco);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tarjetas.add(mapearResultSetATarjeta(rs));
                }
            }
        }
        return tarjetas;
    }

    /**
     * Obtiene todas las tarjetas
     */
    public List<Tarjetas> obtenerTodas() throws SQLException {
        List<Tarjetas> tarjetas = new ArrayList<>();
        String sql = "SELECT * FROM Tarjetas";
        try (Connection conn = JDBC.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tarjetas.add(mapearResultSetATarjeta(rs));
            }
        }
        return tarjetas;
    }

    /**
     * Actualiza una tarjeta existente
     */
    public boolean actualizar(Tarjetas tarjeta) throws SQLException {
        String sql = "UPDATE Tarjetas SET saldo = ?, activa = ? WHERE idt = ?";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBigDecimal(1, tarjeta.getSaldo());
            pstmt.setBoolean(2, tarjeta.isActiva());
            pstmt.setInt(3, tarjeta.getIdTarjeta());
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Elimina una tarjeta
     */
    public boolean eliminar(int idTarjeta) throws SQLException {
        String sql = "DELETE FROM Tarjetas WHERE idt = ?";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idTarjeta);
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Mapea un ResultSet a un objeto Tarjeta
     */
    private Tarjetas mapearResultSetATarjeta(ResultSet rs) throws SQLException {
        LocalDateTime fechaCreacion = null;
        if (rs.getTimestamp("fecha_creacion") != null) {
            fechaCreacion = rs.getTimestamp("fecha_creacion").toLocalDateTime();
        }
        
        return new Tarjetas(
                rs.getInt("idt"),
                rs.getInt("idc"),
                rs.getInt("idb"),
                rs.getString("numero_tarjeta"),
                rs.getBigDecimal("saldo"),
                fechaCreacion,
                rs.getBoolean("activa")
        );
    }
}