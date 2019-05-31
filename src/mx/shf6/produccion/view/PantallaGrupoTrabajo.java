package mx.shf6.produccion.view;

import java.awt.TextField;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.GrupoTrabajo;
import mx.shf6.produccion.model.Puesto;
import mx.shf6.produccion.model.dao.GrupoTrabajoDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class PantallaGrupoTrabajo {

	// PROPIEDADES
	private MainApp mainApp;
	private GrupoTrabajo grupoTrabajo;
	private ArrayList<GrupoTrabajo> listaGrupoTrabajo;

	// CONSTANTES

	// VARIABLES

	// COMPONENTES INTERFAZ
	@FXML
	private TableView<GrupoTrabajo> tablaGrupoTrabajo;
	@FXML
	private PTableColumn<GrupoTrabajo, String> columnaCodigo;
	@FXML
	private PTableColumn<GrupoTrabajo, String> columnaDescripcion;
	@FXML
	private PTableColumn<GrupoTrabajo, String> columnaAcciones;
	@FXML
	private TextField buscarGrupoTrabajo;

	// INICIALIZA COMPONENTES QUE CONTROLAN INTERFAZ
	@FXML
	private void initialize() {

	}// FIN METODO INICIALIZAR COMPONENTE

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.grupoTrabajo = new GrupoTrabajo();
		this.listaGrupoTrabajo = GrupoTrabajoDAO.readGrupoTrabajo(this.mainApp.getConnection());
	}// FIN METODO

	// METODO INICIALIZAR TABLA
	private void inicializarTabla() {
		this.columnaCodigo.setCellValueFactory(CellData -> CellData.getValue().codigoProperty());
		this.columnaDescripcion.setCellValueFactory(CellData -> CellData.getValue().descripcionProperty());
		inicializarColumnaAcciones();
	}// FIN METODO INICIALIZAR TABLA

	//METODO ACTUALIZAR TABLA
	public void actualizarTabla() {
		this.tablaGrupoTrabajo.setItems(null);
		this.listaGrupoTrabajo.clear();
		this.listaGrupoTrabajo = GrupoTrabajoDAO.readGrupoTrabajo(mainApp.getConnection());
		this.tablaGrupoTrabajo.setItems(FXCollections.observableArrayList(listaGrupoTrabajo));
	}//FIN METODO ACTUALIZAR TABLA
	
	// BUSCAR REGISTRO
	@FXML
	public void buscarRegistroTabla() {
		tablaGrupoTrabajo.setItems(null);
		listaGrupoTrabajo.clear();
		listaGrupoTrabajo = GrupoTrabajoDAO.readGrupoTrabajo(mainApp.getConnection(), buscarGrupoTrabajo.getText());
		this.tablaGrupoTrabajo.setItems(FXCollections.observableArrayList(listaGrupoTrabajo));
	}// FIN METODO BUSCAR

	// INICIALIZAR COLUMNAS ACCIONES
	private void inicializarColumnaAcciones() {
		this.columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<GrupoTrabajo, String>, TableCell<GrupoTrabajo, String>> cellFactory = param -> {
			final TableCell<GrupoTrabajo, String> cell = new TableCell<GrupoTrabajo, String>() {
				final Button botonVer = new Button("Ver");
				final Button botonEditar = new Button("Editar");
				final Button botonEliminar = new Button("Eliminar");
				final HBox cajaBotones = new HBox(botonVer, botonEditar, botonEliminar);

				@Override
				public void updateItem(String item, boolean empty) {
					// INICALIZA BOTONES
					botonVer.setGraphic(
							new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/VerIcono.png"))));
					botonVer.setPrefSize(16.0, 16.0);
					botonVer.setPadding(Insets.EMPTY);
					botonVer.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonVer.setStyle("-fx-background-color: transparent");
					botonVer.setCursor(Cursor.HAND);
					
					botonVer.setTooltip(new Tooltip("Ver registro"));

					botonEditar.setGraphic(new ImageView(
							new Image(MainApp.class.getResourceAsStream("view/images/1x/ActualizarIcono.png"))));
					botonEditar.setPrefSize(16.0, 16.0);
					botonEditar.setPadding(Insets.EMPTY);
					botonEditar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonEditar.setStyle("-fx-background-color: transparent");
					botonEditar.setCursor(Cursor.HAND);
					botonEditar.setTooltip(new Tooltip("Editar registro"));

					botonEliminar.setGraphic(new ImageView(
							new Image(MainApp.class.getResourceAsStream("view/images/1x/EliminarIcono.png"))));
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

						// MANEJADORES PARA LOS BOTONES
						botonVer.setOnAction(event -> {
							grupoTrabajo = getTableView().getItems().get(getIndex());
							manejadorBotonVer(grupoTrabajo);
						});// FIN MANEJADDOR

						botonEditar.setOnAction(event -> {
							grupoTrabajo = getTableView().getItems().get(getIndex());
							manejadorBotonEditar(grupoTrabajo);
						});// FIN MANEJADDOR

						botonEliminar.setOnAction(event -> {
							grupoTrabajo = getTableView().getItems().get(getIndex());
							manejadorBotonEliminar(grupoTrabajo);
						});// FIN MANEJADDOR

						cajaBotones.setSpacing(2);
						super.setGraphic(cajaBotones);
						super.setText(null);
					} // FIN IF ELSE

				}// FIN METODO

			};// FIN INSTRUCCION
			return cell;
		};// FIN INSTRUCCION
		columnaAcciones.setCellFactory(cellFactory);
	}// FIN METODO
	//MANEJADORES COMPONENTES
			@FXML private void manejadorBotonCrear() {
				//this.mainApp.iniciarDialogoAcabado(puesto, DialogoAcabado.CREAR);
				this.actualizarTabla();
			}//FIN METODO
			
			@FXML private void manejadorBotonActualizar() {
				this.actualizarTabla();
			}//FIN METODO
			
			private void manejadorBotonVer(GrupoTrabajo grupoTrabajo) {
			//	this.mainApp.iniciarDialogoAcabado(puesto, DialogoAcabado.VER);
				this.actualizarTabla();
			}//FIN METODO
			
			private void manejadorBotonEditar(GrupoTrabajo grupoTrabajo) {
			//	this.mainApp.iniciarDialogoAcabado(puesto, DialogoAcabado.EDITAR);
				this.actualizarTabla();
			}//FIN METODO
			
			private void manejadorBotonEliminar(GrupoTrabajo grupoTrabajo) {
				if (Notificacion.dialogoPreguntar("", "Estas a punto de eliminar el registro, �Deseas continuar?"))
			//		AcabadoDAO.deleteAcabado(this.mainApp.getConnection(), puesto);
				this.actualizarTabla();
			}//FIN METODO

}
