package mx.shf6.produccion.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DetalleProceso {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Integer> operacion;
	private StringProperty descripcion;
	private ObjectProperty<Integer> tiempoPreparacion;
	private ObjectProperty<Integer> tiempoOperacion;
	private ObjectProperty<Integer> centroTrabajoFK;
	private StringProperty nombreCentroTrabajo;
	private ObjectProperty<Integer> grupoTrabajoFK;
	private StringProperty nombreGrupoTrabajo;
	private ObjectProperty<Integer> procesoFK;

	//VARIABLES

	//CONSTANTES

	//CONSTRUCTOR VACIO
	public DetalleProceso() {
		this(0,0,"",0,0,0,"",0,"",0);
	}//FIN CONSTRUCTOR

	//CONSTRUCTOR CON PARAMETROS
	public DetalleProceso(Integer sysPK, Integer operacion, String descripcion, Integer tiempoPreparacion, Integer tiempoOperacion, Integer centroTrabajoFK, String nombreCentroTrabajo, Integer grupoTrabajoFK, String nombreGrupoTrabajo, Integer procesoFK) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.operacion = new SimpleObjectProperty<Integer>(operacion);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.tiempoPreparacion = new SimpleObjectProperty<Integer>(tiempoPreparacion);
		this.tiempoOperacion = new SimpleObjectProperty<Integer>(tiempoOperacion);
		this.centroTrabajoFK = new SimpleObjectProperty<Integer>(centroTrabajoFK);
		this.nombreCentroTrabajo = new SimpleStringProperty(nombreCentroTrabajo);
		this.grupoTrabajoFK = new SimpleObjectProperty<Integer>(grupoTrabajoFK);
		this.nombreGrupoTrabajo =  new SimpleStringProperty(nombreGrupoTrabajo);
		this.procesoFK = new SimpleObjectProperty<Integer>(procesoFK);
	}//FIN CONSTRUCTOR

	//METODOS PARA ACCESO A "SYSPK"
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO
	
	public Integer getSysPK() {
		return sysPK.get();
	}//FIN METODO

	public ObjectProperty<Integer> sysPKProperty() {
    	return sysPK;
    }//FIN METODO
	//FIN METODOS "SYSPK"

	//METODOS PARA ACCESO A "OPERACION"
	public void setOperacion(Integer operacion) {
		this.operacion.set(operacion);
	}//FIN METODO
	
	public Integer getOperacion() {
		return operacion.get();
	}//FIN METODO

	public ObjectProperty<Integer> operacionProperty() {
    	return operacion;
    }//FIN METODO
	//FIN METODOS "OPERACION"

	//METODOS PARA ACCESO A "DESCRIPCION"
	public void setDescripcion(String descripcion) {
		this.descripcion.set(descripcion);
	}//FIN METODO
	
	public String getDescripcion() {
		return descripcion.get();
	}//FIN METODO

	public StringProperty descripcionProperty() {
    	return descripcion;
    }//FIN METODO
	//FIN METODOS "DESCRIPCION"

	//METODOS PARA ACCESO A "PREPARACION"
	public void setTiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion.set(tiempoPreparacion);
	}//FIN METODO
	
	public Integer getTiempoPreparacion() {
		return tiempoPreparacion.get();
	}//FIN METODO

	public ObjectProperty<Integer> tiempoPreparacionProperty() {
    	return tiempoPreparacion;
    }//FIN METODO
	//FIN METODOS "PREPARACION"

	//METODOS PARA ACCESO A "TIEMPOOPERACION"
	public void setTiempoOperacion(Integer tiempoOperacion) {
		this.tiempoOperacion.set(tiempoOperacion);
	}//FIN METODO
	
	public Integer getTiempoOperacion() {
		return tiempoOperacion.get();
	}//FIN METODO

	public ObjectProperty<Integer> tiempoOperacionProperty() {
    	return tiempoOperacion;
    }//FIN METODO
	//FIN METODOS "TIEMPOOPERACION"

	//METODOS PARA ACCESO A "CENTROTRABAJOFK"
	public void setCentroTrabajoFK(Integer centroTrabajoFK) {
		this.centroTrabajoFK.set(centroTrabajoFK);
	}//FIN METODO
	
	public Integer getCentroTrabajoFK() {
		return centroTrabajoFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> centroTrabajoFKProperty() {
    	return centroTrabajoFK;
    }//FIN METODO
	
	public void setNombreCentroTrabajo(String nombreCentroTrabajo) {
		this.nombreCentroTrabajo.set(nombreCentroTrabajo);
	}//FIN METODO
	
	public String getNombreCentroTrabajo() {
		return nombreCentroTrabajo.get();
	}//FIN METODO
	
	public StringProperty nombreCentroTrabajoProperty() {
		return nombreCentroTrabajo;
	}//FIN METODO
	//FIN METODOS "CENTROTRABAJOFK"

	//METODOS PARA ACCESO A "GRUPOTRABAJOFK"
	public void setGrupoTrabajoFK(Integer grupoTrabajoFK) {
		this.grupoTrabajoFK.set(grupoTrabajoFK);
	}//FIN METODO
	
	public Integer getGrupoTrabajoFK() {
		return grupoTrabajoFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> grupoTrabajoFKProperty() {
	    return grupoTrabajoFK;
	}//FIN METODO
	
	public void setNombreGrupoTrabajo(String nombreGrupoTrabajo) {
		this.nombreGrupoTrabajo.set(nombreGrupoTrabajo);
	}//FIN METODO
	
	public String getNombreGrupoTrabajo() {
		return nombreGrupoTrabajo.get();
	}//FIN METODO
	
	public StringProperty nombreGrupoTrabajoProperty() {
		return nombreGrupoTrabajo;
	}//FIN METODO
	//FIN METODOS "GRUPOTRABAJOFK"

	//METODOS PARA ACCESO A "PROCESOFK"
	public void setProcesoFK(Integer procesoFK) {
		this.procesoFK.set(procesoFK);
	}//FIN METODO
	
	public Integer getProcesoFK() {
		return procesoFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> procesoFKProperty() {
    	return procesoFK;
    }//FIN METODO
	//FIN METODOS "PROCESOFK"

}//FIN CLASE
