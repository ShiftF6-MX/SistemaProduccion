package mx.shf6.produccion.view;


import java.text.DecimalFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Status;
import mx.shf6.produccion.model.TipoComponente;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.dao.AcabadoDAO;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.ConsecutivoDAO;
import mx.shf6.produccion.model.dao.MaterialDAO;
import mx.shf6.produccion.model.dao.TipoMateriaPrimaDAO;
import mx.shf6.produccion.model.dao.TipoMiscelaneoDAO;
import mx.shf6.produccion.model.dao.TratamientoDAO;
import mx.shf6.produccion.utilities.Dimensiones;
import mx.shf6.produccion.utilities.Notificacion;

public class DialogoComponente {

	//PROPIEDADES
	private MainApp mainApp;
	private Componente componente;
	private DecimalFormat decimalFormat;
	
	//VARIABLES
	private int opcion;
	
	//CONSTANTES
	public static final int CREAR = 1;
	public static final int VER = 2;
	public static final int EDITAR = 3;
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoNumeroParte;
	@FXML private TextField campoTextoDescripcion;
	@FXML private TextField campoTextoLargo;
	@FXML private TextField campoTextoAncho;
	@FXML private TextField campoTextoAltoEspesor;
	@FXML private TextField campoTextoCosto;
	@FXML private TextField campoTextoUnidad;
	@FXML private TextField campoTextoNotas;
	@FXML private ComboBox<String> comboBoxStatus;
	@FXML private TextField campoTextoConsecutivo;
	@FXML private ComboBox<String> comboBoxTipoComponente;
	@FXML private ComboBox<String> comboBoxMaterial;
	@FXML private ComboBox<String> comboBoxCliente;
	@FXML private ComboBox<String> comboBoxTipoMateriaPrima;
	@FXML private ComboBox<String> comboBoxTipoMiscelaneo;
	@FXML private ComboBox<String> comboBoxAcabado;
	@FXML private ComboBox<String> comboBoxTratamiento;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.componente = new Componente();
		this.decimalFormat = new DecimalFormat("000");
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Componente componente, int opcion) {
		this.mainApp = mainApp;
		this.componente = componente;
		this.opcion = opcion;
		this.inicializarComponentes();
		this.inicializarCombos();
	}//FIN METODO
	
	private void inicializarCombos() {
		ObservableList<String> listaStatus = FXCollections.observableArrayList("No Visible", "Visible");
		this.comboBoxStatus.setItems(listaStatus);
		ObservableList<String> listaComponentes = FXCollections.observableArrayList(TipoComponente.COMPRADO, TipoComponente.MATERIA_PRIMA, TipoComponente.ENSAMBLE, TipoComponente.PARTE_PRIMARIA);
		this.comboBoxTipoComponente.setItems(listaComponentes);
		this.comboBoxTipoComponente.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue == TipoComponente.COMPRADO) {
					comboBoxCliente.getSelectionModel().select("");
					comboBoxCliente.setDisable(true);
					comboBoxTipoMateriaPrima.getSelectionModel().select("");
					comboBoxTipoMateriaPrima.setDisable(true);
					comboBoxTipoMiscelaneo.getSelectionModel().select("");
					comboBoxTipoMiscelaneo.setDisable(false);
					comboBoxAcabado.getSelectionModel().select("");
					comboBoxAcabado.setDisable(true);
					comboBoxTratamiento.getSelectionModel().select("");
					comboBoxTratamiento.setDisable(false);
					campoTextoLargo.setText("0.0");
					campoTextoLargo.setDisable(true);
					campoTextoAncho.setText("0.0");
					campoTextoAncho.setDisable(true);
					campoTextoAltoEspesor.setText("0.0");
					campoTextoAltoEspesor.setDisable(true);
				} else if(newValue == TipoComponente.MATERIA_PRIMA) {
					comboBoxCliente.getSelectionModel().select("");
					comboBoxCliente.setDisable(true);
					comboBoxTipoMateriaPrima.getSelectionModel().select("");
					comboBoxTipoMateriaPrima.setDisable(false);
					comboBoxTipoMiscelaneo.getSelectionModel().select("");
					comboBoxTipoMiscelaneo.setDisable(true);
					comboBoxAcabado.getSelectionModel().select("");
					comboBoxAcabado.setDisable(false);
					comboBoxTratamiento.getSelectionModel().select("");
					comboBoxTratamiento.setDisable(true);
					campoTextoLargo.setText("0.0");
					campoTextoLargo.setDisable(true);
					campoTextoAncho.setText("0.0");
					campoTextoAncho.setDisable(true);
					campoTextoAltoEspesor.setText("0.0");
					campoTextoAltoEspesor.setDisable(true);
				} else if(newValue == TipoComponente.ENSAMBLE || newValue == TipoComponente.PARTE_PRIMARIA) {
					comboBoxCliente.getSelectionModel().select("");
					comboBoxCliente.setDisable(false);
					comboBoxTipoMateriaPrima.getSelectionModel().select("");
					comboBoxTipoMateriaPrima.setDisable(true);
					comboBoxTipoMiscelaneo.getSelectionModel().select("");
					comboBoxTipoMiscelaneo.setDisable(true);
					comboBoxAcabado.getSelectionModel().select("");
					comboBoxAcabado.setDisable(true);
					comboBoxTratamiento.getSelectionModel().select("");
					comboBoxTratamiento.setDisable(true);
					campoTextoLargo.setText("");
					campoTextoLargo.setDisable(false);
					campoTextoAncho.setText("");
					campoTextoAncho.setDisable(false);
					campoTextoAltoEspesor.setText("");
					campoTextoAltoEspesor.setDisable(false);
				}//FIN IF/ELSE
			}//FIN METODO
			
		});//FIN SENTENCIA
		
		ObservableList<String> listaClientes = ClienteDAO.listaNombresClientes(this.mainApp.getConnection());
		this.comboBoxCliente.setItems(listaClientes);
		ObservableList<String> listaMateriales = MaterialDAO.listaTiposMaterial(this.mainApp.getConnection());
		this.comboBoxMaterial.setItems(listaMateriales);
		ObservableList<String> listaTipoMateriaPrima = TipoMateriaPrimaDAO.listaTiposMateriaPrima(this.mainApp.getConnection());
		this.comboBoxTipoMateriaPrima.setItems(listaTipoMateriaPrima);
		ObservableList<String> listaTipoMiscelaneos = TipoMiscelaneoDAO.listaTiposMiscelaneo(this.mainApp.getConnection());
		this.comboBoxTipoMiscelaneo.setItems(listaTipoMiscelaneos);
		ObservableList<String> listaAcabados = AcabadoDAO.listaTiposAcabado(this.mainApp.getConnection());
		this.comboBoxAcabado.setItems(listaAcabados);
		ObservableList<String> listaTratamientos = TratamientoDAO.listaTiposTratamiento(this.mainApp.getConnection());
		this.comboBoxTratamiento.setItems(listaTratamientos);
	}//FIN METODO
	
	//INICIALIZA COMPONENTE
	private void inicializarComponentes() {		
		if (this.opcion == CREAR) {
			this.campoTextoNumeroParte.setText("");
			this.campoTextoNumeroParte.setDisable(true);
			this.campoTextoDescripcion.setText("");
			this.campoTextoDescripcion.setDisable(false);
			this.campoTextoLargo.setText("");
			this.campoTextoLargo.setDisable(false);
			this.campoTextoAncho.setText("");
			this.campoTextoAncho.setDisable(false);
			this.campoTextoAltoEspesor.setText("");
			this.campoTextoAltoEspesor.setDisable(false);
			this.campoTextoCosto.setText("");
			this.campoTextoCosto.setDisable(false);
			this.campoTextoUnidad.setText("");
			this.campoTextoUnidad.setDisable(false);
			this.campoTextoNotas.setText("");
			this.campoTextoNotas.setDisable(false);
			this.comboBoxStatus.getSelectionModel().select("");
			this.comboBoxStatus.setDisable(false);
			this.campoTextoConsecutivo.setText("");
			this.campoTextoConsecutivo.setDisable(true);
			this.comboBoxTipoComponente.getSelectionModel().select("");
			this.comboBoxTipoComponente.setDisable(false);
			this.comboBoxMaterial.getSelectionModel().select("");
			this.comboBoxMaterial.setDisable(false);
			this.comboBoxCliente.getSelectionModel().select("");
			this.comboBoxCliente.setDisable(false);
			this.comboBoxTipoMateriaPrima.getSelectionModel().select("");
			this.comboBoxTipoMateriaPrima.setDisable(false);
			this.comboBoxTipoMiscelaneo.getSelectionModel().select("");
			this.comboBoxTipoMiscelaneo.setDisable(false);
			this.comboBoxAcabado.getSelectionModel().select("");
			this.comboBoxAcabado.setDisable(false);
			this.comboBoxTratamiento.getSelectionModel().select("");
			this.comboBoxTratamiento.setDisable(false);
		} else if (this.opcion == VER) {
			this.campoTextoNumeroParte.setText(this.componente.getNumeroParte());
			this.campoTextoNumeroParte.setDisable(true);
			this.campoTextoDescripcion.setText(componente.getDescripcion());
			this.campoTextoDescripcion.setDisable(true);
			this.campoTextoLargo.setText(String.valueOf(componente.getDimensiones().getLargo()));
			this.campoTextoLargo.setDisable(true);
			this.campoTextoAncho.setText(String.valueOf(componente.getDimensiones().getAncho()));
			this.campoTextoAncho.setDisable(true);
			this.campoTextoAltoEspesor.setText(String.valueOf(componente.getDimensiones().getAltoEspesor()));
			this.campoTextoAltoEspesor.setDisable(true);
			this.campoTextoCosto.setText(String.valueOf(componente.getCosto()));
			this.campoTextoCosto.setDisable(true);
			this.campoTextoUnidad.setText(componente.getUnidad());
			this.campoTextoUnidad.setDisable(true);
			this.campoTextoNotas.setText(componente.getNotas());
			this.campoTextoNotas.setDisable(true);
			this.comboBoxStatus.getSelectionModel().select(componente.getStatus());
			this.comboBoxStatus.setDisable(true);
			String consecutivo = decimalFormat.format(componente.getConsecutivo());
			this.campoTextoConsecutivo.setText(consecutivo);
			this.campoTextoConsecutivo.setDisable(true);
			this.comboBoxTipoComponente.getSelectionModel().select(componente.getTipoComponente());
			this.comboBoxTipoComponente.setDisable(true);
			this.comboBoxMaterial.getSelectionModel().select(componente.getMaterial(this.mainApp.getConnection()).getDescripcion());
			this.comboBoxMaterial.setDisable(true);
			this.comboBoxCliente.getSelectionModel().select(componente.getCliente(this.mainApp.getConnection()).getNombre());
			this.comboBoxCliente.setDisable(true);
			this.comboBoxTipoMateriaPrima.getSelectionModel().select(componente.getTipoMateriaPrima(this.mainApp.getConnection()).getDescripcion());
			this.comboBoxTipoMateriaPrima.setDisable(true);
			this.comboBoxTipoMiscelaneo.getSelectionModel().select(componente.getTipoMiscelaneo(this.mainApp.getConnection()).getDescripcion());
			this.comboBoxTipoMiscelaneo.setDisable(true);
			this.comboBoxAcabado.getSelectionModel().select(componente.getAcabado(this.mainApp.getConnection()).getDescripcion());
			this.comboBoxAcabado.setDisable(true);
			this.comboBoxTratamiento.getSelectionModel().select(componente.getTratamiento(this.mainApp.getConnection()).getDescripcion());
			this.comboBoxTratamiento.setDisable(true);
		} else if (this.opcion == EDITAR) {
			this.campoTextoNumeroParte.setText(this.componente.getNumeroParte());
			this.campoTextoNumeroParte.setDisable(true);
			this.campoTextoDescripcion.setText(componente.getDescripcion());
			this.campoTextoDescripcion.setDisable(false);
			this.campoTextoLargo.setText(String.valueOf(componente.getDimensiones().getLargo()));
			this.campoTextoLargo.setDisable(false);
			this.campoTextoAncho.setText(String.valueOf(componente.getDimensiones().getAncho()));
			this.campoTextoAncho.setDisable(false);
			this.campoTextoAltoEspesor.setText(String.valueOf(componente.getDimensiones().getAltoEspesor()));
			this.campoTextoAltoEspesor.setDisable(false);
			this.campoTextoCosto.setText(String.valueOf(componente.getCosto()));
			this.campoTextoCosto.setDisable(false);
			this.campoTextoUnidad.setText(componente.getUnidad());
			this.campoTextoUnidad.setDisable(false);
			this.campoTextoNotas.setText(componente.getNotas());
			this.campoTextoNotas.setDisable(false);
			this.comboBoxStatus.getSelectionModel().select(componente.getStatus());
			this.comboBoxStatus.setDisable(false);
			String consecutivo = decimalFormat.format(componente.getConsecutivo());
			this.campoTextoConsecutivo.setText(consecutivo);
			this.campoTextoConsecutivo.setDisable(true);
			this.comboBoxTipoComponente.getSelectionModel().select(componente.getTipoComponente());
			this.comboBoxTipoComponente.setDisable(true);
			this.comboBoxMaterial.getSelectionModel().select(componente.getMaterial(this.mainApp.getConnection()).getDescripcion());
			this.comboBoxMaterial.setDisable(false);
			this.comboBoxCliente.getSelectionModel().select(componente.getCliente(this.mainApp.getConnection()).getNombre());
			this.comboBoxCliente.setDisable(true);
			this.comboBoxTipoMateriaPrima.getSelectionModel().select(componente.getTipoMateriaPrima(this.mainApp.getConnection()).getDescripcion());
			this.comboBoxTipoMateriaPrima.setDisable(true);
			this.comboBoxTipoMiscelaneo.getSelectionModel().select(componente.getTipoMiscelaneo(this.mainApp.getConnection()).getDescripcion());
			this.comboBoxTipoMiscelaneo.setDisable(true);
			this.comboBoxAcabado.getSelectionModel().select(componente.getAcabado(this.mainApp.getConnection()).getDescripcion());
			this.comboBoxAcabado.setDisable(true);
			this.comboBoxTratamiento.getSelectionModel().select(componente.getTratamiento(this.mainApp.getConnection()).getDescripcion());
			this.comboBoxTratamiento.setDisable(true);
		}//FIN METODO
	}//FIN METODO
	
	//VALIDAR DATOS
	private boolean validarDatos() {
		if (this.comboBoxTipoComponente.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Tipo Componente\" no puede estar vacio");
			return false;
		} else if (this.comboBoxMaterial.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Material\" no puede estar vacio");
			return false;
		} else if (this.comboBoxCliente.getSelectionModel().getSelectedItem().isEmpty() && (this.comboBoxTipoComponente.getSelectionModel().getSelectedItem() == TipoComponente.PARTE_PRIMARIA || this.comboBoxTipoComponente.getSelectionModel().getSelectedItem() == TipoComponente.ENSAMBLE) ) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Cliente\" no puede estar vacio");
			return false;
		} else if (this.comboBoxTipoMateriaPrima.getSelectionModel().getSelectedItem().isEmpty() && this.comboBoxTipoComponente.getSelectionModel().getSelectedItem() == TipoComponente.MATERIA_PRIMA) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Tipo Materia Prima\" no puede estar vacio");
			return false;
		} else if (this.comboBoxTipoMiscelaneo.getSelectionModel().getSelectedItem().isEmpty() && this.comboBoxTipoComponente.getSelectionModel().getSelectedItem() == TipoComponente.COMPRADO) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Tipo Miscelaneo\" no puede estar vacio");
			return false;
		} else if (this.comboBoxAcabado.getSelectionModel().getSelectedItem().isEmpty() && this.comboBoxTipoComponente.getSelectionModel().getSelectedItem() == TipoComponente.MATERIA_PRIMA) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Acabado\" no puede estar vacio");
			return false;
		} else if (this.comboBoxTratamiento.getSelectionModel().getSelectedItem().isEmpty() && this.comboBoxTipoComponente.getSelectionModel().getSelectedItem() == TipoComponente.COMPRADO) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Tratamiento\" no puede estar vacio");
			return false;
		} else if (this.campoTextoDescripcion.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Descripcion\" no puede estar vacio");
			return false;
		} else if (this.campoTextoLargo.getText().isEmpty() || this.campoTextoAncho.getText().isEmpty() || this.campoTextoAltoEspesor.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "Los campos de \"Dimensiones\" no puede estar vacio");
			return false;
		} else if (this.campoTextoCosto.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Costo\" no puede estar vacio");
			return false;
		}  else if (this.comboBoxStatus.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Status\" no puede estar vacio");
			return false;
		}//FIN IF/ESLE
		return true;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES	
	@FXML private void manejadorBotonAceptar() {
		if (this.validarDatos()) {
			if (this.opcion == CREAR) {
				this.componente.setTipoComponente(TipoComponente.toCharacter(this.comboBoxTipoComponente.getSelectionModel().getSelectedItem()));
				this.componente.setMaterialFK(MaterialDAO.readMaterial(this.mainApp.getConnection(), this.comboBoxMaterial.getSelectionModel().getSelectedItem()).get(0).getSysPK());
				
				if (this.comboBoxCliente.getSelectionModel().getSelectedItem().isEmpty() == false)
					this.componente.setClienteFK(ClienteDAO.readCliente(this.mainApp.getConnection(), this.comboBoxCliente.getSelectionModel().getSelectedItem()).get(0).getSysPK());
				
				if (this.comboBoxTipoMateriaPrima.getSelectionModel().getSelectedItem().isEmpty() == false)
					this.componente.setTipoMateriaPrimaFK(TipoMateriaPrimaDAO.readTipoMateriaPrima(this.mainApp.getConnection(), this.comboBoxTipoMateriaPrima.getSelectionModel().getSelectedItem()).get(0).getSysPK());
				
				if (this.comboBoxTipoMiscelaneo.getSelectionModel().getSelectedItem().isEmpty() == false)
					this.componente.setTipoMiscelaneoFK(TipoMiscelaneoDAO.readTipoMiscelaneo(this.mainApp.getConnection(), this.comboBoxTipoMiscelaneo.getSelectionModel().getSelectedItem()).get(0).getSysPK());
				
				if (this.comboBoxAcabado.getSelectionModel().getSelectedItem().isEmpty() == false)
					this.componente.setAcabadoFK(AcabadoDAO.readAcabado(this.mainApp.getConnection(), this.comboBoxAcabado.getSelectionModel().getSelectedItem()).get(0).getSysPK());
				
				if (this.comboBoxTratamiento.getSelectionModel().getSelectedItem().isEmpty() == false)
					this.componente.setTratamientoFK(TratamientoDAO.readTratamiento(this.mainApp.getConnection(), this.comboBoxTratamiento.getSelectionModel().getSelectedItem()).get(0).getSysPK());
				
				if (this.componente.getTipoComponente() == TipoComponente.ENSAMBLE || this.componente.getTipoComponente() == TipoComponente.PARTE_PRIMARIA)
					this.componente.setConsecutivo(ConsecutivoDAO.readConsecutivoCliente(this.mainApp.getConnection(), this.componente.getclienteFK(), this.componente.getTipoComponenteChar()));
				else if (this.componente.getTipoComponente() == TipoComponente.MATERIA_PRIMA)
					this.componente.setConsecutivo(ConsecutivoDAO.readConsecutivoTipoMateriaPrima(this.mainApp.getConnection(), this.componente.getTipoMateriaPrimaFK()));
				else if (this.componente.getTipoComponente() == TipoComponente.COMPRADO)
					this.componente.setConsecutivo(ConsecutivoDAO.readConsecutivoTipoMiscelaneo(this.mainApp.getConnection(), this.componente.getTipoMiscelaneoFK()));
				
				this.componente.setNumeroParte(this.componente.doNumeroParte(this.mainApp.getConnection()));
				this.componente.setDescripcion(this.campoTextoDescripcion.getText());
				
				Dimensiones dimensiones = new Dimensiones();
				dimensiones.setLargo(Double.parseDouble(this.campoTextoLargo.getText()));
				dimensiones.setAncho(Double.parseDouble(this.campoTextoAncho.getText()));
				dimensiones.setAltoEspesor(Double.parseDouble(this.campoTextoAltoEspesor.getText()));
				this.componente.setDimensiones(dimensiones);
				
				this.componente.setCosto(Double.parseDouble(this.campoTextoCosto.getText()));
				this.componente.setUnidad(this.campoTextoUnidad.getText());
				this.componente.setNotas(this.campoTextoNotas.getText());
				this.componente.setStatus(Status.toInt(this.comboBoxStatus.getSelectionModel().getSelectedItem()));
				
				if (ComponenteDAO.createComponente(this.mainApp.getConnection(), this.componente)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
					this.mainApp.getEscenarioDialogos().close();
				} else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la información sea correcta");
			} else if (this.opcion == EDITAR) {
				this.componente.setTipoComponente(TipoComponente.toCharacter(this.comboBoxTipoComponente.getSelectionModel().getSelectedItem()));
				this.componente.setMaterialFK(MaterialDAO.readMaterial(this.mainApp.getConnection(), this.comboBoxMaterial.getSelectionModel().getSelectedItem()).get(0).getSysPK());
				
				if (this.comboBoxCliente.getSelectionModel().getSelectedItem().isEmpty() == false)
					this.componente.setClienteFK(ClienteDAO.readCliente(this.mainApp.getConnection(), this.comboBoxCliente.getSelectionModel().getSelectedItem()).get(0).getSysPK());
				
				if (this.comboBoxTipoMateriaPrima.getSelectionModel().getSelectedItem().isEmpty() == false)
					this.componente.setTipoMateriaPrimaFK(TipoMateriaPrimaDAO.readTipoMateriaPrima(this.mainApp.getConnection(), this.comboBoxTipoMateriaPrima.getSelectionModel().getSelectedItem()).get(0).getSysPK());
				
				if (this.comboBoxTipoMiscelaneo.getSelectionModel().getSelectedItem().isEmpty() == false)
					this.componente.setTipoMiscelaneoFK(TipoMiscelaneoDAO.readTipoMiscelaneo(this.mainApp.getConnection(), this.comboBoxTipoMiscelaneo.getSelectionModel().getSelectedItem()).get(0).getSysPK());
				
				if (this.comboBoxAcabado.getSelectionModel().getSelectedItem().isEmpty() == false)
					this.componente.setAcabadoFK(AcabadoDAO.readAcabado(this.mainApp.getConnection(), this.comboBoxAcabado.getSelectionModel().getSelectedItem()).get(0).getSysPK());
				
				if (this.comboBoxTratamiento.getSelectionModel().getSelectedItem().isEmpty() == false)
					this.componente.setTratamientoFK(TratamientoDAO.readTratamiento(this.mainApp.getConnection(), this.comboBoxTratamiento.getSelectionModel().getSelectedItem()).get(0).getSysPK());
				
				this.componente.setNumeroParte(this.componente.doNumeroParte(this.mainApp.getConnection()));
				this.componente.setDescripcion(this.campoTextoDescripcion.getText());
				
				Dimensiones dimensiones = new Dimensiones();
				dimensiones.setLargo(Double.parseDouble(this.campoTextoLargo.getText()));
				dimensiones.setAncho(Double.parseDouble(this.campoTextoAncho.getText()));
				dimensiones.setAltoEspesor(Double.parseDouble(this.campoTextoAltoEspesor.getText()));
				this.componente.setDimensiones(dimensiones);
				
				this.componente.setCosto(Double.parseDouble(this.campoTextoCosto.getText()));
				this.componente.setUnidad(this.campoTextoUnidad.getText());
				this.componente.setNotas(this.campoTextoNotas.getText());
				this.componente.setStatus(Status.toInt(this.comboBoxStatus.getSelectionModel().getSelectedItem()));
				
				if (ComponenteDAO.updateComponente(this.mainApp.getConnection(), this.componente)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se actualizo de forma correcta");
					this.mainApp.getEscenarioDialogos().close();
				} else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo actualizar el registro, revisa que la información sea correcta");
			} else if (this.opcion == VER)
				this.mainApp.getEscenarioDialogos().close();
		}//FIN METODO
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
	
}//FIN CLASE
