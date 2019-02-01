package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.TipoProducto;
import mx.shf6.produccion.utilities.Notificacion;

public class TipoProductoDAO implements ObjectDAO{

	//METODO PARA HACER CREATE EN LA TABLA TIPOPRODUCTO
	@Override
	public boolean crear(Connection connection, Object TipoProducto) {	
		TipoProducto tipoProducto = (TipoProducto) TipoProducto;
		String query = "INSERT INTO tipoproducto (codigo, descripcion) "
				+ "values ( ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, tipoProducto.getCodigo());
			preparedStatement.setString(2, tipoProducto.getDescripcion());
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA TIPOPRODUCTO
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query = "";
		ArrayList<Object> listaTipoProducto = new ArrayList<Object>();
		if (campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query = "SELECT * FROM tipoproducto ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);  
				TipoProducto tipoProducto = null;
				while (resultSet.next()) {
					tipoProducto = new TipoProducto();
					tipoProducto.setSysPk(resultSet.getInt(1));
					tipoProducto.setCodigo(resultSet.getString(2));
					tipoProducto.setDescripcion(resultSet.getString(3));
					listaTipoProducto.add(tipoProducto);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else {
			query = "SELECT * FROM tipoproducto WHERE " + campoBusqueda + " = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				TipoProducto tipoProducto = null;
				while (resultSet.next()) {
					tipoProducto = new TipoProducto();
					tipoProducto.setSysPk(resultSet.getInt(1));
					tipoProducto.setCodigo(resultSet.getString(2));
					tipoProducto.setDescripcion(resultSet.getString(3));
					listaTipoProducto.add(tipoProducto);
				}//FIN WHILE
			}catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		}//FIN IF/ELSE
		return listaTipoProducto;
	}//FIN METODO	
	
	//METODO PARA HACER UPDATE EN LA TABLA TIPOPRODUCTO
	@Override
	public boolean modificar(Connection connection, Object TipoProducto) {
		String query = "UPDATE tipoproducto "
				+ "SET  codigo = ?, descripcion = ? "
				+ "WHERE sysPK = ?";
		try {
			TipoProducto tipoProducto = (TipoProducto)TipoProducto;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, tipoProducto.getCodigo());
			preparedStatement.setString(2, tipoProducto.getDescripcion());
			preparedStatement.setInt(3, tipoProducto.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER DELETE EN LA TABLA TIPOPRODUCTO
	@Override
	public boolean eliminar(Connection connection, Object TipoProducto) {
		String query = "DELETE FROM tipoproducto WHERE sysPK = ?";
		try {	
			TipoProducto tipoProducto = (TipoProducto)TipoProducto;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, tipoProducto.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO		
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public int ultimoSysPk(Connection connection) {
		String query = "SELECT sysPK FROM tipoproducto order by sysPK asc";
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