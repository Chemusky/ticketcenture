package es.ticketcenture.daos;

import java.util.List;

import es.ticketcenture.entities.Evento;

public interface IEventoDAO {

	void insertar(Evento evento);

	void actualizar(Evento evento);

	void eliminar(Evento evento);
	
	Evento buscarPorId(Integer id);

	List<Evento> buscarTodos();

	List<Evento> buscarPorNombre(String nombre);

	List<Evento> buscarPaginado(int offset, int limit);

	long contarTotal();

	List<Evento> buscarConFiltros(Integer municipio, Integer grupo, String fecha, Boolean activo, int offset,
			int limit);

	long contarConFiltros(Integer municipio, Integer grupo, String fecha, Boolean activo);
	
	public List<Evento> buscarProximosEventos(int limite);

	List<Evento> buscarEventosFuturosPorGrupo(Integer idGrupo);

}
