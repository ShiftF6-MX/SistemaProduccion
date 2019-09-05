package mx.shf6.produccion.view;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.DetalleComponente;
import mx.shf6.produccion.model.DetalleHojaViajera;
import mx.shf6.produccion.model.HojaViajera;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.DetalleHojaViajeraDAO;
import mx.shf6.produccion.utilities.GenerarDocumento;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoDetalleHojaViajera {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private HojaViajera hojaViajera;
	private ArrayList<DetalleComponente> listaPartePrimaria;

	//VARIABLES
	Double cantidad = 0.0;
	int i = 0;
	int tamañoArrayPartesPrimarias = 0;
	String nombreNumeroComponente;

	//CONSTANTES

	//COMPONENTES INTERFAZ
	@FXML private TableView<DetalleHojaViajera> tablaDetalleHojaViajera;
	@FXML private PTableColumn<DetalleHojaViajera, Integer> columnaOperacion;
	@FXML private PTableColumn<DetalleHojaViajera, String> columnaProceso;
	@FXML private PTableColumn<DetalleHojaViajera, Double> columnaEnProceso;
	@FXML private PTableColumn<DetalleHojaViajera, Double> ColumnaTerminado;
	@FXML private PTableColumn<DetalleHojaViajera, Timestamp> columnaFHInicio;
	@FXML private PTableColumn<DetalleHojaViajera, Timestamp> columnaFHFinal;
	@FXML private Label campoTextoTitulo;
	@FXML private Label campoTextoSubtitulo;


	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		inicializarTabla();
	}// FIN METODO


	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, HojaViajera hojaViajera) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.hojaViajera = hojaViajera;
		this.listaPartePrimaria = new ArrayList<DetalleComponente>();
		
		actualizarTabla();
		inicializarComponentes();
	}//FIN METODO

	private void inicializarTabla() {
		this.columnaOperacion.setCellValueFactory(cellData -> cellData.getValue().detalleProcesoOperacionProperty());
		this.columnaProceso.setCellValueFactory(cellData -> cellData.getValue().detalleProcesoDescripcionProperty());
		this.columnaEnProceso.setCellValueFactory(cellData -> cellData.getValue().cantidadEnProcesoProperty());
		this.ColumnaTerminado.setCellValueFactory(cellData -> cellData.getValue().cantidadTerminadoProperty());
		this.columnaFHInicio.setCellValueFactory(cellData -> cellData.getValue().fechaHoraInicioProperty());
		this.columnaFHFinal.setCellValueFactory(cellData -> cellData.getValue().fechaHoraFinalProperty());
	}//FIN METODO
	
	private void inicializarComponentes() {
		this.campoTextoTitulo.setText(ComponenteDAO.readComponente(this.conexion, this.hojaViajera.getComponenteFK()).getNumeroParte());
		this.campoTextoSubtitulo.setText("Control de producción de componentes primarios");
	}//FIN METODO
	
	private void actualizarTabla(){
		this.tablaDetalleHojaViajera.setItems(null);
		this.tablaDetalleHojaViajera.setItems(FXCollections.observableArrayList(DetalleHojaViajeraDAO.readHojaViajeraPorOrdenProduccionComponente(this.conexion, this.hojaViajera.getSysPK())));
	}//FIN METODO

	
	@FXML private void manejadorBotonAceptar() {
		
	}//FIN METODO

	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlternoSecundario().close();
	}//FIN METODO

	@FXML private void manejadorBotonImprimir() {
		GenerarDocumento.generaListaMateriales(conexion, listaPartePrimaria, this.nombreNumeroComponente);
	}//FIN METODO

}//FIN CLASE
