package mx.shf6.produccion.model;

import java.sql.Connection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.ListaMaterialesDAO;
import mx.shf6.produccion.model.dao.ParteDAO;

public class DetalleLista {
	
	//PROPIEDADES
	public ObjectProperty<Integer> sysPk;
	public StringProperty item;
	public ObjectProperty<Integer> cantidad;
	public StringProperty zona;
	public ObjectProperty<Integer> nivel;
	public ObjectProperty<Integer> listaMaterialesFk;
	public ObjectProperty<Integer> parteFk;
	
	//CONTRUCTOR SIN PARAMETROS
	public DetalleLista() {
		this(0,"",0,"",0,0,0);
	}//FIN CONSTRUCTOR

	//CONSTRUCTOR CON PARAMETROS
	public DetalleLista(int sysPk, String item, int cantidad, String zona, int nivel, int listaMaterialesFk, int parteFk) {
		this.sysPk = new SimpleObjectProperty<Integer>(sysPk);
		this.item = new SimpleStringProperty(item);
		this.cantidad = new SimpleObjectProperty<Integer>(cantidad);
		this.zona = new SimpleStringProperty(zona);
		this.nivel = new SimpleObjectProperty<Integer>(nivel);
		this.listaMaterialesFk = new SimpleObjectProperty<Integer>(listaMaterialesFk);
		this.parteFk = new SimpleObjectProperty<Integer>(parteFk);
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
	
	//METODOS DE ACCESO A "ITEM"
	public void setItem(String item) {
		this.item.set(item);
	}//FIN METODO
	
	public String getItem() {
		return this.item.get();
	}//FIN METODO
	
	public StringProperty itemProperty(){
		return this.item;
	}//FIN METODO
	//FIN METODOS "ITEM"
	
	//METODOS PARA ACCESO A "CANTIDAD"
	public void setCantidad(Integer cantidad) {
		this.cantidad.set(cantidad);
	}//FIN METODO
		
	public Integer getCantidad() {
		return this.cantidad.get();
	}//FIN METODO
		
	public ObjectProperty<Integer> cantidadProperty() {
		return this.cantidad;
	}//FIN METODO
	//FIN METODOS "CANTIDAD"
	
	//METODOS PARA ACCESO A "ZONA"
	public void setZona(String zona) {
		this.zona.set(zona);
	}//FIN METODO
	
	public String getZona() {
		return this.zona.get();
	}//FIN METODO
	
	public StringProperty zonaProperty(){
		return this.zona;
	}//FIN METODO
	//FIN METODOS "ZONA"
	
	//METODOS PARA ACCESO A "NIVEL"
	public void setNivel(Integer nivel) {
		this.nivel.set(nivel);
	}//FIN METODO
		
	public Integer getNivel() {
		return this.nivel.get();
	}//FIN METODO
		
	public ObjectProperty<Integer> nivelProperty() {
		return this.nivel;
	}//FIN METODO
	//FIN METODOS "NIVEL"
	
	//METODOS PARA ACCESO A "LISTA MATERIALES"
	public void setListaMaterialesFk(Integer listaMaterialesFk) {
		this.listaMaterialesFk.set(listaMaterialesFk);
	}//FIN METODO
		
	public Integer getListaMaterialesFk() {
		return this.listaMaterialesFk.get();
	}//FIN METODO
		
	public ObjectProperty<Integer> listaMaterialesFkProperty() {
		return this.listaMaterialesFk;
	}//FIN METODO
	
	public ListaMateriales getListaMateriales(Connection connection) {
		ListaMaterialesDAO listaMaterialesDAO = new ListaMaterialesDAO();
		ListaMateriales listaMateriales = (ListaMateriales) listaMaterialesDAO.leer(connection, "SysPK", "" + this.getListaMaterialesFk()).get(0);
		return listaMateriales;
	}//FIN METODO
	//FIN METODOS "LISTA MATERIALES"
	
	//METODOS PARA ACCESO A "PARTE"
	public void setPartesFk(Integer parteFk) {
		this.parteFk.set(parteFk);
	}//FIN METODO
		
	public Integer getParteFk() {
		return this.parteFk.get();
	}//FIN METODO
		
	public ObjectProperty<Integer> parteFkProperty() {
		return this.parteFk;
	}//FIN METODO
	
	public Parte getParte(Connection connection) {
		ParteDAO parteDAO = new ParteDAO();
		Parte parte = (Parte) parteDAO.leer(connection, "SysPK", "" + this.getParteFk()).get(0);
		return parte;
	}//FIN METODO
	//FIN METODOS "PARTE"
	
}//FIN CLASE