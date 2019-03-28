package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.utilities.Notificacion;

public class ClienteDAO{

	//METODO PARA CREAR UN REGISTRO
	public static boolean createCliente(Connection connection, Cliente cliente) {
		String consulta = "INSERT INTO clientes (Codigo, Nombre, Status, FechaRegistro, RegistroContribuyente, Telefono, Correo, RutaCarpeta, DomicilioFK) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, cliente.getCodigo());
			sentenciaPreparada.setString(2, cliente.getNombre());
			sentenciaPreparada.setInt(3, cliente.getStatus());
			sentenciaPreparada.setDate(4, cliente.getFechaRegistro());
			sentenciaPreparada.setString(5, cliente.getRegistroContribuyente());
			sentenciaPreparada.setString(6, cliente.getTelefono());
			sentenciaPreparada.setString(7, cliente.getCorreo());
			sentenciaPreparada.setString(8, cliente.getRutaCarpeta());
			sentenciaPreparada.setInt(9, cliente.getDomicilioFk());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Cliente> readCliente(Connection connection) {
		ArrayList<Cliente> arrayListCliente = new ArrayList<Cliente>();
		String consulta = "SELECT Sys_PK, Codigo, Nombre, Status, FechaRegistro, RegistroContribuyente, Telefono, Correo, RutaCarpeta, DomicilioFK FROM clientes";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Cliente cliente = new Cliente();
				cliente.setSysPk(resultados.getInt(1));
				cliente.setCodigo(resultados.getString(2));
				cliente.setNombre(resultados.getString(3));
				cliente.setStatus(resultados.getInt(4));
				cliente.setFechaRegistro(resultados.getDate(5));
				cliente.setRegistroContribuyente(resultados.getString(6));
				cliente.setTelefono(resultados.getString(7));
				cliente.setCorreo(resultados.getString(8));
				cliente.setDomicilioFk(resultados.getInt(9));
				arrayListCliente.add(cliente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListCliente;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static Cliente readCliente(Connection connection, int sysPK) {
		Cliente cliente = new Cliente();
		String consulta = "SELECT Sys_PK, Codigo, Nombre, Status, FechaRegistro, RegistroContribuyente, Telefono, Correo, RutaCarpeta, DomicilioFK FROM clientes WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				cliente.setSysPk(resultados.getInt(1));
				cliente.setCodigo(resultados.getString(2));
				cliente.setNombre(resultados.getString(3));
				cliente.setStatus(resultados.getInt(4));
				cliente.setFechaRegistro(resultados.getDate(5));
				cliente.setRegistroContribuyente(resultados.getString(6));
				cliente.setTelefono(resultados.getString(7));
				cliente.setCorreo(resultados.getString(8));
				cliente.setDomicilioFk(resultados.getInt(9));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return cliente;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Cliente> readCliente(Connection connection, String like) {
		ArrayList<Cliente> arrayListCliente = new ArrayList<Cliente>();
		String consulta = "SELECT Sys_Pk, Codigo, Nombre, Status, FechaRegistro, RegistroContribuyente, Telefono, Correo, RutaCarpeta, DomicilioFK FROM clientes WHERE Nombre LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Cliente cliente = new Cliente();
				cliente.setSysPk(resultados.getInt(1));
				cliente.setCodigo(resultados.getString(2));
				cliente.setNombre(resultados.getString(3));
				cliente.setStatus(resultados.getInt(4));
				cliente.setFechaRegistro(resultados.getDate(5));
				cliente.setRegistroContribuyente(resultados.getString(6));
				cliente.setTelefono(resultados.getString(7));
				cliente.setCorreo(resultados.getString(8));
				cliente.setDomicilioFk(resultados.getInt(9));
				arrayListCliente.add(cliente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListCliente;
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateCliente(Connection connection, Cliente cliente) {
		String consulta = "UPDATE clientes SET Codigo = ?, Nombre = ?, Status = ?, FechaRegistro = ?, RegistroContribuyente = ?, Telefono = ?, Correo = ?, RutaCarpeta = ?, DomicilioFK = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, cliente.getCodigo());
			sentenciaPreparada.setString(2, cliente.getNombre());
			sentenciaPreparada.setInt(3, cliente.getStatus());
			sentenciaPreparada.setDate(4, cliente.getFechaRegistro());
			sentenciaPreparada.setString(5, cliente.getRegistroContribuyente());
			sentenciaPreparada.setString(6, cliente.getTelefono());
			sentenciaPreparada.setString(7, cliente.getCorreo());
			sentenciaPreparada.setString(8, cliente.getRutaCarpeta());
			sentenciaPreparada.setInt(9, cliente.getDomicilioFk());
			sentenciaPreparada.setInt(10, cliente.getSysPk());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteCliente(Connection connection, Cliente cliente) {
		String consulta = "DELETE FROM clientes WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, cliente.getSysPk());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<Cliente> toObservableList(ArrayList<Cliente> arrayList) {
		ObservableList<Cliente> listaObservableCliente = FXCollections.observableArrayList();
		for (Cliente cliente : arrayList) 
			listaObservableCliente.add(cliente);
		return listaObservableCliente;
	}//FIN METODO
	
}//FIN CLASE
