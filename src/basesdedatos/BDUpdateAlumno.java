package basesdedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BDUpdateAlumno {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");

			// si se ha conectado correctamente

			// System.out.println("Conexión Correcta.");

			// creo una sentencia
			Statement st = conexion.createStatement();

			// creo la consulta
			
			/*
			
			CON VARIABLES
			 
			String dni = "11111111A";
			String grupo = "1DW3";
			String consulta = "UPDATE bdalumnos.alumnos SET grupo='" + grupo + "' WHERE dni='" + dni + "';";
			
			
			*/
			 String consulta = "UPDATE bdalumnos.alumnos SET grupo='1DW3' WHERE dni='11111111A';" ;
			

			// ejecuto la sentencia
			int registrosdemodificaciones;
			registrosdemodificaciones = st.executeUpdate(consulta);

			// compruebo si hay algun registro modificado
			if (registrosdemodificaciones > 0) {

				// si el registrado a sido modificado
				System.out.println("El registro se ha modificado");

			} else {

				// si no se ah modificado ningun registro
				System.out.println("No se ha modificado ningun registro");

			}

			// cierro el Statement
			st.close();
			// cierro la conexion
			conexion.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Error SQL Numero " + e.getErrorCode() + ":" + e.getMessage());
		}
	}

}
