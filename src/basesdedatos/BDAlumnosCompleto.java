package basesdedatos;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.JTable;

public class BDAlumnosCompleto extends JFrame implements ActionListener, ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// PANELES
	private JPanel Panel1;
	private JPanel panel2;
	private JPanel panel3;
	// TXT
	private JTextField txtDni;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtGrupo;
	// JLABEL
	private JLabel Registro;
	private JLabel lblDni;
	private JLabel lblNombre;
	private JLabel lblApellidos;
	private JLabel lblGrupo;
	// BUTTON
	private JButton btnSalir;
	private JButton btnInsertar;
	private JButton btnBorrar;
	private JButton btnActualizar;
	private JButton btnatras2;
	private JButton btnatras;
	private JButton btnadelante;
	private JButton btnadelante2;
	// variables
	private String dni;
	private String nombre;
	private String apellidos;
	private String grupo;
	private String originaldni;
	private String dni2;

	// variables numero
	private int regActual;
	private int regFin;
	private int indice;
	// Variable boolean
	private boolean modificado = false;
	// Base de datos
	private ResultSet rs;
	private Connection conexion;
	private Alumno alumnoac;
	private Alumno alumno;

	private ArrayList<Alumno> alumno1 = new ArrayList<Alumno>();
	// DefaultTableModel
	private DefaultTableModel dtmTabla;
	// cabecera de la tabla
	// cuerpo de la tabla
	private Vector<Vector<String>> datosTabla;
	private JTable table;
	private JScrollPane scrollPane;

	private TableRowSorter<DefaultTableModel> metodoOrdenacion;
	private JPanel panel_1;
	private JLabel lblCodAsignatura;
	private JLabel lblNota;
	private JTextField txtNota;
	private JButton btnInsertarC;
	private JButton btnActualizarC;
	private JButton btnBorrarC;

	// variables calificaciones
	private String nota;
	private String asignatura;
	private String oldnota;
	private String oldasignatura;
	// combobox
	private JComboBox<String> cmbAsig;
	int filaseleccionada;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BDAlumnosCompleto frame = new BDAlumnosCompleto();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BDAlumnosCompleto() {
		setTitle("BDAlumnoscompleto");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 716, 495);
		Panel1 = new JPanel();
		Panel1.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(Panel1);
		Panel1.setLayout(new BorderLayout(0, 0));

		panel2 = new JPanel();
		Panel1.add(panel2, BorderLayout.NORTH);

		btnatras2 = new JButton("<<");
		panel2.add(btnatras2);

		btnatras = new JButton("<");
		panel2.add(btnatras);

		Registro = new JLabel("");
		panel2.add(Registro);
		/*
		 * "Registros: " + numero + " de " + numerofin
		 * 
		 */
		btnadelante = new JButton(">");
		panel2.add(btnadelante);

		btnadelante2 = new JButton(">>");
		panel2.add(btnadelante2);

		panel3 = new JPanel();
		Panel1.add(panel3, BorderLayout.CENTER);
		GridBagLayout gbl_panel3 = new GridBagLayout();
		gbl_panel3.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel3.rowHeights = new int[] { 0, 0, 0, 0, 0, 53, 0 };
		gbl_panel3.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel3.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panel3.setLayout(gbl_panel3);

		lblDni = new JLabel("DNI :");
		GridBagConstraints gbc_lblDni = new GridBagConstraints();
		gbc_lblDni.anchor = GridBagConstraints.EAST;
		gbc_lblDni.insets = new Insets(0, 0, 5, 5);
		gbc_lblDni.gridx = 0;
		gbc_lblDni.gridy = 0;
		panel3.add(lblDni, gbc_lblDni);

		txtDni = new JTextField();
		GridBagConstraints gbc_txtDni = new GridBagConstraints();
		gbc_txtDni.insets = new Insets(0, 0, 5, 5);
		gbc_txtDni.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDni.gridx = 1;
		gbc_txtDni.gridy = 0;
		panel3.add(txtDni, gbc_txtDni);
		txtDni.setColumns(10);

		btnInsertar = new JButton("Insertar");
		GridBagConstraints gbc_btnInsertar = new GridBagConstraints();
		gbc_btnInsertar.anchor = GridBagConstraints.WEST;
		gbc_btnInsertar.insets = new Insets(0, 0, 5, 0);
		gbc_btnInsertar.gridx = 2;
		gbc_btnInsertar.gridy = 0;
		panel3.add(btnInsertar, gbc_btnInsertar);

		lblNombre = new JLabel("Nombre :");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 1;
		panel3.add(lblNombre, gbc_lblNombre);

		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.gridx = 1;
		gbc_txtNombre.gridy = 1;
		panel3.add(txtNombre, gbc_txtNombre);

		btnBorrar = new JButton("Borrar");
		GridBagConstraints gbc_btnBorrar = new GridBagConstraints();
		gbc_btnBorrar.anchor = GridBagConstraints.WEST;
		gbc_btnBorrar.insets = new Insets(0, 0, 5, 0);
		gbc_btnBorrar.gridx = 2;
		gbc_btnBorrar.gridy = 1;
		panel3.add(btnBorrar, gbc_btnBorrar);

		lblApellidos = new JLabel("Apellidos :");
		GridBagConstraints gbc_lblApellidos = new GridBagConstraints();
		gbc_lblApellidos.anchor = GridBagConstraints.EAST;
		gbc_lblApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidos.gridx = 0;
		gbc_lblApellidos.gridy = 2;
		panel3.add(lblApellidos, gbc_lblApellidos);

		txtApellidos = new JTextField();
		txtApellidos.setColumns(10);
		GridBagConstraints gbc_txtApellidos = new GridBagConstraints();
		gbc_txtApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_txtApellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellidos.gridx = 1;
		gbc_txtApellidos.gridy = 2;
		panel3.add(txtApellidos, gbc_txtApellidos);

		btnActualizar = new JButton("Actualizar");
		GridBagConstraints gbc_btnActualizar = new GridBagConstraints();
		gbc_btnActualizar.anchor = GridBagConstraints.WEST;
		gbc_btnActualizar.insets = new Insets(0, 0, 5, 0);
		gbc_btnActualizar.gridx = 2;
		gbc_btnActualizar.gridy = 2;
		panel3.add(btnActualizar, gbc_btnActualizar);

		lblGrupo = new JLabel("Grupo :");
		GridBagConstraints gbc_lblGrupo = new GridBagConstraints();
		gbc_lblGrupo.anchor = GridBagConstraints.EAST;
		gbc_lblGrupo.insets = new Insets(0, 0, 5, 5);
		gbc_lblGrupo.gridx = 0;
		gbc_lblGrupo.gridy = 3;
		panel3.add(lblGrupo, gbc_lblGrupo);

		txtGrupo = new JTextField();
		txtGrupo.setColumns(10);
		GridBagConstraints gbc_txtGrupo = new GridBagConstraints();
		gbc_txtGrupo.insets = new Insets(0, 0, 5, 5);
		gbc_txtGrupo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGrupo.gridx = 1;
		gbc_txtGrupo.gridy = 3;
		panel3.add(txtGrupo, gbc_txtGrupo);

		// ordeno los datos de la tabla

		// Jtable

		// cabeceras de las columnas
		Vector<String> columnas = new Vector<String>();
		columnas.add("DNI");
		columnas.add("Codasignatura");
		columnas.add("Nota");
		// creo el vector para los datos de la tabla
		datosTabla = new Vector<Vector<String>>();

		dtmTabla = new DefaultTableModel(datosTabla, columnas);

		table = new JTable(dtmTabla);
		conectarbasededatos();

		btnSalir = new JButton("Salir");
		GridBagConstraints gbc_btnSalir = new GridBagConstraints();
		gbc_btnSalir.anchor = GridBagConstraints.WEST;
		gbc_btnSalir.insets = new Insets(0, 0, 5, 0);
		gbc_btnSalir.gridx = 2;
		gbc_btnSalir.gridy = 3;
		panel3.add(btnSalir, gbc_btnSalir);

		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 3;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 4;
		panel3.add(panel_1, gbc_panel_1);

		lblCodAsignatura = new JLabel("CodAsignatura");
		panel_1.add(lblCodAsignatura);

		cmbAsig = new JComboBox<String>();
		cmbAsig.addItem("BD");
		cmbAsig.addItem("ED");
		cmbAsig.addItem("FOL");
		cmbAsig.addItem("PROG");
		cmbAsig.addItem("ISO");
		panel_1.add(cmbAsig);

		lblNota = new JLabel("Nota");
		panel_1.add(lblNota);

		txtNota = new JTextField();
		txtNota.setColumns(10);
		panel_1.add(txtNota);

		btnInsertarC = new JButton("InsertarC");
		panel_1.add(btnInsertarC);

		btnActualizarC = new JButton("ActualizarC");
		panel_1.add(btnActualizarC);

		btnBorrarC = new JButton("BorrarC");
		panel_1.add(btnBorrarC);

		table.setRowSorter(metodoOrdenacion);

		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 0, 0, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 3;
		gbc_table.gridy = 7;
		panel3.add(table, gbc_table);
		// modo ordenar
		// sirve para ordenar la tabla

		scrollPane = new JScrollPane(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 5;
		panel3.add(scrollPane, gbc_scrollPane);

		// ACTIONEVENT BOTONES
		btnSalir.addActionListener(this);
		btnatras2.addActionListener(this);
		btnatras.addActionListener(this);
		btnadelante.addActionListener(this);
		btnadelante2.addActionListener(this);
		btnActualizar.addActionListener(this);
		btnInsertar.addActionListener(this);
		btnBorrar.addActionListener(this);
		btnActualizarC.addActionListener(this);
		btnInsertarC.addActionListener(this);
		btnBorrarC.addActionListener(this);
	}

	public void pasardatos() {

		alumno1.sort(Comparator.naturalOrder());
		alumnoac = alumno1.get(indice);
		regFin = alumno1.size();
		txtDni.setText(alumnoac.getDni());
		originaldni = txtDni.getText();
		txtNombre.setText(alumnoac.getNombre());
		txtApellidos.setText(alumnoac.getApellidos());
		txtGrupo.setText(alumnoac.getGrupo());

		if (indice >= alumno1.size() - 1) {
			btnadelante.setEnabled(false);
			btnadelante2.setEnabled(false);
		} else if (indice <= 0) {
			btnatras.setEnabled(false);
			btnatras2.setEnabled(false);
		} else {
			btnatras.setEnabled(true);
			btnatras2.setEnabled(true);
			btnadelante.setEnabled(true);
			btnadelante2.setEnabled(true);
		}

		Registro.setText("Registro: " + regActual + " de " + regFin);
		metodoOrdenacion.setRowFilter(RowFilter.regexFilter(alumnoac.getDni(), 0));

	}

	public void conectarbasededatos() {
		try {
			// CONECTO LA BASE DE DATOS
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
			// creo una sentencia que pueda ir por delante y por detras
			Statement st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// selecciono todos lod regitros de la tabla alumnos
			rs = st.executeQuery("SELECT * FROM bdalumnos.alumnos;");
			if (rs.next()) {
				rs.beforeFirst();
				while (rs.next()) {
					alumno = new Alumno();
					alumno.setDni(rs.getString("dni"));
					alumno.setNombre(rs.getString("nombre"));
					alumno.setApellidos(rs.getString("apellidos"));
					alumno.setGrupo(rs.getString("grupo"));
					alumno1.add(alumno);
				}

				// añado uno a uno los alumnos al vector de datos
				indice = 0;
				regActual = 1;
				tabladatos();
				metodoOrdenacion = new TableRowSorter<>(dtmTabla);

				pasardatos();

			} else {
				Registro.setText("No hay registros");
				btnatras.setEnabled(false);
				btnatras2.setEnabled(false);
				btnadelante.setEnabled(false);
				btnadelante2.setEnabled(false);
				btnActualizar.setEnabled(false);
				btnBorrar.setEnabled(false);

			}

			st.close();
			rs.close();
			conexion.close();
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(this, (String) "Error de conexion. La conexion no se ha realizado.", "ERROR",
					JOptionPane.ERROR_MESSAGE, null);
			Registro.setText("No hay registros");
			btnatras.setEnabled(false);
			btnatras2.setEnabled(false);
			btnadelante.setEnabled(false);
			btnadelante2.setEnabled(false);
			btnActualizar.setEnabled(false);
			btnBorrar.setEnabled(false);
			btnInsertar.setEnabled(false);
			txtDni.setEnabled(false);
			txtNombre.setEnabled(false);
			txtApellidos.setEnabled(false);
			txtGrupo.setEnabled(false);
		}
	}

	public void insertaralumno() {
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");

			// si se ha conectado correctamente

			// System.out.println("Conexión Correcta.");

			// creo una sentencia
			Statement st = conexion.createStatement();

			// creo la consulta
			String consulta = "Insert into bdalumnos.alumnos values('" + dni + "', '" + nombre + "', '" + apellidos
					+ "','" + grupo + "')";

			// ejecuto la sentencia
			st.executeUpdate(consulta);
			modificado = true;

			// si el registrado a sido insertado

			// cierro el Statement
			st.close();
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

	private boolean verificarCampos(JTextField textField) {
		if (textField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "El campo debe ser rellenado", "Error", JOptionPane.ERROR_MESSAGE);
			textField.requestFocus();
			return false;
		}
		return true;
	}

	public void modificaralumnos() {
		try {
			// CONECTO LA BASE DE DATOS
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
			// creo una sentencia que pueda ir por delante y por detras
			Statement st = conexion.createStatement();
			String consulta;
			// creamos una variable y le hago dentro la operacion de la base de datos
			if (dni != alumnoac.getDni()) {
				JOptionPane.showMessageDialog(this,
						(String) "Error. El dni " + alumnoac.getDni()
								+ " no se puede actualizar porque es clave primaria.",
						"ERROR", JOptionPane.ERROR_MESSAGE, null);
			} else if (this.nombre != alumnoac.getNombre()) {

				consulta = "UPDATE bdalumnos.alumnos set nombre='" + this.nombre + "'WHERE dni='" + alumnoac.getDni()
						+ "';";

				st.executeUpdate(consulta);

			} else if (this.apellidos != alumnoac.getApellidos()) {
				consulta = "UPDATE bdalumnos.alumnos set apellidos='" + this.apellidos + "'WHERE dni='"
						+ alumnoac.getDni() + "';";
				st.executeUpdate(consulta);
			} else if (this.grupo != alumnoac.getGrupo()) {
				consulta = "UPDATE bdalumnos.alumnos set grupo='" + this.grupo + "'WHERE dni='" + alumnoac.getDni()
						+ "';";
				st.executeUpdate(consulta);
			}
			modificado = true;
			// cierro el Statement
			st.close();
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

	public void borraralumnos() {
		try {
			// CONECTO LA BASE DE DATOS
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
			// creo una sentencia que pueda ir por delante y por detras
			Statement st = conexion.createStatement();
			String consulta;
			// obtengo el dni de la fila
			// creo una sentencia
			dni = txtDni.getText();
			// creo la consulta
			consulta = "DELETE FROM `alumnos` WHERE dni='" + dni + "' ";

			// ejecuto la sentencia
			st.executeUpdate(consulta);
			modificado = true;

			// cierro el Statement
			st.close();
			// cierro la conexion
			conexion.close();

			// si borro correctamente en la base de datos
			// lo borro tambien en la tabla

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, (String) "Error, no hay ningún elemento seleccionado", "Error",
					JOptionPane.INFORMATION_MESSAGE, null);

		}
	}

	private boolean verificarCamposborrar(JTextField textField) {
		if (textField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, (String) "Error. No hay elementos para borrar", "ERROR",
					JOptionPane.ERROR_MESSAGE, null);
			return false;
		}
		return true;
	}

	public void salirdealumnos() {
		if (modificado == true) {
			int valor = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres modificar los datos?", "Advertencia",
					JOptionPane.YES_NO_CANCEL_OPTION);

			switch (valor) {
			// si ha pulsado yes
			case JOptionPane.YES_OPTION:
				// guardo los datos en un dlm

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

	public void Ultimoalumno() {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
			// creo una sentencia que pueda ir por delante y por detras
			Statement st = conexion.createStatement();
			String consulta;
			consulta = "SELECT * FROM alumnos ORDER BY dni DESC";
			st.executeUpdate(consulta);

			txtDni.setText(rs.getString("dni"));
			txtNombre.setText(rs.getString("nombre"));
			txtApellidos.setText(rs.getString("apellidos"));
			txtGrupo.setText(rs.getString("grupo"));

			// cierro el Statement
			st.close();
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

			}
		}
	}
	/*
	 * 
	 * 
	 * 
	 * 
	 * */

	/*
	 * Calificaciones
	 */

	public void tabladatos() {

		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
			// creo una sentencia que pueda ir por delante y por detras
			Statement st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// selecciono todos lod regitros de la tabla alumnos
			rs = st.executeQuery("SELECT * FROM bdalumnos.calificaciones ;");
			while (rs.next()) {
				Vector<String> fila = new Vector<String>();
				fila.add(rs.getString("dni"));
				fila.add(rs.getString("codasignatura"));
				fila.add(rs.getString("nota"));
				fila.add("\n\n\n\n\n\n\n");
				datosTabla.add(fila);
			}
			oldasignatura = rs.getString("codasignatura");
			oldnota = rs.getString("nota");
			// A partir de aquí podemos definir un filtro basado en metodoOrdenacion
			// cambio el filtro de la tabla de calificaciones
			// podemos poner un filtro por grupo para sacar solo los alumnos de 1DW3
			st.close();
			rs.close();
			conexion.close();
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(this, (String) "Error SQL Numero " + e.getErrorCode() + ":" + e.getMessage(),
					"ERROR", JOptionPane.ERROR_MESSAGE, null);

		}
		// creo el default model de la tabla

		// para que ordene por la primera columna (dni en este caso) en Ascendente

	}

	public void insertarCalificacion() {
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");

			// si se ha conectado correctamente

			// System.out.println("Conexión Correcta.");

			// creo una sentencia
			Statement st = conexion.createStatement();

			// creo la consulta
			String consulta = "Insert into bdalumnos.calificaciones values( '" + dni2 + "', '" + asignatura + "', '"
					+ nota + "')";

			// ejecuto la sentencia
			st.executeUpdate(consulta);
			modificado = true;

			Vector<String> fila = new Vector<String>();
			fila.add(dni2);
			fila.add(asignatura);
			fila.add(nota);
			fila.add("\n\n\n\n\n\n\n");
			dtmTabla.addRow(fila);

			// si el registrado a sido insertado

			// cierro el Statement
			st.close();
			// cierro la conexion
			conexion.close();

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(this, (String) "Error SQL Numero " + e.getErrorCode() + ":" + e.getMessage(),
					"ERROR", JOptionPane.ERROR_MESSAGE, null);

		}
	}

	public void modificarcalificaiones() {
		try {
			// CONECTO LA BASE DE DATOS
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
			// creo una sentencia que pueda ir por delante y por detras
			Statement st = conexion.createStatement();
			String consulta;
			// creamos una variable y le hago dentro la operacion de la base de datos
			if (this.nota != oldnota) {
				consulta = "UPDATE bdalumnos.calificaciones set nota='" + this.nota + "'WHERE dni='" + alumnoac.getDni()
						+ "';";

				st.executeUpdate(consulta);

			}
			modificado = true;
			// cierro el Statement
			st.close();
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

	public void borrarcalificaiones() {
		try {
			// CONECTO LA BASE DE DATOS
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
			// creo una sentencia que pueda ir por delante y por detras
			Statement st = conexion.createStatement();
			String consulta;
			// obtengo el dni de la fila
			// creo una sentencia
			// creo la consulta
			dni2 = (String) table.getValueAt(filaseleccionada, 0);

			consulta = "DELETE FROM bdalumnos.calificaciones WHERE dni='" + dni2 + "' ";

			// ejecuto la sentencia
			st.executeUpdate(consulta);
			modificado = true;

			// cierro el Statement
			st.close();
			// cierro la conexion
			conexion.close();

			// si borro correctamente en la base de datos
			// lo borro tambien en la tabla
			this.dtmTabla.removeRow(filaseleccionada);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, (String) "Error, no hay ningún elemento seleccionado", "Error",
					JOptionPane.INFORMATION_MESSAGE, null);

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// acciones de botones
		Object boton = e.getSource();
		if (boton == btnInsertar) {
			if (verificarCampos(txtDni)) {
				dni = txtDni.getText();
				if (verificarCampos(txtNombre)) {
					nombre = txtNombre.getText();
					if (verificarCampos(txtApellidos)) {
						apellidos = txtApellidos.getText();
						if (verificarCampos(txtGrupo)) {
							grupo = txtGrupo.getText();

							insertaralumno();
						}
					}
				}
			}
		} else if (boton == btnBorrar) {
			if (verificarCamposborrar(txtDni)) {
				dni = txtDni.getText();
				if (verificarCamposborrar(txtNombre)) {
					nombre = txtNombre.getText();
					if (verificarCamposborrar(txtApellidos)) {
						apellidos = txtApellidos.getText();
						if (verificarCamposborrar(txtGrupo)) {
							grupo = txtGrupo.getText();
							borraralumnos();

							JOptionPane.showMessageDialog(this, (String) "Se ha borrado correctamente",
									"Borrado correctamente", JOptionPane.INFORMATION_MESSAGE, null);
						}
					}
				}
			}

		} else if (boton == btnActualizar) {
			if (verificarCampos(txtDni)) {
				dni = txtDni.getText();
				if (verificarCampos(txtNombre)) {
					nombre = txtNombre.getText();
					if (verificarCampos(txtApellidos)) {
						apellidos = txtApellidos.getText();
						if (verificarCampos(txtGrupo)) {
							grupo = txtGrupo.getText();
							modificaralumnos();
						}
					}
				}
			}
		} else if (boton == btnadelante) {

			indice = indice + 1;
			regActual = regActual + 1;

			pasardatos();

		} else if (boton == btnadelante2) {
			indice = alumno1.size() - 1;
			regActual = alumno1.size();
			btnatras.setEnabled(true);
			btnatras2.setEnabled(true);

			pasardatos();

		} else if (boton == btnatras) {
			indice = indice - 1;
			regActual = regActual - 1;
			pasardatos();

		} else if (boton == btnatras2) {
			indice = 0;
			regActual = 1;
			btnadelante.setEnabled(true);
			btnadelante2.setEnabled(true);
			pasardatos();
		} else if (boton == btnSalir) {
			salirdealumnos();

		}
		if (boton == btnInsertarC) {
			if (verificarCampos(txtNota)) {
				dni2 = originaldni;

				nota = txtNota.getText();
				asignatura = (String) cmbAsig.getSelectedItem();

				insertarCalificacion();

			}
		} else if (boton == btnBorrarC) {
			// Seleccionamos la fila
			filaseleccionada = table.getSelectedRow();
			if (filaseleccionada < 0) {
				// si no tenemos nada selccionado
				JOptionPane.showMessageDialog(this, (String) "Error. No hay elementos selccionados para borrar",
						"ERROR", JOptionPane.ERROR_MESSAGE, null);

			} else {
				// convertimos la fila seleccionada al valor real del dtm
				filaseleccionada = table.convertRowIndexToModel(filaseleccionada);
				borrarcalificaiones();
			}

		} else if (boton == btnActualizarC) {
			// Seleccionamos la fila
			filaseleccionada = table.getSelectedRow();
			
			if (filaseleccionada < 0) {
				// si no tenemos nada selccionado
				JOptionPane.showMessageDialog(this, (String) "Error. No hay elementos selccionados para Actualizar",
						"ERROR", JOptionPane.ERROR_MESSAGE, null);

			} else {
				// convertimos la fila seleccionada al valor real del dtm
				filaseleccionada = table.convertRowIndexToModel(filaseleccionada);
				modificarcalificaiones();

			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
	}
}
