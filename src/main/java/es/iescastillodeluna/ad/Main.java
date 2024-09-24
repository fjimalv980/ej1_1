package es.iescastillodeluna.ad;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal que maneja la interacción con el usuario y muestra los resultados.
 */
public class Main {
    public static void main(String[] args) {
        // Obtiene la ruta del directorio personal del usuario
        Path directorioPersonal = Path.of(System.getProperty("user.home"));
        
        // Crea un scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Solicita al usuario el número máximo de resultados
        System.out.print("Ingrese el número máximo de resultados (0 para todos): ");
        int maxResultados = scanner.nextInt();

        // Crea una instancia de ListarDirectorio con el directorio personal
        ListarDirectorio manager = new ListarDirectorio(directorioPersonal);
        
        // Obtiene la lista de archivos
        List<ArchivoInfo> archivos = manager.listarArchivos(maxResultados);

        // Muestra la información de cada archivo
        for (ArchivoInfo archivo : archivos) {
            System.out.println(archivo.formatearInfo());
        }
    }
}