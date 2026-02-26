package es.ticketcenture.daos;

import java.util.List;
import es.ticketcenture.entities.PromocionEvento;

public interface IPromocionEventoDAO {

    void insertar(PromocionEvento promocion);

    void actualizar(PromocionEvento promocion);

    void eliminar(PromocionEvento promocion);

    PromocionEvento buscarPorId(Integer id);

    List<PromocionEvento> buscarTodos();

    List<PromocionEvento> buscarPorCodigo(String codigo);
    
    List<PromocionEvento> buscarPorEventoYCodigo(Integer idEvento, String codigo);

    boolean existeSolapamientoPromocion(PromocionEvento promocion);
}
