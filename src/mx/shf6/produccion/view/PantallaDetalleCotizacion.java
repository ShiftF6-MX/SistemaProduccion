package mx.shf6.produccion.view;

import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.ControlOperacion;
import mx.shf6.produccion.model.DetalleProceso;
import mx.shf6.produccion.model.Cotizacion;
import mx.shf6.produccion.model.DetalleComponente;
import mx.shf6.produccion.model.DetalleCotizacion;
import mx.shf6.produccion.model.DetalleOrdenProduccion;
import mx.shf6.produccion.model.OrdenProduccion;
import mx.shf6.produccion.model.Proyecto;
import mx.shf6.produccion.model.TipoComponente;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.DetalleProcesoDAO;
import mx.shf6.produccion.model.dao.DetalleComponenteDAO;
import mx.shf6.produccion.model.dao.DetalleCotizacionDAO;
import mx.shf6.produccion.model.dao.DetalleOrdenProduccionDAO;
import mx.shf6.produccion.model.dao.OrdenProduccionDAO;
import mx.shf6.produccion.model.dao.ControlOperacionesDAO;
import mx.shf6.produccion.model.dao.ProyectoDAO;
import mx.shf6.produccion.model.dao.ProcesoDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.geometry.Pos;

public class PantallaDetalleCotizacion {

	public static final int CREAR = 1;
	public static final int EDITAR = 2;
	Double cantidad = 0.0;
	int i = 0;
	int tamañoArrayPartesPrimarias = 0;
	String nombreNumeroComponente;
	
