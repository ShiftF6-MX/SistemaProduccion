package mx.shf6.produccion.view;


import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.CentroTrabajo;
import mx.shf6.produccion.model.Proceso;
import mx.shf6.produccion.model.dao.CentroTrabajoDAO;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.EmpleadoDAO;
import mx.shf6.produccion.model.dao.ProcesoDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoProceso {
	
	//PROPIEDADES
	private MainApp mainApp;
	private Proceso proceso;
	private CentroTrabajo ct;
	
	private ArrayList<CentroTrabajo> listaCentrosTrabajo;
	private ObservableList<String> observableCT;
	private ObservableList<String> listaComponentes;
	private ObservableList<String> listaEmpleados;
	
	//VARIABLES
	private int opcion;
	
	//CONSTANTES
	public static final int MOSTRAR = 1;
	public static final int CREAR = 2;
	public static final int EDITAR = 3;
	
	//COMPONENTES INTERFAZ
	@FXML private DatePicker campoDateFecha;
	@FXML private TextField campoTextoCantidad;
	@FXML private TextField campoTextoOrdenamiento;
	@FXML private TextField campoTextoNivel;
	@FXML private ComboBox<String> comboBoxDestino;
	@FXML private ComboBox<String> comboTextoComponentes;
	@FXML private ComboBox<String> comboBoxEmpleados;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		RestriccionTextField.soloNumeros(this.campoTextoCantidad);
		RestriccionTextField.limitarNumeroCaracteres(this.campoTextoCantidad, 4);
		RestriccionTextField.soloNumeros(this.campoTextoOrdenamiento);
		RestriccionTextField.limitarNumeroCaracteres(this.campoTextoOrdenamiento, 4);
		RestriccionTextField.soloNumeros(this.campoTextoNivel);
		RestriccionTextField.limitarNumeroCaracteres(this.campoTextoNivel, 2);
		this.proceso = new Proceso();
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Proceso proceso, Integer opcion, CentroTrabajo ct) {
		this.mainApp = mainApp;
		this.proceso = proceso;
		this.opcion = opcion;
		this.ct = new CentroTrabajo();
		this.observableCT = FXCollections.observableArrayList();
		this.inicializarComponentes();
	}//FIN METODO
	
	private void inicializarCombosCT() {
		listaCentrosTrabajo = CentroTrabajoDAO.readCentroTrabajo(this.mainApp.getConnection());
		for (CentroTrabajo trabajo : listaCentrosTrabajo) 
			this.observableCT.add(trabajo.getDescripcion());
		this.comboBoxDestino.setItems(this.observableCT);
	}
	
	//INICIALIZA COMPONENTE
	private void inicializarComponentes() {
		if(this.opcion == MOSTRAR) {
			this.campoDateFecha.setUserData(this.proceso.getFecha());
			this.campoDateFecha.setDisable(true);
			this.campoTextoCantidad.setText(this.proceso.getCantidad().toString());
			this.campoTextoCantidad.setDisable(true);
			this.campoTextoOrdenamiento.setText(this.proceso.getOrdenamiento().toString());
			this.campoTextoOrdenamiento.setDisable(true);
			this.campoTextoNivel.setText(this.proceso.getNivel().toString());
			this.campoTextoNivel.setDisable(true);
			this.comboBoxDestino.getSelectionModel().select(proceso.getCentroTrabajoFK());
			this.comboBoxDestino.setDisable(true);
			this.comboTextoComponentes.getSelectionModel().select(proceso.getComponenteFK());
			this.comboTextoComponentes.setDisable(true);
			this.comboBoxEmpleados.getSelectionModel().select(proceso.getEmpleadoFK());
			this.comboBoxEmpleados.setDisable(true);
		} else if (this.opcion == CREAR) {
			this.campoDateFecha.setUserData("");
			this.campoDateFecha.setDisable(false);
			this.campoTextoCantidad.setText("");
			this.campoTextoCantidad.setDisable(false);
			this.campoTextoOrdenamiento.setText("");
			this.campoTextoOrdenamiento.setDisable(false);
			this.campoTextoNivel.setText("");
			this.campoTextoNivel.setDisable(false);
			this.comboBoxDestino.getSelectionModel().select("");
			this.comboBoxDestino.setDisable(false);
			this.comboTextoComponentes.getSelectionModel().select("");
			this.comboTextoComponentes.setDisable(false);
			this.comboBoxEmpleados.getSelectionModel().select("");
			this.comboBoxEmpleados.setDisable(false);
		} else if (this.opcion == EDITAR) {
			this.campoDateFecha.setUserData(this.proceso.getFecha());
			this.campoDateFecha.setDisable(false);
			this.campoTextoCantidad.setText(String.valueOf(proceso.getCantidad()));
			this.campoTextoCantidad.setDisable(false);
			this.campoTextoOrdenamiento.setText(String.valueOf(proceso.getOrdenamiento()));
			this.campoTextoOrdenamiento.setDisable(false);
			this.campoTextoNivel.setText(String.valueOf(proceso.getNivel()));
			this.campoTextoNivel.setDisable(false);
			this.comboBoxDestino.getSelectionModel().select(proceso.getCentroTrabajoFK());
			this.comboBoxDestino.setDisable(false);
			this.comboTextoComponentes.getSelectionModel().select(proceso.getComponenteFK());
			this.comboTextoComponentes.setDisable(false);
			this.comboBoxEmpleados.getSelectionModel().select(proceso.getEmpleadoFK());
			this.comboBoxEmpleados.setDisable(false);
		}//FIN IF-ELSE
	}//FIN METODO
	
	//VALIDAR METODOS
	private boolean validarDatos() {
		if(this.comboBoxDestino.getSelectionModel().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Destino \" no puede estar vacio");
			return false;
		} else if(this.comboBoxEmpleados.getSelectionModel().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Empleado\" no puede estar vacio");
			return false;
		} else if(this.comboTextoComponentes.getSelectionModel().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Componente \" no puede estar vacio");
			return false;
		} else if(this.campoDateFecha.getValue() == null) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Fecha \" no puede estar vacio");
			return false;
		} else if(this.campoTextoCantidad.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Cantidad \" no puede estar vacio");
			return false;
		} else if(this.campoTextoNivel.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Nivel \" no puede estar vacio");
			return false;
		} else if(this.campoTextoOrdenamiento.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Orden \" no puede estar vacio");
			return false;
		}//FIN IF-ELSE
		return true;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES
	@FXML private void manejadorBotonAceptar() {
		if(this.validarDatos()) {
			if(this.opcion == CREAR) {
				LocalDate 
				this.proceso.setFecha(this.campoDateFecha.d);
				this.proceso.setCantidad(Integer.parseInt(this.campoTextoCantidad.getText()));
				this.proceso.setOrdenamiento(Integer.parseInt(this.campoTextoOrdenamiento.getText()));
				this.proceso.setNivel(Integer.parseInt(this.campoTextoNivel.getText()));
				this.proceso.setCentroTrabajoFK(this.comboBoxDestino.getSelectionModel().getSelectedItem());
				this.proceso.setComponenteFK(this.comboTextoComponentes.getSelectionModel().getSelectedItem());
				this.proceso.setEmpleadoFK(this.comboBoxEmpleados.getSelectionModel().getSelectedItem());
				
				if (ProcesoDAO.createProceso(this.mainApp.getConnection(), this.proceso)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
				} else {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la información sea correcta");
				}
				
			} else if (this.opcion == EDITAR) {
				this.proceso.setFecha(fecha);
			}
		}//FIN
	}//FIN METODO 
	
}//FIN CLASE
