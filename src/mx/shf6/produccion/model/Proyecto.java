package mx.shf6.produccion.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Proyecto {
	
	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty codigo;
	private StringProperty descripcion;
	private StringProperty carpeta;
	private StringProperty especificacionTecnica;
	
	//CONTRUCTOR SIN PARAMETROS
	public Proyecto() {
		this(-1,"","","","");
	}//FIN CONSTUCTOR
	
	//CONTRUCTOR CON PARAMETROS
	public Proyecto(Integer sysPK, String codigo, String descripcion, String carpeta, String especificacionTecnica) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.codigo = new SimpleStringProperty(codigo);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.carpeta = new SimpleStringProperty(carpeta);
		this.especificacionTecnica = new SimpleStringProperty(especificacionTecnica);
	}//FIN CONSTRUCTOR
	
	//METODOS DE ACCESO A "SYSPK"
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO
		
	public Integer getSysPK() {
		return this.sysPK.get();
	}//FIN METODO
		
	public ObjectProperty<Integer> sysPKProperty() {
		return this.sysPK;
	}//FIN METODO
	//FIN METODOS "SYSPK"
	
	//METODOS DE ACCESO A "CODIGO"
	public void setCodigo(String codigo) {
		this.codigo.set(codigo);
	}//FIN METODO
	
	public String getCodigo() {
		return this.codigo.get();
	}//FIN METODO
	
	public StringProperty codigoProperty() {
		return this.codigo;
	}//FIN METODO
	//FIN METODOS "CODIGO"
	
	//METODOS DE ACCESO A "DESCRIPCION"
	public void setDescripcion(String descripcion) {
		this.descripcion.set(descripcion);
	}//FIN METODO
	
	public String getDescripcion() {
		return this.descripcion.get();
	}//FIN METODO
	
	public StringProperty descripcionProperty() {
		return this.descripcion;
	}//FIN METODO
	//FIN METODOS "DESCRIPCION"
	
	//METODOS DE ACCESO A "CARPETA"
	public void setCarpeta(String carpeta) {
		this.carpeta.set(carpeta);
	}//FIN METODO
	
	public String getCarpeta() {
		return this.carpeta.get();
	}//FIN METODO
	
	public StringProperty carpetaProperty() {
		return this.carpeta;
	}//FIN METODO
	//FIN METODOS "CARPETA"
	
	//METODO DE ACCESO A "ESPECIFICACION TECNICA"
	public void setEspecificacionTenica(String especificacionTecnica) {
		this.especificacionTecnica.set(especificacionTecnica);
	}//FIN METODO
	
	public String getEspecificacionTecnica() {
		return this.especificacionTecnica.get();
	}//FIN METODO
	
	public StringProperty especificacionTecnicaProperty() {
		return this.especificacionTecnica;
	}//FIN METODO
	//FIN METODOS "ESPECIFICACION TECNICA"
}//FIN METODO