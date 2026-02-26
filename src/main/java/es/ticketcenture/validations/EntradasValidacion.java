package es.ticketcenture.validations;

import es.ticketcenture.entities.Entrada;
import es.ticketcenture.entities.TipoEntrada;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EntradasValidacion {

    /**
     * Valida si una entrada está disponible para ser añadida al carrito.
     *
     * - Si la entrada tiene estado VENDIDA -> NO disponible.
     * - Si reservadaCarrito NO es null y la reserva es de hace menos de 10 minutos -> NO disponible.
     * - En cualquier otro caso -> disponible.
     *
     * @param entrada La entrada que se quiere validar
     * @return true si está disponible ---- false si NO lo está
     */
    public boolean entradaDisponible(Entrada entrada) {

        // Si la entrada está vendida -> NO disponible
        if (entrada.getEstado() == TipoEntrada.VENDIDA) {
            return false;
        }

        // Si no tiene reserva -> está disponible
        if (entrada.getReservadaCarrito() == null) {
            return true;
        }

        // Si tiene reserva, comprobamos si han pasado más de 10 minutos
        LocalDateTime fechaReserva = entrada.getReservadaCarrito();
        LocalDateTime haceDiezMinutos = LocalDateTime.now().minusMinutes(10);

        // Si la reserva es reciente (menos de 10 minutos) -> NO disponible
        if (fechaReserva.isAfter(haceDiezMinutos)) {
            return false;
        }

        //  Si la reserva es antigua (más de 10 minutos) -> disponible
        return true;
    }
}

