package Utility;

import java.util.Scanner;
import java.math.BigDecimal;

/**
 * Clase de utilidades generales
 */
public class Utilidades {
    private static Scanner scanner = new Scanner(System.in);
    
    /**
     * Lee un entero desde la entrada estándar
     */
    public static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            int numero = Integer.parseInt(scanner.nextLine());
            return numero;
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un número válido");
            return -1;
        }
    }
    
    /**
     * Lee una cadena desde la entrada estándar
     */
    public static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
    
    /**
     * Lee un BigDecimal desde la entrada estándar
     */
    public static BigDecimal leerBigDecimal(String mensaje) {
        System.out.print(mensaje);
        try {
            return new BigDecimal(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese una cantidad válida");
            return null;
        }
    }
    
    /**
     * Lee un booleano desde la entrada estándar
     */
    public static boolean leerBooleano(String mensaje) {
        String respuesta = leerCadena(mensaje + " (s/n): ");
        return respuesta.equalsIgnoreCase("s");
    }
    
    /**
     * Pausa la ejecución
     */
    public static void pausar() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Limpia la pantalla (funciona mejor en terminales Unix/Linux/Mac)
     */
    public static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}