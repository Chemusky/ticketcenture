package es.ticketcenture.validations;

import es.ticketcenture.daos.IPromocionEventoDAO;
import es.ticketcenture.entities.PromocionEvento;
import es.ticketcenture.utilities.MensajesError;

public class CodigosPromocionalesValidacion {

	/**
	 * Valida que la promoción NO se solape con otra promoción del mismo evento en
	 * el mismo periodo.
	 *
	 * @param promo La promoción que se quiere guardar
	 * @param dao   El DAO que permite consultar la base de datos
	 */

	// Llama al DAO como parametro porque es estática, no podemos hacer un
	// @autowired dentro
	// y entonces no podemos inyectar el dao automaticamente
	public static void validarSolapamientoPromocion(PromocionEvento promo, IPromocionEventoDAO dao) {

		// 1. Validación básica de fechas
		if (promo.getValidoDesde() == null || promo.getValidoHasta() == null) {
			throw new IllegalArgumentException(MensajesError.ERROR_FECHAS_NULAS);
		}

		if (!promo.getValidoDesde().isBefore(promo.getValidoHasta())) {
			throw new IllegalArgumentException(MensajesError.ERROR_ORDEN_FECHAS);
		}

		// 2. Llamamos al DAO para comprobar si existe solapamiento
		boolean solapa = dao.existeSolapamientoPromocion(promo);

		// 3. Si solapa → lanzamos excepción
		if (solapa) {
			throw new IllegalArgumentException(MensajesError.ERROR_PROMOCION_COINCIDENTE);
		}
	}

	/**
	 * Valida que el código promocional NO esté repetido dentro del mismo evento.
	 * (Ahora permitido repetir código entre eventos distintos)
	 */
	public static void validarCodigoUnicoPorEvento(PromocionEvento promo, IPromocionEventoDAO dao) {
		var existentes = dao.buscarPorEventoYCodigo(promo.getEvento().getIdEvento(), promo.getCodigo());
		// Si existe otra promoción con el mismo código en el mismo evento
		if (!existentes.isEmpty())

		{
			// Si estamos editando, permitir que sea la misma promoción
			if (promo.getIdPromocion() == null || !existentes.get(0).getIdPromocion().equals(promo.getIdPromocion())) {
				throw new IllegalArgumentException(MensajesError.ERROR_CODIGO_UNICO_EVENTO);
			}
		}
	}
	
	
	/**
	 * Valida que el código promocional exista para el evento indicado.
	 * Si no existe, lanza excepción.
	 * Necesita la id del evento y el string del codigo
	 */
	public static void validarCodigoPromocionalExiste(Integer idEvento, String codigo, IPromocionEventoDAO dao) {

	    // Si el usuario no ha introducido código, no validamos nada
	    if (codigo == null || codigo.trim().isEmpty()) {
	        return;
	    }

	    // Buscamos promociones con ese código para ese evento
	    var promociones = dao.buscarPorEventoYCodigo(idEvento, codigo.trim());

	    // Si no hay ninguna → error
	    if (promociones == null || promociones.isEmpty()) {
	        throw new IllegalArgumentException(MensajesError.ERROR_PROMOCION_NO_EXISTE);
	    }
	}

	
	
}
