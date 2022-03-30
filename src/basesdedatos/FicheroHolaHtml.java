package basesdedatos;


import java.io.*;
public class FicheroHolaHtml {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Escribe el mensaje "Hola Mundo" en un fichero de texto de nombre prueba.txt
		FileWriter fichero = null;
		PrintWriter pw = null;
		BufferedWriter bw = null;
		
		try {
			// abro el fichero
			fichero = new FileWriter("hola.html");
			pw = new PrintWriter(fichero);
			bw = new BufferedWriter(pw);
	
//_____________________________________________________________________________________________________________________________			
			bw.write("<html>");
			
			
			
			// cabecera
			bw.write("<head>");
			//el titulo de la pagina
			bw.write("<title>Hola Html </title>");
			bw.write("</head>");
			bw.newLine();
			
			
//_____________________________________________________________________________________________________________________________			
			
			//body
			bw.write("<body>");
			//br como si fuera html para saltar a la siguiente fila
			bw.write("<p>Fichero Hola HTML generado por código.</p>");
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
		
			
			
			// cierro el fichero
			bw.close();
			pw.close();
			fichero.close();
		}
		catch (IOException e) {
			//mensaje de error
			//System.out.println("Se ha producido un error");
			System.out.println("Error: "  + e.getMessage());

		}
	}
}
