package Service;

import Model.Banco;
import DAO.BancoDAO;
import Exception.ErrorBancario;
import java.sql.SQLException;
import java.util.List;

/**
 * Servicio de negocio para Bancos
 */
public class BancoService {
    private BancoDAO bancoDAO;

    public BancoService() {
        this.bancoDAO = new BancoDAO();
    }

    /**
     * Crea un nuevo banco
     */
    public Banco crearBanco(String nombre) throws ErrorBancario {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new ErrorBancario("El nombre del banco no puede estar vacío");
            }
            
            Banco bancoExistente = bancoDAO.obtenerPorNombre(nombre);
            if (bancoExistente != null) {
                throw new ErrorBancario("Ya existe un banco con ese nombre");
            }
            
            Banco banco = new Banco(nombre);
            if (bancoDAO.guardar(banco)) {
                return banco;
            }
            throw new ErrorBancario("Error al guardar el banco");
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene un banco por su ID
     */
    public Banco obtenerBancoPorId(int idBanco) throws ErrorBancario {
        try {
            Banco banco = bancoDAO.obtenerPorId(idBanco);
            if (banco == null) {
                throw new ErrorBancario("Banco no encontrado");
            }
            return banco;
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene todos los bancos
     */
    public List<Banco> obtenerTodosBancos() throws ErrorBancario {
        try {
            return bancoDAO.obtenerTodos();
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza un banco
     */
    public Banco actualizarBanco(int idBanco, String nuevoNombre) throws ErrorBancario {
        try {
            Banco banco = bancoDAO.obtenerPorId(idBanco);
            if (banco == null) {
                throw new ErrorBancario("Banco no encontrado");
            }
            
            if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                throw new ErrorBancario("El nombre del banco no puede estar vacío");
            }
            
            banco.setNombre(nuevoNombre);
            if (bancoDAO.actualizar(banco)) {
                return banco;
            }
            throw new ErrorBancario("Error al actualizar el banco");
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina un banco
     */
    public boolean eliminarBanco(int idBanco) throws ErrorBancario {
        try {
            Banco banco = bancoDAO.obtenerPorId(idBanco);
            if (banco == null) {
                throw new ErrorBancario("Banco no encontrado");
            }
            return bancoDAO.eliminar(idBanco);
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }
}