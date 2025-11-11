package Service;

import Model.Tarjetas;
import Model.Movimientos;
import DAO.TarjetaDAO;
import DAO.MovimientoDAO;
import Exception.ErrorBancario;
import Exception.SaldoInsuficiente;
import Exception.TarjetaInactiva;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;

/**
 * Servicio para manejar transferencias bancarias
 */
public class TransferenciaService {
    private TarjetaDAO tarjetaDAO;
    private MovimientoDAO movimientoDAO;

    public TransferenciaService() {
        this.tarjetaDAO = new TarjetaDAO();
        this.movimientoDAO = new MovimientoDAO();
    }

    /**
     * Transferencia entre tarjetas del mismo banco
     */
    public void transferirIntraBancaria(int idTarjetaOrigen, int idTarjetaDestino, 
                                       BigDecimal cantidad, String descripcion) 
            throws ErrorBancario {
        try {
            Tarjetas tarjetaOrigen = tarjetaDAO.obtenerPorId(idTarjetaOrigen);
            Tarjetas tarjetaDestino = tarjetaDAO.obtenerPorId(idTarjetaDestino);
            
            if (tarjetaOrigen == null || tarjetaDestino == null) {
                throw new ErrorBancario("Una o ambas tarjetas no existen");
            }
            
            if (!tarjetaOrigen.isActiva() || !tarjetaDestino.isActiva()) {
                throw new TarjetaInactiva("Una o ambas tarjetas están inactivas");
            }
            
            if (tarjetaOrigen.getIdBanco() != tarjetaDestino.getIdBanco()) {
                throw new ErrorBancario("Las tarjetas no son del mismo banco");
            }
            
            if (cantidad.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ErrorBancario("La cantidad debe ser mayor a cero");
            }
            
            if (tarjetaOrigen.getSaldo().compareTo(cantidad) < 0) {
                throw new SaldoInsuficiente("Saldo insuficiente. Saldo actual: " + tarjetaOrigen.getSaldo());
            }
            
            // Realizar transferencia
            tarjetaOrigen.setSaldo(tarjetaOrigen.getSaldo().subtract(cantidad));
            tarjetaDestino.setSaldo(tarjetaDestino.getSaldo().add(cantidad));
            
            // Crear movimientos
            Movimientos movimientoOrigen = new Movimientos(idTarjetaOrigen, cantidad, "debito", 
                    "Transferencia intra-bancaria a " + descripcion);
            Movimientos movimientoDestino = new Movimientos(idTarjetaDestino, cantidad, "credito", 
                    "Transferencia intra-bancaria de " + descripcion);
            
            // Guardar cambios
            movimientoDAO.guardar(movimientoOrigen);
            movimientoDAO.guardar(movimientoDestino);
            tarjetaDAO.actualizar(tarjetaOrigen);
            tarjetaDAO.actualizar(tarjetaDestino);
            
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Transferencia entre tarjetas de diferentes bancos (interbancaria)
     */
    public void transferirInterbancaria(int idTarjetaOrigen, int idTarjetaDestino, 
                                       BigDecimal cantidad, BigDecimal comision, 
                                       String descripcion) 
            throws ErrorBancario {
        try {
            Tarjetas tarjetaOrigen = tarjetaDAO.obtenerPorId(idTarjetaOrigen);
            Tarjetas tarjetaDestino = tarjetaDAO.obtenerPorId(idTarjetaDestino);
            
            if (tarjetaOrigen == null || tarjetaDestino == null) {
                throw new ErrorBancario("Una o ambas tarjetas no existen");
            }
            
            if (!tarjetaOrigen.isActiva() || !tarjetaDestino.isActiva()) {
                throw new TarjetaInactiva("Una o ambas tarjetas están inactivas");
            }
            
            if (tarjetaOrigen.getIdBanco() == tarjetaDestino.getIdBanco()) {
                throw new ErrorBancario("Para transferencia interbancaria, las tarjetas deben ser de diferentes bancos");
            }
            
            if (cantidad.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ErrorBancario("La cantidad debe ser mayor a cero");
            }
            
            BigDecimal totalConComision = cantidad.add(comision);
            if (tarjetaOrigen.getSaldo().compareTo(totalConComision) < 0) {
                throw new SaldoInsuficiente("Saldo insuficiente incluyendo comisión. Total: " + totalConComision + 
                                          " Saldo actual: " + tarjetaOrigen.getSaldo());
            }
            
            // Realizar transferencia
            tarjetaOrigen.setSaldo(tarjetaOrigen.getSaldo().subtract(totalConComision));
            tarjetaDestino.setSaldo(tarjetaDestino.getSaldo().add(cantidad));
            
            // Crear movimientos
            Movimientos movimientoOrigen = new Movimientos(idTarjetaOrigen, totalConComision, "debito", 
                    "Transferencia interbancaria a " + descripcion + " (incluye comisión: " + comision + ")");
            Movimientos movimientoDestino = new Movimientos(idTarjetaDestino, cantidad, "credito", 
                    "Transferencia interbancaria de " + descripcion);
            
            // Guardar cambios
            movimientoDAO.guardar(movimientoOrigen);
            movimientoDAO.guardar(movimientoDestino);
            tarjetaDAO.actualizar(tarjetaOrigen);
            tarjetaDAO.actualizar(tarjetaDestino);
            
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }
}