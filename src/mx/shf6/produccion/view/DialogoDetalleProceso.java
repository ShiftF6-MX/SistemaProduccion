package mx.shf6.produccion.view;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.DetalleProceso;
import mx.shf6.produccion.model.dao.DetalleProcesoDAO;
import mx.shf6.produccion.utilities.PTableColumn;
import javafx.scene.control.TableCell;
import mx.shf6.produccion.utilities.Notificacion;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableColumn;

public class DialogoDetalleProceso {

	//PROPIEDADES
	private MainApp mainApp;
	private DetalleProceso detalleProceso;
	private ArrayList<DetalleProceso> listaDetalleProceso;
	
	//VARIABLES
	
	//COMPONENTES INTERFAZ USUARIO
	@FXML private TableView<DetalleProceso> tablaDetalleProceso;
	@FXML private PTableColumn<DetalleProceso, Integer> operacionColumna;
	@FXML private PTableColumn<DetalleProceso, String> descripcionColumna;
	@FXML private PTableColumn<DetalleProceso, Integer> tiempoPreparacionColumna;
	@FXML private PTableColumn<DetalleProceso, Integer> tiempoOperacionColumna;
	@FXML private PTableColumn<DetalleProceso, String> centroTrabajoColumna;
	@FXML private PTableColumn<DetalleProceso, String> grupoTrabajoColumna;
	@FXML private PTableColumn<DetalleProceso, Integer> procesoColumna;
	@FXML private PTableColumn<DetalleProceso, String> accionesColumna;
	@FXML private TextField buscarDetalleProceso;
	
	//INICIALIZAR COMPONENTES CONTROLAN INTERFAZ USUARIO
	@FXML private void incializae() {
		this.detalleProceso = new DetalleProceso();
		this.inicializaComponentes();
	}//FIN METODO
	
