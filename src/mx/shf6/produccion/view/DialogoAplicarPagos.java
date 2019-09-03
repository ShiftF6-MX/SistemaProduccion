package mx.shf6.produccion.view;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.AplicarCuentasXCobrar;
import mx.shf6.produccion.model.DocumentosCuentasXCobrar;
import mx.shf6.produccion.model.dao.AplicarCuentasXCobrarDAO;
import mx.shf6.produccion.model.dao.DocumentosCuentasXCobrarDAO;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoAplicarPagos {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private ArrayList<DocumentosCuentasXCobrar> listaDetalleRecibos;
	private DocumentosCuentasXCobrar documentosCuentasXCobrar;
	private ObservableList<DocumentosCuentasXCobrar> itemsTabla;
	private ArrayList<AplicarCuentasXCobrar> listaImportesAplicar;

	//VARIABLES
	Double sumaPagos = 0.0;
	Boolean aplicarPagos = false;

	//COMPONENTES INTERFAZ
	@FXML private TableView<DocumentosCuentasXCobrar> tablaDetalleRecibos;
	@FXML private PTableColumn<DocumentosCuentasXCobrar, String> columnaReferencia;
	@FXML private PTableColumn<DocumentosCuentasXCobrar, Date> columnaFecha;
	@FXML private PTableColumn<DocumentosCuentasXCobrar, Double> columnaImporte;
	@FXML private PTableColumn<DocumentosCuentasXCobrar, Double> columnaDisponible;
	@FXML private PTableColumn<DocumentosCuentasXCobrar, String> columnaImporteAplicar;
	@FXML private Label etiquetaSaldo;


	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
	}//FIN METODO

	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, DocumentosCuentasXCobrar documentosCuentasXCobrar) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.documentosCuentasXCobrar = documentosCuentasXCobrar;
		this.listaDetalleRecibos = new ArrayList<DocumentosCuentasXCobrar>();
		this.itemsTabla = FXCollections.observableArrayList();
		this.listaImportesAplicar = new ArrayList<AplicarCuentasXCobrar>();

		this.inicializaComponentes();
		this.inicializaTabla();
		this.actualizarTabla();
	}//FIN METODO

	private void inicializaComponentes() {
		this.etiquetaSaldo.setText("Saldo "+Double.toString(this.documentosCuentasXCobrar.getSaldo()));
	}
	private void inicializaTabla() {
		this.columnaReferencia.setCellValueFactory(cellData -> cellData.getValue().referenciaProperty());
		this.columnaFecha.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
		this.columnaImporte.setCellValueFactory(cellData -> cellData.getValue().haberProperty());
		this.columnaDisponible.setCellValueFactory(cellData -> cellData.getValue().xAplicarProperty());
		this.columnaImporteAplicar.setCellFactory(TextFieldTableCell.<DocumentosCuentasXCobrar>forTableColumn());

		this.columnaImporteAplicar.setOnEditCommit(data -> {
		   this.tablaDetalleRecibos.getSelectionModel().getSelectedItem().setImporteAplicar(Double.parseDouble(data.getNewValue()));
		   sumaPagos = sumaPagos + Double.parseDouble(data.getNewValue());
		});
	}//FIN METODO

	private void actualizarTabla() {
		this.tablaDetalleRecibos.setItems(null);
		this.listaDetalleRecibos.clear();
		this.listaDetalleRecibos = DocumentosCuentasXCobrarDAO.readRecibosPorAplicarCliente(conexion, this.documentosCuentasXCobrar.getClienteFK());
		this.tablaDetalleRecibos.setItems(FXCollections.observableArrayList(listaDetalleRecibos));
	}//FIN METODO

	private void validarAbonos() {
		if(this.sumaPagos <= this.documentosCuentasXCobrar.getSaldo() ){
			this.itemsTabla = this.tablaDetalleRecibos.getItems();
			for(DocumentosCuentasXCobrar documentosCuentasXCobrar : this.itemsTabla){
				if(documentosCuentasXCobrar.getImporteAplicar() != 0){
					if(documentosCuentasXCobrar.getXAplicar() >=  documentosCuentasXCobrar.getImporteAplicar()){
						AplicarCuentasXCobrar aplicarCuentasXCobrar = new AplicarCuentasXCobrar();
						aplicarCuentasXCobrar.setDcxcFK(documentosCuentasXCobrar.getSysPK());
						aplicarCuentasXCobrar.setAplicadoA(this.documentosCuentasXCobrar.getSysPK());
						aplicarCuentasXCobrar.setImporte(documentosCuentasXCobrar.getImporteAplicar());

						this.listaImportesAplicar.add(aplicarCuentasXCobrar);
						documentosCuentasXCobrar.setXAplicar(documentosCuentasXCobrar.getXAplicar() -  documentosCuentasXCobrar.getImporteAplicar());

						this.aplicarPagos = true;
					}
					else{
						this.aplicarPagos = false;
						break;
					}//FIN IF ELSE

				}//FIN IF

			}//FIN FOR
		}//FIN IF
	}//FIN METODO

	private void generarTransacciones() {
		if(this.aplicarPagos){
			for(AplicarCuentasXCobrar aplicarCuentasXCobrar : this.listaImportesAplicar){
				if(AplicarCuentasXCobrarDAO.create(conexion, aplicarCuentasXCobrar)){

				}
			}//FIN FOR
			for(DocumentosCuentasXCobrar documentosCuentasXCobrar : this.itemsTabla){
				if(DocumentosCuentasXCobrarDAO.updateXAplicar(conexion, documentosCuentasXCobrar)){

				}
			}//FIN FOR
			this.documentosCuentasXCobrar.setPagos(this.documentosCuentasXCobrar.getPagos() + sumaPagos);
			DocumentosCuentasXCobrarDAO.updatePagos(conexion, this.documentosCuentasXCobrar);
		}//FIN IF
	}//FIN METODO

	//MANEJADORES
	@FXML private void manejadorBotonAceptar() {
		this.validarAbonos();
		this.generarTransacciones();
		this.actualizarTabla();
	}//FIN METODO

	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN METODO

}
