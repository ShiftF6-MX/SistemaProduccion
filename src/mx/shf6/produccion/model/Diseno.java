package mx.shf6.produccion.model;

import java.sql.Connection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.ClienteDAO;

public class Diseno {
	
	//PROPIEDADES
	public ObjectProperty<Integer> sysPk;
	public StringProperty archivoDiseno;
	public StringProperty numeroParte;
	public StringProperty archivoEspecificacionT; //Archivo de Especificacion Tecnica
	public ObjectProperty<Integer> clienteFk;
	
	//CONTRUCTOR SIN PARAMETROS
	public Diseno() {
		this(0,"","","",0);
	}//FIN CONSTUCTOR
	
	//CONTRUCTOR CON PARAMETROS
	public Diseno(Integer sysPk, String archivoDiseno, String numeroParte, String archivoEspecificacionT, int clienteFk) {
		this.sysPk = new SimpleObjectProperty<Integer>(sysPk);
		this.archivoDiseno = new SimpleStringProperty(archivoDiseno);
		this.numeroParte = new SimpleStringProperty(numeroParte);
		this.archivoEspecificacionT = new SimpleStringProperty(archivoEspecificacionT);
		this.clienteFk = new SimpleObjectProperty<Integer>(clienteFk);
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
	
	//METODOS DE ACCESO A "ARCHIVO DISEÑO"
	public void setArchivoDiseno(String archivoDiseno) {
		this.archivoDiseno.set(archivoDiseno);
	}//FIN METODO
	
	public String getArchivoDiseno() {
		return this.archivoDiseno.get();
	}//FIN METODO
	
	public StringProperty archivoDisenoProperty() {
		return this.archivoDiseno;
	}//FIN METODO
	//FIN METODOS "ARCHIVO DISEÑO"
	
	//METODOS DE ACCESO A "NUMERO PARTE"
	public void setNumeroParte(String numeroParte) {
		this.numeroParte.set(numeroParte);
	}//FIN METODO
	
	public String getNumeroParte() {
		return this.numeroParte.get();
	}//FIN METODO
	
	public StringProperty numeroParteProperty() {
		return this.numeroParte;
	}//FIN METODO
	//FIN METODOS "NUMERO PARTE"
	
	//METODOS DE ACCESO A "ARCHIVO ESPECIFICACION TECNICA"
	public void setArchivoEspecificacionT(String archivoEspecificacionT) {
		this.archivoEspecificacionT.set(archivoEspecificacionT);
	}//FIN METODO
	
	public String getArchivoEspecificacionT() {
		return this.archivoEspecificacionT.get();
	}//FIN METODO
	
	public StringProperty archivoEspecificacionTProperty() {
		return this.archivoEspecificacionT;
	}//FIN METODO
	//FIN METODOS "ARCHIVO ESPECIFICACION TECNICA"
	
	//METODO DE ACCESO A "CLIENTE"
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
}//FIN METODO