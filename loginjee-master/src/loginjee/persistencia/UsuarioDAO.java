package loginjee.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import loginjee.bean.Usuario;

public class UsuarioDAO {
	
	//DAO Data ACCESS OBJEECT
	//ESTA CLASE, SERÁ INVOCADA POR EL SERVICIO
	
	private final static Logger log = Logger.getLogger("mylog");
	private static final String INSTRUCCION_CONSULTA_USUARIO = "SELECT * FROM hedima.usuarios WHERE (nombre = ?);";
	private static final String INSTRUCCION_CONSULTA_TODOS_USUARIO = "SELECT * FROM hedima.usuarios;";
	private static final String INSTRUCCION_CONSULTA_USUARIO_POR_ID = "SELECT * from hedima.usuarios where idusuarios = ?;";
	private static final String INSTRUCCION_INSERCION_USUARIO = "INSERT INTO hedima.usuarios (nombre, password) VALUES (?, ?);";
	public final static  String BUSCAR_USUARTIOS_POR_PATRON_NOMBRE = "SELECT nombre FROM hedima.usuarios WHERE nombre like ?;"; 
	
	
	public Usuario obtenerUsuario (String nombre) throws Exception
	{
		Usuario usuario = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
			try {
				connection = BaseDeDatos.getConnection();
				preparedStatement = connection.prepareStatement(INSTRUCCION_CONSULTA_USUARIO);
				preparedStatement.setString(1, nombre);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next())
				{
					usuario = new Usuario(resultSet);
				}
				
			}catch (Exception e) {
				// TODO: handle exception
				log.error("Error al recuperar usuario por nombre ", e);
				throw e;
			}finally {
				BaseDeDatos.liberarRecursos(connection, preparedStatement, resultSet);
			}
		
		return usuario;
	}
	
	public Map<Integer, Usuario> obtenerMapaUsuariosBD () throws Exception
	{
		Map<Integer, Usuario> mu = new HashMap<Integer, Usuario>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Usuario usuario_aux = null;
		
			try {
				
				connection = BaseDeDatos.getConnection();
				statement = connection.createStatement();
				resultSet = statement.executeQuery(INSTRUCCION_CONSULTA_TODOS_USUARIO);
				
				while (resultSet.next()) {
					usuario_aux = new Usuario(resultSet);
					mu.put(usuario_aux.getId(), usuario_aux);
				}
				
			}catch (Exception e) {
				log.error("Error al acceder a la base de datos", e);
				throw e;
			}finally {
				BaseDeDatos.liberarRecursos(connection, statement, resultSet);
			
			}
		
		return mu;
		
	}

	
	public Usuario leerUsuarioBD (int id) throws Exception
	{
		Usuario usuario = null;
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try{
			String consulta_SQL = INSTRUCCION_CONSULTA_USUARIO_POR_ID;
			connection = BaseDeDatos.getConnection();
			st = connection.prepareStatement(consulta_SQL);
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next())
			{
				usuario = new Usuario(rs);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		} finally{
			BaseDeDatos.liberarRecursos(connection, st, rs);
		}
		return usuario;
	}
	
	public void altaUsuarioBD (Usuario usuario) throws Exception
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = BaseDeDatos.getConnection();
			preparedStatement = connection.prepareStatement(INSTRUCCION_INSERCION_USUARIO);
			preparedStatement.setString(1, usuario.getNombre());
			preparedStatement.setString(2, usuario.getPwd());
			int resultado = preparedStatement.executeUpdate();// siempre executeUpdate para INSERTAR; DELETE; o UPDATE para SELECT executeQuery()
			log.debug("resultado insertar = " + resultado);
			
		}catch (Exception e) {
			// TODO: handle exception
			log.error("Error en la insercion de usuario " + usuario , e);
			throw e;
		}finally {
			BaseDeDatos.liberarRecursos(connection, preparedStatement, null);
		}
	}
	
	public List<Usuario> obtenerTodos () throws Exception
	{
		List<Usuario> lista_usuarios = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Usuario usuario_aux = null;
		
		
			try {
				
				connection = BaseDeDatos.getConnection();
				statement = connection.createStatement();
				resultSet = statement.executeQuery(INSTRUCCION_CONSULTA_TODOS_USUARIO);
				
				lista_usuarios = new ArrayList<Usuario>();//creo la lista vacía
				while (resultSet.next()) {
					usuario_aux = new Usuario(resultSet);
					lista_usuarios.add(usuario_aux);
				}
				
			}catch (Exception e) {
				log.error("Error al acceder a la base de datos", e);
				throw e;
			}finally {
				BaseDeDatos.liberarRecursos(connection, statement, resultSet);
			
			}
		
		return lista_usuarios;
		
	}
	
	public List<String> consultaNombresPorPatron (String nombre) throws Exception
	{
		List<String> lista_usuarios = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String nombre_aux = null;
		
		
			try {
				
				connection = BaseDeDatos.getConnection();
				statement = connection.prepareStatement(BUSCAR_USUARTIOS_POR_PATRON_NOMBRE);
				//statement.setString(1, "%"+nombre+"%");
				statement.setString(1, nombre+"%");
				resultSet = statement.executeQuery();
				
				lista_usuarios = new ArrayList<String>();//creo la lista vacía
				while (resultSet.next()) {
					nombre_aux = resultSet.getString(1);
					lista_usuarios.add(nombre_aux);
				}
				
			}catch (Exception e) {
				log.error("Error al acceder a la base de datos", e);
				throw e;
			}finally {
				BaseDeDatos.liberarRecursos(connection, statement, resultSet);
			
			}
		
		return lista_usuarios;
		
	}
	
	/**
	 * Método que accede a la base de datos y comprueba si el usuario recibido existe
	 * @param usuario el usuario que queremos comprobar
	 * @return 0 si existe 1 si falla el pwd (pero existe el nombre) 2 no existe (no coincide ni nombre ni pwd)
	 * @throws Exception si hubo fallo con la base de datos
	 */
	public int  existeUsuarioBD (Usuario usuario) throws Exception
	{
		int num_dev = -1;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		

		try {
			 log.debug("Entramos en existeUsuarioBD");
			 connection = BaseDeDatos.getConnection();
			 ps = connection.prepareStatement(UsuarioDAO.INSTRUCCION_CONSULTA_USUARIO);
			 ps.setString(1, usuario.getNombre());
			 rs = ps.executeQuery();
			 if (rs.next())
			 {
				log.debug("hay un usuario que coincide por el nombre");
				Usuario usuario2 = new Usuario(rs);
				System.out.println("USUARIO RECUPERADO DE LA BD " + usuario2);
				if (usuario2.getPwd().equals(usuario.getPwd()))
				{
					log.debug ("hay un usuario que coincide por el nombre y por la contraseña");
					num_dev = 0;
				} else 
				{
					log.debug ("hay un usuario que coincide por el nombre pero NO por la contraseña");
					num_dev = 1;
				}
				
				//status = HttpsURLConnection.HTTP_OK;//is deprecated DEPRECADO
			} else 
			{
				log.debug("El usuario NO existe CON ESE NOMBRE");
				num_dev = 2;
			}
			
		}catch (Exception e) {
			log.error("Error al acceder a la base de datos", e);
			throw e;
			
		}
		finally {
			BaseDeDatos.liberarRecursos(connection, ps, rs);
			
		}
		
		log.debug("valor devuleto " + num_dev);
		
		return num_dev;
	}

}
