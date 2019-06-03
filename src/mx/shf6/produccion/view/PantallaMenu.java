package mx.shf6.produccion.view;

import javafx.fxml.FXML;
import mx.shf6.produccion.MainApp;

public class PantallaMenu {

	//PROPIEDADES
	private MainApp mainApp;
	
	//COMPONENTES INTERFAZ USUARIO
	
	//VARIABLES
	
	//INICIALIZA COMPONENTES CONTROLA VISTAS
	@FXML private void initialize() {
		
	}//FIN METODO
	
	//ACCESO A LA CLASE PRINICPAL
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES
	@FXML private void manejadorMenuClientes() {
		this.mainApp.iniciarPantallaClientes();
	}//FIN METODO
	
	@FXML private void manejadorMenuCotizaciones() {
		this.mainApp.iniciarPantallaCotizaciones(null); 
	}//FIN METODO
	
	@FXML private void manejadorMenuComponente() {
		this.mainApp.iniciarPantallaComponente();
	}//FIN METODO
	
	@FXML private void manejadorMenuTipoMateriaPrima() {
		this.mainApp.iniciarPantallaTipoMateriaPrima();
	}//FIN METODO
	
	@FXML private void manejadorTipoMiscelaneo() {
		this.mainApp.iniciarPantallaTipoMiscelaneo();
	}//FIN METODO
	
	@FXML private void manejadorMenuMaterial() {
		this.mainApp.iniciarPantallaMaterial();
	}//FIN METODO
	
	@FXML private void manejadorMenuAcabado() {
		this.mainApp.iniciarPantallaAcabado();
	}//FIN METODO
	
	@FXML private void manejadorMenuTratamiento() {
		this.mainApp.iniciarPantallaTratamiento();
	}//FIN METODO
		
	@FXML private void manejadorMenuPuesto() {
		this.mainApp.iniciarPantallaPuesto();
	}//FIN METODO		
	
	@FXML private void manejadorMenuGrupoTrabajo() {
		this.mainApp.iniciarPantallaGrupoTrabajo();
	}//FIN METODO
	
	@FXML private void manejadorMenuCentroTrabajo() {
		this.mainApp.iniciarPantallaCentroTrabajo();
	}//FIN METODO
	
	@FXML private void manejadorMenuEmpleado() {
		this.mainApp.iniciarPantallaEmpleado();
	}//FIN METODO
}//FIN CLASE
