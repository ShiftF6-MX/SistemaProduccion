package mx.shf6.produccion.model;

import java.sql.Connection;
import java.sql.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.ClienteDAO;

public class Solicitud {

	//PROPIEDADES
	public ObjectProperty<Integer> sysPk;
	public ObjectProperty<Date> fecha;
	public ObjectProperty<Integer> status;
	public StringProperty notasGenerales;
	public ObjectProperty<Integer> clienteFk;
	
	//CONSTANTES
	public static final int PENDIENTE = 0;
	public static final int APROBADA = 1;
	public static final int CANCELADA = 2;
	
	//CONSTRUCTOR VACIO
	public Solicitud() {
		this(0,null,0,"",0);
	}//FIN CONSTRUCTOR
	
	//CONSTRUCTOR CON PARAMETROS
	public Solicitud(Integer sysPk, Date fecha, int status, String notasGenerales, int clienteFk) {
		this.sysPk = new SimpleObjectProperty<Integer>(sysPk);
		this.fecha = new SimpleObjectProperty<Date>(fecha);
		this.status = new SimpleObjectProperty<Integer>(status);
		this.notasGenerales = new SimpleStringProperty(notasGenerales);
		this.clienteFk = new SimpleObjectProperty<Integer>(clienteFk);		
	}//FIN CONTRUCTOR
	
	//METODOS DE ACCESO A "SYPK"
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
	
	//METODOS DE ACCESO A "FECHA"
	public void setFecha(Date fecha) {
		this.fecha.set(fecha);
	}//FIN METODO
	
	public Date getFecha() {
		return this.fecha.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaProperty(){
		return this.fecha;
	}//FIN METODO
	//FIN METODOS "FECHA"
	
	//METODOS DE ACCESO A "STATUS"
	public void setStatus(Integer status) {
		this.status.set(status);
	}//FIN METODO
	
	public Integer getStatus() {
		return this.status.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> statusProperty() {
		return this.status;
	}//FIN METODO	

	public String getDescripcionStatus() {
		switch (this.getStatus()) {
		case 0:
			return "Pendiente";
		case 1:
			return "Aprobada";
		case 2:
			return "Cancelada";
		}//FIN WTITCH
		return "";
	}//FIN METODO
	
	public void setNumeroStatus(String status) {
		switch (status) {
		case "Pendiente":
			this.setStatus(0);
			break;
		case "Aprobada":
			this.setStatus(1);
			break;
		case "Cancelada":
			this.setStatus(2);
			break;
		}//FIN WTITCH
	}//FIN METODO
	
	public StringProperty descripcionStatusProperty() {
		switch (this.getStatus()) {
		case 0:
			return new SimpleStringProperty("Pendiente");
		case 1:
			return new SimpleStringProperty("Aprobada");
		case 2:
			return new SimpleStringProperty("Cancelada");
		}//FIN WTITCH
		return new SimpleStringProperty();
	}//FIN METODO
	//FIN METODOS "STATUS"
	
	//METODOS DE ACCESO A "NOTAS GENERALES"
	public void setNotasGenerales(String notasGenerales) {
		this.notasGenerales.set(notasGenerales);
	}//FIN METODO
	
	public String getNotasGenerales() {
		return this.notasGenerales.get();
	}//FIN METODO
	
	public StringProperty notasGeneralesProperty() {
		return this.notasGenerales;
	}//FIN METODO
	//FIN METODOS "NOTAS GENERALES"
	
	//METODOS DE ACCESO A "CLIENTE
	public void setClienteFk(Integer clienteFk) {
		this.clienteFk.set(clienteFk);
	}//FIN METODO
	
	public Integer getClienteFk() {
		return this.clienteFk.get();
	}//FIN METODO
	
	public Cliente getCliente(Connection connection) {
		ClienteDAO clienteDAO = new ClienteDAO();
		Cliente cliente = (Cliente) clienteDAO.leer(connection, "SysPK", "" + this.getClienteFk()).get(0);
		return cliente;
	}//FIN METODO
	//FIN METODOS "CLIENTE"
}//FIN CLASE
