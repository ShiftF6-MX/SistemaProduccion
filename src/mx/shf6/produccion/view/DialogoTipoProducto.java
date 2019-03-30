package mx.shf6.produccion.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Status;
import mx.shf6.produccion.model.TipoProducto;
import mx.shf6.produccion.model.dao.TipoProductoDAO;
import mx.shf6.produccion.utilities.Notificacion;

public class DialogoTipoProducto {

	//PROPIEDADES
	private MainApp mainApp;
	private TipoProducto tipoProducto;
	
	//VARIABLES
	private int opcion;
	
	//CONSTANTES
	public static final int CREAR = 1;
	public static final int VER = 2;
	public static final int EDITAR = 3;
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoCodigo;
	@FXML private TextField campoTextoDescripcion;
	@FXML private ComboBox<String> comboBoxStatus;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.tipoProducto = new TipoProducto();
		ObservableList<String> listaStatus = FXCollections.observableArrayList("No Visible", "Visible");
		this.comboBoxStatus.setItems(listaStatus);
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, TipoProducto tipoProducto, int opcion) {
		this.mainApp = mainApp;
		this.tipoProducto = tipoProducto;
		this.opcion = opcion;
		this.inicializarComponentes();
	}//FIN METODO
	
	//INICIALIZA COMPONENTE
	private void inicializarComponentes() {		
		if (this.opcion == CREAR) {
			this.campoTextoCodigo.setText("");
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText("");
			this.campoTextoDescripcion.setDisable(false);
			this.comboBoxStatus.getSelectionModel().select("");
			this.comboBoxStatus.setDisable(false);
		} else if (this.opcion == VER) {
			this.campoTextoCodigo.setText(this.tipoProducto.getCodigo());
			this.campoTextoCodigo.setDisable(true);
			this.campoTextoDescripcion.setText(this.tipoProducto.getDescripcion());
			this.campoTextoDescripcion.setDisable(true);
			this.comboBoxStatus.getSelectionModel().select(this.tipoProducto.getStatus());
			this.comboBoxStatus.setDisable(true);
		} else if (this.opcion == EDITAR) {
			this.campoTextoCodigo.setText(this.tipoProducto.getCodigo());
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText(this.tipoProducto.getDescripcion());
			this.campoTextoDescripcion.setDisable(false);
			this.comboBoxStatus.getSelectionModel().select(this.tipoProducto.getStatus());
			this.comboBoxStatus.setDisable(false);
		}//FIN METODO
	}//FIN METODO
	
	//VALIDAR DATOS
	private boolean validarDatos() {
		if (this.campoTextoCodigo.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"C�digo\" no puede estar vacio");
			return false;
		} else if (this.campoTextoDescripcion.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Descripci�n\" no puede estar vacio");
			return false;
		} else if (this.comboBoxStatus.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Descripci�n\" no puede estar vacio");
			return false;
		}//FIN IF/ESLE
		return true;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES	
	@FXML private void vmanejadorBotonAceptar() {
		if (this.validarDatos() && this.opcion == CREAR) {
			this.tipoProducto.setCodigo(this.campoTextoCodigo.getText());
			this.tipoProducto.setDescripcion(this.campoTextoDescripcion.getText());
			this.tipoProducto.setStatus(Status.toInt(this.comboBoxStatus.getSelectionModel().getSelectedItem()));
			if (TipoProductoDAO.createTipoProducto(this.mainApp.getConnection(), this.tipoProducto)) {
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
				this.mainApp.getEscenarioDialogos().close();
			} else
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la informaci�n sea correcta");
		} else if (this.validarDatos() && this.opcion == EDITAR) {
			this.tipoProducto.setCodigo(this.campoTextoCodigo.getText());
			this.tipoProducto.setDescripcion(this.campoTextoDescripcion.getText());
			this.tipoProducto.setStatus(Status.toInt(this.comboBoxStatus.getSelectionModel().getSelectedItem()));
			if (TipoProductoDAO.updateTipoProducto(this.mainApp.getConnection(), this.tipoProducto)) {
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se actualizo de forma correcta");
				this.mainApp.getEscenarioDialogos().close();
			} else
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo actualizar el registro, revisa que la informaci�n sea correcta");
		} else if (this.validarDatos() && this.opcion == VER)
			this.mainApp.getEscenarioDialogos().close();			
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
	
}//FIN CLASE