package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.Cotizacion;
import mx.shf6.produccion.utilities.Notificacion;

public class CotizacionDAO{

	//METODO PARA CREAR UN REGISTRO
	public static boolean createCotizacion(Connection connection, Cotizacion cotizacion) {
		String consulta = "INSERT INTO cotizaciones "
				+ "(Referencia, Fecha, Status, Solicitante, AreaDepartamento, TelefonoFax, "
				+ "Email, TipoServicio, CondicionEmbarque, CondicionPago, Moneda, Tipo Cambio, "
				+ "Observaciones, Vigencia, FolioFK, ClienteFK) "
				+ "VALUES (?, CURDATE(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, cotizacion.getReferencia());
			sentenciaPreparada.setInt(2, cotizacion.getStatus());
			sentenciaPreparada.setString(3, cotizacion.getSolicitante());
			sentenciaPreparada.setString(4, cotizacion.getAreaDepartamento());
			sentenciaPreparada.setString(5, cotizacion.getTelefonoFax());
			sentenciaPreparada.setString(6, cotizacion.getEmail());
			sentenciaPreparada.setString(7, cotizacion.getTipoServicio());
			sentenciaPreparada.setString(8, cotizacion.getCondicionEmbarque());
			sentenciaPreparada.setString(9, cotizacion.getCondicionPago());
			sentenciaPreparada.setInt(10, cotizacion.getMoneda());
			sentenciaPreparada.setDouble(11, cotizacion.getTipoCambio());
			sentenciaPreparada.setString(12, cotizacion.getObservaciones());
			sentenciaPreparada.setString(13, cotizacion.getVigencia());
			sentenciaPreparada.setInt(14, cotizacion.getFolioFK());
			sentenciaPreparada.setInt(15, cotizacion.getClienteFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Cotizacion> readCotizacion(Connection connection) {
		ArrayList<Cotizacion> arrayListCotizacion = new ArrayList<Cotizacion>();
		String consulta = "SELECT Sys_PK, Referencia, Fecha, Status, Solicitante, AreaDepartamento, TelefonoFax, "
				+ "Email, TipoServicio, CondicionEmbarque, CondicionPago, Moneda, TipoCambio, Observaciones, "
				+ "Vigencia, FolioFK, ClienteFK "
				+ "FROM cotizaciones";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Cotizacion cotizacion = new Cotizacion();
				cotizacion.setSysPK(resultados.getInt(1));
				cotizacion.setReferencia(resultados.getString(2));
				cotizacion.setFecha(resultados.getDate(3));
				cotizacion.setStatus(resultados.getInt(4));
				cotizacion.setSolicitante(resultados.getString(5));
				cotizacion.setAreaDepartamento(resultados.getString(6));
				cotizacion.setTelefonoFax(resultados.getString(7));
				cotizacion.setEmail(resultados.getString(8));
				cotizacion.setTipoServicio(resultados.getString(9));
				cotizacion.setCondicionEmbarque(resultados.getString(10));
				cotizacion.setCondicionPago(resultados.getString(11));
				cotizacion.setMoneda(resultados.getInt(12));
				cotizacion.setTipoCambio(resultados.getDouble(13));
				cotizacion.setObservaciones(resultados.getString(14));
				cotizacion.setVigencia(resultados.getString(15));
				cotizacion.setFolioFK(resultados.getInt(16));
				cotizacion.setClienteFK(resultados.getInt(17));
				arrayListCotizacion.add(cotizacion);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListCotizacion;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static Cotizacion readCotizacion(Connection connection, int sysPK) {
		Cotizacion cotizacion = new Cotizacion();
		String consulta = "SELECT Sys_PK, Referencia, Fecha, Status, Solicitante, AreaDepartamento, TelefonoFax, "
				+ "Email, TipoServicio, CondicionEmbarque, CondicionPago, Moneda, TipoCambio, Observaciones, "
				+ "Vigencia, FolioFK, ClienteFK "
				+ "FROM cotizaciones "
				+ "WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				cotizacion.setSysPK(resultados.getInt(1));
				cotizacion.setReferencia(resultados.getString(2));
				cotizacion.setFecha(resultados.getDate(3));
				cotizacion.setStatus(resultados.getInt(4));
				cotizacion.setSolicitante(resultados.getString(5));
				cotizacion.setAreaDepartamento(resultados.getString(6));
				cotizacion.setTelefonoFax(resultados.getString(7));
				cotizacion.setEmail(resultados.getString(8));
				cotizacion.setTipoServicio(resultados.getString(9));
				cotizacion.setCondicionEmbarque(resultados.getString(10));
				cotizacion.setCondicionPago(resultados.getString(11));
				cotizacion.setMoneda(resultados.getInt(12));
				cotizacion.setTipoCambio(resultados.getDouble(13));
				cotizacion.setObservaciones(resultados.getString(14));
				cotizacion.setVigencia(resultados.getString(15));
				cotizacion.setFolioFK(resultados.getInt(16));
				cotizacion.setClienteFK(resultados.getInt(17));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return cotizacion;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Cotizacion> readCotizacion(Connection connection, String like) {
		ArrayList<Cotizacion> arrayListCotizacion = new ArrayList<Cotizacion>();
		String consulta = "SELECT Sys_PK, Referencia, Fecha, Status, Solicitante, AreaDepartamento, TelefonoFax, "
				+ "Email, TipoServicio, CondicionEmbarque, CondicionPago, Moneda, TipoCambio, Observaciones, "
				+ "Vigencia, FolioFK, ClienteFK "
				+ "FROM cotizaciones "
				+ "WHERE Referencia LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Cotizacion cotizacion = new Cotizacion();
				cotizacion.setSysPK(resultados.getInt(1));
				cotizacion.setReferencia(resultados.getString(2));
				cotizacion.setFecha(resultados.getDate(3));
				cotizacion.setStatus(resultados.getInt(4));
				cotizacion.setSolicitante(resultados.getString(5));
				cotizacion.setAreaDepartamento(resultados.getString(6));
				cotizacion.setTelefonoFax(resultados.getString(7));
				cotizacion.setEmail(resultados.getString(8));
				cotizacion.setTipoServicio(resultados.getString(9));
				cotizacion.setCondicionEmbarque(resultados.getString(10));
				cotizacion.setCondicionPago(resultados.getString(11));
				cotizacion.setMoneda(resultados.getInt(12));
				cotizacion.setTipoCambio(resultados.getDouble(13));
				cotizacion.setObservaciones(resultados.getString(14));
				cotizacion.setVigencia(resultados.getString(15));
				cotizacion.setFolioFK(resultados.getInt(16));
				cotizacion.setClienteFK(resultados.getInt(17));
				arrayListCotizacion.add(cotizacion);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListCotizacion;
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateCotizacion(Connection connection, Cotizacion cotizacion) {
		String consulta = "UPDATE clientes SET Referencia = ? Status = ?, Solicitante = ?, "
				+ "AreaDepartamento = ?, TelefonoFax = ?, Email = ?, TipoServicio = ?, "
				+ "CondicionEmbarque = ?, CondicionPago = ?, Moneda = ?, TipoCambio = ?, "
				+ "Observaciones = ?, Vigencia = ?, FolioFK = ?, ClienteFK = ? "
				+ "WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, cotizacion.getReferencia());
			sentenciaPreparada.setInt(2, cotizacion.getStatus());
			sentenciaPreparada.setString(3, cotizacion.getSolicitante());
			sentenciaPreparada.setString(4, cotizacion.getAreaDepartamento());
			sentenciaPreparada.setString(5, cotizacion.getTelefonoFax());
			sentenciaPreparada.setString(6, cotizacion.getEmail());
			sentenciaPreparada.setString(7, cotizacion.getTipoServicio());
			sentenciaPreparada.setString(8, cotizacion.getCondicionEmbarque());
			sentenciaPreparada.setString(9, cotizacion.getCondicionPago());
			sentenciaPreparada.setInt(10, cotizacion.getMoneda());
			sentenciaPreparada.setDouble(11, cotizacion.getTipoCambio());
			sentenciaPreparada.setString(12, cotizacion.getObservaciones());
			sentenciaPreparada.setString(13, cotizacion.getVigencia());
			sentenciaPreparada.setInt(14, cotizacion.getFolioFK());
			sentenciaPreparada.setInt(15, cotizacion.getClienteFK());
			sentenciaPreparada.setInt(16, cotizacion.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteCotizacion(Connection connection, Cotizacion cotizacion) {
		String consulta = "DELETE FROM clientes WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, cotizacion.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<Cotizacion> toObservableList(ArrayList<Cotizacion> arrayList) {
		ObservableList<Cotizacion> listaObservableCotizacion = FXCollections.observableArrayList();
		for (Cotizacion cotizacion : arrayList) 
			listaObservableCotizacion.add(cotizacion);
		return listaObservableCotizacion;
	}//FIN METODO

}//FIN CLASE