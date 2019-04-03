package mx.shf6.produccion.model;

public class TipoComponente {

	public static final String COMPRADO = "Comprado";
	public static final String MATERIA_PRIMA = "Materia Prima";
	public static final String ENSAMBLE = "Ensamble";
	public static final String PARTE_PRIMARIA = "Parte Primaria";
	
	public static String toCharacter(String stringComponente) {
		switch (stringComponente) {
		case "Comprado":
			return "C";
		case "Materia Prima":
			return "M";
		case "Ensamble":
			return "E";
		case "Parte Primaria":
			return "P";
		default:
			return "";	
		}//FIN SWITCH
	}//FIN METODO
	
	public static String toString(String charComponente) {
		switch (charComponente) {
		case "C":
			return "Comprado";
		case "M":
			return "Materia Prima";
		case "E":
			return "Ensamble";
		case "P":
			return "Parte Primaria";
		default:
			return "";	
		}//FIN SWITCH
	}//FIN METODO
}//FIN METODO

