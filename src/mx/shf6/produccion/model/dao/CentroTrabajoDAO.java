package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import mx.shf6.produccion.model.CentroTrabajo;
import mx.shf6.produccion.utilities.Notificacion;

public class CentroTrabajoDAO {

	// METODO PARA CREAR UN REGISTRO
	public static boolean createCentroTrabajo(Connection connection, CentroTrabajo centroTrabajo) {
		String consulta = "INSERT INTO centrostrabajo (Codigo, Descripcion, GrupoTrabajoFK) VALUES (?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, centroTrabajo.getCodigo());
			sentenciaPreparada.setString(2, centroTrabajo.getDescripcion());
			sentenciaPreparada.setInt(3, centroTrabajo.getGrupoTrabajoFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY/CATCH
	}// FIN METODO

	// METODO PARA OBTENER UN REGISTRO
	public static ArrayList<CentroTrabajo> readCentroTrabajo(Connection connection) {
		ArrayList<CentroTrabajo> arrayListCentroTrabajo = new ArrayList<CentroTrabajo>();
		String consulta = "select centrostrabajo.Sys_PK, centrostrabajo.Codigo, centrostrabajo.Descripcion, centrostrabajo.GrupoTrabajoFK, grupostrabajo.Codigo from centrostrabajo INNER JOIN grupostrabajo ON centrostrabajo.GrupoTrabajoFK = grupostrabajo.Sys_PK order by centrostrabajo.codigo";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				CentroTrabajo centrotrabajo = new CentroTrabajo();
				centrotrabajo.setSysPK(resultados.getInt(1));
				centrotrabajo.setCodigo(resultados.getString(2));
				centrotrabajo.setDescripcion(resultados.getString(3));
				centrotrabajo.setgrupoTrabajoFK(resultados.getInt(4));
				centrotrabajo.setCodigoGrupoTrabajo(resultados.getString(5));
				arrayListCentroTrabajo.add(centrotrabajo);
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY/CATCH
		return arrayListCentroTrabajo;
	}// FIN METODO

	// METODO PARA OBTENER UN REGISTRO
	public static CentroTrabajo readCentroTrabajo(Connection connection, String sysPK) {
		CentroTrabajo centrotrabajo = new CentroTrabajo();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, GrupoTrabajoFK FROM centrostrabajo order by Codigo ASC WHERE Sys_PK = "
				+ sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				centrotrabajo.setSysPK(resultados.getInt(1));
				centrotrabajo.setCodigo(resultados.getString(2));
				centrotrabajo.setDescripcion(resultados.getString(3));
				centrotrabajo.setgrupoTrabajoFK(resultados.getInt(4));
				centrotrabajo.setCodigoGrupoTrabajo(resultados.getString(5));
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY/CATCH
		return centrotrabajo;
	}// FIN METODO

	// METODO PARA OBTENER UN REGISTRO
	public static ArrayList<CentroTrabajo> readCentroTrabajoLike(Connection connection, String like) {
		ArrayList<CentroTrabajo> arrayListaCentroTrabajo = new ArrayList<CentroTrabajo>();
		String consulta = "select centrostrabajo.Sys_PK, centrostrabajo.Codigo, centrostrabajo.Descripcion, grupostrabajo.Codigo from centrostrabajo INNER JOIN grupostrabajo ON centrostrabajo.GrupoTrabajoFK = grupostrabajo.Sys_PK WHERE centrostrabajo.Codigo LIKE '%"+ like + "%';";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				CentroTrabajo centro = new CentroTrabajo();
				centro.setSysPK(resultados.getInt(1));
				centro.setCodigo(resultados.getString(2));
				centro.setDescripcion(resultados.getString(3));
				centro.setCodigoGrupoTrabajo(resultados.getString(4));
				arrayListaCentroTrabajo.add(centro);

			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY/CATCH
		return arrayListaCentroTrabajo;
	}// FIN METODO

	// METODO PARA EDITAR UN REGISTRO
	public static boolean updateCentroTrabajo(Connection connection, CentroTrabajo centrotrabajo) {
		String consulta = "UPDATE centrostrabajo SET Codigo = ?, Descripcion = ?, GrupoTrabajoFK = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, centrotrabajo.getCodigo());
			sentenciaPreparada.setString(2, centrotrabajo.getDescripcion());
			sentenciaPreparada.setInt(3, centrotrabajo.getGrupoTrabajoFK());
			sentenciaPreparada.setInt(4, centrotrabajo.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY/CATCH
	}// FIN METODO

	// METODO PARA ELIMINAR UN REGISTRO
	public static boolean deleteCentroTrabajo(Connection connection, CentroTrabajo centroTrabajo) {
		String consulta = "DELETE FROM centrostrabajo WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, centroTrabajo.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY/CATCH
	}// FIN METODO

}
