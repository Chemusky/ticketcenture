package es.ticketcenture.daos;

import java.util.List;
import es.ticketcenture.entities.GaleriaEvento;

public interface IGaleriaEventoDAO {

    void insertar(GaleriaEvento imagen);

    GaleriaEvento buscarPorId(Integer idImagen);

    List<GaleriaEvento> buscarPorEvento(Integer idEvento);
}
