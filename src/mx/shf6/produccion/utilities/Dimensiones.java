package mx.shf6.produccion.utilities;

public class Dimensiones {
	
	//PROPIEDADES
	private Double largo;
	private Double ancho;
	private Double altoEspesor;
	
	public Dimensiones() {
		this(0.0, 0.0, 0.0);
	}//FIN CONSTRUCTOR
	
	public Dimensiones(Double largo, Double ancho, Double altoEspesor) {
		this.largo = largo;
		this.ancho = ancho;
		this.altoEspesor = altoEspesor;
	}//FIN CONSTRUCTOR

	public Double getLargo() {
		return largo;
	}//FIN METODO

	public void setLargo(Double largo) {
		this.largo = largo;
	}//FIN METODO

	public Double getAncho() {
		return ancho;
	}//FIN METODO

	public void setAncho(Double ancho) {
		this.ancho = ancho;
	}//FIN METODO

	public Double getAltoEspesor() {
		return altoEspesor;
	}//FIN METODO

	public void setAltoEspesor(Double altoEspesor) {
		this.altoEspesor = altoEspesor;
	}//FIN METODO
	
}//FIN CLASE
