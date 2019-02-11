package mx.shf6.produccion.view;

import javafx.fxml.FXML;
import mx.shf6.produccion.MainApp;

public class PantallaMenu {

	//PROPIEDADES
	private MainApp mainApp;
	
	//COMPONENTES INTERFAZ USUARIO
	
	//VARIABLES
	
	//INICIALIZA COMPONENTES CONTROLA VISTAS
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES
	@FXML private void menuClientes() {
		this.mainApp.iniciarPantallaClientes();
	}//FIN CLASE
	
}//FIN CLASE
