package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.TipoMiscelaneo;
import mx.shf6.produccion.utilities.Notificacion;

public class TipoMiscelaneoDAO implements ObjectDAO {
	
	//METODO PARA HACER CREATE EN LA TABLA TIPOMISCELANEO
	@Override
	public boolean crear(Connection connection, Object TipoMiscelaneo) {	
		TipoMiscelaneo tipoMiscelaneo = (TipoMiscelaneo) TipoMiscelaneo;
		String query = "INSERT INTO tipoMiscelaneo (codigo, descripcion) "
				+ "values ( ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, tipoMiscelaneo.getCodigo());
			preparedStatement.setString(2, tipoMiscelaneo.getDescripcion());
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA TIPOMISCELANEO
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query = "";
		ArrayList<Object> listaTipoMiscelaneo = new ArrayList<Object>();
		if (campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query = "SELECT * FROM tipoMiscelaneo ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);  
				TipoMiscelaneo tipoMiscelaneo = null;
				while (resultSet.next()) {
					tipoMiscelaneo = new TipoMiscelaneo();
					tipoMiscelaneo.setSysPk(resultSet.getInt(1));
					tipoMiscelaneo.setCodigo(resultSet.getString(2));
					tipoMiscelaneo.setDescripcion(resultSet.getString(3));
					listaTipoMiscelaneo.add(tipoMiscelaneo);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else {
			query = "SELECT * FROM tipoMiscelaneo WHERE " + campoBusqueda + " = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				TipoMiscelaneo tipoMiscelaneo = null;
				while (resultSet.next()) {
					tipoMiscelaneo = new TipoMiscelaneo();
					tipoMiscelaneo.setSysPk(resultSet.getInt(1));
					tipoMiscelaneo.setCodigo(resultSet.getString(2));
					tipoMiscelaneo.setDescripcion(resultSet.getString(3));
					listaTipoMiscelaneo.add(tipoMiscelaneo);
				}//FIN WHILE
			}catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		}//FIN IF/ELSE
		return listaTipoMiscelaneo;
	}//FIN METODO	
	
	//METODO PARA HACER UPDATE EN LA TABLA TIPOMISCELANEO
	@Override
	public boolean modificar(Connection connection, Object TipoMiscelaneo) {
		String query = "UPDATE tipoMiscelaneo "
				+ "SET  codigo = ?, descripcion = ? "
				+ "WHERE sysPK = ?";
		try {
			TipoMiscelaneo tipoMiscelaneo = (TipoMiscelaneo)TipoMiscelaneo;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, tipoMiscelaneo.getCodigo());
			preparedStatement.setString(2, tipoMiscelaneo.getDescripcion());
			preparedStatement.setInt(3, tipoMiscelaneo.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER DELETE EN LA TABLA TIPOMISCELANEO
	@Override
	public boolean eliminar(Connection connection, Object TipoMiscelaneo) {
		String query = "DELETE FROM tipoMiscelaneo WHERE sysPK = ?";
		try {	
			TipoMiscelaneo tipoMiscelaneo = (TipoMiscelaneo)TipoMiscelaneo;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, tipoMiscelaneo.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO		
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public int ultimoSysPk(Connection connection) {
		String query = "SELECT sysPK FROM tipoMiscelaneo order by sysPK asc";
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