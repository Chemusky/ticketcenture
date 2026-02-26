package es.ticketcenture.service;
 
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
 
import javax.servlet.http.HttpServletRequest;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import es.ticketcenture.daos.ICarritoDAO;
import es.ticketcenture.daos.ICarritoItemDAO;
import es.ticketcenture.daos.IDescuentoUsuarioDAO;
import es.ticketcenture.daos.IEntradaDAO;
import es.ticketcenture.daos.IPromocionEventoDAO;
import es.ticketcenture.daos.IUsuarioDAO;
import es.ticketcenture.dto.CompradorEventoDTO;
import es.ticketcenture.daos.IButacaDAO;
import es.ticketcenture.entities.Carrito;
import es.ticketcenture.entities.CarritoItem;
import es.ticketcenture.entities.DescuentoUsuario;
import es.ticketcenture.entities.Entrada;
import es.ticketcenture.entities.EstadoCarrito;
import es.ticketcenture.entities.PromocionEvento;
import es.ticketcenture.entities.Butaca;
import es.ticketcenture.entities.TipoEntrada;
import es.ticketcenture.entities.TipoUsuario;
import es.ticketcenture.entities.Usuario;
import es.ticketcenture.utilities.MensajesError;
import es.ticketcenture.utilities.MessageManager;
import es.ticketcenture.validations.CodigosPromocionalesValidacion;
import es.ticketcenture.validations.EntradasValidacion;
 
/**
* Servicio que implementa la logica de negocio del carrito.
*
*/
@Service
public class CarritoService {
 
	private final ICarritoDAO carritoDAO;
	private final ICarritoItemDAO carritoItemDAO;
	private final IEntradaDAO entradaDAO;
	private final IPromocionEventoDAO promocionEventoDAO;
	private final IButacaDAO butacaDAO;
	private final IUsuarioDAO usuarioDAO;
	private final EntradasValidacion entradasValidacion;
	private final IDescuentoUsuarioDAO descuentoUsuarioDAO;
 
	@Autowired
	public CarritoService(ICarritoDAO carritoDAO, ICarritoItemDAO carritoItemDAO, IEntradaDAO entradaDAO,
			IPromocionEventoDAO promocionEventoDAO, IButacaDAO butacaDAO, IUsuarioDAO usuarioDAO, IDescuentoUsuarioDAO descuentoUsuarioDAO,
			EntradasValidacion entradasValidacion) {
		this.carritoDAO = carritoDAO;
		this.carritoItemDAO = carritoItemDAO;
		this.entradaDAO = entradaDAO;
		this.promocionEventoDAO = promocionEventoDAO;
		this.butacaDAO = butacaDAO;
		this.usuarioDAO = usuarioDAO;
		this.entradasValidacion = entradasValidacion;
		this.descuentoUsuarioDAO = descuentoUsuarioDAO;
	}
 
	/**
	 * Procesa el carrito en la base de datos
	 * 
	 * @param idUsuario
	 * @param idsEntradas
	 * @param codigoPromocion
	 */
 
