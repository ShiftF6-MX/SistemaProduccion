package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Acabado;
import mx.shf6.produccion.utilities.Notificacion;

public class AcabadoDAO implements ObjectDAO{

	//METODO PARA HACER CREATE EN LA TABLA ACABADO
	@Override
	public boolean crear(Connection connection, Object Acabado) {	
		Acabado acabado = (Acabado) Acabado;
		String query = "INSERT INTO acabado (codigo, descripcion) "
				+ "values ( ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, acabado.getCodigo());
			preparedStatement.setString(2, acabado.getDescripcion());
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA ACABADO
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query = "";
		ArrayList<Object> listaAcabado = new ArrayList<Object>();
		if (campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query = "SELECT * FROM acabado ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);  
				Acabado acabado = null;
				while (resultSet.next()) {
					acabado = new Acabado();
					acabado.setSysPk(resultSet.getInt(1));
					acabado.setCodigo(resultSet.getString(2));
					acabado.setDescripcion(resultSet.getString(3));
					listaAcabado.add(acabado);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else {
			query = "SELECT * FROM acabado WHERE " + campoBusqueda + " = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				Acabado acabado = null;
				while (resultSet.next()) {
					acabado = new Acabado();
					acabado.setSysPk(resultSet.getInt(1));
					acabado.setCodigo(resultSet.getString(2));
					acabado.setDescripcion(resultSet.getString(3));
					listaAcabado.add(acabado);
				}//FIN WHILE
			}catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		}//FIN IF/ELSE
		return listaAcabado;
	}//FIN METODO	
	
	//METODO PARA HACER UPDATE EN LA TABLA ACABADO
	@Override
	public boolean modificar(Connection connection, Object Acabado) {
		String query = "UPDATE acabado "
				+ "SET  codigo = ?, descripcion = ? "
				+ "WHERE sysPK = ?";
		try {
			Acabado acabado = (Acabado)Acabado;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, acabado.getCodigo());
			preparedStatement.setString(2, acabado.getDescripcion());
			preparedStatement.setInt(3, acabado.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER DELETE EN LA TABLA ACABADO
	@Override
	public boolean eliminar(Connection connection, Object Acabado) {
		String query = "DELETE FROM acabado WHERE sysPK = ?";
		try {	
			Acabado acabado = (Acabado)Acabado;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, acabado.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO		
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public int ultimoSysPk(Connection connection) {
		String query = "SELECT sysPK FROM acabado order by sysPK asc";
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