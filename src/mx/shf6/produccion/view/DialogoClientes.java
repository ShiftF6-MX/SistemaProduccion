package mx.shf6.produccion.view;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.Domicilio;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.DomicilioDAO;
import mx.shf6.produccion.model.dao.SepomexDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoClientes {

	//PROPIEDADES
	private MainApp mainApp;
	private Cliente cliente;
	private ClienteDAO clienteDAO;
	private Domicilio domicilio;
	private DomicilioDAO domicilioDAO;
	private SepomexDAO sepomexDAO;
	private Integer opcionInterfaz;
	private RestriccionTextField restriccionTextField;
	private ObservableList<String> listaEstados;
	private ObservableList<String> listaMunicipios;
	private ObservableList<String> listaStatus;
	
	//CONSTANTES
	public final static int MOSTRAR_INSTITUCION = 1;
	public final static int CREAR_INSTITUCION = 2;
	
	/*//COMPONENTES INTERFAZ 
	@FXML private TextField nombreField;
	@FXML private TextField codigoField;
	@FXML private TextField saldoField;
	@FXML private TextField numeroProyectosField;
	@FXML private TextField registroContribuyenteField;
	@FXML private TextField telefonoField;
	@FXML private TextField correoField;
	@FXML private TextField fechaRegistroField;
	@FXML private TextField rutaField;
	@FXML private TextField statusClienteField;
	@FXML private TextField calleField;
	@FXML private TextField numeroExteriorField;
	@FXML private TextField numeroInteriorField;
	@FXML private TextField coloniaField;
	@FXML private TextField localidadField;
	@FXML private ComboBox<String> statusCombo;
	@FXML private ComboBox<String> municipioCombo;
	@FXML private ComboBox<String> estadoCombo;
	@FXML private TextField codigoPostalField;*/
	
	//INICIALIZA LOS COMPOMENTES QUE SE CONTROLAN EN LA INTERFAZ DE USUARIO
	@FXML private void initialize() {
		/*listaStatus = FXCollections.observableArrayList();
		listaStatus.add("Bloqueado");
    	listaStatus.add("Activo");
    	listaStatus.add("Baja");
    	statusCombo.setItems(listaStatus);
    	cliente = new Cliente();
    	clienteDAO = new ClienteDAO();  
    	domicilio = new Domicilio();
		domicilioDAO = new DomicilioDAO();
		this.restriccionTextField = new RestriccionTextField();
		this.restriccionTextField.limitarNumeroCaracteres(this.nombreField, 64);
		this.restriccionTextField.limitarNumeroCaracteres(this.registroContribuyenteField,16);
		this.restriccionTextField.limitarNumeroCaracteres(this.telefonoField,12);
		this.restriccionTextField.soloNumeros(this.telefonoField);
		this.restriccionTextField.limitarNumeroCaracteres(this.correoField,64);
		this.restriccionTextField.limitarNumeroCaracteres(this.calleField, 64);
		this.restriccionTextField.limitarNumeroCaracteres(this.numeroInteriorField, 16);
		this.restriccionTextField.limitarNumeroCaracteres(this.numeroExteriorField, 16);
		this.restriccionTextField.limitarNumeroCaracteres(this.coloniaField, 32);
		this.restriccionTextField.limitarNumeroCaracteres(this.localidadField, 32);
		this.restriccionTextField.limitarNumeroCaracteres(this.codigoPostalField, 5);
		this.restriccionTextField.soloNumeros(this.codigoPostalField);*/
	}//FIN METODO
	
	public void setMainApp(MainApp mainApp, Cliente cliente, Integer opcionInterfaz) {
		this.mainApp = mainApp;
		/*this.cliente = cliente;
		this.opcionInterfaz = opcionInterfaz;
		this.listaEstados = sepomexDAO.leerEstados(this.mainApp.getConnection()); 
		estadoCombo.setItems(listaEstados);
		estadoCombo.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> {
			municipioCombo.getSelectionModel().clearSelection();
			listaMunicipios = this.sepomexDAO.leerMunicipios(this.mainApp.getConnection(), estadoCombo.getValue()); 
			municipioCombo.setItems(listaMunicipios);
		  });//FIN SENTENCIA
    	this.mostrarDatosInterfaz();*/
	}//FIN METODO
	
	//MUESTRA LOS DATOS DE LA INSTITUCION
	private void mostrarDatosInterfaz() {
		if (this.opcionInterfaz.equals(DialogoClientes.MOSTRAR_INSTITUCION)) {
			this.activarEdicion(false);
			this.informacionControles(this.cliente);
		} else if (this.opcionInterfaz.equals(DialogoClientes.CREAR_INSTITUCION)) {
			this.activarEdicion(true);
			this.informacionControles(null);
		}//FIN IF/ELSE
	}//FIN METODO
	
	private void activarEdicion(boolean opcion) {
		/*nombreField.setEditable(opcion);
		registroContribuyenteField.setEditable(opcion);
		telefonoField.setEditable(opcion);
		correoField.setEditable(opcion);
		fechaRegistroField.setEditable(opcion);
		statusCombo.setDisable(!opcion); 
		estadoCombo.setDisable(!opcion);
		municipioCombo.setDisable(!opcion);
		localidadField.setEditable(opcion);
		coloniaField.setEditable(opcion);
		calleField.setEditable(opcion);
		numeroExteriorField.setEditable(opcion);
		numeroInteriorField.setEditable(opcion);
		codigoPostalField.setEditable(opcion);*/
	}//FIN METODO
	
	private void informacionControles(Cliente cliente) {
		/*if (cliente != null) {
			nombreField.setText(cliente.getNombre());
			registroContribuyenteField.setText(cliente.getRegistroContribuyente());
			telefonoField.setText(cliente.getTelefono());
			correoField.setText(cliente.getCorreo());
			fechaRegistroField.setText(cliente.getFechaRegistro().toString());
		   	statusCombo.setValue(cliente.getDescripcionStatus());
		   	Domicilio domicilio = cliente.getDomicilio(this.mainApp.getConnection());
			estadoCombo.setValue(domicilio.getEstado());
			municipioCombo.setValue(domicilio.getMunicipio());
			localidadField.setText(domicilio.getLocalidad());
			coloniaField.setText(domicilio.getColonia());
			calleField.setText(domicilio.getCalle());
			numeroExteriorField.setText(domicilio.getNumeroExterior());
			numeroInteriorField.setText(domicilio.getNumeroInterior());
			codigoPostalField.setText(domicilio.getCodigoPostal());
        } else {
        	nombreField.setText("");
        	registroContribuyenteField.setText("");
			telefonoField.setText("");
    		correoField.setText("");
    		fechaRegistroField.setVisible(false);
			telefonoField.setText("");
			localidadField.setText("");
			coloniaField.setText("");
			calleField.setText("");
			numeroExteriorField.setText("");
			numeroInteriorField.setText("");
			codigoPostalField.setText("");
        }//FIN IF/ELSE*/
	}//FIN METODO
	
	//VALIDACION DE CAMPOS
	public boolean validacion(){
		/*String mensajeError = "";
		
		if (this.codigoPostalField.getText() == null || this.codigoPostalField.getText().length() == 0)
			mensajeError = "Campo Codigo Postal vacío";
		
		if (this.municipioCombo.getValue() == null || this.municipioCombo.getValue().length() == 0)
			mensajeError = "Seleccione un municipio";
		
		if (this.estadoCombo.getValue() == null || this.estadoCombo.getValue().length() == 0)
			mensajeError = "Seleccione un estado";
		
		if (this.localidadField.getText() == null || this.localidadField.getText().length() == 0)
			mensajeError = "Campo 'Localidad' vacio";
		
		if (this.coloniaField.getText() == null || this.coloniaField.getText().length() == 0)
			mensajeError = "Campo 'Colonia' vacío";
		
		if (this.numeroInteriorField.getText() == null || this.numeroInteriorField.getText().length() == 0)
			mensajeError = "Campo 'Numero Interior' vacío";
		
		if (this.calleField.getText() == null || this.calleField.getText().length() == 0)
			mensajeError = "Campo 'Calle' vacío";
		
		if (this.statusCombo.getValue() == null || this.statusCombo.getValue().length() == 0)
			mensajeError = "Seleccione el status de la constructora";
		
		if (this.correoField.getText() == null || this.correoField.getText().length() == 0)
			mensajeError = "Campo 'Correo' vacío";
		
		if (this.telefonoField.getText() == null || this.telefonoField.getText().length() == 0)
			mensajeError = "Campo 'Teléfono' vacío";
		
		if (this.registroContribuyenteField.getText() == null || this.registroContribuyenteField.getText().length() == 0)
			mensajeError = "Campo 'RFC' vacío";
		
		if (this.nombreField.getText() == null || this.nombreField.getText().length() == 0)
			mensajeError = "Campo 'Nombre Social' vacío";		
		
		if (mensajeError.length() == 0) {			
			return true;
		}else {
			Notificacion.dialogoAlerta(AlertType.ERROR, "Mensaje CANADEVI", mensajeError);
			mensajeError="";
		}//FIN IF-ELSE*/
		return false;		
	}//FIN METODO
	
	@FXML private void cerrarDialogoButtonHandler() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
}//FIN METODO