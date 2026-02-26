package es.ticketcenture.daos;

import java.util.List;
import es.ticketcenture.entities.GaleriaGrupo;

public interface IGaleriaDAO {

    void insertar(GaleriaGrupo imagen);

    void eliminar(GaleriaGrupo imagen);

    GaleriaGrupo buscarPorId(Integer id);

    List<GaleriaGrupo> buscarPorGrupo(Integer idGrupo);
}
