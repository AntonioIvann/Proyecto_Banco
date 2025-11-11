import UI.MenuPrincipal;
import Config.JDBC;

/**
 * Clase principal para ejecutar el Sistema Bancario
 */
public class App {
    public static void main(String[] args) {
        try {
            // Verificar conexión a la base de datos
            JDBC.obtenerConexion();
            System.out.println("Conexión a base de datos establecida exitosamente");
            
            // Iniciar menú principal
            MenuPrincipal menuPrincipal = new MenuPrincipal();
            menuPrincipal.mostrar();
            
            // Cerrar conexión
            JDBC.cerrarConexion();
            System.out.println("Conexión cerrada");
            
        } catch (Exception e) {
            System.out.println("Error al iniciar el sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}