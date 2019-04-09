package mx.shf6.produccion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
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
import mx.shf6.produccion.model.ArchivoProyecto;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.Cotizacion;
import mx.shf6.produccion.model.DetalleComponente;
import mx.shf6.produccion.model.DetalleCotizacion;
import mx.shf6.produccion.model.Material;
import mx.shf6.produccion.model.Proyecto;
import mx.shf6.produccion.model.TipoMateriaPrima;
import mx.shf6.produccion.model.TipoMiscelaneo;
import mx.shf6.produccion.model.Tratamiento;
import mx.shf6.produccion.model.Usuario;
import mx.shf6.produccion.utilities.ConnectionDB;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.view.DialogoAcabado;
import mx.shf6.produccion.view.DialogoAgregarDetalleComponente;
import mx.shf6.produccion.view.DialogoArchivoProyecto;
import mx.shf6.produccion.view.DialogoClientes;
import mx.shf6.produccion.view.DialogoComponente;
import mx.shf6.produccion.view.DialogoCotizacion;
import mx.shf6.produccion.view.DialogoDetalleComponente;
import mx.shf6.produccion.view.DialogoDetalleCotizacion;
import mx.shf6.produccion.view.DialogoMaterial;
import mx.shf6.produccion.view.DialogoProyecto;
import mx.shf6.produccion.view.DialogoTipoMateriaPrima;
import mx.shf6.produccion.view.DialogoTipoMiscelaneo;
import mx.shf6.produccion.view.DialogoTratamiento;
import mx.shf6.produccion.view.PantallaAcabado;
import mx.shf6.produccion.view.PantallaArchivoProyecto;
import mx.shf6.produccion.view.PantallaCabecera;
import mx.shf6.produccion.view.PantallaClientes;
import mx.shf6.produccion.view.PantallaComponente;
import mx.shf6.produccion.view.PantallaCotizaciones;
import mx.shf6.produccion.view.PantallaDetalleCotizacion;
import mx.shf6.produccion.view.PantallaInicio;
import mx.shf6.produccion.view.PantallaMaterial;
import mx.shf6.produccion.view.PantallaMenu;
import mx.shf6.produccion.view.PantallaSesion;
import mx.shf6.produccion.view.PantallaTipoMateriaPrima;
import mx.shf6.produccion.view.PantallaTratamiento;
import mx.shf6.produccion.view.PantallaTipoMiscelaneo;
import mx.shf6.produccion.view.PantallaProyectos;

public class MainApp extends Application {
	
	//PROPIEDADES
	private Connection conexion;
	private ConnectionDB conexionBD;
	private Usuario usuario;
	private boolean sesionActiva;
	
	//PANTALLAS DEL SISTEMA
	private Stage escenarioPrincipal;
	private Stage escenarioDialogos;
	private Stage escenarioDialogosAlterno;
	private BorderPane pantallaBase;
	private AnchorPane pantallaInicio;
	private AnchorPane pantallaSesion;
	private AnchorPane pantallaMenu;
	private AnchorPane pantallaCabecera;
	private AnchorPane pantallaEspera;
	private AnchorPane pantallaClientes;
	private AnchorPane pantallaCotizaciones;
	private AnchorPane pantallaDetalleCotizacion;
	private AnchorPane pantallaComponente;
	private AnchorPane pantallaTipoMateriaPrima;
	private AnchorPane pantallaTipoMiscelaneo;
	private AnchorPane pantallaMaterial;
	private AnchorPane pantallaAcabado;
	private AnchorPane pantallaTratamiento;
	private AnchorPane pantallaProyectos;
	private AnchorPane pantallaArchivoProyecto;
	
	//DIALOGOS DEL SISTEMA
	private AnchorPane dialogoClientes;
	private AnchorPane dialogoCotizacion;
	private AnchorPane dialogoDetalleCotizacion;
	private AnchorPane dialogoComponente;
	private AnchorPane dialogoDetalleComponente;
	private AnchorPane dialogoAgregarDetalleComponente;
	private AnchorPane dialogoTipoMateriaPrima;
	private AnchorPane dialogoTipoMiscelaneo;
	private AnchorPane dialogoMaterial;
	private AnchorPane dialogoAcabado;
	private AnchorPane dialogoTratamiento;
	private AnchorPane dialogoProyecto;
	private AnchorPane dialogoArchivoProyecto;
	
	//CONSTANTES
	public static final String RAIZ_SERVIDOR = "\\\\192.168.0.216\\Ingeniería y Planeación\\PruebasFicherosMFG\\";
	
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
		this.configurarEscenarioDialogos();
		//INICIAR ESCENARIO DIALOGOS SECUNDARIOS
		this.configurarEscenarioAlterno();
		//INICIA LA INTERFAZ DE USUARIO
		iniciarEscenarioPrincipal();
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
	
