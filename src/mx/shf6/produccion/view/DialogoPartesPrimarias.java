package mx.shf6.produccion.view;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;

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
import mx.shf6.produccion.utilities.GenerarDocumento;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoPartesPrimarias {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private Proyecto proyecto;
	private Componente componenteRaiz;
	private DetalleComponente detalleComponenteRaiz;
	private DetalleComponente detalleComponenteSubEnsamble;
	private ArrayList<DetalleComponente> listaPartePrimaria;
	private ArrayList<DetalleComponente> listaSubEnsambles;
	private ArrayList<DetalleComponente> listaEnsambles;
	private HashSet<DetalleComponente> hs;

	//VARIABLES
	Double cantidad = 0.0;
	int i = 0;
	int tamañoArrayPartesPrimarias = 0;
	String nombreNumeroComponente;

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
		this.listaSubEnsambles = new ArrayList<DetalleComponente>();
		this.listaEnsambles = new ArrayList<DetalleComponente>();
		this.componenteRaiz = new Componente();
		this.hs = new HashSet<DetalleComponente>();

		obtenerListaMateriales();
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

	private void obtenerPartesPrimarias(int componenteFK){
		i++;
		componenteRaiz = ComponenteDAO.readComponente(conexion, componenteFK);
		if(i==1){
			this.nombreNumeroComponente = componenteRaiz.getNumeroParte();
			this.campoTextoComponente.setText(this.nombreNumeroComponente);
		}

		if (componenteRaiz.getTipoComponente().equals(TipoComponente.SUB_ENSAMBLE)){
			detalleComponenteSubEnsamble = new DetalleComponente();
			detalleComponenteSubEnsamble.setNumeroParteComponenteSuperior(componenteRaiz.getNumeroParte());
			detalleComponenteSubEnsamble.setCantidad(detalleComponenteRaiz.getCantidad());
			detalleComponenteSubEnsamble.setDescripcionComponenteSuperior(componenteRaiz.getDescripcion());
			detalleComponenteSubEnsamble.setDescripcionComponenteInferior(componenteRaiz.getMaterialDescripcion());
			detalleComponenteSubEnsamble.setTipoComponenteSuperior(componenteRaiz.getTipoComponenteChar());
			listaSubEnsambles.add(detalleComponenteSubEnsamble);
		}//FIN IF

		if (componenteRaiz.getTipoComponente().equals(TipoComponente.ENSAMBLE)){
			detalleComponenteSubEnsamble = new DetalleComponente();
			detalleComponenteSubEnsamble.setNumeroParteComponenteSuperior(componenteRaiz.getNumeroParte());
			detalleComponenteSubEnsamble.setDescripcionComponenteSuperior(componenteRaiz.getDescripcion());
			detalleComponenteSubEnsamble.setTipoComponenteSuperior(componenteRaiz.getTipoComponenteChar());
			listaEnsambles.add(detalleComponenteSubEnsamble);
		}//FIN IF

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
							detalleComponenteRaiz.setTipoComponenteSuperior("C");
							listaPartePrimaria.add(detalleComponenteRaiz);
						}//FIN IF
					}//FIN IF ELSE
					cantidad = detalleComponente.getCantidad();
					detalleComponenteRaiz = new DetalleComponente();
					detalleComponenteRaiz.setComponenteSuperiorFK(detalleComponente.getComponenteSuperiorFK());
					detalleComponenteRaiz.setComponenteInferiorFK(detalleComponente.getComponenteInferiorFK());
					detalleComponenteRaiz.setCantidad(detalleComponente.getCantidad());
					detalleComponenteRaiz.setTipoComponenteSuperior(componenteRaiz.getTipoComponenteChar());
					detalleComponenteRaiz.setDescripcionComponenteInferior(detalleComponente.getDescripcionComponenteInferior());
					detalleComponenteRaiz.setDescripcionComponenteSuperior(detalleComponente.getDescripcionComponenteSuperior());
					detalleComponenteRaiz.setNumeroParteComponenteInferior(detalleComponente.getNumeroParteComponenteInferior());
					detalleComponenteRaiz.setNumeroParteComponenteSuperior(detalleComponente.getNumeroParteComponenteSuperior());
					obtenerPartesPrimarias(detalleComponente.getComponenteInferiorFK());
				}//FIN FOR
		}//FIN IF
	}//FIN METODO

	private void obtenerListaMateriales(){
		obtenerPartesPrimarias(proyecto.getComponenteFK());
		hs.addAll(listaSubEnsambles);
		listaSubEnsambles.clear();
		listaSubEnsambles.addAll(hs);
		hs.clear();
		hs.addAll(listaEnsambles);
		listaSubEnsambles.addAll(hs);
		listaPartePrimaria.addAll(listaSubEnsambles);
	}//FIN METODO

	//MANEJADORES COMPONENTES
	@FXML private void manejadorBotonAceptar() {
	}//FIN METODO

	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO

	@FXML private void manejadorBotonImprimir() {
		GenerarDocumento.generaListaMateriales(conexion, listaPartePrimaria, this.nombreNumeroComponente);
	}//FIN METODO

}//FIN CLASE
