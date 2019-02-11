package mx.shf6.produccion;

import java.io.IOException;
import java.sql.Connection;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mx.shf6.produccion.model.Usuario;
import mx.shf6.produccion.utilities.ConnectionDB;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.view.PantallaCabecera;
import mx.shf6.produccion.view.PantallaInicio;
import mx.shf6.produccion.view.PantallaMenu;
import mx.shf6.produccion.view.PantallaSesion;

public class MainApp extends Application {
	
	//PROPIEDADES
	private Connection conexion;
	private ConnectionDB conexionBD;
	private Usuario usuario;
	private boolean sesionActiva;
	
	//PANTALLAS DEL SISTEMA
	private Stage escenarioPrincipal;
	private BorderPane pantallaBase;
	private AnchorPane pantallaInicio;
	private AnchorPane pantallaSesion;
	private AnchorPane pantallaMenu;
	private AnchorPane pantallaCabecera;
	private AnchorPane pantallaEspera;
	private AnchorPane pantallaClientes;
	
	//VARIABLES
	private double xOffset = 0.0;
	private double yOffset = 0.0;

	@Override
	public void start(Stage primaryStage) {
		//INSTALACIÓN FUENTES
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Medium.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Regular.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Bold.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Black.ttf").toExternalForm(), 10);
		
		//INICIA CONCEXIÓN BASE DATOS
		this.conexionBD = new ConnectionDB("produccion_mfg","104.254.247.249", "ManufacturasG", "WaAYq3PN6qREb+!w");
		this.conexion = conexionBD.conectarMySQL();
		this.sesionActiva = false;
		
		//INICIA EL ESCENARIO PRINCIPAL
		this.escenarioPrincipal = primaryStage;
		this.escenarioPrincipal.setMaximized(false);
		this.escenarioPrincipal.setResizable(false);
		this.escenarioPrincipal.initStyle(StageStyle.TRANSPARENT);
		this.escenarioPrincipal.setAlwaysOnTop(true);
		
		//INICIA LA INTERFAZ DE USUARIO
		iniciarPantallaBase();
		iniciarPantallaInicio();
	}//FIN METODO
	
