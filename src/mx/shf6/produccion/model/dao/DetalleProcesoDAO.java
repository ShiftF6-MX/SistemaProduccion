package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.DetalleProceso;
import mx.shf6.produccion.utilities.Notificacion;

public class DetalleProcesoDAO {

	//METODO PARA CREAR UN REGISTRO
		public static boolean createDetalleProceso(Connection connection, DetalleProceso detalleProceso) {
			String consulta = "INSERT INTO detalleProcesos (Operacion, Descripcion, TiempoPreparacion, TiempoOperacion, CentroTrabajoFK, GrupoTrabajoFK, ProcesoFK, Cantidad, ComponenteFK) VALUES (?,?,?,?,?,?,?,?,?)";
			try {
				PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
				sentenciaPreparada.setInt(1, detalleProceso.getOperacion());
				sentenciaPreparada.setString(2, detalleProceso.getDescripcion());
				sentenciaPreparada.setInt(3, detalleProceso.getTiempoPreparacion());
				sentenciaPreparada.setInt(4, detalleProceso.getTiempoOperacion());
				sentenciaPreparada.setInt(5, detalleProceso.getCentroTrabajoFK());
				sentenciaPreparada.setInt(6, detalleProceso.getGrupoTrabajoFK());
				sentenciaPreparada.setInt(7, detalleProceso.getProcesoFK());
				sentenciaPreparada.setInt(8,detalleProceso.getCantidad());
				if (detalleProceso.getComponenteFK() != 0) 
					sentenciaPreparada.setInt(9, detalleProceso.getComponenteFK());
				else
					sentenciaPreparada.setNull(9, Types.INTEGER);
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
			String consulta = "SELECT detalleProcesos.Sys_PK, detalleProcesos.Operacion, detalleProcesos.Descripcion, detalleProcesos.TiempoPreparacion, detalleProcesos.TiempoOperacion, detalleProcesos.CentroTrabajoFK, centrostrabajo.Descripcion, detalleProcesos.GrupoTrabajoFK, grupostrabajo.Descripcion, detalleProcesos.ProcesoFK, procesos.Cantidad, procesos.Componente, componentes.NumeroParte FROM detalleProcesos INNER JOIN centrostrabajo ON detalleProcesos.CentroTrabajoFK = centrostrabajo.Sys_PK INNER JOIN grupostrabajo ON detalleProcesos.GrupoTrabajoFK = grupostrabajo.Sys_PK INNER JOIN procesos ON detalleProcesos.ProcesoFK = procesos.Sys_PK INNER JOIN componentes ON detalleProcesos.ComponenteFK = componentes.Sys_PK";
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
					detalleProceso.setComponenteFK(resultados.getInt(11));
					detalleProceso.setNombreComponenteFK(resultados.getString(12));
					detalleProceso.setCantidad(resultados.getInt(13));
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
			String consulta = "SELECT Sys_PK, Operacion, Descripcion, TiempoPreparacion, TiempoOperacion, CentroTrabajoFK, GrupoTrabajoFK, ProcesoFK, ComponenteFK, Cantidad FROM detalleProcesos WHERE Sys_PK =" + sysPK;
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
					detalleProceso.setComponenteFK(resultados.getInt(9));
					detalleProceso.setCantidad(resultados.getInt(10));
				}//FIN WHILE
			} catch(SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
			return detalleProceso;
		}//FIN METODO
		
		//METODO PARA OBTENER UN PROCESO
		public static ArrayList<DetalleProceso> readDetalleProcesoFK(Connection connection, int procesoFK) {
			ArrayList<DetalleProceso> arrayListDetalleProceso = new ArrayList<DetalleProceso>();
			String consulta = "SELECT detalleProcesos.Sys_PK, detalleProcesos.Operacion, detalleProcesos.Descripcion, detalleProcesos.TiempoPreparacion, detalleProcesos.TiempoOperacion, detalleProcesos.CentroTrabajoFK, centrostrabajo.Descripcion, detalleProcesos.GrupoTrabajoFK, grupostrabajo.Descripcion, detalleProcesos.ProcesoFK,  detalleProcesos.ComponenteFK, componentes.NumeroParte, detalleProcesos.Cantidad FROM detalleProcesos INNER JOIN centrostrabajo ON detalleProcesos.CentroTrabajoFK = centrostrabajo.Sys_PK INNER JOIN grupostrabajo ON detalleProcesos.GrupoTrabajoFK = grupostrabajo.Sys_PK INNER JOIN procesos ON detalleProcesos.ProcesoFK = procesos.Sys_PK LEFT JOIN componentes ON detalleProcesos.ComponenteFK = componentes.Sys_PK WHERE detalleProcesos.ProcesoFK = " + procesoFK;
			try {
				Statement sentencia = connection.createStatement();
				ResultSet resultados = sentencia.executeQuery(consulta);
				while (resultados.next()) {
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
					detalleProceso.setComponenteFK(resultados.getInt(11));
					detalleProceso.setNombreComponenteFK(resultados.getString(12));
					detalleProceso.setCantidad(resultados.getInt(13));
					arrayListDetalleProceso.add(detalleProceso);
				}//FIN-WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY-CATCH
			return arrayListDetalleProceso;
		}//FIN METODO

		//METODO PARA ACTUALIZAR UN REGISTRO
		public static boolean updateDetalleProceso(Connection connection, DetalleProceso detalleProceso) {
			String consulta = "UPDATE detalleProcesos SET Operacion = ?, Descripcion = ?, TiempoPreparacion = ?, TiempoOperacion = ?, CentroTrabajoFK = ?, GrupoTrabajoFK = ?, ProcesoFK = ?, ComponenteFK = ?, Cantidad = ? WHERE Sys_PK = ?";
			try {
				PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
				sentenciaPreparada.setInt(1, detalleProceso.getOperacion());
				sentenciaPreparada.setString(2, detalleProceso.getDescripcion());
				sentenciaPreparada.setInt(3, detalleProceso.getTiempoPreparacion());
				sentenciaPreparada.setInt(4, detalleProceso.getTiempoOperacion());
				sentenciaPreparada.setInt(5, detalleProceso.getCentroTrabajoFK());
				sentenciaPreparada.setInt(6, detalleProceso.getGrupoTrabajoFK());
				sentenciaPreparada.setInt(7, detalleProceso.getProcesoFK());
				sentenciaPreparada.setInt(8, detalleProceso.getComponenteFK());
				sentenciaPreparada.setInt(9, detalleProceso.getCantidad());	
				sentenciaPreparada.setInt(10, detalleProceso.getSysPK());
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
