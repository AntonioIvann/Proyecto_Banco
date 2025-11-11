package Service;

import Model.Movimientos;
import Model.Tarjetas;
import DAO.MovimientoDAO;
import DAO.TarjetaDAO;
import Exception.ErrorBancario;
import Exception.SaldoInsuficiente;
import Exception.TarjetaInactiva;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;

/**
 * Servicio de negocio para Movimientos
 */
public class MovimientoService {
    private MovimientoDAO movimientoDAO;
    private TarjetaDAO tarjetaDAO;

    public MovimientoService() {
        this.movimientoDAO = new MovimientoDAO();
        this.tarjetaDAO = new TarjetaDAO();
    }

    /**
     * Realiza un abono (crédito) a una tarjeta
     */
    public Movimientos abonarTarjeta(int idTarjeta, BigDecimal cantidad, String descripcion) 
            throws ErrorBancario {
        try {
            if (cantidad.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ErrorBancario("La cantidad debe ser mayor a cero");
            }
            
            Tarjetas tarjeta = tarjetaDAO.obtenerPorId(idTarjeta);
            if (tarjeta == null) {
                throw new ErrorBancario("Tarjeta no encontrada");
            }
            
            if (!tarjeta.isActiva()) {
                throw new TarjetaInactiva("La tarjeta está inactiva");
            }
            
            Movimientos movimiento = new Movimientos(idTarjeta, cantidad, "credito", descripcion);
            if (movimientoDAO.guardar(movimiento)) {
                tarjeta.setSaldo(tarjeta.getSaldo().add(cantidad));
                tarjetaDAO.actualizar(tarjeta);
                return movimiento;
            }
            throw new ErrorBancario("Error al guardar el movimiento");
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Realiza un retiro (débito) de una tarjeta
     */
    public Movimientos retirarDeTarjeta(int idTarjeta, BigDecimal cantidad, String descripcion) 
            throws ErrorBancario {
        try {
            if (cantidad.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ErrorBancario("La cantidad debe ser mayor a cero");
            }
            
            Tarjetas tarjeta = tarjetaDAO.obtenerPorId(idTarjeta);
            if (tarjeta == null) {
                throw new ErrorBancario("Tarjeta no encontrada");
            }
            
            if (!tarjeta.isActiva()) {
                throw new TarjetaInactiva("La tarjeta está inactiva");
            }
            
            if (tarjeta.getSaldo().compareTo(cantidad) < 0) {
                throw new SaldoInsuficiente("Saldo insuficiente. Saldo actual: " + tarjeta.getSaldo());
            }
            
            Movimientos movimiento = new Movimientos(idTarjeta, cantidad, "debito", descripcion);
            if (movimientoDAO.guardar(movimiento)) {
                tarjeta.setSaldo(tarjeta.getSaldo().subtract(cantidad));
                tarjetaDAO.actualizar(tarjeta);
                return movimiento;
            }
            throw new ErrorBancario("Error al guardar el movimiento");
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene todos los movimientos de una tarjeta
     */
    public List<Movimientos> obtenerMovimientosTarjeta(int idTarjeta) throws ErrorBancario {
        try {
            Tarjetas tarjeta = tarjetaDAO.obtenerPorId(idTarjeta);
            if (tarjeta == null) {
                throw new ErrorBancario("Tarjeta no encontrada");
            }
            return movimientoDAO.obtenerPorTarjeta(idTarjeta);
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene todos los movimientos
     */
    public List<Movimientos> obtenerTodosMovimientos() throws ErrorBancario {
        try {
            return movimientoDAO.obtenerTodos();
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }
}