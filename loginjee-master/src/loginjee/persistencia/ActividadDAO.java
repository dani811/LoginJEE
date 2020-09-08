package loginjee.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 
 * @author vale
 *
 *clase que agrupa las operaciones en base de datos sobre la tabla ACTIVIDAD
 */
public class ActividadDAO {

	
	private static final String INSTRUCCION_INSERCION_ACTIVIDAD = "INSERT INTO hedima.actividad (nombreservicio, idsesion) VALUES (?, ?);";
	
	
	private final static Logger log = Logger.getLogger("mylog");
	
	public void guardarActividadesSesion (List<String> lista_actividades, int id_sesion) throws Exception
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			
			connection = BaseDeDatos.getConnection();
			preparedStatement = connection.prepareStatement(INSTRUCCION_INSERCION_ACTIVIDAD);
			for (String nactividad : lista_actividades)
			{
				preparedStatement.setString(1,  nactividad);
				preparedStatement.setInt(2,  id_sesion);
				preparedStatement.executeUpdate();
				
			}
			
		} catch (Exception e) {
			log.error("Error guardando actividades ", e);
			throw e;
			
		} finally {
			BaseDeDatos.liberarRecursos(connection, preparedStatement, null);
		}
	}
	
	public void guardarActividadesSesion (List<String> lista_actividades, int id_sesion, Connection connection) throws Exception
	{
		PreparedStatement preparedStatement = null;
		
		try {
			
			preparedStatement = connection.prepareStatement(INSTRUCCION_INSERCION_ACTIVIDAD);
			for (String nactividad : lista_actividades)
			{
				preparedStatement.setString(1,  nactividad);
				preparedStatement.setInt(2,  id_sesion);
				preparedStatement.executeUpdate();
				
			}
			
		} catch (Exception e) {
			log.error("Error guardando actividades ", e);
			throw e;
			
		} finally {
			BaseDeDatos.liberarRecursos(null, preparedStatement, null);
		}
	}
}
