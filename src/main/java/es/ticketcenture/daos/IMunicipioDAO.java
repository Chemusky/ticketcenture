package es.ticketcenture.daos;

import java.util.List;
import es.ticketcenture.entities.Municipio;

public interface IMunicipioDAO {

    List<Municipio> buscarTodos();

    Municipio buscarPorId(Integer idMunicipio);
}
