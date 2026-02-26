package es.ticketcenture.validations;

import es.ticketcenture.entities.Evento;
import es.ticketcenture.entities.Grupo;
import es.ticketcenture.utilities.ValidarEvento;

public class ValidarGrupoEntradas {

    public static boolean existenEntradasVendidas(Grupo grupo) {

        if (grupo.getEventos() == null) {
            return false;
        }

        for (Evento evento : grupo.getEventos()) {
            if (ValidarEvento.existenEntradasVendidas(evento)) {
                return true;
            }
        }

        return false;
    }
}
