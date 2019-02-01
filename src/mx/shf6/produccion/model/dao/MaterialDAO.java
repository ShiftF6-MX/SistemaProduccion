package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Material;
import mx.shf6.produccion.utilities.Notificacion;

public class MaterialDAO implements ObjectDAO {

	//METODO PARA HACER CREATE EN LA TABLA MATERIAL
	@Override
	public boolean crear(Connection connection, Object Material) {	
		Material material = (Material) Material;
		String query = "INSERT INTO material (codigo, descripcion) "
				+ "values ( ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, material.getCodigo());
			preparedStatement.setString(2, material.getDescripcion());
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA MATERIAL
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query = "";
		ArrayList<Object> listaMaterial = new ArrayList<Object>();
		if (campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query = "SELECT * FROM material ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);  
				Material material = null;
				while (resultSet.next()) {
					material = new Material();
					material.setSysPk(resultSet.getInt(1));
					material.setCodigo(resultSet.getString(2));
					material.setDescripcion(resultSet.getString(3));
					listaMaterial.add(material);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else {
			query = "SELECT * FROM material WHERE " + campoBusqueda + " = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				Material material = null;
				while (resultSet.next()) {
					material = new Material();
					material.setSysPk(resultSet.getInt(1));
					material.setCodigo(resultSet.getString(2));
					material.setDescripcion(resultSet.getString(3));
					listaMaterial.add(material);
				}//FIN WHILE
			}catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		}//FIN IF/ELSE
		return listaMaterial;
	}//FIN METODO	
	
	//METODO PARA HACER UPDATE EN LA TABLA MATERIAL
	@Override
	public boolean modificar(Connection connection, Object Material) {
		String query = "UPDATE material "
				+ "SET  codigo = ?, descripcion = ? "
				+ "WHERE sysPK = ?";
		try {
			Material material = (Material)Material;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, material.getCodigo());
			preparedStatement.setString(2, material.getDescripcion());
			preparedStatement.setInt(3, material.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER DELETE EN LA TABLA MATERIAL
	@Override
	public boolean eliminar(Connection connection, Object Material) {
		String query = "DELETE FROM material WHERE sysPK = ?";
		try {	
			Material material = (Material)Material;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, material.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO		
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public int ultimoSysPk(Connection connection) {
		String query = "SELECT sysPK FROM material order by sysPK asc";
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