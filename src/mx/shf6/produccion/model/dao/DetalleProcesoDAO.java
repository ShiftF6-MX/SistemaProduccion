package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.DetalleProceso;
import mx.shf6.produccion.utilities.Notificacion;

public class DetalleProcesoDAO {

	//METODO PARA CREAR UN REGISTRO
		public static boolean createProceso(Connection connection, DetalleProceso detalleProceso) {
			String consulta = "INSERT INTO detalleProcesos (Operacion, Descripcion, TiempoPreparacion, TiempoOperacion, CentroTrabajoFK, GrupoTrabajoFK, ProcesoFK) VALUES (?,?,?,?,?,?,?)";
			try {
				PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
				sentenciaPreparada.setInt(1, detalleProceso.getOperacion());
				sentenciaPreparada.setString(2, detalleProceso.getDescripcion());
				sentenciaPreparada.setInt(3, detalleProceso.getTiempoPreparacion());
				sentenciaPreparada.setInt(4, detalleProceso.getTiempoOperacion());
				sentenciaPreparada.setInt(5, detalleProceso.getCentroTrabajoFK());
				sentenciaPreparada.setInt(6, detalleProceso.getGrupoTrabajoFK());
				sentenciaPreparada.setInt(7, detalleProceso.getProcesoFK());
				sentenciaPreparada.execute();
				return true;
			} catch(SQLException ex) {
				Notificacion.dialogoException(ex);
				return false;
			}//FIN TRY CATCH
		}//FIN METODO REGISTRO

