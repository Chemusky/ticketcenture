package es.ticketcenture.daos;

import java.util.List;
import es.ticketcenture.entities.TipoAsiento;

public interface ITipoAsientoDAO {

    List<TipoAsiento> buscarTodos();

    TipoAsiento buscarPorId(Integer idTipoAsiento);
}
