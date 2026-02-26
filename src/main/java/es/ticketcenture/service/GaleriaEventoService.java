package es.ticketcenture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ticketcenture.daos.IGaleriaEventoDAO;
import es.ticketcenture.entities.GaleriaEvento;

@Service
@Transactional
public class GaleriaEventoService {

    private final IGaleriaEventoDAO galeriaEventoDAO;

    @Autowired
    public GaleriaEventoService(IGaleriaEventoDAO galeriaEventoDAO) {
        this.galeriaEventoDAO = galeriaEventoDAO;
    }

    public void insertar(GaleriaEvento imagen) {
        galeriaEventoDAO.insertar(imagen);
    }

    @Transactional(readOnly = true)
    public GaleriaEvento buscarPorId(Integer idImagen) {
        return galeriaEventoDAO.buscarPorId(idImagen);
    }

    @Transactional(readOnly = true)
    public List<GaleriaEvento> buscarPorEvento(Integer idEvento) {
        return galeriaEventoDAO.buscarPorEvento(idEvento);
    }
}
