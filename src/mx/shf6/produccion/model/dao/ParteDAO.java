package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Parte;
import mx.shf6.produccion.utilities.Notificacion;

public class ParteDAO implements ObjectDAO{

	//METODO PARA HACER CREATE EN LA TABLA PARTES
	@Override
	public boolean crear(Connection connection, Object Partes) {	
		Parte partes = (Parte) Partes;
		String query = "INSERT INTO partes (cliente, material, tipoProducto, descripcion) "
				+ "values ( ?, ?, ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, partes.getClienteFk());
			preparedStatement.setInt(2, partes.getMaterialFk());
			preparedStatement.setInt(3, partes.getTipoProductoFk());
			preparedStatement.setString(4, partes.getDescripcion());			
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA PARTES
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query = "";
		ArrayList<Object> listaPartes = new ArrayList<Object>();
		if (campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query = "SELECT * FROM partes ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);  
				Parte partes = null;
				while (resultSet.next()) {
					partes = new Parte();
					partes.setSysPk(resultSet.getInt(1));
					partes.setClienteFk(resultSet.getInt(2));
					partes.setMaterialFk(resultSet.getInt(3));
					partes.setTipoProductoFk(resultSet.getInt(4));
					partes.setDescripcion(resultSet.getString(5));					
					listaPartes.add(partes);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else {
			query = "SELECT * FROM partes WHERE " + campoBusqueda + " = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				Parte partes = null;
				while (resultSet.next()) {
					partes = new Parte();
					partes.setSysPk(resultSet.getInt(1));
					partes.setClienteFk(resultSet.getInt(2));
					partes.setMaterialFk(resultSet.getInt(3));
					partes.setTipoProductoFk(resultSet.getInt(4));
					partes.setDescripcion(resultSet.getString(5));					
					listaPartes.add(partes);
				}//FIN WHILE
			}catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		}//FIN IF/ELSE
		return listaPartes;
	}//FIN METODO	
	
	//METODO PARA HACER UPDATE EN LA TABLA PARTES
	@Override
	public boolean modificar(Connection connection, Object Partes) {
		String query = "UPDATE partes "
				+ "SET  cliente = ?, material = ?, tipoProducto = ?, descripcion = ? "
				+ "WHERE sysPK = ?";
		try {
			Parte partes = (Parte)Partes;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, partes.getClienteFk());
			preparedStatement.setInt(2, partes.getMaterialFk());
			preparedStatement.setInt(3, partes.getTipoProductoFk());
			preparedStatement.setString(4, partes.getDescripcion());			
			preparedStatement.setInt(5, partes.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER DELETE EN LA TABLA PARTES
	@Override
	public boolean eliminar(Connection connection, Object Partes) {
		String query = "DELETE FROM partes WHERE sysPK = ?";
		try {	
			Parte partes = (Parte)Partes;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, partes.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO		
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public int ultimoSysPk(Connection connection) {
		String query = "SELECT sysPK FROM partes order by sysPK asc";
		int ultimoSysPk = 0;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
				ultimoSysPk = resultSet.getInt(1);
			return ultimoSysPk;
		}catch (SQLException e) {
			Notificacion.dialogoException(e);
		}//FIN TRY/CATCH
		return ultimoSysPk;
	}//FIN METODO

}//FIN CLASE