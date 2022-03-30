package basesdedatos;

import java.util.Objects;

public class Alumno extends Persona  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4442575994577227861L;
	/**
	 * 
	 */
	
	private String grupo;

//Constructor por defecto

	public Alumno() {
		super();
		this.grupo = "1DWM3";
	}

//Fin Constructor por defecto

//Constructor copia

	public Alumno(Alumno alumno) {
		super(alumno);
		this.grupo = alumno.grupo;
	}

//Fin Constructor copia

//Constructor Personalizado

	public Alumno(Persona p, String nombreGrupo) {
		super(p);
		this.grupo = nombreGrupo;
	}

//Fin Constructor Personalizado

//Constructor Personalizado

	public Alumno(Persona p) {
		super(p);
		this.grupo = "4DWM3";
	}

//Fin Constructor Personalizado

//toString
	@Override
	public String toString() {
		return (super.toString() + " Grupo: " + grupo);
	}
//Fin toString

//Getters and setters

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

//Fin Getters and setters

//hashCode

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(grupo);
		return result;
	}

//Fin hashCode

//equals

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alumno other = (Alumno) obj;

		return Objects.equals(grupo, other.grupo);
	}

//Fin equals

}
