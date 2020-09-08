package loginjee.servicio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import loginjee.bean.SesionBean;
import loginjee.bean.Usuario;
import loginjee.persistencia.ActividadDAO;
import loginjee.persistencia.BaseDeDatos;
import loginjee.persistencia.SessionBeanDAO;
import loginjee.persistencia.UsuarioDAO;

public class SeguimientoUsuario {
	
	
	 public static void registrarActividad (HttpServletRequest request)
	    {
	    	HttpSession sesion =  request.getSession(false);//MIRA SI LA PETICIÓN QUE VIENE TRAE SESIÓN y SI NO LA TRAE, NO LE CREES OTRA
			List<String> lista_actividad = (List<String>)sesion.getAttribute("LISTA_ACTIVIDAD");
			String actividad_nueva = request.getServletPath();
			lista_actividad.add(actividad_nueva);
			sesion.setAttribute("LISTA_ACTIVIDAD", lista_actividad);
	    }
	 
	 public static void mostrarActividad (HttpSession session)
	    {
			List<String> lista_actividad = (List<String>)session.getAttribute("LISTA_ACTIVIDAD");
			for (String actividad : lista_actividad)
			{
				System.out.println(actividad);
			}
	    }
	 
	 public static void guardarActividad (HttpSession session) {
		 //aquí tendremos que llamar la capa de persistencia
		 
		 SesionBean sesionBean = (SesionBean)session.getAttribute("INFO_SESION");
		 sesionBean.setTfin(new Date(System.currentTimeMillis()));//asigno la fecha fin
		 Connection connection = null;
		 Savepoint	savepoint = null;
		
		 
		 try {
			 //INICIO TRANSACCIÓN
			 UsuarioDAO usuarioDAO = new UsuarioDAO();
			 Usuario u= usuarioDAO.obtenerUsuario(sesionBean.getNombre_usuario());
			 sesionBean.setIdusuario(u.getId());//asigno el id de usuario
			 
			 connection = BaseDeDatos.getConnection();
			 connection.setAutoCommit(false);
			 SessionBeanDAO sessionBeanDAO = new SessionBeanDAO();
			 //OP1
			 //sessionBeanDAO.insertarInfoSesion(sesionBean);//TODO mejorar USAR EL TIMESTAMP??registro con el TIEMPO de la sesión. NO SE ESTÄ guardando
			 int idsesion = sessionBeanDAO.insertarInfoSesion(sesionBean, connection);//TODO mejorar USAR EL TIMESTAMP??registro con el TIEMPO de la sesión. NO SE ESTÄ guardando
			 //savepoint = connection.setSavepoint();
			 connection.commit();
			 
			 //TODO REVISAR EL ULTIMO EL ID GENERADO
			 //necesitamos el id de la sesion
			 //TODO buscar forma alternativa de obtener el último ID generado por la base de datos
			 //int idsesion = sessionBeanDAO.obtenerSesionBeanPorSesionHTTP(sesionBean.getSesionhttp());
			 
			 //TODO INSERTAR ACTIVIDADES
			 ActividadDAO actividadDAO = new ActividadDAO();
			 List<String> lista_actividad = (List<String>)session.getAttribute("LISTA_ACTIVIDAD");
			 //OP2
			 //actividadDAO.guardarActividadesSesion(lista_actividad, idsesion);
			 actividadDAO.guardarActividadesSesion(lista_actividad, idsesion, connection);
			 //FIN TRANSACCIÓN
			 connection.commit();
		 }catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				//connection.rollback();
				connection.rollback(savepoint);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			BaseDeDatos.liberarRecursos(connection, null, null);
		}
		 
		
	 }

}
