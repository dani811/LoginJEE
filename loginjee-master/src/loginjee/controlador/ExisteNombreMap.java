package loginjee.controlador;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import loginjee.bean.Usuario;

/**
 * Servlet implementation class ExisteNombre
 */
@WebServlet("/ExisteNombreMap")
public class ExisteNombreMap extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static Logger log = Logger.getLogger("mylog");

       
	/**
	 * TODO haced una versión alternativa a ExisteNombre, llamada ExisteNombreMap
	 * que consulte el mapa del contexto en vez de la base de datos
	 * parar determinar si un nombre está disponible o no
	 */
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExisteNombreMap() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private boolean nombreDisponible (String nombre)
    {
    	boolean disponible = true;
    	Map<Integer, Usuario> ermapa = null;
    	ServletContext servletContext = null;
    	Integer clave_aux = 0;
    	Usuario usuario = null;
    	String nombre_actual = null;
    	
    		servletContext = this.getServletContext();
    		ermapa = (Map<Integer, Usuario>)servletContext.getAttribute("er_mapa");
    		//tengo que recorrer el mapa hasta que lo encuentro se acabe el mapa
    		Set<Integer> conjunto_claves = ermapa.keySet();
    		Iterator<Integer> iterador = conjunto_claves.iterator();
    		while (iterador.hasNext()&&disponible)
    		{
    			clave_aux = iterador.next();
    			usuario = ermapa.get(clave_aux);
    			nombre_actual = usuario.getNombre();
    			if (nombre_actual.equals(nombre))
    			{
    				disponible=false;
    			}
    		}
    		
    	return disponible;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String nombre = request.getParameter("nombre");
		int status = 0;
		//tengo que ver si ese nombre está en bd
		try {
			 if (this.nombreDisponible(nombre))
			 {
				 status = HttpURLConnection.HTTP_OK;//200 es que está disponible
			 } else {
				 
				 status = HttpURLConnection.HTTP_CONFLICT;//409 es que no ESTÁ DISPONIBLE
			 }
			
		} catch (Exception e) {
			status = HttpURLConnection.HTTP_INTERNAL_ERROR;//500
			log.error("Error al consultar si nombre disponible ", e);
		}
		response.setStatus(status);//indico el status en la respuesta
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
	}

}
