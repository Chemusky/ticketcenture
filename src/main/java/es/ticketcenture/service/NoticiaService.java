package es.ticketcenture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ticketcenture.daos.INoticiaDAO;
import es.ticketcenture.entities.Grupo;
import es.ticketcenture.entities.Noticia;

/**
 * Servicio que implementa la logica de negocio de Noticias.
 *
 */
@Service
@Transactional
public class NoticiaService {

	/** El DAO de Noticia */
	private final INoticiaDAO noticiaDAO;

	/**
	 * Creamos una instancia de NoticiaService
	 * 
	 * @param noticiaDAO
	 */
	@Autowired
	public NoticiaService(INoticiaDAO noticiaDAO) {
		this.noticiaDAO = noticiaDAO;
	}

	/**
	 * Metodo para insertar la noticia
	 * 
	 * @param noticia
	 */
	public void insertar(Noticia noticia) {
		noticiaDAO.insertar(noticia);
	}

	/**
	 * Metodo para actualizar la noticia
	 * 
	 * @param noticia
	 */
	public void actualizar(Noticia noticia) {
		noticiaDAO.actualizar(noticia);
	}

	/**
	 * Metodo para eliminar la noticia
	 *
	 * @param id the id
	 */
	public void eliminar(Integer id) {
		Noticia noticia = noticiaDAO.buscarPorId(id);
		if (noticia != null) {
			noticiaDAO.eliminar(noticia);
		}
	}

	/**
	 * Metodo para buscar por Id
	 * 
	 * @param id
	 * @return
	 */
	public Noticia buscarPorId(Integer id) {
		return noticiaDAO.buscarPorId(id);
	}

	/**
	 * Metodo que devuelve un listado de todas las noticias
	 * 
	 * @return
	 */
	public List<Noticia> buscarTodos() {
		return noticiaDAO.buscarTodos();
	}

	/**
	 * Metodo para mostrar el listado de noticias con paginación
	 * 
	 * @param pagina
	 * @param tamanoPagina
	 * @return
	 */
	public List<Noticia> buscarPaginado(int pagina, int tamanoPagina) {
		int offset = (pagina - 1) * tamanoPagina;
		return noticiaDAO.buscarPaginado(offset, tamanoPagina);
	}

	/**
	 * Metodo para contar el paginado
	 * 
	 * @return
	 */
	public long contarPaginado() {
		return noticiaDAO.contarPaginado();
	}

	/**
	 * Metodo para buscar las noticias con filtro
	 * 
	 * @param titulo
	 * @param idGrupo
	 * @param publicado
	 * @param pagina
	 * @param tamanoPagina
	 * @return
	 */
	public List<Noticia> buscarConFiltros(String titulo, Integer idGrupo, Boolean publicado, String fechaPublicacion,
			int pagina, int tamanoPagina) {

		int offset = (pagina - 1) * tamanoPagina;
		return noticiaDAO.buscarConFiltros(titulo, idGrupo, publicado, fechaPublicacion, offset, tamanoPagina);

	}

	/**
	 * Metodo para contar con los filtros
	 * 
	 * @param titulo
	 * @param idGrupo
	 * @param publicado
	 * @return
	 */
	public long contarConFiltros(String titulo, Integer idGrupo, Boolean publicado) {
		return noticiaDAO.contarConFiltros(titulo, idGrupo, publicado);
	}

	/**
	 * Metodo para devolver una lista de las noticias de las ultimas 24 horas de los
	 * grupos favoritos del usuario en sesion
	 * 
	 * @param idUsuario
	 * @return
	 */
	public List<Noticia> buscarNoticiasRecientesFavoritos(Integer idUsuario) {
		return noticiaDAO.buscarNoticiasRecientesFavoritos(idUsuario);
	}

	/**
	 * Método para recuperar las últimas 5 noticias publicadas
	 *
	 * @return lista de noticias ordenadas por fechaPublicacion DESC
	 */
	public List<Noticia> obtenerUltimasNoticias() {
		return noticiaDAO.buscarUltimas5();
	}
	
	
	/**
	 * Metodo que devuelve un listado de todas las noticias PUBLICADAS
	 * 
	 * @return
	 */
	public List<Noticia> buscarTodosPublicados() {
		return noticiaDAO.buscarTodosPublicados();
	}

}
