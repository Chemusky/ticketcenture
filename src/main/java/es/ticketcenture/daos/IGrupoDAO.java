package es.ticketcenture.daos;

import java.util.List;
import es.ticketcenture.entities.Grupo;

public interface IGrupoDAO {

	void insertar(Grupo grupo);

	void actualizar(Grupo grupo);

	Grupo buscarPorId(Integer id);

	void eliminar(Grupo grupo);

	List<Grupo> buscarTodos();

	List<Grupo> buscarActivos();

	List<Grupo> buscarPorNombre(String nombre);

	List<Grupo> buscarPorGenero(Integer idGenero);

	List<Grupo> buscarPaginado(int offset, int limit);

	long contarTotal();

	List<Grupo> buscarConFiltros(String nombre, Integer idGenero, Integer idUsuarioFavoritos, Boolean activo,
			int offset, int limit);

	long contarConFiltros(String nombre, Integer idGenero, Integer idUsuarioFavoritos, Boolean activo);

	Grupo buscarPorIdConFavoritos(Integer id);
	
	List<Grupo> obtenerFavoritosPorUsuario(Integer idUsuario);

	Grupo buscarPorIdConGaleria(Integer idGrupo);

}