	public Stage getEscenarioPrincipal() {
		return this.escenarioPrincipal;
	}//FIN METODO
	
	private void configurarEscenarioDialogos() {
		this.escenarioDialogos = new Stage();
		this.escenarioDialogos.setResizable(false);
		this.escenarioDialogos.setMaximized(false);
		this.escenarioDialogos.initModality(Modality.WINDOW_MODAL);
		this.escenarioDialogos.initStyle(StageStyle.TRANSPARENT);
		this.escenarioDialogos.initOwner(this.escenarioPrincipal);
	} //FIN METODO
	
	public Stage getEscenarioDialogos() {
		return this.escenarioDialogos;
	}//FIN METODO
	
	private void configurarEscenarioAlterno() {
		this.escenarioDialogosAlterno = new Stage();
		this.escenarioDialogosAlterno.setResizable(false);
		this.escenarioDialogosAlterno.setMaximized(false);
		this.escenarioDialogosAlterno.initModality(Modality.WINDOW_MODAL);
		this.escenarioDialogosAlterno.initStyle(StageStyle.TRANSPARENT);
		this.escenarioDialogosAlterno.initOwner(this.escenarioPrincipal);
	} //FIN METODO
		
	public Stage getEscenarioDialogosAlterno() {
		return this.escenarioDialogosAlterno;
	}//FIN METODO
	
	public void iniciarEscenarioPrincipal() {
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
	
	private Scene iniciarEscenarioDialogos(Parent parent) {
		VBox marcoVentana = new VBox();
		marcoVentana.getChildren().add(parent);
		marcoVentana.setPadding(new Insets(10.0d));
		marcoVentana.setBackground(new Background(new BackgroundFill(Color.rgb(0,0,0,0), new CornerRadii(0), new Insets(0))));
		parent.setEffect(new DropShadow());
		((AnchorPane)parent).setBackground(new Background(new BackgroundFill(Color.rgb(0,0,0,0), new CornerRadii(0), new Insets(0))));
		Scene escena = new Scene(marcoVentana);
		escena.setFill(Color.TRANSPARENT);
		return escena;
	}//FIN METODO
	
	private Scene iniciarEscenarioDialogosAlterno(Parent parent) {
		VBox marcoVentana = new VBox();
		marcoVentana.getChildren().add(parent);
		marcoVentana.setPadding(new Insets(10.0d));
		marcoVentana.setBackground(new Background(new BackgroundFill(Color.rgb(0,0,0,0), new CornerRadii(0), new Insets(0))));
		parent.setEffect(new DropShadow());
		((AnchorPane)parent).setBackground(new Background(new BackgroundFill(Color.rgb(0,0,0,0), new CornerRadii(0), new Insets(0))));
		Scene escena = new Scene(marcoVentana);
		escena.setFill(Color.TRANSPARENT);
		return escena;
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
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaEspera.fxml"));
			this.pantallaEspera = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(null);
			this.pantallaBase.setCenter(this.pantallaEspera);

			//MODIFICA EL ESCENARIO PRINCIPAL
			this.escenarioPrincipal.setResizable(false);
			this.escenarioPrincipal.setMaximized(true);
			this.escenarioPrincipal.setAlwaysOnTop(false);			
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
	public void iniciarPantallaCotizaciones(Cliente cliente) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaCotizaciones.fxml"));
			this.pantallaCotizaciones = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaCotizaciones);
			PantallaCotizaciones pantallaCotizaciones = fxmlLoader.getController();
			pantallaCotizaciones.setMainApp(this, cliente);
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//INICIAR PANTALLA DETALLE COTIZACIONES
	public void iniciarPantallaDetalleCotizacion(Cotizacion cotizacion) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaDetalleCotizacion.fxml"));
			this.pantallaDetalleCotizacion = (AnchorPane) fxmlLoader.load();
			Scene escenaDetalleCotizacion = this.iniciarEscenarioDialogosAlterno(this.pantallaDetalleCotizacion);
			this.escenarioDialogos.setScene(escenaDetalleCotizacion);
			PantallaDetalleCotizacion pantallaDetalleCotizacion = fxmlLoader.getController();
			pantallaDetalleCotizacion.setMainApp(this, cotizacion);
			this.escenarioDialogos.showAndWait();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
		
	//INICIAR PANTALLA TIPO PRODUCTO
	public void iniciarPantallaComponente() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaComponente.fxml"));
			this.pantallaComponente = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaComponente);
			
