package mx.shf6.produccion.view;

import java.util.ArrayList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.Cotizacion;
import mx.shf6.produccion.model.Folio;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.CotizacionDAO;
import mx.shf6.produccion.model.dao.FolioDAO;
import mx.shf6.produccion.utilities.Notificacion;

public class DialogoCotizacion {
	
	//CONSTANTES
	public final static int VER = 1;
	public final static int CREAR = 2;
	public final static int EDITAR = 3;

	//PROPIEDADES
	private MainApp mainApp;
	private Cotizacion cotizacion;
	private int opcion;
	private ArrayList<Folio> listaFolios;
	private ArrayList<Cliente> listaClientes;
	private ObservableList<String> listaLetraFolio;
	private ObservableList<String> listaNombreClientes;
	private ObservableList<String> listaMonedas;
	
	//COMPONENTES INTERZAS USUARIO
	@FXML private ComboBox<String> comboBoxFolio;
	@FXML private ComboBox<String> comboBoxClientes;
	@FXML private TextField campoTextoSolicito;
	@FXML private TextField campoTextoAreaDepartamento;
	@FXML private TextField campoTextoTelefonoFax;
	@FXML private TextField campoTextoEmail;
	@FXML private TextField campoTextoTipoServicio;
	@FXML private TextField campoTextoFechaEntrega;
	@FXML private TextField campoTextoCondicionEmbarque;
	@FXML private TextField campoTextoCondicionPago;
	@FXML private ComboBox<String> comboBoxMonedas;
	@FXML private TextField campoTextoTipoCambio;
	@FXML private TextField campoTextoObservaciones;
	@FXML private TextField campoTextoVigencia;;
	
	//INICIALIZA COMPONENTES CONTROLAN INTERFAZ USUARIO
	@FXML private void initialize() {
		listaMonedas = FXCollections.observableArrayList();
		listaClientes = new ArrayList<Cliente>();
		listaFolios = new ArrayList<Folio>();
		listaNombreClientes = FXCollections.observableArrayList();
		listaLetraFolio = FXCollections.observableArrayList();
		listaMonedas.add("MXN");
		listaMonedas.add("USD");
		listaMonedas.add("EUR");
		comboBoxMonedas.setItems(listaMonedas);		
	}//FIN METODO
		
	//ACCESO CLASE PRINCIPAL CONTROLA VISTAS
	public void setMainApp(MainApp mainApp, Cotizacion cotizacion, int opcion) {
		this.cotizacion = cotizacion;
		this.mainApp = mainApp;
		this.opcion = opcion;		
		listaClientes = ClienteDAO.readCliente(this.mainApp.getConnection());
		listaFolios = FolioDAO.readFolio(this.mainApp.getConnection());
		for (Cliente cliente : listaClientes)
			this.listaNombreClientes.add(cliente.getNombre());
		for (Folio folio : listaFolios) 
			this.listaLetraFolio.add(folio.getFolio());
		this.comboBoxClientes.setItems(listaNombreClientes);
		this.comboBoxFolio.setItems(listaLetraFolio);
		this.mostrarDatosInterfaz();
	}//FIN METODO	
		
