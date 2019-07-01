package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import mx.shf6.produccion.model.OrdenProduccion;
import mx.shf6.produccion.utilities.Notificacion;

public class OrdenProduccionDAO {

	//METODO PARA CREAR UN REGISTRO
	public static boolean createOrdenProduccion(Connection connection, OrdenProduccion ordenProduccion) {
		String consulta = "INSERT INTO ordenesproduccion (Fecha, Lote, Status, DetalleCotizacionFK) VALUES (CURDATE(),?,0,?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, ordenProduccion.getLote());
			sentenciaPreparada.setInt(2, ordenProduccion.getDetalleCotizacionFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<OrdenProduccion> readLoteProduccion(Connection connection) {
		ArrayList<OrdenProduccion> arrayListaOrdenProduccion = new ArrayList<OrdenProduccion>();
		String consulta = "SELECT Sys_PK, Fecha, Lote, Status, DetalleCotizacionFK FROM ordenesproduccion";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				OrdenProduccion orden = new OrdenProduccion();
				orden.setSysPK(resultados.getInt(1));
				orden.setFecha(resultados.getDate(2));
				orden.setLote(resultados.getString(3));
				orden.setStatus(resultados.getInt(4));
				orden.setDetalleCotizacionFK(resultados.getInt(5));
				arrayListaOrdenProduccion.add(orden);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return arrayListaOrdenProduccion;
	}//FIN METODO
	
	public static OrdenProduccion searchOrdenProduccion(Connection connection, int detalleCotizacionFK) {
		OrdenProduccion ordenProduccion = new OrdenProduccion();
		String consulta = " SELECT Sys_PK, Fecha, Lote, Status, DetalleCotizacionFK FROM ordenesproduccion WHERE DetalleCotizacionFK = " + detalleCotizacionFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				ordenProduccion.setSysPK(resultados.getInt(1));
				ordenProduccion.setFecha(resultados.getDate(2));
				ordenProduccion.setLote(resultados.getString(3));
				ordenProduccion.setStatus(resultados.getInt(4));
				ordenProduccion.setDetalleCotizacionFK(resultados.getInt(5));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return ordenProduccion;
	}//FIN METODO
	
	public static final int ultimoSysPK(Connection connection) {
		String query = "SELECT Sys_PK FROM ordenesproduccion order by Sys_PK asc";
		int ultimoSyspk = 0;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				ultimoSyspk = resultSet.getInt(1);
			}//FIN WHILE
		} catch (SQLException ex) {
			System.out.println("No hay syspk " + ex);
		}//FIN TRY CATCH
		return ultimoSyspk;
	}//FIN METODO
	
	
}//FIN CLASE
