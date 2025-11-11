package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import Config.JDBC;

/**
 * Gestor de transacciones para operaciones críticas
 */
public class TransactionManager {
    private Connection conexion;
    
    /**
     * Inicia una transacción
     */
    public void iniciarTransaccion() throws SQLException {
        conexion = JDBC.obtenerConexion();
        conexion.setAutoCommit(false);
    }
    
    /**
     * Confirma la transacción
     */
    public void confirmar() throws SQLException {
        if (conexion != null) {
            conexion.commit();
            conexion.setAutoCommit(true);
        }
    }
    
    /**
     * Revierte la transacción
     */
    public void revertir() throws SQLException {
        if (conexion != null) {
            conexion.rollback();
            conexion.setAutoCommit(true);
        }
    }
    
    /**
     * Obtiene la conexión activa
     */
    public Connection obtenerConexion() {
        return conexion;
    }
}