	//ACCESO A CLASE PRINCIPAL CONTROLA VISTAS
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.listaDetalleProceso = DetalleProcesoDAO.readDetalleProceso(this.mainApp.getConnection());
		this.inicializaTabla();
		this.actualizarTabla();
	}//FIN METODO
	
	private void inicializaComponentes() {
		this.buscarDetalleProceso.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					buscarDetalleProceso();
				}
			}//FIN METODO
		});//FIN SENTENCIA
	}//FIN METODO
	
	//INICIALIZA LOS COMPONENTES DE LA TABLA DE PROCESOS
	private void inicializaTabla() {
		this.operacionColumna.setCellValueFactory(cellData -> cellData.getValue().operacionProperty());
		this.descripcionColumna.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
		this.tiempoPreparacionColumna.setCellValueFactory(cellData -> cellData.getValue().tiempoPreparacionProperty());
		this.tiempoOperacionColumna.setCellValueFactory(cellData -> cellData.getValue().tiempoOperacionProperty());
		this.centroTrabajoColumna.setCellValueFactory(cellData -> cellData.getValue().nombreCentroTrabajoProperty());
		this.grupoTrabajoColumna.setCellValueFactory(cellData -> cellData.getValue().nombreGrupoTrabajoProperty());
		this.procesoColumna.setCellValueFactory(cellData -> cellData.getValue().procesoFKProperty());
		this.inicializarColumnaAcciones();
	}//FIN METODO
	
	//ACTUALIZA LA TABLA CON LOS ULTIMOS CAMBIOS EN LA BASE DE DATOS
	private void actualizarTabla() {
		this.tablaDetalleProceso.setItems(null);
		this.listaDetalleProceso.clear();
		this.listaDetalleProceso = DetalleProcesoDAO.readDetalleProceso(this.mainApp.getConnection());
		this.tablaDetalleProceso.setItems(DetalleProcesoDAO.toObservableList(this.listaDetalleProceso));
	}//FIN METODO
	
	//ACTUALIZA LA TABLA DE ACUERDO AL CRITERIO DE BÚSQUEDA
	@FXML private void buscarDetalleProceso() {
		this.tablaDetalleProceso.setItems(null);
		this.listaDetalleProceso.clear();
		this.listaDetalleProceso = DetalleProcesoDAO.readDetalleProceso(this.mainApp.getConnection(), this.buscarDetalleProceso.getText());
		this.tablaDetalleProceso.setItems(DetalleProcesoDAO.toObservableList(this.listaDetalleProceso));
	}//FIN METODO
	
	private void inicializarColumnaAcciones() {
		
		this.accionesColumna.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<DetalleProceso, String>, TableCell<DetalleProceso, String>> cellFactory = param -> {
			
			final TableCell<DetalleProceso, String> cell = new TableCell<DetalleProceso, String>() {
				final Button botonVer = new Button("Ver");
				final Button botonModificar = new Button("Modificar");
				final Button botonEliminar = new Button("Eliminar");
				final Button botonReporte = new Button("Ver reporte");
				final HBox acciones = new HBox(botonVer, botonModificar, botonEliminar,botonReporte);
				
				//PARA MOSTRAR LOS DIALOGOS
				@Override
				public void updateItem(String item, boolean empty) {
					botonVer.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/ViewIcon.png"))));
					botonVer.setPrefSize(16.0, 16.0);
					botonVer.setPadding(Insets.EMPTY);
					botonVer.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonVer.setStyle("-fx-background-color: transparent;");
					botonVer.setCursor(Cursor.HAND);
		        	botonVer.setTooltip(new Tooltip("Ver proceso"));
		        	
		        	botonModificar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/ActualizarIcono.png"))));
		        	botonModificar.setPrefSize(16.0, 16.0);
		        	botonModificar.setPadding(Insets.EMPTY);
		        	botonModificar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonModificar.setStyle("-fx-background-color: transparent;");
		        	botonModificar.setCursor(Cursor.HAND);
		        	botonModificar.setTooltip(new Tooltip("Modificar datos del proceso"));
		        	
		        	botonEliminar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/RemoveIcon.png"))));
		        	botonEliminar.setPrefSize(16.0, 16.0);
		        	botonEliminar.setPadding(Insets.EMPTY);
		        	botonEliminar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonEliminar.setStyle("-fx-background-color: transparent;");
		        	botonEliminar.setCursor(Cursor.HAND);
		        	botonEliminar.setTooltip(new Tooltip("Eliminar proceso"));
		        	
		        	botonReporte.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/DocumentIcon.png"))));
		        	botonReporte.setPrefSize(16.0, 16.0);
		        	botonReporte.setPadding(Insets.EMPTY);
		        	botonReporte.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonReporte.setStyle("-fx-background-color: transparent;");
		        	botonReporte.setCursor(Cursor.HAND);
		        	botonReporte.setTooltip(new Tooltip("Generar reporte"));
		        	
		        	acciones.setSpacing(3);
		        	acciones.setPrefWidth(80.0);
		        	acciones.setAlignment(Pos.CENTER_LEFT);
		        	super.updateItem(item, empty);
		        	
		        	if (empty) {
		        		super.setGraphic(null);
		        		super.setText(null);
		        	} else {
		        		//MANEJADORES PARA LOS BOTONES
		        		botonVer.setOnAction(event -> {
		        			detalleProceso = getTableView().getItems().get(getIndex());
		        			manejadorBotonVer(detalleProceso);
		        		});//FIN MANEJADOR
		        		
		        		botonModificar.setOnAction(event -> {
		        			detalleProceso = getTableView().getItems().get(getIndex());
		        			manejadorBotonModificar(detalleProceso);
		        		});//FIN MANEJADOR
		        		
		        		botonEliminar.setOnAction(event -> {
		        			detalleProceso = getTableView().getItems().get(getIndex());
		        			manejadorBotonEliminar(detalleProceso);
		        		});//FIN MANEJADOR
		        		
		        		botonReporte.setOnAction(event -> {
		        			detalleProceso = getTableView().getItems().get(getIndex());
		        			manejadorBotonReporte(detalleProceso);
		        		});//FIN MANEJADOR
		        		
		        		setGraphic(acciones);
		        		setText(null);
		        	}//FIN IF-ELSE
				}//FIN METODO
			};//FIN METODO
			return cell;
		};//FIN METODO
		accionesColumna.setCellFactory(cellFactory);
	}//FIN METODO
	
	//ACTUALIZAR DATOS
	@FXML private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}//FIN METODO
	
	//CREAR
	private void manejadorBotonCrear(DetalleProceso detalleProceso) {
		//this.mainApp.iniciarDialogoDetalleProceso(detalleProceso, DialogoDetalleProceso.CREAR);
		this.actualizarTabla();
	}//FIN METODO
	
	//VER DATOS
	private void manejadorBotonVer(DetalleProceso detalleProceso) {
		//this.mainApp.iniciarDialogoDetalleProceso(detalleProceso, DialogoDetalleProceso.MOSTRAR);
		this.actualizarTabla();
	}//FIN METODO
	
	//ELIMINAR PROCESO
	private void manejadorBotonEliminar(DetalleProceso detalleProceso) {
		if (Notificacion.dialogoPreguntar("", "Estas a punto de eliminar el registro, ¿Deseas continuar?"))
			DetalleProcesoDAO.deleteDetalleProceso(this.mainApp.getConnection(), detalleProceso);
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonModificar(DetalleProceso detalleProceso) {
		//this.mainApp.iniciarDialogoDetalleProceso(detalleProceso, DialogoDetalleProceso.EDITAR);
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonReporte(DetalleProceso detalleProceso) {
		//this.mainApp.iniciarReporte
	}//FIN METODO
	
}//FIN CLASE
