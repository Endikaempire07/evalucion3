package basesdedatos;

import java.io.Serializable;
import java.util.Objects;

public class Fecha implements Comparable<Fecha>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4825676196498263093L;
	// defino la clase racional
	// propiedades

	private int dia;
	private int mes;
	private int año;

	// Constructor por defecto

	public Fecha() {

		// asignamos un valor a numerador y denominador
		this.dia = 1;
		this.mes = 1;
		this.año = 2020;
	}
	// Fin Constructor por defecto

	// Constructor copia
	public Fecha(Fecha f) {
		this.dia = f.dia;
		this.mes = f.mes;
		this.año = f.año;
	}
	// Fin Constructor copia

	// Constructor personalizado
	public Fecha(int d, int m, int a) {
		this.dia = d;
		this.mes = m;
		this.año = a;
	}

	// Fin Constructor personalizado
	
	// Obtener o cambiar valores de las propiedades
	// Getters and Setters

	public double getDia() {
		return dia;
	}

	public void setDias(int dias) {
		this.dia = dias;
	}

	public double getmes() {
		return mes;
	}

	public void setmes(int mes) {
		this.mes = mes;
	}
	
	public double getaños() {
		return año;
	}

	public void setaños(int año) {
		this.año = año;
		
		
	}
	// Fin Getters and Setters

	// to String
	@Override
	public String toString() {
		return (dia + "/" + mes + "/" + año);
	}
	// Fin to String
	
	//hash code
	@Override
	public int hashCode() {
		return Objects.hash(año, dia, mes);
	}
	
	//Fin hash code

	// equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fecha other = (Fecha) obj;
		return año == other.año && dia == other.dia && mes == other.mes;
	}
	
	//Fin equals
	
	@Override
	public int compareTo(Fecha other) {
		if (this.año > other.año) {
			// si es mayor
			return 1;
		} else if (this.año < other.año) {
			// si es menor
			return -1;
		} else {
			// si año es igual;
			if (this.mes < other.mes) {
				return 1;
			}else if (this.mes > other.mes) {
				return -1;
			} else {
				// si año es igual;
				if (this.dia < other.dia) {
					return 1;
				}else if (this.dia > other.dia) {
					return -1;
				}
			}
		}
		return 0;
	}

}
