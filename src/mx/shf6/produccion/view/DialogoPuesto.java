package mx.shf6.produccion.view;

import java.sql.Connection;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Puesto;
import mx.shf6.produccion.model.dao.PuestoDAO;
import mx.shf6.produccion.utilities.Notificacion;

public class DialogoPuesto {

	//PROPIEDADES
	private MainApp mainApp;
	private Puesto puesto;
	private Connection conexion;
	
	//VARIABLES
	
	private int opcion;
	
	//CONSTANTES
	
	static final int CREAR = 1;
	static final int VER = 2;
	static final int EDITAR = 3;
	
	//COMPONENTES INTERFAZ
	
	@FXML
	private TextField campoTextoCodigo;
	@FXML
	private TextField campoTextoDescripcion;
	
	//INICIA COMPONENTES INTERFAZ DE USUARIO
	@FXML
	private void initialize(){
		this.puesto = new Puesto();		
	}
	//ACCESO A LA CLASE PRINCIPAL
	public void setMain(MainApp mainApp, Puesto puesto, int opcion) {
		this.mainApp = mainApp;
		this.puesto = puesto;
		this.opcion = opcion;
		this.inicializarComponentes();
		
	}
	
	//INICIALIZA COMPONENTE
	public void inicializarComponentes(){
		if (opcion == CREAR) {
			this.campoTextoCodigo.setText("");
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText("");
			this.campoTextoDescripcion.setDisable(false);
		}else if (opcion == VER) {
			this.campoTextoCodigo.setText(puesto.getCodigo());
			this.campoTextoCodigo.setDisable(true);
			this.campoTextoDescripcion.setText(puesto.getDescripcion());
			this.campoTextoDescripcion.setDisable(true);
		}else if (opcion == EDITAR) {
			this.campoTextoCodigo.setText(puesto.getCodigo());
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoCodigo.setText(puesto.getDescripcion());
			this.campoTextoDescripcion.setDisable(false);
		}//FIN METODO ELSE-IF
	}//FIN METODO ACCESO A CLASE PRINCIPAL
	
	//VALIDAR DATOS
	private boolean validarDatos(){
		if (campoTextoCodigo.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"C�digo\" no puede estar vacio");
			return false;
		}else if (campoTextoDescripcion.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Descripcion\" no puede estar vacio");
			return false;
		}//FIN ELSE-IF
		return true;
	}// FIN METODO VALIR
	
	//MANEJADOR COMPONENTES
	@FXML
	private void manejadorBotonAceptar() {
		if (validarDatos()&& opcion == CREAR) {
			this.puesto.setCodigo(campoTextoCodigo.getText());
			this.puesto.setDescripcion(campoTextoDescripcion.getText());
			if (PuestoDAO.createPuesto(conexion, puesto)) {
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo correctamente");
				this.mainApp.getEscenarioDialogos().close();
			}else {
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "Error", "No se pudo crear el registro, revisa que la informaci�n sea correcta");
			}
		}else if(validarDatos() && opcion == EDITAR) {
			this.puesto.setCodigo(campoTextoCodigo.getText());
			this.puesto.setDescripcion(campoTextoDescripcion.getText());
			if (PuestoDAO.updatePuesto(conexion, puesto)) {
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se actualizo correctamente");
			}else {
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "Error", "No se pudo actualizar el registro, revisa que la informacion sea correcta");
			}
		}
		else if (validarDatos() && opcion == VER) {
			mainApp.getEscenarioDialogos().close();
		}
	}
	//BOTON CERRAR
	@FXML 
	private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN BOTON CERRAR
}
