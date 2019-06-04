package mx.shf6.produccion.view;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Almacen;
import mx.shf6.produccion.model.Cardex;
import mx.shf6.produccion.model.Categoria;
import mx.shf6.produccion.model.DetalleCardex;
import mx.shf6.produccion.model.Existencia;
import mx.shf6.produccion.model.dao.AlmacenDAO;
import mx.shf6.produccion.model.dao.CardexDAO;
import mx.shf6.produccion.model.dao.CategoriaDAO;
import mx.shf6.produccion.model.dao.DetalleCardexDAO;
import mx.shf6.produccion.model.dao.DetalleCotizacionDAO;
import mx.shf6.produccion.model.dao.ExistenciaDAO;
import mx.shf6.produccion.model.dao.FolioDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoMovimientoInventario {

	// PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private ObservableList<String> observableListAlmacen;
	private ObservableList<String> observableListCategoria;
	private ArrayList<DetalleCardex> listaDetalleCardex;
	private DetalleCardex detalleCardex;
	private DecimalFormat decimalFormat;
	private Cardex cardex;
	private ArrayList<Categoria> listaCategoria;
	private Existencia existencia;


	// VARIABLES
	private int tipoMovimiento;
	private String almacenOrigen;



	// CONSTANTES
	public final static int ENTRADA = 0;
	public final static int SALIDA = 1;
	public final static int TRASPASO = 2;

	// COMPONENTES INTERFAZ
	@FXML private TableView<DetalleCardex> tablaMovimientos;
	@FXML private PTableColumn<DetalleCardex, String> columnaNumeroParte;
	@FXML private PTableColumn<DetalleCardex, String> columnaComponente;
	@FXML private PTableColumn<DetalleCardex, Double> columnaExistenciaActual;
	@FXML private PTableColumn<DetalleCardex, Double> columnaCantidad;
	@FXML private PTableColumn<DetalleCardex, Double> columnaExistenciaNueva;
	@FXML private Label etiquetaTitulo;
	@FXML private ComboBox<String> comboBoxAlmacenOrigen;
	@FXML private ComboBox<String> comboBoxAlmacenDestino;
	@FXML private ComboBox<String> comboBoxCategoria;
	@FXML private MenuItem menuItemAgregarComponente;
	@FXML private MenuItem menuItemQuitarComponente;
	@FXML private TextField campoTextoReferencia;
	@FXML private DatePicker selectorFecha;
	@FXML private TextArea areaTextoNotas;


	// METODOS
	@FXML
	private void initialize() {
	}// FIN METODO

	public void setMainApp(MainApp mainApp, Integer tipoMovimiento) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.tipoMovimiento = tipoMovimiento;
		this.observableListAlmacen = AlmacenDAO.readDescripcion(conexion);
		this.observableListCategoria = CategoriaDAO.readDescripciones(conexion);
		this.almacenOrigen = "";
		this.listaDetalleCardex = new ArrayList<DetalleCardex>();
		this.decimalFormat = new DecimalFormat("00000000");
		this.listaCategoria = new ArrayList<Categoria>();
		this.existencia = new Existencia();
		this.cardex = new Cardex();
		this.mostrarDatosInterfaz();
		inicializarTabla();
	}// FIN METODO

	public void mostrarDatosInterfaz() {
		if (this.tipoMovimiento == 0){
			etiquetaTitulo.setText("Entrada");
			comboBoxAlmacenDestino.setDisable(true);
		}else if (this.tipoMovimiento == 1){
			etiquetaTitulo.setText("Salida");
			comboBoxAlmacenDestino.setDisable(true);
		}else if (this.tipoMovimiento == 2)
			etiquetaTitulo.setText("Traspaso");

		campoTextoReferencia.setText("MVI" + decimalFormat.format((FolioDAO.readSerieMovimientoIventario(conexion, "MV") + 1)));
		campoTextoReferencia.setDisable(true);

		selectorFecha.setEditable(false);
		selectorFecha.setValue(new Date(System.currentTimeMillis()).toLocalDate());

		comboBoxAlmacenOrigen.setEditable(false);
		comboBoxAlmacenOrigen.setItems(observableListAlmacen);

		comboBoxAlmacenDestino.setEditable(false);
		comboBoxAlmacenDestino.setItems(observableListAlmacen);

		comboBoxCategoria.setEditable(false);
		comboBoxCategoria.setItems(observableListCategoria);

	}// FIN METODO

	private void inicializarTabla() {
		this.columnaNumeroParte.setCellValueFactory(cellData -> cellData.getValue().noPartesComponenteProperty());
		this.columnaComponente.setCellValueFactory(cellData -> cellData.getValue().descripcionComponenteProperty());
		if (this.tipoMovimiento == DialogoMovimientoInventario.ENTRADA)
			this.columnaCantidad.setCellValueFactory(cellData -> cellData.getValue().entradaProperty());
		else
			this.columnaCantidad.setCellValueFactory(cellData -> cellData.getValue().salidaProperty());
		this.columnaExistenciaActual.setCellValueFactory(cellData -> cellData.getValue().existenciaComponenteProperty());
		 this.columnaExistenciaNueva.setCellValueFactory(cellData -> cellData.getValue().nuevaExistenciaProperty());
	}// FIN METODO

	private void actualizarTabla() {
		this.tablaMovimientos.setItems(null);
		this.tablaMovimientos.setItems(FXCollections.observableArrayList(listaDetalleCardex));
		this.tablaMovimientos.refresh();
	}//FIN METODO

	public boolean validacion() {
		if (this.comboBoxAlmacenOrigen.getSelectionModel().getSelectedIndex() == -1) {
			// Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo\ "Almacen Origen\" no puede estar vacio");
			return false;
		} else if ((this.comboBoxAlmacenDestino.getSelectionModel().getSelectedIndex() == -1)
				&& (this.tipoMovimiento == 2)) {
			// Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \ "Almacen Destino\" no puede estar vacio");
			return false;
		} else if (this.comboBoxCategoria.getSelectionModel().getSelectedIndex() == -1) {
			// Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Categor�a\" no puede estar vacio");
			return false;
		} else if (this.areaTextoNotas.getText().isEmpty()) {
			// Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Notas\" no puede estar vacio");
			return false;
		}
		return true;
	}// FIN METODO

	private boolean generarCardex() {

		this.cardex.setFecha(Date.valueOf(this.selectorFecha.getValue()));
		this.cardex.setReferencia(this.campoTextoReferencia.getText());
		this.cardex.setNota(this.areaTextoNotas.getText());
		this.cardex.setTipo(this.tipoMovimiento);
		this.listaCategoria = CategoriaDAO.readTodos(conexion);
		for (Categoria categoria : listaCategoria)
			if (categoria.getDescripcion().equals(this.comboBoxCategoria.getValue()))
				this.cardex.setCategoriaFK(categoria.getSysPK());

		if(CardexDAO.create(conexion, cardex))
			return true;
		else
			return false;

	}//FIN METODO

	private boolean generarMovimiento(ArrayList<DetalleCardex> listaDetalleCardex) {
		int i = 0;
		int auxiliar = 2;
		int cardexFK = CardexDAO.ultimoSysPK(conexion);

		if (this.tipoMovimiento != DialogoMovimientoInventario.TRASPASO)
			auxiliar = 1;

		for (DetalleCardex detalleCardex : listaDetalleCardex) {

			do {

				if (i == 1 && this.tipoMovimiento == DialogoMovimientoInventario.TRASPASO) {
					detalleCardex.setEntrada(detalleCardex.getSalida());
					detalleCardex.setSalida(0.0);
					detalleCardex.setAlmacenFK(AlmacenDAO.readPorDescripcion(conexion, this.comboBoxAlmacenDestino.getValue()).getSysPK());
				}

				detalleCardex.setCardexFK(cardexFK);

				try {
					if (DetalleCardexDAO.create(conexion, detalleCardex)) {
						this.existencia = ExistenciaDAO.readExistencia(this.conexion, detalleCardex.getComponenteFK(),
								detalleCardex.getAlmacenFK());
						this.existencia.setComponenteFK(detalleCardex.getComponenteFK());
						this.existencia.setAlmacenFK(detalleCardex.getAlmacenFK());
						this.existencia.setExistencia(this.existencia.getExistencia() + detalleCardex.getEntrada()
								- detalleCardex.getSalida());

						if (this.existencia.getSysPk() != 0) {
							if (ExistenciaDAO.update(conexion, existencia)) {
								this.conexion.commit();
								this.conexion.setAutoCommit(true);
								// Notificacion.dialogoAlerta(AlertType.INFORMATION,
								// "", "�El registro se guard� de forma
								// correcta!");
							} else {
								this.conexion.rollback();
								this.conexion.setAutoCommit(true);
								// Notificacion.dialogoAlerta(AlertType.INFORMATION,
								// "", "No se pudo guardar el registro, �revisa
								// que la informaci�n sea correcta!");
							}
						} else {
							if (ExistenciaDAO.create(conexion, existencia)) {
								this.conexion.commit();
								this.conexion.setAutoCommit(true);
								// Notificacion.dialogoAlerta(AlertType.INFORMATION,
								// "", "�El registro se guard� de forma
								// correcta!");
							} else {
								this.conexion.rollback();
								this.conexion.setAutoCommit(true);
								// Notificacion.dialogoAlerta(AlertType.INFORMATION,
								// "", "No se pudo guardar el registro, �revisa
								// que la informaci�n sea correcta!");
							}
						} // FIN IF
					} else {
						this.conexion.rollback();
						this.conexion.setAutoCommit(true);
						return false;
						// Notificacion.dialogoAlerta(AlertType.INFORMATION, "",
						// "No se pudo guardar el registro, �revisa que la
						// informaci�n sea correcta!");
					} // FIN IF
				} catch (SQLException e) {

				}
				i++;
			} while (i < auxiliar);
			i = 0;

		}//FIN FOR
		return true;
	}//FIN METODO

	private void accionarBotonAceptar() {
		try {
			this.conexion.setAutoCommit(false);
			if (generarCardex()) {
				if (generarMovimiento(listaDetalleCardex)) {
//					this.conexion.commit();
//					this.conexion.setAutoCommit(true);
				} else {
					this.conexion.rollback();
					this.conexion.setAutoCommit(true);
				}
			} else {
				this.conexion.rollback();
				this.conexion.setAutoCommit(true);
				// Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo guardar el registro, �revisa que la informaci�n sea correcta!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//FIN TRY/CATCH
	}// FIN METODO

	// MANEJADOR DE COMPONENTES

	@FXML
	private void manejadorAgregar() {
		if (validacion()) {
			boolean detalleCardexExistente = true;
			this.almacenOrigen = comboBoxAlmacenOrigen.getValue();
			this.detalleCardex = new DetalleCardex();
			this.detalleCardex = this.mainApp.iniciarDialogoAgregarMovimientoComponente(almacenOrigen, this.tipoMovimiento);

			if (this.listaDetalleCardex.isEmpty() && this.detalleCardex.getComponenteFK() != 0)
				this.listaDetalleCardex.add(this.detalleCardex);
			else {
				for (DetalleCardex detalleCardex1 : this.listaDetalleCardex) {
					if ((detalleCardex1.getDescripcionAlmacen().equals(this.detalleCardex.getDescripcionAlmacen())) && (detalleCardex1.getDescripcionComponente().equals(this.detalleCardex.getDescripcionComponente()))) {
						detalleCardex1.setSalida(this.detalleCardex.getSalida());
						detalleCardex1.setEntrada(this.detalleCardex.getEntrada());
						detalleCardexExistente = true;
						break;
					}else{
						detalleCardexExistente = false;
					}//FIN ELSE/ IF
				}//FIN FOR

				if(detalleCardexExistente == false)
					this.listaDetalleCardex.add(this.detalleCardex);
			}
		}
		actualizarTabla();
	}// FIN METODO

	@FXML
	private void manejadorQuitar() {

		if (this.tablaMovimientos.getSelectionModel().getSelectedItem() != null) {
			for (DetalleCardex detalleCardex : listaDetalleCardex)
				if (detalleCardex.getSysPK() == this.tablaMovimientos.getSelectionModel().getSelectedItem().getSysPK())
					this.listaDetalleCardex.remove(detalleCardex);
		}
		actualizarTabla();


	}// FIN METODO

	@FXML
	private void manejadorBotonAceptar() {
		this.accionarBotonAceptar();
		this.mainApp.getEscenarioDialogos().close();
	}// FIN METODO

	@FXML
	private void manejadorBotonCancelar() {
		this.mainApp.getEscenarioDialogos().close();
	}// FIN METODO

	@FXML
	private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}// FIN METODO

}