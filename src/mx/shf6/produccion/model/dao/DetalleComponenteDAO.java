package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.DetalleComponente;
import mx.shf6.produccion.utilities.Notificacion;

public class DetalleComponenteDAO {
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean createDetalleComponente(Connection connection, DetalleComponente detalleComponente) {
		String consulta = "INSERT INTO detallecomponentes (ComponenteSuperiorFK, ComponenteInferiorFK, Cantidad, Notas) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, detalleComponente.getComponenteSuperiorFK());
			sentenciaPreparada.setInt(2, detalleComponente.getComponenteInferiorFK());
			sentenciaPreparada.setDouble(3, detalleComponente.getCantidad());
			sentenciaPreparada.setString(4, detalleComponente.getNotas());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<DetalleComponente> readDetalleComponente(Connection connection) {
		ArrayList<DetalleComponente> arrayListDetalleComponente = new ArrayList<DetalleComponente>();
		String consulta = "SELECT Sys_PK, ComponenteSuperiorFK, ComponenteInferiorFK, Cantidad, Notas FROM detallecomponentes";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				DetalleComponente detalleComponente = new DetalleComponente();
				detalleComponente.setSysPK(resultados.getInt(1));
				detalleComponente.setComponenteSuperiorFK(resultados.getInt(2));
				detalleComponente.setComponenteInferiorFK(resultados.getInt(3));
				detalleComponente.setCantidad(resultados.getDouble(4));
				detalleComponente.setNotas(resultados.getString(5));
				arrayListDetalleComponente.add(detalleComponente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDetalleComponente;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static DetalleComponente readDetalleComponente(Connection connection, int sysPK) {
		DetalleComponente detalleComponente = new DetalleComponente();
		String consulta = "SELECT Sys_PK, ComponenteSuperiorFK, ComponenteInferiorFK, Cantidad, Notas FROM detallecomponentes WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				detalleComponente.setSysPK(resultados.getInt(1));
				detalleComponente.setComponenteSuperiorFK(resultados.getInt(2));
				detalleComponente.setComponenteInferiorFK(resultados.getInt(3));
				detalleComponente.setCantidad(resultados.getDouble(4));
				detalleComponente.setNotas(resultados.getString(5));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return detalleComponente;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<DetalleComponente> readDetalleComponenteSuperiorFK(Connection connection, int componenteSuperiorFK) {
		ArrayList<DetalleComponente> arrayListDetalleComponente = new ArrayList<DetalleComponente>();
		String consulta = "SELECT Sys_PK, ComponenteSuperiorFK, ComponenteInferiorFK, Cantidad, Notas FROM detallecomponentes WHERE ComponenteSuperiorFK = " + componenteSuperiorFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				DetalleComponente detalleComponente = new DetalleComponente();
				detalleComponente.setSysPK(resultados.getInt(1));
				detalleComponente.setComponenteSuperiorFK(resultados.getInt(2));
				detalleComponente.setComponenteInferiorFK(resultados.getInt(3));
				detalleComponente.setCantidad(resultados.getDouble(4));
				detalleComponente.setNotas(resultados.getString(5));
				arrayListDetalleComponente.add(detalleComponente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDetalleComponente;
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateDetalleComponente(Connection connection, DetalleComponente detalleComponente) {
		String consulta = "UPDATE detallecomponentes SET ComponenteSuperiorFK = ?, ComponenteInferiorFK = ?, Cantidad = ?, Notas = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, detalleComponente.getComponenteSuperiorFK());
			sentenciaPreparada.setInt(2, detalleComponente.getComponenteInferiorFK());
			sentenciaPreparada.setDouble(3, detalleComponente.getCantidad());
			sentenciaPreparada.setString(4, detalleComponente.getNotas());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteDetalleComponente(Connection connection, DetalleComponente detalleComponente) {
		String consulta = "DELETE FROM detallecomponentes WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, detalleComponente.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<DetalleComponente> toObservableList(ArrayList<DetalleComponente> arrayList) {
		ObservableList<DetalleComponente> listaObservableDetalleComponente = FXCollections.observableArrayList();
		for (DetalleComponente detalleComponente : arrayList) 
			listaObservableDetalleComponente.add(detalleComponente);
		return listaObservableDetalleComponente;
	}//FIN METODO
	
}//FIN CLASE
