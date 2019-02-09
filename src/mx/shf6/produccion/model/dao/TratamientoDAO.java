package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Tratamiento;
import mx.shf6.produccion.utilities.Notificacion;

public class TratamientoDAO implements ObjectDAO {

	//METODO PARA HACER CREATE EN LA TABLA TRATAMIENTO
	@Override
	public boolean crear(Connection connection, Object Tratamiento) {	
		Tratamiento tratamiento = (Tratamiento) Tratamiento;
		String query = "INSERT INTO tratamiento (codigo, descripcion) "
				+ "values ( ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, tratamiento.getCodigo());
			preparedStatement.setString(2, tratamiento.getDescripcion());
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA TRATAMIENTO
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query = "";
		ArrayList<Object> listaTratamiento = new ArrayList<Object>();
		if (campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query = "SELECT * FROM tratamiento ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);  
				Tratamiento tratamiento = null;
				while (resultSet.next()) {
					tratamiento = new Tratamiento();
					tratamiento.setSysPk(resultSet.getInt(1));
					tratamiento.setCodigo(resultSet.getString(2));
					tratamiento.setDescripcion(resultSet.getString(3));
					listaTratamiento.add(tratamiento);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else {
			query = "SELECT * FROM tratamiento WHERE " + campoBusqueda + " = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				Tratamiento tratamiento = null;
				while (resultSet.next()) {
					tratamiento = new Tratamiento();
					tratamiento.setSysPk(resultSet.getInt(1));
					tratamiento.setCodigo(resultSet.getString(2));
					tratamiento.setDescripcion(resultSet.getString(3));
					listaTratamiento.add(tratamiento);
				}//FIN WHILE
			}catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		}//FIN IF/ELSE
		return listaTratamiento;
	}//FIN METODO	
	
	//METODO PARA HACER UPDATE EN LA TABLA TRATAMIENTO
	@Override
	public boolean modificar(Connection connection, Object Tratamiento) {
		String query = "UPDATE tratamiento "
				+ "SET  codigo = ?, descripcion = ? "
				+ "WHERE sysPK = ?";
		try {
			Tratamiento tratamiento = (Tratamiento)Tratamiento;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, tratamiento.getCodigo());
			preparedStatement.setString(2, tratamiento.getDescripcion());
			preparedStatement.setInt(3, tratamiento.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER DELETE EN LA TABLA TRATAMIENTO
	@Override
	public boolean eliminar(Connection connection, Object Tratamiento) {
		String query = "DELETE FROM tratamiento WHERE sysPK = ?";
		try {	
			Tratamiento tratamiento = (Tratamiento)Tratamiento;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, tratamiento.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO		
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public int ultimoSysPk(Connection connection) {
		String query = "SELECT sysPK FROM tratamiento order by sysPK asc";
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