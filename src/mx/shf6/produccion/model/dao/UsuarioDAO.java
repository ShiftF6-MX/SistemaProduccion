package mx.shf6.produccion.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.Usuario;
import mx.shf6.produccion.model.dao.ObjectDAO;
import mx.shf6.produccion.utilities.Notificacion;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UsuarioDAO implements ObjectDAO {
	
	//METODO PARA HACER CREATE EN LA TABLA USUARIOS
	@Override
	public boolean crear(Connection connection, Object usuario){	
		Usuario claseUsuario = (Usuario) usuario;
		String query=" INSERT INTO usuarios (usuario, contrasena, correoElectronico, fechaRegistro, status, grupoUsuario)"
		+ " values ( ?, aes_encrypt(?, 'ShiftF6'), ?, curdate(), ?, ?)";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, claseUsuario.getUsuario());
			preparedStatement.setString(2, claseUsuario.getContrasena());
			preparedStatement.setString(3, claseUsuario.getCorreoElectronico());
			preparedStatement.setInt(4, claseUsuario.getStatus());
			preparedStatement.setInt(5, claseUsuario.getGrupoUsuarioFk());
			preparedStatement.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	
	//METODO PARA HACER SELECT EN LA TABLA USUARIOS
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query = "";
		ArrayList<Object> listaUsuario = new ArrayList<Object>();
		if (campoBusqueda.isEmpty() && valorBusqueda.isEmpty()) {
			query = "SELECT * FROM usuarios ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
				while (resultSet.next()) {
					Usuario usuario = new Usuario();			
					usuario.setSysPk(resultSet.getInt(1));
					usuario.setUsuario(resultSet.getString(2));
					usuario.setContrasena(resultSet.getString(3));
					usuario.setCorreoElectronico(resultSet.getString(4));
					usuario.setFechaRegistro(resultSet.getDate(5));
					usuario.setFechaBloqueo(resultSet.getDate(6));
					usuario.setStatus(resultSet.getInt(7));
			    	usuario.setGrupoUsuarioFk(resultSet.getInt(8));
					listaUsuario.add(usuario);
				}//FIN WHILE
			} catch (SQLException ex) {
				System.out.println("Error: En método leer");
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else if (campoBusqueda.isEmpty()) {
			query= "SELECT * FROM usuarios WHERE usuario LIKE '%"+valorBusqueda+"%' OR correoElectronico LIKE '%"+valorBusqueda+"%'";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
				Usuario usuario = null;
				while (resultSet.next()) {
					usuario = new Usuario();		
					usuario.setSysPk(Integer.parseInt(resultSet.getString(1)));
					usuario.setUsuario(resultSet.getString(2));;
					usuario.setContrasena(resultSet.getString(3));
					usuario.setCorreoElectronico(resultSet.getString(4));
					usuario.setFechaRegistro(resultSet.getDate(5));
					usuario.setFechaBloqueo(resultSet.getDate(6));
					usuario.setStatus(resultSet.getInt(7));
			    	usuario.setGrupoUsuarioFk(resultSet.getInt(8));
					listaUsuario.add(usuario);
				}//END WHILE
			} catch (SQLException ex) {
				System.out.println("Error: En método leer");
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH			
		} else {
			query = "SELECT * FROM usuarios WHERE "+campoBusqueda+" = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				Usuario usuario = null;
				while (resultSet.next()) {
					usuario = new Usuario();		
					usuario.setSysPk(Integer.parseInt(resultSet.getString(1)));
					usuario.setUsuario(resultSet.getString(2));;
					usuario.setContrasena(resultSet.getString(3));
					usuario.setCorreoElectronico(resultSet.getString(4));
					usuario.setFechaRegistro(resultSet.getDate(5));
					usuario.setFechaBloqueo(resultSet.getDate(6));
					usuario.setStatus(resultSet.getInt(7));
			    	usuario.setGrupoUsuarioFk(resultSet.getInt(8));
					listaUsuario.add(usuario);
				}//END WHILE
			} catch (SQLException ex) {
				System.out.println("Error: En método leer");
				Notificacion.dialogoException(ex);
			}//FIN TRy/CATCH	
		}//FIN IF/ELSE
		return listaUsuario;
	}//FIN METODO	
	
	@Override
	public boolean modificar(Connection connection, Object usuario){
		Usuario claseUsuario = (Usuario)usuario;
		String query = "";
		if (claseUsuario.getStatus().equals(Usuario.BAJA)) {
			query = "UPDATE usuarios  "
					+ "SET usuario = ?, contrasena = aes_encrypt(?, 'ShiftF6'), correoElectronico = ?, fechaBloqueo = CURDATE(), status = ?, grupoUsuario = ? "
					+ "WHERE sysPK = ?;";		
		} else {
			query="UPDATE usuarios  "
					+ "SET usuario = ?, contrasena = aes_encrypt(?, 'ShiftF6'), correoElectronico = ?, fechaBloqueo = null, status = ?, grupoUsuario = ? "
					+ "WHERE sysPK = ?;";		
		}//FIN IF/ELSE
		
		try {					
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, claseUsuario.getUsuario());
			preparedStatement.setString(2, claseUsuario.getContrasena());
			preparedStatement.setString(3, claseUsuario.getCorreoElectronico());
			preparedStatement.setInt(4, claseUsuario.getStatus());
			preparedStatement.setInt(5, claseUsuario.getGrupoUsuarioFk());
			preparedStatement.setInt(6, claseUsuario.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException ex) {
			System.out.println("Error: En método modificar");
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	

	//METODO PARA HACER DELETE EN LA TABLA USUARIOS
	@Override
	public boolean eliminar(Connection connection, Object usuario) {
		String query = "DELETE FROM usuarios WHERE sysPK= ?";
		try {	
			Usuario claseUsuario=(Usuario)usuario;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, claseUsuario.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException ex) {
			System.out.println("Error: En método eliminar");
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH		
	}//FIN METODO
	
	
	//METODO PARA CONVERTIR UNA ARRAYLIST A OBSERVABLELIST
	public ObservableList<Usuario> toObservableList(ArrayList<Object> arrayList) {
		ObservableList<Usuario> usuarioData = FXCollections.observableArrayList(); 
		for(Object usuario : arrayList) {
			usuarioData.add((Usuario) usuario);
		}//FIN FOR
		return usuarioData;
	}//FIN METODO
	
	
	//METODO PARA VALIDAR SI UN USUARIO ESTA REGISTRADO Y/O BLOQUEDO Y SI HA ESCRITO CORRECTAMENTE SU CONTRASEÑA 
	public int validarUsuario(Connection connection, String nombreUsuario, String contrasena) {
		Usuario usuario =new Usuario();
		ArrayList <Object> resultadoUsuario = leer(connection, "usuario", nombreUsuario);
		usuario = (Usuario) resultadoUsuario.get(0);
		if(usuario.getUsuario().equals(nombreUsuario)) {
			if(usuario.getUsuario().equals(nombreUsuario) && usuario.getContrasena().equals(contrasena)){
				if(usuario.getStatus().equals(0)) {
					return 2;//USUARIO BLOQUEADO
				}else {
					return 3;//ACCESO CORRECTO
				}				
			}else {
				return 1;//CONTRASENA INCORRECTA
			}
		}else {
			return 0;//USUARIO NO REGISTRADO
		}				
	}//FIN METODO
	
	//METDODO PARA OBTENER EL ULTIMO SYSPK REGISTRADO EN LA TABAL USUARIOS
	public int ultimoSysPk(Connection connection) {
		String query="SELECT sysPK FROM usuarios order by sysPK asc";
		int ultimoSysPk=0;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				ultimoSysPk=resultSet.getInt(1);
			}//FIN WHILE
			return ultimoSysPk;
		}catch (SQLException e) {
			System.out.println("Error: En método leer");
			e.printStackTrace();
		}//FIN TRY-CATCH
		return ultimoSysPk;
	}//FIN METODO
	
	public String aes_encrypt(Connection connection, String palabra) {
    	String query = "SELECT AES_ENCRYPT('"+palabra+"','ShiftF6')";
    	String desencriptacion = "";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				desencriptacion = resultSet.getString(1);
			}//FIN WHILE
			return desencriptacion;
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return desencriptacion;
		}//FIN TRY-CATCH		
    }//FIN METODO
	
	public String aes_decrypt(Connection connection, String palabra) {
    	String query = "SELECT AES_DECRYPT('"+palabra+"','ShiftF6')";
    	String desencriptacion = "";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				desencriptacion = resultSet.getString(1);
			}//FIN WHILE
			return desencriptacion;
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return desencriptacion;
		}//FIN TRY-CATCH			
    }//FIN METODO

}//FIN CLASE