package mx.shf6.produccion.view;

import java.io.IOException;
import java.util.ArrayList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.Cotizacion;
import mx.shf6.produccion.model.DetalleCotizacion;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.DetalleCotizacionDAO;
import mx.shf6.produccion.model.dao.Seguridad;
import mx.shf6.produccion.utilities.Notificacion;

public class DialogoCotizacion {
	
	//CONSTANTES
	public final static int CREAR = 0;
	public final static int EDITAR = 1;

	//PROPIEDADES
	private MainApp mainApp;
	private Cotizacion cotizacion;
	private DetalleCotizacion detalleCotizacion;
	private int cantidadRenglonesTabla;
	private int cantidadRegistrosTablaDetalleCotizaciones;
	private int cantidadPaginasTablaDetalleCotizaciones;
	private ArrayList<Cliente> listaClientes;
	private ObservableList<String> listaNombreClientes;
	private ObservableList<String> listaMonedas;
	private ArrayList<DetalleCotizacion> listaDetalleCotizaciones;
	
	//COMPONENTES INTERZAS USUARIO
	@FXML private ComboBox<String> clentesCombo;
	@FXML private TextField solicitoText;
	@FXML private TextField areaDepartamentoText;
	@FXML private TextField telefonoFaxText;
	@FXML private TextField emailText;
	@FXML private TableView<DetalleCotizacion> tablaDetalleCotizacion;
	@FXML private TableColumn<DetalleCotizacion, String> codigoColumna;
	@FXML private TableColumn<DetalleCotizacion, String> descripcionColumna;
	@FXML private TableColumn<DetalleCotizacion, Double> cantidadColumna;
	@FXML private TableColumn<DetalleCotizacion, String> observacionesColumna;
	@FXML private TableColumn<DetalleCotizacion, String> accionesColumn;
	@FXML private TextField fechaEntregaText;
	@FXML private TextField condicionEmbaruqueText;
	@FXML private TextField condicionPagoText;
	@FXML private ComboBox<String> monedasCombo;
	@FXML private TextField tipoCambioText;
	@FXML private TextField observacionesText;
	@FXML private TextField vigenciaText;
	@FXML private Pagination paginacionTablaDetalleCotizaciones;
	@FXML private TextField buscarProyecto;	
	
	//INICIALIZA COMPONENTES CONTROLAN INTERFAZ USUARIO
	@FXML private void initialize() {
		this.detalleCotizacion = new DetalleCotizacion();
		listaMonedas = FXCollections.observableArrayList();
		listaMonedas.add("MXN");
		listaMonedas.add("USD");
		listaMonedas.add("EUR");
		monedasCombo.setItems(listaMonedas);
		/*this.buscarProyecto.setOnKeyPressed(new EventHandler<KeyEvent>() {
    		@Override
    		public void handle(KeyEvent event) {
    			if (event.getCode().equals(KeyCode.ENTER))
    				buscarButtonHandler();
    		}//FIN METODO
    	});//FIN SENTENCIA
		this.inicializaTabla();*/
	}//FIN METODO
	
	//ACTUALIZA LA TABLA DE ACUERDO AL CRITERIO DE BÚSQUEDA
	@FXML private void buscarButtonHandler() {
    	if (Seguridad.verificarAcceso(this.mainApp.getConnection(), this.mainApp.getUsuario().getGrupoUsuarioFk(), "rClientes")) {
    		tablaDetalleCotizacion.setItems(null);
    		listaDetalleCotizaciones.clear();
    		listaDetalleCotizaciones = DetalleCotizacionDAO.readCotizacionDetalle(this.mainApp.getConnection(), this.detalleCotizacion.getCotizacionFK());
    		tablaDetalleCotizacion.setItems(DetalleCotizacionDAO.toObservableList(listaDetalleCotizaciones));
    	} else {
    		Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
    	}//FIN IF-ELSE
    	
    }//FIN METODO
	
	//ACCESO CLASE PRINCIPAL CONTROLA VISTAS
	public void setMainApp(MainApp mainApp, Cotizacion cotizacion, int opcion) {
		this.cotizacion = cotizacion;
		this.mainApp = mainApp;
		listaClientes = new ArrayList<Cliente>();
		listaNombreClientes = FXCollections.observableArrayList();
		listaClientes = ClienteDAO.readCliente(this.mainApp.getConnection());
		for (Cliente cliente : listaClientes) {
			this.listaNombreClientes.add(cliente.getNombre());
		}
		this.clentesCombo.setItems(listaNombreClientes);
		listaDetalleCotizaciones = DetalleCotizacionDAO.readCotizacionDetalle(this.mainApp.getConnection(), this.cotizacion.getSysPK());
		this.actualizarTabla();
		//asignarVariables();
	}//FIN METODO	
	
