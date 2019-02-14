package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.utilities.Notificacion;

public class ComponenteDAO implements ObjectDAO{

	/**
	 * METODO PARA HACER CREATE EN LA TABLA COMPONENTES
	 *@param connection Recibe la conexion de la base de datos
	 *@param Componente Recibe un objeto de tipo Componente
	 *@return Verdadero si se pudo realizar el Insert Into en la base de datos, False si no se pudo realizar el Insert Into
	 */
	@Override
	public boolean crear(Connection connection, Object Componente) {	
		Componente componente = (Componente) Componente;
		String query = "INSERT INTO componentes (acabadoFk, clienteFk, materialFk, tipoMiscelaneoFk, tipoMateriaPrimaFk, "
				+ "tipoProducto, tratamientoFk, costo, unidad, dimension, descripcion) "
				+ "values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, componente.getAcabadoFk());
			preparedStatement.setInt(2, componente.getClienteFk());
			preparedStatement.setInt(3, componente.getMaterialFk());
			preparedStatement.setInt(4, componente.getTipoMiscelaneoFk());
			preparedStatement.setInt(5, componente.getTipoMateriaPrimaFk());
			preparedStatement.setInt(6, componente.getTipoProductoFk());
			preparedStatement.setInt(7, componente.getTratamientoFk());
			preparedStatement.setDouble(8, componente.getCosto());
			preparedStatement.setString(9, componente.getUnidad());
			preparedStatement.setString(10, componente.getDimension());
			preparedStatement.setString(11, componente.getDescripcion());			
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA COMPONENTES
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query = "";
		ArrayList<Object> listaPartes = new ArrayList<Object>();
		if (campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query = "SELECT * FROM componentes ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);  
				Componente componente = null;
				while (resultSet.next()) {
					componente = new Componente();
					componente.setSysPk(resultSet.getInt(1));
					componente.setAcabadoFk(resultSet.getInt(2));
					componente.setClienteFk(resultSet.getInt(3));
					componente.setMaterialFk(resultSet.getInt(4));
					componente.setTipoMiscelaneoFk(resultSet.getInt(5));
					componente.setTipoMateriaPrimaFk(resultSet.getInt(6));
					componente.setTipoProductoFk(resultSet.getInt(7));
					componente.setTratamientoFk(resultSet.getInt(8));
					componente.setCosto(resultSet.getDouble(9));
					componente.setUnidad(resultSet.getString(10));
					componente.setDimension(resultSet.getString(11));
					componente.setDescripcion(resultSet.getString(12));					
					listaPartes.add(componente);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else {
			query = "SELECT * FROM componentes WHERE " + campoBusqueda + " = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				Componente componente = null;
				while (resultSet.next()) {
					componente = new Componente();
					componente.setSysPk(resultSet.getInt(1));
					componente.setAcabadoFk(resultSet.getInt(2));
					componente.setClienteFk(resultSet.getInt(3));
					componente.setMaterialFk(resultSet.getInt(4));
					componente.setTipoMiscelaneoFk(resultSet.getInt(5));
					componente.setTipoMateriaPrimaFk(resultSet.getInt(6));
					componente.setTipoProductoFk(resultSet.getInt(7));
					componente.setTratamientoFk(resultSet.getInt(8));
					componente.setCosto(resultSet.getDouble(9));
					componente.setUnidad(resultSet.getString(10));
					componente.setDimension(resultSet.getString(11));
					componente.setDescripcion(resultSet.getString(12));					
					listaPartes.add(componente);
				}//FIN WHILE
			}catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		}//FIN IF/ELSE
		return listaPartes;
	}//FIN METODO	
	
	//METODO PARA HACER UPDATE EN LA TABLA COMPONENTES
	@Override
	public boolean modificar(Connection connection, Object Componente) {
		String query = "UPDATE componentes "
				+ "SET  acabadoFk = ?, clienteFk = ?, materialFk = ?, tipoMiscelaneoFk = ?, tipoMateriaPrimaFk = ?, "
				+ "tipoProductoFk = ?, tratamientoFk = ?, costo = ?, unidad = ?, dimension = ?, descripcion = ? "
				+ "WHERE sysPK = ?";
		try {
			Componente componente = (Componente)Componente;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, componente.getAcabadoFk());
			preparedStatement.setInt(2, componente.getClienteFk());
			preparedStatement.setInt(3, componente.getMaterialFk());
			preparedStatement.setInt(4, componente.getTipoMiscelaneoFk());
			preparedStatement.setInt(5, componente.getTipoMateriaPrimaFk());
			preparedStatement.setInt(6, componente.getTipoProductoFk());
			preparedStatement.setInt(7, componente.getTratamientoFk());
			preparedStatement.setDouble(8, componente.getCosto());
			preparedStatement.setString(9, componente.getUnidad());
			preparedStatement.setString(10, componente.getDimension());
			preparedStatement.setString(11, componente.getDescripcion());			
			preparedStatement.setInt(12, componente.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER DELETE EN LA TABLA COMPONENTES
	@Override
	public boolean eliminar(Connection connection, Object Componente) {
		String query = "DELETE FROM componentes WHERE sysPK = ?";
		try {	
			Componente componente = (Componente)Componente;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, componente.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO		
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public int ultimoSysPk(Connection connection) {
		String query = "SELECT sysPK FROM componentes order by sysPK asc";
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