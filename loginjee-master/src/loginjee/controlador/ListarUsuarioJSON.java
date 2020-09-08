package loginjee.controlador;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import loginjee.bean.Usuario;
import loginjee.servicio.SeguimientoUsuario;
import loginjee.servicio.UsuarioService;

/**
 * Servlet implementation class ListarUsuario
 */
@WebServlet("/ListarUsuarioJSON")
public class ListarUsuarioJSON extends HttpServlet {
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
    public ListarUsuarioJSON() {
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
		String id_usuario_string = request.getParameter("id");
		int id_usuario = Integer.parseInt(id_usuario_string);
		System.out.println("ID rx =  "+ id_usuario);
		
		//vamos a ver la sesión que se ha creado
		//para comprobar si existe o no la sesión, tenemos que usar getsession con false
		SeguimientoUsuario.registrarActividad(request);
		
			
	
		//MVC 
		/**
		 * Controlador --> Servlet
		 * Modelo --> Servicio y Persistencia
		 * Vista --> JSP
		 */
		UsuarioService usuarioService = new UsuarioService();
		try {
			Usuario u = usuarioService.obtenerUsuario(id_usuario);
			if (u!=null)
			{
			System.out.println("Usuario obtenido = " + u);
			//TODO PASARLE AL JSP EL BEAN DE USUARIO
			
			//HACER UN JSON Y DEVOLVERLO
			Gson gson = new Gson();
			String usuario_json = gson.toJson(u);
			
			response.getWriter().append(usuario_json);
			response.setContentType("application/json");
			response.setStatus(HttpURLConnection.HTTP_OK);
			} else {
				response.setStatus(HttpURLConnection.HTTP_NO_CONTENT);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
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
