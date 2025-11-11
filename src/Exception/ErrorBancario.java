package Exception;

/**
 * Excepción personalizada para errores bancarios
 */
public class ErrorBancario extends Exception {
    public ErrorBancario(String mensaje) {
        super(mensaje);
    }

    public ErrorBancario(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}