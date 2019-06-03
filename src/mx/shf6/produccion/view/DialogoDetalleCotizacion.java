package mx.shf6.produccion.view;

import java.util.ArrayList;

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
import mx.shf6.produccion.model.Proyecto;
import mx.shf6.produccion.model.dao.DetalleCotizacionDAO;
import mx.shf6.produccion.model.dao.ProyectoDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoDetalleCotizacion {

	//PROPIEDADES
	private MainApp mainApp;
	private Cotizacion cotizacion;
	private Proyecto proyecto;
	private DetalleCotizacion detalleCotizacio;
	private ArrayList<Proyecto> listaProyectos;
	private ObservableList<String> listaNombreProyectos;
	
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
		RestriccionTextField.limitarPuntoDecimal(this.campoTextoCantidad);
		this.listaNombreProyectos = FXCollections.observableArrayList();
		this.detalleCotizacio = new DetalleCotizacion();
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Cotizacion cotizacion) {
		this.mainApp = mainApp;
		this.cotizacion = cotizacion;
		listaProyectos = ProyectoDAO.readProyectoCliente(this.mainApp.getConnection(), cotizacion.getClienteFK());
		for (Proyecto proyecto : listaProyectos) {
			listaNombreProyectos.add(proyecto.getDescripcion());
		}//FIN FOR
		comboBoxProyectos.setItems(listaNombreProyectos);
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
			this.campoTextoCantidad.setText(this.detalleCotizacio.getCantidad().toString());
			this.campoTextoCantidad.setDisable(true);
			this.campoTextoObservaciones.setText(this.detalleCotizacio.getObservaciones());
			this.campoTextoObservaciones.setDisable(true);
			this.comboBoxProyectos.getSelectionModel().select(this.detalleCotizacio.getProyecto(this.mainApp.getConnection()).getDescripcion());
			this.comboBoxProyectos.setDisable(true);
		} else if (this.opcion == EDITAR) {
			this.campoTextoCantidad.setText(this.detalleCotizacio.getCantidad().toString());
			this.campoTextoCantidad.setDisable(false);
			this.campoTextoObservaciones.setText(this.detalleCotizacio.getObservaciones());
			this.campoTextoObservaciones.setDisable(false);
			this.comboBoxProyectos.getSelectionModel().select(this.detalleCotizacio.getProyecto(this.mainApp.getConnection()).getDescripcion());
			this.comboBoxProyectos.setDisable(false);
		}//FIN METODO
	}//FIN METODO
	
	//VALIDAR DATOS
	private boolean validarDatos() {
		if (this.campoTextoCantidad.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Código\" no puede estar vacio");
			return false;
		} else if (this.comboBoxProyectos.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Proyectos\" no puede estar vacio");
			return false;
		}//FIN IF/ESLE
		return true;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES	
	@FXML private void manejadorBotonAceptar() {
		if (validarDatos()) {
			if (DetalleCotizacionDAO.createDetalleCotizacion(this.mainApp.getConnection(), getDetalleCotizacion())) {
				Notificacion.dialogoAlerta(AlertType.CONFIRMATION, "", "El proyecto se agrego de forma correcta a la cotización");
				this.mainApp.getEscenarioDialogosAlterno().close();
			} else
				Notificacion.dialogoAlerta(AlertType.WARNING, "", "El proyecto no se pudo agregar a la cotización, ¡revisa que la información sea correcta!");
		}//FIN IF
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN METODO	
	
	public DetalleCotizacion getDetalleCotizacion() {
		this.proyecto = listaProyectos.get(comboBoxProyectos.getSelectionModel().getSelectedIndex());
		detalleCotizacio.setCantidad(Double.parseDouble(campoTextoCantidad.getText()));
		detalleCotizacio.setCosto(proyecto.getCostoDirecto() + proyecto.getCostoIndirecto());
		detalleCotizacio.setPrecio(proyecto.getPrecio());
		detalleCotizacio.setProyectoFK(proyecto.getSysPK());
		detalleCotizacio.setCotizacionFK(cotizacion.getSysPK());
		detalleCotizacio.setObservaciones(campoTextoObservaciones.getText());
		return detalleCotizacio;
	}//FIN METODO
	
}//FIN CLASE