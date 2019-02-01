package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.TipoMateriaPrima;
import mx.shf6.produccion.utilities.Notificacion;

public class TipoMateriaPrimaDAO implements ObjectDAO{

	//METODO PARA HACER CREATE EN LA TABLA TIPOMATERIAPRIMA
	@Override
	public boolean crear(Connection connection, Object TipoMateriaPrima) {	
		TipoMateriaPrima tipoMateriaPrima = (TipoMateriaPrima) TipoMateriaPrima;
		String query = "INSERT INTO tipomateriaprima (codigo, descripcion) "
				+ "values ( ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, tipoMateriaPrima.getCodigo());
			preparedStatement.setString(2, tipoMateriaPrima.getDescripcion());
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA TIPOMATERIAPRIMA
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query = "";
		ArrayList<Object> listaTipoMateriaPrima = new ArrayList<Object>();
		if (campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query = "SELECT * FROM tipomateriaprima ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);  
				TipoMateriaPrima tipoMateriaPrima = null;
				while (resultSet.next()) {
					tipoMateriaPrima = new TipoMateriaPrima();
					tipoMateriaPrima.setSysPk(resultSet.getInt(1));
					tipoMateriaPrima.setCodigo(resultSet.getString(2));
					tipoMateriaPrima.setDescripcion(resultSet.getString(3));
					listaTipoMateriaPrima.add(tipoMateriaPrima);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else {
			query = "SELECT * FROM tipomateriaprima WHERE " + campoBusqueda + " = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet = preparedStatement.executeQuery();
				TipoMateriaPrima tipoMateriaPrima = null;
				while (resultSet.next()) {
					tipoMateriaPrima = new TipoMateriaPrima();
					tipoMateriaPrima.setSysPk(resultSet.getInt(1));
					tipoMateriaPrima.setCodigo(resultSet.getString(2));
					tipoMateriaPrima.setDescripcion(resultSet.getString(3));
					listaTipoMateriaPrima.add(tipoMateriaPrima);
				}//FIN WHILE
			}catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		}//FIN IF/ELSE
		return listaTipoMateriaPrima;
	}//FIN METODO	
	
	//METODO PARA HACER UPDATE EN LA TABLA TIPOMATERIAPRIMA
	@Override
	public boolean modificar(Connection connection, Object TipoMateriaPrima) {
		String query = "UPDATE tipomateriaprima "
				+ "SET  codigo = ?, descripcion = ? "
				+ "WHERE sysPK = ?";
		try {
			TipoMateriaPrima tipoMateriaPrima = (TipoMateriaPrima)TipoMateriaPrima;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, tipoMateriaPrima.getCodigo());
			preparedStatement.setString(2, tipoMateriaPrima.getDescripcion());
			preparedStatement.setInt(3, tipoMateriaPrima.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER DELETE EN LA TABLA TIPOMATERIAPRIMA
	@Override
	public boolean eliminar(Connection connection, Object TipoMateriaPrima) {
		String query = "DELETE FROM tipomateriaprima WHERE sysPK = ?";
		try {	
			TipoMateriaPrima tipoMateriaPrima = (TipoMateriaPrima)TipoMateriaPrima;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, tipoMateriaPrima.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO		
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public int ultimoSysPk(Connection connection) {
		String query = "SELECT sysPK FROM tipomateriaprima order by sysPK asc";
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

}//FIN METODO