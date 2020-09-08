package loginjee.persistencia;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class BaseDeDatos {

	//CARGAMOS EL POOL DE CONEXIONES DEFINIDO EN El fichero META-INF/context.xml
	
	private static final String fuente = "java:comp/env/jdbc/pool";
	private final static Logger log = Logger.getLogger("mylog");
	private static DataSource fuenteDatos = null;

	
	private static DataSource iniciarFuente() {
		DataSource ds = null;

		try {
			InitialContext ct = new InitialContext();
			ds = (DataSource) ct.lookup(fuente);
		} catch (Exception e) {
			log.error("Error al acceder a los recursos del context.xml para configurar el Pool de la base de datos", e);
		}
		return ds;
	}
	
	//PATRÓN SINGLETON--> SÓLO PUEDE HABER UNA INSTANCIA DE LA CLASE. UN ÚNICO OBJETO
	//HACER EL CONSTRUCTOR PRIVATE Y ASÍ EVITAMOS QUE SE PUEDAN CREAR INSTANCIAS DESDE FUERA
	
	private BaseDeDatos() {
		fuenteDatos = iniciarFuente();
	}
	
	static
	{
		new BaseDeDatos();
	}

	

	public static Connection getConnection() {
		Connection conexion = null;
		try {
			conexion = fuenteDatos.getConnection();
		} catch (Exception e) {
			log.error("Imposible obtener conexión a la base de datos", e);
		}
		return conexion;
	}

	public static void liberarRecursos(Connection conexion, Statement st, ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			}

			catch (SQLException e) {
				log.error("Error al liberar el ResultSet", e);
			}
		}
		if (st != null) {
			try {
				st.close();
			}

			catch (SQLException e) {
				log.error("Error al liberar el Statement", e);
			}
		}

		if (conexion != null) {
			try {
				conexion.close();
			}

			catch (SQLException e) {
				log.error("Error al liberar la conexion", e);
			}
		}
	}

}
