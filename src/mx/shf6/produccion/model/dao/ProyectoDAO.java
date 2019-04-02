package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Diseno;
import mx.shf6.produccion.utilities.Notificacion;

public class DisenoDAO implements ObjectDAO{

	//METODO PARA HACER CREATE EN LA TABLA DISEÑOS
	@Override
	public boolean crear(Connection connection, Object Diseno) {	
		Diseno diseno = (Diseno)Diseno;
		String query = "INSERT INTO disenos (archivoDiseno, numeroParte, archivoEspecificacionT, clienteFk) "
				+ "values ( ?, ?, ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, diseno.getArchivoDiseno());
			preparedStatement.setString(2, diseno.getNumeroParte());
			preparedStatement.setString(3, diseno.getArchivoEspecificacionT());
			preparedStatement.setInt(4, diseno.getClienteFk());
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA DISEÑOS
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query = "";
		ArrayList<Object> listaDiseno = new ArrayList<Object>();
		if (campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query = "SELECT * FROM disenos ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);  
				Diseno diseno = null;
				while (resultSet.next()) {
					diseno = new Diseno();
					diseno.setSysPk(resultSet.getInt(1));
					diseno.setArchivoDiseno(resultSet.getString(2));
					diseno.setNumeroParte(resultSet.getString(3));
					diseno.setArchivoEspecificacionT(resultSet.getString(4));
					listaDiseno.add(diseno);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else {
			query = "SELECT * FROM disenos WHERE "+campoBusqueda+" = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				Diseno diseno = null;
				while (resultSet.next()) {
					diseno = new Diseno();
					diseno.setSysPk(resultSet.getInt(1));
					diseno.setArchivoDiseno(resultSet.getString(2));
					diseno.setNumeroParte(resultSet.getString(3));
					diseno.setArchivoEspecificacionT(resultSet.getString(4));
					listaDiseno.add(diseno);
				}//FIN WHILE
			}catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		}//FIN IF/ELSE
		return listaDiseno;
	}//FIN METODO	
	
	//METODO PARA HACER UPDATE EN LA TABLA DISEÑOS
	@Override
	public boolean modificar(Connection connection, Object Diseno) {
		String query = "UPDATE disenos "
				+ "SET  archivoDiseno = ?, numeroParte = ?, archivoEspecificacionT = ?, clienteFk = ? "
				+ "WHERE sysPK = ?";
		try {
			Diseno diseno = (Diseno)Diseno;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, diseno.getArchivoDiseno());
			preparedStatement.setString(2, diseno.getNumeroParte());
			preparedStatement.setString(3, diseno.getArchivoEspecificacionT());
			preparedStatement.setInt(4, diseno.getClienteFk());
			preparedStatement.setInt(5, diseno.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER DELETE EN LA TABLA DISEÑOS
	@Override
	public boolean eliminar(Connection connection, Object Diseno) {
		String query = "DELETE FROM disenos WHERE sysPK = ?";
		try {	
			Diseno diseno = (Diseno)Diseno;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, diseno.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO		
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public int ultimoSysPk(Connection connection) {
		String query = "SELECT sysPK FROM disenos order by sysPK asc";
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
