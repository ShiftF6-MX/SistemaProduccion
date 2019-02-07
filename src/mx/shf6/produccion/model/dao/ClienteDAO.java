package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.utilities.Notificacion;



public class ClienteDAO implements ObjectDAO{

	//METODO PARA HACER CREATE EN LA TABLA CLIENTE
	@Override
	public boolean crear(Connection connection, Object Cliente) {	
		Cliente cliente = (Cliente)Cliente;
		String query = "INSERT INTO clientes (codigo, nombre, status, fechaRegistro, registroContrubuyente, telefono, correo, rutaCarpeta, domicilioFk) "
				+ "values ( ?, ?, ?, CURDATE(), ?, ?, ?, ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, cliente.getCodigo());
			preparedStatement.setString(2, cliente.getNombre());
			preparedStatement.setInt(3, cliente.getStatus());
			preparedStatement.setString(4, cliente.getRegistroContribuyente());
			preparedStatement.setString(5, cliente.getTelefono());
			preparedStatement.setString(6, cliente.getCorreo());
			preparedStatement.setString(7, cliente.getRutaCarpeta());
			preparedStatement.setInt(8, cliente.getDomicilioFk());
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA CLIENTES
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query ="";
		Cliente cliente = new Cliente();
		ArrayList<Object> listaCliente = new ArrayList<Object>();
		if(campoBusqueda.isEmpty() && valorBusqueda.isEmpty()) {
			query="SELECT * FROM clientes ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
				while (resultSet.next()) {
					cliente = new Cliente();			
					cliente.setSysPk(resultSet.getInt(1));
					cliente.setCodigo(resultSet.getString(2));
					cliente.setNombre(resultSet.getString(3));
					cliente.setStatus(resultSet.getInt(4));
					cliente.setFechaRegistro(resultSet.getDate(5));
					cliente.setRegistroContribuyente(resultSet.getString(6));
					cliente.setTelefono(resultSet.getString(7));
					cliente.setCorreo(resultSet.getString(8));
					cliente.setRutaCarpeta(resultSet.getString(9));
					cliente.setDomicilioFk(resultSet.getInt(10));
					listaCliente.add(cliente);
				}//FIN WHILE
			}catch (SQLException e) {
				Notificacion.dialogoException(e);
			}//FIN TRY/CATCH
		}else {
			if(campoBusqueda.isEmpty()) {
				query="SELECT * FROM clientes "
						+ "WHERE (nombre like '%" + valorBusqueda + "%' "
						+ "OR registroContribuyente like '%" + valorBusqueda + "%') ORDER BY sysPK;";	
				try {
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery(query);
					while (resultSet.next()) {
						cliente = new Cliente();			
						cliente.setSysPk(resultSet.getInt(1));
						cliente.setCodigo(resultSet.getString(2));
						cliente.setNombre(resultSet.getString(3));
						cliente.setStatus(resultSet.getInt(4));
						cliente.setFechaRegistro(resultSet.getDate(5));
						cliente.setRegistroContribuyente(resultSet.getString(6));
						cliente.setTelefono(resultSet.getString(7));
						cliente.setCorreo(resultSet.getString(8));
						cliente.setRutaCarpeta(resultSet.getString(9));
						cliente.setDomicilioFk(resultSet.getInt(10));
						listaCliente.add(cliente);
					}//FIN WHILE
				}catch (SQLException e) {
					Notificacion.dialogoException(e);
				}//FIN TRY/CATCH
			}else {
				query="SELECT * FROM clientes WHERE " + campoBusqueda + " = ? ORDER BY sysPK;";	
				try {
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, valorBusqueda);
					ResultSet resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						cliente = new Cliente();			
						cliente.setSysPk(resultSet.getInt(1));
						cliente.setCodigo(resultSet.getString(2));
						cliente.setNombre(resultSet.getString(3));
						cliente.setStatus(resultSet.getInt(4));
						cliente.setFechaRegistro(resultSet.getDate(5));
						cliente.setRegistroContribuyente(resultSet.getString(6));
						cliente.setTelefono(resultSet.getString(7));
						cliente.setCorreo(resultSet.getString(8));
						cliente.setRutaCarpeta(resultSet.getString(9));
						cliente.setDomicilioFk(resultSet.getInt(10));
						listaCliente.add(cliente);
					}//FIN WHILE
				}catch (SQLException e) {
					Notificacion.dialogoException(e);
				}//FIN TRY-CATCH
			}//FIN IF-ELSE		
		}//FIN IF-ELSE
		return listaCliente;
	}//FIN METODO	
	
	//METODO PARA HACER UPDATE EN LA TABLA CLIENTES
	@Override
	public boolean modificar(Connection connection, Object Cliente) {
		String query = "UPDATE clientes "
				+ "SET  codigo = ?, nombre = ?, status = ?, registroContribuyente = ?, telefono = ?, correo = ?, rutaCarpeta = ?, domicilioFk = ? "
				+ "WHERE sysPK = ?";
		try {
			Cliente cliente = (Cliente)Cliente;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, cliente.getCodigo());
			preparedStatement.setString(2, cliente.getNombre());
			preparedStatement.setInt(3, cliente.getStatus());
			preparedStatement.setString(4, cliente.getRegistroContribuyente());
			preparedStatement.setString(5, cliente.getTelefono());
			preparedStatement.setString(6, cliente.getCorreo());
			preparedStatement.setString(7, cliente.getRutaCarpeta());
			preparedStatement.setInt(8, cliente.getDomicilioFk());
			preparedStatement.setInt(9, cliente.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER DELETE EN LA TABLA CLIENTES
	@Override
	public boolean eliminar(Connection connection, Object Cliente) {
		String query = "DELETE FROM clientes WHERE sysPK = ?";
		try {	
			Cliente cliente = (Cliente)Cliente;
			DomicilioDAO domicilioDAO = new DomicilioDAO();
			if (domicilioDAO.eliminar(connection, cliente.getDomicilio(connection))){
				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
				preparedStatement.setInt(1, cliente.getSysPk());
				preparedStatement.execute();
				return true;
			}
			else
				return false;
			
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO		
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public int ultimoSysPk(Connection connection) {
		String query = "SELECT sysPK FROM clientes order by sysPK asc";
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
