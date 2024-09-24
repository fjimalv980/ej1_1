package es.iescastillodeluna.ad;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa la información de un archivo.
 */
public class ArchivoInfo {
    private final Path path;
    private final BasicFileAttributes attrs;
    private final UserPrincipal owner;

    /**
     * Constructor que inicializa la información del archivo.
     * @param path Path del archivo
     * @throws IOException Si ocurre un error al leer los atributos del archivo
     */
    public ArchivoInfo(Path path) throws IOException {
        this.path = path;
        this.attrs = Files.readAttributes(path, BasicFileAttributes.class);
        FileOwnerAttributeView ownerView = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
        this.owner = ownerView.getOwner();
    }

    /**
     * Formatea la información del archivo como una cadena.
     * @return String con la información formateada del archivo
     */
    public String formatearInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(path.getFileName()).append("\n");

        if (Files.isDirectory(path)) {
            sb.append("Tipo: Directorio\n");
        } else {
            LocalDateTime fechaModificacion = LocalDateTime.ofInstant(attrs.lastModifiedTime().toInstant(), ZoneId.systemDefault());
            sb.append("Fecha de modificación: ").append(fechaModificacion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("\n");
            sb.append("Propietario: ").append(owner.getName()).append("\n");
            sb.append("Tamaño: ").append(formatearTamaño(attrs.size())).append("\n");
        }

        return sb.toString();
    }

    /**
     * Formatea el tamaño del archivo en la unidad más apropiada.
     * @param bytes Tamaño del archivo en bytes
     * @return String con el tamaño formateado
     */
    private String formatearTamaño(long bytes) {
        final String[] unidades = {"B", "KB", "MB", "GB", "TB"};
        int unidad = 0;
        double tamaño = bytes;

        while (tamaño >= 1024 && unidad < unidades.length - 1) {
            tamaño /= 1024;
            unidad++;
        }

        return String.format("%.2f %s", tamaño, unidades[unidad]);
    }
}