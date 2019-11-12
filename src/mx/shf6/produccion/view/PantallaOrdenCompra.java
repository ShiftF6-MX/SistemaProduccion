package mx.shf6.produccion.view;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.function.Predicate;



import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
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
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.OrdenCompra;
import mx.shf6.produccion.model.dao.OrdenCompraDAO;
import mx.shf6.produccion.utilities.PTableColumn;

public class PantallaOrdenCompra {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private ArrayList<OrdenCompra> arrayListOrdenCompra;
	
	//VARIABLES
	
	//CONSTANTES
	
	//COMPONENTES DE LA INTERFAZ
	@FXML private TableView<OrdenCompra> tableViewOrdenCompra;
	@FXML private PTableColumn<OrdenCompra, String> tableColumnFolio;
	@FXML private PTableColumn<OrdenCompra, Date> tableColumnFechaPedido;
	@FXML private PTableColumn<OrdenCompra, String> tableColumnCliente;
	@FXML private PTableColumn<OrdenCompra, String> tableColumnPMP;
	@FXML private PTableColumn<OrdenCompra, String> tableColumnComentarios;
	@FXML private PTableColumn<OrdenCompra, String> tableColumnAcciones;
	@FXML private TextField textFieldBusqueda;
	
	//METODOS
	@FXML private void initialize() {
		initTabla();
	}//FIN METODO
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.connection = this.mainApp.getConnection();
		this.arrayListOrdenCompra = new ArrayList<OrdenCompra>();
		updateTablaOrdenCompra();
	}//FIN METODO
	
	private void initTabla() {
		this.tableColumnFolio.setCellValueFactory(cellData -> cellData.getValue().folioProperty());
		this.tableColumnFechaPedido.setCellValueFactory(cellData -> cellData.getValue().fechaPedidoProperty());
		this.tableColumnCliente.setCellValueFactory(cellData -> cellData.getValue().getClienteFK().nombreProperty());
		this.tableColumnPMP.setCellValueFactory(cellData -> cellData.getValue().pmpProperty());
		this.tableColumnComentarios.setCellValueFactory(cellData -> cellData.getValue().comentariosProperty());
		initColumnaAcciones();
	}//FIN METODO
	
	private void initColumnaAcciones() {
		this.tableColumnAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<OrdenCompra, String>, TableCell<OrdenCompra, String>> cellFactory = param -> {
			final TableCell<OrdenCompra, String> cell = new TableCell<OrdenCompra, String>() {
				final Button botonVer = new Button("Ver");
				final Button botonEditar = new Button("Editar");
				final Button botonEliminar = new Button("Eliminar");
				final Button botonDetalles = new Button("Detalles");
				final HBox cajaBotones = new HBox(botonVer, botonEditar, botonEliminar, botonDetalles);
				
				@Override
				public void updateItem(String item, boolean empty) {
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
					botonEliminar.setTooltip(new Tooltip("Eliminar registro"));
					
					botonDetalles.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/DetalleIcono.png"))));
					botonDetalles.setPrefSize(16.0, 16.0);
					botonDetalles.setPadding(Insets.EMPTY);
					botonDetalles.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonDetalles.setStyle("-fx-background-color: transparent");
					botonDetalles.setCursor(Cursor.HAND);
					botonDetalles.setTooltip(new Tooltip("Detalles orden compra"));
					
					super.updateItem(item, empty);
					if (empty) {
						super.setGraphic(null);
						super.setText(null);
					} else {
						//MANEJADOR DE BOTONES
						botonVer.setOnAction(event -> {
							manejadorBotonMostrar(getTableView().getItems().get(getIndex()));
						});//FIN MANEJADOR
						
						botonEditar.setOnAction(event -> {
							manejadorBotonEditar(getTableView().getItems().get(getIndex()));
						});//FIN MANEJADOR
						
						botonEliminar.setOnAction(event -> {
							manejadorBotonEliminar(getTableView().getItems().get(getIndex()));
						});//FIN MANEJADOR
						
						botonDetalles.setOnAction(event -> {
							manejadorBotonAgregarDetalle(getTableView().getItems().get(getIndex()));
						});//FIN MANEJADOR
						
						cajaBotones.setSpacing(2);
						super.setGraphic(cajaBotones);
						super.setText(null);
					}//FIN IF/ELSE
				}//FIN METODO
			};//FIN INSTRUCCION
			return cell;
		};//FIN INSTRUCCION
		tableColumnAcciones.setCellFactory(cellFactory);
	}//FIN METODO
	
	private void updateTablaOrdenCompra() {
		this.arrayListOrdenCompra.clear();
		this.tableViewOrdenCompra.setItems(null);
		this.arrayListOrdenCompra = OrdenCompraDAO.read(connection);
		
		ObjectProperty<Predicate<OrdenCompra>> filtroBusqueda = new SimpleObjectProperty<>();
		filtroBusqueda.bind(Bindings.createObjectBinding(() -> ordenCompra -> textFieldBusqueda.getText().isEmpty() ||
				ordenCompra.getFolio().toLowerCase().contains(textFieldBusqueda.getText().toLowerCase()) ||
					ordenCompra.getPMP().toLowerCase().contains(textFieldBusqueda.getText().toLowerCase()) ||
						ordenCompra.getClienteFK().getNombre().toLowerCase().contains(textFieldBusqueda.getText().toLowerCase()),
				textFieldBusqueda.textProperty()));
		
		FilteredList<OrdenCompra> filteredOrdenCompra = new FilteredList<OrdenCompra>(FXCollections.observableArrayList(this.arrayListOrdenCompra));
		tableViewOrdenCompra.setItems(filteredOrdenCompra);
		filteredOrdenCompra.predicateProperty().bind(Bindings.createObjectBinding(() -> filtroBusqueda.get(), filtroBusqueda));
	}//FIN METODO
	
	//MANEJADORES
	private void manejadorBotonMostrar(OrdenCompra ordenCompra) {
		
	}//FIN METODO
	
	private void manejadorBotonEditar(OrdenCompra ordenCompra) {
		
	}//FIN METODO
	
	private void manejadorBotonEliminar(OrdenCompra ordenCompra) {
		
	}//FIN METODO
	
	private void manejadorBotonAgregarDetalle(OrdenCompra ordenCompra) {
		
	}//FIN METODO
	
	@FXML private void manejadorBotonActualizar() {
		updateTablaOrdenCompra();
	}//FIN METODO
	
	@FXML private void manejadorBotonBuscar() {
		updateTablaOrdenCompra();
	}//FIN METODO
	
	@FXML private void manejadorBotonCrear() {
		
	}//FIN METODO
}//FIN CLASE
