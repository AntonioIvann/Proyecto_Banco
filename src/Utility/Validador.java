package Utility;

import java.math.BigDecimal;

/**
 * Clase con métodos de validación
 */
public class Validador {
    
    /**
     * Valida que un número de tarjeta sea válido
     */
    public static boolean validarNumeroTarjeta(String numeroTarjeta) {
        if (numeroTarjeta == null || numeroTarjeta.length() != 16) {
            return false;
        }
        return numeroTarjeta.matches("\\d+");
    }
    
    /**
     * Valida que un email sea válido
     */
    public static boolean validarEmail(String email) {
        if (email == null) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    /**
     * Valida que una cantidad sea válida
     */
    public static boolean validarCantidad(BigDecimal cantidad) {
        return cantidad != null && cantidad.compareTo(BigDecimal.ZERO) > 0;
    }
    
    /**
     * Valida que una cadena no esté vacía
     */
    public static boolean validarCadenaNoVacia(String cadena) {
        return cadena != null && !cadena.trim().isEmpty();
    }
    
    /**
     * Valida un ID positivo
     */
    public static boolean validarIdPositivo(int id) {
        return id > 0;
    }
}