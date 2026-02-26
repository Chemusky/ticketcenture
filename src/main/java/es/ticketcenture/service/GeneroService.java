package es.ticketcenture.service;

import es.ticketcenture.daos.GeneroDAO;
import es.ticketcenture.entities.Genero;
import es.ticketcenture.utilities.MensajesError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GeneroService {

    private final GeneroDAO generoDAO;

    @Autowired
    public GeneroService(GeneroDAO generoDAO) {
        this.generoDAO = generoDAO;
    }

    @Transactional(readOnly = true)
    public Genero buscarPorId(Integer idGenero) {
        return generoDAO.buscarPorId(idGenero);
    }

    @Transactional(readOnly = true)
    public List<Genero> buscarTodos() {
        return generoDAO.buscarTodos();
    }

    @Transactional
    public void insertar(Genero genero) {

        validarGenero(genero, false);

        generoDAO.insertar(genero);
    }


    @Transactional
    public void actualizar(Genero genero) {

        validarGenero(genero, true);

        generoDAO.actualizar(genero);
    }

    @Transactional
    public void eliminar(Integer idGenero) {

        Genero g = generoDAO.buscarPorId(idGenero);

        if (g == null) {
            throw new IllegalArgumentException(MensajesError.GENERO_NO_ENCONTRADO);
        }
        
        if (generoDAO.tieneGruposAsociados(idGenero)) {
        	throw new IllegalArgumentException(MensajesError.GENERO_NO_ELIMINABLE); 
        }

        generoDAO.eliminar(g);
    }

    /**
     * Valida un género tanto para creación como para edición.
     *
     * @param genero objeto a validar
     * @param esEdicion true si es edición, false si es creación
     */
    private void validarGenero(Genero genero, boolean esEdicion) {

        if (genero == null) {
            throw new IllegalArgumentException(MensajesError.GENERO_NULO);
        }

        String nombre = genero.getNombreGenero();

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(MensajesError.GENERO_NOMBRE_VACIO);
        }

        if (nombre.length() < 3) {
            throw new IllegalArgumentException(MensajesError.GENERO_NOMBRE_CORTO);
        }

        if (nombre.length() > 50) {
            throw new IllegalArgumentException(MensajesError.GENERO_NOMBRE_LARGO);
        }

        // Validación de duplicado
        boolean existe = generoDAO.buscarTodos().stream()
                .anyMatch(g ->
                        g.getNombreGenero().equalsIgnoreCase(nombre)
                        && (!esEdicion || !g.getIdGenero().equals(genero.getIdGenero()))
                );

        if (existe) {
            throw new IllegalArgumentException(MensajesError.GENERO_DUPLICADO);
        }
    }
}
