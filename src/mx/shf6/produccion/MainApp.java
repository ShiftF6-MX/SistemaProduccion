package mx.shf6.produccion;

import java.io.IOException;
import java.sql.Connection;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mx.shf6.produccion.model.Acabado;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.Domicilio;
import mx.shf6.produccion.model.Material;
import mx.shf6.produccion.model.TipoMateriaPrima;
import mx.shf6.produccion.model.TipoMiscelaneo;
import mx.shf6.produccion.model.TipoProducto;
import mx.shf6.produccion.model.Usuario;
import mx.shf6.produccion.utilities.ConnectionDB;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.view.DialogoAcabado;
import mx.shf6.produccion.view.DialogoClientes;
import mx.shf6.produccion.view.DialogoMaterial;
import mx.shf6.produccion.view.DialogoTipoMateriaPrima;
import mx.shf6.produccion.view.DialogoTipoMiscelaneo;
import mx.shf6.produccion.view.DialogoTipoProducto;
import mx.shf6.produccion.view.PantallaAcabado;
import mx.shf6.produccion.view.PantallaCabecera;
import mx.shf6.produccion.view.PantallaClientes;
import mx.shf6.produccion.view.PantallaCotizaciones;
import mx.shf6.produccion.view.PantallaInicio;
import mx.shf6.produccion.view.PantallaMaterial;
import mx.shf6.produccion.view.PantallaMenu;
import mx.shf6.produccion.view.PantallaSesion;
import mx.shf6.produccion.view.PantallaTipoMateriaPrima;
import mx.shf6.produccion.view.PantallaTipoProducto;
import mx.shf6.produccion.view.PantallaTipoMiscelaneo;

public class MainApp extends Application {
	
	//PROPIEDADES
	private Connection conexion;
	private ConnectionDB conexionBD;
	private Usuario usuario;
	private boolean sesionActiva;
	
	//PANTALLAS DEL SISTEMA
	private Stage escenarioPrincipal;
	private Stage escenarioDialogos;
	private BorderPane pantallaBase;
	private AnchorPane pantallaInicio;
	private AnchorPane pantallaSesion;
	private AnchorPane pantallaMenu;
	private AnchorPane pantallaCabecera;
	private AnchorPane pantallaEspera;
	private AnchorPane pantallaClientes;
	private AnchorPane pantallaCotizaciones;
	private AnchorPane pantallaTipoMateriaPrima;
	private AnchorPane pantallaTipoProducto;
	private AnchorPane pantallaAcabado;
	private AnchorPane pantallaMaterial;
	private AnchorPane pantallaTipoMiscelaneo;
	
	//DIALOGOS DEL SISTEMA
	private AnchorPane dialogoClientes;
	private AnchorPane dialogoTipoMateriaPrima;
	private AnchorPane dialogoTipoProducto;
	private AnchorPane dialogoAcabado;
	private AnchorPane dialogoMaterial;
	private AnchorPane dialogoTipoMiscelaneo;
	
	//VARIABLES
	private double xOffset = 0.0;
	private double yOffset = 0.0;

	@Override
	public void start(Stage primaryStage) {
		//INSTALACIÓN FUENTES
		this.cargarFuentes();
		//INICIA CONCEXIÓN BASE DATOS
		this.configurarBaseDatos();
		//INICIA ESCENARIO PRINCIPAL
		this.configurarEscenarioPrincipal(primaryStage);
		//INICIA ESCENARIO DIALOGOS
		this.configurarEscenarioSecundario();
		//INICIA LA INTERFAZ DE USUARIO
		iniciarPantallaBase();
		iniciarPantallaInicio();
	}//FIN METODO
	
	private void cargarFuentes() {
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Light.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Regular.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Medium.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Bold.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Black.ttf").toExternalForm(), 10);
	}//FIN METODO
	
