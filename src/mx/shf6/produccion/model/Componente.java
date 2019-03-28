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
import mx.shf6.produccion.utilities.Dimensiones;

public class Componente {
	
	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Integer> consecutivo;
	private StringProperty descripcion;
	private ObjectProperty<Dimensiones> dimensiones;
	private ObjectProperty<Double> costo;
	private StringProperty unidad;
	private ObjectProperty<Integer> clienteFK;					
	private ObjectProperty<Integer> tipoProductoFK;				
	private ObjectProperty<Integer> acabadoFK;
	private ObjectProperty<Integer> tratamientoFK;
	private ObjectProperty<Integer> materialFK;
	private StringProperty gradoMaterial;
	private ObjectProperty<Integer> tipoMiscelaneoFK;
	private ObjectProperty<Integer> tipoMateriaPrimaFK;
	private StringProperty notas;
	private StringProperty status;
		
	//CONSTRUCTOR VACIO
	public Componente() {
		this(0, 0, "", new Dimensiones(), 0.0, "", 0, 0, 0, 0, 0, "", 0, 0, "", "");
	}//FIN CONSTRUCTOR


	public Componente(int sysPK, int consecutivo, String descripcion, Dimensiones dimensiones, Double costo, String unidad, int clienteFK, 
			int tipoProductoFK, int acabadoFK, int tratamientoFK, int materialFK, String gradoMaterial, int tipoMiscelaneoFK, int tipoMateriaPrimaFK, 
			String notas, String status) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.consecutivo = new SimpleObjectProperty<Integer>(consecutivo);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.dimensiones = new SimpleObjectProperty<Dimensiones>(dimensiones);
		this.costo = new SimpleObjectProperty<Double>(costo);
		this.unidad = new SimpleStringProperty(unidad);
		this.clienteFK = new SimpleObjectProperty<Integer>(clienteFK);
		this.tipoProductoFK = new SimpleObjectProperty<Integer>(tipoProductoFK);
		this.acabadoFK = new SimpleObjectProperty<Integer>(acabadoFK);
		this.tratamientoFK = new SimpleObjectProperty<Integer>(tratamientoFK);
		this.materialFK = new SimpleObjectProperty<Integer>(materialFK);
		this.gradoMaterial = new SimpleStringProperty(gradoMaterial);
		this.tipoMiscelaneoFK = new SimpleObjectProperty<Integer>(tipoMiscelaneoFK);
		this.tipoMateriaPrimaFK = new SimpleObjectProperty<Integer>(tipoMateriaPrimaFK);
		this.notas = new SimpleStringProperty(notas);
		this.status = new SimpleStringProperty(status);		
	}//FIN CONSTRUCTOR

	public void setSysPK(int sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO

	public int getSysPK() {
		return this.sysPK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> sysPKProperty() {
		return this.sysPK;
	}//FIN METODO
	
	public void setConsecutivo(int consecutivo) {
		this.consecutivo.set(consecutivo);
	}//FIN METODO
	
	public int getConsecutivo() {
		return this.consecutivo.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> consecutivoProperty() {
		return this.consecutivo;
	}//FIN METODO
	
	public void setDescripcion(String descripcion) {
		this.descripcion.set(descripcion);
	}//FIN METODO
	
	public String getDescripcion() {
		return this.descripcion.get();
	}//FIN METODO
	
	public StringProperty descripcionProperty() {
		return this.descripcion;
	}//FIN METODO
	
	public void setDimensiones(Dimensiones dimensiones) {
		this.dimensiones.set(dimensiones);
	}//FIN METODO
	
	public Dimensiones getDimensiones() {
		return this.dimensiones.get();
	}//FIN METODO
	
	public ObjectProperty<Dimensiones> dimesionesProperty() {
		return this.dimensiones;
	}//FIN METODO
	
	public void setCosto(Double costo) {
		this.costo.set(costo);
	}//FIN METODO
	
	public Double getCosto() {
		return this.costo.get();
	}//FIN METODO
	
	public ObjectProperty<Double> costoProperty() {
		return this.costo;
	}//FIN METODO
	
	public void setUnidad(String unidad) {
		this.unidad.set(unidad);
	}//FIN METODO
	
	public String getUnidad() {
		return this.unidad.get();
	}//FIN METODO
	
	public StringProperty unidadProperty() {
		return this.unidad;
	}//FIN METODO
	
	public void setClienteFK(int clienteFK) {
		this.clienteFK.set(clienteFK);
	}//FIN METODO
	
	public int getclienteFK() {
		return this.clienteFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> clienteFKProperty() {
		return this.clienteFK;
	}//FIN METODO
	
	public Cliente getCliente(Connection connection) {
		return ClienteDAO.readCliente(connection, this.getSysPK());
	}//FIN METODO
	
	public void setTipoProductoFK(int tipoProductoFK) {
		this.tipoProductoFK.set(tipoProductoFK);
	}//FIN METODO
	
	public int getTipoProductoFK() {
		return this.tipoProductoFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> tipoProductoFKProperty() {
		return this.tipoProductoFK;
	}//FIN METODO
	
	public TipoProducto getTipoProducto(Connection connection) {
		return TipoProductoDAO.readTipoProducto(connection, this.getTipoProductoFK());
	}//FIN METODO
	
	public void setAcabadoFK(int acabadoFK) {
		this.acabadoFK.set(acabadoFK);
	}//FIN METODO
	
	public int getAcabadoFK() {
		return this.acabadoFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> acabadoFKProperty() {
		return this.acabadoFK;
	}//FIN METODO
	
	public Acabado getAcabado(Connection connection) {
		return AcabadoDAO.readAcabado(connection, this.getAcabadoFK());
	}//FIN METODO
	
	public void setTratamientoFK(int tratamientoFK) {
		this.tratamientoFK.set(tratamientoFK);
	}//FIN METODO
	
	public int getTratamientoFK() {
		return this.tratamientoFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> tratamientoFKProperty() {
		return this.tratamientoFK;
	}//FIN METODO
	
	public Tratamiento getTratamiento(Connection connection) {
		return TratamientoDAO.readTratamiento(connection, this.getTratamientoFK());
	}//FIN METODO
	
	public void setMaterialFK(int materialFK) {
		this.materialFK.set(materialFK);
	}//FIN METODO
	
	public int getMaterialFK() {
		return this.materialFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> materialFKProperty() {
		return this.materialFK;
	}//FIN METODO
	
	public Material getMaterial(Connection connection) {
		return MaterialDAO.readMaterial(connection, this.getMaterialFK());
	}//FIN METODO
	
	public void setGradoMaterial(String gradoMaterial) {
		this.gradoMaterial.set(gradoMaterial);
	}//FIN METODO
	
	public String getGradoMaterial() {
		return this.gradoMaterial.get();
	}//FIN METODO
	
	public StringProperty gradoMaterialProperty() {
		return this.gradoMaterial;
	}//FIN METODO
	
	public void setTipoMiscelaneoFK(int tipoMiscelaneoFK) {
		this.tipoMiscelaneoFK.set(tipoMiscelaneoFK);
	}//FIN METODO
	
	public int getTipoMiscelaneoFK() {
		return this.tipoMiscelaneoFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> tipoMiscelaneoFKProperty() {
		return this.tipoMiscelaneoFK;
	}//FIN METODO
	
	public TipoMiscelaneo getTipoMiscelaneo(Connection connection) {
		return TipoMiscelaneoDAO.readTipoMiscelaneo(connection, this.getTipoMiscelaneoFK());
	}//FIN METODO
	
	public void setTipoMateriaPrimaFK(int tipoMateriaPrimaFK) {
		this.tipoMateriaPrimaFK.set(tipoMateriaPrimaFK);
	}//FIN METODO
	
	public int getTipoMateriaPrimaFK() {
		return this.tipoMateriaPrimaFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> tipoMateriaPrimaFKProperty() {
		return this.tipoMateriaPrimaFK;
	}//FIN METODO
	
	public TipoMateriaPrima getTipoMateriaPrima(Connection connection) {
		return TipoMateriaPrimaDAO.readTipoMateriaPrima(connection, getTipoMateriaPrimaFK());
	}//FIN METODO
	
	public void setNotas(String notas) {
		this.notas.set(notas);
	}//FIN METODO
	
	public String getNotas() {
		return this.notas.get();
	}//FIN METODO
	
	public StringProperty notasProperty() {
		return this.notas;
	}//FIN METODO
	
	public void setStatus(int status) {
		this.status.set(Status.toString(status));
	}//FIN METODO
	
	public String getStatus() {
		return this.status.get();
	}//FIN METODO
	
	public StringProperty statusProperty() {
		return this.status;
	}//FIN METODO
	
	public int getStatusFK() {
		return Status.toInt(this.getStatus()); 
	}//FIN METODO
	
	public String getNumeroParte(Connection connection) {
		String numeroParte = "";
		if (this.getTipoProducto(connection).getCodigo() == "E" || this.getTipoProducto(connection).getCodigo() == "P")
			 numeroParte = this.getCliente(connection).getCodigo() + this.getMaterial(connection).getCodigo() + this.getConsecutivo() + this.getTipoProducto(connection).getCodigo();
		else if (this.getTipoProducto(connection).getCodigo() == "M")
			numeroParte = this.getTipoMateriaPrima(connection).getCodigo() + this.getMaterial(connection).getCodigo() + this.getAcabado(connection).getCodigo() + this.getConsecutivo() +  this.getTipoProducto(connection).getCodigo();
		else if (this.getTipoProducto(connection).getCodigo() == "C")
			numeroParte = this.getTipoMiscelaneo(connection).getCodigo() + this.getMaterial(connection).getCodigo() + this.getTratamiento(connection).getCodigo() + this.getConsecutivo() +  this.getTipoProducto(connection).getCodigo();
		return numeroParte;
	}//FIN METODO
	
}//FIN CLASE