package mx.shf6.produccion.view;

import java.sql.Connection;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.ControlOperacion;
import mx.shf6.produccion.model.DetalleOrdenProduccion;
import mx.shf6.produccion.model.OrdenProduccion;
import mx.shf6.produccion.model.dao.DetalleOrdenProduccionDAO;
import mx.shf6.produccion.model.dao.OrdenProduccionDAO;
import mx.shf6.produccion.utilities.AutoCompleteComboBoxListener;
import mx.shf6.produccion.utilities.Notificacion;

public class PantallaDashboard extends Thread{
	
	//PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private ArrayList<OrdenProduccion> listaLotes;
	private ArrayList<DetalleOrdenProduccion> listaSeries;
	private ArrayList<ControlOperacion> listaControlOperaciones;
	private ArrayList<String> listaComboLotes;
	private ArrayList<String> listaStatusLote;
	private ArrayList<String> listaStatusSerie;
	private ArrayList<String> listaDescripcionCentros;
	private ArrayList<String> listaDescripcionGrupos;
	
	//VARIABLES
	
	//CONSTANTES
	
	//COMPONENTES INTERFAZ
	@FXML private ComboBox<String> comboLotes;
	@FXML private BarChart<String, Integer> graficaPorLotes;
	@FXML private StackedBarChart<String, Integer> graficaPorSeries;
	@FXML private BarChart<String, Integer> graficaCentrosTrabajo;
	@FXML private BarChart<String, Integer> graficaGruposTrabajo;
	@FXML private CategoryAxis xAxisLote;
	@FXML private NumberAxis yAxisLote;
	@FXML private CategoryAxis xAxisSerie;
	@FXML private NumberAxis yAxisSerie;	
	@FXML private CategoryAxis xAxisCentro;
	@FXML private NumberAxis yAxisCentro;
	@FXML private CategoryAxis xAxisGrupo;
	@FXML private NumberAxis yAxisGrupo;
		
	//METODOS
	@FXML private void initialize() {
		this.listaLotes = new ArrayList<OrdenProduccion>();
		this.listaSeries = new ArrayList<DetalleOrdenProduccion>();
		this.listaControlOperaciones = new ArrayList<ControlOperacion>();
		this.listaComboLotes = new ArrayList<String>();
		this.listaStatusLote = new ArrayList<String>();
		this.listaStatusSerie = new ArrayList<String>();
		this.listaDescripcionCentros = new ArrayList<String>();
		this.listaDescripcionGrupos = new ArrayList<String>();
		this.inicializarComponentes();
	}//FIN METODO
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.connection = this.mainApp.getConnection();
	}//FIN METODO
	
	private void inicializarComponentes() {
		
		//GRAFICA POR LOTES
		this.listaStatusLote.add("Pendiente");
		this.listaStatusLote.add("En Proceso");
		this.listaStatusLote.add("Paro");
		this.xAxisLote.setCategories(FXCollections.observableArrayList(this.listaStatusLote));
		this.xAxisLote.setLabel("Lotes");
		this.yAxisLote.setLabel("Cantidad");
		this.yAxisLote.setTickUnit(1);
		
		//GRAFICA POR SERIES 
		this.listaStatusSerie.add("PE");
		this.listaStatusSerie.add("PR");
		this.listaStatusSerie.add("PA");
		this.listaStatusSerie.add("TE");
		this.xAxisSerie.setCategories(FXCollections.observableArrayList(this.listaStatusSerie));
		
	}//FIN METODO
	
	@Override
	public void run() {
		try {
			while(true) {
				Platform.runLater(() -> {
					inicializarCombo();
					graficaPorLotes();
					graficaPorSeries();
					graficaPorCentro();
					graficaPorGrupo();
				});//FIN SENTENCIA
				Thread.sleep(1000);
			}//FIN WHILE
		}catch(InterruptedException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
	}//FIN METODO
		
	private void inicializarCombo() {
		this.listaComboLotes.clear();
		this.listaComboLotes.add("Todos");
		for (OrdenProduccion orden : OrdenProduccionDAO.readLoteProduccion(this.connection)) 
			this.listaComboLotes.add(orden.getLote());
		this.comboLotes.setItems(FXCollections.observableArrayList(this.listaComboLotes));
		new AutoCompleteComboBoxListener(this.comboLotes);
	}//FIN METODO
	
	private void graficaPorLotes() {
		this.listaLotes.clear();
		this.listaLotes = OrdenProduccionDAO.readLoteProduccion(connection);
		int[] contadorStatus = new int[3]; 
		for (OrdenProduccion orden : this.listaLotes) {
			int status = orden.getStatus();
			contadorStatus[status]++;
		}//FIN FOR
		
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		this.graficaPorLotes.getData().clear();
		for (int i = 0; i < contadorStatus.length; i++) {
			series.getData().add(new XYChart.Data<>(this.listaStatusLote.get(i), contadorStatus[i]));
		}//FIN FOR	
		this.graficaPorLotes.getData().add(series);
	}//FIN METODO
	
	@SuppressWarnings("unchecked")
	private void graficaPorSeries() {
		int sysPKLote = 0;
		int cantidadTotal = 1;
		this.listaSeries.clear();
		if(this.comboLotes.getSelectionModel().isEmpty() || this.comboLotes.getValue().equals("Todos"))
			this.listaSeries = DetalleOrdenProduccionDAO.readDetalleLoteProduccion(this.connection, -1);
		else {
			sysPKLote = OrdenProduccionDAO.sysPKOrdenProduccion(this.connection, this.comboLotes.getValue());
			this.listaSeries = DetalleOrdenProduccionDAO.readDetalleLoteProduccion(this.connection, sysPKLote);
		}//FIN IF-ELSE
		
		if (this.listaSeries.size() != 0) {
			cantidadTotal = this.listaSeries.size();
		}//FIN METODO
		
		int[] contadorStatus = new int[4];
		for (DetalleOrdenProduccion detalle: this.listaSeries) {
			int series = detalle.getStatus();
			contadorStatus[series]++;
		}//FIN FOR
		
		XYChart.Series<String, Integer> serie1 = new XYChart.Series<>();
		XYChart.Series<String, Integer> serie2 = new XYChart.Series<>();
		this.graficaPorSeries.getData().clear();
		for (int i = 0; i < contadorStatus.length; i++) {
			serie1.getData().add(new XYChart.Data<>(this.listaStatusSerie.get(i), (contadorStatus[i] *100)/ cantidadTotal));
			serie2.getData().add(new XYChart.Data<>(this.listaStatusSerie.get(i), 100-((contadorStatus[i] *100)/ cantidadTotal)));
		}//FIN FOR
		this.graficaPorSeries.getData().addAll(serie1, serie2);
	}//FIN METODO
	
	private void graficaPorCentro() {
		
	}//FIN METODO
	
	private void graficaPorGrupo() {
		
	}//FIN METODO
	//MANEJADORES 
}//FIN METODO
