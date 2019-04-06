package mx.shf6.produccion.utilities;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class RestriccionTextField {
	
	public static void limitarNumeroCaracteres(TextField textField, int numeroCaracteres){
		textField.setOnKeyTyped(KeyEvent  -> {
			if (textField.getText().length()== numeroCaracteres) {
				KeyEvent.consume();
			}//FIN IF						
		});//FIN LISTENER
	}//FIN METODO
	
	public static void soloNumeros(TextField textField) {		
		textField.setTextFormatter(new TextFormatter<>(change ->
        (change.getControlNewText().matches("([0-9]*)?")) ? change : null));
	}//FIN METODO
	
	public static void soloLetras(TextField textField) {
		textField.setTextFormatter(new TextFormatter<>(change ->
        (change.getControlNewText().matches("([^0-9]*)?")) ? change : null));
	}//FIN METODO
	
	public static void limitarPuntoDecimal(TextField textField ) {
		final String pattern = "(\\d)+(\\.?\\d*)?$";
	    
	    
		textField.setOnKeyTyped(event -> {
	        String newText = textField.getText() + event.getCharacter();
	        if (!newText.matches(pattern)) {
	            event.consume();
	        }//FIN IF
	    });//FIN METODO
	}//FIN METODO

}//FIN CLASE
