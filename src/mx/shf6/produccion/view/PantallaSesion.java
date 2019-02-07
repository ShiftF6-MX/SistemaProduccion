package mx.shf6.produccion.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Usuario;
import mx.shf6.produccion.model.dao.UsuarioDAO;
import mx.shf6.produccion.utilities.Notificacion;

public class PantallaSesion {

	//PROPIEDADES
	private MainApp mainApp;
	private Usuario usuario;
	private UsuarioDAO usuarioDAO;
	
	//COMPONENTES INTERZAS USUARIO
	@FXML private TextField campoTextoUsuario;
	@FXML private PasswordField campoTextoContrasena;
	
	//VARIABLES
	
		
	//INICIALIZA COMPONENTES CONTROLAN INTERFAZ USUARIO
	@FXML private void initialize() {
		this.usuarioDAO = new UsuarioDAO();
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL CONTROLA VISTAS
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}//FIN METODO
	
	//COMPRUEBA CREDENCIALES USUARIO
	private boolean autenticarUsuario() {
		int resultado = this.usuarioDAO.validarUsuario(this.mainApp.getConnection(), this.campoTextoUsuario.getText(), this.campoTextoContrasena.getText());
		switch (resultado) {
			case UsuarioDAO.NO_REGISTRADO:
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "Usuario no registrado", "Lo sentimos no puedes ingresar al sistema con este nombre de usuario.");
				return false;
			case UsuarioDAO.USUARIO_BLOQUEADO:
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "Usuario bloqueado", "Lo sentimos el usuario con el que intentas ingresar se encuentra bloqueado, por favor ponte en contacto con el administrador del sistema.");
				return false;
			case UsuarioDAO.CONRASENA_INCORRECTA:
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "Contraseña incorrecta", "Lo sentimos la contraseña con la que intentas ingresar no es la correcta, si la has olvidado, por favor ponte en contacto con el administrador del sistema.");
				return false;
			case UsuarioDAO.ACCESO_CORRECTO:
				return true;
			default:
				return false;
		}//FIN SWITCH
	}//FIN METODO
	
	//MANEJADORES COMPONENTES
	@FXML private void cerrarButtonHandler() {
		this.mainApp.iniciarPantallaInicio();
	}//FIN METODO
	
	@FXML private void ingresarButtonHandler() {
		//this.mainApp.iniciarPantallaSistema();
		if (this.autenticarUsuario()) {
			this.mainApp.iniciarPantallaSistema();
			this.mainApp.setSesionActiva(true);
		}
	}//FIN METODO
	
}//FIN CLASE
