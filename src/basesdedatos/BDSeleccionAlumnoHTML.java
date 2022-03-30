package basesdedatos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BDSeleccionAlumnoHTML {

	public static void main(String[] args) {
		// Prueba de conexion a una base de datos **4
		conexion_html();
	}
		
	public static void conexion_html(){
		
	
		try{
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
			// si se ha conectado correctamente
			
			// System.out.println("Conexión Correcta.");
			
			//creo una sentencia
			Statement st = conexion.createStatement();
			
			//creo un ResulSet            el nombre de la base de datos en las cosultas siempre
			//selecciono todos lod regitros de la tabla alumnos
			ResultSet rs = st.executeQuery("SELECT * FROM bdalumnos.alumnos;");
			
			
			
			
			// recorro registro a registro el ResultSet
			FileWriter fichero = null;
			PrintWriter pw = null;
			BufferedWriter bw = null;
			
			try {
				// abro el fichero
				
				
				fichero = new FileWriter("alumnos.html");
				pw = new PrintWriter(fichero);
				bw = new BufferedWriter(pw);

		//_____________________________________________________________________________________________________________________________			
				bw.write("<html>");
				
				
				
				// cabecera
				
				
				
				bw.write("<head>");
				//el title de la pagina
				bw.write("<title>Listado Alumnos Html </title>");
				bw.write("</head>");
				bw.newLine();
				
				
		//_____________________________________________________________________________________________________________________________			
				
				//body
				
				
				bw.write("<body>");
				bw.write("<h1>Listado Alumnos.</h1>");
				
			
				// compruebo si hay registros
				if (rs.isBeforeFirst() ) {
					// genero la tabla
				bw.write("<table border='1'>");
				//ponemos la cabecera del titulo
				bw.write("<thead>");
				bw.write("<tr>");
				
				// genero los campos de la tabla
				bw.write("<th>dni </th>");
				bw.write("<th>nombre </th>");
				bw.write("<th>apellidos </th>");
				bw.write("<th>grupo </th>");
				
				bw.write("</tr>");
				bw.write("</thead>");
				// genero el cuerpo de la tabla
				bw.write("<tbody>");
				//iniciamos la repetitiva
				// genero una fila por cada uno de los registros de la tabla alumno
				
				while (rs.next()){
					bw.write("<tr>");
					bw.write("<td>" + rs.getObject("dni") + "</td>");
					bw.write("<td>" + rs.getObject("nombre") + "</td>");
					bw.write("<td>" + rs.getObject("apellidos") + "</td>");
					bw.write("<td>" + rs.getObject("grupo") + "</td>");
					bw.write("</tr>");

					}
				bw.write("</tbody>");
				bw.write("</table>");
			
				
					
				
				//br como si fuera html para saltar a la siguiente fila
				//bw.write("<br>");
				bw.newLine();
				
				
				
				
		//_____________________________________________________________________________________________________________________________			

				//pie de pagina
				
				
				bw.write("<footer>");
				//bw.write("<p>Pie de pagina.</p>");
				bw.write("</footer>");
				bw.newLine();
				
				
				
				
				
		//_____________________________________________________________________________________________________________________________			
			
				//cierre de body y html
				bw.write("</body>");
				bw.newLine();	
				bw.write("</html>");

		//_____________________________________________________________________________________________________________________________			
				    
				} else {
					bw.write("<h3>Listado de alumnos vacio</h3>");		
					}
			
				
				// cierro el fichero
				bw.close();
				pw.close();
				fichero.close();
			}
			catch (IOException e) {
				//mensaje de error
				System.out.println("Se ha producido un error");
				
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

