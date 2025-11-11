package Exception;

/**
 * Excepción para tarjeta inactiva
 */
public class TarjetaInactiva extends ErrorBancario {
    public TarjetaInactiva(String mensaje) {
        super(mensaje);
    }
}