package Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Clase que representa un Movimiento Bancario
 */
public class Movimientos {
    private int idMovimiento;
    private int idTarjeta;
    private BigDecimal cantidad;
    private String tipo; // 'credito' o 'debito'
    private String descripcion;
    private LocalDateTime fechaMovimiento;

    public Movimientos() {
    }

    public Movimientos(int idTarjeta, BigDecimal cantidad, String tipo, String descripcion) {
        this.idTarjeta = idTarjeta;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public Movimientos(int idMovimiento, int idTarjeta, BigDecimal cantidad, String tipo, 
                     String descripcion, LocalDateTime fechaMovimiento) {
        this.idMovimiento = idMovimiento;
        this.idTarjeta = idTarjeta;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechaMovimiento = fechaMovimiento;
    }

    // Getters y Setters
    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(LocalDateTime fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    @Override
    public String toString() {
        return "Movimiento{" +
                "idMovimiento=" + idMovimiento +
                ", cantidad=" + cantidad +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaMovimiento=" + fechaMovimiento +
                '}';
    }
}