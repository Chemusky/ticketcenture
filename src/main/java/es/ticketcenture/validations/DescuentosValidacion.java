package es.ticketcenture.validations;

import es.ticketcenture.daos.IDescuentoUsuarioDAO;
import es.ticketcenture.entities.DescuentoUsuario;
import es.ticketcenture.utilities.MensajesError;

public class DescuentosValidacion {

    /**
     * Valida que NO exista otro descuento para el mismo tipo de usuario
     * cuyo periodo se solape con el descuento actual.
     *
     * @param descuento El descuento que se quiere guardar
     * @param dao       El DAO que permite consultar la base de datos
     */
    public static void validarSolapamientoDescuento(
            DescuentoUsuario descuento,
            IDescuentoUsuarioDAO dao) {

        // 1. Validación básica de fechas
        if (descuento.getValidoDesde() == null || descuento.getValidoHasta() == null) {
            throw new IllegalArgumentException(MensajesError.ERROR_FECHAS_NULAS);
        }

        if (!descuento.getValidoDesde().isBefore(descuento.getValidoHasta())) {
            throw new IllegalArgumentException(MensajesError.ERROR_ORDEN_FECHAS);
        }

        // 2. Llamamos al DAO para comprobar si existe solapamiento
        boolean solapa = dao.existeSolapamientoDescuento(descuento);

        // 3. Si solapa → lanzamos excepción
        if (solapa) {
            throw new IllegalArgumentException(
                MensajesError.ERROR_DESCUENTO_COINCIDENTE
            );
        }
    }
}

