package mx.shf6.produccion;

import java.io.IOException;

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
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.view.PantallaInicio;
import mx.shf6.produccion.view.PantallaSesion;

public class MainApp extends Application {
	
	//PROPIEDADES
	
	//PANTALLAS DEL SISTEMA
	private Stage escenarioPrincipal;
	private BorderPane pantallaBase;
	private AnchorPane pantallaInicio;
	private AnchorPane pantallaSesion;
	
	//VARIABLES
	private double xOffset = 0.0;
	private double yOffset = 0.0;

	@Override
	public void start(Stage primaryStage) {
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Medium.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Regular.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Black.ttf").toExternalForm(), 10);
		
		//INICIA EL ESCENARIO PRINCIPAL
		this.escenarioPrincipal = primaryStage;
		this.escenarioPrincipal.setMaximized(false);
		this.escenarioPrincipal.setResizable(false);
		this.escenarioPrincipal.initStyle(StageStyle.TRANSPARENT);
		this.escenarioPrincipal.setAlwaysOnTop(true);
		
		//INICIA LA INTERFAZ DE USUARIO
		//iniciarPantallaBase();
		iniciarPantallaInicio();
	}//FIN METODO
	
	public void iniciarPantallaBase() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaBase.fxml"));
			this.pantallaBase = (BorderPane) fxmlLoader.load();
			this.pantallaBase.setPrefSize(100, 100);
			Scene escenaPrincipal = new Scene(this.pantallaBase);
			escenaPrincipal.setFill(Color.TRANSPARENT);
			this.escenarioPrincipal.setScene(escenaPrincipal);
			this.escenarioPrincipal.show();			
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public void iniciarPantallaInicio() {
		//MUESTRA PANTALLA EN ESQUINA SUPERIOR IZQUIERDA
		iniciarPantallaBase();
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
			
			this.pantallaBase.setCenter(this.pantallaInicio);
			PantallaInicio pantallaInicio = fxmlLoader.getController();
			pantallaInicio.setMainApp(this);
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public void iniciarPantallaSesion() {		
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
			
			this.pantallaBase.setCenter(this.pantallaSesion);
			PantallaSesion pantallaSesion = fxmlLoader.getController();
			pantallaSesion.setMainApp(this);
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		
		//MUESTRA PANTALLA EN ESQUINA SUPERIOR IZQUIERDA
		this.pantallaBase.setPrefSize(333, 517);
		this.escenarioPrincipal.sizeToScene();
		Rectangle2D limitesPantalla = Screen.getPrimary().getVisualBounds();
		this.escenarioPrincipal.setX((limitesPantalla.getWidth() - this.escenarioPrincipal.getWidth()) / 2);
		this.escenarioPrincipal.setY((limitesPantalla.getHeight() - this.escenarioPrincipal.getHeight()) /2);		
	}//FIN METODO

	@Override
	public void stop() {
		System.out.println("Cerrando aplicacion...");
		boolean opcion = Notificacion.dialogoPreguntar("Mensaje de Sistema", "Estas a punto de salir del sistema, ¿Realmente deseas cerrar la aplicación");
		if (opcion) {
			System.exit(0);
		}//FIN IF
	}//FIN METODO
	
	public static void main(String[] args) {
		launch(args);
	}//FIN METODO
	
}//FIN CLASE
