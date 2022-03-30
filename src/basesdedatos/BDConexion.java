package basesdedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConexion {

	public static void main(String[] args) {
		// Prueba de conexion a una base de datos **
		try{
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
			// si se ha conectado correctamente
			System.out.println("Conexión Correcta.");
			// cierro la conexion
			conexion.close();
			} catch (SQLException e){
				// si NO se ha conectado correctamente
				e.printStackTrace();
				System.out.println("Error de Conexión");
		}
	}

	
	

}