			PantallaComponente pantallaComponente = fxmlLoader.getController();
			pantallaComponente.setMainApp(this);
		} catch(IOException | IllegalStateException ex) {
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
	
	//INICIAR PANTALLA ACABADO
	public void iniciarPantallaTratamiento() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaTratamiento.fxml"));
			this.pantallaTratamiento = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaTratamiento);
			
			PantallaTratamiento pantallaTratamiento = fxmlLoader.getController();
			pantallaTratamiento.setMainApp(this);
		} catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//INICIAR PANTALLA PROYECTO
	public void iniciarPantallaProyecto(Cliente cliente) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaProyectos.fxml"));
			this.pantallaProyectos = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaProyectos);
			
			PantallaProyectos pantallaProyecto = fxmlLoader.getController();
			pantallaProyecto.setMainApp(this,cliente);
		} catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//INICIAR PANTALLA ARCHIVO PROYECTO
	public void iniciarPantallaArchivoProyecto(Proyecto proyecto, Cliente cliente) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaArchivoProyecto.fxml"));
            this.pantallaArchivoProyecto = (AnchorPane) fxmlLoader.load();
            this.pantallaBase.setCenter(this.pantallaArchivoProyecto);
            
            PantallaArchivoProyecto pantallaArchivoProyecto = fxmlLoader.getController();
            pantallaArchivoProyecto.setMainApp(this,proyecto,cliente);
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY/CATCH
    }//FIN METODO
	
	//METODOS DIALOGOS
	public void iniciarDialogoClientes(Cliente cliente , int opcion) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoClientes.fxml"));
			this.dialogoClientes = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoClientes = this.iniciarEscenarioDialogos(this.dialogoClientes);
			this.escenarioDialogos.setScene(escenaDialogoClientes);
			
			DialogoClientes dialogoClientes = fxmlLoader.getController();
			dialogoClientes.setMainApp(this, cliente, opcion);
			
			this.escenarioDialogos.showAndWait();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//INICIAR PANTALLA DIALOGO COTIZACIONES
	public void iniciarDialogoCotizacion(Cotizacion cotizacion, int opcion) {
 		try {
 			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoCotizacion.fxml"));
			this.dialogoCotizacion = (AnchorPane) fxmlLoader.load();
			Scene PantallaSecundariaCotizaciones = this.iniciarEscenarioDialogos(this.dialogoCotizacion);
			this.escenarioDialogos.setScene(PantallaSecundariaCotizaciones);
			DialogoCotizacion dialogoCotizacion = fxmlLoader.getController();
			dialogoCotizacion.setMainApp(this, cotizacion, opcion);
			this.escenarioDialogos.showAndWait();;
 		} catch (IOException | IllegalStateException ex) {
 			Notificacion.dialogoException(ex);
 		}//FIN TRY/CATCH
 	}//FIN METODO
	
	public void iniciarDialogoComponente(Componente componente, int opcion) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoComponente.fxml"));
			this.dialogoComponente = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoComponente = this.iniciarEscenarioDialogos(this.dialogoComponente);
			this.escenarioDialogos.setScene(escenaDialogoComponente);
			
			DialogoComponente dialogoComponente = fxmlLoader.getController();
			dialogoComponente.setMainApp(this, componente, opcion);
			
			this.escenarioDialogos.showAndWait();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN METODO
	}//FIN METODO
	
	public void iniciarDialogoDetalleComponente(Componente componente) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoDetalleComponente.fxml"));
			this.dialogoDetalleComponente = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoDetalleComponente = this.iniciarEscenarioDialogos(this.dialogoDetalleComponente);
			this.escenarioDialogos.setScene(escenaDialogoDetalleComponente);
			
			DialogoDetalleComponente dialogoDetalleComponente = fxmlLoader.getController();
			dialogoDetalleComponente.setMainApp(this, componente);
			
			this.escenarioDialogos.showAndWait();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN METODO
	}//FIN METODO
	
	public DetalleComponente iniciarDialogoAgregarDetalleComponente(Componente componente) {
		DetalleComponente detalleComponente = new DetalleComponente();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoAgregarDetalleComponente.fxml"));
			this.dialogoAgregarDetalleComponente = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoAgregarDetalleComponente = this.iniciarEscenarioDialogosAlterno(this.dialogoAgregarDetalleComponente);
			this.escenarioDialogosAlterno.setScene(escenaDialogoAgregarDetalleComponente);
			
			DialogoAgregarDetalleComponente dialogoAgregarDetalleComponente = fxmlLoader.getController();
			dialogoAgregarDetalleComponente.setMainApp(this, componente);
			
			this.escenarioDialogosAlterno.showAndWait();
			return detalleComponente = dialogoAgregarDetalleComponente.getDetalleComponente();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return  detalleComponente;
	}//FIN METODO
	
	public DetalleCotizacion iniciarDialogoDetalleCotizacion(Cotizacion cotizacion) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoDetalleCotizacion.fxml"));
			this.dialogoDetalleCotizacion = (AnchorPane) fxmlLoader.load();
			Scene escenaDialogoDetalleCotizacion = this.iniciarEscenarioDialogosAlterno(this.dialogoDetalleCotizacion);
			this.escenarioDialogosAlterno.setScene(escenaDialogoDetalleCotizacion);
			DialogoDetalleCotizacion dialogoDetalleCotizacion = fxmlLoader.getController();
			dialogoDetalleCotizacion.setMainApp(this, cotizacion);
			this.escenarioDialogosAlterno.showAndWait();
			return dialogoDetalleCotizacion.getDetalleCotizacion();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
			return null;
		}//FIN METODO
	}//FIN METODO
	
	public void iniciarDialogoTipoMateriaPrima(TipoMateriaPrima tipoMateriaPrima, int opcion) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoTipoMateriaPrima.fxml"));
			this.dialogoTipoMateriaPrima = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoTipoMateriaPrima = this.iniciarEscenarioDialogos(this.dialogoTipoMateriaPrima);
			this.escenarioDialogos.setScene(escenaDialogoTipoMateriaPrima);
			
			DialogoTipoMateriaPrima dialogoTipoMateriaPrima = fxmlLoader.getController();
			dialogoTipoMateriaPrima.setMainApp(this, tipoMateriaPrima, opcion);
			
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
			
			Scene escenaDialogoTipoMiscelaneo = this.iniciarEscenarioDialogos(this.dialogoTipoMiscelaneo);
			this.escenarioDialogos.setScene(escenaDialogoTipoMiscelaneo);
			
			DialogoTipoMiscelaneo dialogoTipoMiscelaneo = fxmlLoader.getController();
			dialogoTipoMiscelaneo.setMainApp(this, tipoMiscelaneo, opcion);
			
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
			
			Scene escenaDialogoMaterial = this.iniciarEscenarioDialogos(this.dialogoMaterial);
			this.escenarioDialogos.setScene(escenaDialogoMaterial);
			
			DialogoMaterial dialogoMaterial = fxmlLoader.getController();
			dialogoMaterial.setMainApp(this, material, opcion);
			
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
			
			Scene escenaDialogoAcabado = this.iniciarEscenarioDialogos(this.dialogoAcabado);
			this.escenarioDialogos.setScene(escenaDialogoAcabado);
			
			DialogoAcabado dialogoAcabado = fxmlLoader.getController();
			dialogoAcabado.setMainApp(this, acabado, opcion);
			
			this.escenarioDialogos.showAndWait();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN METODO
	}//FIN METODO
	
	public void iniciarDialogoTratamiento(Tratamiento tratamiento, int opcion) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoTratamiento.fxml"));
			this.dialogoTratamiento = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoTratamiento = this.iniciarEscenarioDialogos(this.dialogoTratamiento);
			this.escenarioDialogos.setScene(escenaDialogoTratamiento);
			
			DialogoTratamiento dialogoTratamiento = fxmlLoader.getController();
			dialogoTratamiento.setMainApp(this, tratamiento, opcion);
			
			this.escenarioDialogos.showAndWait();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN METODO
	}//FIN METODO
	
	public void iniciarDialogoProyecto(Proyecto proyecto, int opcion, Cliente cliente) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoProyecto.fxml"));
			this.dialogoProyecto = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoProyecto = this.iniciarEscenarioDialogos(this.dialogoProyecto);
			this.escenarioDialogos.setScene(escenaDialogoProyecto);
			
			DialogoProyecto dialogoProyecto = fxmlLoader.getController();
			dialogoProyecto.setMainApp(this, proyecto, opcion, cliente);
			
			this.escenarioDialogos.showAndWait();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN METODO
	}//FIN METODO
	
	public void iniciarDialogoArchivoProyecto(ArchivoProyecto archivoProyecto, int opcion, Proyecto proyecto) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoArchivoProyecto.fxml"));
            this.dialogoArchivoProyecto = (AnchorPane) fxmlLoader.load();
            
            Scene escenaDialogoArchivoProyecto = this.iniciarEscenarioDialogos(this.dialogoArchivoProyecto);
            this.escenarioDialogos.setScene(escenaDialogoArchivoProyecto);
            
            DialogoArchivoProyecto dialogoArchivoProyecto = fxmlLoader.getController();
            dialogoArchivoProyecto.setMainApp(this, archivoProyecto, opcion, proyecto);
            
            this.escenarioDialogos.showAndWait();
        } catch (IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN METODO
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
		try {
			if (this.conexion.isClosed()) {
				this.configurarBaseDatos();
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "La conexión a la basa de datos se a perdido");
			}
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}
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
