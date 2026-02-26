package es.ticketcenture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ticketcenture.daos.ITipoAsientoDAO;
import es.ticketcenture.entities.TipoAsiento;

@Service
@Transactional(readOnly = true)
public class TipoAsientoService {

    private final ITipoAsientoDAO tipoAsientoDAO;

    @Autowired
    public TipoAsientoService(ITipoAsientoDAO tipoAsientoDAO) {
        this.tipoAsientoDAO = tipoAsientoDAO;
    }

    public List<TipoAsiento> buscarTodos() {
        return tipoAsientoDAO.buscarTodos();
    }

    public TipoAsiento buscarPorId(Integer idTipoAsiento) {
        return tipoAsientoDAO.buscarPorId(idTipoAsiento);
    }
}
