package mx.shf6.produccion.model;

import java.sql.Connection;
import java.sql.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import mx.shf6.produccion.model.dao.DisenoDAO;
import mx.shf6.produccion.model.dao.SolicitudDAO;

public class DetalleSolicitud {

	//PROPIEDADES
	public ObjectProperty<Integer> sysPk;
	public ObjectProperty<Integer> cantidad;
	public ObjectProperty<Date> fechaEntrega;
	public ObjectProperty<Integer> disenoFk;
	public ObjectProperty<Integer> solicitudFk;
	
	//CONSTRUCTOR VACIO
	public DetalleSolicitud() {
		this(0,0,null,0);
	}//FIN CONTRUCTOR
	
	//CONSTRUCTOR CON PARAMETROS
	public DetalleSolicitud(Integer sysPk, int cantidad, Date fechaEntrega, int solicitudFk) {
		this.sysPk = new SimpleObjectProperty<Integer>(sysPk);
		this.cantidad = new SimpleObjectProperty<Integer>(cantidad);
		this.fechaEntrega = new SimpleObjectProperty<Date>(fechaEntrega);
		this.solicitudFk = new SimpleObjectProperty<Integer>(solicitudFk);
	}//FIN CONSTRUCTOR
	
	//METODOS DE ACCESO A "SYSPK"
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
	
	//METODOS DE ACCESO A "CANTIDAD"
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
	
	//METODOS DE ACCESO A "FECHA ENTREGA"
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega.set(fechaEntrega);
	}//FIN METODO
	
	public Date getFechaEntrega() {
		return this.fechaEntrega.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaEntregaProperty() {
		return this.fechaEntrega;
	}//FIN METODO
	//FIN METODOS "FECHA ENTREGA"
	
	//METODOS DE ACCESO A "DISEÑO"
	public void setDisenoFk(Integer disenoFk) {
		this.disenoFk.set(disenoFk);
	}//FIN METODO
	
	public Integer getDisenoFk() {
		return this.disenoFk.get();
	}//FIN METODO
	
	public Diseno getDiseno(Connection connection) {
		DisenoDAO clienteDAO = new DisenoDAO();
		Diseno diseno = (Diseno) clienteDAO.leer(connection, "SysPK", "" + this.getDisenoFk()).get(0);
		return diseno;
	}//FIN METODO
	//FIN METODOS "DISEÑO"
	
	//METODOS DE ACCESO A "SOLICITUD"
	public void setSolicitudFk(Integer solicitudFk) {
		this.solicitudFk.set(solicitudFk);
	}//FIN METODO
	
	public Integer getSolicitudFk() {
		return this.solicitudFk.get();
	}//FIN METODO
	
	public Solicitud getSolicitud(Connection connection) {
		SolicitudDAO solicitudDAO = new SolicitudDAO();
		Solicitud solicitud = (Solicitud) solicitudDAO.leer(connection, "SysPK", "" + this.getSolicitudFk()).get(0);
		return solicitud;
	}//FIN METODO
	//FIN METODOS "SOLICITUD"
}//FIN CLASE