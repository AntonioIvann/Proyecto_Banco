package Model;

/**
 * Clase que representa un Banco
 */
public class Banco {
    private int idBanco;
    private String nombre;

    public Banco() {
    }

    public Banco(String nombre) {
        this.nombre = nombre;
    }

    public Banco(int idBanco, String nombre) {
        this.idBanco = idBanco;
        this.nombre = nombre;
    }

    public int getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Banco{" +
                "idBanco=" + idBanco +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}