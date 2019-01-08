package mx.shf6.produccion.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Rol;
import mx.shf6.produccion.model.dao.ObjectDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RolDAO implements ObjectDAO{
	
	//METODO PARA HACER CREATE EN LA TABLA ROLES
	@Override
	public boolean crear(Connection connection, Object rol) {	
		Rol claseRol=(Rol)rol;
		String query=" INSERT INTO roles (codigoItem, descripcion) values ( ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, claseRol.getCodigoItem());
			preparedStatement.setString(2, claseRol.getDescripcion());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error: En método crear");
			e.printStackTrace();
			return false;
		}		
	}//FIN METODO
	
	
	//METODO PARA HACER SELECT EN LA TABLA ROLES
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query ="";
		ArrayList<Object> listaRol = new ArrayList<Object>();
		if(campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query="SELECT * FROM roles ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
				Rol claseRol = null;
				while (resultSet.next()) {	
					claseRol = new Rol();
					claseRol.setSysPk(resultSet.getInt(1));
					claseRol.setCodigoItem(resultSet.getString(2));
					claseRol.setDescripcion(resultSet.getString(3));
					listaRol.add(claseRol);
				}
			}catch (SQLException e) {
				System.out.println("Error: En método leer");
				e.printStackTrace();
			}
		}else {
			query="SELECT * FROM roles WHERE "+campoBusqueda+" = ? ORDER BY sysPK;";
				try {
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, valorBusqueda);
					ResultSet resultSet=preparedStatement.executeQuery();
					Rol claseRol = null;
					while (resultSet.next()) {
						claseRol = new Rol();
						claseRol.setSysPk(resultSet.getInt(1));
						claseRol.setCodigoItem(resultSet.getString(2));
						claseRol.setDescripcion(resultSet.getString(3));
						listaRol.add(claseRol);
					}
				}catch (SQLException e) {
					System.out.println("Error: En método leer");
					e.printStackTrace();
				}
		}
		return listaRol;
	}//FIN METODO
	
	
	//METODO PARA HACER UPDATE EN LA TABLA ROLES
	@Override
	public boolean modificar(Connection connection, Object rol) {
		String query="UPDATE roles SET codigoItem= ?, descripcion= ? WHERE sysPK= ?;";
		try {
			Rol claseRol=(Rol)rol;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, claseRol.getCodigoItem());
			preparedStatement.setString(2, claseRol.getDescripcion());	
			preparedStatement.setInt(3, claseRol.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error: En método modificar");
			e.printStackTrace();
			return false;
		}		
	}//FIN METODO
	
	
	//METODO PARA HACER DELETE EN LA TABLA ROLES
	@Override
	public boolean eliminar(Connection connection, Object rol) {
		String query=" DELETE FROM roles WHERE sysPK= ?";
		try {		
			Rol claseRol=(Rol)rol;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, claseRol.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error: En método eliminar");
			e.printStackTrace();
			return false;
		}	
	}//FIN METODO
	
}//FIN CLASE