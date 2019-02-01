package mx.shf6.produccion.model;

import java.sql.Connection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.MaterialDAO;
import mx.shf6.produccion.model.dao.TipoProductoDAO;

public class Parte {
	
	//PROPIEDADES
	public ObjectProperty<Integer> sysPk;
	public ObjectProperty<Integer> clienteFk;
	public ObjectProperty<Integer> materialFk;
	public ObjectProperty<Integer> tipoProductoFk;
	public StringProperty descripcion;
		
	//CONSTRUCTOR VACIO
	public Parte() {
		this(0,0,0,0,"");
	}//FIN CONSTRUCTOR


	public Parte(int sysPk, int clienteFk, int materialFk, int tipoProductoFk, String descripcion) {
		this.sysPk = new SimpleObjectProperty<Integer>(sysPk);
		this.clienteFk = new SimpleObjectProperty<Integer>(clienteFk);
		this.materialFk = new SimpleObjectProperty<Integer>(materialFk);
		this.tipoProductoFk = new SimpleObjectProperty<Integer>(tipoProductoFk);
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
	
	//METODOS PARA ACCESO A "CLIENTE"
	public void setClienteFk(int clienteFk) {
		this.clienteFk.set(clienteFk);
	}//FIN METODO
	
	public Integer getClienteFk() {
		return this.clienteFk.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> clienteFkProperty(){
		return this.clienteFk;
	}//FIN METODO
	
	public Cliente getTipoMateriaPrima(Connection connection) {
		ClienteDAO clienteDAO = new ClienteDAO();
		Cliente cliente = (Cliente) clienteDAO.leer(connection, "SysPK", "" + this.getClienteFk()).get(0);
		return cliente;
	}//FIN METODO
	//FIN METODOS DE ACCESO A "CLIENTE"
	
	//METODOS PARA ACCESO A "MATERIAL"
	public void setMaterialFk(int materialFk) {
		this.materialFk.set(materialFk);
	}//FIN METODO
	
	public Integer getMaterialFk() {
		return this.materialFk.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> materialFkProperty(){
		return this.materialFk;
	}//FIN METODO
	
	public Material getMaterial(Connection connection) {
		MaterialDAO materialDAO = new MaterialDAO();
		Material tipoMateriaPrima = (Material) materialDAO.leer(connection, "SysPK", "" + this.getMaterialFk()).get(0);
		return tipoMateriaPrima;
	}//FIN METODO
	//FIN METODOS DE ACCESO A "MATERIAL"
	
	//METODOS PARA ACCESO A "TIPO PRODUCTO"	
	public void setTipoProductoFk(int tipoProductoFk) {
		this.tipoProductoFk.set(tipoProductoFk);
	}//FIN METODO
	
	public Integer getTipoProductoFk() {
		return this.tipoProductoFk.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> tipoProductoFkProperty(){
		return this.tipoProductoFk;
	}//FIN METODO
	
	public TipoProducto getTipoProducto(Connection connection) {
		TipoProductoDAO tipoProductoDAO = new TipoProductoDAO();
		TipoProducto tipoProducto = (TipoProducto) tipoProductoDAO.leer(connection, "SysPK", "" + this.getTipoProductoFk()).get(0);
		return tipoProducto;
	}//FIN METODO
	//FIN METODOS DE ACCESO A "TIPO PRODUCTO"
	
	//METODOS DE ACCESO A "DESCRIPCION"
	public void setDescripcion (String descripcion) {
		this.descripcion.set(descripcion);
	}//FIN METODO
	
	public String getDescripcion() {
		return this.descripcion.get();
	}//FIN METODO
		
	public StringProperty descripcionProperty() {
		return this.descripcion;
	}//FIN METODO
	//FIN METODOS DE ACCESO A "DESCRIPCION"	
}//FIN CLASE