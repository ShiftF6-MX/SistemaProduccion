package mx.shf6.produccion.view;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
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
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Proyecto;
import mx.shf6.produccion.model.dao.ProyectoDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class PantallaProyectos {
	//PROPIEDADES
	private MainApp mainApp;
	private Proyecto proyecto;
	private ArrayList<Proyecto> listaProyecto;
	
	//VARIABLES
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoBusqueda;
	@FXML private TableView<Proyecto> tablaProyecto;
	@FXML private PTableColumn<Proyecto, Integer> columnaSysPK;
	@FXML private PTableColumn<Proyecto, String> columnaCodigo;
	@FXML private PTableColumn<Proyecto, String> columnaDescripcion;
	//@FXML private PTableColumn<Proyecto, String> columnaStatus;
	@FXML private PTableColumn<Proyecto, String> columnaAcciones;
		
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.proyecto = new Proyecto();
		this.inicializaComponentes();
		this.inicializaTabla();
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.listaProyecto = ProyectoDAO.readProyecto(this.mainApp.getConnection());
		this.actualizarTabla();
	}//FIN METODO
	
	private void inicializaComponentes() {
		this.campoTextoBusqueda.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER))
					buscarRegistroTabla();
			}//FIN METODO
			
		});//FIN SENTENCIA
	}//FIN METODO
	
	private void inicializaTabla() {
		this.columnaSysPK.setCellValueFactory(cellData -> cellData.getValue().sysPKProperty());
		this.columnaCodigo.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
		this.columnaDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
		//this.columnaStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
		this.inicializarColumnaAcciones();
	}//FIN METODO.
	
	private void actualizarTabla() {
		this.tablaProyecto.setItems(null);
		this.listaProyecto.clear();
		this.listaProyecto = ProyectoDAO.readProyecto(this.mainApp.getConnection());
		this.tablaProyecto.setItems(ProyectoDAO.toObservableList(this.listaProyecto));
	}//FIN METODO
		
	@FXML private void buscarRegistroTabla() {
		this.tablaProyecto.setItems(null);
		this.listaProyecto.clear();
		this.listaProyecto = ProyectoDAO.readProyecto(this.mainApp.getConnection(), this.campoTextoBusqueda.getText());
		this.tablaProyecto.setItems(ProyectoDAO.toObservableList(this.listaProyecto));
	}//FIN METODO
	
	private void inicializarColumnaAcciones() {
		this.columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<Proyecto, String>, TableCell<Proyecto, String>> cellFactory = param -> {
			final TableCell<Proyecto, String> cell = new TableCell<Proyecto, String>() {
				final Button botonVer = new Button("Ver");
				final Button botonEditar = new Button("Editar");
				final Button botonEliminar = new Button("Eliminar");
				final HBox cajaBotones = new HBox(botonVer, botonEditar, botonEliminar);
				
				@Override
				public void updateItem(String item, boolean empty) {
					//INICALIZA BOTONES
					botonVer.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/VerIcono.png"))));
					botonVer.setPrefSize(16.0, 16.0);
					botonVer.setPadding(Insets.EMPTY);
					botonVer.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonVer.setStyle("-fx-background-color: transparent");
					botonVer.setCursor(Cursor.HAND);
					botonVer.setTooltip(new Tooltip("Ver registro"));
					
					botonEditar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/ActualizarIcono.png"))));
					botonEditar.setPrefSize(16.0, 16.0);
					botonEditar.setPadding(Insets.EMPTY);
					botonEditar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonEditar.setStyle("-fx-background-color: transparent");
					botonEditar.setCursor(Cursor.HAND);
					botonEditar.setTooltip(new Tooltip("Editar registro"));
					
					botonEliminar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/EliminarIcono.png"))));
					botonEliminar.setPrefSize(16.0, 16.0);
					botonEliminar.setPadding(Insets.EMPTY);
					botonEliminar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonEliminar.setStyle("-fx-background-color: transparent");
					botonEliminar.setCursor(Cursor.HAND);
					botonEliminar.setTooltip(new Tooltip("Eliminar regsitro"));
					
					super.updateItem(item, empty);
					if (empty) {
						super.setGraphic(null);
						super.setText(null);
					} else {
						
						//MANEJADORES PARA LOS BOTONES
						botonVer.setOnAction(event -> {
							proyecto = getTableView().getItems().get(getIndex());
							manejadorBotonVer(proyecto);
						});//FIN MANEJADDOR
						
						botonEditar.setOnAction(event -> {
							proyecto = getTableView().getItems().get(getIndex());
							manejadorBotonEditar(proyecto);
						});//FIN MANEJADDOR
						
						botonEliminar.setOnAction(event -> {
							proyecto = getTableView().getItems().get(getIndex());
							manejadorBotonEliminar(proyecto);
						});//FIN MANEJADDOR
						
						cajaBotones.setSpacing(2);
						super.setGraphic(cajaBotones);
						super.setText(null);
					}//FIN IF ELSE
						
				}//FIN METODO
				
			};//FIN INSTRUCCION
			return cell;
		};//FIN INSTRUCCION
		columnaAcciones.setCellFactory(cellFactory);
	}//FIN METODO
		
	//MANEJADORES COMPONENTES
	@FXML private void manejadorBotonCrear() {
		this.mainApp.iniciarDialogoProyecto(proyecto, DialogoTipoMateriaPrima.CREAR);
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonVer(Proyecto proyecto) {
		this.mainApp.iniciarDialogoProyecto(proyecto, DialogoTipoMateriaPrima.VER);
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonEditar(Proyecto proyecto) {
		this.mainApp.iniciarDialogoProyecto(proyecto, DialogoTipoMateriaPrima.EDITAR);
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonEliminar(Proyecto proyecto) {
		if (Notificacion.dialogoPreguntar("", "Estas a punto de eliminar el registro, ¿Deseas continuar?"))
			ProyectoDAO.deleteProyecto(this.mainApp.getConnection(), proyecto);
		this.actualizarTabla();
	}//FIN METODO
		
}//FIN CLASE