package Service;

import Model.Clientes;
import DAO.ClienteDAO;
import Exception.ErrorBancario;
import java.sql.SQLException;
import java.util.List;

/**
 * Servicio de negocio para Clientes
 */
public class ClienteService {
    private ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    /**
     * Crea un nuevo cliente
     */
    public Clientes crearCliente(String apellidoPaterno, String apellidoMaterno, String nombre) 
            throws ErrorBancario {
        try {
            if (apellidoPaterno == null || apellidoPaterno.trim().isEmpty() ||
                nombre == null || nombre.trim().isEmpty()) {
                throw new ErrorBancario("Nombre y apellido paterno son requeridos");
            }
            
            Clientes cliente = new Clientes(apellidoPaterno, apellidoMaterno, nombre);
            if (clienteDAO.guardar(cliente)) {
                return cliente;
            }
            throw new ErrorBancario("Error al guardar el cliente");
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene un cliente por su ID
     */
    public Clientes obtenerClientePorId(int idCliente) throws ErrorBancario {
        try {
            Clientes cliente = clienteDAO.obtenerPorId(idCliente);
            if (cliente == null) {
                throw new ErrorBancario("Cliente no encontrado");
            }
            return cliente;
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene clientes por nombre
     */
    public List<Clientes> obtenerClientesPorNombre(String nombre) throws ErrorBancario {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new ErrorBancario("El nombre no puede estar vacío");
            }
            return clienteDAO.obtenerPorNombre(nombre);
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene todos los clientes
     */
    public List<Clientes> obtenerTodosClientes() throws ErrorBancario {
        try {
            return clienteDAO.obtenerTodos();
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza un cliente
     */
    public Clientes actualizarCliente(int idCliente, String apellidoPaterno, 
                                    String apellidoMaterno, String nombre) throws ErrorBancario {
        try {
            Clientes cliente = clienteDAO.obtenerPorId(idCliente);
            if (cliente == null) {
                throw new ErrorBancario("Cliente no encontrado");
            }
            
            if (apellidoPaterno == null || apellidoPaterno.trim().isEmpty() ||
                nombre == null || nombre.trim().isEmpty()) {
                throw new ErrorBancario("Nombre y apellido paterno son requeridos");
            }
            
            cliente.setApellidoPaterno(apellidoPaterno);
            cliente.setApellidoMaterno(apellidoMaterno);
            cliente.setNombre(nombre);
            
            if (clienteDAO.actualizar(cliente)) {
                return cliente;
            }
            throw new ErrorBancario("Error al actualizar el cliente");
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina un cliente
     */
    public boolean eliminarCliente(int idCliente) throws ErrorBancario {
        try {
            Clientes cliente = clienteDAO.obtenerPorId(idCliente);
            if (cliente == null) {
                throw new ErrorBancario("Cliente no encontrado");
            }
            return clienteDAO.eliminar(idCliente);
        } catch (SQLException e) {
            throw new ErrorBancario("Error en la base de datos: " + e.getMessage(), e);
        }
    }
}