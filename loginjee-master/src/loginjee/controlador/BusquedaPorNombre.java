package loginjee.controlador;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import loginjee.bean.Usuario;
import loginjee.servicio.UsuarioService;

/**
 * Servlet implementation class ExisteNombre
 */
@WebServlet("/BusquedaPorNombre")
public class BusquedaPorNombre extends HttpServlet {
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
    public BusquedaPorNombre() {
        super();
        // TODO Auto-generated constructor stub
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
		UsuarioService usuarioService = new UsuarioService();
		try {
			 Usuario usuario = usuarioService.obtenerUsuario(nombre);
			 if (usuario!=null)
			 {
				 status = HttpURLConnection.HTTP_OK;//200 es que está disponible
				 Gson gson = new Gson();
				 String json_usuario = gson.toJson(usuario);
				 System.out.println("Usuario json = " + json_usuario);
				 response.getWriter().append(json_usuario);
				 response.setContentType("application/json");
				 
			 }
			 
			 else {
				 status = HttpURLConnection.HTTP_NO_CONTENT;//204 es que no ESTÁ DISPONIBLE
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
