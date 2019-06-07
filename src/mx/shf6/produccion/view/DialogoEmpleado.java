package mx.shf6.produccion.view;



import java.sql.Connection;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Empleado;
import mx.shf6.produccion.model.Puesto;
import mx.shf6.produccion.model.dao.EmpleadoDAO;
import mx.shf6.produccion.model.dao.PuestoDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoEmpleado {
	
	//PROPIEDADES
	private MainApp mainApp;
	private Empleado empleado;
	private Connection conexion;
	private Puesto puesto;
	private ObservableList<String> observablePuesto;
	private ArrayList<Puesto> listaPuesto;
	
	//FIN PROPIEDADES
	
	//VARIABLES
	private int opcion;
	//FIN VARIABLES
	
	//CONSTANTES
	public static final int CREAR = 1;
	public static final int VER = 2;
	public static final int EDITAR = 3;
	//FIN CONSTANTES
	
	//COMPONENETS INTERFAZ
	@FXML private TextField campoTextoCodigo;
	@FXML private TextField campoTextoNombre;
	@FXML private ComboBox<String> comboPuesto;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML
	private void initialize() {

		RestriccionTextField.limitarNumeroCaracteres(campoTextoCodigo, 8);
		RestriccionTextField.limitarNumeroCaracteres(campoTextoNombre, 32);
		RestriccionTextField.soloLetras(campoTextoNombre);
	}
	
	//ACCESO A LA CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Empleado empleado, int opcion) {
		this.mainApp = mainApp;
		this.empleado = empleado;
		this.observablePuesto = FXCollections.observableArrayList();
		this.conexion = this.mainApp.getConnection();
		this.opcion = opcion;
		this.puesto = new Puesto();
		inicializarComponentes();
		llenarComboEmpleado();
	}
	
	//LLENAR COMBO
	private void llenarComboEmpleado() {
		this.listaPuesto = PuestoDAO.readPuesto(conexion);
		for(Puesto puesto: listaPuesto)
			this.observablePuesto.add(puesto.getCodigo());
		this.comboPuesto.setItems(observablePuesto);
		
	}//FIN COMBO
	
	//OBTENER DATOS
	private void obtenerDatos() {
		this.empleado.setCodigo(this.campoTextoCodigo.getText());
		this.empleado.setNombre(this.campoTextoNombre.getText());
		this.empleado.setPuestoFK(this.listaPuesto.get(this.comboPuesto.getSelectionModel().getSelectedIndex()).getSysPK());
	}
	
	//INICIALIZA COMPONENTES
	private void inicializarComponentes() {
		if (this.opcion == CREAR) {
			this.campoTextoCodigo.setText("");
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoNombre.setText("");
			this.campoTextoNombre.setDisable(false);
			this.comboPuesto.setValue(empleado.getPuesto(conexion).getCodigo());
			this.comboPuesto.setItems(observablePuesto);
		} else if (this.opcion == VER) {
			this.campoTextoCodigo.setText(empleado.getCodigo());
			this.campoTextoCodigo.setDisable(true);
			this.campoTextoNombre.setText(empleado.getNombre());
			this.campoTextoNombre.setDisable(true);
			this.comboPuesto.setValue(empleado.getPuesto(conexion).getCodigo());
			this.comboPuesto.setDisable(true);
		} else if (this.opcion == EDITAR) {
			this.campoTextoCodigo.setText(empleado.getCodigo());
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoNombre.setText(empleado.getNombre());
			this.campoTextoNombre.setDisable(false);
			this.comboPuesto.getSelectionModel().select(PuestoDAO.readPuesto(conexion,this.empleado.getPuestoFK()).getCodigo());
			this.comboPuesto.setDisable(false);
		}//FIN ELSE-IF
	}//FIN INICIALIZA COMPONENTE
	
	//METODO VALIDAR DATOS
	
	private boolean validarDatos() {
		if (this.campoTextoCodigo.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El campo \"Código\" no puede estar vacio");
			return false;
		} else if( this.campoTextoNombre.getText().isEmpty()){
			Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El campo \" Nombre \" no puede ir vacio");
			return false;
		} else if (this.comboPuesto.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El campo \"Puesto \" no puede ir vacio");
			return false;
		}//FIN ELSE-IF
		return true;
	}// FIN METODO VALIDAR DATOS
	
	//MANJEADOR DE COMPONENTES
	
	//MANEJADOR BOTON ACEPTAR
	@FXML private void manejadorBotonAceptar() {
		if (this.validarDatos()) {
			if (opcion == CREAR) {
				obtenerDatos();
				if (EmpleadoDAO.createEmpleado(conexion, empleado)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
					this.mainApp.getEscenarioDialogos().close();
				} else {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la información sea correcta");
					
				}
			}else if (opcion == EDITAR) {
				obtenerDatos();
				if (EmpleadoDAO.updateEmpleado(conexion, empleado)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se actualizo de forma correcta");
					this.mainApp.getEscenarioDialogos().close();
				} else {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo actualizar el registro, revisa que la información sea correcta");
				}
			} else if (opcion == VER) {
				this.mainApp.getEscenarioDialogos().close();
			}
		}//FIN IF
	}// FIN MANEJADOR BOTON ACEPTAR
	
	@FXML 
	private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN BOTON CERRAR
}//FIN CLASE


