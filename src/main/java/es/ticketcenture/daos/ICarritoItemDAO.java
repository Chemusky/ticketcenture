package es.ticketcenture.daos;

import es.ticketcenture.entities.CarritoItem;

public interface ICarritoItemDAO {

    void insertar(CarritoItem item);
    
    void eliminar(CarritoItem item);
    
    CarritoItem buscarPorId(Integer idCarritoItem);
    
    void actualizar(CarritoItem item);
}
