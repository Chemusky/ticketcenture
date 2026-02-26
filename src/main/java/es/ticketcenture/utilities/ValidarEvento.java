package es.ticketcenture.utilities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import es.ticketcenture.entities.Butaca;
import es.ticketcenture.entities.Entrada;
import es.ticketcenture.entities.Evento;
import es.ticketcenture.entities.TipoEntrada;

/**
 * Clase con utilidades y validaciones para la funcionalidad de Evento
 */
public class ValidarEvento {

    /**
     * Metodo que genera las entradas de un evento
     * @param evento
     * @return
     */
    public List<Entrada> generarEntradas(Evento evento) {

        List<Entrada> entradas = new ArrayList<>();

        // Recupero las butacas del evento
        List<Butaca> butacas = evento.getButacas();

        // Y generamos las entradas
        for (Butaca b : butacas) {

            int filas = b.getNumeroFilas();
            int asientos = b.getNumeroAsientos();

            for (int f = 1; f <= filas; f++) {
                for (int a = 1; a <= asientos; a++) {

                    Entrada entrada = new Entrada();
                    entrada.setEvento(evento);
                    entrada.setTipoAsiento(b.getTipoAsiento());
                    entrada.setFila(f);
                    entrada.setAsiento(a);
                    entrada.setEstado(TipoEntrada.DISPONIBLE);

                    entradas.add(entrada);
                }
            }
        }

        return entradas;
    }

    /**
     * Comprueba si un evento tiene alguna entrada vendida.
     */
    public static boolean existenEntradasVendidas(Evento evento) {

        if (evento.getEntradas() == null) {
            return false;
        }

        for (Entrada e : evento.getEntradas()) {
            if (e.getEstado() == TipoEntrada.VENDIDA || 
            	e.getEstado() == TipoEntrada.RESERVADA_CARRITO) {
                return true;
            }
        }

        return false;
    }


    /**
     * Valida que la configuración de las entradas es correcta.
     * Si es modificación, primero comprueba que no existan entradas vendidas.
     */
    public static void validarConfiguracionEntradas(Evento evento, boolean esModificacion) {

        if (esModificacion && existenEntradasVendidas(evento)) {
            throw new IllegalStateException(MensajesError.EVENTO_NO_MODIFICABLE);
        }

        if (evento.getButacas() == null || evento.getButacas().isEmpty()) {
            throw new IllegalArgumentException(MensajesError.EVENTO_SIN_BUTACAS);
        }

        for (Butaca b : evento.getButacas()) {

            if (b.getNumeroFilas() <= 0) {
                throw new IllegalArgumentException(MensajesError.EVENTO_FILAS_INVALIDAS);
            }

            if (b.getNumeroAsientos() <= 0) {
                throw new IllegalArgumentException(MensajesError.EVENTO_ASIENTOS_INVALIDOS);
            }

            if (b.getTipoAsiento() == null) {
                throw new IllegalArgumentException(MensajesError.EVENTO_TIPO_ASIENTO_NULO);
            }
        }
    }

    
    
    /**
     * Valida que no vengan en null los inputs para la configuracion de butacas
     * @param filasStr
     * @param asientosStr
     * @param precioStr
     */
    public static void validarCamposButaca(String filasStr, String asientosStr, String precioStr) {

        if (filasStr == null || filasStr.isBlank() ||
            asientosStr == null || asientosStr.isBlank() ||
            precioStr == null || precioStr.isBlank()) {

            throw new IllegalArgumentException("Debes completar filas, asientos y precio para cada tipo de butaca.");
        }
    }

}
