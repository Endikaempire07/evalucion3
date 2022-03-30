package basesdedatos;

import java.io.Serializable;
import java.util.Objects;

public class Persona implements Comparable<Persona>, Serializable {
	// defino la clase racional
	// propiedades

	/**
	 * 
	 */
	private static final long serialVersionUID = -1263852824637692510L;
	/**
	 * 
	 */

	private String dni;
	private String nombre;
	private String apellidos;
	private Fecha fechanacimiento;

	// Constructor por defecto

	public Persona() {

		// asignamos un valor a numerador y denominador
		this.dni = "";
		this.nombre = "";
		this.apellidos = "";
		this.fechanacimiento = new Fecha();

	}
	// Fin Constructor por defecto

	// Constructor Copia
	public Persona(Persona p) {

		this.dni = p.dni;
		this.nombre = p.nombre;
		this.apellidos = p.apellidos;
		this.fechanacimiento = new Fecha(p.fechanacimiento);

	}
	// Fin Constructor Copia

	// Constructor personalizados 1
	public Persona(String d, String n, String a) {
		this.dni = d;
		this.nombre = n;
		this.apellidos = a;
		this.fechanacimiento = new Fecha();
	}
	// Fin Constructor personalizados 1

	// Constructor personalizados 2
	public Persona(String d, String n, String a, Fecha f) {
		this.dni = d;
		this.nombre = n;
		this.apellidos = a;
		this.fechanacimiento = new Fecha(f);
	}
	// Fin Constructor personalizados 2
	
	
	// Obtener o cambiar valores de las propiedades
	// Setters y Getters

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Fecha getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(Fecha fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}
	
	// Fin Setters y Getters

	//hash code
	@Override
	public int hashCode() {
		return Objects.hash(apellidos, dni, fechanacimiento, nombre);
	}
	//FIN hash code
	
	//equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			// si es el mismo objeto
			return true;
		if (obj == null)
			// si el objeto obj no esta creado
			return false;
		if (getClass() != obj.getClass())
			// si el objeto obj es de otra Clase diferente
			return false;
		Persona other = (Persona) obj;
		// Comparo las Propiedades
		return (this.dni.equals(other.dni));
		}
	//FIN equals
	
	// to String
	
		@Override
		public String toString() {
			return (dni + " " + nombre + " " + apellidos + " " + fechanacimiento);
		}
		
	//FIN to String

	//compareTo
	@Override
	public int compareTo(Persona other) {
		return(this.dni.compareTo(other.dni));
	}
	//compareTo


	
	
}
