package mx.shf6.produccion.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DetalleProceso {

	//PROPIEDADES
	private IntegerProperty sysPK;
	private IntegerProperty operacion;
	private StringProperty descripcion;
	private IntegerProperty tiempoPreparacion;
	private IntegerProperty tiempoOperacion;
	private IntegerProperty centroTrabajoFK;
	private IntegerProperty grupoTrabajoFK;
	private IntegerProperty procesoFK;

	//VARIABLES

	//CONSTANTES

	//CONSTRUCTOR VACIO
	public DetalleProceso() {
		this(0,0,"",0,0,0,0,0);
	}//FIN CONSTRUCTOR

	//CONSTRUCTOR CON PARAMETROS
	public DetalleProceso(Integer sysPK, Integer operacion, String descripcion, Integer tiempoPreparacion, Integer tiempoOperacion, Integer centroTrabajoFK, Integer grupoTrabajoFK, Integer procesoFK) {
		this.sysPK = new SimpleIntegerProperty(sysPK);
		this.operacion = new SimpleIntegerProperty(operacion);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.tiempoPreparacion = new SimpleIntegerProperty(tiempoPreparacion);
		this.tiempoOperacion = new SimpleIntegerProperty(tiempoOperacion);
		this.centroTrabajoFK = new SimpleIntegerProperty(centroTrabajoFK);
		this.grupoTrabajoFK = new SimpleIntegerProperty(grupoTrabajoFK);
		this.procesoFK = new SimpleIntegerProperty(procesoFK);
	}//FIN CONSTRUCTOR

	//METODOS PARA ACCESO A "SYSPK"
	public Integer getSysPK() {
		return sysPK.get();
	}//FIN METODO

	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO

	public IntegerProperty sysPKProperty() {
    	return sysPK;
    }//FIN METODO
	//FIN METODOS "SYSPK"

	//METODOS PARA ACCESO A "OPERACION"
	public Integer getOperacion() {
		return operacion.get();
	}//FIN METODO

	public void setOperacion(Integer operacion) {
		this.operacion.set(operacion);
	}//FIN METODO

	public IntegerProperty operacionProperty() {
    	return operacion;
    }//FIN METODO
	//FIN METODOS "OPERACION"

	//METODOS PARA ACCESO A "DESCRIPCION"
	public String getDescripcion() {
		return descripcion.get();
	}//FIN METODO

	public void setDescripcion(String descripcion) {
		this.descripcion.set(descripcion);
	}//FIN METODO

	public StringProperty descripcionProperty() {
    	return descripcion;
    }//FIN METODO
	//FIN METODOS "DESCRIPCION"

	//METODOS PARA ACCESO A "PREPARACION"
	public Integer getTiempoPreparacion() {
		return tiempoPreparacion.get();
	}//FIN METODO

	public void setTiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion.set(tiempoPreparacion);
	}//FIN METODO

	public IntegerProperty tiempoPreparacionProperty() {
    	return tiempoPreparacion;
    }//FIN METODO
	//FIN METODOS "PREPARACION"

	//METODOS PARA ACCESO A "TIEMPOOPERACION"
	public Integer getTiempoOperacion() {
		return tiempoOperacion.get();
	}//FIN METODO

	public void setTiempoOperacion(Integer tiempoOperacion) {
		this.tiempoOperacion.set(tiempoOperacion);
	}//FIN METODO

	public IntegerProperty tiempoOperacionProperty() {
    	return tiempoOperacion;
    }//FIN METODO
	//FIN METODOS "TIEMPOOPERACION"

	//METODOS PARA ACCESO A "CENTROTRABAJOFK"
	public Integer getCentroTrabajoFK() {
		return centroTrabajoFK.get();
	}//FIN METODO

	public void setCentroTrabajoFK(Integer centroTrabajoFK) {
		this.centroTrabajoFK.set(centroTrabajoFK);
	}//FIN METODO

	public IntegerProperty centroTrabajoFKProperty() {
    	return centroTrabajoFK;
    }//FIN METODO
	//FIN METODOS "CENTROTRABAJOFK"

	//METODOS PARA ACCESO A "GRUPOTRABAJOFK"
	public Integer getGrupoTrabajoFK() {
		return grupoTrabajoFK.get();
	}//FIN METODO

	public void setGrupoTrabajoFK(Integer grupoTrabajoFK) {
		this.grupoTrabajoFK.set(grupoTrabajoFK);
	}//FIN METODO

	public IntegerProperty grupoTrabajoFKProperty() {
	    return grupoTrabajoFK;
	}//FIN METODO
	//FIN METODOS "GRUPOTRABAJOFK"

	//METODOS PARA ACCESO A "PROCESOFK"
	public Integer getProcesoFK() {
		return procesoFK.get();
	}//FIN METODO

	public void setProcesoFK(Integer procesoFK) {
		this.procesoFK.set(procesoFK);
	}//FIN METODO

	public IntegerProperty procesoFKProperty() {
    	return procesoFK;
    }//FIN METODO
	//FIN METODOS "PROCESOFK"

}//FIN CLASE
