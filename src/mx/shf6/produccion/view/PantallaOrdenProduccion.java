package mx.shf6.produccion.view;

import java.sql.Date;
import java.util.ArrayList;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.OrdenProduccion;
import mx.shf6.produccion.model.dao.OrdenProduccionDAO;
import mx.shf6.produccion.utilities.PTableColumn;

public class PantallaOrdenProduccion {
	
	//PROPIEDADES
	private MainApp mainApp;
	private OrdenProduccion ordenProduccion;
	private ArrayList<OrdenProduccion> listaOrdenProduccion;
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoBusqueda;
	@FXML private TableView<OrdenProduccion> tablaOrdenProduccion;
	@FXML private PTableColumn<OrdenProduccion, Date> columnaFecha;
	@FXML private PTableColumn<OrdenProduccion, String> columnaLote;
	@FXML private PTableColumn<OrdenProduccion, String> columnaCliente;
	@FXML private PTableColumn<OrdenProduccion, String> columnaCotizacion;
	@FXML private PTableColumn<OrdenProduccion, String> columnaProyecto;
	@FXML private PTableColumn<OrdenProduccion, String> columnaComponente;
	@FXML private PTableColumn<OrdenProduccion, String> columnaStatus;
	@FXML private PTableColumn<OrdenProduccion, String> columnaAcciones;
	@FXML private ComboBox<String> comboStatus;
	@FXML private DatePicker fechaInicial;
	@FXML private DatePicker fechaFinal;
	
