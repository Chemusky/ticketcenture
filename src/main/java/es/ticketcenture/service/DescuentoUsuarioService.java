package es.ticketcenture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ticketcenture.daos.IDescuentoUsuarioDAO;
import es.ticketcenture.entities.DescuentoUsuario;
import es.ticketcenture.validations.DescuentosValidacion;

@Service
@Transactional
public class DescuentoUsuarioService {

    private final IDescuentoUsuarioDAO descuentoDAO;

    @Autowired
    public DescuentoUsuarioService(IDescuentoUsuarioDAO descuentoDAO) {
        this.descuentoDAO = descuentoDAO;
    }

    public void insertar(DescuentoUsuario descuento) {

        // Validación completa (fechas + solapamiento)
        DescuentosValidacion.validarSolapamientoDescuento(descuento, descuentoDAO);

        descuentoDAO.insertar(descuento);
    }

    public void actualizar(DescuentoUsuario descuento) {

        // Validación completa (fechas + solapamiento)
        DescuentosValidacion.validarSolapamientoDescuento(descuento, descuentoDAO);

        descuentoDAO.actualizar(descuento);
    }

    public void eliminar(DescuentoUsuario descuento) {
        descuentoDAO.eliminar(descuento);
    }

    @Transactional(readOnly = true)
    public DescuentoUsuario buscarPorId(Integer id) {
        return descuentoDAO.buscarPorId(id);
    }

    @Transactional(readOnly = true)
    public List<DescuentoUsuario> buscarTodos() {
        return descuentoDAO.buscarTodos();
    }
}
