package loginjee.controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import loginjee.bean.Usuario;
import loginjee.servicio.UsuarioService;

/**
 * Servlet implementation class AltaUsuario
 */
@WebServlet("/AltaUsuario")
public class AltaUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final static Logger log = Logger.getLogger("mylog");

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AltaUsuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	private boolean validarUsuario(String nombre, String pwd) {
		boolean usuario_valido = false;

		usuario_valido = ((nombre != null) && (pwd != null) && (nombre.length() >= 2) && (pwd.length() >= 2));

		return usuario_valido;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO RECIBIR LOS DATOS DEL USUARIO Y DARLES DE ALTA
		String nombre = request.getParameter("nombre");
		String pwd = request.getParameter("pwd");
		// TODO VALIDAR EN EL SERVIDOR
		String mensaje_error = null;
		if (validarUsuario(nombre, pwd)) {
			System.out.println("NOMBRE RX = " + nombre);
			System.out.println("PWD RX = " + pwd);
			// una vez dado de alta, podemos enviarlo a login o menu
			Usuario usuario = new Usuario(nombre, pwd);
			log.debug("Insertamos en AltaUsuario " + usuario);
			UsuarioService usuarioService = new UsuarioService();
			try {
				usuarioService.altaUsuario(usuario);
				response.sendRedirect("menu.html");
			} catch (Exception e) {
				// TODO: handle exception
				log.error("Error en alta usuario", e);
				if (e instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException) {
					log.error("ERROR HA SIDO POR NOMBRE REPE");
					mensaje_error = "HA REPETIDO EL NOMBRE DEL USUARIO";

				} else {
					mensaje_error = "ERROR DESCONOCIDO EN EL ALTA";
				}
			}
		} else {
			log.error("No ha superado la validación");
			mensaje_error = "EL NOMBRE Y LA PWD SON OBLIGATORIAS Y CON LONGUITUD MÍNIMA DOS";

		}
		if (mensaje_error != null) {
			request.setAttribute("error", mensaje_error);
			request.getRequestDispatcher("erroralta.jsp").forward(request, response);
		}

	}

}
