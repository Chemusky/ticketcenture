package es.ticketcenture.daos;

import java.util.List;
import es.ticketcenture.entities.Noticia;

public interface INoticiaDAO {

    // CRUD básico
    void insertar(Noticia noticia);

    void actualizar(Noticia noticia);

    void eliminar(Noticia noticia);

    Noticia buscarPorId(Integer id);

    List<Noticia> buscarTodos();

    // Paginación
    List<Noticia> buscarPaginado(int offset, int limit);

    long contarPaginado();

    // Filtros + paginación
    List<Noticia> buscarConFiltros(String titulo, Integer idGrupo, Boolean publicado,
            String fechaPublicacion, int offset, int limit);

    long contarConFiltros(String titulo, Integer idGrupo, Boolean publicado);
    
    /**
     * Método para buscar los últimas 5 noticias más recientes
     * @return
     */
    List<Noticia> buscarUltimas5();
    
    
    /**
     * Método para buscar las noticias de las ultimas 24horas de los grupos favoritos
     * del usuario en sesion
     * @param idUsuario
     * @return
     */
    List<Noticia> buscarNoticiasRecientesFavoritos(Integer idUsuario);
    
    /**
     * Metodo para recuperar todas las noticias PUBLICADAS
     * @return
     */
    List<Noticia> buscarTodosPublicados();
    
    
    
    
    
}