	@Transactional
	public void procesarCarrito(Integer idUsuario, List<Integer> idsEntradas, String codigoPromocion) {
 
		// 1. Buscar carrito activo del usuario
		Carrito carrito = carritoDAO.buscarCarritoActivoPorUsuario(idUsuario);
 
		if (carrito == null) {
			carrito = new Carrito();
			carrito.setEstado(EstadoCarrito.ACTIVO);
			carrito.setFechaCreacion(LocalDateTime.now());
			carrito.setUsuario(usuarioDAO.buscarPorId(idUsuario));
			carritoDAO.insertar(carrito);
		}
 
		BigDecimal total = BigDecimal.ZERO;
 
		// 2. Procesar cada entrada seleccionada
		for (Integer idEntrada : idsEntradas) {
 
			Entrada entrada = entradaDAO.buscarPorId(idEntrada);
 
			if (entrada == null) {
				throw new RuntimeException(MessageManager.get(MensajesError.ENTRADA_NO_ENCONTRADA) + ": " + idEntrada);
			}
 
			if (entrada.getEstado() != TipoEntrada.DISPONIBLE) {
				throw new RuntimeException(MessageManager.get(MensajesError.ENTRADA_NO_DISPONIBLE) + ": " + idEntrada);
			}
			if (entrada.getEvento().getFechaEvento().isBefore(LocalDateTime.now())) {
				throw new RuntimeException(MessageManager.get(MensajesError.EVENTO_CADUCADO)); 
			}
 
			// Obtener precio real desde BUTACAS
			Butaca butaca = butacaDAO.buscarPorEventoYTipo(entrada.getEvento().getIdEvento(),
					entrada.getTipoAsiento().getIdTipo());
 
			if (butaca == null) {
				throw new RuntimeException(MessageManager.get(MensajesError.BUTACA_NO_CONFIGURADA));
			}
 
			BigDecimal precioEntrada = butaca.getPrecio();
 
			// Reservar entrada
			entrada.setEstado(TipoEntrada.RESERVADA_CARRITO);
			entrada.setReservadaCarrito(LocalDateTime.now());
			entradaDAO.actualizar(entrada);
 
			// Crear item del carrito
			CarritoItem item = new CarritoItem();
			item.setCarrito(carrito);
			item.setEntrada(entrada);
			item.setPrecioEntrada(precioEntrada);
			item.setPrecioTotalEntrada(precioEntrada);
 
			carritoItemDAO.insertar(item);
 
			total = total.add(precioEntrada);
 
			// 3. Validar promoción (dentro del bucle)
			if (codigoPromocion != null && !codigoPromocion.isBlank()) {
 
				List<PromocionEvento> promos = promocionEventoDAO
						.buscarPorEventoYCodigo(entrada.getEvento().getIdEvento(), codigoPromocion);
 
				if (promos != null && !promos.isEmpty()) {
 
					PromocionEvento promo = promos.get(0);
 
					LocalDate hoy = LocalDate.now();
 
					boolean valida = !hoy.isBefore(promo.getValidoDesde()) && !hoy.isAfter(promo.getValidoHasta());
 
					if (valida) {
						total = total.subtract(promo.getDescuentoEuros());
						if (total.compareTo(BigDecimal.ZERO) < 0) {
							total = BigDecimal.ZERO;
						}
					}
				}
			}
		}
 
		// 4. Guardar total final
		carrito.setTotal(total);
		carritoDAO.actualizar(carrito);
	}
 
	/**
	 * 
	 * @param idItemCarrito
	 */
	@Transactional
	public void eliminarItemDelCarrito(Integer idItemCarrito) {
 
		CarritoItem item = carritoItemDAO.buscarPorId(idItemCarrito);
 
		if (item == null) {
			return;
		}
		Carrito carrito = item.getCarrito();
		Entrada entrada = item.getEntrada(); 
		
		// 1. Liberar la entrada 
		entrada.setEstado(TipoEntrada.DISPONIBLE); 
		entrada.setReservadaCarrito(null); 
		entrada.setUsuario(null);
		carrito.getItems().remove(item);
		carrito.recalcularTotal();
		
		//Hay que comprobar si el carrito se queda vacio
		if(carrito.getItems().isEmpty()) {
			//Y si esta vacio se elimina, para que no se vea el enlace en principal
			carritoDAO.eliminarCarrito(carrito);
			
		}
 
	}
 
