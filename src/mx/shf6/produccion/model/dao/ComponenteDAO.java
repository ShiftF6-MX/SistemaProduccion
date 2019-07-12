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
		String consulta = "INSERT INTO componentes (NumeroParte, Descripcion, Largo, Ancho, AltoEspesor, Grado, EspesorPulgadas, TipoComponente, Costo, CostoDirecto, CostoIndirecto, Unidad, MaterialFK, TipoMiscelaneoFK, TipoMateriaPrimaFK, AcabadoFK, TratamientoFK, Notas, Status, Consecutivo, ClienteFK) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, componente.getNumeroParte());
			sentenciaPreparada.setString(2, componente.getDescripcion());
			sentenciaPreparada.setDouble(3, componente.getDimensiones().getLargo());
			sentenciaPreparada.setDouble(4, componente.getDimensiones().getAncho());
			sentenciaPreparada.setDouble(5, componente.getDimensiones().getAltoEspesor());
			sentenciaPreparada.setString(6, componente.getGradoMaterial());
			sentenciaPreparada.setString(7, componente.getEspesorMaterial());
			sentenciaPreparada.setString(8, componente.getTipoComponenteChar());
			sentenciaPreparada.setDouble(9, componente.getCosto());
			sentenciaPreparada.setDouble(10, componente.getCostoDirecto());
			sentenciaPreparada.setDouble(11, componente.getCostoIndirecto());
			sentenciaPreparada.setString(12, componente.getUnidad());
			if (componente.getMaterialFK() > 0)
				sentenciaPreparada.setInt(13, componente.getMaterialFK());
			else
				sentenciaPreparada.setNull(13, Types.INTEGER);
			if (componente.getTipoMiscelaneoFK() > 0)
				sentenciaPreparada.setInt(14, componente.getTipoMiscelaneoFK());
			else
				sentenciaPreparada.setNull(14, Types.INTEGER);
			if (componente.getTipoMateriaPrimaFK() > 0)
				sentenciaPreparada.setInt(15, componente.getTipoMateriaPrimaFK());
			else
				sentenciaPreparada.setNull(15, Types.INTEGER);
			if (componente.getAcabadoFK() > 0)
				sentenciaPreparada.setInt(16, componente.getAcabadoFK());
			else
				sentenciaPreparada.setNull(16, Types.INTEGER);
			if (componente.getTratamientoFK() > 0)
				sentenciaPreparada.setInt(17, componente.getTratamientoFK());
			else
				sentenciaPreparada.setNull(17, Types.INTEGER);
			sentenciaPreparada.setString(18, componente.getNotas());
			sentenciaPreparada.setInt(19, componente.getStatusFK());
			sentenciaPreparada.setInt(20, componente.getConsecutivo());
			if (componente.getclienteFK() > 0)
				sentenciaPreparada.setInt(21, componente.getclienteFK());
			else
				sentenciaPreparada.setNull(21, Types.INTEGER);
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
		String consulta = "SELECT Sys_PK, NumeroParte, Descripcion, Largo, Ancho, AltoEspesor, Grado, EspesorPulgadas, TipoComponente, Costo, CostoDirecto, CostoIndirecto, Unidad, MaterialFK, TipoMiscelaneoFK, TipoMateriaPrimaFK, AcabadoFK, TratamientoFK, Notas, Status, Consecutivo, ClienteFK FROM componentes";
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
				componente.setGradoMaterial(resultados.getString(7));
				componente.setEspesorMaterial(resultados.getString(8));
				componente.setTipoComponente(resultados.getString(9));
				componente.setCosto(resultados.getDouble(10));
				componente.setCostoDirecto(resultados.getDouble(11));
				componente.setCostoIndirecto(resultados.getDouble(12));
				componente.setUnidad(resultados.getString(13));
				componente.setMaterialFK(resultados.getInt(14));;
				componente.setTipoMiscelaneoFK(resultados.getInt(15));
				componente.setTipoMateriaPrimaFK(resultados.getInt(16));
				componente.setAcabadoFK(resultados.getInt(17));
				componente.setTratamientoFK(resultados.getInt(18));
				componente.setNotas(resultados.getString(19));
				componente.setStatus(resultados.getInt(20));
				componente.setConsecutivo(resultados.getInt(21));
				componente.setClienteFK(resultados.getInt(22));
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
		String consulta = "SELECT componentes.Sys_PK, componentes.NumeroParte, componentes.Descripcion, componentes.Largo, componentes.Ancho, componentes.AltoEspesor, componentes.Grado, componentes.EspesorPulgadas, componentes.TipoComponente, componentes.Costo, componentes.CostoDirecto, componentes.CostoIndirecto, componentes.Unidad, componentes.MaterialFK, componentes.TipoMiscelaneoFK, componentes.TipoMateriaPrimaFK, componentes.AcabadoFK, componentes.TratamientoFK, componentes.Notas, componentes.Status, componentes.Consecutivo, componentes.ClienteFK, materiales.Descripcion FROM componentes  INNER JOIN materiales ON componentes.MaterialFK = materiales.Sys_PK WHERE componentes.Sys_PK =  " + sysPK;
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
				componente.setGradoMaterial(resultados.getString(7));
				componente.setEspesorMaterial(resultados.getString(8));
				componente.setTipoComponente(resultados.getString(9));
				componente.setCosto(resultados.getDouble(10));
				componente.setCostoDirecto(resultados.getDouble(11));
				componente.setCostoIndirecto(resultados.getDouble(12));
				componente.setUnidad(resultados.getString(13));
				componente.setMaterialFK(resultados.getInt(14));;
				componente.setTipoMiscelaneoFK(resultados.getInt(15));
				componente.setTipoMateriaPrimaFK(resultados.getInt(16));
				componente.setAcabadoFK(resultados.getInt(17));
				componente.setTratamientoFK(resultados.getInt(18));
				componente.setNotas(resultados.getString(19));
				componente.setStatus(resultados.getInt(20));
				componente.setConsecutivo(resultados.getInt(21));
				componente.setClienteFK(resultados.getInt(22));
				componente.setMaterialDescripcion(resultados.getString(23));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return componente;
	}//FIN METODO

	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Componente> readComponente(Connection connection, String like) {
		ArrayList<Componente> arrayListComponente = new ArrayList<Componente>();
		String consulta = "SELECT Sys_PK, NumeroParte, Descripcion, Largo, Ancho, AltoEspesor, Grado, EspesorPulgadas, TipoComponente, Costo, CostoDirecto, CostoIndirecto, Unidad, MaterialFK, TipoMiscelaneoFK, TipoMateriaPrimaFK, AcabadoFK, TratamientoFK, Notas, Status, Consecutivo, ClienteFK FROM componentes WHERE Descripcion LIKE '%" + like + "%' OR NumeroParte LIKE '%" + like + "%'";
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
				componente.setGradoMaterial(resultados.getString(7));
				componente.setEspesorMaterial(resultados.getString(8));
				componente.setTipoComponente(resultados.getString(9));
				componente.setCosto(resultados.getDouble(10));
				componente.setCostoDirecto(resultados.getDouble(11));
				componente.setCostoIndirecto(resultados.getDouble(12));
				componente.setUnidad(resultados.getString(13));
				componente.setMaterialFK(resultados.getInt(14));;
				componente.setTipoMiscelaneoFK(resultados.getInt(15));
				componente.setTipoMateriaPrimaFK(resultados.getInt(16));
				componente.setAcabadoFK(resultados.getInt(17));
				componente.setTratamientoFK(resultados.getInt(18));
				componente.setNotas(resultados.getString(19));
				componente.setStatus(resultados.getInt(20));
				componente.setConsecutivo(resultados.getInt(21));
				componente.setClienteFK(resultados.getInt(22));
				arrayListComponente.add(componente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListComponente;
	}//FIN METODO

	//METODO PARA OBTENER UN REGISTRO
	public static Componente readComponenteNumeroParte(Connection connection, String numeroParte) {
		Componente componente = new Componente();
		String consulta = "SELECT Sys_PK, NumeroParte, Descripcion, Largo, Ancho, AltoEspesor, Grado, EspesorPulgadas, TipoComponente, Costo, CostoDirecto, CostoIndirecto, Unidad, MaterialFK, TipoMiscelaneoFK, TipoMateriaPrimaFK, AcabadoFK, TratamientoFK, Notas, Status, Consecutivo, ClienteFK FROM componentes WHERE NumeroParte = '" + numeroParte + "'";
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
				componente.setGradoMaterial(resultados.getString(7));
				componente.setEspesorMaterial(resultados.getString(8));
				componente.setTipoComponente(resultados.getString(9));
				componente.setCosto(resultados.getDouble(10));
				componente.setCostoDirecto(resultados.getDouble(11));
				componente.setCostoIndirecto(resultados.getDouble(12));
				componente.setUnidad(resultados.getString(13));
				componente.setMaterialFK(resultados.getInt(14));;
				componente.setTipoMiscelaneoFK(resultados.getInt(15));
				componente.setTipoMateriaPrimaFK(resultados.getInt(16));
				componente.setAcabadoFK(resultados.getInt(17));
				componente.setTratamientoFK(resultados.getInt(18));
				componente.setNotas(resultados.getString(19));
				componente.setStatus(resultados.getInt(20));
				componente.setConsecutivo(resultados.getInt(21));
				componente.setClienteFK(resultados.getInt(22));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return componente;
	}//FIN METODO

	//METODO PARA OBTENER TODOS LOS COMPONENTES DE UN TIPOP EN ESPECIFICO
	public static ArrayList<Componente> readComponenteTipoComponente(Connection connection, String tipoComponenteChar) {
		ArrayList<Componente> arrayListComponente = new ArrayList<Componente>();
		String consulta = "SELECT Sys_PK, NumeroParte, Descripcion, Largo, Ancho, AltoEspesor, Grado, EspesorPulgadas, TipoComponente, Costo, CostoDirecto, CostoIndirecto, Unidad, MaterialFK, TipoMiscelaneoFK, TipoMateriaPrimaFK, AcabadoFK, TratamientoFK, Notas, Status, Consecutivo, ClienteFK FROM componentes WHERE TipoComponente = '" + tipoComponenteChar + "'";
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
				componente.setGradoMaterial(resultados.getString(7));
				componente.setEspesorMaterial(resultados.getString(8));
				componente.setTipoComponente(resultados.getString(9));
				componente.setCosto(resultados.getDouble(10));
				componente.setCostoDirecto(resultados.getDouble(11));
				componente.setCostoIndirecto(resultados.getDouble(12));
				componente.setUnidad(resultados.getString(13));
				componente.setMaterialFK(resultados.getInt(14));;
				componente.setTipoMiscelaneoFK(resultados.getInt(15));
				componente.setTipoMateriaPrimaFK(resultados.getInt(16));
				componente.setAcabadoFK(resultados.getInt(17));
				componente.setTratamientoFK(resultados.getInt(18));
				componente.setNotas(resultados.getString(19));
				componente.setStatus(resultados.getInt(20));
				componente.setConsecutivo(resultados.getInt(21));
				componente.setClienteFK(resultados.getInt(22));
				arrayListComponente.add(componente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListComponente;
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

	//METODO PARA OBTENER TODOS LOS NUMEROS DE PARTE
	public static ArrayList<Componente> readComponentesEnsambleCliente(Connection connection, int clienteFK) {
		ArrayList<Componente> arrayListComponentesEnsambleCliente = new ArrayList<Componente>();
		String consulta = "SELECT * FROM componentes WHERE ClienteFK = "+ clienteFK +" AND TipoComponente = 'A'";
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
				componente.setGradoMaterial(resultados.getString(7));
				componente.setEspesorMaterial(resultados.getString(8));
				componente.setTipoComponente(resultados.getString(9));
				componente.setCosto(resultados.getDouble(10));
				componente.setCostoDirecto(resultados.getDouble(11));
				componente.setCostoIndirecto(resultados.getDouble(12));
				componente.setUnidad(resultados.getString(13));
				componente.setMaterialFK(resultados.getInt(14));;
				componente.setTipoMiscelaneoFK(resultados.getInt(15));
				componente.setTipoMateriaPrimaFK(resultados.getInt(16));
				componente.setAcabadoFK(resultados.getInt(17));
				componente.setTratamientoFK(resultados.getInt(18));
				componente.setNotas(resultados.getString(19));
				componente.setStatus(resultados.getInt(20));
				componente.setConsecutivo(resultados.getInt(21));
				componente.setClienteFK(resultados.getInt(22));
				arrayListComponentesEnsambleCliente.add(componente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListComponentesEnsambleCliente;
	}//FIN METODO

	//METODO PARA CREAR UN REGISTRO
	public static boolean updateComponente(Connection connection, Componente componente) {
		String consulta = "UPDATE componentes SET NumeroParte = ?, Descripcion = ?, Largo = ?, Ancho = ?, AltoEspesor = ?, Grado = ?, EspesorPulgadas = ?, TipoComponente = ?, Costo = ?, CostoDirecto = ?, CostoIndirecto = ?, Unidad = ?, MaterialFK = ?, TipoMiscelaneoFK = ?, TipoMateriaPrimaFK = ?, AcabadoFK = ?, TratamientoFK = ?, Notas = ?, Status = ?, Consecutivo = ?, ClienteFK = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, componente.getNumeroParte());
			sentenciaPreparada.setString(2, componente.getDescripcion());
			sentenciaPreparada.setDouble(3, componente.getDimensiones().getLargo());
			sentenciaPreparada.setDouble(4, componente.getDimensiones().getAncho());
			sentenciaPreparada.setDouble(5, componente.getDimensiones().getAltoEspesor());
			sentenciaPreparada.setString(6, componente.getGradoMaterial());
			sentenciaPreparada.setString(7, componente.getEspesorMaterial());
			sentenciaPreparada.setString(8, componente.getTipoComponenteChar());
			sentenciaPreparada.setDouble(9, componente.getCosto());
			sentenciaPreparada.setDouble(10, componente.getCostoDirecto());
			sentenciaPreparada.setDouble(11, componente.getCostoIndirecto());
			sentenciaPreparada.setString(12, componente.getUnidad());
			if (componente.getMaterialFK() > 0)
				sentenciaPreparada.setInt(13, componente.getMaterialFK());
			else
				sentenciaPreparada.setNull(13, Types.INTEGER);
			if (componente.getTipoMiscelaneoFK() > 0)
				sentenciaPreparada.setInt(14, componente.getTipoMiscelaneoFK());
			else
				sentenciaPreparada.setNull(14, Types.INTEGER);
			if (componente.getTipoMateriaPrimaFK() > 0)
				sentenciaPreparada.setInt(15, componente.getTipoMateriaPrimaFK());
			else
				sentenciaPreparada.setNull(15, Types.INTEGER);
			if (componente.getAcabadoFK() > 0)
				sentenciaPreparada.setInt(16, componente.getAcabadoFK());
			else
				sentenciaPreparada.setNull(16, Types.INTEGER);
			if (componente.getTratamientoFK() > 0)
				sentenciaPreparada.setInt(17, componente.getTratamientoFK());
			else
				sentenciaPreparada.setNull(17, Types.INTEGER);
			sentenciaPreparada.setString(18, componente.getNotas());
			sentenciaPreparada.setInt(19, componente.getStatusFK());
			sentenciaPreparada.setInt(20, componente.getConsecutivo());
			if (componente.getclienteFK() > 0)
				sentenciaPreparada.setInt(21, componente.getclienteFK());
			else
				sentenciaPreparada.setNull(21, Types.INTEGER);
			sentenciaPreparada.setInt(22, componente.getSysPK());
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

}//FIN CLASE