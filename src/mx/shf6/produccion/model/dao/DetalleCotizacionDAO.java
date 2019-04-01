package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.DetalleCotizacion;
import mx.shf6.produccion.utilities.Notificacion;

public class DetalleCotizacionDAO {

	//METODO PARA CREAR UN REGISTRO
	public static boolean createDetalleCotizacion(Connection connection, DetalleCotizacion detalleCotizacion) {
		String consulta = "INSERT INTO detallecotizaciones (Cantidad, Precio, Costo, FechaEntrega, Observaciones, ProyectoFK, CotizacionFK) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDouble(1, detalleCotizacion.getCantidad());
			sentenciaPreparada.setDouble(2, detalleCotizacion.getPrecio());
			sentenciaPreparada.setDouble(3, detalleCotizacion.getCosto());
			sentenciaPreparada.setDate(4, detalleCotizacion.getFechaEntrega());
			sentenciaPreparada.setString(5, detalleCotizacion.getObservaciones());
			sentenciaPreparada.setInt(6, detalleCotizacion.getProyectoFK());
			sentenciaPreparada.setInt(7, detalleCotizacion.getCotizacionFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<DetalleCotizacion> readCliente(Connection connection) {
		ArrayList<DetalleCotizacion> arrayListDetalleCotizacion = new ArrayList<DetalleCotizacion>();
		String consulta = "SELECT Sys_PK, Cantidad, Precio, Costo, FechaEntrega, Observaciones, ProyectoFK, CotizacionFK "
				+ "FROM detallecotizaciones";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				DetalleCotizacion detalleCotizacion = new DetalleCotizacion();
				detalleCotizacion.setSysPK(resultados.getInt(1));
				detalleCotizacion.setCantidad(resultados.getDouble(2));
				detalleCotizacion.setPrecio(resultados.getDouble(3));
				detalleCotizacion.setCosto(resultados.getDouble(4));
				detalleCotizacion.setFechaEntrega(resultados.getDate(5));
				detalleCotizacion.setObservaciones(resultados.getString(6));
				detalleCotizacion.setProyectoFK(resultados.getInt(7));
				detalleCotizacion.setCotizacionFK(resultados.getInt(8));
				arrayListDetalleCotizacion.add(detalleCotizacion);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDetalleCotizacion;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static Cliente readCliente(Connection connection, int sysPK) {
		Cliente cliente = new Cliente();
		String consulta = "SELECT Sys_PK, Codigo, Nombre, Status, FechaRegistro, RegistroContribuyente, Telefono, Correo, RutaCarpeta, DomicilioFK FROM clientes WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				cliente.setSysPK(resultados.getInt(1));
				cliente.setCodigo(resultados.getString(2));
				cliente.setNombre(resultados.getString(3));
				cliente.setStatus(resultados.getInt(4));
				cliente.setFechaRegistro(resultados.getDate(5));
				cliente.setRegistroContribuyente(resultados.getString(6));
				cliente.setTelefono(resultados.getString(7));
				cliente.setCorreo(resultados.getString(8));
				cliente.setRutaCarpeta(resultados.getString(9));
				cliente.setDomicilioFk(resultados.getInt(10));
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
				cliente.setSysPK(resultados.getInt(1));
				cliente.setCodigo(resultados.getString(2));
				cliente.setNombre(resultados.getString(3));
				cliente.setStatus(resultados.getInt(4));
				cliente.setFechaRegistro(resultados.getDate(5));
				cliente.setRegistroContribuyente(resultados.getString(6));
				cliente.setTelefono(resultados.getString(7));
				cliente.setCorreo(resultados.getString(8));
				cliente.setRutaCarpeta(resultados.getString(9));
				cliente.setDomicilioFk(resultados.getInt(10));
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
			sentenciaPreparada.setInt(10, cliente.getSysPK());
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
			sentenciaPreparada.setInt(1, cliente.getSysPK());
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