	/**
	 * Método que añade las entradas al carrito desde el formulario
	 * @param idUsuario
	 * @param idEvento
	 * @param request
	 * @param codigoPromocional
	 * @return un buleano que indica si hay o no entradas añadidas
	 */
	@Transactional
	public boolean anadirEntradasDesdeFormulario(Integer idUsuario, Integer idEvento, 
	                                             HttpServletRequest request,
	                                             String codigoPromocional) {

	    boolean seAnadioAlgo = false;

	    // 1. Obtener carrito activo
	    Carrito carrito = carritoDAO.buscarCarritoActivoPorUsuario(idUsuario);

	    if (carrito == null) {
	        carrito = new Carrito();
	        carrito.setEstado(EstadoCarrito.ACTIVO);
	        carrito.setFechaCreacion(LocalDateTime.now());
	        carrito.setUsuario(usuarioDAO.buscarPorId(idUsuario));
	        carritoDAO.insertar(carrito);
	    }

	    BigDecimal total = carrito.getTotal() != null ? carrito.getTotal() : BigDecimal.ZERO;

	    Set<String> entradasProcesadas = new HashSet<>();

	    // 2. Recorrer las 5 filas del formulario
	    for (int i = 1; i <= 5; i++) {

	        String tipo = request.getParameter("tipoAsiento" + i);
	        String fila = request.getParameter("fila" + i);
	        String asiento = request.getParameter("asiento" + i);

	        // Ignorar selects vacíos
	        if (tipo == null || tipo.isBlank() ||
	            fila == null || fila.isBlank() ||
	            asiento == null || asiento.isBlank()) {
	            continue;
	        }

	        // Evitar duplicados
	        String clave = tipo + "-" + fila + "-" + asiento;
	        if (entradasProcesadas.contains(clave)) {
	            continue;
	        }
	        entradasProcesadas.add(clave);

	        Integer idTipo = Integer.parseInt(tipo);
	        Integer filaNum = Integer.parseInt(fila);
	        Integer asientoNum = Integer.parseInt(asiento);

	        // 3. Buscar la entrada exacta
	        Entrada entrada = entradaDAO.buscarPorEventoTipoFilaAsiento(idEvento, idTipo, filaNum, asientoNum);

	        if (entrada == null) {
	            throw new RuntimeException(MessageManager.get(MensajesError.ENTRADA_NO_ENCONTRADA));
	        }

	        if (entrada.getEstado() != TipoEntrada.DISPONIBLE) {
	            throw new RuntimeException(MessageManager.get(MensajesError.ENTRADA_NO_DISPONIBLE));
	        }
	        
	        if (entrada.getEvento().getFechaEvento().isBefore(LocalDateTime.now())) {
				throw new RuntimeException(MessageManager.get(MensajesError.EVENTO_CADUCADO)); 
			}

	        // 4. Reservar entrada
	        entrada.setEstado(TipoEntrada.RESERVADA_CARRITO);
	        entrada.setReservadaCarrito(LocalDateTime.now());
	        entradaDAO.actualizar(entrada);

	        // 5. Obtener precio
	        Butaca butaca = butacaDAO.buscarPorEventoYTipo(idEvento, idTipo);
	        BigDecimal precio = butaca.getPrecio();

	        // 6. Crear item
	        CarritoItem item = new CarritoItem();
	        item.setCarrito(carrito);
	        item.setEntrada(entrada);
	        item.setPrecioEntrada(precio);
	        item.setPrecioTotalEntrada(precio);

	        carritoItemDAO.insertar(item);

	        // *** IMPORTANTE: añadirlo al carrito para que aparezca en getItems() ***
	        carrito.getItems().add(item);

	        total = total.add(precio);
	        seAnadioAlgo = true;
	    }

	    // 7. Validar código promocional (si existe)
	    CodigosPromocionalesValidacion.validarCodigoPromocionalExiste(
	            idEvento, codigoPromocional, promocionEventoDAO);

	    // 8. Aplicar promoción
	    if (codigoPromocional != null && !codigoPromocional.isBlank()) {

	        List<PromocionEvento> promos =
	                promocionEventoDAO.buscarPorEventoYCodigo(idEvento, codigoPromocional);

	        if (promos != null && !promos.isEmpty()) {

	            PromocionEvento promo = promos.get(0);
	            LocalDate hoy = LocalDate.now();

	            boolean valida = !hoy.isBefore(promo.getValidoDesde()) &&
	                             !hoy.isAfter(promo.getValidoHasta());

	            if (valida) {

	                // Aplicar descuento a cada item del carrito
	                for (CarritoItem item : carrito.getItems()) {

	                    item.setDescuentoPromocion(promo.getDescuentoEuros());

	                    // Recalcular precio final
	                    BigDecimal precioFinal =
	                            item.getPrecioEntrada().subtract(promo.getDescuentoEuros());

	                    if (precioFinal.compareTo(BigDecimal.ZERO) < 0) {
	                        precioFinal = BigDecimal.ZERO;
	                    }

	                    item.setPrecioTotalEntrada(precioFinal);
	                    // No hace falta actualizar: está en estado managed
	                }
	            }
	        }
	    }

	    // Recalcular total del carrito
	    carrito.recalcularTotal();
	    total = carrito.getTotal();

	    return seAnadioAlgo;
	}



