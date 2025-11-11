package UI;

import Utility.Formateador;
import Utility.Utilidades;

/**
 * Menú principal del sistema
 */
public class MenuPrincipal {
    private MenuBancos menuBancos;
    private MenuClientes menuClientes;
    private MenuTarjetas menuTarjetas;
    private MenuMovimientos menuMovimientos;

    public MenuPrincipal() {
        this.menuBancos = new MenuBancos();
        this.menuClientes = new MenuClientes();
        this.menuTarjetas = new MenuTarjetas();
        this.menuMovimientos = new MenuMovimientos();
    }

    /**
     * Muestra el menú principal
     */
    public void mostrar() {
        boolean continuar = true;
        
        while (continuar) {
            Formateador.imprimirEncabezado("SISTEMA BANCARIO");
            System.out.println("1. Gestionar Bancos");
            System.out.println("2. Gestionar Clientes");
            System.out.println("3. Gestionar Tarjetas");
            System.out.println("4. Movimientos Bancarios");
            System.out.println("5. Salir");
            Formateador.imprimirSeparador();
            
            int opcion = Utilidades.leerEntero("Seleccione una opción: ");
            
            switch (opcion) {
                case 1:
                    menuBancos.mostrar();
                    break;
                case 2:
                    menuClientes.mostrar();
                    break;
                case 3:
                    menuTarjetas.mostrar();
                    break;
                case 4:
                    menuMovimientos.mostrar();
                    break;
                case 5:
                    System.out.println("¡Gracias por usar el Sistema Bancario!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
            
            if (continuar && opcion != 5) {
                Utilidades.pausar();
                Utilidades.limpiarPantalla();
            }
        }
    }
}