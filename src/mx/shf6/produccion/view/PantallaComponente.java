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
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class PantallaComponente {

	//PROPIEDADES
	private MainApp mainApp;
	private Componente componente;
	private ArrayList<Componente> listaComponente;
	
	//VARIABLES
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoBusqueda;
	@FXML private TableView<Componente> tablaComponente;
	@FXML private PTableColumn<Componente, String> columnaNumeroParte;
	@FXML private PTableColumn<Componente, String> columnaDescripcion;
	@FXML private PTableColumn<Componente, String> columnaTipoComponente;
	@FXML private PTableColumn<Componente, String> columnaTipoMaterial;
	@FXML private PTableColumn<Componente, Double> columnaCosto;
	@FXML private PTableColumn<Componente, String> columnaNotas;
	@FXML private PTableColumn<Componente, String> columnaStatus;
	@FXML private PTableColumn<Componente, String> columnaAcciones;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.componente = new Componente();
		this.inicializaComponentes();
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.listaComponente = ComponenteDAO.readComponente(this.mainApp.getConnection());
		this.inicializaTabla();
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
		this.columnaNumeroParte.setCellValueFactory(cellData -> cellData.getValue().numeroParteProperty(this.mainApp.getConnection()));
		this.columnaDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
		this.columnaTipoComponente.setCellValueFactory(cellData -> cellData.getValue().tipoComponenteProperty());
		this.columnaTipoMaterial.setCellValueFactory(cellData -> cellData.getValue().getMaterial(this.mainApp.getConnection()).descripcionProperty());
		this.columnaCosto.setCellValueFactory(cellData -> cellData.getValue().costoProperty());
		this.columnaNotas.setCellValueFactory(cellData -> cellData.getValue().notasProperty());
		this.columnaStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
		this.inicializarColumnaAcciones();
	}//FIN METODO.
	
	private void actualizarTabla() {
		this.tablaComponente.setItems(null);
		this.listaComponente.clear();
		this.listaComponente = ComponenteDAO.readComponente(this.mainApp.getConnection());
		this.tablaComponente.setItems(ComponenteDAO.toObservableList(this.listaComponente));
	}//FIN METODO
	
	@FXML private void buscarRegistroTabla() {
		this.tablaComponente.setItems(null);
		this.listaComponente.clear();
		this.listaComponente = ComponenteDAO.readComponente(this.mainApp.getConnection(), this.campoTextoBusqueda.getText());
		this.tablaComponente.setItems(ComponenteDAO.toObservableList(this.listaComponente));
	}//FIN METODO
	
	private void inicializarColumnaAcciones() {
		this.columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<Componente, String>, TableCell<Componente, String>> cellFactory = param -> {
			final TableCell<Componente, String> cell = new TableCell<Componente, String>() {
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
							componente = getTableView().getItems().get(getIndex());
							manejadorBotonVer(componente);
						});//FIN MANEJADDOR
						
						botonEditar.setOnAction(event -> {
							componente = getTableView().getItems().get(getIndex());
							manejadorBotonEditar(componente);
						});//FIN MANEJADDOR
						
						botonEliminar.setOnAction(event -> {
							componente = getTableView().getItems().get(getIndex());
							manejadorBotonEliminar(componente);
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
		this.mainApp.iniciarDialogoComponente(componente, DialogoComponente.CREAR);
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonVer(Componente componente) {
		this.mainApp.iniciarDialogoComponente(componente, DialogoComponente.VER);
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonEditar(Componente componente) {
		this.mainApp.iniciarDialogoComponente(componente, DialogoComponente.EDITAR);
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonEliminar(Componente componente) {
		if (Notificacion.dialogoPreguntar("", "Estas a punto de eliminar el registro, ¿Deseas continuar?"))
			ComponenteDAO.deleteComponente(this.mainApp.getConnection(), componente);
		this.actualizarTabla();
	}//FIN METODO
		
}//FIN CLASE
