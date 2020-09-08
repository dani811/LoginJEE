package loginjee.controlador.listener;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import loginjee.bean.Usuario;
import loginjee.persistencia.UsuarioDAO;

/**
 * Application Lifecycle Listener implementation class EscuchaInicioYFinContexto
 *
 */
@WebListener
public class EscuchaInicioYFinContexto implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public EscuchaInicioYFinContexto() {
        // TODO Auto-generated constructor stub
    	//System.out.println("Constructor ");
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("Contexto Destruido - App finalizada");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("Contexto Creado - App lanzada");
    	/*El Contexto es como un saco (es una estructura de datos, un objeto)
    	 * que se crea al principio (al iniciar la APP)
    	 * y se mantiene vivo hasta el final (cuando el servidor para la app)
    	 * Me viene bien, para tener un sitio donde guardar
    	 * cosas, que dure más que un Servlet
    	 * 
    	 */
    	ServletContext sc = arg0.getServletContext(); 
    	//TODO contar todas las veces que alguien se Loguea
    	//por cualquier usuario
    	int total_logins = 0;
    	
    	sc.setAttribute("NUM_LOGINS", total_logins);
    	int num_usuarios_activos = 0;
    	sc.setAttribute("NUM_USUARIOS", num_usuarios_activos);
    	
    	
    	//GUARDAR EL MAPA EN EL CONTEXTO
    	UsuarioDAO usuarioDAO = new UsuarioDAO();
    	try {
			Map<Integer, Usuario> mu = usuarioDAO.obtenerMapaUsuariosBD();
			sc.setAttribute("er_mapa", mu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    }
	
}
