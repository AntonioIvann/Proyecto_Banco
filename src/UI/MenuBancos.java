package UI;

import Model.Banco;
import Service.BancoService;
import Exception.ErrorBancario;
import Utility.Formateador;
import Utility.Utilidades;
import java.util.List;

/**
 * Menú para gestionar bancos
 */
public class MenuBancos {
    private BancoService bancoServicio;

    public MenuBancos() {
        this.bancoServicio = new BancoService();
    }

    /**
     * Muestra el menú de bancos
     */
    public void mostrar() {
        boolean continuar = true;
        
        while (continuar) {
            Formateador.imprimirEncabezado("GESTIÓN DE BANCOS");
            System.out.println("1. Crear Banco");
            System.out.println("2. Consultar Banco");
            System.out.println("3. Listar Todos los Bancos");
            System.out.println("4. Actualizar Banco");
            System.out.println("5. Eliminar Banco");
            System.out.println("6. Volver al Menú Principal");
            Formateador.imprimirSeparador();
            
            int opcion = Utilidades.leerEntero("Seleccione una opción: ");
            
            switch (opcion) {
                case 1:
                    crearBanco();
                    break;
                case 2:
                    consultarBanco();
                    break;
                case 3:
                    listarTodosBancos();
                    break;
                case 4:
                    actualizarBanco();
                    break;
                case 5:
                    eliminarBanco();
                    break;
                case 6:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
            
            if (continuar && opcion != 6) {
                Utilidades.pausar();
            }
        }
    }

    private void crearBanco() {
        Formateador.imprimirSeparador();
        String nombre = Utilidades.leerCadena("Ingrese el nombre del banco: ");
        
        try {
            Banco banco = bancoServicio.crearBanco(nombre);
            System.out.println("✓ Banco creado exitosamente con ID: " + banco.getIdBanco());
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void consultarBanco() {
        Formateador.imprimirSeparador();
        int idBanco = Utilidades.leerEntero("Ingrese el ID del banco: ");
        
        try {
            Banco banco = bancoServicio.obtenerBancoPorId(idBanco);
            System.out.println("\n" + banco);
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void listarTodosBancos() {
        try {
            List<Banco> bancos = bancoServicio.obtenerTodosBancos();
            
            if (bancos.isEmpty()) {
                System.out.println("No hay bancos registrados");
                return;
            }
            
            System.out.println("\n");
            Formateador.imprimirSeparador();
            System.out.println("LISTA DE BANCOS");
            Formateador.imprimirSeparador();
            
            for (Banco banco : bancos) {
                System.out.printf("ID: %d | Nombre: %s%n", banco.getIdBanco(), banco.getNombre());
            }
            
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void actualizarBanco() {
        Formateador.imprimirSeparador();
        int idBanco = Utilidades.leerEntero("Ingrese el ID del banco a actualizar: ");
        String nuevoNombre = Utilidades.leerCadena("Ingrese el nuevo nombre: ");
        
        try {
            Banco banco = bancoServicio.actualizarBanco(idBanco, nuevoNombre);
            System.out.println("✓ Banco actualizado exitosamente");
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void eliminarBanco() {
        Formateador.imprimirSeparador();
        int idBanco = Utilidades.leerEntero("Ingrese el ID del banco a eliminar: ");
        
        if (Utilidades.leerBooleano("¿Está seguro de que desea eliminar este banco?")) {
            try {
                boolean eliminado = bancoServicio.eliminarBanco(idBanco);
                if (eliminado) {
                    System.out.println("✓ Banco eliminado exitosamente");
                }
            } catch (ErrorBancario e) {
                System.out.println("✗ Error: " + e.getMessage());
            }
        }
    }
}