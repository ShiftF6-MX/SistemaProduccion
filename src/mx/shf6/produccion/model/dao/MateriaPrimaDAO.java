package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.MateriaPrima;
import mx.shf6.produccion.utilities.Notificacion;

public class MateriaPrimaDAO implements ObjectDAO{

	//METODO PARA HACER CREATE EN LA TABLA MATERIAPRIMA
	@Override
	public boolean crear(Connection connection, Object MateriaPrima) {	
		MateriaPrima materiaPrima = (MateriaPrima) MateriaPrima;
		String query = "INSERT INTO materiaprima (tipoMateriaPrima, material, acabado, tipoProducto) "
				+ "values ( ?, ?, ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, materiaPrima.getTipoMateriaPrimaFk());
			preparedStatement.setInt(2, materiaPrima.getMaterialFk());
			preparedStatement.setInt(3, materiaPrima.getAcabadoFk());
			preparedStatement.setInt(4, materiaPrima.getTipoProductoFk());
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA MATERIAPRIMA
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query = "";
		ArrayList<Object> listaMateriaPrima = new ArrayList<Object>();
		if (campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query = "SELECT * FROM materiaprima ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);  
				MateriaPrima materiaPrima = null;
				while (resultSet.next()) {
					materiaPrima = new MateriaPrima();
					materiaPrima.setSysPk(resultSet.getInt(1));
					materiaPrima.setTipoMateriaPrimaFk(resultSet.getInt(2));
					materiaPrima.setMaterialFk(resultSet.getInt(3));
					materiaPrima.setAcabadoFk(resultSet.getInt(4));
					materiaPrima.setTipoProductoFk(resultSet.getInt(5));
					listaMateriaPrima.add(materiaPrima);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else {
			query = "SELECT * FROM materiaprima WHERE " + campoBusqueda + " = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				MateriaPrima materiaPrima = null;
				while (resultSet.next()) {
					materiaPrima = new MateriaPrima();
					materiaPrima.setSysPk(resultSet.getInt(1));
					materiaPrima.setTipoMateriaPrimaFk(resultSet.getInt(2));
					materiaPrima.setMaterialFk(resultSet.getInt(3));
					materiaPrima.setAcabadoFk(resultSet.getInt(4));
					materiaPrima.setTipoProductoFk(resultSet.getInt(5));
					listaMateriaPrima.add(materiaPrima);
				}//FIN WHILE
			}catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		}//FIN IF/ELSE
		return listaMateriaPrima;
	}//FIN METODO	
	
	//METODO PARA HACER UPDATE EN LA TABLA MATERIAPRIMA
	@Override
	public boolean modificar(Connection connection, Object MateriaPrima) {
		String query = "UPDATE materiaprima "
				+ "SET  tipoMateriaPrima = ?, material = ?, acabado = ?, tipoProducto = ? "
				+ "WHERE sysPK = ?";
		try {
			MateriaPrima materiaPrima = (MateriaPrima)MateriaPrima;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, materiaPrima.getTipoMateriaPrimaFk());
			preparedStatement.setInt(2, materiaPrima.getMaterialFk());
			preparedStatement.setInt(3, materiaPrima.getAcabadoFk());
			preparedStatement.setInt(4, materiaPrima.getTipoProductoFk());
			preparedStatement.setInt(5, materiaPrima.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER DELETE EN LA TABLA MATERIAPRIMA
	@Override
	public boolean eliminar(Connection connection, Object MateriaPrima) {
		String query = "DELETE FROM materiaprima WHERE sysPK = ?";
		try {	
			MateriaPrima materiaPrima = (MateriaPrima)MateriaPrima;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, materiaPrima.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO		
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public int ultimoSysPk(Connection connection) {
		String query = "SELECT sysPK FROM materiaprima order by sysPK asc";
		int ultimoSysPk = 0;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
				ultimoSysPk = resultSet.getInt(1);
			return ultimoSysPk;
		}catch (SQLException e) {
			Notificacion.dialogoException(e);
		}//FIN TRY/CATCH
		return ultimoSysPk;
	}//FIN METODO

}//FIN CLASE