package mx.shf6.produccion.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

	public class CentroTrabajo {
	
	//PROPIEDADES
	private IntegerProperty sysPK;
	private StringProperty codigo;
	private StringProperty descripcion;
	private IntegerProperty grupoTrabajoFK;
	
	//VARIABLES
	
	//CONSTRUCTORES
	public CentroTrabajo() {
		this(0,"","",0);	
	}//FIN CONSTRUCTOR
	
	public CentroTrabajo(Integer sysPK, String codigo, String descripcion, Integer grupoTrabajoFK) {
		this.sysPK = new SimpleIntegerProperty(sysPK);
		this.codigo = new SimpleStringProperty(codigo);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.grupoTrabajoFK = new SimpleIntegerProperty(grupoTrabajoFK);
	}//FIN CONSTRUCTOR
	
	//METODOS
	public int getSysPK() {
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
	
	public String getDescripcion() {
		return descripcion.get();
	}//FIN METODO
	
	public void setDescripcion(String descripcion) {
		this.descripcion.set(descripcion);	
	}//FIN METODO
	
	public StringProperty descripcionProperty() {
		return descripcion;
	}//FIN METODO
	
	public int getGrupoTrabajoFK() {
		return grupoTrabajoFK.get();
	}//FIN METODO
	
	public void setgrupoTrabajoFK(Integer grupoTrabajoFK){
		this.grupoTrabajoFK.set(grupoTrabajoFK);	
	}//FIN METODO
	
	public IntegerProperty grupoTrabajoFK() {
		return grupoTrabajoFK;
	}//FIN METODO
}//FIN CLASE