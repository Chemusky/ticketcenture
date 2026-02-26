package es.ticketcenture.daos;

import java.util.List;
import es.ticketcenture.entities.DescuentoUsuario;
import es.ticketcenture.entities.TipoUsuario;

public interface IDescuentoUsuarioDAO {

	void insertar(DescuentoUsuario descuento);

	void actualizar(DescuentoUsuario descuento);

	void eliminar(DescuentoUsuario descuento);

	DescuentoUsuario buscarPorId(Integer id);

	List<DescuentoUsuario> buscarTodos();

	List<DescuentoUsuario> buscarPorTipoUsuario(TipoUsuario tipoUsuario);

	boolean existeSolapamientoDescuento(DescuentoUsuario descuento);
	
	DescuentoUsuario obtenerDescuentoUsuario(TipoUsuario tipoUsuario);
}
