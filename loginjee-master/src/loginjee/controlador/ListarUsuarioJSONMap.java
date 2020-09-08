package loginjee.controlador;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import loginjee.bean.Usuario;
import loginjee.servicio.SeguimientoUsuario;
import loginjee.servicio.UsuarioService;

/**
 * Servlet implementation class ListarUsuario
 */
@WebServlet("/ListarUsuarioJSONMap")
public class ListarUsuarioJSONMap extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * TODO Se pide hacer otro servlet con idéntico funcionamiento.
	 * Es decir, misma entrada (parámetro ID del usuario)
	 * Misma salida, JSON con la info del usuaario
	 * Pero en vez de consultar la base de datos, que recupere la
	 * info del usuario de un Map<Integer, Usuario> previamente
	 * cargado en el contexto
	 */
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarUsuarioJSONMap() {
        super();
        // TODO Auto-generated constructor stub
    }
    
   /*TODO haced un SERVLET QUE RX UN ID DE UN USUARIO POR PARÁMETROS (URL)
    Y MOSTRARLO POR PANTALLA CON SYSOUT EN EL DOGET

    HACED LAS CAPAS DE SERVICIO Y PERSISTENCIA POR SEPARADO - SI SE PUEDE-*/

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		SeguimientoUsuario.registrarActividad(request);
		String id_usuario_string = request.getParameter("id");
		int id_usuario = Integer.parseInt(id_usuario_string);
		System.out.println("ID rx =  "+ id_usuario);
		//1 coger el mapa del contexto
		ServletContext sc = this.getServletContext();
		Map<Integer, Usuario> mapa_usuarios = (Map<Integer, Usuario>) sc.getAttribute("er_mapa");
		//del mapa, pillar el usuario
		Usuario usuario = mapa_usuarios.get(id_usuario);
		if (usuario!=null)
		{
			System.out.println("Usuario encontrado");
			Gson gson = new Gson();
			String usuario_json = gson.toJson(usuario);
			
			response.getWriter().append(usuario_json);
			response.setContentType("application/json");
			response.setStatus(HttpURLConnection.HTTP_OK);
			
		}else {
			System.out.println("Usuario No encontrado");
			response.setStatus(HttpURLConnection.HTTP_NO_CONTENT);
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