	//PROPIEDADES
	private MainApp mainApp;
	private Cotizacion cotizacion;
	private ArrayList<DetalleCotizacion> listaDetalleCotizacion;
	private DetalleCotizacion detalleCotizacion;
	private ArrayList<DetalleComponente> listaDetalleComponente;
	private ArrayList<Integer> listaComponenteInferior;
	ArrayList<DetalleComponente> listaComponentesP;
	ArrayList<Componente> listaPartePrimarias;
	private Componente componenteRaiz;
	private DetalleComponente detalleComponenteRaiz;
	private DetalleComponente detalleComponenteSubEnsamble;
	private ArrayList<DetalleComponente> listaPartePrimaria;
	private ArrayList<DetalleComponente> listaSubEnsambles;
	private ArrayList<DetalleComponente> listaEnsambles;
	private HashSet<DetalleComponente> hs;
	
	
	//COMPONENTES INTERFAZ
	@FXML private TextField labelCotizacion;
	@FXML private TextField labelCliente;
	@FXML private TableView<DetalleCotizacion> tablaDetalleCotizacion;
	@FXML private PTableColumn<DetalleCotizacion, String> columnaNumeroDibujo;
	@FXML private PTableColumn<DetalleCotizacion, String> columnaDescripcion;
	@FXML private PTableColumn<DetalleCotizacion, Double> columnaCantidad;
	@FXML private PTableColumn<DetalleCotizacion, Double> columaPrecioUnitario;
	@FXML private PTableColumn<DetalleCotizacion, Date> columnaFechaEstimada;
	@FXML private PTableColumn<DetalleCotizacion, String> columnaObservaciones;
	@FXML private PTableColumn<DetalleCotizacion, String> columnaAcciones;
	@FXML private MenuItem menuItemAgregarProyecto;
	@FXML private MenuItem menuItemQuitarProyecto;
	@FXML private MenuItem menuItemVerProyecto;
	@FXML private MenuItem menuItemEditarProyecto;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.detalleCotizacion = new DetalleCotizacion();
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Cotizacion cotizacion) {
		this.mainApp = mainApp;
		this.cotizacion = cotizacion;
		this.listaDetalleCotizacion = DetalleCotizacionDAO.readCotizacionDetalle(this.mainApp.getConnection(), cotizacion.getSysPK());
		this.labelCotizacion.setText(this.cotizacion.getReferencia());
		this.labelCliente.setText(this.cotizacion.getCliente(this.mainApp.getConnection()).getNombre());
		this.inicializaTabla();
		this.actualizarTabla();
	}//FIN METODO	
	
	private void inicializaTabla() {
		this.columnaNumeroDibujo.setCellValueFactory(cellData -> cellData.getValue().getProyecto(this.mainApp.getConnection()).codigoProperty());
		this.columnaDescripcion.setCellValueFactory(cellData -> cellData.getValue().getProyecto(this.mainApp.getConnection()).descripcionProperty());
		this.columnaCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
		this.columaPrecioUnitario.setCellValueFactory(cellData -> cellData.getValue().costoProperty());
		this.columnaFechaEstimada.setCellValueFactory(cellData -> cellData.getValue().fechaEntregaProperty());
		this.columnaObservaciones.setCellValueFactory(cellData -> cellData.getValue().observacionesProperty());
		
		this.columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<DetalleCotizacion, String>, TableCell<DetalleCotizacion, String>> cellFactory = param -> {
			
			final TableCell<DetalleCotizacion, String> cell = new TableCell<DetalleCotizacion, String>() {
				final Button botonIniciarOrdenProduccion = new Button("I");
				final HBox acciones = new HBox(botonIniciarOrdenProduccion);
				
				//PARA MOSTRAR LOS DIALOGOS DE INSTITUCION
				@Override
				public void updateItem(String item, boolean empty) {
					botonIniciarOrdenProduccion.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/AprobarIcono.png"))));
					botonIniciarOrdenProduccion.setPrefSize(16.0, 16.0);
					botonIniciarOrdenProduccion.setPadding(Insets.EMPTY);
					botonIniciarOrdenProduccion.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonIniciarOrdenProduccion.setStyle("-fx-background-color: transparent");
					botonIniciarOrdenProduccion.setCursor(Cursor.HAND);
					botonIniciarOrdenProduccion.setTooltip(new Tooltip("Generar Orden de trabajo"));
					
					acciones.setSpacing(2);
					acciones.setPrefWidth(80.0);
					acciones.setAlignment(Pos.CENTER_LEFT);
					super.updateItem(item, empty);
					
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						botonIniciarOrdenProduccion.setOnAction(event -> {
							detalleCotizacion = getTableView().getItems().get(getIndex());
							
							OrdenProduccion ordenProduccion = new OrdenProduccion();
							ordenProduccion = OrdenProduccionDAO.searchOrdenProduccion(mainApp.getConnection(), detalleCotizacion.getSysPK());
							Proyecto proyecto = ProyectoDAO.readProyecto(mainApp.getConnection(), detalleCotizacion.getProyectoFK());
    						
							if (ordenProduccion.getSysPK() == 0) {
								if (Notificacion.dialogoPreguntar("Confirmación para generar una orden de trabajo", "¿Desea generar una orden de trabajo?")){
		            				OrdenProduccion orden = new OrdenProduccion();
		            				orden.setLote(generarLote());
		            				orden.setDetalleCotizacionFK(detalleCotizacion.getSysPK());
		            				
			            			if (OrdenProduccionDAO.createOrdenProduccion(mainApp.getConnection(), orden)) {
			            				for (int i = 0; i < detalleCotizacion.getCantidad(); i++) {
			            					int syspk = OrdenProduccionDAO.ultimoSysPK(mainApp.getConnection());
			            					DetalleOrdenProduccion detalleOrden = new DetalleOrdenProduccion();
			            					detalleOrden.setNumeroSerie(generarNumeroSerie());
			            					detalleOrden.setOrdenProduccionFK(syspk);
			            					if (DetalleOrdenProduccionDAO.createDetalleOrdenProduccion(mainApp.getConnection(), detalleOrden)) {
			         
			            						listaPartePrimaria = new ArrayList<DetalleComponente>();
			            						listaSubEnsambles = new ArrayList<DetalleComponente>();
			            						listaEnsambles = new ArrayList<DetalleComponente>();
			            						componenteRaiz = new Componente();
			            						hs = new HashSet<DetalleComponente>();
			            						
			            						obtenerPartesPrimarias(proyecto.getComponenteFK());
			            						hs.addAll(listaSubEnsambles);
			            						listaSubEnsambles.clear();
			            						listaSubEnsambles.addAll(hs);
			            						hs.clear();
			            						hs.addAll(listaEnsambles);
			            						listaSubEnsambles.addAll(hs);
			            						listaPartePrimaria.addAll(listaSubEnsambles);
			            						
			            						for (DetalleComponente comp : listaPartePrimaria) {
			            							
			            							ControlOperacion controlOperacion = new ControlOperacion();
			            							DetalleProceso detalleProceso = new DetalleProceso();
			            							Componente cont = ComponenteDAO.readComponenteNumeroParte(mainApp.getConnection(), comp.getNumeroParteComponenteSuperior());
			            							int proceso = ProcesoDAO.readProcesoComponenteFK(mainApp.getConnection(), cont.getSysPK());
			            							detalleProceso = DetalleProcesoDAO.primeraOperacion(mainApp.getConnection(), proceso);
			            							DetalleOrdenProduccion detalleOrdenProduccion = DetalleOrdenProduccionDAO.searchOrdenProduccion(mainApp.getConnection(), syspk);
			            							DetalleComponente detalleCom = DetalleComponenteDAO.readDetalleComponenteInferiorFKObject(mainApp.getConnection(), comp.getComponenteSuperiorFK());
			            							
			            					        java.sql.Timestamp hor = new java.sql.Timestamp(System.currentTimeMillis());
			            					        
			            					        int cant = detalleCom.getCantidad().intValue();
			            					       
			            					        if (!comp.getTipoComponenteSuperior().equals("Comprado") && !comp.getTipoComponenteSuperior().equals("Materia prima")) {
			            					        	if (cant == 0)
				            					        	controlOperacion.setCantidad(1);
				            					        else
				            					        	controlOperacion.setCantidad(cant);
					            						controlOperacion.setHoraFechaInicio(hor);
					            						controlOperacion.setHoraFechaFinal(null);
					            						controlOperacion.setFechaEstimada(detalleCotizacion.getFechaEntrega());
					            						if (detalleProceso.getCentroTrabajoFK() != 0)
					            							controlOperacion.setCentroTrabajoFK(detalleProceso.getCentroTrabajoFK());
					            						else 
					            							controlOperacion.setCentroTrabajoFK(0);
					            						controlOperacion.setCodigoParo(1);
					            						controlOperacion.setComponenteFK(cont.getSysPK());
					            						controlOperacion.setDetalleProcesoFK(detalleProceso.getSysPK());
					            						controlOperacion.setDetalleOrdenProduccionFK(detalleOrdenProduccion.getSysPK());
					            						
					            						if (ControlOperacionesDAO.createControlOperaciones(mainApp.getConnection(), controlOperacion))
					            							Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "Se genero exitosamente la orden de producción");	
			            					        }//FIN IF
			            						}//FIN FOR	            							
			            					}//FIN IF
			            				}//FIN FOR		            				
			            			}//FIN IF
								}//FIN IF
							} else {
								Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "En este registro ya se generó su orden de producción");		
							}//FIN IF ELSE
						});//FIN LISTENER
						setGraphic(acciones);
						setText(null);
					}//FIN IF ELSE
				}//FIN METODO
			};//FIN METODO
			return cell;
		};//FIN METODO
		columnaAcciones.setCellFactory(cellFactory);
	}//FIN METODO
	
	public String generarLote() {
		int syspk = (OrdenProduccionDAO.ultimoSysPK(this.mainApp.getConnection()) + 1);
		Month mes = LocalDate.now().getMonth();
        String nombre = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        char[] l = nombre.toUpperCase().toCharArray();
        String m = String.valueOf(l[0])+String.valueOf(l[1])+String.valueOf(l[2]);
        String fechaSys = String.valueOf(LocalDate.now().getDayOfMonth()) + m + String.valueOf(LocalDate.now().getYear() + String.valueOf(syspk));
        
        return fechaSys;
	}//FIN METODO
		
	public void obtenerPartesPrimarias(int componenteFK){
		i++;
		componenteRaiz = ComponenteDAO.readComponente(this.mainApp.getConnection(), componenteFK);
		if(i==1){
			this.nombreNumeroComponente = componenteRaiz.getNumeroParte();
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
			listaDetalleComponente = DetalleComponenteDAO.readDetalleComponenteSuperiorFK(this.mainApp.getConnection(), componenteFK);
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
	
	public String generarNumeroSerie() {
		String result = new SecureRandom().ints(0,36)
	            .mapToObj(i -> Integer.toString(i, 36))
	            .map(String::toUpperCase).distinct().limit(8).collect(Collectors.joining());
		
		return result;
	}//FIN METODO
	
	public boolean revisarExistencia(int componenteSuperior) {
		this.listaDetalleComponente = DetalleComponenteDAO.readDetalleComponenteSuperiorFK(this.mainApp.getConnection(), componenteSuperior);
		if (listaDetalleComponente.size() <= 0)
			return false;
		else
			return true;
	}//FIN METODO
	
	public ArrayList<Integer> obtenerComponenteInferiror(int componenteSuperior) {
		this.listaDetalleComponente = DetalleComponenteDAO.readDetalleComponenteSuperiorFK(this.mainApp.getConnection(), componenteSuperior);
		
		for(int i = 0; i < listaDetalleComponente.size(); i++) {
			listaComponenteInferior.add(listaDetalleComponente.get(i).getComponenteInferiorFK());
		}//FIN FOR
		return listaComponenteInferior;
	}//FIN METODO
		
	private void actualizarTabla() {
		this.tablaDetalleCotizacion.setItems(null);
		this.listaDetalleCotizacion.clear();
		this.listaDetalleCotizacion = DetalleCotizacionDAO.readCotizacionDetalle(this.mainApp.getConnection(), this.cotizacion.getSysPK());
		this.tablaDetalleCotizacion.setItems(DetalleCotizacionDAO.toObservableList(this.listaDetalleCotizacion));
	}//FIN METODO
	
	@FXML private void btnCancelar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
	
	@FXML private void manejadorAgregar() {
		this.mainApp.iniciarDialogoDetalleCotizacion(this.cotizacion, detalleCotizacion, DialogoDetalleCotizacion.CREAR);
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorEditar() {
		if (this.tablaDetalleCotizacion.getSelectionModel().getSelectedItem() != null) {
			detalleCotizacion = this.tablaDetalleCotizacion.getSelectionModel().getSelectedItem();
			this.mainApp.iniciarDialogoDetalleCotizacion(this.cotizacion, this.detalleCotizacion, DialogoDetalleCotizacion.EDITAR);
		}
		this.actualizarTabla();
	}
	
	@FXML private void manejadorQuitar() {
		DetalleCotizacion detalle = this.tablaDetalleCotizacion.getSelectionModel().getSelectedItem();
		OrdenProduccion orden = OrdenProduccionDAO.searchOrdenProduccion(this.mainApp.getConnection(), detalle.getSysPK());
		if(detalle.getSysPK() != 0) {
			if (orden.getSysPK() == 0) {
				if (Notificacion.dialogoPreguntar("Confirmación para eliminar", "¿Desea eliminar el proyecto de la cotización?"))
					DetalleCotizacionDAO.deleteDetalleCotizacion(mainApp.getConnection(), this.tablaDetalleCotizacion.getSelectionModel().getSelectedItem());
			} else {
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se puede eliminar porque ya se genero la orden de producción.");
			}//FIN IF
		}else
			Notificacion.dialogoAlerta(AlertType.ERROR, "Mensaje Sistema", "Seleccione el elemento que desea borrar.");
		actualizarTabla();
	}//FIN METODO
		
}//FIN CLASE
