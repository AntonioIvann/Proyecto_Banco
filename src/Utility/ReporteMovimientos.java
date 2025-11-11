package Utility;

import Model.Movimientos;
import Model.Tarjetas;
import Service.MovimientoService;
import Service.TarjetaService;
import Exception.ErrorBancario;
import java.math.BigDecimal;
import java.util.List;

/**
 * Generador de reportes de movimientos
 */
public class ReporteMovimientos {
    private MovimientoService movimientoServicio;
    private TarjetaService tarjetaServicio;

    public ReporteMovimientos() {
        this.movimientoServicio = new MovimientoService();
        this.tarjetaServicio = new TarjetaService();
    }

    /**
     * Genera un reporte de movimientos por tarjeta
     */
    public void generarReporteTarjeta(int idTarjeta) throws ErrorBancario {
        List<Movimientos> movimientos = movimientoServicio.obtenerMovimientosTarjeta(idTarjeta);
        Tarjetas tarjeta = tarjetaServicio.obtenerTarjetaPorId(idTarjeta);
        
        Formateador.imprimirEncabezado("REPORTE DE TARJETA");
        System.out.printf("Número de Tarjeta: %s%n", tarjeta.getNumeroTarjeta());
        System.out.printf("Saldo Actual: %s%n", Formateador.formatearMoneda(tarjeta.getSaldo()));
        Formateador.imprimirSeparador();
        
        BigDecimal totalCreditos = BigDecimal.ZERO;
        BigDecimal totalDebitos = BigDecimal.ZERO;
        
        for (Movimientos mov : movimientos) {
            if (mov.getTipo().equalsIgnoreCase("credito")) {
                totalCreditos = totalCreditos.add(mov.getCantidad());
            } else {
                totalDebitos = totalDebitos.add(mov.getCantidad());
            }
        }
        
        System.out.printf("Total Créditos: %s%n", Formateador.formatearMoneda(totalCreditos));
        System.out.printf("Total Débitos: %s%n", Formateador.formatearMoneda(totalDebitos));
        System.out.printf("Total Movimientos: %d%n", movimientos.size());
        Formateador.imprimirSeparador();
    }
}