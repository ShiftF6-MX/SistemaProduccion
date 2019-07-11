package mx.shf6.produccion.model;

import java.sql.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ControlOperacion {
	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Integer> cantidad;
	private ObjectProperty<Date> horaFechaInicio;
	private ObjectProperty<Date> horaFechaFinal;
	private ObjectProperty<Integer> centroTrabajoFK;
	private ObjectProperty<Integer> codigoParoFK;
	private ObjectProperty<Integer> componenteFK;
	private ObjectProperty<Integer> detalleProcesoFK;
	private ObjectProperty<Integer> detalleOrdenProduccionFK;
	
	//CONSTRUCTOR VACIO
	public ControlOperacion() {
		this(0, 0, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 0, 0, 0, 0, 0);
	}//FIN CONSTRUCTOR
	
	//CONSTRUCTOR LLENO
	public ControlOperacion(Integer sysPK, Integer cantidad, Date horaFechaInicio, Date horaFechaFinal, Integer centroTrabajoFK,Integer codigoParoFK, Integer componenteFK, Integer detalleProcesoFK, Integer detalleOrdenProduccionFK) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.cantidad = new SimpleObjectProperty<Integer>(cantidad);
		this.horaFechaInicio = new SimpleObjectProperty<Date>(horaFechaInicio);
		this.horaFechaFinal = new SimpleObjectProperty<Date>(horaFechaFinal);
		this.centroTrabajoFK = new SimpleObjectProperty<Integer>(centroTrabajoFK);
		this.codigoParoFK = new SimpleObjectProperty<Integer>(codigoParoFK);
		this.componenteFK = new SimpleObjectProperty<Integer>(componenteFK);
		this.detalleProcesoFK = new SimpleObjectProperty<Integer>(detalleProcesoFK);
		this.detalleOrdenProduccionFK = new SimpleObjectProperty<Integer>(detalleOrdenProduccionFK);
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
	//FIN METODOS DE ACCESO A SYSPK
	
	//METODOS DE ACCESO A CANTIDAD
	public void setCantidad(Integer cantidad) {
		this.cantidad.set(cantidad);
	}//FIN METODO
	
	public Integer getCantidad() {
		return cantidad.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> cantidadProperty() {
		return cantidad;
	}//FIN METODO
	//FIN METODOS DE ACCESO A CANTIDAD
	
	//METODOS DE ACCESO A HORAFECHAINICIO
	public void setHoraFechaInicio(Date horaFechaInicio) {
		this.horaFechaInicio.set(horaFechaInicio);
	}//FIN METODO
	
	public Date getHoraFechaInicio() {
		return horaFechaInicio.get();
	}//FIN METODO
	
	public ObjectProperty<Date> horaFechaInicioProperty() {
		return horaFechaInicio;
	}//FIN METODO
	//FIN METODOS DE ACCESO A HORAFECHAINICIO
	
	//METODOS DE ACCESO A HORAFECHAFINAL
	public void setHoraFechaFinal(Date horaFechaFinal) {
		this.horaFechaInicio.set(horaFechaFinal);
	}//FIN METODO
	
	public Date getHoraFechaFinal() {
		return horaFechaFinal.get();
	}//FIN METODO
	
	public ObjectProperty<Date> horaFechaFinalProperty() {
		return horaFechaFinal;
	}//FIN METODO
	//FIN METODOS DE ACCESO A HORAFECHAFINAL
	
	//METODOS DE ACCESO A CENTROTRABAJOFK
	public void setCentroTrabajoFK(Integer centroTrabajoFK) {
		this.centroTrabajoFK.set(centroTrabajoFK);
	}//FIN METODO
	
	public Integer getCentroTrabajo() {
		return centroTrabajoFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> centroTrabajoFKProperty() {
		return centroTrabajoFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A CENTROTRABAJOFK
	
	//METODOS DE ACCESO A CODIGOPAROFK
	public void setCodigoParo(Integer codigoParoFK) {
		this.codigoParoFK.set(codigoParoFK);
	}//FIN METODO
	
	public Integer getCodigoParoFK() {
		return codigoParoFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> codigoParoFK() {
		return codigoParoFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A CODIGOPAROFK
	
	//METODOS DE ACCESO A COMPONENTEFK
	public void setComponenteFK(Integer componenteFK) {
		this.componenteFK.set(componenteFK);
	}//FIN METODO
	
	public Integer getComponenteFK() {
		return componenteFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> componenteFKProperty() {
		return componenteFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A COMPONENTEFK
	
	//METODOS DE ACCESO A DETALLEPROCESOFK
	public void setDetalleProcesoFK(Integer detalleProcesoFK) {
		this.detalleProcesoFK.set(detalleProcesoFK);
	}//FIN METODO
	
	public Integer getDetalleProcesoFK() {
		return detalleProcesoFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> detalleProcesoFKProperty() {
		return detalleProcesoFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A DETALLEPROCESOFK
	
	//METODOS DE ACCESO A DETALLELOTEPRODUCCIONFK
	public void setDetalleOrdenProduccionFK(Integer detalleOrdenProduccion) {
		this.detalleOrdenProduccionFK.set(detalleOrdenProduccion);
	}//FIN METODO
	
	public Integer getDetalleOrdenProduccionFK() {
		return detalleOrdenProduccionFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> detalleOrdenProduccionFKProperty() {
		return detalleOrdenProduccionFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A DETALLELOTEPRODUCCIONFK
}//FIN CLASE