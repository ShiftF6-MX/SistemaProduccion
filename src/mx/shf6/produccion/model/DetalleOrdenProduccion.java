package mx.shf6.produccion.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DetalleOrdenProduccion {
	
	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty numeroSerie;
	private ObjectProperty<Integer> status; 
	private ObjectProperty<Integer> ordenProduccionFK;
	
	//CONSTRUCTOR VACIO
	public DetalleOrdenProduccion() {
		this(0,"",0,0);
	}//FIN CONSTRUCTOR
	
	//CONSTRUCTOR LLENO
	public DetalleOrdenProduccion(Integer sysPK, String numeroSerie, Integer status, Integer ordenProduccionFK) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.numeroSerie = new SimpleStringProperty(numeroSerie);
		this.status = new SimpleObjectProperty<Integer>(status);
		this.ordenProduccionFK = new SimpleObjectProperty<Integer>(ordenProduccionFK);
	}//FIN CONSTRUCTO
	
	//METODOS DE ACCESO A SYSPK
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO
	
	public Integer getSysPK() {
		return sysPK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> sysPKProperty() {
		return sysPK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A SYSPK
	
	//METODOS DE ACCESO A NUMEROSERIE
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie.set(numeroSerie);
	}//FIN METODO
	
	public String getNumeroSerie() {
		return numeroSerie.get();
	}//FIN METODO
	
	public StringProperty numeroSerieProperty() {
		return numeroSerie;
	}//FIN METODO
	//FIN METODOS DE ACCESO A NUMERO DE SERIE
	
	//METODOS DE ACCESO A STATUS
	public void setStatus(Integer status) {
		this.status.set(status);
	}//FIN METODO
	
	public Integer getStatus() {
		return status.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> statusProperty() {
		return status;
	}//FIN METODO
	//FIN METODOS DE ACCESO A STATUS
	
	//METODOS DE ACCESO A LOTEPRODUCCIONFK
	public void setOrdenProduccionFK(Integer ordenProduccionFK) {
		this.ordenProduccionFK.set(ordenProduccionFK);
	}//FIN METODO
	
	public Integer getOrdenProduccionFK() {
		return ordenProduccionFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> ordenProduccionFK() {
		return ordenProduccionFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A LOTEPRODUCCIONFK
}//FIN CLASE
