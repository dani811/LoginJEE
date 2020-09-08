package loginjee.controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import loginjee.bean.Usuario;
import loginjee.servicio.SeguimientoUsuario;
import loginjee.servicio.UsuarioService;

/**
 * Servlet implementation class ListarUsuario
 */
@WebServlet("/ListarUsuarioJSP")
public class ListarUsuarioJSP extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarUsuarioJSP() {
        super();
        // TODO Auto-generated constructor stub
    }
    
   /**
    * PONED UN LÍMITE DE USO DEL SERVICIO ListarUsuarioJSP
    * LA tercera vez que un mismo usuario acceda a este servlet, 
    * deberá ser redirifido a una página de error, que le informe
    * que ha excedido el uso de este servicio
    */

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
		//MVC 
		/**
		 * Controlador --> Servlet
		 * Modelo --> Servicio y Persistencia
		 * Vista --> JSP
		 */
		
		UsuarioService usuarioService = new UsuarioService();
		try {
			Usuario u = usuarioService.obtenerUsuario(id_usuario);
			System.out.println("Usuario obtenido = " + u);
			//TODO PASARLE AL JSP EL BEAN DE USUARIO
			
			request.setAttribute("usuario", u);//le meto el objeto usuario en la request
			request.getRequestDispatcher("verusuario.jsp").forward(request, response);//dirijo el servlet al jsp
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
