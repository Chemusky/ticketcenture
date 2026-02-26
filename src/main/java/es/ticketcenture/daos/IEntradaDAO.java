package es.ticketcenture.daos;

import es.ticketcenture.entities.Entrada;

/**
 * Interfaz para EntradaDAO
 */
public interface IEntradaDAO {

    /**
     * Buscar entrada por id.
     *
     * @param idEntrada the id entrada
     * @return the entrada
     */
    Entrada buscarPorId(Integer idEntrada);

    /**
     * Actualizar estado / reserva / usuario
     *
     * @param entrada the entrada
     */
    void actualizar(Entrada entrada);
    
    /**
     * Buscar entrada por evento, tipo, fila y asiento
     * @param idEvento
     * @param idTipo
     * @param fila
     * @param asiento
     * @return
     */
    Entrada buscarPorEventoTipoFilaAsiento(Integer idEvento, Integer idTipo, Integer fila, Integer asiento);

}
