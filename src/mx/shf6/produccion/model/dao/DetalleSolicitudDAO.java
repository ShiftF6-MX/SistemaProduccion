package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.DetalleSolicitud;
import mx.shf6.produccion.utilities.Notificacion;

public class DetalleSolicitudDAO implements ObjectDAO {

	//METODO PARA HACER INSET INTO  EN LA TABLA "DETALLESOLICITUDES"
	@Override
	public boolean crear(Connection connection, Object DetalleSolicitud) {	
		DetalleSolicitud detalleSolicitud = (DetalleSolicitud)DetalleSolicitud;
		String query = "INSERT INTO detallesolicitudes (cantidad, fechaEntrega, disenoFk, solicitudFk) "
				+ "values ( ?, ?, ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, detalleSolicitud.getCantidad());
			preparedStatement.setDate(2, detalleSolicitud.getFechaEntrega());
			preparedStatement.setInt(3, detalleSolicitud.getDisenoFk());
			preparedStatement.setInt(4, detalleSolicitud.getSolicitudFk());
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA DETALLESOLICITUDES
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query = "";
		DetalleSolicitud detalleSolicitud = null;
		ArrayList<Object> listaDetalleSolicitud = new ArrayList<Object>();
		if (campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query = "SELECT * FROM detallesolicitudes ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);  				
				while (resultSet.next()) {
					detalleSolicitud = new DetalleSolicitud();
					detalleSolicitud.setSysPk(resultSet.getInt(1));
					detalleSolicitud.setCantidad(resultSet.getInt(2));
					detalleSolicitud.setFechaEntrega(resultSet.getDate(3));
					detalleSolicitud.setDisenoFk(resultSet.getInt(4));
					detalleSolicitud.setSolicitudFk(resultSet.getInt(5));
					listaDetalleSolicitud.add(detalleSolicitud);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else {
			query = "SELECT * FROM detallesolicitudes WHERE " + campoBusqueda  + " = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				while (resultSet.next()) {
					detalleSolicitud = new DetalleSolicitud();
					detalleSolicitud.setSysPk(resultSet.getInt(1));
					detalleSolicitud.setCantidad(resultSet.getInt(2));
					detalleSolicitud.setFechaEntrega(resultSet.getDate(3));
					detalleSolicitud.setDisenoFk(resultSet.getInt(4));
					detalleSolicitud.setSolicitudFk(resultSet.getInt(5));
					listaDetalleSolicitud.add(detalleSolicitud);
				}//FIN WHILE
			}catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		}//FIN IF/ELSE
		return listaDetalleSolicitud;
	}//FIN METODO	
	
	//METODO PARA HACER UPDATE EN LA TABLA DETALLESOLICITUDES
	@Override
	public boolean modificar(Connection connection, Object DetalleSolicitud) {
		String query = "UPDATE detallesolicitudes "
				+ "SET  cantidad = ?, fechaEntrega = ?, disenoFk = ?, solicitudFk = ? "
				+ "WHERE sysPK = ?";
		try {
			DetalleSolicitud detalleSolicitud = (DetalleSolicitud)DetalleSolicitud;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, detalleSolicitud.getCantidad());
			preparedStatement.setDate(2, detalleSolicitud.getFechaEntrega());
			preparedStatement.setInt(3, detalleSolicitud.getDisenoFk());
			preparedStatement.setInt(4, detalleSolicitud.getSolicitudFk());
			preparedStatement.setInt(5, detalleSolicitud.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER DELETE EN LA TABLA DETALLESOLICITUDES
	@Override
	public boolean eliminar(Connection connection, Object DetalleSolicitud) {
		String query = "DELETE FROM detallesolicitudes WHERE sysPK = ?";
		try {	
			DetalleSolicitud detalleSolicitud = (DetalleSolicitud)DetalleSolicitud;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, detalleSolicitud.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO		
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public int ultimoSysPk(Connection connection) {
		String query = "SELECT sysPK FROM detallesolicitudes order by sysPK asc";
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