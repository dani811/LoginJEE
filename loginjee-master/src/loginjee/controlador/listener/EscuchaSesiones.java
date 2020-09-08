package loginjee.controlador.listener;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import loginjee.bean.SesionBean;
import loginjee.servicio.SeguimientoUsuario;

/**
 * Application Lifecycle Listener implementation class EscuchaSesiones
 *
 */
@WebListener
public class EscuchaSesiones implements HttpSessionListener {

	
	
    /**
     * Default constructor. 
     */
    public EscuchaSesiones() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent eventosesion)  { 
         // TODO Auto-generated method stub
    	System.out.println("CREADA UNA NUEVA SESIÓN");
    	System.out.println("IDSession = " + eventosesion.getSession().getId() + " " +new Date());
    	
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent eventosesion)  { 
         // TODO Auto-generated method stub
    	System.out.println("DESTRUIDA LA SESIÓN");
    	System.out.println("IDSession = " + eventosesion.getSession().getId() + " " +new Date());
    	SeguimientoUsuario.mostrarActividad(eventosesion.getSession());//TODO
    	SeguimientoUsuario.guardarActividad(eventosesion.getSession());
    	
    	ServletContext sc = eventosesion.getSession().getServletContext();
    	int numusuarios = (int)sc.getAttribute("NUM_USUARIOS");
		numusuarios = numusuarios-1;
		sc.setAttribute("NUM_USUARIOS", numusuarios);
    	
    	//TODO registrar esta actividad en la base de datos
    	//1DEFINIR LAS NUEVAS TABLA O TABLA
    		//NOMBRE TABLA(S) ATRIBUTOS (COLUMNAS) RELACIONES CON OTRAS TABLAS
    		
    	/*	PEDRO'S SOLUTION (Fan de SOnia)
    		----------------
    		tabla actividad
    			id_actividad : int PK clave primaria
    			id_usuario : int FK clave ajena
    			inicio_conexion : date timestamp 
    			actividad : VARCHAR nombre de los servicios (separados por coma,espacio)
    			fin de_conexion : date timestamp
    		
    		ELO'S SOLUTION (Fan de Pedro y de SOnia)
        	----------------
        	tabla trayectoria
        		id_trayectoria : int PK clave primaria
        		id_usuario : int FK clave ajena
        		id_sesion : VARCHAR
        		trayectoria : VARCHAR nombre de los servicios (separados por coma,espacio)
        		
    	
    		SONIA'S SOLUTION (BIEN PERO INCOMPLETA)
    		----------------
    		tabla actividad_usuario_sesion
    			id_usuario
    			id_sesion
    			nombre_servicio
    			fecha_hora_cierre_sesion
    			
    		tabla_servicios
    			id_usuario
    			ListarUsuariosJSP : int
    			ListarUsuarioJSON : int
    			ListarUsuarioJSONMap : int
    			ListarUsuarioJSP : int
    			ListarUsuariosJSON : int
    			
    		VALE'S SOLUTION (Muy madridista 1-3)
    		---------------
    		
    		tabla sesion
    			id_sesion : int PK autoinc clave primaria
    			sesion_http : VARCHAR "el numerito"
    			id_usario : int FK clave ajena a la tabla usuarios
    			fecha_hora_inicio : date
    			fecha_hora_fin : date
    			
    		tabla actividad
    			id_actividad : int PK autoinc clave primaria
    			nombre_servicio : varchar 
    			id_sesion_ int FK clave ajena a la tabla sesion*/
    		
    	//2DEFINIR EN LA PERSISTENCIA EL MÉTODO QUE GUARDE LA ACTIVIDAD DE LA SESIÓN
    	//3DEFINIR EL MÉTODO EN LA CAPA DE SERVICIO QUE USE AL DE LA PERSISTENCIA
    	
    }
	
}
