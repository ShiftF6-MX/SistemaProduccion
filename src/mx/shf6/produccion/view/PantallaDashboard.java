package mx.shf6.produccion.view;

import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
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
	private ArrayList<String> listaComboLotes;
	private ArrayList<String> listaStatusLote;
	private ArrayList<String> listaStatusSerie;
	private GraphicsContext graficaLineaTiempo;
	
	//VARIABLES

	//CONSTANTES
	
	//COMPONENTES INTERFAZ
	@FXML private ComboBox<String> comboLotes;
	@FXML private ComboBox<String> comboLotes2;
	@FXML private BarChart<String, Integer> graficaPorLotes;
	@FXML private StackedBarChart<String, Integer> graficaPorSeries;
	@FXML private CategoryAxis xAxisLote;
	@FXML private NumberAxis yAxisLote;
	@FXML private CategoryAxis xAxisSerie;
	@FXML private NumberAxis yAxisSerie;	
	@FXML private Canvas canvas;
	@FXML private Group root;
	@FXML private Label fechaActual;
	@FXML private Label fechaInicio;
	@FXML private Label fechaFinal;
	@FXML private Label diasTranscurridos;
	@FXML private Label diasFaltantes;
	
	//METODOS
	@FXML private void initialize() {
		this.listaLotes = new ArrayList<OrdenProduccion>();
		this.listaSeries = new ArrayList<DetalleOrdenProduccion>();
		this.listaComboLotes = new ArrayList<String>();
		this.listaStatusLote = new ArrayList<String>();
		this.listaStatusSerie = new ArrayList<String>();
		this.fechaActual.setText(new Date(System.currentTimeMillis()).toString());
		this.graficaLineaTiempo = this.canvas.getGraphicsContext2D();
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
					lineaDelTiempo();
				});//FIN SENTENCIA
				Thread.sleep(1000);
			}//FIN WHILE
		}catch(InterruptedException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
	}//FIN METODO
		
	private void inicializarCombo() {
		//COMBO LOTES 1
		this.listaComboLotes.clear();
		this.listaComboLotes.add("Todos");
		for (OrdenProduccion orden : OrdenProduccionDAO.readLoteProduccion(this.connection)) 
			this.listaComboLotes.add(orden.getLote());
		this.comboLotes.setItems(FXCollections.observableArrayList(this.listaComboLotes));
		new AutoCompleteComboBoxListener(this.comboLotes);
		
		//COMBO LOTES 2
		this.listaComboLotes.clear();
		for (OrdenProduccion orden : OrdenProduccionDAO.readLoteProduccion(this.connection)) 
			this.listaComboLotes.add(orden.getLote());
		this.comboLotes2.setItems(FXCollections.observableArrayList(this.listaComboLotes));
		new AutoCompleteComboBoxListener(this.comboLotes2);
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
	
	private void lineaDelTiempo() {
		Date fechaActual = new Date(System.currentTimeMillis());
		OrdenProduccion orden = new OrdenProduccion();
		int sysPKLote = OrdenProduccionDAO.sysPKOrdenProduccion(this.connection, this.comboLotes2.getValue());
		int diasFaltantes = 0;
		int diasTranscurridos = 0;
    	orden = OrdenProduccionDAO.fechasPorLote(this.connection, sysPKLote);
        
        //FECHA INICIAL Y FINAL
        if (this.comboLotes2.getSelectionModel().isEmpty()) {
        	this.fechaInicio.setText("");
            this.fechaFinal.setText("");
            this.diasFaltantes.setText("");
            this.diasTranscurridos.setText("");
        }else {        	
        	diasTranscurridos = (int) ((fechaActual.getTime() - orden.getFecha().getTime())/86400000);
    		diasFaltantes = (int) ((orden.getFechaEntrega().getTime() - fechaActual.getTime())/86400000);
            this.fechaInicio.setText(orden.getFecha().toString());
            this.fechaFinal.setText(orden.getFechaEntrega().toString());
            this.diasTranscurridos.setText(Integer.toString(diasTranscurridos));
            if (diasFaltantes < 0 )
            	this.diasFaltantes.setText("0");
            else
            	this.diasFaltantes.setText(Integer.toString(diasFaltantes));
        }//FIN IF-ELSE
        
        //LINEA DEL TIEMPO	
        if (diasFaltantes > 3)
        	this.graficaLineaTiempo.setFill(Color.GREEN);
        else if (diasFaltantes ==3)
        	this.graficaLineaTiempo.setFill(Color.YELLOW);
        else if (diasFaltantes <= 0)
        	this.graficaLineaTiempo.setFill(Color.RED);
        //FIN IF-ELSE
        this.graficaLineaTiempo.fillRect(100, 100, 500, 100);
        this.graficaLineaTiempo.setFill(Color.BLACK);
        this.graficaLineaTiempo.setLineWidth(5);
        this.graficaLineaTiempo.strokeLine(100, 88, 100, 212);
        this.graficaLineaTiempo.strokeLine(600, 88, 600, 212);
       
	}//FIN METODO
	//MANEJADORES 
}//FIN METODO
