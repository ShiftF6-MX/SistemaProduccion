package mx.shf6.produccion.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Status;
import mx.shf6.produccion.model.Material;
import mx.shf6.produccion.model.dao.MaterialDAO;
import mx.shf6.produccion.utilities.Notificacion;

public class DialogoAgregarMaterial {

	//PROPIEDADES
	private MainApp mainApp;
	private Material material;
	
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
		this.material = new Material();
		ObservableList<String> listaStatus = FXCollections.observableArrayList("No Visible", "Visible");
		this.comboBoxStatus.setItems(listaStatus);
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Material material, int opcion) {
		this.mainApp = mainApp;
		this.material = material;
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
			this.campoTextoCodigo.setText(this.material.getCodigo());
			this.campoTextoCodigo.setDisable(true);
			this.campoTextoDescripcion.setText(this.material.getDescripcion());
			this.campoTextoDescripcion.setDisable(true);
			this.comboBoxStatus.getSelectionModel().select(this.material.getStatus());
			this.comboBoxStatus.setDisable(true);
		} else if (this.opcion == EDITAR) {
			this.campoTextoCodigo.setText(this.material.getCodigo());
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText(this.material.getDescripcion());
			this.campoTextoDescripcion.setDisable(false);
			this.comboBoxStatus.getSelectionModel().select(this.material.getStatus());
			this.comboBoxStatus.setDisable(false);
		}//FIN METODO
	}//FIN METODO
	
	//VALIDAR DATOS
	private boolean validarDatos() {
		if (this.campoTextoCodigo.getText().isEmpty() || String.valueOf(this.campoTextoCodigo.getText().charAt(0)).equals(" ")) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"C�digo\" no puede estar vacio");
			return false;
		} else if (this.campoTextoDescripcion.getText().isEmpty() || String.valueOf(this.campoTextoCodigo.getText().charAt(0)).equals(" ")) {
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
		if (this.validarDatos()) {
			if (this.opcion == CREAR) {
				this.material.setCodigo(this.campoTextoCodigo.getText());
				this.material.setDescripcion(this.campoTextoDescripcion.getText());
				this.material.setStatus(Status.toInt(this.comboBoxStatus.getSelectionModel().getSelectedItem()));
				if (MaterialDAO.createMaterial(this.mainApp.getConnection(), this.material)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
					this.mainApp.getEscenarioDialogosAlterno().close();
				} else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la informaci�n sea correcta");
			} else if (this.opcion == EDITAR) {
				this.material.setCodigo(this.campoTextoCodigo.getText());
				this.material.setDescripcion(this.campoTextoDescripcion.getText());
				this.material.setStatus(Status.toInt(this.comboBoxStatus.getSelectionModel().getSelectedItem()));
				if (MaterialDAO.updateMaterial(this.mainApp.getConnection(), this.material)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se actualizo de forma correcta");
					this.mainApp.getEscenarioDialogosAlterno().close();
				} else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo actualizar el registro, revisa que la informaci�n sea correcta");
			} else if (this.opcion == VER)
				this.mainApp.getEscenarioDialogosAlterno().close();		
		}//FIN IF
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN METODO
	
}//FIN CLASE
