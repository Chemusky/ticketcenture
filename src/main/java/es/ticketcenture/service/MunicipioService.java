package es.ticketcenture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ticketcenture.daos.IMunicipioDAO;
import es.ticketcenture.entities.Municipio;

@Service
@Transactional(readOnly = true)
public class MunicipioService {

    private final IMunicipioDAO municipioDAO;

    @Autowired
    public MunicipioService(IMunicipioDAO municipioDAO) {
        this.municipioDAO = municipioDAO;
    }

    public List<Municipio> buscarTodos() {
        return municipioDAO.buscarTodos();
    }

    public Municipio buscarPorId(Integer idMunicipio) {
        return municipioDAO.buscarPorId(idMunicipio);
    }
}
