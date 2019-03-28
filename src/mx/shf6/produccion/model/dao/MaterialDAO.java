package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.Material;
import mx.shf6.produccion.utilities.Notificacion;

public class MaterialDAO {

	//METODO PARA CREAR UN REGISTRO
		public static boolean createMaterial(Connection connection, Material material) {
			String consulta = "INSERT INTO materiales (Codigo, Descripcion, Status) VALUES (?, ?, ?)";
			try {
				PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
				sentenciaPreparada.setString(1, material.getCodigo());
				sentenciaPreparada.setString(2, material.getDescripcion());
				sentenciaPreparada.setInt(3,  material.getStatusFK());
				sentenciaPreparada.execute();
				return true;
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
				return false;
			}//FIN TRY/CATCH
		}//FIN METODO
		
		//METODO PARA OBTENER UN REGISTRO
		public static ArrayList<Material> readMaterial(Connection connection) {
			ArrayList<Material> arrayListMaterial = new ArrayList<Material>();
			String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status FROM materiales";
			try {
				Statement sentencia = connection.createStatement();
				ResultSet resultados = sentencia.executeQuery(consulta);
				while (resultados.next()) {
					Material material = new Material();
					material.setSysPK(resultados.getInt(1));
					material.setCodigo(resultados.getString(2));
					material.setDescripcion(resultados.getString(3));
					material.setStatus(resultados.getInt(4));
					arrayListMaterial.add(material);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
			return arrayListMaterial;
		}//FIN METODO
		
		//METODO PARA OBTENER UN REGISTRO
		public static Material readMaterial(Connection connection, int sysPK) {
			Material material = new Material();
			String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status FROM materiales WHERE Sys_PK = " + sysPK;
			try {
				Statement sentencia = connection.createStatement();
				ResultSet resultados = sentencia.executeQuery(consulta);
				while (resultados.next()) {
					material.setSysPK(resultados.getInt(1));
					material.setCodigo(resultados.getString(2));
					material.setDescripcion(resultados.getString(3));
					material.setStatus(resultados.getInt(4));
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
			return material;
		}//FIN METODO
		
		//METODO PARA OBTENER UN REGISTRO
		public static ArrayList<Material> readMaterial(Connection connection, String like) {
			ArrayList<Material> arrayListMaterial = new ArrayList<Material>();
			String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status FROM materiales WHERE Codigo LIKE '%" + like + "%' OR Descripcion LIKE '%" + like + "%'";
			try {
				Statement sentencia = connection.createStatement();
				ResultSet resultados = sentencia.executeQuery(consulta);
				while (resultados.next()) {
					Material material = new Material();
					material.setSysPK(resultados.getInt(1));
					material.setCodigo(resultados.getString(2));
					material.setDescripcion(resultados.getString(3));
					material.setStatus(resultados.getInt(4));
					arrayListMaterial.add(material);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
			return arrayListMaterial;
		}//FIN METODO
		
		//METODO PARA CREAR UN REGISTRO
		public static boolean updateMaterial(Connection connection, Material material) {
			String consulta = "UPDATE tipoproducto SET Codigo = ?, Descripcion = ?, Status = ? WHERE Sys_PK = ?";
			try {
				PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
				sentenciaPreparada.setString(1, material.getCodigo());
				sentenciaPreparada.setString(2, material.getDescripcion());
				sentenciaPreparada.setInt(3,  material.getStatusFK());
				sentenciaPreparada.setInt(4, material.getSysPK());
				sentenciaPreparada.execute();
				return true;
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
				return false;
			}//FIN TRY/CATCH
		}//FIN METODO
		
		//METODO PARA CREAR UN REGISTRO
		public static boolean deleteMaterial(Connection connection, Material material) {
			String consulta = "DELETE FROM tipoproducto WHERE Sys_PK = ?";
			try {
				PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
				sentenciaPreparada.setInt(1, material.getSysPK());
				sentenciaPreparada.execute();
				return true;
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
				return false;
			}//FIN TRY/CATCH
		}//FIN METODO
		
		//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
		public static ObservableList<Material> toObservableList(ArrayList<Material> arrayList) {
			ObservableList<Material> listaObservableMaterial = FXCollections.observableArrayList();
			for (Material venta : arrayList) 
				listaObservableMaterial.add(venta);
			return listaObservableMaterial;
		}//FIN METODO

}//FIN CLASE