package Utility;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;

/**
 * Clase para formatear salidas
 */
public class Formateador {
    private static final DateTimeFormatter DATE_FORMATTER = 
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("$#,##0.00");
    
    /**
     * Formatea una fecha y hora
     */
    public static String formatearFecha(LocalDateTime fecha) {
        if (fecha == null) {
            return "N/A";
        }
        return fecha.format(DATE_FORMATTER);
    }
    
    /**
     * Formatea una cantidad en formato moneda
     */
    public static String formatearMoneda(BigDecimal cantidad) {
        if (cantidad == null) {
            return "$0.00";
        }
        return DECIMAL_FORMATTER.format(cantidad);
    }
    
    /**
     * Imprime un separador
     */
    public static void imprimirSeparador() {
        System.out.println("================================");
    }
    
    /**
     * Imprime un separador grande
     */
    public static void imprimirSeparadorGrande() {
        System.out.println("========================================");
    }
    
    /**
     * Imprime un encabezado
     */
    public static void imprimirEncabezado(String texto) {
        imprimirSeparadorGrande();
        System.out.println("  " + texto);
        imprimirSeparadorGrande();
    }
}