		//METODO PARA OBTENER UN REGISTRO
		public static ArrayList<DetalleProceso> readDetalleProceso(Connection connection) {
			ArrayList<DetalleProceso> arrayListDetalleProceso = new ArrayList<DetalleProceso>();
			String consulta = "SELECT Sys_PK, Operacion, Descripcion, TiempoPreparacion, TiempoOperacion, CentroTrabajoFK, GrupoTrabajoFK, ProcesoFK FROM detalleProcesos";
			try {
				Statement sentencia = connection.createStatement();
				ResultSet resultados = sentencia.executeQuery(consulta);
				while(resultados.next()) {
					DetalleProceso detalleProceso = new DetalleProceso();
					detalleProceso.setSysPK(resultados.getInt(1));
					detalleProceso.setOperacion(resultados.getInt(2));
					detalleProceso.setDescripcion(resultados.getString(3));
					detalleProceso.setTiempoPreparacion(resultados.getInt(4));
					detalleProceso.setTiempoOperacion(resultados.getInt(5));
					detalleProceso.setCentroTrabajoFK(resultados.getInt(6));
					detalleProceso.setNombreCentroTrabajo(resultados.getString(7));
					detalleProceso.setGrupoTrabajoFK(resultados.getInt(8));
					detalleProceso.setNombreGrupoTrabajo(resultados.getString(9));
					detalleProceso.setProcesoFK(resultados.getInt(10));
					arrayListDetalleProceso.add(detalleProceso);
				}//FIN WHILE
			} catch(SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
			return arrayListDetalleProceso;
		}//FIN METODO

		//METODO PARA OBTENER UN REGISTRO POR SYSPK
		public static DetalleProceso readDetalleProceso(Connection connection, int sysPK) {
			DetalleProceso detalleProceso = new DetalleProceso();
			String consulta = "SELECT Sys_PK, Operacion, Descripcion, TiempoPreparacion, TiempoOperacion, CentroTrabajoFK, GrupoTrabajoFK, ProcesoFK FROM detalleProcesos WHERE Sys_PK =" + sysPK;
			try {
				Statement sentencia = connection.createStatement();
				ResultSet resultados = sentencia.executeQuery(consulta);
				while(resultados.next()) {
					detalleProceso.setSysPK(resultados.getInt(1));
					detalleProceso.setOperacion(resultados.getInt(2));
					detalleProceso.setDescripcion(resultados.getString(3));
					detalleProceso.setTiempoPreparacion(resultados.getInt(4));
					detalleProceso.setTiempoOperacion(resultados.getInt(5));
					detalleProceso.setCentroTrabajoFK(resultados.getInt(6));
					detalleProceso.setGrupoTrabajoFK(resultados.getInt(7));
					detalleProceso.setProcesoFK(resultados.getInt(8));
				}//FIN WHILE
			} catch(SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
			return detalleProceso;
		}//FIN METODO

		//METODO PARA OBTENER UN REGISTRO
		public static ArrayList<DetalleProceso> readDetalleProceso(Connection connection, String like) {
			ArrayList<DetalleProceso> arrayListDetalleProceso = new ArrayList<DetalleProceso>();
			String consulta = "SELECT Sys_PK, Operacion, Descripcion, TiempoPreparacion, TiempoOperacion, CentroTrabajoFK, GrupoTrabajoFK, ProcesoFK FROM detalleProcesos INNER JOIN  WHERE Fecha LIKE';";
			try {
				Statement sentencia = connection.createStatement();
				ResultSet resultados = sentencia.executeQuery(consulta);
				while(resultados.next()) {
					DetalleProceso detalleProceso = new DetalleProceso();
					detalleProceso.setSysPK(resultados.getInt(1));
					detalleProceso.setOperacion(resultados.getInt(2));
					detalleProceso.setDescripcion(resultados.getString(3));
					detalleProceso.setTiempoPreparacion(resultados.getInt(4));
					detalleProceso.setTiempoOperacion(resultados.getInt(5));
					detalleProceso.setCentroTrabajoFK(resultados.getInt(6));
					detalleProceso.setNombreCentroTrabajo(resultados.getString(7));
					detalleProceso.setGrupoTrabajoFK(resultados.getInt(8));
					detalleProceso.setNombreGrupoTrabajo(resultados.getString(9));
					detalleProceso.setProcesoFK(resultados.getInt(10));
					arrayListDetalleProceso.add(detalleProceso);
				}//FIN WHILE
			} catch(SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
			return arrayListDetalleProceso;
		}//FIN METODO

		//METODO PARA ACTUALIZAR UN REGISTRO
				public static boolean updateDetalleProceso(Connection connection, DetalleProceso detalleProceso) {
					String consulta = "UPDATE detalleProcesos SET Operacion = ?, Descripcion = ?, TiempoPreparacion = ?, TiempoOperacion = ?, CentroTrabajoFK = ?, GrupoTrabajoFK = ?, ProcesoFK = ? WHERE Sys_PK = ?";
					try {
						PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
						sentenciaPreparada.setInt(1, detalleProceso.getOperacion());
						sentenciaPreparada.setString(2, detalleProceso.getDescripcion());
						sentenciaPreparada.setInt(3, detalleProceso.getTiempoPreparacion());
						sentenciaPreparada.setInt(4, detalleProceso.getTiempoOperacion());
						sentenciaPreparada.setInt(5, detalleProceso.getCentroTrabajoFK());
						sentenciaPreparada.setInt(6, detalleProceso.getGrupoTrabajoFK());
						sentenciaPreparada.setInt(7, detalleProceso.getProcesoFK());
						sentenciaPreparada.setInt(8, detalleProceso.getSysPK());
						sentenciaPreparada.execute();
						return true;
					} catch (SQLException ex) {
						Notificacion.dialogoException(ex);
						return false;
					}//FIN TRY/CATCH
				}//FIN METODO

		//METODO PARA ELIMINAR UN REGISTRO
		public static boolean deleteDetalleProceso(Connection connection, DetalleProceso detalleProceso) {
			String consulta = "DELETE FROM detalleProcesos WHERE Sys_PK = ?";
			try {
				PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
				sentenciaPreparada.setInt(1, detalleProceso.getSysPK());
				sentenciaPreparada.execute();
				return true;
			} catch(SQLException ex) {
				Notificacion.dialogoException(ex);
			return false;
			}//FIN TRY/CATCH
		}//FIN METODO
		
		public static ObservableList<DetalleProceso> toObservableList(ArrayList<DetalleProceso> arrayList) {
			ObservableList<DetalleProceso> listaObservableDetalleProceso = FXCollections.observableArrayList();
			for (DetalleProceso detalleProceso : arrayList) {
				listaObservableDetalleProceso.add(detalleProceso);
			}
			return listaObservableDetalleProceso;
		}//FIN METODO
}//FIN CLASE
