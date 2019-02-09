package mx.shf6.produccion.model;

import java.sql.Connection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.AcabadoDAO;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.MaterialDAO;
import mx.shf6.produccion.model.dao.TipoMateriaPrimaDAO;
import mx.shf6.produccion.model.dao.TipoMiscelaneoDAO;
import mx.shf6.produccion.model.dao.TipoProductoDAO;
import mx.shf6.produccion.model.dao.TratamientoDAO;

public class Componente {
	
	//PROPIEDADES
	public ObjectProperty<Integer> sysPk;
	public ObjectProperty<Integer> acabadoFk;
	public ObjectProperty<Integer> clienteFk;
	public ObjectProperty<Integer> materialFk;
	public ObjectProperty<Integer> tipoMiscelaneoFk;
	public ObjectProperty<Integer> tipoMateriaPrimaFk;
	public ObjectProperty<Integer> tipoProductoFk;
	public ObjectProperty<Integer> tratamientoFk;
	public ObjectProperty<Double> costo;
	public StringProperty unidad;
	public StringProperty dimension;	
	public StringProperty descripcion;
		
	//CONSTRUCTOR VACIO
	public Componente() {
		this(0,0,0,0,0,0,0,0,0.0,"","","");
	}//FIN CONSTRUCTOR


	public Componente(int sysPk, int acabadoFk, int clienteFk, int materialFk, int tipoMiscelaneoFk, int tipoMateriaPrimaFk, 
			int tipoProductoFk, int tratamientoFk, Double costo, String unidad, String dimension, String descripcion) {
		this.sysPk = new SimpleObjectProperty<Integer>(sysPk);
		this.acabadoFk = new SimpleObjectProperty<Integer>(acabadoFk);		
		this.clienteFk = new SimpleObjectProperty<Integer>(clienteFk);
		this.materialFk = new SimpleObjectProperty<Integer>(materialFk);
		this.tipoMiscelaneoFk = new SimpleObjectProperty<Integer>(tipoMiscelaneoFk);
		this.tipoMateriaPrimaFk = new SimpleObjectProperty<Integer>(tipoMateriaPrimaFk);
		this.tipoProductoFk = new SimpleObjectProperty<Integer>(tipoProductoFk);
		this.tratamientoFk = new SimpleObjectProperty<Integer>(tratamientoFk);
		this.costo = new SimpleObjectProperty<Double>(costo);
		this.unidad = new SimpleStringProperty(unidad);
		this.dimension = new SimpleStringProperty(dimension);
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
	
	public Cliente getCliente(Connection connection) {
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
	
	//METODOS PARA ACCESO A "TIPO MISCELANEO"
	public void setTipoMiscelaneoFk(int tipoMiscelaneoFk) {
		this.tipoMiscelaneoFk.set(tipoMiscelaneoFk);
	}//FIN METODO
	
	public Integer getTipoMiscelaneoFk() {
		return this.tipoMiscelaneoFk.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> tipoMiscelaneoFkProperty(){
		return this.tipoMiscelaneoFk;
	}//FIN METODO
	
	public TipoMiscelaneo getTipoMiscelaneo(Connection connection) {
		TipoMiscelaneoDAO tipoMiscelaneoDAO = new TipoMiscelaneoDAO();
		TipoMiscelaneo tipoMiscelaneo = (TipoMiscelaneo) tipoMiscelaneoDAO.leer(connection, "SysPK", "" + this.getTipoMiscelaneoFk()).get(0);
		return tipoMiscelaneo;
	}//FIN METODO
	//FIN METODOS DE ACCESO A "TIPO MISCELANEO"
	
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
	
	//METODOS PARA ACCESO A "TRATAMIENTO"
	public void setTratamientoFk(int tratamientoFk) {
		this.tratamientoFk.set(tratamientoFk);
	}//FIN METODO
	
	public Integer getTratamientoFk() {
		return this.tratamientoFk.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> tratamientoFkProperty(){
		return this.tratamientoFk;
	}//FIN METODO
	
	public Tratamiento getTratamiento(Connection connection) {
		TratamientoDAO tratamientoDAO = new TratamientoDAO();
		Tratamiento tratamiento = (Tratamiento) tratamientoDAO.leer(connection, "SysPK", "" + this.getTratamientoFk()).get(0);
		return tratamiento;
	}//FIN METODO
	//FIN METODOS DE ACCESO A "TRATAMIENTO"	
	
	//METODOS PARA ACCESO A "COSTO"
	public void setCosto (Double costo) {
		this.costo.set(costo);
	}//FIN METODO
	
	public Double getCosto() {
		return this.costo.get();
	}//FIN METODO
		
	public ObjectProperty<Double> costoProperty() {
		return this.costo;
	}//FIN METODO
	//FIN METODOS DE ACCESO A "COSTO"	
	
	//METODOS PARA ACCESO A "UNIDAD"
	public void setUnidad (String unidad) {
		this.unidad.set(unidad);
	}//FIN METODO
	
	public String getUnidad() {
		return this.unidad.get();
	}//FIN METODO
		
	public StringProperty unidadProperty() {
		return this.unidad;
	}//FIN METODO
	//FIN METODOS DE ACCESO A "UNIDAD"
	
	//METODOS PARA ACCESO A "DIMENSION"
	public void setDimension (String dimension) {
		this.dimension.set(dimension);
	}//FIN METODO
	
	public String getDimension() {
		return this.dimension.get();
	}//FIN METODO
		
	public StringProperty dimensionProperty() {
		return this.dimension;
	}//FIN METODO
	//FIN METODOS DE ACCESO A "DIMENSION"	
	
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