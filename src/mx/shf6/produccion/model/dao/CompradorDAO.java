package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Comprador;
import mx.shf6.produccion.utilities.Notificacion;

public class CompradorDAO {
		
	//METODO PARA CREAR UN REGISTRO
	public static boolean createCromprador(Connection connection, Comprador comprador) {
		String consulta = "INSERT INTO compradores (Nombre, Correo, Telefono, TelefonoAuxiliar, AreaDepartamento, ClienteFK) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, comprador.getNombre());
			sentenciaPreparada.setString(2, comprador.getCorreo());
			sentenciaPreparada.setString(3, comprador.getTelefono());
			sentenciaPreparada.setString(4, comprador.getTelefonoAuxiliar());
			sentenciaPreparada.setString(5, comprador.getAreaDepartamento());
			sentenciaPreparada.setInt(6, comprador.getClienteFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY CATCH
	}//FIN METODO
	
	public static ArrayList<Comprador> readComprador(Connection connection) {
		ArrayList<Comprador> arrayListComprador = new ArrayList<Comprador>();
		String consulta = "SELECT Sys_PK Nombre, Correo, Telefono, TelefonoAuxiliar, AreaDepartamento, ClienteFK FROM compradores";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while(resultados.next()) {
				Comprador comprador = new Comprador();
				comprador.setSysPK(resultados.getInt(1));
				comprador.setNombre(resultados.getString(2));
				comprador.setCorreo(resultados.getString(3));
				comprador.setTelefono(resultados.getString(4));
				comprador.setTelefonoAuxiliar(resultados.getString(5));
				comprador.setAreaDepartamento(resultados.getString(6));
				comprador.setClienteFK(resultados.getInt(7));
				arrayListComprador.add(comprador);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return arrayListComprador;
	}//FIN METODO
	
	public static ArrayList<Comprador> readCompradorNombre(Connection connection, String like) {
		ArrayList<Comprador> arrayListComprador = new ArrayList<Comprador>();
		String consulta = "SELECT Sys_PK Nombre, Correo, Telefono, TelefonoAuxiliar, AreaDepartamento, ClienteFK FROM compradores WHERE Nombre LIKE '%" + like;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while(resultados.next()) {
				Comprador comprador = new Comprador();
				comprador.setSysPK(resultados.getInt(1));
				comprador.setNombre(resultados.getString(2));
				comprador.setCorreo(resultados.getString(3));
				comprador.setTelefono(resultados.getString(4));
				comprador.setTelefonoAuxiliar(resultados.getString(5));
				comprador.setAreaDepartamento(resultados.getString(6));
				comprador.setClienteFK(resultados.getInt(7));
				arrayListComprador.add(comprador);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return arrayListComprador;
	}//FIN METODO
	
	public static Comprador readCompradorSys(Connection connection, int sysPK) {
		Comprador comprador = new Comprador();
		String consulta = "SELECT Sys_PK Nombre, Correo, Telefono, TelefonoAuxiliar, AreaDepartamento, ClienteFK FROM compradores WHERE Sys_PK = " + sysPK ;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while(resultados.next()) {
				comprador.setSysPK(resultados.getInt(1));
				comprador.setNombre(resultados.getString(2));
				comprador.setCorreo(resultados.getString(3));
				comprador.setTelefono(resultados.getString(4));
				comprador.setTelefonoAuxiliar(resultados.getString(5));
				comprador.setAreaDepartamento(resultados.getString(6));
				comprador.setClienteFK(resultados.getInt(7));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return comprador;
	}//FIN METODO
	
	public static Comprador readCompradorClienteFK(Connection connection, int clienteFK) {
		Comprador comprador = new Comprador();
		String consulta = "SELECT Sys_PK, Nombre, Correo, Telefono, TelefonoAuxiliar, AreaDepartamento, ClienteFK FROM compradores WHERE ClienteFK = " + clienteFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while(resultados.next()) {
				comprador.setSysPK(resultados.getInt(1));
				comprador.setNombre(resultados.getString(2));
				comprador.setCorreo(resultados.getString(3));
				comprador.setTelefono(resultados.getString(4));
				comprador.setTelefonoAuxiliar(resultados.getString(5));
				comprador.setAreaDepartamento(resultados.getString(6));
				comprador.setClienteFK(resultados.getInt(7));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return comprador;
	}//FIN METODO
	
	public static boolean updateComprador(Connection connection, Comprador comprador) {
		String consulta = "UPDATE compradores SET Nombre = ?, Correo = ?, Telefono = ?, TelefonoAuxiliar = ?, AreaDepartamento = ?, ClienteFK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, comprador.getNombre());
			sentenciaPreparada.setString(2, comprador.getCorreo());
			sentenciaPreparada.setString(3, comprador.getTelefono());
			sentenciaPreparada.setString(4, comprador.getTelefonoAuxiliar());
			sentenciaPreparada.setString(5, comprador.getAreaDepartamento());
			sentenciaPreparada.setInt(6, comprador.getClienteFK());
			sentenciaPreparada.execute();
			return true;
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY CATCH
	}//FIN METODO
	
	public static boolean deleteComprador(Connection connection, Comprador comprador) {
		String consulta = "DELETE FROM compradores WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, comprador.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FINAL TRY CATCH
	}//FIN METODO
}//FIN CLASE
