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
	
	//CONSTRUCTOR VACIO
	public OrdenProduccion() {
		this(0, new Date(System.currentTimeMillis()), "",0,0);
	}//FIN CONSTRUCTOR
	
	//CONSTRUCTOR LLENO
	public OrdenProduccion(Integer sysPK, Date fecha, String lote, Integer status, Integer detalleCotizacionFK) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.fecha = new SimpleObjectProperty<Date>(fecha);
		this.lote = new SimpleStringProperty(lote);
		this.status = new SimpleObjectProperty<Integer>(status);
		this.detalleCotizacionFK = new SimpleObjectProperty<Integer>(detalleCotizacionFK);
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
}//FIN CLASE
