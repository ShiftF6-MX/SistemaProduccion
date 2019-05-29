package mx.shf6.produccion.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Almacen {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty codigo;
	private StringProperty descripcion;

	//CONSTRUCTOR SIN PARAMETROS
	public Almacen () {
		this(0, "", "");
	}

	//CONSTRUCTOR CON PARAMETROS
	public Almacen (Integer sysPK, String codigo, String descripcion) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.codigo = new SimpleStringProperty(codigo);
		this.descripcion = new SimpleStringProperty(descripcion);
	}

	//METODOS DE ACCESO A "SYSPK"
	public void setSysPK(Integer sysPK){
		this.sysPK.set(sysPK);
	}//FIN METODO

	public Integer getSysPK(){
		return this.sysPK.get();
	}//FIN METODO

	public ObjectProperty<Integer> sysPKProperty(){
		return this.sysPK;
	}//FIN METODO

	//METODOS DE ACCESO A "CODIGO"
	public void setCodigo(String codigo){
		this.codigo.set(codigo);
	}//FIN METODO

	public String getCodigo(){
		return this.codigo.get();
	}//FIN METODO

	public StringProperty codigoProperty(){
		return this.codigo;
	}//FIN METODO

	//METODO DE ACCESO A "DESCRIPCION"
	public void setDescripcion(String descripcion){
		this.descripcion.set(descripcion);
	}//FIN METODO

	public String getDescripcion(){
		return this.descripcion.get();
	}//FIN METODO

	public StringProperty descripcionProperty(){
		return this.descripcion;
	}//FIN METODO

}//FIN CLASE
