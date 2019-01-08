package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Solicitud;
import mx.shf6.produccion.utilities.Notificacion;

public class SolicitudDAO implements ObjectDAO{

	//METODO PARA HACER CREATE EN LA TABLA SOLICITUDES
	@Override
	public boolean crear(Connection connection, Object Solicitud) {	
		Solicitud solicitud = (Solicitud)Solicitud;
		String query = "INSERT INTO solicitudes (fecha, status, notasGenerales, clienteFk) "
				+ "values ( CURDATE(), ?, ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, solicitud.getStatus());
			preparedStatement.setString(2, solicitud.getNotasGenerales());
			preparedStatement.setInt(3, solicitud.getClienteFk());
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA SOLICITUDES
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query = "";
		Solicitud solicitud = null;
		ArrayList<Object> listaSolicitud = new ArrayList<Object>();
		if (campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query = "SELECT * FROM solicitudes ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);  
				
				while (resultSet.next()) {
					solicitud = new Solicitud();
					solicitud.setSysPk(resultSet.getInt(1));
					solicitud.setFecha(resultSet.getDate(2));
					solicitud.setStatus(resultSet.getInt(3));;
					solicitud.setNotasGenerales(resultSet.getString(4));
					solicitud.setClienteFk(resultSet.getInt(5));
					listaSolicitud.add(solicitud);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else {
			query = "SELECT * FROM solicitudes WHERE " + campoBusqueda  +" = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				while (resultSet.next()) {
					solicitud = new Solicitud();
					solicitud.setSysPk(resultSet.getInt(1));
					solicitud.setFecha(resultSet.getDate(2));
					solicitud.setStatus(resultSet.getInt(3));;
					solicitud.setNotasGenerales(resultSet.getString(4));
					solicitud.setClienteFk(resultSet.getInt(5));
					listaSolicitud.add(solicitud);
				}//FIN WHILE
			}catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		}//FIN IF/ELSE
		return listaSolicitud;
	}//FIN METODO	
	
	//METODO PARA HACER UPDATE EN LA TABLA SOLICITUDES
	@Override
	public boolean modificar(Connection connection, Object Solicitud) {
		String query = "UPDATE solicitudes "
				+ "SET  fecha = ?, status = ?, notasGenerales = ?, clienteFk = ? "
				+ "WHERE sysPK = ?";
		try {
			Solicitud solicitud = (Solicitud)Solicitud;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setDate(1, solicitud.getFecha());
			preparedStatement.setInt(2, solicitud.getStatus());
			preparedStatement.setString(3, solicitud.getNotasGenerales());
			preparedStatement.setInt(4, solicitud.getClienteFk());
			preparedStatement.setInt(5, solicitud.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER DELETE EN LA TABLA SOLICITUDES
	@Override
	public boolean eliminar(Connection connection, Object Solicitud) {
		String query = "DELETE FROM solicitudes WHERE sysPK = ?";
		try {	
			Solicitud solicitud = (Solicitud)Solicitud;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, solicitud.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO		
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public int ultimoSysPk(Connection connection) {
		String query = "SELECT sysPK FROM solicitudes order by sysPK asc";
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