	@Transactional(readOnly = true)
	public Carrito obtenerCarritoActivoConTodo(Integer idUsuario) {
 
	    Carrito carrito = carritoDAO.buscarCarritoActivoConTodo(idUsuario);
 
	    if (carrito != null) {
	        // Forzamos carga de direcciones
	        carrito.getUsuario().getDirecciones().size();
	    }
 
	    return carrito;
	}
	
	/**
	 * Confirmar compra.
	 *
	 * @param idUsuario the id usuario
	 * @param direccionEnvio la direccionEnvio del usuario en ese momento
	 * @param direccionFacturacion la direccionFacturacion del usuario en ese momento
	 */
	@Transactional
	public void confirmarCompra(Integer idUsuario, String direccionEnvio, String direccionFacturacion) {

	    // 1. Buscar carrito activo con items y entradas
	    Carrito carrito = carritoDAO.buscarCarritoActivoConTodo(idUsuario);

	    if (carrito == null) {
	        throw new RuntimeException(MessageManager.get(MensajesError.ERROR_CARRITO_NO_ENCONTRADO));
	    }

	    if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
	        throw new RuntimeException(MessageManager.get(MensajesError.ERROR_CARRITO_VACIO));
	    }

	    LocalDateTime haceDiezMin = LocalDateTime.now().minusMinutes(10);

	    // 2. Validar cada entrada del carrito
	    for (CarritoItem item : carrito.getItems()) {

	        Entrada entrada = entradaDAO.buscarPorId(item.getEntrada().getIdEntrada());

	        if (entrada == null) {
	            throw new RuntimeException(MessageManager.get(MensajesError.ENTRADA_NO_ENCONTRADA));
	        }

	        // --- CASO 1: Entrada vendida ---
	        if (entrada.getEstado().equals("vendida")) {
	            throw new RuntimeException(MessageManager.get(MensajesError.ENTRADA_NO_DISPONIBLE));
	        }

	        // --- CASO 2: Entrada reservada hace menos de 10 minutos ---
	        if (entrada.getReservadaCarrito() != null &&
	            entrada.getReservadaCarrito().isAfter(haceDiezMin)) {

	            // Si está reservada hace poco, comprobar si está en OTRO carrito
	            boolean enOtroCarrito = carritoDAO.entradaEnOtroCarritoActivo(
	                    entrada.getIdEntrada(),
	                    carrito.getIdCarrito()
	            );

	            if (enOtroCarrito) {
	                throw new RuntimeException(MessageManager.get(MensajesError.ENTRADA_NO_DISPONIBLE));
	            }

	            // Si NO está en otro carrito → es nuestra → OK
	            continue;
	        }

	        // --- CASO 3: Entrada reservada hace más de 10 minutos (caducada) ---
	        if (entrada.getReservadaCarrito() != null &&
	            entrada.getReservadaCarrito().isBefore(haceDiezMin)) {

	            boolean enOtroCarrito = carritoDAO.entradaEnOtroCarritoActivo(
	                    entrada.getIdEntrada(),
	                    carrito.getIdCarrito()
	            );

	            if (enOtroCarrito) {
	                throw new RuntimeException(MessageManager.get(MensajesError.ENTRADA_NO_DISPONIBLE));
	            }

	            // Si no está en otro carrito → se puede comprar
	        }
	    }

	    // 3. Marcar entradas como vendidas
	    for (CarritoItem item : carrito.getItems()) {
	        Entrada entrada = item.getEntrada();
	        entrada.setEstado(TipoEntrada.VENDIDA);	       
	        entrada.setReservadaCarrito(null);
	        entradaDAO.actualizar(entrada);
	    }

	    // 4. Guardamos las direcciones en el carrito
	    carrito.setDireccionEnvio(direccionEnvio);
	    carrito.setDireccionFacturacion(direccionFacturacion);
	    
	    // 5. Calculamos el subtotal
	    BigDecimal subtotal = carrito.getItems().stream()
	    				.map(CarritoItem::getPrecioTotalEntrada)
	    				.reduce(BigDecimal.ZERO, BigDecimal::add);
	    
