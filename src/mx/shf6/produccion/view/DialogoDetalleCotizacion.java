package mx.shf6.produccion.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cotizacion;
import mx.shf6.produccion.model.DetalleCotizacion;
import mx.shf6.produccion.model.Status;
import mx.shf6.produccion.utilities.Notificacion;

public class DialogoDetalleCotizacion {

	//PROPIEDADES
	private MainApp mainApp;
	private Cotizacion cotizacion;
	private DetalleCotizacion detalleSolicitud;
	
	//VARIABLES
	private int opcion;
	
	//CONSTANTES
	public static final int CREAR = 1;
	public static final int VER = 2;
	public static final int EDITAR = 3;
	
	//COMPONENTES INTERFAZ
	@FXML private ComboBox<String> comboBoxProyectos;
	@FXML private TextField campoTextoCantidad;
	@FXML private TextArea campoTextoObservaciones;
	
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Cotizacion cotizacion) {
		this.mainApp = mainApp;
		this.cotizacion = cotizacion;
		this.inicializarComponentes();
	}//FIN METODO
	
	//INICIALIZA COMPONENTE
	private void inicializarComponentes() {		
		if (this.opcion == CREAR) {
			this.campoTextoCantidad.setText("");
			this.campoTextoCantidad.setDisable(false);
			this.campoTextoObservaciones.setText("");
			this.campoTextoObservaciones.setDisable(false);
			this.comboBoxProyectos.getSelectionModel().select("");
			this.comboBoxProyectos.setDisable(false);
		} else if (this.opcion == VER) {
			this.campoTextoCantidad.setText(this.detalleSolicitud.getCantidad().toString());
			this.campoTextoCantidad.setDisable(true);
			this.campoTextoObservaciones.setText(this.detalleSolicitud.getObservaciones());
			this.campoTextoObservaciones.setDisable(true);
			this.comboBoxProyectos.getSelectionModel().select(this.detalleSolicitud.getProyecto(this.mainApp.getConnection()).getDescripcion());
			this.comboBoxProyectos.setDisable(true);
		} else if (this.opcion == EDITAR) {
			this.campoTextoCantidad.setText(this.detalleSolicitud.getCantidad().toString());
			this.campoTextoCantidad.setDisable(false);
			this.campoTextoObservaciones.setText(this.detalleSolicitud.getObservaciones());
			this.campoTextoObservaciones.setDisable(false);
			this.comboBoxProyectos.getSelectionModel().select(this.detalleSolicitud.getProyecto(this.mainApp.getConnection()).getDescripcion());
			this.comboBoxProyectos.setDisable(false);
		}//FIN METODO
	}//FIN METODO
	
	//VALIDAR DATOS
	private boolean validarDatos() {
		if (this.campoTextoCantidad.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Código\" no puede estar vacio");
			return false;
		} else if (this.campoTextoObservaciones.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Descripción\" no puede estar vacio");
			return false;
		} else if (this.comboBoxProyectos.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Descripción\" no puede estar vacio");
			return false;
		}//FIN IF/ESLE
		return true;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES	
	@FXML private void manejadorBotonAceptar() {
		/*
		if (this.validarDatos() && this.opcion == CREAR) {
			this.detalleSolicitud.setCodigo(this.campoTextoCodigo.getText());
			this.detalleSolicitud.setDescripcion(this.campoTextoDescripcion.getText());
			this.detalleSolicitud.setStatus(Status.toInt(this.comboBoxStatus.getSelectionModel().getSelectedItem()));
			if (TratamientoDAO.createTratamiento(this.mainApp.getConnection(), this.tratamiento)) {
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
				this.mainApp.getEscenarioDialogos().close();
			} else
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la información sea correcta");
		} else if (this.validarDatos() && this.opcion == EDITAR) {
			this.tratamiento.setCodigo(this.campoTextoCodigo.getText());
			this.tratamiento.setDescripcion(this.campoTextoDescripcion.getText());
			this.tratamiento.setStatus(Status.toInt(this.comboBoxStatus.getSelectionModel().getSelectedItem()));
			if (TratamientoDAO.updateTratamiento(this.mainApp.getConnection(), this.tratamiento)) {
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se actualizo de forma correcta");
				this.mainApp.getEscenarioDialogos().close();
			} else
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo actualizar el registro, revisa que la información sea correcta");
		} else if (this.validarDatos() && this.opcion == VER)
			this.mainApp.getEscenarioDialogos().close();		
			*/	
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosSecundarios().close();
	}//FIN METODO
	
}//FIN CLASE