	private void configurarBaseDatos() {
		this.conexionBD = new ConnectionDB("produccion_mfg","104.254.247.249", "ManufacturasG", "WaAYq3PN6qREb+!w");
		this.conexion = conexionBD.conectarMySQL();
		this.sesionActiva = false;
	}//FIN METODO
	
	private void configurarEscenarioPrincipal(Stage primaryStage) {
		this.escenarioPrincipal = primaryStage;
		this.escenarioPrincipal.setMaximized(false);
		this.escenarioPrincipal.setResizable(false);
		this.escenarioPrincipal.initStyle(StageStyle.TRANSPARENT);
		this.escenarioPrincipal.setAlwaysOnTop(true);
	}//FIN METODO
	
	private void configurarEscenarioSecundario() {
		this.escenarioDialogos = new Stage();
		this.escenarioDialogos.setResizable(false);
		this.escenarioDialogos.setMaximized(false);
		this.escenarioDialogos.initModality(Modality.WINDOW_MODAL);
		this.escenarioDialogos.initStyle(StageStyle.TRANSPARENT);
		this.escenarioDialogos.initOwner(this.escenarioPrincipal);
	} //FIN METODO
	
	private Scene getEscenaSecundaria(Parent parent) {
		VBox marcoVentana = new VBox();
		marcoVentana.getChildren().add(parent);
		marcoVentana.setPadding(new Insets(10.0d));
		marcoVentana.setBackground(new Background(new BackgroundFill(Color.rgb(0,0,0,0), new CornerRadii(0), new Insets(0))));
		parent.setEffect(new DropShadow());
		((AnchorPane)parent).setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0))));
		Scene escena = new Scene(marcoVentana);
		return escena;
	}//FIN METODO
	
	public Stage getEscenarioDialogos() {
		return this.escenarioDialogos;
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
			
			PantallaClientes pantallaClientes = fxmlLoader.getController();
			pantallaClientes.setMainApp(this);
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//INICIAR PANTALLA COTIZACIONES
	public void iniciarPantallaCotizaciones() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaCotizaciones.fxml"));
			this.pantallaCotizaciones = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaCotizaciones);
			PantallaCotizaciones pantallaCotizaciones = fxmlLoader.getController();
			pantallaCotizaciones.setMainApp(this);
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//INICIAR PANTALLA TIPO MATERIA PRIMA
	public void iniciarPantallaTipoMateriaPrima() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaTipoMateriaPrima.fxml"));
			this.pantallaTipoMateriaPrima = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaTipoMateriaPrima);
			
			PantallaTipoMateriaPrima pantallaTipoMateriaPrima = fxmlLoader.getController();
			pantallaTipoMateriaPrima.setMainApp(this);
		} catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//INICIAR PANTALLA TIPO PRODUCTO
	public void iniciarPantallaTipoProducto() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaTipoProducto.fxml"));
			this.pantallaTipoProducto = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaTipoProducto);
			
			PantallaTipoProducto pantallaTipoProducto = fxmlLoader.getController();
			pantallaTipoProducto.setMainApp(this);
		} catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//INICIAR PANTALLA ACABADO
	public void iniciarPantallaAcabado() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaAcabado.fxml"));
			this.pantallaAcabado = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaAcabado);
			
			PantallaAcabado pantallaAcabado = fxmlLoader.getController();
			pantallaAcabado.setMainApp(this);
		} catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//INICIAR PANTALLA MATERIAL
	public void iniciarPantallaMaterial() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaMaterial.fxml"));
			this.pantallaMaterial = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaMaterial);
			
			PantallaMaterial pantallaMaterial = fxmlLoader.getController();
			pantallaMaterial.setMainApp(this);
		} catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODOS DIALOGOS
	public void iniciarDialogoClientes(Cliente cliente , int opcion, Domicilio domicilio) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoClientes.fxml"));
			this.dialogoClientes = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoClientes = this.getEscenaSecundaria(this.dialogoClientes);
			this.escenarioDialogos.setScene(escenaDialogoClientes);
			
			DialogoClientes dialogoClientes = fxmlLoader.getController();
			dialogoClientes.setMainApp(this, cliente, opcion, domicilio);
			
			this.escenarioDialogos.showAndWait();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public void iniciarDialogoTipoMateriaPrima(TipoMateriaPrima tipoMateriaPrima, int opcion) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoTipoMateriaPrima.fxml"));
			this.dialogoTipoMateriaPrima = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoTipoMateriaPrima = this.getEscenaSecundaria(this.dialogoTipoMateriaPrima);
			this.escenarioDialogos.setScene(escenaDialogoTipoMateriaPrima);
			
			DialogoTipoMateriaPrima dialogoTipoMateriaPrima = fxmlLoader.getController();
			dialogoTipoMateriaPrima.setMainApp(this, tipoMateriaPrima, opcion);
			
			this.escenarioDialogos.showAndWait();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN METODO
	}//FIN METODO
	
	public void iniciarDialogoTipoProducto(TipoProducto tipoProducto, int opcion) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoTipoProducto.fxml"));
			this.dialogoTipoProducto = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoTipoProducto = this.getEscenaSecundaria(this.dialogoTipoProducto);
			this.escenarioDialogos.setScene(escenaDialogoTipoProducto);
			
			DialogoTipoProducto dialogoTipoProducto = fxmlLoader.getController();
			dialogoTipoProducto.setMainApp(this, tipoProducto, opcion);
			
			this.escenarioDialogos.showAndWait();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN METODO
	}//FIN METODO
	
	public void iniciarDialogoAcabado(Acabado acabado, int opcion) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoAcabado.fxml"));
			this.dialogoAcabado = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoAcabado = this.getEscenaSecundaria(this.dialogoAcabado);
			this.escenarioDialogos.setScene(escenaDialogoAcabado);
			
			DialogoAcabado dialogoAcabado = fxmlLoader.getController();
			dialogoAcabado.setMainApp(this, acabado, opcion);
			
			this.escenarioDialogos.showAndWait();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN METODO
	}//FIN METODO
	
	public void iniciarDialogoMaterial(Material material, int opcion) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoMaterial.fxml"));
			this.dialogoMaterial = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoMaterial = this.getEscenaSecundaria(this.dialogoMaterial);
			this.escenarioDialogos.setScene(escenaDialogoMaterial);
			
			DialogoMaterial dialogoMaterial = fxmlLoader.getController();
			dialogoMaterial.setMainApp(this, material, opcion);
			
			this.escenarioDialogos.showAndWait();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN METODO
	}//FIN METODO
	
	public void iniciarDialogoTipoMiscelaneo(TipoMiscelaneo tipoMiscelaneo, int opcion) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoTipoMiscelaneo.fxml"));
			this.dialogoTipoMiscelaneo = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoTipoMiscelaneo = this.getEscenaSecundaria(this.dialogoTipoMiscelaneo);
			this.escenarioDialogos.setScene(escenaDialogoTipoMiscelaneo);
			
			DialogoTipoMiscelaneo dialogoTipoMiscelaneo = fxmlLoader.getController();
			dialogoTipoMiscelaneo.setMainApp(this, tipoMiscelaneo, opcion);
			
			this.escenarioDialogos.showAndWait();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN METODO
	}//FIN METODO
	
	//INICIAR PANTALLA TIPO MATERIA PRIMA
	public void iniciarPantallaTipoMiscelaneo() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaTipoMiscelaneo.fxml"));
			this.pantallaTipoMiscelaneo = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaTipoMiscelaneo);
			
			PantallaTipoMiscelaneo pantallaTipoMiscelaneo = fxmlLoader.getController();
			pantallaTipoMiscelaneo.setMainApp(this);
		} catch(IOException | IllegalStateException ex) {
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
