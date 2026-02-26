package es.ticketcenture.daos;

import java.util.List;
import es.ticketcenture.entities.Genero;

public interface IGeneroDAO {

    void insertar(Genero genero);

    void actualizar(Genero genero);

    Genero buscarPorId(Integer id);

    List<Genero> buscarTodos();

	void eliminar(Genero genero);

	boolean tieneGruposAsociados(Integer idGenero);
}
