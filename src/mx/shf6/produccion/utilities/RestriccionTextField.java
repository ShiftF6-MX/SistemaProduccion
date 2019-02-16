package mx.shf6.produccion.utilities;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class RestriccionTextField {
	
	public void limitarNumeroCaracteres(TextField testField, int numeroCaracteres){
		testField.setOnKeyTyped(KeyEvent  -> {
			if (testField.getText().length()== numeroCaracteres) {
				KeyEvent.consume();
			}//FIN IF						
		});//FIN LISTENER
	}//FIN METODO
	
	public void soloNumeros(TextField textField) {		
		textField.setTextFormatter(new TextFormatter<>(change ->
        (change.getControlNewText().matches("([0-9]*)?")) ? change : null));
	}//FIN METODO
	
	public void soloLetras(TextField textField) {
		textField.setTextFormatter(new TextFormatter<>(change ->
        (change.getControlNewText().matches("([^0-9]*)?")) ? change : null));
	}//FIN METODO

}//FIN CLASE
