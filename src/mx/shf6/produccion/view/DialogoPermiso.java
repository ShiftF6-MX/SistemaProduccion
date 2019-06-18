package mx.shf6.produccion.view;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Rol;
import mx.shf6.produccion.model.dao.RolDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoPermiso {
	
	//CONSTANTES
	public static int CREAR = 1;
	public static int EDITAR = 2;
	
	//PROPIEDADES
	private MainApp mainApp;
	private Rol permiso;
	private ArrayList<Rol> listaPermisos;
	
	//VARIABLES
	
	//COMPONENTES INTERFAZ PERMISOS
	@FXML private TextField campoDescripcion;
	@FXML private TextField campoDetalle;
	@FXML private TableView<Rol> tablaPermisos;
	@FXML private PTableColumn<Rol, String> codigoItemColumna;
	@FXML private PTableColumn<Rol, String> descripcionColumna;
	
	//INICIALIZAR COMPONENTES 
	@FXML private void initialize() {
		this.permiso = new Rol();
		this.campoDescripcion.setFocusTraversable(false);
		this.campoDetalle.setFocusTraversable(false);
	}//FIN METODO
	
	//ACCESO A CLASE PRINCIPAL CONTROLA VISTAS
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.listaPermisos = RolDAO.readTodos(this.mainApp.getConnection());
		this.inicializarTabla();
		this.actualizarTabla();
	}//FIN METODO
	
	private void inicializarTabla() {
		this.codigoItemColumna.setCellValueFactory(cellData -> cellData.getValue().codigoItemProperty());
		this.descripcionColumna.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
	}//FIN METODO
	
	//ACTUALIZAR LA TABLA CON LOS ULTIMOS CAMBIOS EN LA BASE DE DATOS
	private void actualizarTabla() {
		this.tablaPermisos.setItems(null);
		this.listaPermisos.clear();
		this.listaPermisos = RolDAO.readTodos(this.mainApp.getConnection());
		this.tablaPermisos.setItems(RolDAO.toObservableList(listaPermisos));
	}//FIN METODO
	
	//AGREGAR PERMISO
	private void agregarPermiso() {
		Rol permiso = new Rol();
		permiso = this.mainApp.iniciarDialogoAgregarPermiso(this.permiso, DialogoAgregarPermiso.CREAR);
		if (permiso != null) {
			RolDAO.create(this.mainApp.getConnection(), permiso);
			this.actualizarTabla();
		} else {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "No se puede agregar el permiso");
		}
	}//FIN METODO
	
	//MODIFICAR PERMISO
	private void modificarDetalle() {
		Rol permiso = this.tablaPermisos.getSelectionModel().getSelectedItem();
		System.out.println(this.permiso.getCodigoItem());
		permiso = this.mainApp.iniciarDialogoAgregarPermiso(permiso, DialogoAgregarPermiso.EDITAR);
		if (permiso != null) {
			RolDAO.update(this.mainApp.getConnection(), permiso);
			this.actualizarTabla();
		} else {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "No se pudo actualizar el dato");
		}//FIN IF ELSE
	}//FIN METODO
	
	//QUITAR PERMISO
	private void quitarPermiso() {
		Rol permiso = this.tablaPermisos.getSelectionModel().getSelectedItem();
		if (permiso != null) {
			if (Notificacion.dialogoPreguntar("", "¿Realmente desea quitar esté permiso?")) {
				RolDAO.delete(this.mainApp.getConnection(), permiso);
				this.actualizarTabla();
			}//FIN IF
		} else {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "Debe seleccionar un registro");
		}//FIN IF ELSE
	}//FIN METODO
	
	//MANEJADOR BOTON CERRAR
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
	
	@FXML private void manejadorModificarPermiso() {
		this.modificarDetalle();
	}//FIN METODO
	
	@FXML private void manejadorAgregarPermiso() {
		this.agregarPermiso();
	}//FIN METODO
	
	@FXML private void manejadorEliminarPermiso() {
		this.quitarPermiso();
	}//FIN METODO
	
}//FIN CLASE
