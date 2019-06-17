package mx.shf6.produccion.view;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.CentroTrabajo;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.DetalleProceso;
import mx.shf6.produccion.model.GrupoTrabajo;
import mx.shf6.produccion.model.TipoComponente;
import mx.shf6.produccion.model.dao.CentroTrabajoDAO;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.GrupoTrabajoDAO;
import mx.shf6.produccion.utilities.AutoCompleteComboBoxListener;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoAgregarDetalleProceso {
	
	//VARIABLES
	private int syspk;
	
	//CONSTANTES
	public static int VER = 1;
	public static int CREAR = 2;
	public static int EDITAR = 3;
	
	//PROPIEDADES
	private MainApp mainApp;
	private int opcion;
	private DetalleProceso detalleProceso;
	private Componente componente;
	private ArrayList<CentroTrabajo> listaCentroTrabajo;
	private ArrayList<GrupoTrabajo> listaGrupoTrabajo;
	private ArrayList<Componente> listaComponente;
	private ObservableList<String> observableListaCentroTrabajo;
	private ObservableList<String> observableListaGrupoTrabajo;	
	private ObservableList<String> observableListaComponente;
	
	//COMPONENTE INTERFAZ
	@FXML private TextField campoOperacion;
	@FXML private TextField campoDescripcion;
	@FXML private TextField campoTiempoOperacion;
	@FXML private TextField campoTiempoPreparacion;
	@FXML private ComboBox<String> comboBoxCentroTrabajo;
	@FXML private ComboBox<String> comboBoxGrupoTrabajo;
	@FXML private ComboBox<String> comboBoxComponentes;
	@FXML private TextField campoCantidad;
	@FXML private Label labelComboboxParte;
	@FXML private Label labelCantidad;
	
	//INICIALIZAR
	@FXML private void initialize() {
		restricciones();
	}//FIN METODO
	
	//ACCESO A LA CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, DetalleProceso detalleProceso, int opcion, int syspk, Componente componente) {
		this.mainApp = mainApp;
		this.detalleProceso = detalleProceso;
		this.componente = componente;
		this.syspk = syspk;
		this.opcion = opcion;
		this.observableListaCentroTrabajo = FXCollections.observableArrayList();
		this.observableListaGrupoTrabajo = FXCollections.observableArrayList();
		this.observableListaComponente = FXCollections.observableArrayList();
		this.listaCentroTrabajo = CentroTrabajoDAO.readCentroTrabajo(this.mainApp.getConnection());
		this.listaGrupoTrabajo = GrupoTrabajoDAO.readGrupoTrabajo(this.mainApp.getConnection());
		this.listaComponente = ComponenteDAO.readComponente(this.mainApp.getConnection());
		inicializarCombos();
		mostrarDatosInterfaz();
	}//FIN METODO
	
	//RESTRICCIONES
	private void restricciones() {
		RestriccionTextField.soloNumeros(this.campoOperacion);
		RestriccionTextField.limitarNumeroCaracteres(this.campoOperacion, 4);
		RestriccionTextField.soloNumeros(this.campoTiempoPreparacion);
		RestriccionTextField.limitarNumeroCaracteres(this.campoTiempoPreparacion, 4);
		RestriccionTextField.soloNumeros(this.campoTiempoOperacion);
		RestriccionTextField.limitarNumeroCaracteres(this.campoTiempoOperacion, 4);
		RestriccionTextField.soloNumeros(this.campoCantidad);
		RestriccionTextField.limitarNumeroCaracteres(this.campoCantidad, 4);
	}//FIN METODO
	
	//INICIALIZAR COMBOS
	private void inicializarCombos() {
		listaCentroTrabajo = CentroTrabajoDAO.readCentroTrabajo(this.mainApp.getConnection());
		for (CentroTrabajo trabajo : listaCentroTrabajo)
			this.observableListaCentroTrabajo.add(trabajo.getDescripcion());
		this.comboBoxCentroTrabajo.setItems(this.observableListaCentroTrabajo);
		new AutoCompleteComboBoxListener(comboBoxCentroTrabajo);
		
		listaGrupoTrabajo = GrupoTrabajoDAO.readGrupoTrabajo(this.mainApp.getConnection());
		for (GrupoTrabajo grupo : listaGrupoTrabajo)
			this.observableListaGrupoTrabajo.add(grupo.getDescripcion());
		this.comboBoxGrupoTrabajo.setItems(this.observableListaGrupoTrabajo);
		new AutoCompleteComboBoxListener(comboBoxGrupoTrabajo);
		
		listaComponente = ComponenteDAO.readComponente(this.mainApp.getConnection());
		for (Componente componente : listaComponente)
			this.observableListaComponente.add(componente.getNumeroParte());
		this.comboBoxComponentes.setItems(this.observableListaComponente);
		new AutoCompleteComboBoxListener(comboBoxComponentes);
	}//FIN METODO
	
	//INICIALIZA COMPONENTES
	private void mostrarDatosInterfaz() {
		if (this.componente.getTipoComponente() != TipoComponente.ENSAMBLE && this.componente.getTipoComponente() != TipoComponente.SUB_ENSAMBLE) {
			if (this.opcion == CREAR) {
				this.campoOperacion.setUserData("");
				this.campoOperacion.setDisable(false);
				this.campoDescripcion.setUserData("");
				this.campoDescripcion.setDisable(false);
				this.campoTiempoPreparacion.setUserData("");
				this.campoTiempoOperacion.setDisable(false);
				this.campoTiempoOperacion.setUserData("");
				this.campoOperacion.setDisable(false);
				this.comboBoxCentroTrabajo.getSelectionModel().select("");
				this.comboBoxCentroTrabajo.setDisable(false);
				this.comboBoxGrupoTrabajo.getSelectionModel().select("");
				this.comboBoxGrupoTrabajo.setDisable(false);
				this.comboBoxComponentes.setVisible(false);
				this.labelComboboxParte.setVisible(false);
				this.labelCantidad.setVisible(false);
				this.campoCantidad.setVisible(false);
			} else if (this.opcion == EDITAR) {
				this.campoOperacion.setText(String.valueOf(this.detalleProceso.getOperacion()));
				this.campoOperacion.setDisable(false);
				this.campoDescripcion.setText(this.detalleProceso.getDescripcion());
				this.campoDescripcion.setDisable(false);
				this.campoTiempoPreparacion.setText(String.valueOf(this.detalleProceso.getTiempoPreparacion()));
				this.campoTiempoOperacion.setDisable(false);
				this.campoTiempoOperacion.setText(String.valueOf(this.detalleProceso.getTiempoOperacion()));
				this.campoOperacion.setDisable(false);
				this.comboBoxCentroTrabajo.setValue(this.detalleProceso.getNombreCentroTrabajo());
				this.comboBoxCentroTrabajo.setDisable(false);
				this.comboBoxGrupoTrabajo.setValue(this.detalleProceso.getNombreGrupoTrabajo());
			}//FIN IF ELSE
		} else {
			if (this.opcion == CREAR ) {
				this.campoOperacion.setUserData("");
				this.campoOperacion.setDisable(false);
				this.campoDescripcion.setUserData("");
				this.campoDescripcion.setDisable(false);
				this.campoTiempoPreparacion.setUserData("");
				this.campoTiempoOperacion.setDisable(false);
				this.campoTiempoOperacion.setUserData("");
				this.campoOperacion.setDisable(false);
				this.comboBoxCentroTrabajo.getSelectionModel().select("");
				this.comboBoxCentroTrabajo.setDisable(false);
				this.comboBoxGrupoTrabajo.getSelectionModel().select("");
				this.comboBoxGrupoTrabajo.setDisable(false);
				this.comboBoxComponentes.getSelectionModel().select("");
				this.comboBoxComponentes.setDisable(false);
				this.campoCantidad.setText("");
				this.campoCantidad.setDisable(false);
			} else if (this.opcion == EDITAR) {
				this.campoOperacion.setText(String.valueOf(this.detalleProceso.getOperacion()));
				this.campoOperacion.setDisable(false);
				this.campoDescripcion.setText(this.detalleProceso.getDescripcion());
				this.campoDescripcion.setDisable(false);
				this.campoTiempoPreparacion.setText(String.valueOf(this.detalleProceso.getTiempoPreparacion()));
				this.campoTiempoOperacion.setDisable(false);
				this.campoTiempoOperacion.setText(String.valueOf(this.detalleProceso.getTiempoOperacion()));
				this.campoOperacion.setDisable(false);
				this.comboBoxCentroTrabajo.setValue(this.detalleProceso.getNombreCentroTrabajo());
				this.comboBoxCentroTrabajo.setDisable(false);
				this.comboBoxGrupoTrabajo.setValue(this.detalleProceso.getNombreGrupoTrabajo());
				this.comboBoxGrupoTrabajo.setDisable(false);
				this.comboBoxComponentes.setValue(this.detalleProceso.getNombreComponenteFK());
				this.comboBoxComponentes.setDisable(false);
				this.campoCantidad.setText(String.valueOf(this.detalleProceso.getCantidad()));
				this.campoCantidad.setDisable(false);
			}//FIN IF-ELSE
		}//FIN IF ELSE			
	}//FIN METODO
	
	//VALIDAR DATOS
	private void validarDatos() {
		if (this.campoOperacion.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Descripci�n \" no puede estar vacio");
		} else if (this.campoDescripcion.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Operacion \" no puede estar vacio");
		} else if (this.campoTiempoPreparacion.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Tiempo de preparaci�n \" no puede estar vacio");
		} else if (this.campoTiempoOperacion.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Tiempo de operaci�n \" no puede estar vacio");
		} else if (this.comboBoxCentroTrabajo.getSelectionModel().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Centro de trabajo\" no puede estar vacio");
		} else if (this.comboBoxGrupoTrabajo.getSelectionModel().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Grupo de trabajo\" no puede estar vacio");
		} else {
			if (this.componente.getTipoComponente() != TipoComponente.ENSAMBLE && this.componente.getTipoComponente() != TipoComponente.SUB_ENSAMBLE) {
				this.detalleProceso.setOperacion(Integer.parseInt(this.campoOperacion.getText()));
				this.detalleProceso.setDescripcion(this.campoDescripcion.getText());
				this.detalleProceso.setTiempoPreparacion(Integer.parseInt(this.campoTiempoPreparacion.getText()));
				this.detalleProceso.setTiempoOperacion(Integer.parseInt(this.campoTiempoOperacion.getText()));
				this.detalleProceso.setCentroTrabajoFK(listaCentroTrabajo.get(comboBoxCentroTrabajo.getSelectionModel().getSelectedIndex()).getSysPK());
				this.detalleProceso.setGrupoTrabajoFK(listaGrupoTrabajo.get(comboBoxGrupoTrabajo.getSelectionModel().getSelectedIndex()).getSysPK());
				this.detalleProceso.setProcesoFK(this.syspk);
				this.detalleProceso.setCantidad(0);
				this.detalleProceso.setComponenteFK(0);
				
				this.mainApp.getEscenarioDialogosAlterno().close();
			} else {
				this.detalleProceso.setOperacion(Integer.parseInt(this.campoOperacion.getText()));
				this.detalleProceso.setDescripcion(this.campoDescripcion.getText());
				this.detalleProceso.setTiempoPreparacion(Integer.parseInt(this.campoTiempoPreparacion.getText()));
				this.detalleProceso.setTiempoOperacion(Integer.parseInt(this.campoTiempoOperacion.getText()));
				this.detalleProceso.setCentroTrabajoFK(listaCentroTrabajo.get(comboBoxCentroTrabajo.getSelectionModel().getSelectedIndex()).getSysPK());
				this.detalleProceso.setGrupoTrabajoFK(listaGrupoTrabajo.get(comboBoxGrupoTrabajo.getSelectionModel().getSelectedIndex()).getSysPK());
				this.detalleProceso.setProcesoFK(this.syspk);
				this.detalleProceso.setCantidad(Integer.parseInt(this.campoCantidad.getText()));
				this.detalleProceso.setComponenteFK(listaComponente.get(comboBoxComponentes.getSelectionModel().getSelectedIndex()).getSysPK());
				
				this.mainApp.getEscenarioDialogosAlterno().close();
			}//FIN IF ELSE
		}//FIN IF ELSE
	}//FIN METODO
	
	//RETORNO DE OBJETO
	public DetalleProceso getDetalleProceso() {
		return this.detalleProceso;
	}//FIN METODO
	
	//BOTON ACEPTAR
	@FXML private void manejadorBotonAceptar() {
		this.validarDatos();
	}//FIN METODO
	
	//BOTON CERRAR
	@FXML private void manejadorBotonCerrar() {
		this.detalleProceso = null;
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN MODELO
	
}//FIN CLASE