package mx.shf6.produccion.model;

import java.sql.Connection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.DomicilioDAO;

public class Cliente {

	//PROPIEDADES
	public ObjectProperty<Integer> sysPk;
	public StringProperty nombre;
	public ObjectProperty<Integer> tipo;
	public StringProperty registroContribuyente;
	public StringProperty telefono;
	public StringProperty correo;
	public StringProperty rutaCarpeta;
	public ObjectProperty<Integer> domicilioFk;
	
	//CONSTANTES
	public static final int FISICA = 0;
	public static final int MORAL = 1;
	
	//CONSTRUCTOR VACIO
	public Cliente () {
		this(0,"",0,"","","","",0);
	}//FIN CONSTRUCTOR
	
	public Cliente (Integer sysPk, String nombre, int tipo, String registroContribuyente, String telefono, String correo, String rutaCarpeta, int domicilioFk) {
		this.sysPk = new SimpleObjectProperty<Integer>(sysPk);
		this.nombre = new SimpleStringProperty(nombre);
		this.tipo = new SimpleObjectProperty<Integer>(tipo);
		this.registroContribuyente = new SimpleStringProperty(registroContribuyente);
		this.telefono = new SimpleStringProperty(telefono);
		this.correo = new SimpleStringProperty(correo);
		this.rutaCarpeta = new SimpleStringProperty(rutaCarpeta);
		this.domicilioFk = new SimpleObjectProperty<Integer>(domicilioFk);
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
	
	//METODOS DE ACCESO A "NOMBRE"
	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}//FIN METODO
	
	public String getNombre() {
		return this.nombre.get();
	}//FIN METODO
	
	public StringProperty nombreProperty() {
		return this.nombre;
	}//FIN METODO
	//FIN METODOS "NOMBRE"
	
	//METODOS DE ACCESO A "TIPO"
	public void setTipo(Integer tipo) {
		this.tipo.set(tipo);
	}//FIN METODO
	
	public Integer getTipo() {
		return this.tipo.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> tipoProperty() {
		return this.tipo;
	}//FIN METODO
	
	public String getDescripciontTipo() {
		switch (this.getTipo()) {
		case 0:
			return "Fisica";
		case 1:
			return "Moral";
		}//FIN WTITCH
		return "";
	}//FIN METODO
	
	public void setNumeroTipo(String tipo) {
		switch (tipo) {
		case "Fisica":
			this.setTipo(0);
		case "Moral":
			this.setTipo(1);
		}//FIN SWTITCH
	}//FIN METODO
	//FIN METODOS "TIPO"
	
	//METODOS DE ACCESO A "REGISTRO CONTRIBUYENTE"
	public void setRegistroContribuyente(String registroContribuyente) {
		this.registroContribuyente.set(registroContribuyente);
	}//FIN METODO
	
	public String getRegistroContribuyente() {
		return this.registroContribuyente.get();
	}//FIN METODO
	
	public StringProperty registroContribuyenteProperty(){
		return this.registroContribuyente;
	}//FIN METODO
	//FIN METODOS "REGISTRO CONTRIBUYENTE"
	
	//METODOS DE ACCESO A "TELEFONO"
	public void setTelefono(String telefono) {
		this.telefono.set(telefono);
	}//FIN METODO
	
	public String getTelefono() {
		return this.telefono.get();
	}//FIN METODO
	
	public StringProperty telefonoProperty() {
		return this.telefono;
	}//FIN METODO
	//FIN METODOS "TELEFONO"
	
	//METODOS DE ACCESO A "CORREO"
	public void setCorreo(String correo) {
		this.correo.set(correo);
	}//FIN METODO
	
	public String getCorreo() {
		return this.correo.get();
	}//FIN METODO
	
	public StringProperty correoProperty() {
		return this.correo;
	}//FIN METODO
	//FIN METODOS "CORREO"
	
	//METODOS DE ACCESO A "RUTA CARPETA"
	public void setRutaCarpeta(String rutaCarpeta) {
		this.rutaCarpeta.set(rutaCarpeta);
	}//FIN METODO
	
	public String getRutaCarpeta() {
		return this.rutaCarpeta.get();
	}//FIN METODO
	
	public StringProperty rutaCarpetaProperty() {
		return this.rutaCarpeta;
	}//FIN METODO
	//FIN METODOS "RUTA CARPETA"
	
	//METODOS PARA ACCESO A "DOMICILIO"
	public void setDomicilioFk(Integer domicilioFk) {
		this.domicilioFk.set(domicilioFk);
	}//FIN METODO
	
	public Integer getDomicilioFk() {
		return this.domicilioFk.get();
	}//FIN METODO
	
	public Domicilio getDomicilio(Connection connection) {
		DomicilioDAO domicilioDAO = new DomicilioDAO();
		Domicilio domicilio = (Domicilio) domicilioDAO.leer(connection, "SysPK", "" + this.getDomicilioFk()).get(0);
		return domicilio;
	}//FIN METODO
	//FIN METODOS "DOMICILIO"
	
}//FIN METODO
