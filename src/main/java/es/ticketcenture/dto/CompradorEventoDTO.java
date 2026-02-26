package es.ticketcenture.dto;

import java.util.ArrayList;
import java.util.List;

public class CompradorEventoDTO {

	private String nombre;
	private String telefono;
	private List<String> localidades = new ArrayList<>();

	/**
	 * Constructor del DTO
	 * 
	 * @param nombre
	 * @param telefono
	 */
	public CompradorEventoDTO(String nombre, String telefono) {
		this.nombre = nombre;
		this.telefono = telefono;
	}

	/**
	 * Añade una localidad (fila y asiento) a la lista de entradas adquiridas por el
	 * comprador
	 * 
	 * @param fila
	 * @param asiento
	 */
	public void addLocalidad(Integer fila, Integer asiento) {
		localidades.add("Fila " + fila + " - Asiento " + asiento);
	}

	/**
	 * Devuelve el número de entradas
	 * @return
	 */
	public int getNumeroEntradas() {
		return localidades.size();
	}

	/**
	 * Obtiene el nombre
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Obtiene el número de teléfono
	 * @return
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Devuelve la lista de localidades
	 * @return
	 */
	public List<String> getLocalidades() {
		return localidades;
	}

}