	public void iniciarPantallaBase() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaBase.fxml"));
			this.pantallaBase = (BorderPane) fxmlLoader.load();
			Scene escenaPrincipal = new Scene(this.pantallaBase);
			escenaPrincipal.setFill(Color.TRANSPARENT);
			this.escenarioPrincipal.setScene(escenaPrincipal);
			this.escenarioPrincipal.show();			
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	
	public void iniciarPantallaInicio() {
		//MODIFICA ESCENARIO PRINCIPAL
		this.escenarioPrincipal.setMaximized(false);
		this.escenarioPrincipal.setResizable(false);
		this.escenarioPrincipal.setAlwaysOnTop(true);
		
		//ESTABLECE TAMAÑO APLICACIÓN
		//Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		//escenarioPrincipal.setX(primaryScreenBounds.getMinX());
		//escenarioPrincipal.setY(primaryScreenBounds.getMinY());
		escenarioPrincipal.setMaxWidth(100);
		escenarioPrincipal.setMinWidth(100);
		escenarioPrincipal.setMaxHeight(100);
		escenarioPrincipal.setMinHeight(100);
		
		//MUESTRA PANTALLA EN ESQUINA SUPERIOR IZQUIERDA
		Rectangle2D limitesPantalla = Screen.getPrimary().getVisualBounds();
		this.escenarioPrincipal.setX(limitesPantalla.getWidth() - this.escenarioPrincipal.getWidth() - 25);
		this.escenarioPrincipal.setY(25);
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaInicio.fxml"));
			this.pantallaInicio = (AnchorPane) fxmlLoader.load();
			
			//SELECCIONAR PANTALLA PARA MOVER
			this.pantallaInicio.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					xOffset = escenarioPrincipal.getX() - event.getScreenX();
					yOffset = escenarioPrincipal.getY() - event.getScreenY();
				}//FIN METODO
			});//FIN MOUSEHANDLER
			
			//MOVER VENTAN ARRASTRANDO
			this.pantallaInicio.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					escenarioPrincipal.setX(event.getScreenX() + xOffset);
					escenarioPrincipal.setY(event.getScreenY() + yOffset);
				}//FIN METODO
			});//FIN MOUSEHANDLER

			this.pantallaBase.setCenter(null);
			this.pantallaBase.setLeft(null);
			this.pantallaBase.setTop(null);
			this.pantallaBase.setCenter(this.pantallaInicio);
			PantallaInicio pantallaInicio = fxmlLoader.getController();
			pantallaInicio.setMainApp(this);
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public void iniciarPantallaSesion() {
		if (this.getSesionActiva()) {
			this.iniciarPantallaSistema();
		} else {
			//MODIFICA ESCENARIO PRINCIPAL
			this.escenarioPrincipal.setMaximized(false);
			this.escenarioPrincipal.setResizable(false);
			this.escenarioPrincipal.setAlwaysOnTop(false);
			
			//ESTABLECE TAMAÑO APLICACIÓN
			//Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			//escenarioPrincipal.setX(primaryScreenBounds.getMinX());
			//escenarioPrincipal.setY(primaryScreenBounds.getMinY());
			escenarioPrincipal.setMaxWidth(333);
			escenarioPrincipal.setMinWidth(333);
			escenarioPrincipal.setMaxHeight(517);
			escenarioPrincipal.setMinHeight(517);
			
			try {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaSesion.fxml"));
				this.pantallaSesion = (AnchorPane) fxmlLoader.load();
				
				//SELECCIONAR PANTALLA PARA MOVER
				this.pantallaSesion.setOnMousePressed(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						xOffset = escenarioPrincipal.getX() - event.getScreenX();
						yOffset = escenarioPrincipal.getY() - event.getScreenY();
					}//FIN METODO
				});//FIN MOUSEHANDLER
				
				//MOVER VENTAN ARRASTRANDO
				this.pantallaSesion.setOnMouseDragged(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						escenarioPrincipal.setX(event.getScreenX() + xOffset);
						escenarioPrincipal.setY(event.getScreenY() + yOffset);
					}//FIN METODO
				});//FIN MOUSEHANDLER
				
				this.pantallaBase.setCenter(null);
				this.pantallaBase.setCenter(this.pantallaSesion);
				PantallaSesion pantallaSesion = fxmlLoader.getController();
				pantallaSesion.setMainApp(this);
			} catch (IOException | IllegalStateException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
							
			//MUESTRA PANTALLA CENTRO
			this.escenarioPrincipal.sizeToScene();
			Rectangle2D limitesPantalla = Screen.getPrimary().getVisualBounds();
			this.escenarioPrincipal.setX((limitesPantalla.getWidth() - this.escenarioPrincipal.getWidth()) / 2);
			this.escenarioPrincipal.setY((limitesPantalla.getHeight() - this.escenarioPrincipal.getHeight()) /2);
		}//FIN IF/ELSE
	}//FIN METODO
	
	public void iniciarPantallaSistema() {			
		this.iniciarPantallaEspera();
		this.iniciarPantallaMenu();
		this.iniciarPantallaCabecera();
	}//FIN METODO
	
	public void iniciarPantallaEspera() {
		this.pantallaBase.setCenter(null);
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaEspera.fxml"));
			this.pantallaEspera = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(null);
			this.pantallaBase.setCenter(this.pantallaEspera);

			//MODIFICA EL ESCENARIO PRINCIPAL
			this.escenarioPrincipal.setResizable(true);
			this.escenarioPrincipal.setMaximized(true);
			this.escenarioPrincipal.setAlwaysOnTop(false);
			
			//ESTABLECE TAMAÑO APLICACIÓN
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			escenarioPrincipal.setX(primaryScreenBounds.getMinX());
			escenarioPrincipal.setY(primaryScreenBounds.getMinY());
			escenarioPrincipal.setMaxWidth(primaryScreenBounds.getWidth());
			escenarioPrincipal.setMinWidth(primaryScreenBounds.getWidth());
			escenarioPrincipal.setMaxHeight(primaryScreenBounds.getHeight());
			escenarioPrincipal.setMinHeight(primaryScreenBounds.getHeight());
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH		
	}//FIN METODO
	
	public void iniciarPantallaMenu() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaMenu.fxml"));
			this.pantallaMenu = (AnchorPane) fxmlLoader.load();			
			this.pantallaBase.setLeft(this.pantallaMenu);
			
			PantallaMenu pantallaMenu =fxmlLoader.getController();
			pantallaMenu.setMainApp(this);
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRy/CATCH
	}//FIN METODO
	
	public void iniciarPantallaCabecera() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaCabecera.fxml"));
			this.pantallaCabecera = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setTop(this.pantallaCabecera);
			
			PantallaCabecera pantallaCabecera = fxmlLoader.getController();
			pantallaCabecera.setMainApp(this);
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public void iniciarPantallaClientes() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaClientes.fxml"));
			this.pantallaClientes = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaClientes);
			
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO

	@Override
	public void stop() {
		System.out.println("Cerrando aplicacion...");
		boolean opcion = Notificacion.dialogoPreguntar("Sistema de Producción", "Estas a punto de salir del sistema, ¿Realmente deseas cerrar la aplicación?");
		if (opcion) {
			this.conexionBD.terminarConexion(this.getConnection());
			System.exit(0);
		}//FIN IF
	}//FIN METODO
	
	//METODOS DE ACCESO CONEXION
	public Connection getConnection() {
		return this.conexion;
	}//FIN METODO
	
	//METODOS DE ACCESO USUARIO
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}//FIN METODO
	
	public Usuario getUsuario() {
		return this.usuario;
	}//FIN METODO
	
	public boolean getSesionActiva() {
		return this.sesionActiva;
	}//FIN METODO
	
	public void setSesionActiva(boolean sesionActiva) {
		this.sesionActiva = sesionActiva;
	}//FIN METODO
	
	public static void main(String[] args) {
		launch(args);
	}//FIN METODO
	
}//FIN CLASE
