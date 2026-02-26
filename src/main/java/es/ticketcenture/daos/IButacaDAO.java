package es.ticketcenture.daos;

import es.ticketcenture.entities.Butaca;

/**
 * Interfaz para Butaca
 */
public interface IButacaDAO {
	
    /**
     * @param idEvento
     * @param idTipo
     * @return
     */
    Butaca buscarPorEventoYTipo(Integer idEvento, Integer idTipo);
}
