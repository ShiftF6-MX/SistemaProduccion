package mx.shf6.produccion.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Domicilio;
import mx.shf6.produccion.utilities.Notificacion;


public class DomicilioDAO implements ObjectDAO {
	
	//METODO PARA HACER CREATE EN LA TABLA DOMICILIOS
	@Override
	public boolean crear(Connection connection, Object domicilio) {	
		Domicilio claseDomicilio=(Domicilio)domicilio;
		String query = "INSERT INTO domicilios (calle, numeroInterior, numeroExterior, colonia, localidad, municipio, estado, codigoPostal) "
				+ "values ( ?, ?, ?, ?, ?, ?, ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, claseDomicilio.getCalle());
			preparedStatement.setString(2, claseDomicilio.getNumeroInterior());
			preparedStatement.setString(3, claseDomicilio.getNumeroExterior());
			preparedStatement.setString(4, claseDomicilio.getColonia());
			preparedStatement.setString(5, claseDomicilio.getLocalidad());
			preparedStatement.setString(6, claseDomicilio.getMunicipio());
			preparedStatement.setString(7, claseDomicilio.getEstado());
			preparedStatement.setString(8, claseDomicilio.getCodigoPostal());
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA DOMICILIOS
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query = "";
		ArrayList<Object> listaDomicilio = new ArrayList<Object>();
		if (campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query = "SELECT * FROM domicilios ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);  
				Domicilio domicilio = null;
				while (resultSet.next()) {
					domicilio=new Domicilio();
					domicilio.setSysPk(Integer.parseInt(resultSet.getString(1)));
					domicilio.setCalle(resultSet.getString(2));
					domicilio.setNumeroInterior(resultSet.getString(3));
					domicilio.setNumeroExterior(resultSet.getString(4));
					domicilio.setColonia(resultSet.getString(5));
					domicilio.setLocalidad(resultSet.getString(6));
					domicilio.setMunicipio(resultSet.getString(7));
					domicilio.setEstado(resultSet.getString(8));
					domicilio.setCodigoPostal(resultSet.getString(9));
					listaDomicilio.add(domicilio);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else {
			query = "SELECT * FROM domicilios WHERE "+campoBusqueda+" = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				Domicilio domicilio = null;
				while (resultSet.next()) {
					domicilio=new Domicilio();
					domicilio.setSysPk(Integer.parseInt(resultSet.getString(1)));
					domicilio.setCalle(resultSet.getString(2));
					domicilio.setNumeroInterior(resultSet.getString(3));
					domicilio.setNumeroExterior(resultSet.getString(4));
					domicilio.setColonia(resultSet.getString(5));
					domicilio.setLocalidad(resultSet.getString(6));
					domicilio.setMunicipio(resultSet.getString(7));
					domicilio.setEstado(resultSet.getString(8));
					domicilio.setCodigoPostal(resultSet.getString(9));
					listaDomicilio.add(domicilio);
				}//FIN WHILE
			}catch (SQLException e) {
				e.printStackTrace();
			}//FIN TRY/CATCH
		}//FIN IF/ELSE
		return listaDomicilio;
	}//FIN METODO	
	
	//METODO PARA HACER UPDATE EN LA TABLA DOMICILIOS
	@Override
	public boolean modificar(Connection connection, Object domicilio) {
		String query = "UPDATE domicilios "
				+ "SET  calle = ?, numeroInterior = ?, numeroExterior = ?, colonia = ?, localidad = ?, municipio = ?, estado = ?, codigoPostal = ? "
				+ "WHERE sysPK = ?";
		try {
			Domicilio claseDomicilio=(Domicilio)domicilio;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, claseDomicilio.getCalle());
			preparedStatement.setString(2, claseDomicilio.getNumeroInterior());
			preparedStatement.setString(3, claseDomicilio.getNumeroExterior());
			preparedStatement.setString(4, claseDomicilio.getColonia());
			preparedStatement.setString(5, claseDomicilio.getLocalidad());
			preparedStatement.setString(6, claseDomicilio.getMunicipio());
			preparedStatement.setString(7, claseDomicilio.getEstado());
			preparedStatement.setString(8, claseDomicilio.getCodigoPostal());
			preparedStatement.setInt(9, claseDomicilio.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER DELETE EN LA TABLA DOMICILIOS
	@Override
	public boolean eliminar(Connection connection, Object domicilio) {
		String query = "DELETE FROM domicilios WHERE sysPK = ?";
		try {	
			Domicilio claseDomicilio=(Domicilio)domicilio;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, claseDomicilio.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO		
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public int ultimoSysPk(Connection connection) {
		String query = "SELECT sysPK FROM domicilios order by sysPK asc";
		int ultimoSysPk = 0;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
				ultimoSysPk=resultSet.getInt(1);
			return ultimoSysPk;
		}catch (SQLException e) {
			System.out.println("Error: En método leer");
			e.printStackTrace();
		}//FIN TRY/CATCH
		return ultimoSysPk;
	}//FIN METODO

}//FIN CLASE
