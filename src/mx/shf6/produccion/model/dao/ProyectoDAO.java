package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.Proyecto;
import mx.shf6.produccion.utilities.Notificacion;

public class ProyectoDAO{

	//METODO PARA CREAR UN REGISTRO
	public static boolean createProyecto(Connection connection, Proyecto proyecto) {
		String consulta = "INSERT INTO proyectos (Codigo, Descripcion, Carpeta, EspecificacionTecnica) "
				+ "VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, proyecto.getCodigo());
			sentenciaPreparada.setString(2, proyecto.getDescripcion());
			sentenciaPreparada.setString(3, proyecto.getCarpeta());
			sentenciaPreparada.setString(4, proyecto.getEspecificacionTecnica());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Proyecto> readProyecto(Connection connection) {
		ArrayList<Proyecto> arrayListProyecto = new ArrayList<Proyecto>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Carpeta, EspecificacionTecnica "
				+ "FROM proyectos";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Proyecto proyecto = new Proyecto();
				proyecto.setSysPK(resultados.getInt(1));
				proyecto.setCodigo(resultados.getString(2));
				proyecto.setDescripcion(resultados.getString(3));
				proyecto.setCarpeta(resultados.getString(4));
				proyecto.setEspecificacionTenica(resultados.getString(5));
				arrayListProyecto.add(proyecto);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListProyecto;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static Proyecto readProyecto(Connection connection, int sysPK) {
		Proyecto proyecto = new Proyecto();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Carpeta, EspecificacionTecnica "
				+ "FROM proyectos "
				+ "WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				proyecto.setSysPK(resultados.getInt(1));
				proyecto.setCodigo(resultados.getString(2));
				proyecto.setDescripcion(resultados.getString(3));
				proyecto.setCarpeta(resultados.getString(4));
				proyecto.setEspecificacionTenica(resultados.getString(5));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return proyecto;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Proyecto> readProyecto(Connection connection, String like) {
		ArrayList<Proyecto> arrayListProyecto = new ArrayList<Proyecto>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Carpeta, EspecificacionTecnica "
				+ "FROM proyectos "
				+ "WHERE Codigo LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Proyecto proyecto = new Proyecto();
				proyecto.setSysPK(resultados.getInt(1));
				proyecto.setCodigo(resultados.getString(2));
				proyecto.setDescripcion(resultados.getString(3));
				proyecto.setCarpeta(resultados.getString(4));
				proyecto.setEspecificacionTenica(resultados.getString(5));
				arrayListProyecto.add(proyecto);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListProyecto;
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateProyecto(Connection connection, Proyecto proyecto) {
		String consulta = "UPDATE proyectos SET Codigo = ?, Descripcion = ?, Carpeta = ?, EspecificacionTecnica = ? "
				+ "WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, proyecto.getCodigo());
			sentenciaPreparada.setString(2, proyecto.getDescripcion());
			sentenciaPreparada.setString(3, proyecto.getCarpeta());
			sentenciaPreparada.setString(4, proyecto.getEspecificacionTecnica());
			sentenciaPreparada.setInt(5, proyecto.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteProyecto(Connection connection, Proyecto proyecto) {
		String consulta = "DELETE FROM proyectos WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, proyecto.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<Proyecto> toObservableList(ArrayList<Proyecto> arrayList) {
		ObservableList<Proyecto> listaObservableProyecto = FXCollections.observableArrayList();
		for (Proyecto proyecto : arrayList) 
			listaObservableProyecto.add(proyecto);
		return listaObservableProyecto;
	}//FIN METODO
	
}//FIN CLASE