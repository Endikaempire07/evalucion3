package basesdedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BDInsertAlumno {

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
			String consulta = "Insert into bdalumnos.alumnos values( '12345678A', 'Nuevo', 'Alumno','1AS3')";

			// ejecuto la sentencia
			st.executeUpdate(consulta);

			// si el registrado a sido insertado
			System.out.println("Se ha insertado");

			// cierro el Statement
			st.close();
			// cierro la conexion
			conexion.close();

		} catch (SQLException e) {
			// si se produce una excepción SQL
			int errorcode = e.getErrorCode();
			// si la excepcion es clave duplicada
			if (errorcode == 1062) {
				// si es un error de clave suplicada
				System.out.println("Error Clave Duplicada. Ya existe un registro con esa clave.");
			} else {
				//si es otro tipo de excepcion
				System.out.println("Error SQL Numero " + e.getErrorCode() + ":" + e.getMessage());
			}
		}
	}

}