	//METODO COMPONENTES INTERFAZ
	@FXML private void initialize() {
		this.ordenProduccion = new OrdenProduccion();
		inicializarComponentes();
		comboStatus();
	}//FIN METODO
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.listaOrdenProduccion = OrdenProduccionDAO.readOrdenProduccion(this.mainApp.getConnection());
		this.inicializaTabla();
		this.actualizarTabla();
	}//FIN METODO
	
	private void inicializarComponentes() {
		this.campoTextoBusqueda.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override 
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					buscarOrdenProduccion();
				}//FIN IF
			}//FIN METODO
		});//FIN SENTENCIA
		
		this.fechaInicial.setValue(new Date(System.currentTimeMillis()).toLocalDate());
		this.fechaFinal.setValue(new Date(System.currentTimeMillis()).toLocalDate());
		
		this.fechaInicial.valueProperty().addListener((ov, oldValue, newValue) -> {
			buscarFecha();
		});//FIN LISTENER
		
		this.fechaFinal.valueProperty().addListener((ov, oldValue, newValue) -> {
			buscarFecha();
		});//FIN LISTENER
		
		this.comboStatus.valueProperty().addListener((ov, oldValue, newValue) -> {
			buscarCombo();
		});//FIN LISTENER
	}//FIN METODO
	
	private void buscarFecha() {
		this.tablaOrdenProduccion.setItems(null);
		this.listaOrdenProduccion.clear();
		this.listaOrdenProduccion = OrdenProduccionDAO.dateOrdenProduccion(this.mainApp.getConnection(), Date.valueOf(this.fechaInicial.getValue()), Date.valueOf(this.fechaFinal.getValue()));
		this.tablaOrdenProduccion.setItems(OrdenProduccionDAO.toObservableList(this.listaOrdenProduccion));
	}//FIN METODO
	
	private void buscarCombo() {
		this.tablaOrdenProduccion.setItems(null);
		this.listaOrdenProduccion.clear();
		if (comboStatus.getValue() == "Todos")
			actualizarTabla();
		else {
			int combo = 0;
			if (comboStatus.getValue() == "Pendiente")
				combo = 0; 
			if (comboStatus.getValue() == "En proceso")
				combo = 1;
			if (comboStatus.getValue() == "Terminado")
				combo = 2;
			this.listaOrdenProduccion = OrdenProduccionDAO.statusOrdenProduccion(this.mainApp.getConnection(), combo);
			this.tablaOrdenProduccion.setItems(OrdenProduccionDAO.toObservableList(this.listaOrdenProduccion));
		}//FIN IF ELSE
	}//FIN METODO
	
	//INICIALIZA LOS COMPONENTES DE LA TABLA DE ORDENPRODUCCION
	private void inicializaTabla() {
		this.columnaFecha.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
		this.columnaLote.setCellValueFactory(cellData -> cellData.getValue().loteProperty());
		this.columnaCliente.setCellValueFactory(cellData -> cellData.getValue().clienteProperty());
		this.columnaCotizacion.setCellValueFactory(cellData -> cellData.getValue().cotizacionProperty());
		this.columnaProyecto.setCellValueFactory(cellData -> cellData.getValue().proyectoProperty());
		this.columnaComponente.setCellValueFactory(cellData -> cellData.getValue().componenteProperty());
		this.columnaStatus.setCellValueFactory(cellData -> cellData.getValue().descripcionStatusProperty());
		this.iniciarColumnaAcciones();
	}//FIN METODO
	
	private void actualizarTabla() {
		this.tablaOrdenProduccion.setItems(null);
		this.listaOrdenProduccion.clear();
		this.listaOrdenProduccion = OrdenProduccionDAO.readOrdenProduccion(this.mainApp.getConnection());
		this.tablaOrdenProduccion.setItems(OrdenProduccionDAO.toObservableList(this.listaOrdenProduccion));
	}//FIN METODO
	
	@FXML private void buscarOrdenProduccion() {
		this.tablaOrdenProduccion.setItems(null);
		this.listaOrdenProduccion.clear();
		this.listaOrdenProduccion = OrdenProduccionDAO.searchOrdenProduccion(this.mainApp.getConnection(), this.campoTextoBusqueda.getText());
		this.tablaOrdenProduccion.setItems(OrdenProduccionDAO.toObservableList(this.listaOrdenProduccion));
	}//FIN METODO
	
	private void comboStatus() {
		ObservableList<String> status = FXCollections.observableArrayList("Todos","Pendiente", "En proceso", "Terminado");
		this.comboStatus.getItems().addAll(status);
	}//FIN METODO
	
	private void iniciarColumnaAcciones() {
		
		this.columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<OrdenProduccion, String>, TableCell<OrdenProduccion, String>> cellFactory = param -> {
			
			final TableCell<OrdenProduccion, String> cell = new TableCell<OrdenProduccion, String>() {
				final Button botonVerDetalleOrdenProduccion = new Button("VerDetalleOrdenProduccion");
				final HBox acciones = new HBox(botonVerDetalleOrdenProduccion);
				
				//PARA MOSTRAR LOS DIALOGOS
				@Override
				public void updateItem(String item, boolean empty) {
					botonVerDetalleOrdenProduccion.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/DetalleIcono.png"))));
					botonVerDetalleOrdenProduccion.setPrefSize(16.0, 16.0);
					botonVerDetalleOrdenProduccion.setPadding(Insets.EMPTY);
					botonVerDetalleOrdenProduccion.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonVerDetalleOrdenProduccion.setStyle("-fx-background-color: transparent;");
					botonVerDetalleOrdenProduccion.setCursor(Cursor.HAND);
					botonVerDetalleOrdenProduccion.setTooltip(new Tooltip("Detalles de la orden de producción"));
					
					acciones.setSpacing(3);
		        	acciones.setPrefWidth(80.0);
		        	acciones.setAlignment(Pos.CENTER_LEFT);
		        	super.updateItem(item, empty);
		        	
		        	if (empty) {
		        		super.setGraphic(null);
		                super.setText(null);
		        	} else {
		        		botonVerDetalleOrdenProduccion.setOnAction(event -> {
		        			ordenProduccion = getTableView().getItems().get(getIndex());
		        			manejadorBotonVerDetalleOrdenProduccion(ordenProduccion);
		        		});
		        		
		        		setGraphic(acciones);
		        		setText(null);
		        	}//FIN IF ELSE
				}//FIN METODO
			};//FIN METODO
			return cell;
		};//FIN METODO
		columnaAcciones.setCellFactory(cellFactory);
	}//FIN METODO
	
	//ACTUALIZAR DATOS
	@FXML private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}//FIN METODO
	
	//VER DETALLES DE ORDEN DE PRODUCCION
	private void manejadorBotonVerDetalleOrdenProduccion(OrdenProduccion ordenProduccion) {
		//this.mainApp.
	}//FIN METODO
	
}//FIN CLASE
