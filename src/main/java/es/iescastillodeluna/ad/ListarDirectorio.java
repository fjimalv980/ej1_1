package es.iescastillodeluna.ad;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Clase que maneja la lógica para listar y filtrar archivos en un directorio.
 */
public class ListarDirectorio {
    private final Path directorio;

    /**
     * Constructor que inicializa el directorio a listar.
     * @param directorio Path del directorio a listar
     */
    public ListarDirectorio(Path directorio) {
        this.directorio = directorio;
    }

    /**
     * Lista los archivos del directorio aplicando filtros y límites.
     * @param maxResultados Número máximo de resultados a devolver (0 para todos)
     * @return Lista de ArchivoInfo con la información de los archivos
     */
    public List<ArchivoInfo> listarArchivos(int maxResultados) {
        try (Stream<Path> archivos = Files.list(directorio)) {
            return archivos
                .filter(this::noEsOcultoNiEnlace)
                .limit(maxResultados == 0 ? Long.MAX_VALUE : maxResultados)
                .map(this::crearArchivoInfo)
                .filter(info -> info != null)
                .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error al listar el directorio: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Verifica si un archivo no es oculto ni un enlace simbólico.
     * @param path Path del archivo a verificar
     * @return true si el archivo no es oculto ni enlace simbólico, false en caso contrario
     */
    private boolean noEsOcultoNiEnlace(Path path) {
        try {
            return !Files.isHidden(path) && !Files.isSymbolicLink(path);
        } catch (IOException e) {
            System.err.println("Error al verificar archivo: " + path);
            return false;
        }
    }

    /**
     * Crea un objeto ArchivoInfo a partir de un Path.
     * @param path Path del archivo
     * @return ArchivoInfo creado o null si ocurre un error
     */
    private ArchivoInfo crearArchivoInfo(Path path) {
        try {
            return new ArchivoInfo(path);
        } catch (IOException e) {
            System.err.println("Error al crear ArchivoInfo para: " + path);
            return null;
        }
    }
}