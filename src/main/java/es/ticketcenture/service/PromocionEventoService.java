package es.ticketcenture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ticketcenture.daos.IPromocionEventoDAO;
import es.ticketcenture.entities.PromocionEvento;
import es.ticketcenture.validations.CodigosPromocionalesValidacion;

@Service
@Transactional
public class PromocionEventoService {

    private final IPromocionEventoDAO promocionDAO;

    @Autowired
    public PromocionEventoService(IPromocionEventoDAO promocionDAO) {
        this.promocionDAO = promocionDAO;
    }

    public void insertar(PromocionEvento promocion) {

        // Validación: código único por evento
        CodigosPromocionalesValidacion.validarCodigoUnicoPorEvento(promocion, promocionDAO);

        // Validación: solapamiento de fechas
        CodigosPromocionalesValidacion.validarSolapamientoPromocion(promocion, promocionDAO);

        promocionDAO.insertar(promocion);
    }

    public void actualizar(PromocionEvento promocion) {

        // Validación: código único por evento
        CodigosPromocionalesValidacion.validarCodigoUnicoPorEvento(promocion, promocionDAO);

        // Validación: solapamiento de fechas
        CodigosPromocionalesValidacion.validarSolapamientoPromocion(promocion, promocionDAO);

        promocionDAO.actualizar(promocion);
    }

    public void eliminar(PromocionEvento promocion) {
        promocionDAO.eliminar(promocion);
    }

    @Transactional(readOnly = true)
    public PromocionEvento buscarPorId(Integer id) {
        return promocionDAO.buscarPorId(id);
    }

    @Transactional(readOnly = true)
    public List<PromocionEvento> buscarTodos() {
        return promocionDAO.buscarTodos();
    }
}
