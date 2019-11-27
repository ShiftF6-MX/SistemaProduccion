package mx.shf6.produccion.view;

import java.sql.Connection;
import java.sql.Date;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.DetalleOrdenCompra;
import mx.shf6.produccion.model.OrdenCompra;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.DetalleOrdenCompraDAO;
import mx.shf6.produccion.utilities.Notificacion;

public class DialogoAgregarDetalleOrdenCompra {
	
	//PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private OrdenCompra ordenCompra;
	private DetalleOrdenCompra detalleOrdenCompra;
	
	//VARIABLES
	private int opcion;
	
	//CONSTANTES
	public static final int CREAR = 0;
	public static final int EDITAR = 1;
	public static final int MOSTRAR = 2;
	
	//COMPONENTES DE LA INTERFAZ
	@FXML private TextField textFieldPlanoOrdenamiento;
	@FXML private TextField textFieldItem;
	@FXML private TextField textFieldPorEntregar;
	@FXML private TextField textFieldProcesoPintura;
	@FXML private DatePicker datePickerFechaCliente;
	@FXML private DatePicker datePickerEntregaFinal;
	@FXML private ComboBox<Componente> comboBoxComponente;
	
	//METODOS
	@FXML private void initialize() {
		this.detalleOrdenCompra = new DetalleOrdenCompra();
		this.ordenCompra = new OrdenCompra();
	}//FIN METODO

	public void setMainApp(MainApp mainApp, DetalleOrdenCompra detalleOrdenCompra, OrdenCompra ordenCompra, int opcion) {
		this.mainApp = mainApp;
		this.connection = this.mainApp.getConnection();
		this.detalleOrdenCompra = detalleOrdenCompra;
		this.ordenCompra = ordenCompra;
		this.opcion = opcion;
		initComponentes();
		showInterfaz();
	}//FIN METODO
	
	private void initComponentes() {
		this.comboBoxComponente.setItems(FXCollections.observableArrayList(ComponenteDAO.readComponente(connection)));
	}//FIN METODO

	private void showInterfaz() {
		if (this.opcion == EDITAR) {
			this.textFieldPlanoOrdenamiento.setText(this.detalleOrdenCompra.getPlanoOrdenamiento());
			this.textFieldPlanoOrdenamiento.setDisable(false);
			this.textFieldItem.setText(this.detalleOrdenCompra.getItem());
			this.textFieldItem.setDisable(false);
			this.textFieldPorEntregar.setText(this.detalleOrdenCompra.getPorEntregar().toString());
			this.textFieldPorEntregar.setDisable(false);
			this.textFieldProcesoPintura.setText(this.detalleOrdenCompra.getProcesoPintura());
			this.textFieldProcesoPintura.setDisable(false);
			this.datePickerFechaCliente.setValue(this.detalleOrdenCompra.getFechaCliente().toLocalDate());
			this.datePickerFechaCliente.setDisable(false);
			if (this.detalleOrdenCompra.getEntregaFinal() == null)
				this.datePickerEntregaFinal.setValue(null);
			else
				this.datePickerEntregaFinal.setValue(this.detalleOrdenCompra.getEntregaFinal().toLocalDate());
			this.datePickerEntregaFinal.setDisable(false);
			this.comboBoxComponente.getSelectionModel().select(this.detalleOrdenCompra.getComponenteFK());
			this.comboBoxComponente.setDisable(false);
		} else if (this.opcion == MOSTRAR) {
			this.textFieldPlanoOrdenamiento.setText(this.detalleOrdenCompra.getPlanoOrdenamiento());
			this.textFieldPlanoOrdenamiento.setDisable(true);
			this.textFieldItem.setText(this.detalleOrdenCompra.getItem());
			this.textFieldItem.setDisable(true);
			this.textFieldPorEntregar.setText(this.detalleOrdenCompra.getPorEntregar().toString());
			this.textFieldPorEntregar.setDisable(true);
			this.textFieldProcesoPintura.setText(this.detalleOrdenCompra.getProcesoPintura());
			this.textFieldProcesoPintura.setDisable(true);
			this.datePickerFechaCliente.setValue(this.detalleOrdenCompra.getFechaCliente().toLocalDate());
			this.datePickerFechaCliente.setDisable(true);
			if (this.detalleOrdenCompra.getEntregaFinal() == null)
				this.datePickerEntregaFinal.setValue(null);
			else
				this.datePickerEntregaFinal.setValue(this.detalleOrdenCompra.getEntregaFinal().toLocalDate());
			this.datePickerEntregaFinal.setDisable(true);
			this.comboBoxComponente.getSelectionModel().select(this.detalleOrdenCompra.getComponenteFK());
			this.comboBoxComponente.setDisable(true);
		}//FIN IF/ELSE
	}//FIN METODO
	
	private boolean checkComponentes() {
		if (this.textFieldItem.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Item\" no puede estar vac�o");
			return false;
		} else if (this.textFieldPorEntregar.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Por Entregar \" no puede estar vac�o");
			return false;
		} else if (this.datePickerFechaCliente.getValue() == null) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Fecha Cliente\" no puede estar vac�o");
			return false;
		} else if (this.comboBoxComponente.getValue() == null) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Componente\" no puede estar vac�o");
			return false;
		}//FIN IF/ELSE
		return true;
	}//FIN METODO
	
	private boolean saveInformacion() {
		this.detalleOrdenCompra.setPlanoOdernamiento(this.textFieldPlanoOrdenamiento.getText());
		this.detalleOrdenCompra.setItem(this.textFieldItem.getText());
		this.detalleOrdenCompra.setFechaCliente(Date.valueOf(this.datePickerFechaCliente.getValue()));
		if (this.datePickerEntregaFinal.getValue() == null)
			this.detalleOrdenCompra.setEntregaFinal(null);
		else
			this.detalleOrdenCompra.setEntregaFinal(Date.valueOf(this.datePickerEntregaFinal.getValue()));
		this.detalleOrdenCompra.setPorEntregar(Integer.valueOf(this.textFieldPorEntregar.getText()));
		this.detalleOrdenCompra.setProcesoPintura(this.textFieldProcesoPintura.getText());
		this.detalleOrdenCompra.setComponenteFK(this.comboBoxComponente.getSelectionModel().getSelectedItem());
		this.detalleOrdenCompra.setOrdenCompraFK(this.ordenCompra);
		if (this.opcion == CREAR) {
			this.detalleOrdenCompra.setSaldo(Integer.valueOf(this.textFieldPorEntregar.getText()));
			if (DetalleOrdenCompraDAO.create(connection, detalleOrdenCompra))
				return true;
			else
				return false;
		} else if (this.opcion == EDITAR) {
			if (DetalleOrdenCompraDAO.update(connection, detalleOrdenCompra))
				return true;
			else
				return false;
		}else
			return true;		
	}//FIN METODO
	
	//MANEJADORES
	@FXML private void manejadorBotonAceptar () {
		if (this.checkComponentes()) {
			if (saveInformacion()) {
				if (this.opcion != MOSTRAR)
					Notificacion.dialogoAlerta(AlertType.CONFIRMATION, "", "El registro se realiz� correctamente");
				this.mainApp.getEscenarioDialogosAlterno().close();
			}else
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "No se pudo realizar el registro correctamente, revisa que la informaci�n sea correcta");
		}//FIN IF		
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN METODO
}//FIN CLASE
