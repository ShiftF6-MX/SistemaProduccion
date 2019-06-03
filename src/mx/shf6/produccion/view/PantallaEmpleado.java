package mx.shf6.produccion.view;


import java.sql.Connection;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Empleado;
import mx.shf6.produccion.model.dao.EmpleadoDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;


public class PantallaEmpleado {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private Empleado empleado;
	private ArrayList<Empleado> listaEmpleado;
	
	//VARAIBLES
	
	//CONSTANTES
	
	//COMPONENTES INTERFAZ
	@FXML 
	private TextField campoTextoBusqueda;
	@FXML
	private TableView<Empleado> tablaEmpleado;
	@FXML
	private PTableColumn<Empleado, String> columnaCodigo;
	@FXML
	private PTableColumn<Empleado, String> columnaNombre;
	@FXML 
	private PTableColumn<Empleado,String > columnaPuesto;
	@FXML
	private PTableColumn<Empleado, String>  columnaAcciones;
	
	//METODOS
	
	//METODO COMPONENTES INTERFAZ
	private void initialize() {
		inicializarTabla();
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.empleado = new Empleado();
		this.listaEmpleado = EmpleadoDAO.readEmpleado(this.mainApp.getConnection());
		this.connection = this.mainApp.getConnection();
	}//FIN METODO
	
	//METODO INICIALIZAR TABLA
	private void inicializarTabla() {
		this.columnaCodigo.setCellValueFactory(CellData -> CellData.getValue().codigoProperty());
		this.columnaNombre.setCellValueFactory(CellData -> CellData.getValue().nombreProperty());
		this.columnaPuesto.setCellValueFactory(CellData ->CellData.getValue().getPuesto(this.connection).codigoProperty());
		inicializarColumnaAcciones();
	}//FIN METODO 
	
	//METODO ACTUALIZAR TABLA
	private void actualizarTabla() {
		this.tablaEmpleado.setItems(null);
		this.listaEmpleado.clear();
		this.listaEmpleado = EmpleadoDAO.readEmpleado(this.mainApp.getConnection());
		this.tablaEmpleado.setItems(FXCollections.observableArrayList(listaEmpleado));
		
	}//FIN METODO ACTUALIZAR TABLA
	
	//METODO BUSCAR REGISTRO
	private void buscarRegistro() {
		tablaEmpleado.setItems(null);
		listaEmpleado.clear();
		listaEmpleado = EmpleadoDAO.readEmpleado(mainApp.getConnection());
		tablaEmpleado.setItems(FXCollections.observableArrayList(listaEmpleado));
	}//FIN BUSCAR REGISTRO
	
	//INICIALIZAR COLUMNA ACCIONES
		private void inicializarColumnaAcciones() {
			this.columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
			Callback<TableColumn<Empleado, String>, TableCell<Empleado, String>> cellFactory = param -> {
				final TableCell<Empleado, String> cell = new TableCell<Empleado, String>() {
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
									empleado = getTableView().getItems().get(getIndex());
									manejadorBotonVer(empleado);
								});//FIN MANEJADDOR
							
								botonEditar.setOnAction(event -> {
									empleado = getTableView().getItems().get(getIndex());
									manejadorBotonEditar(empleado);
							});//FIN MANEJADDOR
							
							botonEliminar.setOnAction(event -> {
								 empleado = getTableView().getItems().get(getIndex());
								 manejadorBotonEliminar(empleado);
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
				//this.mainApp.iniciarDialogoAcabado(empleado, DialogoAcabado.CREAR);
				this.actualizarTabla();
			}//FIN METODO
			
			@FXML private void manejadorBotonActualizar() {
				this.actualizarTabla();
			}//FIN METODO
			
			private void manejadorBotonVer(Empleado empleado) {
			//	this.mainApp.iniciarDialogoAcabado(empleado, DialogoAcabado.VER);
				this.actualizarTabla();
			}//FIN METODO
			
			private void manejadorBotonEditar(Empleado empleado) {
			//	this.mainApp.iniciarDialogoAcabado(empleado, DialogoAcabado.EDITAR);
				this.actualizarTabla();
			}//FIN METODO
			
			private void manejadorBotonEliminar(Empleado empleado) {
				if (Notificacion.dialogoPreguntar("", "Estas a punto de eliminar el registro, ¿Deseas continuar?"))
			//		AcabadoDAO.deleteAcabado(this.mainApp.getConnection(), empleado);
				this.actualizarTabla();
			}//FIN METODO
	
	
	
}
