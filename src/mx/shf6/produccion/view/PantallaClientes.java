package mx.shf6.produccion.view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.Seguridad;
import mx.shf6.produccion.utilities.Notificacion;

public class PantallaClientes {

	//PROPIEDADES
	private MainApp mainApp;
	//private Cliente cliente;
	private ClienteDAO clienteDAO;
	private int cantidadRenglonesTabla;
	private int cantidadRegistrosTablaClientes;
	private int cantidadPaginasTablaClientes;
	//private int usuarioSistema;
	private ArrayList<Object> listaClientes;
	
	/*
	//COMPONENTES INTERZAS USUARIO
	@FXML private TableView<Cliente> tablaCliente;
	@FXML private TableColumn<Cliente, String> codigoColumna;
	@FXML private TableColumn<Cliente, String> nombreColumna;
	@FXML private TableColumn<Cliente, String> registroContribuyentesColumna;
	@FXML private TableColumn<Cliente, String> telefonoColumna;
	@FXML private TableColumn<Cliente, String> correoColumna;
	@FXML private TableColumn<Cliente, Double> saldoColumna;
	@FXML private TableColumn<Cliente, String> accionesColumn;	
	@FXML private Pagination paginacionTablaClientes;
	@FXML private TextField buscarCliente;	
	@FXML private Pagination paginacionTablaSolicitudes;
	*/
	
	//INICIALIZA COMPONENTES CONTROLAN INTERFAZ USUARIO
	@FXML private void initialize() {
		/*
		this.cliente = new Cliente();
		this.clienteDAO = new ClienteDAO();
		this.buscarCliente.setOnKeyPressed(new EventHandler<KeyEvent>() {
    		@Override
    		public void handle(KeyEvent event) {
    			if (event.getCode().equals(KeyCode.ENTER))
    				buscarButtonHandler();
    		}//FIN METODO
    	});//FIN SENTENCIA
		this.inicializaTabla();
		*/
	}//FIN METODO
	/*
	//ACTUALIZA LA TABLA DE ACUERDO AL CRITERIO DE BÚSQUEDA
	@FXML private void buscarButtonHandler() {
    	if (Seguridad.verificarAcceso(this.mainApp.getConnection(), this.mainApp.getUsuario().getGrupoUsuarioFk(), "rClientes")) {
    		tablaCliente.setItems(null);
    		listaClientes.clear();
    		listaClientes = clienteDAO.leer(this.mainApp.getConnection(), "", buscarCliente.getText());
    		tablaCliente.setItems(clienteDAO.toObservableList(listaClientes));
    	} else {
    		Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
    	}//FIN IF-ELSE
    	
    }//FIN METODO
	*/
	
	//ACCESO CLASE PRINCIPAL CONTROLA VISTAS
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		//listaClientes = clienteDAO.leer(this.mainApp.getConnection(), "", ""); 
		//this.actualizarTabla();
		//asignarVariables();
	}//FIN METODO	
	
	/*
	//INICIALIZA LOS COMPONENTES DE LA TABLA DE CLIENTES
	private void inicializaTabla() {
    	codigoColumna.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
        nombreColumna.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        registroContribuyentesColumna.setCellValueFactory(cellData -> cellData.getValue().registroContribuyenteProperty());
        correoColumna.setCellValueFactory(cellData -> cellData.getValue().correoProperty());
        
        accionesColumn.setCellValueFactory(new PropertyValueFactory<>("DUM"));
        Callback<TableColumn<Cliente, String>, TableCell<Cliente, String>> cellFactory =  param -> {
        	
        	final TableCell<Cliente, String> cell = new TableCell<Cliente, String>() {  
        		final Button botonVer = new Button("V");
        		final Button botonEditar = new Button("E");
        		final Button botonEliminar = new Button("B");
        		final HBox acciones = new HBox(botonVer, botonEditar, botonEliminar);
        		
		        //PARA MOSTRAR LOS DIALOGOS DE INSTITUCION
		        @Override
		        public void updateItem(String item, boolean empty) {
		        	acciones.setSpacing(5);
		        	acciones.setPrefWidth(80.0);
		        	acciones.setAlignment(Pos.CENTER_LEFT);
		        	super.updateItem(item, empty);
		        	if (empty) {
		        		setGraphic(null);
		                setText(null);
		            } else {
		            	
		                
		            }//FIN IF/ELSE
		        }//FIN METODO
		    };//FIN METODO
		    
		    return cell;
		};//FIN METODO
		accionesColumn.setCellFactory(cellFactory);
    }//FIN METODO
	*/
	@FXML private void nuevoCliente() {
		this.mainApp.iniciarDialogoClietes();
	}//FIN METODO	

	/*
	//ACTUALIZA LA TABLA CON LOS ULTIMOS CAMBIOS EN LA BASE DE DATOS
	private void actualizarTabla() {
		tablaCliente.setItems(null);
		listaClientes.clear();
		listaClientes = clienteDAO.leer(this.mainApp.getConnection(), "", "");
		if (!listaClientes.isEmpty()) {
			this.asignarVariables();
			//tablaInformacionVivienda.setItems(informacionViviendaDAO.toObservableList(listaInformacionVivienda));
	    	buscarCliente.setText("");	
			this.paginacionTablaSolicitudes.setDisable(false);
		} else
			this.paginacionTablaSolicitudes.setDisable(true);
	}//FIN METODO
	 
	private void asignarVariables() {
		this.cantidadRenglonesTabla = 4;
		this.cantidadRegistrosTablaClientes = this.listaClientes.size();
		if ((this.cantidadRegistrosTablaClientes % this.cantidadRenglonesTabla) == 0)
			this.cantidadPaginasTablaClientes = this.cantidadRegistrosTablaClientes / this.cantidadRenglonesTabla;
		else
			this.cantidadPaginasTablaClientes = (this.cantidadRegistrosTablaClientes / this.cantidadRenglonesTabla) + 1;
		
		//INICIALIZA PAGINACION
			this.paginacionTablaClientes.setPageFactory(this::createPaginaTablaClientes);
			this.paginacionTablaClientes.setMaxPageIndicatorCount(3);
			this.paginacionTablaClientes.setPageCount(cantidadPaginasTablaClientes);
    }//FIN METODO
	
	//PAGINACION DE LA TABLA CLIENTES
	private Node createPaginaTablaClientes(int indicePagina) {
		int indiceInicial = indicePagina * this.cantidadRenglonesTabla;
		int indiceFinal = Math.min(indiceInicial + this.cantidadRenglonesTabla, this.cantidadRegistrosTablaClientes);
		ObservableList<Cliente> lista = this.clienteDAO.toObservableList(listaClientes);
		tablaCliente.setItems(FXCollections.observableArrayList(lista.subList(indiceInicial, indiceFinal)));
		return tablaCliente;
	}//FIN METODO
	*/
}//FIN CLASE
