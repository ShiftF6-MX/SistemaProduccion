package mx.shf6.produccion.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.GrupoUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class GrupoUsuarioDAO implements ObjectDAO {
	
	//METODO PARA HACER CREATE EN TABLA GRUPOUSUARIO
	@Override
	public boolean crear(Connection connection, Object grupousuario) {
		GrupoUsuario grupoUsuario=(GrupoUsuario)grupousuario;
		String query=" INSERT INTO gruposusuario (nombre, descripcion) values ( ?, ?)";
		try {		
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, grupoUsuario.getNombre());
			preparedStatement.setString(2, grupoUsuario.getDescripcion());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error: En método crear");
			e.printStackTrace();
			return false;
		}
	}//FIN METODO
	
	
	//METODO PARA HACER SELECT EN LA TABLA GRUPOSUSUARIO
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query ="";
		ArrayList<Object> listaGrupoUsuario = new ArrayList<Object>();
		if(campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query="SELECT * FROM gruposusuario ORDER BY sysPK";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
				GrupoUsuario grupoUsuario = null;
				while (resultSet.next()) {
					grupoUsuario = new GrupoUsuario();
					grupoUsuario.setSysPk(Integer.parseInt(resultSet.getString(1)));
					grupoUsuario.setNombre(resultSet.getString(2));
					grupoUsuario.setDescripcion(resultSet.getString(3));
					listaGrupoUsuario.add(grupoUsuario);
				}
			}catch (SQLException e) {
					System.out.println("Error: En método leer");
					e.printStackTrace();
			}
		}else {
			query="SELECT * FROM gruposusuario WHERE "+campoBusqueda+" = ?  ORDER BY sysPK";
			
			try {
				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				GrupoUsuario grupoUsuario = null;
				while (resultSet.next()) {
					grupoUsuario = new GrupoUsuario();
					grupoUsuario.setSysPk(resultSet.getInt(1));
					grupoUsuario.setNombre(resultSet.getString(2));
					grupoUsuario.setDescripcion(resultSet.getString(3));
					listaGrupoUsuario.add(grupoUsuario);
				}
			}catch (SQLException e) {
					System.out.println("Error: En método leer");
					e.printStackTrace();
			}
		}
		return listaGrupoUsuario;
	}//FIN METODO
	
	//METODO PARA HACER UPDATE EN LA TABLA GRUPOSUSUARIO
	@Override
	public boolean modificar(Connection connection, Object grupousuario) {
		String query="UPDATE gruposusuario SET nombreGrupo= ?, descripcion= ? WHERE sysPK= ?;";
		try {
			GrupoUsuario grupoUsuario=(GrupoUsuario)grupousuario;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, grupoUsuario.getNombre());
			preparedStatement.setString(2, grupoUsuario.getDescripcion());	
			preparedStatement.setInt(3, grupoUsuario.getSysPk());
			preparedStatement.execute();
			return true;
			} catch (SQLException e) {
				System.out.println("Error: En método modificar");
				e.printStackTrace();
				return false;
			}
	}//FIN METODO
	
	//METODO PARA HACER DELETE EN LA TABLA GRUPOSUSUARIO
	@Override
	public boolean eliminar(Connection connection, Object grupousuario) {
		String query=" DELETE FROM gruposusuario WHERE sysPK= ?";
		try {			
			GrupoUsuario grupoUsuario=(GrupoUsuario)grupousuario;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, grupoUsuario.getSysPk());
			preparedStatement.execute();
			return true;
			} catch (SQLException e) {
				System.out.println("Error: En método eliminar");
				e.printStackTrace();
				return false;
			}	
	}//FIN METODO
	
	//METODO PARA CONVERTIR UNA ARRAYLIST A OBSERVABLELIST
	public ObservableList<GrupoUsuario> toObservableList(ArrayList<Object> arrayList) {
		ObservableList<GrupoUsuario> grupoUsuarioData = FXCollections.observableArrayList();         
     	for(Object usuario : arrayList) {
     		grupoUsuarioData.add((GrupoUsuario) usuario);
     	}//FIN FOR
     	return grupoUsuarioData;
	}//FIN METODO
	
	//METODO PARA CONVERTIR UNA ARRAYLIST A OBSERVABLELIST
		public ObservableList<String> nombresGruposUsuario(ArrayList<Object> arrayList) {
			ObservableList<String> gruposUsuarioData = FXCollections.observableArrayList();         
	     	for(Object gruposUsuario : arrayList) {
	     		gruposUsuarioData.add(((GrupoUsuario)gruposUsuario).getNombre());
	     	}//FIN FOR
	     	return gruposUsuarioData;
		}//FIN METODO
	
}//FIN CLASE