package basesdedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BDDeleteAlumno {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
		
		// si se ha conectado correctamente
		
		// System.out.println("Conexión Correcta.");
		
		//creo una sentencia
		Statement st = conexion.createStatement();
		
		//creo la consulta
		String consulta = "DELETE FROM bdalumnos.alumnos WHERE dni='12345678A';" ;
		
		//ejecuto la sentencia
		int registrosdeeliminacion;
		registrosdeeliminacion = st.executeUpdate(consulta);
		
		//compruebo si hay algun registro modificado
		if(registrosdeeliminacion > 0 ) {
			
			// si el registrado a sido borrado
			System.out.println("Se ha borrado");
			
		} else {
			
			// si no se ah borrado ningun registro
			System.out.println("No se ha borrado ningun registro");
		}

		
		
		//cierro el Statement
		st.close();
		// cierro la conexion
		conexion.close();
		
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Error SQL Numero " + e.getErrorCode() + ":" + e.getMessage());
		}
	}

}
