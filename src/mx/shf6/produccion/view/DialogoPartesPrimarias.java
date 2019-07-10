package mx.shf6.produccion.view;

import java.sql.Connection;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.DetalleComponente;
import mx.shf6.produccion.model.Proyecto;
import mx.shf6.produccion.model.TipoComponente;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.DetalleComponenteDAO;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoPartesPrimarias {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private Proyecto proyecto;
	private Componente componenteRaiz;
	private DetalleComponente detalleComponenteRaiz;
	private ArrayList<DetalleComponente> listaPartePrimaria;


	//VARIABLES
	Double cantidad = 0.0;
	int i = 0;
	int componentePrincipal = 0;

	//CONSTANTES


	//COMPONENTES INTERFAZ
	@FXML private TableView<DetalleComponente> tablaPartesPrimarias;
	@FXML private PTableColumn<DetalleComponente, String> columnaNumeroPartePrimaria;
	@FXML private PTableColumn<DetalleComponente, Double> columnaCantidad;
	@FXML private PTableColumn<DetalleComponente, String> columnaDescripcionPartePrimaria;
	@FXML private PTableColumn<DetalleComponente, String> columnaDescripcionMateriaPrima;
	@FXML private PTableColumn<DetalleComponente, String> columnaNumeroMateriaPrima;
	@FXML private Label campoTextoComponente;


	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		inicializarTabla();
	}// FIN METODO


	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Proyecto proyecto) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.proyecto = proyecto;
		this.listaPartePrimaria = new ArrayList<DetalleComponente>();
		this.componenteRaiz = new Componente();
		this.detalleComponenteRaiz = new DetalleComponente();
		this.componentePrincipal = proyecto.getComponenteFK();

		obtenerArbol(proyecto.getComponenteFK());
		actualizarTabla();
	}//FIN METODO

	private void inicializarTabla(){
		this.columnaNumeroPartePrimaria.setCellValueFactory(cellData -> cellData.getValue().numeroParteComponenteSuperiorProperty());
		this.columnaCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
		this.columnaDescripcionPartePrimaria.setCellValueFactory(cellData -> cellData.getValue().descripcionComponenteSuperiorProperty());
		this.columnaDescripcionMateriaPrima.setCellValueFactory(cellData -> cellData.getValue().descripcionComponenteInferiorProperty());
		this.columnaNumeroMateriaPrima.setCellValueFactory(cellData -> cellData.getValue().numeroParteComponenteInferiorProperty());
	}//FIN METODO

	private void actualizarTabla(){
		this.tablaPartesPrimarias.setItems(null);
		this.tablaPartesPrimarias.setItems(FXCollections.observableArrayList(this.listaPartePrimaria));
	}//FIN METODO

	private void obtenerArbol(int componenteFK){
		i++;
		componenteRaiz = ComponenteDAO.readComponente(conexion, componenteFK);
		if(i==1)
			this.campoTextoComponente.setText(componenteRaiz.getDescripcion());

		ArrayList<DetalleComponente> listaDetalleComponente = new ArrayList<DetalleComponente>();
		if(componenteRaiz.getTipoComponente().equals(TipoComponente.ENSAMBLE) || componenteRaiz.getTipoComponente().equals(TipoComponente.SUB_ENSAMBLE) || componenteRaiz.getTipoComponente().equals(TipoComponente.PARTE_PRIMARIA) || componenteRaiz.getTipoComponente().equals(TipoComponente.COMPRADO)){
			listaDetalleComponente = DetalleComponenteDAO.readDetalleComponenteSuperiorFK(conexion, componenteFK);

				for(DetalleComponente detalleComponente : listaDetalleComponente){
					if(componenteRaiz.getTipoComponente().equals(TipoComponente.PARTE_PRIMARIA)){
						detalleComponente.setCantidad(cantidad);
						listaPartePrimaria.add(detalleComponente);
					}else if (componenteRaiz.getTipoComponente().equals(TipoComponente.COMPRADO)){
						if(!detalleComponenteRaiz.getTipoComponenteSuperior().equals(TipoComponente.PARTE_PRIMARIA)){
							detalleComponenteRaiz.setDescripcionComponenteSuperior("");
							detalleComponenteRaiz.setNumeroParteComponenteSuperior(detalleComponenteRaiz.getNumeroParteComponenteInferior());
							detalleComponenteRaiz.setNumeroParteComponenteInferior("");
							listaPartePrimaria.add(detalleComponenteRaiz);
						}//FIN IF
					}//FIN IF ELSE
					cantidad = detalleComponente.getCantidad();
					detalleComponenteRaiz = new DetalleComponente();
					detalleComponenteRaiz.setComponenteSuperiorFK(detalleComponente.getComponenteSuperiorFK());
					detalleComponenteRaiz.setComponenteInferiorFK(detalleComponente.getComponenteInferiorFK());
					detalleComponenteRaiz.setCantidad(detalleComponente.getCantidad());
					detalleComponenteRaiz.setTipoComponenteSuperior(componenteRaiz.getTipoComponente());
					detalleComponenteRaiz.setDescripcionComponenteInferior(detalleComponente.getDescripcionComponenteInferior());
					detalleComponenteRaiz.setDescripcionComponenteSuperior(detalleComponente.getDescripcionComponenteSuperior());
					detalleComponenteRaiz.setNumeroParteComponenteInferior(detalleComponente.getNumeroParteComponenteInferior());
					detalleComponenteRaiz.setNumeroParteComponenteSuperior(detalleComponente.getNumeroParteComponenteSuperior());
					obtenerArbol(detalleComponente.getComponenteInferiorFK());
				}//FIN FOR
		}//FIN IF
	}//FIN METODO

	//MANEJADORES COMPONENTES
	@FXML private void manejadorBotonAceptar() {
	}//FIN METODO

	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
}//FIN CLASE
