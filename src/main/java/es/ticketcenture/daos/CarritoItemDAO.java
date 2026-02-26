package es.ticketcenture.daos;
 
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
 
import es.ticketcenture.entities.CarritoItem;
 
 
/**
* En esta clase gestionaremos las operaciones contra la bbdd
* para la entidad CarritoItem
*
*/
@Repository
public class CarritoItemDAO implements ICarritoItemDAO {
 
    private final SessionFactory sessionFactory;
 
    /**
     * @param sessionFactory
     */
    @Autowired
    public CarritoItemDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    /**
	 * Gestiona la sesion
     * @return
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
 
    /**
     * Inserta el Item en el carrito
     */
    @Override
    public void insertar(CarritoItem item) {
        getSession().persist(item);
    }
 
    
    /**
     * Elimina un Item en el carrito
     */
	@Override
	public void eliminar(CarritoItem item) {
		getSession().remove(item);
	}
 
	
	/**
	 * Busca un CarritoItem por id
	 */
	@Override
	public CarritoItem buscarPorId(Integer idCarritoItem) {
		return getSession().get(CarritoItem.class, idCarritoItem);
	}

	@Override
	public void actualizar(CarritoItem item) {
		getSession().merge(item);
		
	}


}