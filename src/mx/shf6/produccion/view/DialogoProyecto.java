package mx.shf6.produccion.view;

import java.io.File;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.Proyecto;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.ProyectoDAO;
import mx.shf6.produccion.utilities.Notificacion;

public class DialogoProyecto {

	
	//PROPIEDADES
	private MainApp mainApp;
	private Proyecto proyecto;
	private Cliente cliente;
	private ArrayList<Cliente> listaClientes;
	private ObservableList<String> listaNombreClientes;
	private ObservableList<Integer> listaSysPKClientes;
	
	//VARIABLES
	private int opcion;
	
	//CONSTANTES
	public static final int CREAR = 1;
	public static final int VER = 2;
	public static final int EDITAR = 3;
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoCodigo;
	@FXML private TextField campoTextoDescripcion;
	@FXML private TextField campoTextoCarpeta;
	@FXML private TextField campoTextoEspecificacionTecnica;
	@FXML private ComboBox<String> comboBoxClientes;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.proyecto = new Proyecto();
		this.cliente = new Cliente();
		listaClientes = new ArrayList<Cliente>();
		listaNombreClientes = FXCollections.observableArrayList();
		listaSysPKClientes = FXCollections.observableArrayList();
		
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Proyecto proyecto, int opcion) {
		this.mainApp = mainApp;
		this.proyecto = proyecto;
		this.opcion =opcion;
			
		listaClientes = ClienteDAO.readCliente(this.mainApp.getConnection());
		for (Cliente cliente : listaClientes) {
			this.listaSysPKClientes.add(cliente.getSysPK());
			this.listaNombreClientes.add(cliente.getNombre());
		}
		this.comboBoxClientes.setItems(listaNombreClientes);
		
		this.inicializarComponentes();
		
	}//FIN METODO
	
	//INICIALIZA COMPONENTE
	private void inicializarComponentes() {		
		if (this.opcion == CREAR) {
			this.campoTextoCodigo.setText("");
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText("");
			this.campoTextoDescripcion.setDisable(false);
			this.campoTextoCarpeta.setText("");
			this.campoTextoCarpeta.setDisable(false);
			this.campoTextoEspecificacionTecnica.setText("");
			this.campoTextoEspecificacionTecnica.setDisable(false);
			
		} else if (this.opcion == VER) {
			this.campoTextoCodigo.setText(this.proyecto.getCodigo());
			this.campoTextoCodigo.setDisable(true);
			this.campoTextoDescripcion.setText(this.proyecto.getDescripcion());
			this.campoTextoDescripcion.setDisable(true);
			this.campoTextoCarpeta.setText(MainApp.RAIZ_SERVIDOR);
			this.campoTextoCarpeta.setDisable(true);
			this.campoTextoEspecificacionTecnica.setText(this.proyecto.getEspecificacionTecnica());
			this.campoTextoEspecificacionTecnica.setDisable(true);
			this.comboBoxClientes.setValue("");
			this.comboBoxClientes.setDisable(true);
		} else if (this.opcion == EDITAR) {
			this.campoTextoCodigo.setText(this.proyecto.getCodigo());
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText(this.proyecto.getDescripcion());
			this.campoTextoDescripcion.setDisable(false);
			this.campoTextoCarpeta.setText(this.proyecto.getCarpeta());
			this.campoTextoCarpeta.setDisable(false);
			this.campoTextoEspecificacionTecnica.setText(this.proyecto.getEspecificacionTecnica());
			this.campoTextoEspecificacionTecnica.setDisable(false);
			this.comboBoxClientes.setValue("");
			this.comboBoxClientes.setDisable(false);
		}//FIN METODO
	}//FIN METODO
	
	//VALIDAR DATOS
	private boolean validarDatos() {
		if (this.campoTextoCodigo.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Código\" no puede estar vacio");
			return false;
		} else if (this.campoTextoDescripcion.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Descripción\" no puede estar vacio");
			return false;
		}else if (this.campoTextoCarpeta.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Carpeta\" no puede estar vacio");
			return false;
		}//FIN IF/ESLE
		return true;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES	
	@FXML private void vmanejadorBotonAceptar() {
		if (this.validarDatos() && this.opcion == CREAR) {
			this.proyecto.setCodigo(this.campoTextoCodigo.getText());
			this.proyecto.setDescripcion(this.campoTextoDescripcion.getText());
			this.proyecto.setCarpeta(this.campoTextoCarpeta.getText());
			this.proyecto.setEspecificacionTenica(this.campoTextoEspecificacionTecnica.getText());
		    
			for(Cliente cliente : listaClientes) {
		    	if(this.comboBoxClientes.getValue().equals(cliente.getNombre())) {
		    		this.proyecto.setClienteFK(cliente.getSysPK());
		    	}
		    }
		    	
			
			if (ProyectoDAO.createProyecto(this.mainApp.getConnection(), this.proyecto)) {
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
				this.mainApp.getEscenarioDialogos().close();
			} else
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la información sea correcta");
		} else if (this.validarDatos() && this.opcion == EDITAR) {
			this.proyecto.setCodigo(this.campoTextoCodigo.getText());
			this.proyecto.setDescripcion(this.campoTextoDescripcion.getText());
			this.proyecto.setCarpeta(this.campoTextoCarpeta.getText());
			this.proyecto.setEspecificacionTenica(this.campoTextoEspecificacionTecnica.getText());
			this.proyecto.setClienteFK(this.listaClientes.get(this.comboBoxClientes.getSelectionModel().getSelectedIndex()).getSysPK());
			
			if (ProyectoDAO.updateProyecto(this.mainApp.getConnection(), this.proyecto)) {
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se actualizo de forma correcta");
				this.mainApp.getEscenarioDialogos().close();
			} else
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo actualizar el registro, revisa que la información sea correcta");
		} else if (this.validarDatos() && this.opcion == VER)
			this.mainApp.getEscenarioDialogos().close();			
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
	
}//FIN CLASE


