package loginjee.persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import loginjee.bean.SesionBean;

/**
 * 
 * @author vale
 * 
 * En esta clase agrupamos los métodos relativos a la tabla Sesion en la base de datos
 * 
 *
 */
public class SessionBeanDAO {
	
	
	private static final String INSERTAR_SESION = "INSERT INTO hedima.sesion (idusuario, sesionhttp, tinicio, tfin) VALUES (?, ?, ?, ?);";
	private static final String BUSCAR_IDSESION_POR_HTTPSESION = "SELECT idsesion FROM hedima.sesion WHERE (sesionhttp = ?);";
	private final static Logger log = Logger.getLogger("mylog");
	
	public void insertarInfoSesion (SesionBean sesionBean) throws Exception
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = BaseDeDatos.getConnection();
			
			preparedStatement = connection.prepareStatement(INSERTAR_SESION);
			preparedStatement.setInt(1, sesionBean.getIdusuario());
			preparedStatement.setString(2, sesionBean.getSesionhttp());
			Date fecha_inicio_sql = new Date(sesionBean.getTinicio().getTime());//construyo un tipo sql.Date partiendo de util.Date
			//Date fecha_inicio_sql = new Date(sesionBean.getTinicio().);//construyo un tipo sql.Date partiendo de util.Date
			//Date fecha_fin_sql = new Date(sesionBean.getTfin().getTime());//construyo un tipo sql.Date partiendo de util.Date
			preparedStatement.setDate(3, sesionBean.getTinicio());
			preparedStatement.setDate(4, sesionBean.getTfin());
			int resultado = preparedStatement.executeUpdate();
			log.debug("Resultado = " + resultado);
			
		} catch (Exception e) {
			log.error("Error insertando los datos en la tabla Sesión", e);
			throw e;
			
			
		}finally {
			BaseDeDatos.liberarRecursos(connection, preparedStatement, null);
			
		}
		
	}
	
	private String sqlDate2String (Date feDate) {
		String fecha_tiempo = null;
		
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
				fecha_tiempo = sdf.format(feDate);
			
			return fecha_tiempo;
	}
	
	public int insertarInfoSesion (SesionBean sesionBean, Connection connection) throws Exception
	{
		int id_generado = -1;
		PreparedStatement preparedStatement = null;
		Statement statement = null;
		ResultSet rs= null;
		try {
			
			String fhinicio = sqlDate2String(sesionBean.getTinicio());
			String fhfin = sqlDate2String(sesionBean.getTfin());
			
			String sql = "INSERT INTO hedima.sesion (idusuario, sesionhttp, tinicio, tfin) VALUES ("+sesionBean.getIdusuario() +",'"+sesionBean.getSesionhttp() +"', '"+fhinicio+"' ,'"+fhfin+"');";
			System.out.println(sql);
			statement = connection.createStatement();
			int resultado = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			rs = statement.getGeneratedKeys();
			if (rs.next())
			{
				id_generado = Integer.parseInt(rs.getString(1));
				System.out.println("resultado genkeys " + rs.getString(1));
			}
			log.debug("Resultado = " + resultado);
			
		} catch (Exception e) {
			log.error("Error insertando los datos en la tabla Sesión", e);
			throw e;
			
			
		}finally {
			BaseDeDatos.liberarRecursos(null, preparedStatement, null);
			
		}
		return id_generado;
		
	}
	
	public int obtenerSesionBeanPorSesionHTTP (String sesion_http) throws Exception
	{
		int numsesion = -1;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		
		try {
			connection = BaseDeDatos.getConnection();
			preparedStatement = connection.prepareStatement(BUSCAR_IDSESION_POR_HTTPSESION);
			preparedStatement.setString(1, sesion_http);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				numsesion = resultSet.getInt("idsesion");
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			log.error("Error seleccionando id de la tabla sesion", e);
			throw e;
			
		}finally {
			BaseDeDatos.liberarRecursos(connection, preparedStatement, resultSet);
		}
		
		
		
		
		return numsesion;
	}
	
	
	
	
	
	/**
	 * 
	 * COMPRAR SERVICE
	 * 
	 * COMPRAR	{
	 * 
	 *  inicio transacción
	 *  
	 *  TRY {
	 * 		obtengo conexion
	 * 
	 * 		1MARCA ASIENTO COMO OCUPADO
	 *  	2PAGO CON INTERFAZ BANCARIA
	 *  	3ASIENTO CONTABLE
	 *  	4EMITE EL BILLETE
	 *  
	 *  	commit --confirmo todas las operaciones. Se hacen de verdad
	 *  }
	 *  CATCH{
	 *  	rollback --deshago las operaciones hasta el inicio de la conexion
	 *  }
	 *  
	 *  fin de la trasacción	
	 *  
	 * }
	 */
	
	//APLLICACIONES WEB
	
			//1 peticion (request) --> 1 transacción || Transaction per request
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
