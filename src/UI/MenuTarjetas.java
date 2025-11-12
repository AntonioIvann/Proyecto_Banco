package UI;

import Model.Tarjetas;
import Model.Clientes;
import Model.Banco;
import Service.TarjetaService;
import Service.ClienteService;
import Service.BancoService;
import Exception.ErrorBancario;
import Utility.Formateador;
import Utility.Utilidades;
import java.util.List;

/**
 * Menú para gestionar tarjetas
 */
public class MenuTarjetas {
    private TarjetaService tarjetaServicio;
    private ClienteService clienteServicio;
    private BancoService bancoServicio;

    public MenuTarjetas() {
        this.tarjetaServicio = new TarjetaService();
        this.clienteServicio = new ClienteService();
        this.bancoServicio = new BancoService();
    }

    /**
     * Muestra el menú de tarjetas
     */
    public void mostrar() {
        boolean continuar = true;
        
        while (continuar) {
            Formateador.imprimirEncabezado("GESTIÓN DE TARJETAS");
            System.out.println("1. Crear Tarjeta");
            System.out.println("2. Consultar Tarjeta");
            System.out.println("3. Listar Tarjetas de Cliente");
            System.out.println("4. Listar Tarjetas de Banco");
            System.out.println("5. Listar Todas las Tarjetas");
            System.out.println("6. Desactivar Tarjeta");
            System.out.println("7. Activar Tarjeta");
            System.out.println("8. Eliminar Tarjeta");
            System.out.println("0. Volver al Menú Principal");
            Formateador.imprimirSeparador();
            
            int opcion = Utilidades.leerEntero("Seleccione una opción: ");
            
            switch (opcion) {
                case 1:
                    crearTarjeta();
                    break;
                case 2:
                    consultarTarjeta();
                    break;
                case 3:
                    listarTarjetasCliente();
                    break;
                case 4:
                    listarTarjetasBanco();
                    break;
                case 5:
                    listarTodasTarjetas();
                    break;
                case 6:
                    desactivarTarjeta();
                    break;
                case 7:
                    activarTarjeta();
                    break;
                case 8:
                    eliminarTarjeta();
                    break;
                case 0:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
            
            if (continuar && opcion != 0) {
                Utilidades.pausar();
                Utilidades.limpiarPantalla();
            }
        }
    }

    private void crearTarjeta() {
        Formateador.imprimirSeparador();
        int idCliente = Utilidades.leerEntero("Ingrese el ID del cliente: ");
        int idBanco = Utilidades.leerEntero("Ingrese el ID del banco: ");
        
        try {
            Tarjetas tarjeta = tarjetaServicio.crearTarjeta(idCliente, idBanco);
            System.out.println("✓ Tarjeta creada exitosamente");
            System.out.println("Número de tarjeta: " + tarjeta.getNumeroTarjeta());
            System.out.println("ID Tarjeta: " + tarjeta.getIdTarjeta());
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void consultarTarjeta() {
        Formateador.imprimirSeparador();
        int idTarjeta = Utilidades.leerEntero("Ingrese el ID de la tarjeta: ");
        
        try {
            Tarjetas tarjeta = tarjetaServicio.obtenerTarjetaPorId(idTarjeta);
            mostrarDetallesTarjeta(tarjeta);
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void listarTarjetasCliente() {
        Formateador.imprimirSeparador();
        int idCliente = Utilidades.leerEntero("Ingrese el ID del cliente: ");
        
        try {
            List<Tarjetas> tarjetas = tarjetaServicio.obtenerTarjetasPorCliente(idCliente);
            
            if (tarjetas.isEmpty()) {
                System.out.println("El cliente no tiene tarjetas registradas");
                return;
            }
            
            mostrarListaTarjetas(tarjetas, "TARJETAS DEL CLIENTE");
            
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void listarTarjetasBanco() {
        Formateador.imprimirSeparador();
        int idBanco = Utilidades.leerEntero("Ingrese el ID del banco: ");
        
        try {
            List<Tarjetas> tarjetas = tarjetaServicio.obtenerTarjetasPorBanco(idBanco);
            
            if (tarjetas.isEmpty()) {
                System.out.println("El banco no tiene tarjetas registradas");
                return;
            }
            
            mostrarListaTarjetas(tarjetas, "TARJETAS DEL BANCO");
            
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void listarTodasTarjetas() {
        try {
            List<Tarjetas> tarjetas = tarjetaServicio.obtenerTodasTarjetas();
            
            if (tarjetas.isEmpty()) {
                System.out.println("No hay tarjetas registradas");
                return;
            }
            
            mostrarListaTarjetas(tarjetas, "TODAS LAS TARJETAS");
            
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void desactivarTarjeta() {
        Formateador.imprimirSeparador();
        int idTarjeta = Utilidades.leerEntero("Ingrese el ID de la tarjeta a desactivar: ");
        
        if (Utilidades.leerBooleano("¿Está seguro de que desea desactivar esta tarjeta?")) {
            try {
                Tarjetas tarjeta = tarjetaServicio.desactivarTarjeta(idTarjeta);
                System.out.println("✓ Tarjeta desactivada exitosamente");
            } catch (ErrorBancario e) {
                System.out.println("✗ Error: " + e.getMessage());
            }
        }
    }

    private void activarTarjeta() {
        Formateador.imprimirSeparador();
        int idTarjeta = Utilidades.leerEntero("Ingrese el ID de la tarjeta a activar: ");
        
        try {
            Tarjetas tarjeta = tarjetaServicio.activarTarjeta(idTarjeta);
            System.out.println("✓ Tarjeta activada exitosamente");
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void eliminarTarjeta() {
        Formateador.imprimirSeparador();
        int idTarjeta = Utilidades.leerEntero("Ingrese el ID de la tarjeta a eliminar: ");
        
        if (Utilidades.leerBooleano("¿Está seguro de que desea eliminar esta tarjeta?")) {
            try {
                boolean eliminada = tarjetaServicio.eliminarTarjeta(idTarjeta);
                if (eliminada) {
                    System.out.println("✓ Tarjeta eliminada exitosamente");
                }
            } catch (ErrorBancario e) {
                System.out.println("✗ Error: " + e.getMessage());
            }
        }
    }

    private void mostrarDetallesTarjeta(Tarjetas tarjeta) {
        System.out.println("\n");
        Formateador.imprimirSeparador();
        System.out.printf("ID: %d%n", tarjeta.getIdTarjeta());
        System.out.printf("Número: %s%n", tarjeta.getNumeroTarjeta());
        System.out.printf("Saldo: %s%n", Formateador.formatearMoneda(tarjeta.getSaldo()));
        System.out.printf("Activa: %s%n", tarjeta.isActiva() ? "Sí" : "No");
        System.out.printf("Fecha Creación: %s%n", Formateador.formatearFecha(tarjeta.getFechaCreacion()));
        Formateador.imprimirSeparador();
    }

    private void mostrarListaTarjetas(List<Tarjetas> tarjetas, String titulo) {
        System.out.println("\n");
        Formateador.imprimirSeparador();
        System.out.println(titulo);
        Formateador.imprimirSeparador();
        
        for (Tarjetas tarjeta : tarjetas) {
            System.out.printf("ID: %d | Número: %s | Saldo: %s | Estado: %s%n", 
                            tarjeta.getIdTarjeta(), 
                            tarjeta.getNumeroTarjeta(), 
                            Formateador.formatearMoneda(tarjeta.getSaldo()),
                            tarjeta.isActiva() ? "Activa" : "Inactiva");
        }
    }
}