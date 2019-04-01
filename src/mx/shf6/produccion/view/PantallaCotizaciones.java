package mx.shf6.produccion.view;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cotizacion;
import mx.shf6.produccion.model.dao.CotizacionDAO;
import mx.shf6.produccion.model.dao.Seguridad;
import mx.shf6.produccion.utilities.Notificacion;

public class PantallaCotizaciones {

	//PROPIEDADES
	private MainApp mainApp;
	private Cotizacion cotizacion;
	private int cantidadRenglonesTabla;
	private int cantidadRegistrosTablaCotizaciones;
	private int cantidadPaginasTablaCotizaciones;
	private ArrayList<Cotizacion> listaCotizaciones;
	
	//COMPONENTES INTERZAS USUARIO
	@FXML private TableView<Cotizacion> tablaCotizacion;
	@FXML private TableColumn<Cotizacion, String> clienteColumna;
	@FXML private TableColumn<Cotizacion, String> referenciaColumna;
	@FXML private TableColumn<Cotizacion, Date> fechaColumna;
	@FXML private TableColumn<Cotizacion, String> statusColumna;
	@FXML private TableColumn<Cotizacion, String> observacionesColumna;
	@FXML private TableColumn<Cotizacion, String> accionesColumn;	
	@FXML private Pagination paginacionTablaCotizaciones;
	@FXML private TextField buscarCotizacion;	
	
	//INICIALIZA COMPONENTES CONTROLAN INTERFAZ USUARIO
	@FXML private void initialize() {
		this.cotizacion = new Cotizacion();
		this.buscarCotizacion.setOnKeyPressed(new EventHandler<KeyEvent>() {
    		@Override
    		public void handle(KeyEvent event) {
    			if (event.getCode().equals(KeyCode.ENTER))
    				buscarButtonHandler();
    		}//FIN METODO
    	});//FIN SENTENCIA
		this.inicializaTabla();
	}//FIN METODO
	
	//ACTUALIZA LA TABLA DE ACUERDO AL CRITERIO DE BÚSQUEDA
	@FXML private void buscarButtonHandler() {
    	if (Seguridad.verificarAcceso(this.mainApp.getConnection(), this.mainApp.getUsuario().getGrupoUsuarioFk(), "rClientes")) {
    		tablaCotizacion.setItems(null);
    		listaCotizaciones.clear();
    		listaCotizaciones = CotizacionDAO.readCotizacion(this.mainApp.getConnection(), buscarCotizacion.getText());
    		tablaCotizacion.setItems(CotizacionDAO.toObservableList(listaCotizaciones));
    	} else {
    		Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
    	}//FIN IF-ELSE
    	
    }//FIN METODO
	
	//ACCESO CLASE PRINCIPAL CONTROLA VISTAS
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		listaCotizaciones = CotizacionDAO.readCotizacion(this.mainApp.getConnection());
		this.actualizarTabla();
		//asignarVariables();
	}//FIN METODO	
	
	//INICIALIZA LOS COMPONENTES DE LA TABLA DE COTIZACIONES
	private void inicializaTabla() {
    	clienteColumna.setCellValueFactory(cellData -> cellData.getValue().getCliente(this.mainApp.getConnection()).nombreProperty());
        referenciaColumna.setCellValueFactory(cellData -> cellData.getValue().referenciaProperty());
        fechaColumna.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
        statusColumna.setCellValueFactory(cellData -> cellData.getValue().descripcionStatusProperty());
        observacionesColumna.setCellValueFactory(cellData -> cellData.getValue().observacionesProperty());
        
        accionesColumn.setCellValueFactory(new PropertyValueFactory<>("DUM"));
        Callback<TableColumn<Cotizacion, String>, TableCell<Cotizacion, String>> cellFactory =  param -> {
        	
        	final TableCell<Cotizacion, String> cell = new TableCell<Cotizacion, String>() {  
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
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "rCliente")) {
		            			cotizacion = getTableView().getItems().get(getIndex());
			            		//Inciar JASPER
		            		}else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");		            		
		            	});//FIN LISTENER
		            	
		            	//ABRE EL DIALOGO PARA BORRAR LA COTIZACION
		            	botonEliminar.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "dCliente")) {
		            			cotizacion = getTableView().getItems().get(getIndex());
			            		if (Notificacion.dialogoPreguntar("Confirmación para eliminar", "¿Desea eliminar la cotizacion " + cotizacion.getReferencia() + "?")){
			            			CotizacionDAO.deleteCotizacion(mainApp.getConnection(), cotizacion);
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
		this.mainApp.iniciarPantallaSecundariaCotizaciones(this.cotizacion, PantallaSecundariaCotizaciones.CREAR);
	}//FIN METODO	

	//ACTUALIZA LA TABLA CON LOS ULTIMOS CAMBIOS EN LA BASE DE DATOS
	private void actualizarTabla() {
		tablaCotizacion.setItems(null);
		listaCotizaciones.clear();
		listaCotizaciones = CotizacionDAO.readCotizacion(this.mainApp.getConnection());
		if (!listaCotizaciones.isEmpty()) {
			//this.asignarVariables();
			tablaCotizacion.setItems(CotizacionDAO.toObservableList(listaCotizaciones));
	    	buscarCotizacion.setText("");	
			this.paginacionTablaCotizaciones.setDisable(false);
		} else
			this.paginacionTablaCotizaciones.setDisable(true);
	}//FIN METODO
	 
	private void asignarVariables() {
		this.cantidadRenglonesTabla = 4;
		this.cantidadRegistrosTablaCotizaciones = this.listaCotizaciones.size();
		if ((this.cantidadRegistrosTablaCotizaciones % this.cantidadRenglonesTabla) == 0)
			this.cantidadPaginasTablaCotizaciones = this.cantidadRegistrosTablaCotizaciones / this.cantidadRenglonesTabla;
		else
			this.cantidadPaginasTablaCotizaciones = (this.cantidadRegistrosTablaCotizaciones / this.cantidadRenglonesTabla) + 1;
		
		//INICIALIZA PAGINACION
			this.paginacionTablaCotizaciones.setPageFactory(this::createPaginaTablaCotizaciones);
			this.paginacionTablaCotizaciones.setMaxPageIndicatorCount(3);
			this.paginacionTablaCotizaciones.setPageCount(cantidadPaginasTablaCotizaciones);
    }//FIN METODO
	
	//PAGINACION DE LA TABLA COTIZACION
	private Node createPaginaTablaCotizaciones(int indicePagina) {
		int indiceInicial = indicePagina * this.cantidadRenglonesTabla;
		int indiceFinal = Math.min(indiceInicial + this.cantidadRenglonesTabla, this.cantidadRegistrosTablaCotizaciones);
		ObservableList<Cotizacion> lista = CotizacionDAO.toObservableList(listaCotizaciones);
		tablaCotizacion.setItems(FXCollections.observableArrayList(lista.subList(indiceInicial, indiceFinal)));
		return tablaCotizacion;
	}//FIN METODO
}//FIN CLASE