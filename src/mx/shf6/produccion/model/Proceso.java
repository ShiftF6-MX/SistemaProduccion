package mx.shf6.produccion.model;

import java.sql.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public class Proceso {

	//PROPIEDADES
	private IntegerProperty sysPK;
	private ObjectProperty<Date> fecha;
	private IntegerProperty cantidad;
	private IntegerProperty ordenamiento;
	private IntegerProperty nivel;
	private StringProperty destino;
	//private IntegerProperty tiempo;
	private IntegerProperty componenteFK;
	private IntegerProperty empleadoFK;

	//VARIABLES

	//CONSTANTES

	//CONSTRUCTOR VACIO
	public Proceso() {
		this(0,null,0,0,0,"",0,0);
	}//FIN CONSTRUCTOR

	//CONSTRUCTOR CON PARAMETROS
	public Proceso(Integer sysPK, Date fecha, Integer cantidad, Integer ordenamiento, Integer nivel, String destino, Integer componenteFK, Integer empleadoFK){
		this.sysPK = new SimpleIntegerProperty(sysPK);
		this.fecha = new SimpleObjectProperty<Date>(fecha);
		this.cantidad = new SimpleIntegerProperty(cantidad);
		this.ordenamiento = new SimpleIntegerProperty(ordenamiento);
		this.nivel = new SimpleIntegerProperty(nivel);
		this.destino = new SimpleStringProperty(destino);
		//this.tiempo = new SimpleIntegerProperty(tiempo);
		this.componenteFK = new SimpleIntegerProperty(componenteFK);
		this.empleadoFK = new SimpleIntegerProperty(empleadoFK);
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

	//METODOS PARA ACCESO A FECHA
	public Date getFecha() {
		return fecha.get();
	}//FIN METODO

	public void setFecha(Date fecha) {
		this.fecha.set(fecha);
	}//FIN METODO

	public ObjectProperty<Date> fechaProperty() {
		return fecha;
	}//FIN METODO
	//FIN METODOS "FECHA"

	//METODOS PARA ACCESO A "CANTIDAD"
	public Integer getCantidad() {
        return cantidad.get();
    }//FIN METODO

	public void setCantidad(Integer cantidad) {
        this.cantidad.set(cantidad);
    }//FIN METODO

	public IntegerProperty cantidadProperty() {
        return cantidad;
    }//FIN METODO
	//FIN METODOS "CANTIDAD"

	//METODO PARA ACCESO A "ORDENAMIENTO"
	public Integer getOrdenamiento() {
        return ordenamiento.get();
    }//FIN METODO

	 public void setOrdenamiento(Integer ordenamiento) {
		 this.ordenamiento.set(ordenamiento);
	 }//FIN METODO

	 public IntegerProperty ordenamientoProperty() {
	      return ordenamiento;
	 }//FIN METODO
	//FIN METODOS "ORDENAMIENTO"

	 //METODO PARA ACCESO A "NIVEL"
	public Integer getNivel() {
		return nivel.get();
	}//FIN METODO

	public void setNivel(Integer nivel) {
    	this.nivel.set(nivel);
    }//FIN METODO

	public IntegerProperty nivelProperty() {
    	return nivel;
    }//FIN METODO
	//FIN METODOS "NIVEL"

	//METODOS PARA ACCESO A "DESTINO"
	public String getDestino() {
		return destino.get();
	}//FIN METODO

	public void setDestino(String destino) {
    	this.destino.set(destino);
    }//FIN METODO

	public StringProperty destinoProperty() {
    	return destino;
    }//FIN METODO
	//FIN METODOS "DESTINO"

	//METODOS PARA ACCESO A "TIEMPO"
	/*
	public Integer getTiempo() {
		return tiempo.get();
	}//FIN METODO

	 public void setTiempo(Integer tiempo) {
	    this.tiempo.set(tiempo);
	 }//FIN METODO

	 public IntegerProperty tiempoProperty() {
	   	return tiempo;
	 }//FIN METODO
	//FIN METODOS A "TIEMPO"
	 */

	//METODOS PARA ACCESO A "COMPONENTEFK"
	public Integer getComponenteFK() {
		return componenteFK.get();
	}//FIN METODO

	public void setComponenteFK(Integer componenteFK) {
    	this.componenteFK.set(componenteFK);
    }//FIN METODO

	public IntegerProperty componenteFKProperty() {
    	return componenteFK;
    }//FIN METODO
	//FIN METODOS "COMPONENTEFK"

	//METODOS PARA ACCESO A "EMPLEADOFK"
	public Integer getEmpleadoFK() {
		return empleadoFK.get();
	}//FIN METODO

	 public void setEmpleadoFK(Integer empleadoFK) {
	    this.empleadoFK.set(empleadoFK);
	 }//FIN METODO

	 public IntegerProperty empleadoFK() {
	    return empleadoFK;
	 }//FIN METODO
	//FIN METODOS "EMPLEADOFK"

}//FIN CLASE
