package es.ticketcenture.entities;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import es.ticketcenture.validations.ValidDni;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un usuario en la BBDD.
 * 
 */
@Entity
@Table(name = "usuarios")
public class Usuario {
	
	//Declaracion de atributos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Integer idUsuario;

	@NotBlank(message = "{usuario.nombre.notblank}")
    @Size(max = 30, message = "{usuario.nombre.size}")
	@Column(nullable = false, length = 30)
	private String nombre;

	@NotBlank(message = "{usuario.apellidos.notblank}")
    @Size(max = 50, message = "{usuario.apellidos.size}")
	@Column(nullable = false, length = 50)
	private String apellidos;
	
	@NotNull(message = "{usuario.telefono.notnull}")
	@Min(value = 100000000, message = "{usuario.telefono.min}")   
	@Max(value = 999999999, message = "{usuario.telefono.max}")   
	@Column(nullable = false)
	private Integer telefono;

	@NotBlank(message = "{usuario.dni.notblank}")
	@ValidDni
	@Column(length = 9, unique = true, nullable = false)
	private String dni;

	@Column(nullable = false)
	private boolean admin;

	@NotBlank(message = "{usuario.username.notblank}")
    @Size(max = 30, message = "{usuario.username.size}")
	@Column(nullable = false, unique = true, length = 30)
	private String username;
	
	@NotBlank(message = "{usuario.email.notblank}")
    @Email(message = "{usuario.email.email}")
    @Size(max = 200, message = "{usuario.email.size}")
	@Column(nullable = false, unique = true, length = 200)
	private String email;

	@NotBlank(message = "{usuario.password.notblank}")
    @Size(min = 8, max = 255, message = "{usuario.password.size}")
	@Column(nullable = false, length = 255)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_usuario", nullable = false)
	private TipoUsuario tipoUsuario = TipoUsuario.NORMAL;

	@Column(nullable = false)
	private boolean activo;

	@Column(name = "fecha_alta", nullable = false, insertable = false, updatable = false)
	private LocalDateTime fechaAlta;

	@Column(name = "fecha_baja")
	private LocalDateTime fechaBaja;
	
	// Relacion con actividades (LO PUSE EN REMOVE PARA PODER ELIMINAR EL USUARIO SIENDO ADMIN)
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Actividad> actividades = new ArrayList<>();
	
	// Relacion con direcciones
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.EAGER)
	@Valid
	private List<Direccion> direcciones = new ArrayList<>();
	
	//Intentos fallidos consecutivos al hacer login
	@Column(name = "intentos_fallidos")
	private int intentosFallidos = 0;

	//Hasta cuando se bloquea si hay 3 intentos fallidos consecutivos
	@Column(name = "bloqueado_hasta")
	private LocalDateTime bloqueadoHasta;
	
	
	//Agregamos Carrito también
	//Un usuario puede tener varios carritos, lo usaremos para el historico de compras
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<Carrito> carritos;

	
	/**
	 * Constructor vacio
	 */
	public Usuario() {
		
	}

	/**
	 * Constructor de parametros
	 *
	 * @param idUsuario Id del usuario
	 * @param nombre Nombre real del usuario
	 * @param apellidos Apellidos del usuario
	 * @param telefono Telefono del usuario
	 * @param dni DNI del usuario
	 * @param admin Verificacion de admin
	 * @param username User utilizado en la plataforma
	 * @param email Email del usuario
	 * @param password Password utilizada en la plataforma
	 * @param tipoUsuario Tipo de usuario
	 * @param activo Verificacion de usuario activo
	 * @param fechaAlta Fecha del alta
	 * @param fechaBaja Fecha de la baja
	 */
	public Usuario(Integer idUsuario, String nombre, String apellidos, Integer telefono, String dni, boolean admin,
			String username, String email, String password, TipoUsuario tipoUsuario, boolean activo,
			LocalDateTime fechaAlta, LocalDateTime fechaBaja) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.dni = dni;
		this.admin = admin;
		this.username = username;
		this.email = email;
		this.password = password;
		this.tipoUsuario = tipoUsuario;
		this.activo = activo;
		this.fechaAlta = fechaAlta;
		this.fechaBaja = fechaBaja;
	}

	//Getters y Setters

	/**
	 * @return Id de usuario
	 */
	public Integer getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario Nuevo ID de usuario
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return Nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre Nuevo nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos Nuevos apellidos
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return Telefono
	 */
	public Integer getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono Nuevo telefono del usuario
	 */
	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return DNI
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * @param dni Nuevo DNI
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * @return true, si es admin.
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * @param admin Nuevo estado de admin
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * @return Username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username Nuevo username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return Email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email Nuevo email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password Nueva password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Tipo usuario
	 */
	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	/**
	 * @param tipoUsuario Nuevo tipo usuario
	 */
	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	/**
	 * @return true, si esta activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo Nuevo estado de activo
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return Fecha de alta
	 */
	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	/**
	 * @param fechaAlta Nueva fecha de alta
	 */
	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	/**
	 * @return Fecha de baja
	 */
	public LocalDateTime getFechaBaja() {
		return fechaBaja;
	}

	/**
	 * @param fechaBaja Nueva fecha de baja
	 */
	public void setFechaBaja(LocalDateTime fechaBaja) {
		this.fechaBaja = fechaBaja;
	}


	/**
	 * Nos devuelve la cantidad de intentos fallidos consecutivos
	 * @return
	 */
	public int getIntentosFallidos() {
	    return intentosFallidos;
	}

	/**
	 * Establece la cantidad de intentos fallidos consecutivos 
	 * @param intentosFallidos
	 */
	public void setIntentosFallidos(int intentosFallidos) {
	    this.intentosFallidos = intentosFallidos;
	}

	/**
	 * Nos informa hasta cuando estar� bloqueada la cuenta
	 * @return
	 */
	public LocalDateTime getBloqueadoHasta() {
	    return bloqueadoHasta;
	}

	
	
	/**
	 * @return lista de actividades
	 */
	public List<Actividad> getActividades() {
		return actividades;
	}

	/**
	 * @param actividades
	 */
	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}

	/**
	 * @return lista de direcciones
	 */
	public List<Direccion> getDirecciones() {
		return direcciones;
	}

	/**
	 * @param direcciones
	 */
	public void setDirecciones(List<Direccion> direcciones) {
		this.direcciones = direcciones;
	}

	public List<Carrito> getCarritos() {
		return carritos;
	}

	public void setCarritos(List<Carrito> carritos) {
		this.carritos = carritos;
	}

	/**
	 * Metodo toString
	 */
	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellidos=" + apellidos + ", telefono="
				+ telefono + ", dni=" + dni + ", admin=" + admin + ", username=" + username + ", email=" + email
				+ ", password=" + password + ", tipoUsuario=" + tipoUsuario + ", activo=" + activo + ", fechaAlta="
				+ fechaAlta + ", fechaBaja=" + fechaBaja + "]";
	}

}
