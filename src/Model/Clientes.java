package Model;

/**
 * Clase que representa un Cliente
 */
public class Clientes {
    private int idCliente;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombre;

    public Clientes() {
    }

    public Clientes(String apellidoPaterno, String apellidoMaterno, String nombre) {
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.nombre = nombre;
    }

    public Clientes(int idCliente, String apellidoPaterno, String apellidoMaterno, String nombre) {
        this.idCliente = idCliente;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.nombre = nombre;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCompleto() {
        return apellidoPaterno + " " + apellidoMaterno + " " + nombre;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}