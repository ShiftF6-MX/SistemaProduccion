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
import mx.shf6.produccion.model.Almacen;
import mx.shf6.produccion.model.ArchivoProyecto;
import mx.shf6.produccion.model.CentroTrabajo;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.Cotizacion;
import mx.shf6.produccion.model.DetalleCardex;
import mx.shf6.produccion.model.DetalleComponente;
import mx.shf6.produccion.model.DetalleProceso;
import mx.shf6.produccion.model.Empleado;
import mx.shf6.produccion.model.GrupoTrabajo;
import mx.shf6.produccion.model.GrupoUsuario;
import mx.shf6.produccion.model.Material;
import mx.shf6.produccion.model.Proceso;
import mx.shf6.produccion.model.Proyecto;
import mx.shf6.produccion.model.Puesto;
import mx.shf6.produccion.model.Rol;
import mx.shf6.produccion.model.TipoMateriaPrima;
import mx.shf6.produccion.model.TipoMiscelaneo;
import mx.shf6.produccion.model.Tratamiento;
import mx.shf6.produccion.model.Usuario;
import mx.shf6.produccion.utilities.ConnectionDB;
import mx.shf6.produccion.utilities.LeerArchivo;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.view.DialogoAcabado;
import mx.shf6.produccion.view.DialogoAgregarDetalleComponente;
import mx.shf6.produccion.view.DialogoAgregarDetalleProceso;
import mx.shf6.produccion.view.DialogoAgregarGrupoUsuario;
import mx.shf6.produccion.view.DialogoAgregarMovimientoComponente;
import mx.shf6.produccion.view.DialogoAgregarPermiso;
import mx.shf6.produccion.view.DialogoAlmacen;
import mx.shf6.produccion.view.DialogoArchivoProyecto;
import mx.shf6.produccion.view.DialogoArchivos;
import mx.shf6.produccion.view.DialogoCentroTrabajo;
import mx.shf6.produccion.view.DialogoClientes;
import mx.shf6.produccion.view.DialogoComponente;
import mx.shf6.produccion.view.DialogoCotizacion;
import mx.shf6.produccion.view.DialogoCotizacionCliente;
import mx.shf6.produccion.view.DialogoDetalleComponente;
import mx.shf6.produccion.view.DialogoDetalleCotizacion;
import mx.shf6.produccion.view.DialogoDetalleProceso;
import mx.shf6.produccion.view.DialogoEmpleado;
import mx.shf6.produccion.view.DialogoEsquemaSeguridad;
import mx.shf6.produccion.view.DialogoGrupoTrabajo;
import mx.shf6.produccion.view.DialogoGrupoUsuario;
import mx.shf6.produccion.view.DialogoMaterial;
import mx.shf6.produccion.view.DialogoMovimientoInventario;
import mx.shf6.produccion.view.DialogoPermiso;
import mx.shf6.produccion.view.DialogoProceso;
import mx.shf6.produccion.view.DialogoProyectos;
import mx.shf6.produccion.view.DialogoProyectosCliente;
import mx.shf6.produccion.view.DialogoPuesto;
import mx.shf6.produccion.view.DialogoTipoMateriaPrima;
import mx.shf6.produccion.view.DialogoTipoMiscelaneo;
import mx.shf6.produccion.view.DialogoTratamiento;
import mx.shf6.produccion.view.DialogoUsuario;
import mx.shf6.produccion.view.PantallaAcabado;
import mx.shf6.produccion.view.PantallaAlmacen;
import mx.shf6.produccion.view.PantallaCabecera;
import mx.shf6.produccion.view.PantallaCentroTrabajo;
import mx.shf6.produccion.view.PantallaClientes;
import mx.shf6.produccion.view.PantallaComponente;
import mx.shf6.produccion.view.PantallaCotizaciones;
import mx.shf6.produccion.view.PantallaDashboard;
import mx.shf6.produccion.view.PantallaDetalleCotizacion;
import mx.shf6.produccion.view.PantallaEmpleado;
import mx.shf6.produccion.view.PantallaExistencia;
import mx.shf6.produccion.view.PantallaGrupoTrabajo;
import mx.shf6.produccion.view.PantallaInicio;
import mx.shf6.produccion.view.PantallaMaterial;
import mx.shf6.produccion.view.PantallaMenu;
import mx.shf6.produccion.view.PantallaProceso;
import mx.shf6.produccion.view.PantallaPuesto;
import mx.shf6.produccion.view.PantallaSesion;
import mx.shf6.produccion.view.PantallaTipoMateriaPrima;
import mx.shf6.produccion.view.PantallaTratamiento;
import mx.shf6.produccion.view.PantallaUsuario;
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
	private Stage escenarioDialogosAlterno;
	private Stage escenarioDialogosAlternoSecundario;
	
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
	private AnchorPane pantallaPuesto;
	private AnchorPane pantallaGrupoTrabajo;	
	private AnchorPane pantallaCentroTrabajo;
	private AnchorPane pantallaEmpleado;
	private AnchorPane pantallaProceso;
	private AnchorPane pantallaExistencia;
	private AnchorPane pantallaAlmacen;
	private AnchorPane pantallaUsuario;
	private AnchorPane pantallaDashboard;
	
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
	private AnchorPane dialogoCotizacionCliente;
	private AnchorPane dialogoProyectos;
	private AnchorPane dialogoProyectosCliente;	
	private AnchorPane dialogoArchivos;
	private AnchorPane dialogoArchivoProyecto;
	private AnchorPane dialogoPuesto;
	private AnchorPane dialogoGrupoTrabajo;
	private AnchorPane dialogoCentroTrabajo;
	private AnchorPane dialogoEmpleado;
	private AnchorPane dialogoAlmacen;
	private AnchorPane dialogoMovimientoInventario;
	private AnchorPane dialogoAgregarMovimientoComponente;
	private AnchorPane dialogoProceso;
	private AnchorPane dialogoDetalleProceso;
	private AnchorPane dialogoAgregarDetalleProceso;
	private AnchorPane dialogoGrupoUsuario;
	private AnchorPane dialogoAgregarGrupoUsuario;
	private AnchorPane dialogoEsquemaSeguridad;
	private AnchorPane dialogoUsuario;
	private AnchorPane dialogoPermiso;
	private AnchorPane dialogoAgregarPermiso;
	
	//CONSTANTES
	public static final String RAIZ_SERVIDOR = "\\\\192.168.0.100\\SistemaProduccion\\Ficheros\\";
	
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
		this.configurarEscenarioAlternoSecundario();
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
		LeerArchivo.leerArchivo();
		this.conexionBD = new ConnectionDB(LeerArchivo.nameDB, LeerArchivo.hostDB, LeerArchivo.userDB, LeerArchivo.passwordDB);
		//this.conexionBD = new ConnectionDB("produccion_mfg","104.254.247.249", "ManufacturasG", "WaAYq3PN6qREb+!w");
		this.conexion = conexionBD.conectarMySQL();
		this.sesionActiva = false;
		this.conexionBD.start();
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
		this.escenarioDialogosAlterno.initOwner(this.escenarioDialogos);
	} //FIN METODO
		
	public Stage getEscenarioDialogosAlterno() {
		return this.escenarioDialogosAlterno;
	}//FIN METODO
	
	private void configurarEscenarioAlternoSecundario() {
		this.escenarioDialogosAlternoSecundario = new Stage();
		this.escenarioDialogosAlternoSecundario.setResizable(false);
		this.escenarioDialogosAlternoSecundario.setMaximized(false);
		this.escenarioDialogosAlternoSecundario.initModality(Modality.WINDOW_MODAL);
		this.escenarioDialogosAlternoSecundario.initStyle(StageStyle.TRANSPARENT);
		this.escenarioDialogosAlternoSecundario.initOwner(this.escenarioPrincipal);
	} //FIN METODO
	
	public Stage getEscenarioDialogosAlternoSecundario() {
		return this.escenarioDialogosAlternoSecundario;
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
	
	private Scene iniciarEscenarioDialogosAlternoSecundario(Parent parent) {
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
			this.escenarioPrincipal.setResizable(true);
			this.escenarioPrincipal.setMaximized(true);
			this.escenarioPrincipal.setAlwaysOnTop(false);
			
			Rectangle2D limitesPantalla = Screen.getPrimary().getVisualBounds();
			this.escenarioPrincipal.setX(limitesPantalla.getMinX());
			this.escenarioPrincipal.setY(limitesPantalla.getMinY());

			this.escenarioPrincipal.setMaxWidth(limitesPantalla.getWidth());
			this.escenarioPrincipal.setMinWidth(limitesPantalla.getWidth());

			this.escenarioPrincipal.setMaxHeight(limitesPantalla.getHeight());
			
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
	
	//INICIAR PANTALLA PUESTO
	public void iniciarPantallaPuesto() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaPuesto.fxml"));
			this.pantallaPuesto = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaPuesto);
			
			PantallaPuesto pantallaPuesto = fxmlLoader.getController();
			pantallaPuesto.setMainApp(this);
		} catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public void iniciarPantallaGrupoTrabajo() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaGrupoTrabajo.fxml"));
			this.pantallaGrupoTrabajo = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaGrupoTrabajo);
			
			PantallaGrupoTrabajo pantallaGrupoTrabajo = fxmlLoader.getController();
			pantallaGrupoTrabajo.setMainApp(this);
		} catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public void iniciarPantallaCentroTrabajo() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaCentroTrabajo.fxml"));
			this.pantallaCentroTrabajo = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaCentroTrabajo);
			
			PantallaCentroTrabajo pantallaCentroTrabajo = fxmlLoader.getController();
			pantallaCentroTrabajo.setMainApp(this);
		} catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public void iniciarPantallaEmpleado() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaEmpleado.fxml"));
			this.pantallaEmpleado = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaEmpleado);
			
			PantallaEmpleado pantallaEmpleado = fxmlLoader.getController();
			pantallaEmpleado.setMainApp(this);
		} catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public void iniciarPantallaProceso() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaProceso.fxml"));
			this.pantallaProceso = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaProceso);
			
			PantallaProceso pantallaProceso = fxmlLoader.getController();
			pantallaProceso.setMainApp(this);
		} catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public void iniciarPantallaExistencia() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaExistencia.fxml"));
			this.pantallaExistencia = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaExistencia);
			
			PantallaExistencia pantallaExistencia = fxmlLoader.getController();
			pantallaExistencia.setMainApp(this);
		} catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public void iniciarPantallaAlmacen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaAlmacen.fxml"));
			this.pantallaAlmacen = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaAlmacen);
			
			PantallaAlmacen pantallaAlmacen = fxmlLoader.getController();
			pantallaAlmacen.setMainApp(this);
		} catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public void iniciarPantallaUsuario() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaUsuario.fxml"));
			this.pantallaUsuario = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaUsuario);
			
			PantallaUsuario pantallaUsuario = fxmlLoader.getController();
			pantallaUsuario.setMainApp(this);
		} catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public void iniciarPantallaDashboard() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaDashboard.fxml"));
			this.pantallaDashboard = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(this.pantallaDashboard);
			
			PantallaDashboard pantallaDashboard = fxmlLoader.getController();
			pantallaDashboard.setMainApp(this);
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
	public void iniciarDialogoCotizacion(Cotizacion cotizacion, int opcion, Cliente cliente) {
 		try {
 			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoCotizacion.fxml"));
			this.dialogoCotizacion = (AnchorPane) fxmlLoader.load();
			Scene PantallaSecundariaCotizaciones = this.iniciarEscenarioDialogos(this.dialogoCotizacion);
			this.escenarioDialogos.setScene(PantallaSecundariaCotizaciones);
			DialogoCotizacion dialogoCotizacion = fxmlLoader.getController();
			dialogoCotizacion.setMainApp(this, cotizacion, opcion, cliente);
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
	
	public void iniciarDialogoDetalleCotizacion(Cotizacion cotizacion) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoDetalleCotizacion.fxml"));
			this.dialogoDetalleCotizacion = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoDetalleCotizacion = this.iniciarEscenarioDialogosAlterno(this.dialogoDetalleCotizacion);
			this.escenarioDialogosAlterno.setScene(escenaDialogoDetalleCotizacion);
			
			DialogoDetalleCotizacion dialogoDetalleCotizacion = fxmlLoader.getController();
			dialogoDetalleCotizacion.setMainApp(this, cotizacion);
			
			this.escenarioDialogosAlterno.showAndWait();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
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
	
	public void iniciarDialogoProyectosCliente(Proyecto proyecto, int opcion, Cliente cliente) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoProyectosCliente.fxml"));
			this.dialogoProyectosCliente = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoProyecto = this.iniciarEscenarioDialogos(this.dialogoProyectosCliente);
			this.escenarioDialogos.setScene(escenaDialogoProyecto);
			
			DialogoProyectosCliente dialogoProyecto = fxmlLoader.getController();
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
            
            Scene escenaDialogoArchivoProyecto = this.iniciarEscenarioDialogosAlterno(this.dialogoArchivoProyecto);
            this.escenarioDialogosAlterno.setScene(escenaDialogoArchivoProyecto);
            
            DialogoArchivoProyecto dialogoArchivoProyecto = fxmlLoader.getController();
            dialogoArchivoProyecto.setMainApp(this, archivoProyecto, opcion, proyecto);
            
            this.escenarioDialogosAlterno.showAndWait();
        } catch (IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN METODO
    }//FIN METODO
	
	public void iniciarDialogoCotizacionCliente(Cliente cliente) {
		try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoCotizacionCliente.fxml"));
            this.dialogoCotizacionCliente = (AnchorPane) fxmlLoader.load();
		            
            Scene escenaDialogoCotizacionCliente = this.iniciarEscenarioDialogosAlternoSecundario(this.dialogoCotizacionCliente);
            this.escenarioDialogosAlternoSecundario.setScene(escenaDialogoCotizacionCliente);
      
            DialogoCotizacionCliente dialogoCotizacionCliente = fxmlLoader.getController();
            dialogoCotizacionCliente.setMainApp(this, cliente);
            
            this.escenarioDialogosAlternoSecundario.showAndWait();

        } catch (IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN METODO		
	}//FIN METODO
	
	
	public void iniciarDialogoProyectos(Cliente cliente) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoProyectos.fxml"));
			this.dialogoProyectos = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoProyectos = this.iniciarEscenarioDialogosAlternoSecundario(this.dialogoProyectos);
            this.escenarioDialogosAlternoSecundario.setScene(escenaDialogoProyectos);
			
			DialogoProyectos pantallaProyecto = fxmlLoader.getController();
			pantallaProyecto.setMainApp(this,cliente);
			
			this.escenarioDialogosAlternoSecundario.showAndWait();
		} catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public void iniciarDialogoArchivos(Proyecto proyecto, Cliente cliente) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoArchivos.fxml"));
            this.dialogoArchivos = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoArchivos = this.iniciarEscenarioDialogos(this.dialogoArchivos);
            this.escenarioDialogos.setScene(escenaDialogoArchivos);
            
            DialogoArchivos pantallaArchivoProyecto = fxmlLoader.getController();
            pantallaArchivoProyecto.setMainApp(this,proyecto,cliente);
            
            this.escenarioDialogos.showAndWait();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY/CATCH
    }//FIN METODO
	
	public void iniciarDialogoPuesto(Puesto puesto, int opcion) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoPuesto.fxml"));
            this.dialogoPuesto = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoPuesto = this.iniciarEscenarioDialogos(this.dialogoPuesto);
            this.escenarioDialogos.setScene(escenaDialogoPuesto);
            
            DialogoPuesto dialogoPuesto = fxmlLoader.getController();
            dialogoPuesto.setMain(this, puesto, opcion);
            
            this.escenarioDialogos.showAndWait();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY/CATCH
    }//FIN METODO
	
	public void iniciarDialogoGrupoTrabajo(GrupoTrabajo grupoTrabajo, int opcion) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoGrupoTrabajo.fxml"));
            this.dialogoGrupoTrabajo = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoGrupoTrabajo = this.iniciarEscenarioDialogos(this.dialogoGrupoTrabajo);
            this.escenarioDialogos.setScene(escenaDialogoGrupoTrabajo);
            
            DialogoGrupoTrabajo dialogoGrupoTrabajo = fxmlLoader.getController();
            dialogoGrupoTrabajo.setMainApp(this, grupoTrabajo, opcion);
            
            this.escenarioDialogos.showAndWait();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY/CATCH
    }//FIN METODO
	
	public void iniciarDialogoCentroTrabajo(CentroTrabajo centroTrabajo, int opcion) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoCentroTrabajo.fxml"));
            this.dialogoCentroTrabajo = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoCentroTrabajo = this.iniciarEscenarioDialogos(this.dialogoCentroTrabajo);
            this.escenarioDialogos.setScene(escenaDialogoCentroTrabajo);
            
            DialogoCentroTrabajo dialogoCentroTrabajo = fxmlLoader.getController();
            dialogoCentroTrabajo.setMainApp(this, centroTrabajo, opcion);
            
            this.escenarioDialogos.showAndWait();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY/CATCH
    }//FIN METODO
	
	public void iniciarDialogoEmpleado(Empleado empleado, int opcion) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoEmpleado.fxml"));
            this.dialogoEmpleado = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoEmpleado = this.iniciarEscenarioDialogos(this.dialogoEmpleado);
            this.escenarioDialogos.setScene(escenaDialogoEmpleado);
            
            DialogoEmpleado dialogoEmpleado = fxmlLoader.getController();
            dialogoEmpleado.setMainApp(this, empleado, opcion);
            
            this.escenarioDialogos.showAndWait();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY/CATCH
    }//FIN METODO
	
	public void iniciarDialogoAlmacen(Almacen almacen, int opcion) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoAlmacen.fxml"));
            this.dialogoAlmacen = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoAlmacen = this.iniciarEscenarioDialogos(this.dialogoAlmacen);
            this.escenarioDialogos.setScene(escenaDialogoAlmacen);
            
            DialogoAlmacen dialogoAlmacen = fxmlLoader.getController();
            dialogoAlmacen.setMainApp(this, almacen, opcion);
            
            this.escenarioDialogos.showAndWait();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY/CATCH
    }//FIN METODO
	
	public void iniciarDialogoMovimientoInventario(int tipoMovimiento) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoMovimientoInventario.fxml"));
            this.dialogoMovimientoInventario = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoMovimientoInventario = this.iniciarEscenarioDialogos(this.dialogoMovimientoInventario);
            this.escenarioDialogos.setScene(escenaDialogoMovimientoInventario);
            
            DialogoMovimientoInventario dialogoMovimientoInventario = fxmlLoader.getController();
            dialogoMovimientoInventario.setMainApp(this, tipoMovimiento);
            
            this.escenarioDialogos.showAndWait();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY/CATCH
    }//FIN METODO
	
	public DetalleCardex iniciarDialogoAgregarMovimientoComponente(String almacenOrigen, int tipoMovimiento) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoAgregarMovimientoComponente.fxml"));
            this.dialogoAgregarMovimientoComponente = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoAgregarMovimientoComponente = this.iniciarEscenarioDialogos(this.dialogoAgregarMovimientoComponente);
            this.escenarioDialogosAlterno.setScene(escenaDialogoAgregarMovimientoComponente);
            
            DialogoAgregarMovimientoComponente dialogoAgregarMovimientoComponente = fxmlLoader.getController();
            dialogoAgregarMovimientoComponente.setMainApp(this, almacenOrigen, tipoMovimiento);
            
            this.escenarioDialogosAlterno.showAndWait();
            return dialogoAgregarMovimientoComponente.getDetalleCardex();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
            return null;
        }//FIN TRY/CATCH
    }//FIN METODO
	
	public void iniciarDialogoProceso(Proceso proceso, int opcion) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoProceso.fxml"));
            this.dialogoProceso = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoProceso = this.iniciarEscenarioDialogos(this.dialogoProceso);
            this.escenarioDialogos.setScene(escenaDialogoProceso);
            
            DialogoProceso dialogoProceso = fxmlLoader.getController();
            dialogoProceso.setMainApp(this, proceso, opcion);
            
            this.escenarioDialogos.showAndWait();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY/CATCH
    }//FIN METODO
	
	public void iniciarDialogoDetalleProceso(Proceso proceso) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoDetalleProceso.fxml"));
            this.dialogoDetalleProceso = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoDetalleProceso = this.iniciarEscenarioDialogos(this.dialogoDetalleProceso);
            this.escenarioDialogos.setScene(escenaDialogoDetalleProceso);
            
            DialogoDetalleProceso dialogoDetalleProceso = fxmlLoader.getController();
            dialogoDetalleProceso.setMainApp(this, proceso);
            
            this.escenarioDialogos.showAndWait();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY/CATCH
    }//FIN METODO
	
	public DetalleProceso iniciarDialogoAgregarDetalleProceso(DetalleProceso detalleProceso, int opcion, int procesoFK, Componente componente) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoAgregarDetalleProceso.fxml"));
            this.dialogoAgregarDetalleProceso = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoAgregarDetalleProceso = this.iniciarEscenarioDialogos(this.dialogoAgregarDetalleProceso);
            this.escenarioDialogosAlterno.setScene(escenaDialogoAgregarDetalleProceso);
            
            DialogoAgregarDetalleProceso dialogoAgregarDetalleProceso = fxmlLoader.getController();
            dialogoAgregarDetalleProceso.setMainApp(this, detalleProceso, opcion, procesoFK, componente);;
            
            this.escenarioDialogosAlterno.showAndWait();
            return dialogoAgregarDetalleProceso.getDetalleProceso();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
            return null;
        }//FIN TRY/CATCH
    }//FIN METODO
	
	public void iniciarDialogoGrupoUsuario() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoGrupoUsuario.fxml"));
            this.dialogoGrupoUsuario = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoGrupoUsuario = this.iniciarEscenarioDialogos(this.dialogoGrupoUsuario);
            this.escenarioDialogos.setScene(escenaDialogoGrupoUsuario);
            
            DialogoGrupoUsuario dialogoGrupoUsuario = fxmlLoader.getController();
            dialogoGrupoUsuario.setMainApp(this);
            
            this.escenarioDialogos.showAndWait();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY/CATCH
    }//FIN METODO
	
	public void iniciarDialogoAgregarGrupoUsuario(GrupoUsuario grupoUsuario, int opcion) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoAgregarGrupoUsuario.fxml"));
            this.dialogoAgregarGrupoUsuario = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoAgregarGrupoUsuario = this.iniciarEscenarioDialogos(this.dialogoAgregarGrupoUsuario);
            this.escenarioDialogosAlterno.setScene(escenaDialogoAgregarGrupoUsuario);
            
            DialogoAgregarGrupoUsuario dialogoAgregarGrupoUsuario = fxmlLoader.getController();
            dialogoAgregarGrupoUsuario.setMainApp(this, grupoUsuario, opcion);
            
            this.escenarioDialogosAlterno.showAndWait();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY/CATCH
    }//FIN METODO
	
	public void iniciarDialogoEsquemaSeguridad(GrupoUsuario grupoUsuario) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoEsquemaSeguridad.fxml"));
            this.dialogoEsquemaSeguridad = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoEsquemaSeguridad = this.iniciarEscenarioDialogos(this.dialogoEsquemaSeguridad);
            this.escenarioDialogosAlterno.setScene(escenaDialogoEsquemaSeguridad);
            
            DialogoEsquemaSeguridad dialogoEsquemaSeguridad = fxmlLoader.getController();
            dialogoEsquemaSeguridad.setMainApp(this, grupoUsuario);
            
            this.escenarioDialogosAlterno.showAndWait();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY/CATCH
    }//FIN METODO
	
	public void iniciarDialogoUsuario(Usuario usuario, int opcion) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoUsuario.fxml"));
            this.dialogoUsuario = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoUsuario = this.iniciarEscenarioDialogos(this.dialogoUsuario);
            this.escenarioDialogos.setScene(escenaDialogoUsuario);
            
            DialogoUsuario dialogoUsuario = fxmlLoader.getController();
            dialogoUsuario.setMainApp(this, usuario, opcion);
            
            this.escenarioDialogos.showAndWait();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY/CATCH
    }//FIN METODO
	
	public void iniciarDialogoAgregarPermiso(Rol rol, int opcion) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoAgregarPermiso.fxml"));
            this.dialogoAgregarPermiso = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoAgregarPermiso = this.iniciarEscenarioDialogos(this.dialogoAgregarPermiso);
            this.escenarioDialogosAlterno.setScene(escenaDialogoAgregarPermiso);
            
            DialogoAgregarPermiso dialogoAgregarPermiso = fxmlLoader.getController();
            dialogoAgregarPermiso.setMainApp(this, rol, opcion);
            
            this.escenarioDialogosAlterno.showAndWait();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY/CATCH
    }//FIN METODO
	
	public void iniciarDialogoPermiso() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoPermiso.fxml"));
            this.dialogoPermiso = (AnchorPane) fxmlLoader.load();
           
            Scene escenaDialogoPermiso = this.iniciarEscenarioDialogos(this.dialogoPermiso);
            this.escenarioDialogos.setScene(escenaDialogoPermiso);
            
            DialogoPermiso dialogoPermiso = fxmlLoader.getController();
            dialogoPermiso.setMainApp(this);
            
            this.escenarioDialogos.showAndWait();
        } catch(IOException | IllegalStateException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY/CATCH
    }//FIN METODO

	@Override
	public void stop() {
		//System.out.println("Cerrando aplicacion...");
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
