package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.DetalleLista;
import mx.shf6.produccion.utilities.Notificacion;

public class DetalleListaDAO implements ObjectDAO{

	//METODO PARA HACER CREATE EN LA TABLA DETALLELISTA
	@Override
	public boolean crear(Connection connection, Object DetalleLista) {	
		DetalleLista detalleLista = (DetalleLista)DetalleLista;
		String query = "INSERT INTO detallelista (diseno, responsable, descripcion) "
				+ "values ( ?, ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, detalleLista.getItem());
			preparedStatement.setInt(2, detalleLista.getCantidad());
			preparedStatement.setString(3, detalleLista.getZona());
			preparedStatement.setInt(4, detalleLista.getNivel());
			preparedStatement.setInt(5, detalleLista.getListaMaterialesFk());
			preparedStatement.setInt(6, detalleLista.getComponenteFk());
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA DETALLELISTA
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query = "";
		DetalleLista detalleLista = null;
		ArrayList<Object> listaDetalleLista = new ArrayList<Object>();
		if (campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query = "SELECT * FROM detallelista ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);  				
				while (resultSet.next()) {
					detalleLista = new DetalleLista();
					detalleLista.setSysPk(resultSet.getInt(1));
					detalleLista.setItem((resultSet.getString(2)));
					detalleLista.setCantidad(resultSet.getInt(3));
					detalleLista.setZona((resultSet.getString(4)));
					detalleLista.setNivel(resultSet.getInt(5));
					detalleLista.setListaMaterialesFk(resultSet.getInt(6));
					detalleLista.setComponenteFk(resultSet.getInt(7));
					listaDetalleLista.add(detalleLista);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else {
			query = "SELECT * FROM detallelista WHERE " + campoBusqueda  + " = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				while (resultSet.next()) {
					detalleLista = new DetalleLista();
					detalleLista.setSysPk(resultSet.getInt(1));
					detalleLista.setItem((resultSet.getString(2)));
					detalleLista.setCantidad(resultSet.getInt(3));
					detalleLista.setZona((resultSet.getString(4)));
					detalleLista.setNivel(resultSet.getInt(5));
					detalleLista.setListaMaterialesFk(resultSet.getInt(6));
					detalleLista.setComponenteFk(resultSet.getInt(7));
					listaDetalleLista.add(detalleLista);
				}//FIN WHILE
			}catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		}//FIN IF/ELSE
		return listaDetalleLista;
	}//FIN METODO		
	
	//METODO PARA HACER UPDATE EN LA TABLA DETALLELISTA
	@Override
	public boolean modificar(Connection connection, Object DetalleLista) {
		String query = "UPDATE detallelista "
				+ "SET  item = ?, cantidad = ?, zona = ?, nivel = ?, listamateriales = ?, parte = ? "
				+ "WHERE sysPK = ?";
		try {
			DetalleLista detalleLista = (DetalleLista)DetalleLista;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, detalleLista.getItem());
			preparedStatement.setInt(2, detalleLista.getCantidad());
			preparedStatement.setString(3, detalleLista.getZona());
			preparedStatement.setInt(4, detalleLista.getNivel());
			preparedStatement.setInt(5, detalleLista.getListaMaterialesFk());
			preparedStatement.setInt(6, detalleLista.getComponenteFk());
			preparedStatement.setInt(7, detalleLista.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER DELETE EN LA TABLA DETALLELISTA
	@Override
	public boolean eliminar(Connection connection, Object DetalleLista) {
		String query = "DELETE FROM detallelista WHERE sysPK = ?";
		try {	
			DetalleLista detalleLista = (DetalleLista)DetalleLista;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, detalleLista.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO				
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public int ultimoSysPk(Connection connection) {
		String query = "SELECT sysPK FROM detallelista order by sysPK asc";
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