	//INICIALIZA LOS COMPONENTES DE LA TABLA DE COTIZACIONES
	private void inicializaTabla() {
		codigoColumna.setCellValueFactory(cellData -> cellData.getValue().getProyecto(this.mainApp.getConnection()).codigoProperty());
		descripcionColumna.setCellValueFactory(cellData -> cellData.getValue().getProyecto(this.mainApp.getConnection()).descripcionProperty());
		cantidadColumna.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
        observacionesColumna.setCellValueFactory(cellData -> cellData.getValue().observacionesProperty());
        
        accionesColumn.setCellValueFactory(new PropertyValueFactory<>("DUM"));
        Callback<TableColumn<DetalleCotizacion, String>, TableCell<DetalleCotizacion, String>> cellFactory =  param -> {
        	
        	final TableCell<DetalleCotizacion, String> cell = new TableCell<DetalleCotizacion, String>() {  
        		final Button botonVer = new Button("V");
        		final Button botonEliminar = new Button("B");
        		final Button botonEstadoCuenta = new Button("EC");
        		final Button botonCarpeta = new Button("C");
        		final Button botonArchivo = new Button("A");
        		final HBox acciones = new HBox(botonVer, botonEliminar, botonEstadoCuenta, botonCarpeta, botonArchivo);
        		
		        //PARA MOSTRAR LOS DIALOGOS DE INSTITUCION
		        @Override
		        public void updateItem(String item, boolean empty) {
		        	botonVer.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/ViewIcon.png"))));
		        	botonVer.setPrefSize(18.0, 18.0);
		        	botonVer.setPadding(Insets.EMPTY);
		        	botonVer.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonVer.setStyle("-fx-background-color: transparent;");		        	
		        	botonVer.setCursor(Cursor.HAND);
		        	botonEliminar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/RemoveIcon.png"))));
		        	botonEliminar.setPrefSize(16.0, 16.0);
		        	botonEliminar.setPadding(Insets.EMPTY);
		        	botonEliminar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonEliminar.setStyle("-fx-background-color: transparent;");
		        	botonEliminar.setCursor(Cursor.HAND);
		        	botonEstadoCuenta.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/AccountIcon.png"))));
		        	botonEstadoCuenta.setPrefSize(16.0, 16.0);
		        	botonEstadoCuenta.setPadding(Insets.EMPTY);
		        	botonEstadoCuenta.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonEstadoCuenta.setStyle("-fx-background-color: transparent;");
		        	botonEstadoCuenta.setCursor(Cursor.HAND);
		        	botonCarpeta.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/FolderIcon.png"))));
		        	botonCarpeta.setPrefSize(16.0, 16.0);
		        	botonCarpeta.setPadding(Insets.EMPTY);
		        	botonCarpeta.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonCarpeta.setStyle("-fx-background-color: transparent;");
		        	botonCarpeta.setCursor(Cursor.HAND);
		        	botonArchivo.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/DocumentIcon.png"))));
		        	botonArchivo.setPrefSize(16.0, 16.0);
		        	botonArchivo.setPadding(Insets.EMPTY);
		        	botonArchivo.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonArchivo.setStyle("-fx-background-color: transparent;");
		        	botonArchivo.setCursor(Cursor.HAND);
		        	acciones.setSpacing(5);
		        	acciones.setPrefWidth(80.0);
		        	acciones.setAlignment(Pos.CENTER_LEFT);
		        	super.updateItem(item, empty);
		        	if (empty) {
		        		setGraphic(null);
		                setText(null);
		            } else {
		            	
		            	//ABRE EL DIALOGO PARA VER LOS DATOS DE LA COTIZACION
		            	botonVer.setOnAction(event -> {
		            		//if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "rCliente")) {
		            		//	cotizacion = getTableView().getItems().get(getIndex());
			            	//	mainApp.iniciarDialogoClientes();
		            		//}else
		            		//	Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");		            		
		            	});//FIN LISTENER
		            	
		            	//ABRE EL DIALOGO PARA BORRAR LA COTIZACION
		            	botonEliminar.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "dCliente")) {
		            			detalleCotizacion = getTableView().getItems().get(getIndex());
			            		if (Notificacion.dialogoPreguntar("Confirmación para eliminar", "¿Desea eliminar " + detalleCotizacion.getProyecto(mainApp.getConnection()).getCodigo() + "?")){
			            			DetalleCotizacionDAO.deleteDetalleCotizacion(mainApp.getConnection(), detalleCotizacion);
			            			actualizarTabla();
			            		}//FIN IF
		            		} else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");		        					                	
		                });//FIN LISTENER
		            	
		            	//ABRE EL DIALOGO PARA EDITAR LOS DATOS DE LA COTIZACION
		            	botonEstadoCuenta.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "rCliente")) {
		            			actualizarTabla();
			                } else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
		            	});//FIN LISTENER
		            	
		            	botonCarpeta.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "rCliente")) {
		            			String ruta ="\\\\192.168.0.216\\Ingeniería y Planeación";
		            			try {
		            				Runtime.getRuntime().exec("explorer.exe /select, " + ruta);
								} catch (IOException e) {
									
									e.printStackTrace();
								}
			                } else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
		            	});//FIN LISTENER
		            	
		            	botonArchivo.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "rCliente")) {
		            			actualizarTabla();
			                } else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
		            	});//FIN LISTENER        	
		            		
		            	setGraphic(acciones);		                
		                setText(null);
		                
		            }//FIN IF/ELSE
		        }//FIN METODO
		    };//FIN METODO
		    
		    return cell;
		};//FIN METODO
		accionesColumn.setCellFactory(cellFactory);
    }//FIN METODO
	
	
	@FXML private void nuevaCotizacion() {
		//this.mainApp.iniciarDialogoClientes();
	}//FIN METODO	

	//ACTUALIZA LA TABLA CON LOS ULTIMOS CAMBIOS EN LA BASE DE DATOS
	private void actualizarTabla() {
		tablaDetalleCotizacion.setItems(null);
		listaDetalleCotizaciones.clear();
		listaDetalleCotizaciones = DetalleCotizacionDAO.readDetalleCotizacion(this.mainApp.getConnection());
		if (!listaDetalleCotizaciones.isEmpty()) {
			//this.asignarVariables();
			tablaDetalleCotizacion.setItems(DetalleCotizacionDAO.toObservableList(listaDetalleCotizaciones));
	    	buscarProyecto.setText("");	
			this.paginacionTablaDetalleCotizaciones.setDisable(false);
		} else
			this.paginacionTablaDetalleCotizaciones.setDisable(true);
	}//FIN METODO
	 
	private void asignarVariables() {
		this.cantidadRenglonesTabla = 4;
		this.cantidadRegistrosTablaDetalleCotizaciones = this.listaDetalleCotizaciones.size();
		if ((this.cantidadRegistrosTablaDetalleCotizaciones % this.cantidadRenglonesTabla) == 0)
			this.cantidadPaginasTablaDetalleCotizaciones = this.cantidadRegistrosTablaDetalleCotizaciones / this.cantidadRenglonesTabla;
		else
			this.cantidadPaginasTablaDetalleCotizaciones = (this.cantidadRegistrosTablaDetalleCotizaciones / this.cantidadRenglonesTabla) + 1;
		
		//INICIALIZA PAGINACION
			this.paginacionTablaDetalleCotizaciones.setPageFactory(this::createPaginaTablaCotizaciones);
			this.paginacionTablaDetalleCotizaciones.setMaxPageIndicatorCount(3);
			this.paginacionTablaDetalleCotizaciones.setPageCount(cantidadPaginasTablaDetalleCotizaciones);
    }//FIN METODO
	
	//PAGINACION DE LA TABLA COTIZACION
	private Node createPaginaTablaCotizaciones(int indicePagina) {
		int indiceInicial = indicePagina * this.cantidadRenglonesTabla;
		int indiceFinal = Math.min(indiceInicial + this.cantidadRenglonesTabla, this.cantidadRegistrosTablaDetalleCotizaciones);
		ObservableList<DetalleCotizacion> lista = DetalleCotizacionDAO.toObservableList(listaDetalleCotizaciones);
		tablaDetalleCotizacion.setItems(FXCollections.observableArrayList(lista.subList(indiceInicial, indiceFinal)));
		return tablaDetalleCotizacion;
	}//FIN METODO
	@FXML private void agregarProyecto() {
		this.mainApp.iniciarDialogoDetalleCotizacion(this.cotizacion);
	}//FIN METODO	

	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
}//FIN CLASE