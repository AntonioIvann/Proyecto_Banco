package Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Clase que representa una Tarjeta Bancaria
 */
public class Tarjetas {
    private int idTarjeta;
    private int idCliente;
    private int idBanco;
    private String numeroTarjeta;
    private BigDecimal saldo;
    private LocalDateTime fechaCreacion;
    private boolean activa;

    public Tarjetas() {
    }

    public Tarjetas(int idCliente, int idBanco, String numeroTarjeta) {
        this.idCliente = idCliente;
        this.idBanco = idBanco;
        this.numeroTarjeta = numeroTarjeta;
        this.saldo = BigDecimal.ZERO;
        this.activa = true;
    }

    public Tarjetas(int idTarjeta, int idCliente, int idBanco, String numeroTarjeta, 
                   BigDecimal saldo, LocalDateTime fechaCreacion, boolean activa) {
        this.idTarjeta = idTarjeta;
        this.idCliente = idCliente;
        this.idBanco = idBanco;
        this.numeroTarjeta = numeroTarjeta;
        this.saldo = saldo;
        this.fechaCreacion = fechaCreacion;
        this.activa = activa;
    }

    // Getters y Setters
    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    @Override
    public String toString() {
        return "Tarjeta{" +
                "idTarjeta=" + idTarjeta +
                ", numeroTarjeta='" + numeroTarjeta + '\'' +
                ", saldo=" + saldo +
                ", activa=" + activa +
                '}';
    }
}