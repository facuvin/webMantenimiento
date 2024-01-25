package uy.edu.bios.obligatorio.obligatoriospringboot.utilidades;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UtilidadesArchivos {

    public static void guardarComoImagen(byte[] datos, String directorio, String nombreArchivo, String formato)
            throws IOException {
        BufferedImage imagen = ImageIO.read(new ByteArrayInputStream(datos));

        File archivo = new File(directorio, nombreArchivo + "." + formato.toLowerCase());
        archivo.createNewFile();

        ImageIO.write(imagen, formato.toLowerCase(), archivo);
    }

    public static void eliminarArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);

        archivo.delete();
    }

}
