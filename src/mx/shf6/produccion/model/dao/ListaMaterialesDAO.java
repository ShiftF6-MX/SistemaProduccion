package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.ListaMateriales;
import mx.shf6.produccion.utilities.Notificacion;

public class ListaMaterialesDAO implements ObjectDAO{

	//METODO PARA HACER CREATE EN LA TABLA LISTAMATERIALES
	@Override
	public boolean crear(Connection connection, Object ListaMateriales) {	
		ListaMateriales listaMateriales = (ListaMateriales)ListaMateriales;
		String query = "INSERT INTO listamateriales (diseno, responsable, descripcion) "
				+ "values ( ?, ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, listaMateriales.getDisenoFk());
			preparedStatement.setInt(2, listaMateriales.getResponsableFk());
			preparedStatement.setString(3, listaMateriales.getDescripcion());
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA LISTAMATERIALES
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query = "";
		ListaMateriales listaMateriales = null;
		ArrayList<Object> arrayListaMateriales = new ArrayList<Object>();
		if (campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query = "SELECT * FROM listamateriales ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);  
				
				while (resultSet.next()) {
					listaMateriales = new ListaMateriales();
					listaMateriales.setSysPk(resultSet.getInt(1));
					listaMateriales.setDisenoFk(resultSet.getInt(2));
					listaMateriales.setResponsableFk(resultSet.getInt(3));
					listaMateriales.setDescripcion((resultSet.getString(4)));
					arrayListaMateriales.add(listaMateriales);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else {
			query = "SELECT * FROM listamateriales WHERE " + campoBusqueda  + " = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				while (resultSet.next()) {
					listaMateriales = new ListaMateriales();
					listaMateriales.setSysPk(resultSet.getInt(1));
					listaMateriales.setDisenoFk(resultSet.getInt(2));
					listaMateriales.setResponsableFk(resultSet.getInt(3));
					listaMateriales.setDescripcion((resultSet.getString(4)));
					arrayListaMateriales.add(listaMateriales);
				}//FIN WHILE
			}catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		}//FIN IF/ELSE
		return arrayListaMateriales;
	}//FIN METODO		
	
	//METODO PARA HACER UPDATE EN LA TABLA LISTAMATERIALES
	@Override
	public boolean modificar(Connection connection, Object ListaMateriales) {
		String query = "UPDATE listamateriales "
				+ "SET  diseno = ?, responsable = ?, descripcion = ? "
				+ "WHERE sysPK = ?";
		try {
			ListaMateriales listaMateriales = (ListaMateriales)ListaMateriales;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, listaMateriales.getDisenoFk());
			preparedStatement.setInt(2, listaMateriales.getResponsableFk());
			preparedStatement.setString(3, listaMateriales.getDescripcion());
			preparedStatement.setInt(5, listaMateriales.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER DELETE EN LA TABLA LISTAMATERIALES
	@Override
	public boolean eliminar(Connection connection, Object ListaMateriales) {
		String query = "DELETE FROM listamateriales WHERE sysPK = ?";
		try {	
			ListaMateriales listaMateriales = (ListaMateriales)ListaMateriales;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, listaMateriales.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO				
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public int ultimoSysPk(Connection connection) {
		String query = "SELECT sysPK FROM listamateriales order by sysPK asc";
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