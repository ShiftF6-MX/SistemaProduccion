package mx.shf6.produccion.view;

import java.sql.Connection;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.DetalleCardex;
import mx.shf6.produccion.model.Existencia;
import mx.shf6.produccion.model.dao.AlmacenDAO;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.ExistenciaDAO;


public class DialogoAgregarMovimientoComponente {

	// PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private ObservableList<String> observableListaComponentes;
	private DetalleCardex detalleCardex;
	private Componente componente;
	private Existencia existencia;

	// VARIABLES
	String almacenOrigen = "";
	int tipoMovimiento = -1;

	// CONSTANTES

	// COMPONENTES INTERFAZ
	@FXML private ComboBox<String> comboBoxComponentes;
	@FXML private TextField campoTextoCantidad;

	// METODOS
	@FXML
	private void initialize() {
	}// FIN METODO

	public void setMainApp(MainApp mainApp, String almacenOrigen, int tipoMovimiento) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.detalleCardex = new DetalleCardex();
		this.almacenOrigen = almacenOrigen;
		this.tipoMovimiento = tipoMovimiento;
		this.componente = new Componente();
		this.existencia = new Existencia();

		this.mostrarDatosInterfaz();
	}// FIN METODO

	public void mostrarDatosInterfaz() {
		if (this.tipoMovimiento == DialogoMovimientoInventario.ENTRADA)
			this.observableListaComponentes = ComponenteDAO.listaNumerosParte(conexion);
		else
			this.observableListaComponentes = ExistenciaDAO.readDescripcionComponente(conexion, almacenOrigen);

		this.comboBoxComponentes.setItems(observableListaComponentes);
	}// FIN METODO

	public boolean validacion() {
		if (this.comboBoxComponentes.getSelectionModel().getSelectedIndex() == -1) {
			// Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Componente\" no puede estar vacio");
			return false;
		} else if (this.campoTextoCantidad.getText().isEmpty()) {
			// Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Cantidad\" no puede estar vacio");
			return false;
		} // FIN IF/ESLE
		return true;
	}// FIN METODO

	private void accionarBotonAceptar() {
		if (validacion()){
			this.componente = ComponenteDAO.readComponenteNumeroParte(conexion, this.comboBoxComponentes.getValue());
			detalleCardex.setComponenteFK(componente.getSysPK());
			detalleCardex.setAlmacenFK(AlmacenDAO.readPorDescripcion(conexion, almacenOrigen).getSysPK());

			existencia = ExistenciaDAO.readExistencia(conexion, detalleCardex.getComponenteFK(), detalleCardex.getAlmacenFK());
			detalleCardex.setExistenciaComponente(existencia.getExistencia());

			if ((tipoMovimiento == DialogoMovimientoInventario.SALIDA || tipoMovimiento == DialogoMovimientoInventario.TRASPASO) && (detalleCardex.getExistenciaComponente() < Double.parseDouble(this.campoTextoCantidad.getText()))) {

			} else {
				detalleCardex.setDescripcionComponente(componente.getDescripcion());
				detalleCardex.setNoPartesComponente(componente.getNumeroParte());
				detalleCardex.setDescripcionAlmacen(almacenOrigen);

				if (this.tipoMovimiento == DialogoMovimientoInventario.ENTRADA)
					detalleCardex.setEntrada(Double.parseDouble(this.campoTextoCantidad.getText()));
				else
					detalleCardex.setSalida(Double.parseDouble(this.campoTextoCantidad.getText()));
			}//FIN IF
			this.mainApp.getEscenarioDialogosAlterno().close();
		}//FIIN IF

	}// FIN METODO

	public DetalleCardex getDetalleCardex() {
		return this.detalleCardex;
	}//FIN METODO

	// MANEJADOR DE COMPONENTES
	@FXML private void manejadorBotonAceptar() {
		this.accionarBotonAceptar();

	}// FIN METODO

	@FXML private void manejadorBotonCancelar() {
		this.mainApp.getEscenarioDialogosAlterno().close();
	}// FIN METODO
}
