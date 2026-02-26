package es.ticketcenture.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.ticketcenture.entities.Actividad;
import es.ticketcenture.entities.Usuario;
import es.ticketcenture.service.ActividadService;
import es.ticketcenture.utilities.MensajesExito;

/**
 * Clase que controla Actividad. Llamara al servicio necesario en cada caso
 * 
 *
 */

@Controller 
@RequestMapping("/actividad")   
public class ActividadController {

	private ActividadService actividadService;
		
	/**
	 * @param actividadService
	 */
	@Autowired
	public ActividadController(ActividadService actividadService) {
		this.actividadService = actividadService;
	}
    // Mostrar formulario
    @GetMapping("/consulta")
    public String mostrarFormularioConsulta() {
        return "actividad/consulta"; 
    }

}
