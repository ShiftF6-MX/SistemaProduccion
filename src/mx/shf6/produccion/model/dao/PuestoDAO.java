package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import mx.shf6.produccion.model.Puesto;


public class PuestoDAO {

	// METODO PARA CREAR UN REGISTRO
	public static boolean createPuesto(Connection connection, Puesto puesto) {
		String consulta = "INSERT INTO puestos (Codigo, Descripcion) VALUES (?, ?)";
		try {
		PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
		sentenciaPreparada.setString(1, puesto.getCodigo());
		sentenciaPreparada.setString(2, puesto.getDescripcion());
		sentenciaPreparada.execute();
		return true;
	} catch (SQLException ex) {
		JOptionPane.showMessageDialog(null, "Error " + ex);
		return false;
	} // FIN TRY/CATCH
	}// FIN METODO

	// METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Puesto> readPuesto(Connection connection) {
		ArrayList<Puesto> arrayListPuesto = new ArrayList<Puesto>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion FROM puestos";
		try {
		Statement sentencia = connection.createStatement();
		java.sql.ResultSet resultados = sentencia.executeQuery(consulta);
		while (resultados.next()) {
			Puesto puesto = new Puesto();
			puesto.setSysPK(resultados.getInt(1));
			puesto.setCodigo(resultados.getString(2));
			puesto.setDescripcion(resultados.getString(3));
			arrayListPuesto.add(puesto);
			} // FIN WHILE
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error " + ex);
		} // FIN TRY/CATCH
		return arrayListPuesto;
	}// FIN METODO

	// METODO PARA OBTENER UN REGISTRO
	public static Puesto readPuesto(Connection connection, int sysPK) {
		Puesto puesto = new Puesto();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion from puestos WHERE Sys_PK = " + sysPK;
		try {
		Statement sentencia = connection.createStatement();
		java.sql.ResultSet resultados = sentencia.executeQuery(consulta);
		while (resultados.next()) {
			puesto.setSysPK(resultados.getInt(1));
			puesto.setCodigo(resultados.getString(2));
			puesto.setDescripcion(resultados.getString(3));
		} // FIN WHILE
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error " + ex);
		} // FIN TRY/CATCH
		return puesto;
	}// FIN METODO

	// METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Puesto> readPuesto(Connection connection, String like) {
		ArrayList<Puesto> arrayListPuesto = new ArrayList<Puesto>();
		String consulta = "SELECT Sys_PK, Codigo,Desripcion FROM puestos WHERE Codigo LIKE '%" + like + "%';";
		try {
			Statement sentencia = connection.createStatement();
			java.sql.ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Puesto puesto = new Puesto();
				puesto.setSysPK(resultados.getInt(1));
				puesto.setCodigo(resultados.getString(2));
				puesto.setDescripcion(resultados.getString(3));
				arrayListPuesto.add(puesto);
			} // FIN WHILE
		} catch (SQLException ex) {
			// Notificacion.dialogoException(ex);
		} // FIN TRY/CATCH
		return arrayListPuesto;
	}// FIN METODO

	// METODO PARA EDITAR UN REGISTRO
	public static boolean updatePuesto(Connection connection, Puesto puesto) {
		String consulta = "UPDATE puestos SET Codigo = ?, Descripcion = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, puesto.getCodigo());
			sentenciaPreparada.setString(2, puesto.getDescripcion());
			sentenciaPreparada.setInt(3, puesto.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error " + ex);
			return false;
		} // FIN TRY/CATCH
	}// FIN METODO

	// METODO PARA ELIMINAR UN REGISTRO
	public static boolean deletePuesto(Connection connection, Puesto puesto) {
		String consulta = "DELETE FROM puestos WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, puesto.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error " + ex);
			return false;
		} // FIN TRY/CATCH
	}// FIN METODO

}// FIN CLASE
