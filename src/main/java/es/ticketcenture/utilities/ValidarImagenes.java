package es.ticketcenture.utilities;

import org.springframework.web.multipart.MultipartFile;


/**
 * Clase de utilidades para validar acciones relacionadas con grupos musicales.
 * 
 */
public class ValidarImagenes {

    
    /**
     * Valida que la imagen principal no sea nula ni vacía
     * @param imagen
     * @return
     */
    public static boolean imagenPrincipalValida(MultipartFile imagen) {
        if (imagen == null || imagen.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Comprueba que el archivo subido sea una imagen válida.
     * @param imagen
     * @return
     */
    public static boolean esImagenValida(MultipartFile imagen) {
 
        String tipo = imagen.getContentType();

        if (tipo == null) {
            return false;
        }

        return tipo.equals("image/jpeg") ||
               tipo.equals("image/png") ||
               tipo.equals("image/jpg");
    }

    /**
     * Comprueba que el tamaño de la imagen no supere el máximo permitido.
     * @param imagen
     * @param maxBytes
     * @return
     */
    public static boolean tamanoValido(MultipartFile imagen, long maxBytes) {
 
    	return imagen.getSize() <= maxBytes;
    
    }

}
