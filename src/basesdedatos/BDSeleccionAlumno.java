package basesdedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BDSeleccionAlumno {

	public static void main(String[] args) {
		// Prueba de conexion a una base de datos **
		try{
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
			// si se ha conectado correctamente
			
			// System.out.println("Conexión Correcta.");
			
			//creo una sentencia
			Statement st = conexion.createStatement();
			
			//creo un ResulSet            el nombre de la base de datos en las cosultas siempre
			//selecciono todos lod regitros de la tabla alumnos
			ResultSet rs = st.executeQuery("SELECT * FROM bdalumnos.alumnos;");
			
			// muestro por pantalla uno a uno los registros de la tabla alumno
			while (rs.next()){
				System.out.println("DNI: " + rs.getObject("dni") + ", nombre: " + rs.getObject("nombre") + ", Apellidos: " +
				rs.getObject("apellidos") + ", Grupo: " + rs.getObject("grupo"));
				}
			
			//cierro el Statement
			st.close();
			//cierro el ResulSet
			rs.close();
			// cierro la conexion
			conexion.close();
		
			} catch (SQLException e){
				// si NO se ha conectado correctamente
				e.printStackTrace();
				System.out.println("Error de Conexión");
				
		}
	}

	
	

}
