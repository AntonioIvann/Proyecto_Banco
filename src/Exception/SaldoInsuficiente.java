package Exception;

/**
 * Excepción para saldo insuficiente
 */
public class SaldoInsuficiente extends ErrorBancario {
    public SaldoInsuficiente(String mensaje) {
        super(mensaje);
    }
}