	    // 6. Si hay descuento de usuario lo aplicamos...
	    DescuentoUsuario descuentoUsuario = descuentoUsuarioDAO.obtenerDescuentoUsuario( carrito.getUsuario().getTipoUsuario() );
	    
	    if (descuentoUsuario != null) { 
	    	BigDecimal descuentoAplicado = subtotal
	    			.multiply(BigDecimal.valueOf(descuentoUsuario.getDescuentoPorcentaje()))
	    			.divide(BigDecimal.valueOf(100));
	    	BigDecimal totalFinal = subtotal.subtract(descuentoAplicado);
	    	carrito.setDescuentoAplicado(descuentoAplicado);
	    	carrito.setTotal(totalFinal);
	    	
	    } else { 
	    	carrito.setDescuentoAplicado(BigDecimal.ZERO);
	    	carrito.setTotal(subtotal); }
	    
	    // 4. Confirmar carrito
	    carrito.setEstado(EstadoCarrito.CONFIRMADO);
	    carrito.setFechaCompra(LocalDateTime.now());
	    carritoDAO.actualizar(carrito);
	}

	
	/**
	 * Metodo que obtiene el descuento de usuario
	 * @param usuarioSesion
	 * @return
	 */
	@Transactional
	public DescuentoUsuario obtenerDescuentoUsuario(Usuario usuarioSesion) {

	    if (usuarioSesion == null) {
	        return null;
	    }

	    TipoUsuario tipo = usuarioSesion.getTipoUsuario();

	    return descuentoUsuarioDAO.obtenerDescuentoUsuario(tipo);
	}
	
	
	/**
	 * Calcula el total del carrito aplicando el descuento del usuario si existe.
	 *
	 * @param carrito   Carrito del usuario
	 * @param descuento DescuentoUsuario activo (o null si no hay)
	 * @return Total final con descuento aplicado
	 */
	public BigDecimal calcularTotalConDescuento(Carrito carrito, DescuentoUsuario descuento) {

	    // Seguridad: si el carrito es null, devolvemos 0
	    if (carrito == null) {
	        return BigDecimal.ZERO;
	    }

	    // Total original del carrito
	    BigDecimal total = carrito.getTotal();

	    // Si no hay descuento, devolvemos el total normal
	    if (descuento == null) {
	        return total;
	    }

	    // Convertimos el porcentaje a BigDecimal
	    BigDecimal porcentaje = new BigDecimal(descuento.getDescuentoPorcentaje());

	    // Calculamos el factor: 10% → 0.90, 20% → 0.80, etc.
	    BigDecimal factor = BigDecimal.ONE.subtract(porcentaje.divide(new BigDecimal(100)));

	    // Total final con descuento LO FORMATEAMOS PARA QUE SOLO TENGA 2 DECIMALES
	    return total.multiply(factor).setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * Obtiene el histórico de compras (carritos confirmados) de un usuario.
	 *
	 * @param idUsuario ID del usuario autenticado
	 * @return Lista de carritos confirmados ordenados por fecha de compra
	 */
	@Transactional(readOnly = true)
	public List<Carrito> obtenerHistoricoCompras(Integer idUsuario) {
	    return carritoDAO.buscarCarritosConfirmadosPorUsuario(idUsuario);
	}
	
	/**
	 * Agrupa los compradores de un evento en DTOs.
	 *
	 * @param idEvento ID del evento.
	 * @return Lista de compradores con sus localidades agrupadas.
	 */
	@Transactional(readOnly = true)
	public List<CompradorEventoDTO> obtenerCompradoresEvento(Integer idEvento) {

	    List<Object[]> datos = carritoDAO.obtenerCompradoresPorEvento(idEvento);

	    Map<String, CompradorEventoDTO> mapa = new LinkedHashMap<>();

	    for (Object[] fila : datos) {

	        String nombre = String.valueOf(fila[0]);
	        String telefono = String.valueOf(fila[1]);
	        Integer filaNum = ((Number) fila[2]).intValue();
	        Integer asientoNum = ((Number) fila[3]).intValue();

	        String clave = nombre + "-" + telefono;

	        mapa.putIfAbsent(clave, new CompradorEventoDTO(nombre, telefono));

	        mapa.get(clave).addLocalidad(filaNum, asientoNum);
	    }

	    return new ArrayList<>(mapa.values());
	}

 
}