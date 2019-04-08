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
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.utilities.Dimensiones;
import mx.shf6.produccion.utilities.Notificacion;

public class ComponenteDAO {

	//METODO PARA CREAR UN REGISTRO
	public static boolean createComponente(Connection connection, Componente componente) {
		String consulta = "INSERT INTO componentes (NumeroParte, Descripcion, Largo, Ancho, AltoEspesor, TipoComponente, Costo, Unidad, MaterialFK, TipoMiscelaneoFK, TipoMateriaPrimaFK, AcabadoFK, TratamientoFK, Notas, Status, Consecutivo, ClienteFK) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, componente.getNumeroParte());
			sentenciaPreparada.setString(2, componente.getDescripcion());
			sentenciaPreparada.setDouble(3, componente.getDimensiones().getLargo());
			sentenciaPreparada.setDouble(4, componente.getDimensiones().getAncho());
			sentenciaPreparada.setDouble(5, componente.getDimensiones().getAltoEspesor());
			sentenciaPreparada.setString(6, componente.getTipoComponenteChar());
			sentenciaPreparada.setDouble(7, componente.getCosto());
			sentenciaPreparada.setString(8, componente.getUnidad());
			if (componente.getMaterialFK() > 0)
				sentenciaPreparada.setInt(9, componente.getMaterialFK());
			else
				sentenciaPreparada.setNull(9, Types.INTEGER);
			if (componente.getTipoMiscelaneoFK() > 0)
				sentenciaPreparada.setInt(10, componente.getTipoMiscelaneoFK());
			else
				sentenciaPreparada.setNull(10, Types.INTEGER);
			if (componente.getTipoMateriaPrimaFK() > 0)
				sentenciaPreparada.setInt(11, componente.getTipoMateriaPrimaFK());
			else
				sentenciaPreparada.setNull(11, Types.INTEGER);
			if (componente.getAcabadoFK() > 0)
				sentenciaPreparada.setInt(12, componente.getAcabadoFK());
			else
				sentenciaPreparada.setNull(12, Types.INTEGER);
			if (componente.getTratamientoFK() > 0)
				sentenciaPreparada.setInt(13, componente.getTratamientoFK());
			else
				sentenciaPreparada.setNull(13, Types.INTEGER);
			sentenciaPreparada.setString(14, componente.getNotas());
			sentenciaPreparada.setInt(15, componente.getStatusFK());
			sentenciaPreparada.setInt(16, componente.getConsecutivo());
			if (componente.getclienteFK() > 0) 
				sentenciaPreparada.setInt(17, componente.getclienteFK());
			else
				sentenciaPreparada.setNull(17, Types.INTEGER);
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Componente> readComponente(Connection connection) {
		ArrayList<Componente> arrayListComponente = new ArrayList<Componente>();
		String consulta = "SELECT Sys_PK, NumeroParte, Descripcion, Largo, Ancho, AltoEspesor, TipoComponente, Costo, Unidad, MaterialFK, TipoMiscelaneoFK, TipoMateriaPrimaFK, AcabadoFK, TratamientoFK, Notas, Status, Consecutivo, ClienteFK FROM componentes";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Componente componente = new Componente();
				componente.setSysPK(resultados.getInt(1));
				componente.setNumeroParte(resultados.getString(2));
				componente.setDescripcion(resultados.getString(3));
				Dimensiones dimensiones = new Dimensiones();
				dimensiones.setLargo(resultados.getDouble(4));
				dimensiones.setAncho(resultados.getDouble(5));
				dimensiones.setAltoEspesor(resultados.getDouble(6));
				componente.setDimensiones(dimensiones);
				componente.setTipoComponente(resultados.getString(7));
				componente.setCosto(resultados.getDouble(8));
				componente.setUnidad(resultados.getString(9));
				componente.setMaterialFK(resultados.getInt(10));;
				componente.setTipoMiscelaneoFK(resultados.getInt(11));
				componente.setTipoMateriaPrimaFK(resultados.getInt(12));
				componente.setAcabadoFK(resultados.getInt(13));
				componente.setTratamientoFK(resultados.getInt(14));
				componente.setNotas(resultados.getString(15));
				componente.setStatus(resultados.getInt(16));
				componente.setConsecutivo(resultados.getInt(17));
				componente.setClienteFK(resultados.getInt(18));
				arrayListComponente.add(componente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListComponente;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static Componente readComponente(Connection connection, int sysPK) {
		Componente componente = new Componente();
		String consulta = "SELECT Sys_PK, NumeroParte, Descripcion, Largo, Ancho, AltoEspesor, TipoComponente, Costo, Unidad, MaterialFK, TipoMiscelaneoFK, TipoMateriaPrimaFK, AcabadoFK, TratamientoFK, Notas, Status, Consecutivo, ClienteFK FROM componentes WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				componente.setSysPK(resultados.getInt(1));
				componente.setNumeroParte(resultados.getString(2));
				componente.setDescripcion(resultados.getString(3));
				Dimensiones dimensiones = new Dimensiones();
				dimensiones.setLargo(resultados.getDouble(4));
				dimensiones.setAncho(resultados.getDouble(5));
				dimensiones.setAltoEspesor(resultados.getDouble(6));
				componente.setDimensiones(dimensiones);
				componente.setTipoComponente(resultados.getString(7));
				componente.setCosto(resultados.getDouble(8));
				componente.setUnidad(resultados.getString(9));
				componente.setMaterialFK(resultados.getInt(10));;
				componente.setTipoMiscelaneoFK(resultados.getInt(11));
				componente.setTipoMateriaPrimaFK(resultados.getInt(12));
				componente.setAcabadoFK(resultados.getInt(13));
				componente.setTratamientoFK(resultados.getInt(14));
				componente.setNotas(resultados.getString(15));
				componente.setStatus(resultados.getInt(16));
				componente.setConsecutivo(resultados.getInt(17));
				componente.setClienteFK(resultados.getInt(18));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return componente;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Componente> readComponente(Connection connection, String like) {
		ArrayList<Componente> arrayListComponente = new ArrayList<Componente>();
		String consulta = "SELECT Sys_PK, NumeroParte, Descripcion, Largo, Ancho, AltoEspesor, TipoComponente, Costo, Unidad, MaterialFK, TipoMiscelaneoFK, TipoMateriaPrimaFK, AcabadoFK, TratamientoFK, Notas, Status, Consecutivo, ClienteFK FROM componentes WHERE Descripcion LIKE '%" + like + "%' OR NumeroParte LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Componente componente = new Componente();
				componente.setSysPK(resultados.getInt(1));
				componente.setNumeroParte(resultados.getString(2));
				componente.setDescripcion(resultados.getString(3));
				Dimensiones dimensiones = new Dimensiones();
				dimensiones.setLargo(resultados.getDouble(4));
				dimensiones.setAncho(resultados.getDouble(5));
				dimensiones.setAltoEspesor(resultados.getDouble(6));
				componente.setDimensiones(dimensiones);
				componente.setTipoComponente(resultados.getString(7));
				componente.setCosto(resultados.getDouble(8));
				componente.setUnidad(resultados.getString(9));
				componente.setMaterialFK(resultados.getInt(10));;
				componente.setTipoMiscelaneoFK(resultados.getInt(11));
				componente.setTipoMateriaPrimaFK(resultados.getInt(12));
				componente.setAcabadoFK(resultados.getInt(13));
				componente.setTratamientoFK(resultados.getInt(14));
				componente.setNotas(resultados.getString(15));
				componente.setStatus(resultados.getInt(16));
				componente.setConsecutivo(resultados.getInt(17));
				componente.setClienteFK(resultados.getInt(18));
				arrayListComponente.add(componente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListComponente;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Componente> readComponenteNumeroParte(Connection connection, String numeroParte) {
		ArrayList<Componente> arrayListComponente = new ArrayList<Componente>();
		String consulta = "SELECT Sys_PK, NumeroParte, Descripcion, Largo, Ancho, AltoEspesor, TipoComponente, Costo, Unidad, MaterialFK, TipoMiscelaneoFK, TipoMateriaPrimaFK, AcabadoFK, TratamientoFK, Notas, Status, Consecutivo, ClienteFK FROM componentes WHERE NumeroParte = '" + numeroParte + "'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Componente componente = new Componente();
				componente.setSysPK(resultados.getInt(1));
				componente.setNumeroParte(resultados.getString(2));
				componente.setDescripcion(resultados.getString(3));
				Dimensiones dimensiones = new Dimensiones();
				dimensiones.setLargo(resultados.getDouble(4));
				dimensiones.setAncho(resultados.getDouble(5));
				dimensiones.setAltoEspesor(resultados.getDouble(6));
				componente.setDimensiones(dimensiones);
				componente.setTipoComponente(resultados.getString(7));
				componente.setCosto(resultados.getDouble(8));
				componente.setUnidad(resultados.getString(9));
				componente.setMaterialFK(resultados.getInt(10));;
				componente.setTipoMiscelaneoFK(resultados.getInt(11));
				componente.setTipoMateriaPrimaFK(resultados.getInt(12));
				componente.setAcabadoFK(resultados.getInt(13));
				componente.setTratamientoFK(resultados.getInt(14));
				componente.setNotas(resultados.getString(15));
				componente.setStatus(resultados.getInt(16));
				componente.setConsecutivo(resultados.getInt(17));
				componente.setClienteFK(resultados.getInt(18));
				arrayListComponente.add(componente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListComponente;
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateComponente(Connection connection, Componente componente) {
		String consulta = "UPDATE componentes SET NumeroParte = ?, Descripcion = ?, Largo = ?, Ancho = ?, AltoEspesor = ?, TipoComponente = ?, Costo = ?, Unidad = ?, MaterialFK = ?, TipoMiscelaneoFK = ?, TipoMateriaPrimaFK = ?, AcabadoFK = ?, TratamientoFK = ?, Notas = ?, Status = ?, Consecutivo = ?, ClienteFK = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, componente.getNumeroParte());
			sentenciaPreparada.setString(2, componente.getDescripcion());
			sentenciaPreparada.setDouble(3, componente.getDimensiones().getLargo());
			sentenciaPreparada.setDouble(4, componente.getDimensiones().getAncho());
			sentenciaPreparada.setDouble(5, componente.getDimensiones().getAltoEspesor());
			sentenciaPreparada.setString(6, componente.getTipoComponenteChar());
			sentenciaPreparada.setDouble(7, componente.getCosto());
			sentenciaPreparada.setString(8, componente.getUnidad());
			if (componente.getMaterialFK() > 0)
				sentenciaPreparada.setInt(9, componente.getMaterialFK());
			else
				sentenciaPreparada.setNull(9, Types.INTEGER);
			if (componente.getTipoMiscelaneoFK() > 0)
				sentenciaPreparada.setInt(10, componente.getTipoMiscelaneoFK());
			else
				sentenciaPreparada.setNull(10, Types.INTEGER);
			if (componente.getTipoMateriaPrimaFK() > 0)
				sentenciaPreparada.setInt(11, componente.getTipoMateriaPrimaFK());
			else
				sentenciaPreparada.setNull(11, Types.INTEGER);
			if (componente.getAcabadoFK() > 0)
				sentenciaPreparada.setInt(12, componente.getAcabadoFK());
			else
				sentenciaPreparada.setNull(12, Types.INTEGER);
			if (componente.getTratamientoFK() > 0)
				sentenciaPreparada.setInt(13, componente.getTratamientoFK());
			else
				sentenciaPreparada.setNull(13, Types.INTEGER);
			sentenciaPreparada.setString(14, componente.getNotas());
			sentenciaPreparada.setInt(15, componente.getStatusFK());
			sentenciaPreparada.setInt(16, componente.getConsecutivo());
			if (componente.getclienteFK() > 0) 
				sentenciaPreparada.setInt(17, componente.getclienteFK());
			else
				sentenciaPreparada.setNull(17, Types.INTEGER);
			sentenciaPreparada.setInt(18, componente.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteComponente(Connection connection, Componente componente) {
		String consulta = "DELETE FROM componentes WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, componente.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<Componente> toObservableList(ArrayList<Componente> arrayList) {
		ObservableList<Componente> listaObservableComponente = FXCollections.observableArrayList();
		for (Componente componente : arrayList) 
			listaObservableComponente.add(componente);
		return listaObservableComponente;
	}//FIN METODO
	
	//METODO PARA OBTENER TODOS LOS NUMEROS DE PARTE
	public static ObservableList<String> listaNumerosParte(Connection connection) {
		ObservableList<String> arrayListNumeroParteComponente = FXCollections.observableArrayList();
		String consulta = "SELECT NumeroParte FROM componentes ORDER BY NumeroParte ASC";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				String numeroParte = resultados.getString(1);
				arrayListNumeroParteComponente.add(numeroParte);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListNumeroParteComponente;
	}//FIN METODO
		
}//FIN CLASE