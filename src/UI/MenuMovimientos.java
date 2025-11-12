package UI;

import Model.Movimientos;
import Model.Tarjetas;
import Service.MovimientoService;
import Service.TarjetaService;
import Service.TransferenciaService;
import Exception.ErrorBancario;
import Utility.Formateador;
import Utility.Utilidades;
import java.math.BigDecimal;
import java.util.List;

/**
 * Menú para gestionar movimientos bancarios
 */
public class MenuMovimientos {
    private MovimientoService movimientoServicio;
    private TarjetaService tarjetaServicio;
    private TransferenciaService transferenciaServicio;

    public MenuMovimientos() {
        this.movimientoServicio = new MovimientoService();
        this.tarjetaServicio = new TarjetaService();
        this.transferenciaServicio = new TransferenciaService();
    }

    /**
     * Muestra el menú de movimientos
     */
    public void mostrar() {
        boolean continuar = true;
        
        while (continuar) {
            Formateador.imprimirEncabezado("MOVIMIENTOS BANCARIOS");
            System.out.println("1. Abonar a Tarjeta");
            System.out.println("2. Retirar efectivo");
            System.out.println("3. Transferencia Intra-bancaria");
            System.out.println("4. Transferencia Interbancaria");
            System.out.println("5. Ver Movimientos de Tarjeta");
            System.out.println("6. Ver Todos los Movimientos");
            System.out.println("0. Volver al Menú Principal");
            Formateador.imprimirSeparador();
            
            int opcion = Utilidades.leerEntero("Seleccione una opción: ");
            
            switch (opcion) {
                case 1:
                    abonarTarjeta();
                    break;
                case 2:
                    retirarTarjeta();
                    break;
                case 3:
                    transferenciaIntraBancaria();
                    break;
                case 4:
                    transferenciaInterbancaria();
                    break;
                case 5:
                    verMovimientosTarjeta();
                    break;
                case 6:
                    verTodosMovimientos();
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

    private void abonarTarjeta() {
        Formateador.imprimirSeparador();
        int idTarjeta = Utilidades.leerEntero("Ingrese el ID de la tarjeta: ");
        BigDecimal cantidad = Utilidades.leerBigDecimal("Ingrese la cantidad a abonar: $");
        String descripcion = Utilidades.leerCadena("Ingrese descripción del movimiento: ");
        
        try {
            Movimientos movimiento = movimientoServicio.abonarTarjeta(idTarjeta, cantidad, descripcion);
            System.out.println("✓ Abono realizado exitosamente");
            
            Tarjetas tarjeta = tarjetaServicio.obtenerTarjetaPorId(idTarjeta);
            System.out.println("Nuevo saldo: " + Formateador.formatearMoneda(tarjeta.getSaldo()));
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void retirarTarjeta() {
        Formateador.imprimirSeparador();
        int idTarjeta = Utilidades.leerEntero("Ingrese el ID de la tarjeta: ");
        BigDecimal cantidad = Utilidades.leerBigDecimal("Ingrese la cantidad a retirar: $");
        String descripcion = Utilidades.leerCadena("Ingrese descripción del movimiento: ");
        
        try {
            Movimientos movimiento = movimientoServicio.retirarDeTarjeta(idTarjeta, cantidad, descripcion);
            System.out.println("✓ Retiro realizado exitosamente");
            
            Tarjetas tarjeta = tarjetaServicio.obtenerTarjetaPorId(idTarjeta);
            System.out.println("Nuevo saldo: " + Formateador.formatearMoneda(tarjeta.getSaldo()));
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void transferenciaIntraBancaria() {
        Formateador.imprimirSeparador();
        System.out.println("-- TRANSFERENCIA INTRA-BANCARIA (Mismo Banco) --");
        
        int idTarjetaOrigen = Utilidades.leerEntero("Ingrese ID de tarjeta origen: ");
        int idTarjetaDestino = Utilidades.leerEntero("Ingrese ID de tarjeta destino: ");
        BigDecimal cantidad = Utilidades.leerBigDecimal("Ingrese cantidad a transferir: $");
        
        try {
            Tarjetas tarjetaOrigen = tarjetaServicio.obtenerTarjetaPorId(idTarjetaOrigen);
            Tarjetas tarjetaDestino = tarjetaServicio.obtenerTarjetaPorId(idTarjetaDestino);
            
            System.out.println("\n--- Detalles de la Transferencia ---");
            System.out.println("Desde: " + tarjetaOrigen.getNumeroTarjeta() + 
                             " (Saldo actual: " + Formateador.formatearMoneda(tarjetaOrigen.getSaldo()) + ")");
            System.out.println("Hacia: " + tarjetaDestino.getNumeroTarjeta() + 
                             " (Saldo actual: " + Formateador.formatearMoneda(tarjetaDestino.getSaldo()) + ")");
            System.out.println("Cantidad: " + Formateador.formatearMoneda(cantidad));
            
            if (Utilidades.leerBooleano("\n¿Desea confirmar esta transferencia?")) {
                transferenciaServicio.transferirIntraBancaria(idTarjetaOrigen, idTarjetaDestino, 
                                                             cantidad, "Transferencia intra-bancaria");
                System.out.println("\n✓ Transferencia intra-bancaria completada exitosamente");
                
                tarjetaOrigen = tarjetaServicio.obtenerTarjetaPorId(idTarjetaOrigen);
                tarjetaDestino = tarjetaServicio.obtenerTarjetaPorId(idTarjetaDestino);
                
                System.out.println("\nNuevos saldos:");
                System.out.println("Origen: " + Formateador.formatearMoneda(tarjetaOrigen.getSaldo()));
                System.out.println("Destino: " + Formateador.formatearMoneda(tarjetaDestino.getSaldo()));
            }
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void transferenciaInterbancaria() {
        Formateador.imprimirSeparador();
        System.out.println("-- TRANSFERENCIA INTERBANCARIA (Diferentes Bancos) --");
        
        int idTarjetaOrigen = Utilidades.leerEntero("Ingrese ID de tarjeta origen: ");
        int idTarjetaDestino = Utilidades.leerEntero("Ingrese ID de tarjeta destino: ");
        BigDecimal cantidad = Utilidades.leerBigDecimal("Ingrese cantidad a transferir: $");
        BigDecimal comision = Utilidades.leerBigDecimal("Ingrese comisión de transferencia: $");
        
        try {
            Tarjetas tarjetaOrigen = tarjetaServicio.obtenerTarjetaPorId(idTarjetaOrigen);
            Tarjetas tarjetaDestino = tarjetaServicio.obtenerTarjetaPorId(idTarjetaDestino);
            
            BigDecimal totalConComision = cantidad.add(comision);
            
            System.out.println("\n--- Detalles de la Transferencia ---");
            System.out.println("Desde: " + tarjetaOrigen.getNumeroTarjeta() + 
                             " (Saldo actual: " + Formateador.formatearMoneda(tarjetaOrigen.getSaldo()) + ")");
            System.out.println("Hacia: " + tarjetaDestino.getNumeroTarjeta() + 
                             " (Saldo actual: " + Formateador.formatearMoneda(tarjetaDestino.getSaldo()) + ")");
            System.out.println("Cantidad: " + Formateador.formatearMoneda(cantidad));
            System.out.println("Comisión: " + Formateador.formatearMoneda(comision));
            System.out.println("Total a descontar: " + Formateador.formatearMoneda(totalConComision));
            
            if (Utilidades.leerBooleano("\n¿Desea confirmar esta transferencia?")) {
                transferenciaServicio.transferirInterbancaria(idTarjetaOrigen, idTarjetaDestino, 
                                                             cantidad, comision, "Transferencia interbancaria");
                System.out.println("\n✓ Transferencia interbancaria completada exitosamente");
                
                tarjetaOrigen = tarjetaServicio.obtenerTarjetaPorId(idTarjetaOrigen);
                tarjetaDestino = tarjetaServicio.obtenerTarjetaPorId(idTarjetaDestino);
                
                System.out.println("\nNuevos saldos:");
                System.out.println("Origen: " + Formateador.formatearMoneda(tarjetaOrigen.getSaldo()));
                System.out.println("Destino: " + Formateador.formatearMoneda(tarjetaDestino.getSaldo()));
            }
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void verMovimientosTarjeta() {
        Formateador.imprimirSeparador();
        int idTarjeta = Utilidades.leerEntero("Ingrese el ID de la tarjeta: ");
        
        try {
            List<Movimientos> movimientos = movimientoServicio.obtenerMovimientosTarjeta(idTarjeta);
            
            if (movimientos.isEmpty()) {
                System.out.println("La tarjeta no tiene movimientos registrados");
                return;
            }
            
            mostrarListaMovimientos(movimientos, "MOVIMIENTOS DE LA TARJETA");
            
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void verTodosMovimientos() {
        try {
            List<Movimientos> movimientos = movimientoServicio.obtenerTodosMovimientos();
            
            if (movimientos.isEmpty()) {
                System.out.println("No hay movimientos registrados");
                return;
            }
            
            mostrarListaMovimientos(movimientos, "TODOS LOS MOVIMIENTOS");
            
        } catch (ErrorBancario e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void mostrarListaMovimientos(List<Movimientos> movimientos, String titulo) {
        System.out.println("\n");
        Formateador.imprimirSeparadorGrande();
        System.out.println(titulo);
        Formateador.imprimirSeparadorGrande();
        
        for (Movimientos movimiento : movimientos) {
            String tipo = movimiento.getTipo().equalsIgnoreCase("credito") ? "CRÉDITO" : "DÉBITO";
            System.out.printf("ID: %d | Tarjeta: %d | Tipo: %s | Cantidad: %s | Fecha: %s%n", 
                            movimiento.getIdMovimiento(),
                            movimiento.getIdTarjeta(),
                            tipo,
                            Formateador.formatearMoneda(movimiento.getCantidad()),
                            Formateador.formatearFecha(movimiento.getFechaMovimiento()));
            System.out.println("   Descripción: " + movimiento.getDescripcion());
        }
    }
}