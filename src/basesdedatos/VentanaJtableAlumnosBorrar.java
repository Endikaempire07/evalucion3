package basesdedatos;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ListSelectionModel;

public class VentanaJtableAlumnosBorrar extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// PANEL
	private JPanel contentPane;
	// Jtable
	private JTable table;
	// buttons
	private JButton btnSalir;
	// jlabel
	private JLabel lblListadoAlumno;
	// DefaultTableModel
	private DefaultTableModel dtmTabla;
	// cabecera de la tabla
	private Vector<String> columnas;
	// cuerpo de la tabla
	private Vector<Vector<String>> datosTabla;

	/**
	 * @wbp.nonvisual location=219,394
	 */
	private JPanel panelboton;
	private JButton btnInsertar;
	private JButton btnEliminar;
	
	
	int filaseleccionada ;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaJtableAlumnosBorrar frame = new VentanaJtableAlumnosBorrar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public VentanaJtableAlumnosBorrar() throws SQLException {
		setTitle("VentanaJtableAlumnosBorrar");
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

		conectarbasededatos();

		// creo el default model de la tabla
		dtmTabla = new DefaultTableModel(datosTabla, columnas) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
			// hago que NO sea editable la tercera columna (columna 2)
			return (column == 1 || column == 2 || column == 3);
			}
			};
		
		// Jtable
		table = new JTable(dtmTabla);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPane.add(table, BorderLayout.CENTER);

		// modo ordenar
		// sirve para ordenar la tabla
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

		panelboton = new JPanel();
		contentPane.add(panelboton, BorderLayout.SOUTH);
		GridBagLayout gbl_panelboton = new GridBagLayout();
		gbl_panelboton.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panelboton.rowHeights = new int[] { 0, 0 };
		gbl_panelboton.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelboton.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelboton.setLayout(gbl_panelboton);

		btnInsertar = new JButton("Insertar");
		GridBagConstraints gbc_btnInsertar = new GridBagConstraints();
		gbc_btnInsertar.insets = new Insets(0, 0, 0, 5);
		gbc_btnInsertar.gridx = 1;
		gbc_btnInsertar.gridy = 0;
		panelboton.add(btnInsertar, gbc_btnInsertar);

		// boton salir
		btnSalir = new JButton("Salir");
		GridBagConstraints gbc_btnSalir = new GridBagConstraints();
		gbc_btnSalir.insets = new Insets(0, 0, 0, 5);
		gbc_btnSalir.gridx = 3;
		gbc_btnSalir.gridy = 0;
		panelboton.add(btnSalir, gbc_btnSalir);
		
		btnEliminar = new JButton("Eliminar");
		GridBagConstraints gbc_btnEliminar = new GridBagConstraints();
		gbc_btnEliminar.insets = new Insets(0, 0, 0, 5);
		gbc_btnEliminar.gridx = 5;
		gbc_btnEliminar.gridy = 0;
		panelboton.add(btnEliminar, gbc_btnEliminar);

		btnSalir.addActionListener(this);
		btnInsertar.addActionListener(this);
		btnEliminar.addActionListener(this);

	}

	public void conectarbasededatos() {
		try {
			// CONECTO LA BASE DE DATOS
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
			// creo una sentencia que pueda ir por delante y por detras
			Statement st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// selecciono todos lod regitros de la tabla alumnos
			ResultSet rs = st.executeQuery("SELECT * FROM bdalumnos.alumnos;");

			// cabeceras de las columnas
			ResultSetMetaData metaDatos = rs.getMetaData();
			// Se obtiene el número de columnas.
			int numeroColumnas = metaDatos.getColumnCount();
			columnas = new Vector<String>();
			// Se obtiene cada una de las etiquetas para cada columna
			for (int i = 0; i < numeroColumnas; i++) {
				// cojo el valor de la etiqueta de la columna
				// los índices del rs empiezan en 1 pero los índices de las columnas empiezan en
				// 0
				columnas.add(metaDatos.getColumnLabel(i + 1));
			}

			// creo el vector para los datos de la tabla
			datosTabla = new Vector<Vector<String>>();
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
			// cierro el Statement
			st.close();
			// cierro el ResulSet
			rs.close();
			// cierro la conexion
			conexion.close();

		} catch (SQLException e) {
			// si se produce una excepción SQL
			int errorcode = e.getErrorCode();
			if (errorcode == 1062) {
				// si es un error de clave suplicada
				JOptionPane.showMessageDialog(this, (String) "Error Clave Duplicada. Ya existe un registro con esa clave.",
						"ERROR", JOptionPane.ERROR_MESSAGE, null);
				
			} else {
				
				JOptionPane.showMessageDialog(this, (String) "Error SQL Numero " + e.getErrorCode() + ":" + e.getMessage(),
						"ERROR", JOptionPane.ERROR_MESSAGE, null);
			}
		}
	}
	public void insertaralumno() {
		try {
			// CONECTO LA BASE DE DATOS
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
			// creo una sentencia que pueda ir por delante y por detras
			Statement st = conexion.createStatement();

			String consulta = "INSERT INTO bdalumnos.alumnos VALUES('00000009A','N0','A0','1DW3');";
			st.executeUpdate(consulta);

			// cierro el Statement
			st.close();
			// cierro la conexion
			conexion.close();

			// si inserto correctamente en la base de datos
			// lo inserto tambien en la tabla
			Vector<String> fila = new Vector<String>();
			fila.add("00000009A");
			fila.add("N0");
			fila.add("A0");
			fila.add("1DW3");
			fila.add("\n\n\n\n\n\n\n");
			dtmTabla.addRow(fila);

		} catch (SQLException e) {
			// si se produce una excepción SQL
			int errorcode = e.getErrorCode();
			if (errorcode == 1062) {
				// si es un error de clave suplicada
				JOptionPane.showMessageDialog(this, (String) "Error Clave Duplicada. Ya existe un registro con esa clave.",
						"ERROR", JOptionPane.ERROR_MESSAGE, null);
				
			} else {
				
				JOptionPane.showMessageDialog(this, (String) "Error SQL Numero " + e.getErrorCode() + ":" + e.getMessage(),
						"ERROR", JOptionPane.ERROR_MESSAGE, null);
			}
		}
	}
	
	private void eliminaralumno() {
		try {
			// CONECTO LA BASE DE DATOS
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
			// creo una sentencia que pueda ir por delante y por detras
			Statement st = conexion.createStatement();
			//obtengo el dni de la fila
			String dni;
			dni = (String) table.getValueAt(filaseleccionada, 0);
			
			
			String consulta = "DELETE from bdalumnos.alumnos WHERE dni='"+dni+"';";
			st.executeUpdate(consulta);
			
			// cierro el Statement
			st.close();
			// cierro la conexion
			conexion.close();
			
			// si borro correctamente en la base de datos
			// lo borro tambien en la tabla
			this.dtmTabla.removeRow(filaseleccionada);
			
		} catch (SQLException e) {
			// si se produce una excepción SQL
			int errorcode = e.getErrorCode();
			if (errorcode == 1062) {
				// si es un error de clave suplicada
				JOptionPane.showMessageDialog(this, (String) "Error Clave Duplicada. Ya existe un registro con esa clave.",
						"ERROR", JOptionPane.ERROR_MESSAGE, null);
				
			} else {
				
				JOptionPane.showMessageDialog(this, (String) "Error SQL Numero " + e.getErrorCode() + ":" + e.getMessage(),
						"ERROR", JOptionPane.ERROR_MESSAGE, null);
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent ae) {

		Object o = ae.getSource();

		if (o == btnInsertar) {

			insertaralumno();

		} else if (o == btnSalir) {
			
			System.exit(0);
			
		}else if (o == btnEliminar) {
			// Seleccionamos la fila
			filaseleccionada = table.getSelectedRow();
			if(filaseleccionada  < 0) {
				// si no tenemos nada selccionado
				JOptionPane.showMessageDialog(this, (String) "Error. No hay elementos selccionados para borrar",
						"ERROR", JOptionPane.ERROR_MESSAGE, null);
				
			}else {
				//convertimos la fila seleccionada al valor real del dtm 
				filaseleccionada = table.convertRowIndexToModel(filaseleccionada);
				eliminaralumno();
			}		
		}
	}
}