	//MUESTRA DATOS DEL LA SOLICITUD
	private void mostrarDatosInterfaz() {
		if (this.opcion == CREAR) {
			this.comboBoxClientes.getSelectionModel().select("");
			this.comboBoxClientes.setDisable(false);
			this.campoTextoSolicito.setText("");
			this.campoTextoSolicito.setDisable(false);
			this.campoTextoAreaDepartamento.setText("");
			this.campoTextoAreaDepartamento.setDisable(false);
			this.campoTextoTelefonoFax.setText("");
			this.campoTextoTelefonoFax.setDisable(false);
			this.campoTextoEmail.setText("");
			this.campoTextoEmail.setDisable(false);
			this.campoTextoTipoServicio.setText("");
			this.campoTextoTipoServicio.setDisable(false);
			this.campoTextoFechaEntrega.setText("");
			this.campoTextoFechaEntrega.setDisable(false);
			this.campoTextoCondicionEmbarque.setText("");
			this.campoTextoCondicionEmbarque.setDisable(false);
			this.campoTextoCondicionPago.setText("");
			this.campoTextoCondicionPago.setDisable(false);
			this.comboBoxMonedas.getSelectionModel().select("");
			this.comboBoxMonedas.setDisable(false);
			this.campoTextoTipoCambio.setText("");
			this.campoTextoTipoCambio.setDisable(false);
			this.campoTextoObservaciones.setText("");
			this.campoTextoObservaciones.setDisable(false);
			this.campoTextoVigencia.setText("");
			this.campoTextoVigencia.setDisable(false);
			
		} else if (this.opcion == VER) {
			this.comboBoxFolio.setDisable(true);
			this.comboBoxFolio.setValue(this.cotizacion.getFolio(this.mainApp.getConnection()).getFolio());
			this.comboBoxClientes.getSelectionModel().select(this.cotizacion.getCliente(this.mainApp.getConnection()).getNombre());
			this.comboBoxClientes.setDisable(true);
			this.campoTextoSolicito.setText(this.cotizacion.getSolicitante());
			this.campoTextoSolicito.setDisable(true);
			this.campoTextoAreaDepartamento.setText(this.cotizacion.getAreaDepartamento());
			this.campoTextoAreaDepartamento.setDisable(true);
			this.campoTextoTelefonoFax.setText(this.cotizacion.getTelefonoFax());
			this.campoTextoTelefonoFax.setDisable(true);
			this.campoTextoEmail.setText(this.cotizacion.getEmail());
			this.campoTextoEmail.setDisable(true);
			this.campoTextoTipoServicio.setText(this.cotizacion.getTipoServicio());
			this.campoTextoTipoServicio.setDisable(true);
			this.campoTextoFechaEntrega.setText(this.cotizacion.getFechaEntrega());
			this.campoTextoFechaEntrega.setDisable(true);
			this.campoTextoCondicionEmbarque.setText(this.cotizacion.getCondicionEmbarque());
			this.campoTextoCondicionEmbarque.setDisable(true);
			this.campoTextoCondicionPago.setText(this.cotizacion.getCondicionPago());
			this.campoTextoCondicionPago.setDisable(true);
			this.comboBoxMonedas.getSelectionModel().select(this.cotizacion.getDescripcionMoneda());
			this.comboBoxMonedas.setDisable(true);
			this.campoTextoTipoCambio.setText(this.cotizacion.getTipoCambio().toString());
			this.campoTextoTipoCambio.setDisable(true);
			this.campoTextoObservaciones.setText(this.cotizacion.getObservaciones());
			this.campoTextoObservaciones.setDisable(true);
			this.campoTextoVigencia.setText(this.cotizacion.getVigencia());
			this.campoTextoVigencia.setDisable(true);
			
		} else if (this.opcion == EDITAR) {
			this.comboBoxFolio.setDisable(true);
			this.comboBoxFolio.setValue(this.cotizacion.getFolio(this.mainApp.getConnection()).getFolio());
			this.comboBoxClientes.getSelectionModel().select(this.cotizacion.getCliente(this.mainApp.getConnection()).getNombre());
			this.comboBoxClientes.setDisable(false);
			this.campoTextoSolicito.setText(this.cotizacion.getSolicitante());
			this.campoTextoSolicito.setDisable(false);
			this.campoTextoAreaDepartamento.setText(this.cotizacion.getAreaDepartamento());
			this.campoTextoAreaDepartamento.setDisable(false);
			this.campoTextoTelefonoFax.setText(this.cotizacion.getTelefonoFax());
			this.campoTextoTelefonoFax.setDisable(false);
			this.campoTextoEmail.setText(this.cotizacion.getEmail());
			this.campoTextoEmail.setDisable(false);
			this.campoTextoTipoServicio.setText(this.cotizacion.getTipoServicio());
			this.campoTextoTipoServicio.setDisable(false);
			this.campoTextoFechaEntrega.setText(this.cotizacion.getFechaEntrega());
			this.campoTextoFechaEntrega.setDisable(false);
			this.campoTextoCondicionEmbarque.setText(this.cotizacion.getCondicionEmbarque());
			this.campoTextoCondicionEmbarque.setDisable(false);
			this.campoTextoCondicionPago.setText(this.cotizacion.getCondicionPago());
			this.campoTextoCondicionPago.setDisable(false);
			this.comboBoxMonedas.getSelectionModel().select(this.cotizacion.getDescripcionMoneda());
			this.comboBoxMonedas.setDisable(false);
			this.campoTextoTipoCambio.setText(this.cotizacion.getTipoCambio().toString());
			this.campoTextoTipoCambio.setDisable(false);
			this.campoTextoObservaciones.setText(this.cotizacion.getObservaciones());
			this.campoTextoObservaciones.setDisable(false);
			this.campoTextoVigencia.setText(this.cotizacion.getVigencia());
			this.campoTextoVigencia.setDisable(false);			
		}//FIN IF/ELSE
	}	
	
