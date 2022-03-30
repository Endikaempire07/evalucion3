package basesdedatos;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
import javax.sql.RowSetMetaData;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ListSelectionModel;

public class VentanaJTableAlumnosBorrarDesconectado extends JFrame implements ActionListener {

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

	int filaseleccionada;

	boolean modificado = false;

	private CachedRowSet crs;
	private Connection conexion;

	private String dni;
	private String nombre;
	private String apellidos;
	private String grupo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaJTableAlumnosBorrarDesconectado frame = new VentanaJTableAlumnosBorrarDesconectado();
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
	public VentanaJTableAlumnosBorrarDesconectado() throws SQLException {
		setTitle("VentanaJTableAlumnosBorrarDesconectado");
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
			// me conecto usando una conexion
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
			// desactivo la actualizacion automatica de datos
			conexion.setAutoCommit(false);
			// creo el CachedRowSet

			RowSetFactory myRowSetFactory = null;
			myRowSetFactory = RowSetProvider.newFactory();
			crs = myRowSetFactory.createCachedRowSet();

			String cosulta = "SELECT * FROM alumnos";
			// selecciono todos los alumnos
			// usando la conexion anterior
			crs.setCommand(cosulta);
			crs.execute(conexion);
			// cierro la conexion con la base de datos
			// selecciono todos lod regitros de la tabla alumnos
			// cabeceras de las columnas
			RowSetMetaData rsmd = (RowSetMetaData) crs.getMetaData();
			int numeroColumnas = rsmd.getColumnCount();

			// Se obtiene el número de columnas.
			columnas = new Vector<String>();
			// Se obtiene cada una de las etiquetas para cada columna
			for (int i = 0; i < numeroColumnas; i++) {
				// cojo el valor de la etiqueta de la columna
				// los índices del rs empiezan en 1 pero los índices de las columnas empiezan en
				// 0
				columnas.add(rsmd.getColumnLabel(i + 1));
			}

			// creo el vector para los datos de la tabla
			datosTabla = new Vector<Vector<String>>();
			// añado uno a uno los alumnos al vector de datos
			while (crs.next()) {
				Vector<String> fila = new Vector<String>();
				fila.add(crs.getString("dni"));
				fila.add(crs.getString("nombre"));
				fila.add(crs.getString("apellidos"));
				fila.add(crs.getString("grupo"));
				fila.add("\n\n\n\n\n\n\n");
				datosTabla.add(fila);
			}

			// cierro CachedRowSet
			// crs.close();
			// cierro la conexion
			conexion.close();

		} catch (SQLException e) {
			// si se produce una excepción SQL
			int errorcode = e.getErrorCode();

			if (errorcode == 1062) {
				// si es un error de clave suplicada
				JOptionPane.showMessageDialog(this,
						(String) "Error Clave Duplicada. Ya existe un registro con esa clave.", "ERROR",
						JOptionPane.ERROR_MESSAGE, null);

			} else {

				JOptionPane.showMessageDialog(this,
						(String) "Error SQL Numero " + e.getErrorCode() + ":" + e.getMessage(), "ERROR",
						JOptionPane.ERROR_MESSAGE, null);

			}
		}
	}

	public void insertaralumnodesconectado() {
		try {
			dni = "00000000A";
			nombre = "N0";
			apellidos = "A0";
			grupo = "1DW3";
			// añado el registro al CachedRowSet
			crs.moveToInsertRow();
			crs.updateString(1, dni);
			crs.updateString(2, nombre);
			crs.updateString(3, apellidos);
			crs.updateString(4, grupo);
			crs.insertRow();
			crs.moveToCurrentRow();
			// actualizo el valor de modificado
			modificado = true;

		} catch (SQLException e) {
			// si se produce una excepción SQL
			int errorcode = e.getErrorCode();

			if (errorcode == 1062) {
				// si es un error de clave suplicada
				JOptionPane.showMessageDialog(this,
						(String) "Error Clave Duplicada. Ya existe un registro con esa clave.", "ERROR",
						JOptionPane.ERROR_MESSAGE, null);

			} else {

				JOptionPane.showMessageDialog(this,
						(String) "Error SQL Numero " + e.getErrorCode() + ":" + e.getMessage(), "ERROR",
						JOptionPane.ERROR_MESSAGE, null);

			}
		}
	}

	private void guardaerlosdatosdesconectados() {
		// TODO Auto-generated method stub
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
			// si se ha conectado correctamente
			// desactivo la actualizacion automatica de datos
			conexion.setAutoCommit(false);
			crs.acceptChanges(conexion);
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(this, (String) "Error SQL Numero " + e.getErrorCode() + ":" + e.getMessage(),
					"ERROR", JOptionPane.ERROR_MESSAGE, null);

		}
	}

	public void eliminaralumnos() {
		try {
			// voy a la posición filaseleccionada
			crs.absolute(filaseleccionada + 1);
			// borro el registro de esa posicion
			crs.deleteRow();
			// me posiciono en el primer registro para que los cambios tengan efecto
			crs.first();
			// actualizo el valor de modificado
			modificado = true;

			this.dtmTabla.removeRow(filaseleccionada);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, (String) "Error. No se ha podido borrar el registro", "Error",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		Object o = ae.getSource();

		if (o == btnInsertar) {

			insertaralumnodesconectado();
			if (modificado) {
				try {
					conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
					// si se ha conectado correctamente
					// desactivo la actualizacion automatica de datos
					conexion.setAutoCommit(false);
					crs.acceptChanges(conexion);

					// lo inserto tambien en la tabla
					Vector<String> fila = new Vector<String>();
					fila.add(dni);
					fila.add(nombre);
					fila.add(apellidos);
					fila.add(grupo);
					fila.add("\n\n\n\n\n\n\n");
					dtmTabla.addRow(fila);

				} catch (SQLException e) {
					JOptionPane.showMessageDialog(this,
							(String) "Error SQL Numero " + e.getErrorCode() + ":" + e.getMessage(), "ERROR",
							JOptionPane.ERROR_MESSAGE, null);
				}
			}

		} else if (o == btnEliminar) {
			// Seleccionamos la fila
			filaseleccionada = table.getSelectedRow();
			if (filaseleccionada >= 0) {
				filaseleccionada = table.convertRowIndexToModel(filaseleccionada);
				// convertimos la fila seleccionada al valor real del dtm
				eliminaralumnos();
			} else {

				// si no tenemos nada selccionado
				JOptionPane.showMessageDialog(this, (String) "Error. No hay elementos selccionados para borrar",
						"ERROR", JOptionPane.ERROR_MESSAGE, null);
			}
		} else if (o == btnSalir) {
			if (modificado == true) {
				int valor = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres modificar los datos?",
						"Advertencia", JOptionPane.YES_NO_CANCEL_OPTION);

				switch (valor) {
				// si ha pulsado yes
				case JOptionPane.YES_OPTION:
					// guardo los datos en un dlm
					guardaerlosdatosdesconectados();
					
					break;
				// funcion opcional
				// si ha pulsado NO
				case JOptionPane.NO_OPTION:
					break;

				case JOptionPane.CANCEL_OPTION:
				case JOptionPane.CLOSED_OPTION:
					// si ha pulsado Cancelar o
					// si ha cerrado la ventana

					return;
				}
			}
			System.exit(0);
		}
	}
}
