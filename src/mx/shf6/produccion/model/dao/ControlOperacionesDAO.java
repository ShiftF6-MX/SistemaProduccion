package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.ControlOperacion;
import mx.shf6.produccion.utilities.Notificacion;

public class ControlOperacionesDAO {
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean createBitacoraNumeroSerie(Connection connection, ControlOperacion controlOperacion) {
		String consulta = "INSERTE INTO bitacorasNumeroSerie (HoraInicio, FechaInicio, HoraFinal, FechaFinal, ProcesoFK, DetalleProcesoFK, DetalleLoteProduccionFK) VALUES (?,?,?,?,?,?,?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, controlOperacion.getCantidad());
			sentenciaPreparada.setDate(2, controlOperacion.getHoraFechaInicio());
			sentenciaPreparada.setDate(3, controlOperacion.getHoraFechaFinal());
			sentenciaPreparada.setInt(4, controlOperacion.getCentroTrabajo());
			sentenciaPreparada.setInt(5, controlOperacion.getCodigoParoFK());
			sentenciaPreparada.setInt(6, controlOperacion.getComponenteFK());
			sentenciaPreparada.setInt(7, controlOperacion.getDetalleProcesoFK());
			sentenciaPreparada.setInt(8, controlOperacion.getDetalleOrdenProduccionFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY CATCH
	}//FIN METODO
	
	public static ArrayList<ControlOperacion> readBitacoraNumeroSerie(Connection connection) {
		ArrayList<ControlOperacion> listaControlOperaciones = new ArrayList<ControlOperacion>();
		String consulta = "";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				ControlOperacion operacion = new ControlOperacion();
				operacion.setSysPK(resultados.getInt(1));
				operacion.setCantidad(resultados.getInt(2));
				operacion.setHoraFechaInicio(resultados.getDate(3));
				operacion.setHoraFechaFinal(resultados.getDate(4));
				operacion.setCentroTrabajoFK(resultados.getInt(5));
				operacion.setCodigoParo(resultados.getInt(6));
				operacion.setComponenteFK(resultados.getInt(7));
				operacion.setDetalleProcesoFK(resultados.getInt(8));
				operacion.setDetalleOrdenProduccionFK(resultados.getInt(9));
				listaControlOperaciones.add(operacion);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return listaControlOperaciones;
	}//FIN METODO
	
	public static ArrayList<ControlOperacion> readLote(Connection connection, String lote) {
		ArrayList<ControlOperacion> listaControlOperacionesLote = new ArrayList<ControlOperacion>();
		String consulta = "SELECT controloperaciones.Sys_PK, controloperaciones.Cantidad, MAX(controloperaciones.HoraFechaInicio) AS fecha, controloperaciones.HoraFechaFinal, controloperaciones.CentroTrabajoFK, controloperaciones.CodigoParoFK, controloperaciones.ComponenteFK, controloperaciones.DetalleProcesoFK, controloperaciones.DetalleOrdenProduccionFK, detalleordenesproduccion.NumeroSerie, ordenesproduccion.Lote FROM controloperaciones INNER JOIN detalleordenesproduccion ON controloperaciones.DetalleOrdenProduccionFK = detalleordenesproduccion.Sys_PK INNER JOIN ordenesproduccion ON detalleordenesproduccion.OrdenProduccionFK = ordenesproduccion.Sys_PK WHERE ordenesproduccion.Lote = '" + lote + "' GROUP BY DetalleOrdenProduccionFK";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				ControlOperacion operacion = new ControlOperacion();
				operacion.setSysPK(resultados.getInt(1));
				operacion.setCantidad(resultados.getInt(2));
				operacion.setHoraFechaInicio(resultados.getDate(3));
				operacion.setHoraFechaFinal(resultados.getDate(4));
				operacion.setCentroTrabajoFK(resultados.getInt(5));
				operacion.setCodigoParo(resultados.getInt(6));
				operacion.setComponenteFK(resultados.getInt(7));
				operacion.setDetalleProcesoFK(resultados.getInt(8));
				operacion.setDetalleOrdenProduccionFK(resultados.getInt(9));
				operacion.setNumeroSerie(resultados.getString(10));
				operacion.setNumeroLote(resultados.getString(11));
				listaControlOperacionesLote.add(operacion);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return listaControlOperacionesLote;
	}//FIN METODO
	
}//FIN CLASE
