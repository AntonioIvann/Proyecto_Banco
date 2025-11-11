package DAO;

import Model.Movimientos;
import Config.JDBC;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DAO para la entidad Movimiento
 */
public class MovimientoDAO {

    /**
     * Guarda un nuevo movimiento en la base de datos
     */
    public boolean guardar(Movimientos movimiento) throws SQLException {
        String sql = "INSERT INTO Movimientos (idt, cantidad, tipo, descripcion) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, movimiento.getIdTarjeta());
            pstmt.setBigDecimal(2, movimiento.getCantidad());
            pstmt.setString(3, movimiento.getTipo());
            pstmt.setString(4, movimiento.getDescripcion());
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        movimiento.setIdMovimiento(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene todos los movimientos de una tarjeta
     */
    public List<Movimientos> obtenerPorTarjeta(int idTarjeta) throws SQLException {
        List<Movimientos> movimientos = new ArrayList<>();
        String sql = "SELECT * FROM Movimientos WHERE idt = ? ORDER BY fecha_movimiento DESC";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idTarjeta);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    movimientos.add(mapearResultSetAMovimiento(rs));
                }
            }
        }
        return movimientos;
    }

    /**
     * Obtiene todos los movimientos
     */
    public List<Movimientos> obtenerTodos() throws SQLException {
        List<Movimientos> movimientos = new ArrayList<>();
        String sql = "SELECT * FROM Movimientos ORDER BY fecha_movimiento DESC";
        try (Connection conn = JDBC.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                movimientos.add(mapearResultSetAMovimiento(rs));
            }
        }
        return movimientos;
    }

    /**
     * Obtiene un movimiento por su ID
     */
    public Movimientos obtenerPorId(int idMovimiento) throws SQLException {
        String sql = "SELECT * FROM Movimientos WHERE idm = ?";
        try (Connection conn = JDBC.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idMovimiento);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetAMovimiento(rs);
                }
            }
        }
        return null;
    }

    /**
     * Mapea un ResultSet a un objeto Movimiento
     */
    private Movimientos mapearResultSetAMovimiento(ResultSet rs) throws SQLException {
        LocalDateTime fechaMovimiento = null;
        if (rs.getTimestamp("fecha_movimiento") != null) {
            fechaMovimiento = rs.getTimestamp("fecha_movimiento").toLocalDateTime();
        }
        
        return new Movimientos(
                rs.getInt("idm"),
                rs.getInt("idt"),
                rs.getBigDecimal("cantidad"),
                rs.getString("tipo"),
                rs.getString("descripcion"),
                fechaMovimiento
        );
    }
}