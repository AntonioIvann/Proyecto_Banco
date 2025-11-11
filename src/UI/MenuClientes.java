package UI;

import Model.Clientes;
import Service.ClienteService;
import Exception.ErrorBancario;
import Utility.Formateador;
import Utility.Utilidades;
import java.util.List;

/**
 * Menú para gestionar clientes
 */
public class MenuClientes {
    private ClienteService clienteServicio;

    public MenuClientes() {
        this.clienteServicio = new ClienteService();
    }

    /**
     * Muestra el menú de clientes
     */
    public void mostrar() {
        boolean continuar = true;
        
        while (continuar) {
            Formateador.imprimirEncabezado("GESTIÓN DE CLIENTES");
            System.out.println("1. Crear Cliente");
            System.out.println("2. Consultar Cliente");
            System.out.println("3. Buscar Cliente por Nombre");
            System.out.println("4. Listar Todos los Clientes");
            System.out.println("5. Actualizar Cliente");
            System.out.println("6. Eliminar Cliente");
            System.out.println("7. Volver al Menú Principal");
            Formateador.imprimirSeparador();
            
            int opcion = Utilidades.leerEntero("Seleccione una opción: ");
            
            switch (opcion) {
                case 1:
                    crearCliente();
                    break;
                case 2:
                    consultarCliente();
                    break;
                case 3:
                    buscarClientePorNombre();
                    break;
                case 4:
                    listarTodosClientes();
                    break;
                case 5:
                    actualizarCliente();
                    break;
                case 6:
                    eliminarCliente();
                    break;
                case 7:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
            
            if (continuar && opcion != 7) {
                Utilidades.pausar();
            }
        }
    }

    private void crearCliente() {
        Formateador.imprimirSeparador();
        String apellidoPaterno = Utilidades.leerCadena("Ingrese apellido paterno: ");
        String apellidoMaterno = Utilidades.leerCadena("Ingrese apellido materno: ");
        String nombre = Utilidades.leerCadena("Ingrese nombre: ");
        
        try {
            Clientes cliente = clienteServicio.crearCliente(apellidoPaterno, apellidoMaterno, nombre);
            System.out.println("✓ Cliente creado exitosamente con ID: " + cliente.getIdCliente());
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void consultarCliente() {
        Formateador.imprimirSeparador();
        int idCliente = Utilidades.leerEntero("Ingrese el ID del cliente: ");
        
        try {
            Clientes cliente = clienteServicio.obtenerClientePorId(idCliente);
            System.out.println("\n" + cliente);
            System.out.println("Nombre completo: " + cliente.getNombreCompleto());
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void buscarClientePorNombre() {
        Formateador.imprimirSeparador();
        String nombre = Utilidades.leerCadena("Ingrese nombre del cliente a buscar: ");
        
        try {
            List<Clientes> clientes = clienteServicio.obtenerClientesPorNombre(nombre);
            
            if (clientes.isEmpty()) {
                System.out.println("No se encontraron clientes con ese nombre");
                return;
            }
            
            System.out.println("\nResultados encontrados:");
            for (Clientes cliente : clientes) {
                System.out.printf("ID: %d | %s%n", cliente.getIdCliente(), cliente.getNombreCompleto());
            }
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void listarTodosClientes() {
        try {
            List<Clientes> clientes = clienteServicio.obtenerTodosClientes();
            
            if (clientes.isEmpty()) {
                System.out.println("No hay clientes registrados");
                return;
            }
            
            System.out.println("\n");
            Formateador.imprimirSeparador();
            System.out.println("LISTA DE CLIENTES");
            Formateador.imprimirSeparador();
            
            for (Clientes cliente : clientes) {
                System.out.printf("ID: %d | %s%n", cliente.getIdCliente(), cliente.getNombreCompleto());
            }
            
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void actualizarCliente() {
        Formateador.imprimirSeparador();
        int idCliente = Utilidades.leerEntero("Ingrese el ID del cliente a actualizar: ");
        String apellidoPaterno = Utilidades.leerCadena("Ingrese nuevo apellido paterno: ");
        String apellidoMaterno = Utilidades.leerCadena("Ingrese nuevo apellido materno: ");
        String nombre = Utilidades.leerCadena("Ingrese nuevo nombre: ");
        
        try {
            Clientes cliente = clienteServicio.actualizarCliente(idCliente, apellidoPaterno, 
                                                               apellidoMaterno, nombre);
            System.out.println("✓ Cliente actualizado exitosamente");
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void eliminarCliente() {
        Formateador.imprimirSeparador();
        int idCliente = Utilidades.leerEntero("Ingrese el ID del cliente a eliminar: ");
        
        if (Utilidades.leerBooleano("¿Está seguro de que desea eliminar este cliente?")) {
            try {
                boolean eliminado = clienteServicio.eliminarCliente(idCliente);
                if (eliminado) {
                    System.out.println("✓ Cliente eliminado exitosamente");
                }
            } catch (ErrorBancario e) {
                System.out.println("✗ Error: " + e.getMessage());
            }
        }
    }
}