package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mx.shf6.produccion.model.Proceso;
import mx.shf6.produccion.utilities.Notificacion;

public class ProcesoDAO {

	//METODO PARA CREAR UN REGISTRO
	public static boolean createProceso(Connection connection, Proceso proceso) {
		String consulta = "INSERT INTO procesos (Fecha, Cantidad, Ordenamiento, Nivel, Destino, ComponenteFK, EmpleadoFK) VALUES (?,?,?,?,?,?,?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDate(1, proceso.getFecha());
			sentenciaPreparada.setInt(2, proceso.getCantidad());
			sentenciaPreparada.setInt(3, proceso.getOrdenamiento());
			sentenciaPreparada.setInt(4, proceso.getNivel());
			sentenciaPreparada.setString(5, proceso.getDestino());
			sentenciaPreparada.setInt(6, proceso.getComponenteFK());
			sentenciaPreparada.setInt(7, proceso.getEmpleadoFK());
			sentenciaPreparada.execute();
			return true;
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY CATCH
	}//FIN METODO REGISTRO

	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Proceso> readProceso(Connection connection) {
		ArrayList<Proceso> arrayListProceso = new ArrayList<Proceso>();
		String consulta = "SELECT Sys_PK, Fecha, Cantidad, Ordenamiento, Nivel, Destino, ComponenteFK, EmpleadoFK FROM procesos";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while(resultados.next()) {
				Proceso proceso = new Proceso();
				proceso.setSysPK(resultados.getInt(1));
				proceso.setFecha(resultados.getDate(2));
				proceso.setCantidad(resultados.getInt(3));
				proceso.setOrdenamiento(resultados.getInt(4));
				proceso.setNivel(resultados.getInt(5));
				proceso.setDestino(resultados.getString(6));
				proceso.setComponenteFK(resultados.getInt(7));
				proceso.setEmpleadoFK(resultados.getInt(8));
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListProceso;
	}//FIN METODO

	//METODO PARA OBTENER UN REGISTRO POR SYSPK
		public static Proceso readProceso(Connection connection, int sysPK) {
			Proceso proceso = new Proceso();
			String consulta = "SELECT Sys_PK, Fecha, Cantidad, Ordenamiento, Nivel, Destino, ComponenteFK,EmpleadoFK FROM procesos WHERE Sys_PK = " + sysPK;
			try {
				Statement sentencia = connection.createStatement();
				ResultSet resultados = sentencia.executeQuery(consulta);
				while(resultados.next()) {
					proceso.setSysPK(resultados.getInt(1));
					proceso.setFecha(resultados.getDate(2));
					proceso.setCantidad(resultados.getInt(3));
					proceso.setOrdenamiento(resultados.getInt(4));
					proceso.setNivel(resultados.getInt(5));
					proceso.setDestino(resultados.getString(6));
					proceso.setComponenteFK(resultados.getInt(7));
					proceso.setEmpleadoFK(resultados.getInt(8));
				}//FIN WHILE
			} catch(SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
			return proceso;
		}//FIN METODO

		//METODO PARA OBTENER UN REGISTRO POR LIKE
		public static ArrayList<Proceso> readProceso(Connection connection, String like) {
			ArrayList<Proceso> arrayListProceso = new ArrayList<Proceso>();
			String consulta = "SELECT Sys_PK, Fecha, Cantidad, Ordenamiento, Nivel, Destino, ComponenteFK, EmpleadoFK FROM procesos WHERE Fecha LIKE '%" + like + "%' OR INNER JOIN   LIKE '%" + like + "%';";
			try {
				Statement sentencia = connection.createStatement();
				ResultSet resultados = sentencia.executeQuery(consulta);
				while(resultados.next()) {
					Proceso proceso = new Proceso();
					proceso.setSysPK(resultados.getInt(1));
					proceso.setFecha(resultados.getDate(2));
					proceso.setCantidad(resultados.getInt(3));
					proceso.setOrdenamiento(resultados.getInt(4));
					proceso.setNivel(resultados.getInt(5));
					proceso.setDestino(resultados.getString(6));
					proceso.setComponenteFK(resultados.getInt(7));
					proceso.setEmpleadoFK(resultados.getInt(8));
				}//FIN WHILE
			} catch(SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
			return arrayListProceso;
		}//FIN METODO

		//METODO PARA ACTUALIZAR UN REGISTRO
		public static boolean updateProceso(Connection connection, Proceso proceso) {
			String consulta = "UPDATE procesos SET Fecha = ?, Cantidad = ?, Ordenamiento = ?, Nivel = ?, Destino = ?, ComponenteFK = ?, EmpleadoFK = ? WHERE Sys_PK = ?";
			try {
				PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
				sentenciaPreparada.setDate(1, proceso.getFecha());
				sentenciaPreparada.setInt(2, proceso.getCantidad());
				sentenciaPreparada.setInt(3, proceso.getOrdenamiento());
				sentenciaPreparada.setInt(4, proceso.getNivel());
				sentenciaPreparada.setString(5, proceso.getDestino());
				sentenciaPreparada.setInt(6, proceso.getComponenteFK());
				sentenciaPreparada.setInt(7, proceso.getEmpleadoFK());
				sentenciaPreparada.setInt(8, proceso.getSysPK());
				sentenciaPreparada.execute();
				return true;
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
				return false;
			}//FIN TRY/CATCH
		}//FIN METODO

		//METODO PARA ELIMINAR UN REGISTRO
		public static boolean deleteProceso(Connection connection, Proceso proceso) {
			String consulta = "DELETE FROM procesos WHERE Sys_PK = ?";
			try {
				PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
				sentenciaPreparada.setInt(1, proceso.getSysPK());
				sentenciaPreparada.execute();
				return true;
			} catch(SQLException ex) {
				Notificacion.dialogoException(ex);
			return false;
			}//FIN TRY/CATCH
		}//FIN METODO



}//FIN CLASE
