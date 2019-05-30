package mx.shf6.produccion.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Empleado {

	//PROPIEDADES 
	private IntegerProperty sysPK;
	private StringProperty codigo;
	private StringProperty nombre;
	private IntegerProperty puestoFK;
	
	//VARIABLES
	
	//CONSTRUCTORES
	
	public Empleado() {
		this(0, "", "", 0);		
	}//FIN CONSTRUCTOR
	
	public Empleado(Integer sysPK, String codigo, String nombre, Integer puestoFK) {
		this.sysPK = new SimpleIntegerProperty(sysPK);
		this.codigo = new SimpleStringProperty(codigo);
		this.nombre = new SimpleStringProperty(nombre);
		this.puestoFK = new SimpleIntegerProperty(puestoFK);
	}//FIN CONSTRUCTOR
	
	///METODOS
	public Integer getSysPK() {
		return sysPK.get();
	}//FIN METODO
	
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);	
	}//FIN METODO
	
	public IntegerProperty sysPKProperty() {
		return sysPK;
	}//FIN METODO
	
	public String getCodigo() {
		return codigo.get();
	}//FIN METODO
	
	public void setCodigo(String codigo) {
		this.codigo.set(codigo);	
	}//FIN METODO
	
	public StringProperty codigoProperty() {
		return codigo;
	}//FIN METODO
	
	public String getNombre() {
		return nombre.get();
	}//FIN METODO
	
	public void setNombre(String nombre) {
		this.nombre.set(nombre);	
	}//FIN METODO
	
	public StringProperty nombreProperty() {
		return nombre;
	}//FIN METODO
	
	public int getPuestoFK() {
		return puestoFK.get();
	}//FIN METODO
	
	public void setPuestoFK(Integer puestoFK) {
		this.puestoFK.set(puestoFK);	
	}//FIN METODO
	
	public IntegerProperty puestoFK() {
		return puestoFK;
	}//FIN METODO

}//FIN CLASE
