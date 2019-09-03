package mx.shf6.produccion.model;

import java.sql.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrdenProduccion {
	
	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Date> fecha;
	private StringProperty lote;
	private ObjectProperty<Integer> status;
	private ObjectProperty<Integer> detalleCotizacionFK;
	private StringProperty cliente;
	private StringProperty cotizacion;
	private StringProperty proyecto;
	private StringProperty componente;
	private ObjectProperty<Double> cantidad;
	private ObjectProperty<Date> fechaEntrega;
	
	//CONSTANTES
	public static final int PENDIENTE = 0;
	public static final int EN_PROCESO = 1;
	public static final int PARO = 2;
	public static final int TERMINADO = 3;
	
	//CONSTRUCTOR VACIO
	public OrdenProduccion() {
		this(0, new Date(System.currentTimeMillis()), "",0,0,"","","","", 0.0, new Date(System.currentTimeMillis()));
	}//FIN CONSTRUCTOR
	
	//CONSTRUCTOR LLENO
	public OrdenProduccion(Integer sysPK, Date fecha, String lote, Integer status, Integer detalleCotizacionFK, String cliente, String cotizacion, String proyecto, String componente, Double cantidad, Date fechaEntrega) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.fecha = new SimpleObjectProperty<Date>(fecha);
		this.lote = new SimpleStringProperty(lote);
		this.status = new SimpleObjectProperty<Integer>(status);
		this.detalleCotizacionFK = new SimpleObjectProperty<Integer>(detalleCotizacionFK);
		this.cliente = new SimpleStringProperty(cliente);
		this.cotizacion = new SimpleStringProperty(cotizacion);
		this.proyecto = new SimpleStringProperty(proyecto);
		this.componente =  new SimpleStringProperty(componente);
		this.cantidad = new SimpleObjectProperty<Double>(cantidad);
		this.fechaEntrega = new SimpleObjectProperty<Date>(fechaEntrega);
	}//FIN CONSTRUCTOR
	
	//METODOS DE ACCESO A SYSPK
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO
	
	public Integer getSysPK() {
		return sysPK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> sysPKProperty() {
		return sysPK;
	}//FIN METODO
	//FIN METODOS DE ACCESO SYSPK
	
	//METODOS DE ACCESO A FECHA
	public void setFecha(Date fecha) {
		this.fecha.set(fecha);
	}//FIN METODO
	
	public Date getFecha() {
		return fecha.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaProperty() {
		return fecha;
	}//FIN METODO
	//FIN METODOS DE ACCESO A FECHA
	
	//METODOS DE ACCESO A LOTE
	public void setLote(String lote) {
		this.lote.set(lote);
	}//FIN METODO
	
	public String getLote() {
		return lote.get();
	}//FIN METODO
	
	public StringProperty loteProperty() {
		return lote;
	}//FIN METODO
	//FIN METODOS DE ACCESO A LOTE
	
	//METODOS DE ACCESO A STATUS
	public void setStatus(Integer status) {
		this.status.set(status);
	}//FIN METODO
	
	public Integer getStatus() {
		return status.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> statusProperty() {
		return status;
	}//FIN METODO
	//FIN METODOS DE ACCESO A STATUS

	//METODOS DE ACCESO A DETALLECOTIZACIONFK
	public void setDetalleCotizacionFK(Integer detalleCotizacionFK) {
		this.detalleCotizacionFK.set(detalleCotizacionFK);
	}//FIN METODO
	
	public Integer getDetalleCotizacionFK() {
		return detalleCotizacionFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> detalleCotizacionFKProperty() {
		return detalleCotizacionFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A DETALLECOTIZACIONFK
	
	//METODOS DE ACCESO A CLIENTE
	public void setCliente(String cliente) {
		this.cliente.set(cliente);
	}//FIN METODO
	
	public String getCliente() {
		return cliente.get();
	}//FIN METODO
	
	public StringProperty clienteProperty() {
		return cliente;
	}//FIN METODO
	//FIN METODOS DE ACCESO A CLIENTE
	
	//METODOS DE ACCESO A COTIZACION
	public void setCotizacion(String cotizacion) {
		this.cotizacion.set(cotizacion);
	}//FIN METODO
	
	public String getCotizacion() {
		return cotizacion.get();
	}//FIN METODO
	
	public StringProperty cotizacionProperty() {
		return cotizacion;
	}//FIN METODO
	//FIN METODOS DE ACCESO A COTIZACION
	
	//METODOS DE ACCESO A PROYECTO
	public void setProyecto(String proyecto) {
		this.proyecto.set(proyecto);
	}//FIN METODO
	
	public String getProyecto() {
		return this.proyecto.get();
	}//FIN METODO
	
	public StringProperty proyectoProperty() {
		return proyecto;
	}//FIN METODO
	//FIN METODOS DE ACCESO A PROYECTO
	
	//METODOS DE ACCESO A COMPONENTE
	public void setComponente(String componente) {
		this.componente.set(componente);
	}//FIN METODO
	
	public String getComponente() {
		return this.componente.get();
	}//FIN METODO
	
	public StringProperty componenteProperty() {
		return componente;
	}//FIN METODO
	//FIN METODOS DE ACCESO A COMPONENTE
	
	public void setCantidad(Double cantidad) {
		this.cantidad.set(cantidad);
	}//FIN METODO
	
	public Double getCantidad() {
		return this.cantidad.get();
	}//FIN METODO
	
	public ObjectProperty<Double> cantidadProperty() {
		return this.cantidad;
	}//FIN METODO
	
	//METODOS PARA ACCESO A FECHA ENTREGA
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega.set(fechaEntrega);
	}//FIN METODO
	
	public Date getFechaEntrega() {
		return this.fechaEntrega.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaEntregaProperty(){
		return this.fechaEntrega;
	}//FIN METODO
	
	public StringProperty descripcionStatusProperty() {
		switch (this.getStatus()) {
			case 0:
				return new SimpleStringProperty("Pendiente");
			case 1:
				return new SimpleStringProperty("En proceso");
			case 2:
				return new SimpleStringProperty("En paro");
			case 3: 
				return new SimpleStringProperty("Terminado");
			case 4:
			return new SimpleStringProperty("Entregado");
		}//FIN SWITCH
		return new SimpleStringProperty();
	}//FIN METODO
}//FIN CLASE
