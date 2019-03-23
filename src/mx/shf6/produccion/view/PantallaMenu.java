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
	
	@FXML private void manejadorMenuTipoMateriaPrima() {
		this.mainApp.iniciarPantallaTipoMateriaPrima();
	}//FIN METODO
	
}//FIN CLASE
