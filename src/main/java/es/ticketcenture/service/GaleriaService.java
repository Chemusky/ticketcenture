package es.ticketcenture.service;

import es.ticketcenture.daos.GaleriaDAO;
import es.ticketcenture.daos.GrupoDAO;
import es.ticketcenture.entities.GaleriaGrupo;
import es.ticketcenture.entities.Grupo;
import es.ticketcenture.entities.Usuario;
import es.ticketcenture.utilities.Acciones;
import es.ticketcenture.utilities.MensajesError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class GaleriaService {

    private final GaleriaDAO galeriaDAO;
    private final GrupoDAO grupoDAO;
    private final ActividadService actividadService;

    @Autowired
    public GaleriaService(GaleriaDAO galeriaDAO, GrupoDAO grupoDAO, ActividadService actividadService) {
        this.galeriaDAO = galeriaDAO;
        this.grupoDAO = grupoDAO;
        this.actividadService = actividadService;
    }

    private void validarImagen(byte[] imagen) {
        if (imagen == null || imagen.length == 0) {
            throw new IllegalArgumentException(MensajesError.IMAGEN_VACIA);
        }
        int maxSize = 5 * 1024 * 1024;
        if (imagen.length > maxSize) {
            throw new IllegalArgumentException(MensajesError.IMAGEN_TAMANO_EXCESIVO);
        }
    }

    @Transactional
    public void agregarImagen(Integer idGrupo, byte[] imagen, Usuario usuario, HttpServletRequest request) {

        if (usuario == null) {
            throw new IllegalArgumentException(MensajesError.USUARIO_NO_ENCONTRADO);
        }

        Grupo grupo = grupoDAO.buscarPorId(idGrupo);
        if (grupo == null) {
            throw new IllegalArgumentException(MensajesError.GRUPO_NO_ENCONTRADO);
        }

        if (!grupo.isActivo()) {
            throw new IllegalArgumentException(MensajesError.GRUPO_NO_ENCONTRADO);
        }

        validarImagen(imagen);

        GaleriaGrupo nuevaImagen = new GaleriaGrupo(grupo, imagen);
        galeriaDAO.insertar(nuevaImagen);

        actividadService.insertarActividad(
                usuario,
                Acciones.AGREGAR_IMAGEN_GALERIA,
                request.getRemoteAddr(),
                "Imagen añadida a la galería del grupo: " + grupo.getNombreGrupo()
        );
    }

    @Transactional
    public void eliminarImagen(Integer idImagen, Usuario usuario, HttpServletRequest request) {

        if (usuario == null) {
            throw new IllegalArgumentException(MensajesError.USUARIO_NO_ENCONTRADO);
        }

        GaleriaGrupo imagen = galeriaDAO.buscarPorId(idImagen);
        if (imagen == null) {
            throw new IllegalArgumentException(MensajesError.IMAGEN_NO_ENCONTRADA);
        }

        galeriaDAO.eliminar(imagen);

        actividadService.insertarActividad(
                usuario,
                Acciones.ELIMINAR_IMAGEN_GALERIA,
                request.getRemoteAddr(),
                "Imagen eliminada del grupo: " + imagen.getGrupo().getNombreGrupo()
        );
    }

    @Transactional(readOnly = true)
    public GaleriaGrupo buscarPorId(Integer idImagen) {
        return galeriaDAO.buscarPorId(idImagen);
    }

    @Transactional(readOnly = true)
    public List<GaleriaGrupo> buscarPorGrupo(Integer idGrupo) {
        return galeriaDAO.buscarPorGrupo(idGrupo);
    }
}
