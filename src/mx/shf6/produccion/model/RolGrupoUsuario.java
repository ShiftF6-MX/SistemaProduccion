package mx.shf6.produccion.model;

import java.sql.Connection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import mx.shf6.produccion.model.dao.GrupoUsuarioDAO;
import mx.shf6.produccion.model.dao.RolDAO;

public class RolGrupoUsuario {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPk;
	private ObjectProperty<Integer> grupoUsuarioFk;
	private ObjectProperty<Integer> rolFk;
	
	//CONSTRUCTOR SIN PARAMETROS
	public RolGrupoUsuario() {
		this(0,0,0);
	}//FIN CONSTRUCTOR
	
	//CONSTRUCTOR CON PARAMETROS
	public RolGrupoUsuario(Integer sysPk, int grupoUsuarioFk, int rolFk) {
		this.sysPk = new SimpleObjectProperty<Integer>(sysPk);
		this.grupoUsuarioFk = new SimpleObjectProperty<Integer>(grupoUsuarioFk);
		this.rolFk = new SimpleObjectProperty<Integer>(rolFk);
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
	}//FIN METODOD
	//FIN METODOS "SYSPK"
	
	//METODOS PARA ACCESO A "GRUPO USUARIO"
	public void setGrupoUsuarioFk(Integer grupoUsuarioFk) {
		this.grupoUsuarioFk.set(grupoUsuarioFk);
	}//FIN METODO
	
	public Integer geGrupoUsuarioFk() {
		return this.grupoUsuarioFk.get();
	}//FIN METODO
	
	public GrupoUsuario getGrupoUsuario(Connection connection) {
		GrupoUsuarioDAO grupoUsuarioDAO = new GrupoUsuarioDAO();
		GrupoUsuario grupoUsuario = (GrupoUsuario) grupoUsuarioDAO.leer(connection, "SysPK", "" + this.geGrupoUsuarioFk()).get(0);
		return grupoUsuario;
	}//FIN METODO
	//FIN METODOS "GRUPO USUARIO"
	
	//METODOS PARA ACCESO A "ROL"
	public void setRolFk(Integer rolFk) {
		this.rolFk.set(rolFk);
	}//FIN METODO
	
	public Integer getRolFk() {
		return this.rolFk.get();
	}//FIN METODO
	
	public Rol getRol(Connection connection) {
		RolDAO rolDAO = new RolDAO();
		Rol rol = (Rol) rolDAO.leer(connection, "SysPK", "" + this.geGrupoUsuarioFk()).get(0);
		return rol;
	}//FIN METODO
	//FIN METODOS
	
	public String showInformacion(Connection connection) {
		String informacionRolGrupoUsuario = "INFORMACIÓN DEL ROL GRUPO USUARIO \nSysPk: " + this.getSysPk() + "\n"
				+ this.getGrupoUsuario(connection).showInformacion() + "\n"
						+ this.getRol(connection).showInformacion();
		return informacionRolGrupoUsuario;
	}//FIN CLASE
	
}//FIN CLASE