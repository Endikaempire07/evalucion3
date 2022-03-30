package basesdedatos;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import java.awt.Button;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaJtable extends JFrame   {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// PANEL
	private JPanel contentPane;
	// Jtable
	private JTable table;
	// buttons
	private Button btnSalir;
	// jlabel
	private JLabel lblListadoAlumno;
	// DefaultTableModel
	private DefaultTableModel dtmTabla;
	// cabecera de la tabla
	//cuerpo de la tabla
	private Vector<Vector<String>> datosTabla;
/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaJtable frame = new VentanaJtable();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public VentanaJtable() throws SQLException {
		setTitle("ventanaJTable");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		
		// Label
		lblListadoAlumno = new JLabel("Datos de los Alumnos");
		lblListadoAlumno.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblListadoAlumno, BorderLayout.NORTH);
		
		// boton salir
		btnSalir = new Button("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		contentPane.add(btnSalir, BorderLayout.SOUTH);
		
		// cabeceras de las columnas
		Vector<String> columnas = new Vector<String>();
		columnas.add("DNI");
		columnas.add("Nombre");
		columnas.add("Apellidos");
		columnas.add("Grupo");
		// creo el vector para los datos de la tabla
		datosTabla = new Vector<Vector<String>>();

		try {
			// CONECTO LA BASE DE DATOS
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
			// creo una sentencia que pueda ir por delante y por detras
			Statement st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// selecciono todos lod regitros de la tabla alumnos
			ResultSet rs = st.executeQuery("SELECT * FROM bdalumnos.alumnos;");

		
			// añado uno a uno los alumnos al vector de datos
			while (rs.next()) {
			Vector<String> fila = new Vector<String>();
			fila.add(rs.getString("dni"));
			fila.add(rs.getString("nombre"));
			fila.add(rs.getString("apellidos"));
			fila.add(rs.getString("grupo"));
			fila.add("\n\n\n\n\n\n\n");
			datosTabla.add(fila);
			}
			//cierro el Statement
			st.close();
			//cierro el ResulSet
			rs.close();
			// cierro la conexion
			conexion.close();
			
			
		}catch (SQLException e) {
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
		
		//creo el default model de la tabla
		dtmTabla = new DefaultTableModel(datosTabla, columnas);

		// Jtable
		table = new JTable(dtmTabla);
		contentPane.add(table, BorderLayout.CENTER);
		
		//modo ordenar
		//sirve para ordenar la tabla
		table.setAutoCreateRowSorter(true);
		// ordeno los datos de la tabla
		TableRowSorter<DefaultTableModel> metodoOrdenacion = new TableRowSorter<>(dtmTabla);
		table.setRowSorter(metodoOrdenacion);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		// para que ordene por la primera columna (dni en este caso) en Ascendente
		int columnIndexToSort = 0;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
		metodoOrdenacion.setSortKeys(sortKeys);
		metodoOrdenacion.sort();
		
		// creo un scroll pane y le añado la tabla
		JScrollPane scrollPane = new JScrollPane(table);
		// añado el scroll pane al panel principal
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		
		
	}

	

}
