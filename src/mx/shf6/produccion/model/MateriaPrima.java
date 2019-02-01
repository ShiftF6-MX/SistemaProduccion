package mx.shf6.produccion.model;

import java.sql.Connection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import mx.shf6.produccion.model.dao.AcabadoDAO;
import mx.shf6.produccion.model.dao.MaterialDAO;
import mx.shf6.produccion.model.dao.TipoMateriaPrimaDAO;
import mx.shf6.produccion.model.dao.TipoProductoDAO;

public class MateriaPrima {
	
	//PROPIEDADES
	public ObjectProperty<Integer> sysPk;
	public ObjectProperty<Integer> tipoMateriaPrimaFk;
	public ObjectProperty<Integer> materialFk;
	public ObjectProperty<Integer> acabadoFk;
	public ObjectProperty<Integer> tipoProductoFk;
	
	//CONSTRUCTOR VACIO
	public MateriaPrima() {
		this(0,0,0,0,0);
	}//FIN CONSTRUCTOR

	public MateriaPrima(Integer sysPk, int tipoMateriaPrimaFk, int materialFk, int acabadoFk, int tipoProductoFk) {
		this.sysPk = new SimpleObjectProperty<Integer>(sysPk);
		this.tipoMateriaPrimaFk = new SimpleObjectProperty<Integer>(tipoMateriaPrimaFk);
		this.materialFk = new SimpleObjectProperty<Integer>(materialFk);
		this.acabadoFk = new SimpleObjectProperty<Integer>(acabadoFk);
		this.tipoProductoFk = new SimpleObjectProperty<Integer>(tipoProductoFk);
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
	
	//METODOS PARA ACCESO A "TIPO MATERIA PRIMA"
	public void setTipoMateriaPrimaFk(int tipoMateriaPrima) {
		this.tipoMateriaPrimaFk.set(tipoMateriaPrima);
	}//FIN METODO
	
	public Integer getTipoMateriaPrimaFk() {
		return this.tipoMateriaPrimaFk.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> tipoMateriaPrimaFkProperty(){
		return this.tipoMateriaPrimaFk;
	}//FIN METODO
	
	public TipoMateriaPrima getTipoMateriaPrima(Connection connection) {
		TipoMateriaPrimaDAO tipoMateriaPrimaDAO = new TipoMateriaPrimaDAO();
		TipoMateriaPrima tipoMateriaPrima = (TipoMateriaPrima) tipoMateriaPrimaDAO.leer(connection, "SysPK", "" + this.getTipoMateriaPrimaFk()).get(0);
		return tipoMateriaPrima;
	}//FIN METODO
	//FIN METODOS DE ACCESO A "TIPO MATERIA PRIMA"
	
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
	
	//METODOS PARA ACCESO A "ACABADO"	
	public void setAcabadoFk(int acabadoFk) {
		this.acabadoFk.set(acabadoFk);
	}//FIN METODO
	
	public Integer getAcabadoFk() {
		return this.acabadoFk.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> acabadoFkProperty(){
		return this.acabadoFk;
	}//FIN METODO
	
	public Acabado getAcabado(Connection connection) {
		AcabadoDAO acabadoDAO = new AcabadoDAO();
		Acabado acabado = (Acabado) acabadoDAO.leer(connection, "SysPK", "" + this.getAcabadoFk()).get(0);
		return acabado;
	}//FIN METODO
	//FIN METODOS DE ACCESO A "ACABADO"
	
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
}//FIN CLASE
