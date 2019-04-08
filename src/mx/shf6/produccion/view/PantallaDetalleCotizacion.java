package mx.shf6.produccion.view;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cotizacion;
import mx.shf6.produccion.model.DetalleCotizacion;
import mx.shf6.produccion.model.dao.DetalleCotizacionDAO;
import mx.shf6.produccion.utilities.Notificacion;

public class PantallaDetalleCotizacion {

	//PROPIEDADES
	private MainApp mainApp;
	private Cotizacion cotizacion;
	private ArrayList<DetalleCotizacion> listaDetalleCotizacion;
	
	//COMPONENTES INTERFAZ
	@FXML private TextField labelCotizacion;
	@FXML private TextField labelCliente;
	@FXML private TableView<DetalleCotizacion> tablaDetalleCotizacion;
	@FXML private TableColumn<DetalleCotizacion, String> columnaNumeroDibujo;
	@FXML private TableColumn<DetalleCotizacion, String> columnaDescripcion;
	@FXML private TableColumn<DetalleCotizacion, Double> columnaCantidad;
	@FXML private TableColumn<DetalleCotizacion, Double> columaPrecioUnitario;
	@FXML private TableColumn<DetalleCotizacion, String> columnaObservaciones;
	@FXML private MenuItem menuItemAgregarProyecto;
	@FXML private MenuItem menuItemQuitarProyecto;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Cotizacion cotizacion) {
		this.mainApp = mainApp;
		this.cotizacion = cotizacion;
		this.listaDetalleCotizacion = DetalleCotizacionDAO.readCotizacionDetalle(this.mainApp.getConnection(), cotizacion.getSysPK());
		this.labelCotizacion.setText(this.cotizacion.getReferencia());
		this.labelCliente.setText(this.cotizacion.getCliente(this.mainApp.getConnection()).getNombre());
		this.inicializaTabla();
		this.actualizarTabla();
	}//FIN METODO	
	
	private void inicializaTabla() {
		this.columnaNumeroDibujo.setCellValueFactory(cellData -> cellData.getValue().getProyecto(this.mainApp.getConnection()).codigoProperty());
		this.columnaDescripcion.setCellValueFactory(cellData -> cellData.getValue().getProyecto(this.mainApp.getConnection()).descripcionProperty());
		this.columnaCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
		this.columaPrecioUnitario.setCellValueFactory(cellData -> cellData.getValue().costoProperty());
		this.columnaObservaciones.setCellValueFactory(cellData -> cellData.getValue().observacionesProperty());
	}//FIN METODO
	
	private void actualizarTabla() {
		this.tablaDetalleCotizacion.setItems(null);
		this.listaDetalleCotizacion.clear();
		this.listaDetalleCotizacion = DetalleCotizacionDAO.readCotizacionDetalle(this.mainApp.getConnection(), this.cotizacion.getSysPK());
		this.tablaDetalleCotizacion.setItems(DetalleCotizacionDAO.toObservableList(this.listaDetalleCotizacion));
	}//FIN METODO
	
	@FXML private void btnCancelar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
	
	@FXML private void manejadorAgregar() {
		DetalleCotizacionDAO.createDetalleCotizacion(this.mainApp.getConnection(), this.mainApp.iniciarDialogoDetalleCotizacion(this.cotizacion));
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorQuitar() {
		if (Notificacion.dialogoPreguntar("Confirmación para eliminar", "¿Desea eliminar el proyecto de la cotización?")){
			DetalleCotizacionDAO.deleteDetalleCotizacion(mainApp.getConnection(), this.tablaDetalleCotizacion.getSelectionModel().getSelectedItem());
			actualizarTabla();
		}//FIN IF
	}//FIN METODO
		
}//FIN CLASE
