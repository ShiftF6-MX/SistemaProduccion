package mx.shf6.produccion.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.RolGrupoUsuario;
import mx.shf6.produccion.model.dao.ObjectDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RolGrupoUsuarioDAO implements ObjectDAO{
	
	//METODO PARA HACER CREATE EN LA TABLA ROLGRUPOSUSUARIO 
	@Override
	public boolean crear(Connection connection, Object rolgrupousuario){
		RolGrupoUsuario rolGrupoUsuario=(RolGrupoUsuario)rolgrupousuario;
		String query=" INSERT INTO rolgruposusuario (grupoUsuario, rol) values ( ?, ?)";
		try {			
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, rolGrupoUsuario.getGrupoUsuario(connection).getSysPk());
			preparedStatement.setInt(2, rolGrupoUsuario.getRol(connection).getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error: En método crear");
			e.printStackTrace();
			return false;
		}		
	}//FIN METODO
	
	
	//METOSO PARA HCER SELECT EN LA TABLA ROLGRUPOSUSUARIO
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query ="";
		ArrayList<Object> listaRolGrupoUsuario = new ArrayList<Object>();
		if(campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query="SELECT * FROM rolgruposusuario ORDER BY sysPK";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
				RolGrupoUsuario rolGrupoUsuario = null;
				while (resultSet.next()) {	
					rolGrupoUsuario = new RolGrupoUsuario();	
					rolGrupoUsuario.setSysPk(resultSet.getInt(1));
					rolGrupoUsuario.setGrupoUsuarioFk(resultSet.getInt(2));
					rolGrupoUsuario.setRolFk(resultSet.getInt(2));
					listaRolGrupoUsuario.add(rolGrupoUsuario);
				}
			}catch (SQLException e) {
				System.out.println("Error: En método leer");
				e.printStackTrace();
			}
		}else {
			query="SELECT * FROM rolgruposusuario WHERE "+campoBusqueda+" = ?  ORDER BY sysPK";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				RolGrupoUsuario rolGrupoUsuario =null;
				while (resultSet.next()) {	
					rolGrupoUsuario = new RolGrupoUsuario();	
					rolGrupoUsuario.setSysPk(resultSet.getInt(1));
					rolGrupoUsuario.setGrupoUsuarioFk(resultSet.getInt(2));
					rolGrupoUsuario.setRolFk(resultSet.getInt(2));
					listaRolGrupoUsuario.add(rolGrupoUsuario);
				}				
			}catch (SQLException e) {
				System.out.println("Error: En método leer");
				e.printStackTrace();				
			}			
		}
		return listaRolGrupoUsuario;		
	}//FIN METODO
	

	//METODO PARA HACER UPDATE EN LA TABLA ROLGRUPOSUSUARIO
	@Override
	public boolean modificar(Connection connection, Object rolgrupousuario){
		String query="UPDATE rolgruposusuario SET grupoUsuario= ?, rol= ? WHERE sysPK= ?;";
		try {
			RolGrupoUsuario rolGrupoUsuario=(RolGrupoUsuario)rolgrupousuario;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);			
			preparedStatement.setInt(1, rolGrupoUsuario.getGrupoUsuario(connection).getSysPk());
			preparedStatement.setInt(2, rolGrupoUsuario.getRol(connection).getSysPk());
			preparedStatement.setInt(3, rolGrupoUsuario.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error: En método modificar");
			e.printStackTrace();
			return false;			
		}
	}//FIN METODO
	

	//METODO PARA HACER DELETE EN LA TABLA ROLGRUPOSUSUARIO
	@Override
	public boolean eliminar(Connection connection, Object rolgrupousuario) {
		String query=" DELETE FROM rolgruposusuario WHERE sysPK= ?";
		try {		
			RolGrupoUsuario rolGrupoUsuario=(RolGrupoUsuario)rolgrupousuario;
			PreparedStatement preparedStmt = (PreparedStatement) connection.prepareStatement(query);
			preparedStmt.setInt(1, rolGrupoUsuario.getSysPk());
			preparedStmt.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error: En método eliminar");
			e.printStackTrace();
			return false;
		}			
	}//FIN METODO
	
}//FIN CLASE
