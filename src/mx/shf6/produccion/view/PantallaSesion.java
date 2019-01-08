package mx.shf6.produccion.view;

import javafx.fxml.FXML;
import mx.shf6.produccion.MainApp;

public class PantallaSesion {

	//PROPIEDADES
	private MainApp mainApp;
	
	//COMPONENTES INTERZAS USUARIO
	
	//INICIALIZA COMPONENTES CONTROLAN INTERFAZ USUARIO
	@FXML private void initialize() {
		
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL CONTROLA VISTAS
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}//FIN METODO
	
	@FXML private void cerrarButtonHandler() {
		this.mainApp.iniciarPantallaInicio();
	}//FIN METODO
	
}//FIN CLASE
