package mx.shf6.produccion.model;

import com.mysql.jdbc.Connection;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.EmpleadoDAO;
import mx.shf6.produccion.model.dao.PuestoDAO;

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
	
	///METODO PARA ACCESO AL "SYS_PK"
	public Integer getSysPK() {
		return sysPK.get();
	}//FIN METODO
	
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);	
	}//FIN METODO
	
	public IntegerProperty sysPKProperty() {
		return sysPK;
	}//FIN METODO 
	//FIN METODOS SYS_PK
	
	///METODO PARA ACCESO AL "CODIGO"
	public String getCodigo() {
		return codigo.get();
	}//FIN METODO
	
	public void setCodigo(String codigo) {
		this.codigo.set(codigo);	
	}//FIN METODO
	
	public StringProperty codigoProperty() {
		return codigo;
	}//FIN METODO
	//FIN METODOS CODIGO
	
	///METODO PARA ACCESO AL "NOMBRE"
	public String getNombre() {
		return nombre.get();
	}//FIN METODO
	
	public void setNombre(String nombre) {
		this.nombre.set(nombre);	
	}//FIN METODO
	
	public StringProperty nombreProperty() {
		return nombre;
	}//FIN METODO
	//FIN METODOS NOMBRE
	
	///METODO PARA ACCESO AL "PUESTO"
	public int getPuestoFK() {
		return puestoFK.get();
	}//FIN METODO
	
	public void setPuestoFK(Integer puestoFK) {
		this.puestoFK.set(puestoFK);	
	}//FIN METODO
	
	public IntegerProperty puestoFKProperty() {
		return puestoFK;
	}//FIN METODO
	
	public Puesto getPuesto(Connection conection) {
		return PuestoDAO.readPuesto(conection, this.getPuestoFK());
	}//FIN METODO
	//FIN METODOS PUESTO

}//FIN CLASE
