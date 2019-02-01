package mx.shf6.produccion.model;

import java.sql.Connection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.DisenoDAO;
import mx.shf6.produccion.model.dao.UsuarioDAO;

public class ListaMateriales {
	
	//PROPIEDADES
	public ObjectProperty<Integer> sysPk;
	public ObjectProperty<Integer> disenoFk;
	public ObjectProperty<Integer> responsableFk;
	public StringProperty descripcion;
	
	//CONSTRUCTOR SIN PARAMETROS
	public ListaMateriales() {
		this(0,0,0,"");
	}//FIN CONSTRUCTOR

	public ListaMateriales(Integer sysPk, int diseno, int responsable, String descripcion) {
		this.sysPk = new SimpleObjectProperty<Integer>(sysPk);
		this.disenoFk = new SimpleObjectProperty<Integer>(diseno);
		this.responsableFk = new SimpleObjectProperty<Integer>(responsable);
		this.descripcion = new SimpleStringProperty(descripcion);
	}//FIN CONSTRUCTOR
	
	//METODOS PARA ACCESO A "SYSPK"
	public void setSysPk(Integer sysPk) {
		this.sysPk.set(sysPk);
	}//FIN METODO
		
	public Integer getSysPk() {
		return this.sysPk.get();
	}//FIN METODO
		
	public ObjectProperty<Integer> sysPkProperty() {
		return this.sysPk;
	}//FIN METODO
	//FIN METODOS "SYSPK"
	
	//METODOS DE ACCESO A "DISENO"
	public void setDisenoFk(Integer diseno) {
		this.disenoFk.set(diseno);
	}//FIN METODO
		
	public Integer getDisenoFk() {
		return this.disenoFk.get();
	}//FIN METODO
		
	public ObjectProperty<Integer> disenoFkProperty() {
		return this.disenoFk;
	}//FIN METODO
	
	public Diseno getDiseno(Connection connection) {
		DisenoDAO disenoDAO = new DisenoDAO();
		Diseno diseno = (Diseno) disenoDAO.leer(connection, "SysPK", "" + this.getDisenoFk()).get(0);
		return diseno;
	}//FIN METODO
	//FIN METODOS "DISENO"
	
	//METODOS DE ACCESO A "RESPONSABLE"
	public void setResponsableFk(Integer responsable) {
		this.responsableFk.set(responsable);
	}//FIN METODO
		
	public Integer getResponsableFk() {
		return this.responsableFk.get();
	}//FIN METODO
		
	public ObjectProperty<Integer> responsableFkProperty() {
		return this.responsableFk;
	}//FIN METODO
	
	public Usuario getResponsable(Connection connection) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario responsable = (Usuario) usuarioDAO.leer(connection, "SysPK", "" + this.getResponsableFk()).get(0);
		return responsable;
	}//FIN METODO
	//FIN METODOS "RESPONSABLE"
	
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
}//PROPIEDADES