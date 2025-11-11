package Service;

import Model.Tarjetas;
import DAO.TarjetaDAO;
import DAO.ClienteDAO;
import DAO.BancoDAO;
import Exception.ErrorBancario;
import Exception.TarjetaInactiva;
import Utility.Validador;
import java.sql.SQLException;
import java.util.List;

/**
 * Servicio de negocio para Tarjetas
 */
public class TarjetaService {
    private TarjetaDAO tarjetaDAO;
    private ClienteDAO clienteDAO;
    private BancoDAO bancoDAO;

    public TarjetaService() {
        this.tarjetaDAO = new TarjetaDAO();
        this.clienteDAO = new ClienteDAO();
        this.bancoDAO = new BancoDAO();
    }

    /**
     * Crea una nueva tarjeta
     */
    public Tarjetas crearTarjeta(int idCliente, int idBanco) throws ErrorBancario {
        try {
            if (clienteDAO.obtenerPorId(idCliente) == null) {
                throw new ErrorBancario("Cliente no encontrado");
            }
            if (bancoDAO.obtenerPorId(idBanco) == null) {
                throw new ErrorBancario("Banco no encontrado");
            }
            
            String numeroTarjeta = generarNumeroTarjeta();
            Tarjetas tarjeta = new Tarjetas(idCliente, idBanco, numeroTarjeta);
            
            if (tarjetaDAO.guardar(tarjeta)) {
                return tarjeta;
            }
            throw new ErrorBancario("Error al guardar la tarjeta");
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene una tarjeta por su ID
     */
    public Tarjetas obtenerTarjetaPorId(int idTarjeta) throws ErrorBancario {
        try {
            Tarjetas tarjeta = tarjetaDAO.obtenerPorId(idTarjeta);
            if (tarjeta == null) {
                throw new ErrorBancario("Tarjeta no encontrada");
            }
            return tarjeta;
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene una tarjeta por su número
     */
    public Tarjetas obtenerTarjetaPorNumero(String numeroTarjeta) throws ErrorBancario {
        try {
            Tarjetas tarjeta = tarjetaDAO.obtenerPorNumero(numeroTarjeta);
            if (tarjeta == null) {
                throw new ErrorBancario("Tarjeta no encontrada");
            }
            return tarjeta;
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene todas las tarjetas de un cliente
     */
    public List<Tarjetas> obtenerTarjetasPorCliente(int idCliente) throws ErrorBancario {
        try {
            if (clienteDAO.obtenerPorId(idCliente) == null) {
                throw new ErrorBancario("Cliente no encontrado");
            }
            return tarjetaDAO.obtenerPorCliente(idCliente);
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene todas las tarjetas de un banco
     */
    public List<Tarjetas> obtenerTarjetasPorBanco(int idBanco) throws ErrorBancario {
        try {
            if (bancoDAO.obtenerPorId(idBanco) == null) {
                throw new ErrorBancario("Banco no encontrado");
            }
            return tarjetaDAO.obtenerPorBanco(idBanco);
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene todas las tarjetas
     */
    public List<Tarjetas> obtenerTodasTarjetas() throws ErrorBancario {
        try {
            return tarjetaDAO.obtenerTodas();
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Desactiva una tarjeta
     */
    public Tarjetas desactivarTarjeta(int idTarjeta) throws ErrorBancario {
        try {
            Tarjetas tarjeta = tarjetaDAO.obtenerPorId(idTarjeta);
            if (tarjeta == null) {
                throw new ErrorBancario("Tarjeta no encontrada");
            }
            tarjeta.setActiva(false);
            if (tarjetaDAO.actualizar(tarjeta)) {
                return tarjeta;
            }
            throw new ErrorBancario("Error al desactivar la tarjeta");
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Activa una tarjeta
     */
    public Tarjetas activarTarjeta(int idTarjeta) throws ErrorBancario {
        try {
            Tarjetas tarjeta = tarjetaDAO.obtenerPorId(idTarjeta);
            if (tarjeta == null) {
                throw new ErrorBancario("Tarjeta no encontrada");
            }
            tarjeta.setActiva(true);
            if (tarjetaDAO.actualizar(tarjeta)) {
                return tarjeta;
            }
            throw new ErrorBancario("Error al activar la tarjeta");
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina una tarjeta
     */
    public boolean eliminarTarjeta(int idTarjeta) throws ErrorBancario {
        try {
            Tarjetas tarjeta = tarjetaDAO.obtenerPorId(idTarjeta);
            if (tarjeta == null) {
                throw new ErrorBancario("Tarjeta no encontrada");
            }
            return tarjetaDAO.eliminar(idTarjeta);
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Genera un número de tarjeta aleatorio
     */
    private String generarNumeroTarjeta() {
        StringBuilder numero = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            numero.append((int)(Math.random() * 10));
        }
        return numero.toString();
    }
}