	//FIN METODO
	private boolean validarDatos() {
		if (this.campoTextoVigencia.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Vigencia\" no puede estar vacio");
			return false;
		}else if (this.campoTextoTipoCambio.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Tipo Cambio\" no puede estar vacio");
			return false;
		}else if (this.comboBoxMonedas.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Moneda\" no puede estar vacio");
			return false;
		}else if (this.campoTextoCondicionPago.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Condicion Pago\" no puede estar vacio");
			return false;
		}else if (this.campoTextoCondicionEmbarque.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Condicion Embarque\" no puede estar vacio");
			return false;
		}else if (this.campoTextoFechaEntrega.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Código\" no puede estar vacio");
			return false;
		} else if (this.comboBoxClientes.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Cliente\" no puede estar vacio");
			return false;
		}//FIN IF/ESLE
		return true;
	}//FIN METODO
	
	
	
	@FXML private void btnGuardar() {
		if (this.validarDatos() && this.opcion == CREAR) {
			this.cotizacion.setReferencia(listaFolios.get(comboBoxFolio.getSelectionModel().getSelectedIndex()).getFolio() + (listaFolios.get(comboBoxFolio.getSelectionModel().getSelectedIndex()).getSerie() + 1));
			this.cotizacion.setStatus(Cotizacion.PENDIENTE);
			this.cotizacion.setClienteFK(listaClientes.get(comboBoxClientes.getSelectionModel().getSelectedIndex()).getSysPK());
			this.cotizacion.setSolicitante(this.campoTextoSolicito.getText());
			this.cotizacion.setAreaDepartamento(this.campoTextoAreaDepartamento.getText());
			this.cotizacion.setTelefonoFax(this.campoTextoTelefonoFax.getText());
			this.cotizacion.setEmail(this.campoTextoEmail.getText());
			this.cotizacion.setTipoServicio(this.campoTextoTipoServicio.getText());
			this.cotizacion.setFechaEntrega(this.campoTextoFechaEntrega.getText());
			this.cotizacion.setCondicionEmbarque(this.campoTextoCondicionEmbarque.getText());
			this.cotizacion.setCondicionPago(this.campoTextoCondicionPago.getText());
			this.cotizacion.setNumeroMoneda(this.comboBoxMonedas.getValue());
			this.cotizacion.setTipoCambio(Double.parseDouble(this.campoTextoTipoCambio.getText()));
			this.cotizacion.setObservaciones(this.campoTextoObservaciones.getText());
			this.cotizacion.setVigencia(this.campoTextoVigencia.getText());
			this.cotizacion.setFolioFK(listaFolios.get(comboBoxFolio.getSelectionModel().getSelectedIndex()).getSysPK());
			if (CotizacionDAO.createCotizacion(this.mainApp.getConnection(), cotizacion)) {
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
				this.mainApp.getEscenarioDialogos().close();
			} else
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la información sea correcta");
		} else if (this.validarDatos() && this.opcion == EDITAR) {
			this.cotizacion.setClienteFK(listaClientes.get(comboBoxClientes.getSelectionModel().getSelectedIndex()).getSysPK());
			this.cotizacion.setSolicitante(this.campoTextoSolicito.getText());
			this.cotizacion.setAreaDepartamento(this.campoTextoAreaDepartamento.getText());
			this.cotizacion.setTelefonoFax(this.campoTextoTelefonoFax.getText());
			this.cotizacion.setEmail(this.campoTextoEmail.getText());
			this.cotizacion.setFechaEntrega(this.campoTextoFechaEntrega.getText());
			this.cotizacion.setCondicionEmbarque(this.campoTextoCondicionEmbarque.getText());
			this.cotizacion.setCondicionPago(this.campoTextoCondicionPago.getText());
			this.cotizacion.setNumeroMoneda(this.comboBoxMonedas.getValue());
			this.cotizacion.setTipoCambio(Double.parseDouble(this.campoTextoTipoCambio.getText()));
			this.cotizacion.setObservaciones(this.campoTextoObservaciones.getText());
			this.cotizacion.setVigencia(this.campoTextoVigencia.getText());
			if (CotizacionDAO.updateCotizacion(this.mainApp.getConnection(), cotizacion)) {				
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se actualizo de forma correcta");
				this.mainApp.getEscenarioDialogos().close();
			} else
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo actualizar el registro, revisa que la información sea correcta");
		} else if (this.validarDatos() && this.opcion == VER)
			this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO

	@FXML private void btnCancelar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
}//FIN CLASE