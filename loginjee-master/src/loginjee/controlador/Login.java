package loginjee.controlador;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import loginjee.bean.SesionBean;
import loginjee.bean.Usuario;
import loginjee.servicio.UsuarioService;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final static Logger log = Logger.getLogger("mylog");

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	private Usuario obtenerUsuario(HttpServletRequest request) {
		Usuario usuario = null;

		try {
			BufferedReader br = request.getReader();// accedo al cuerpo del mensaje HTTP
			String string_cuerpo = br.readLine();// leo el cuerpo
			System.out.println("CUERPO RX = " + string_cuerpo);
			Gson gson = new Gson();
			usuario = gson.fromJson(string_cuerpo, Usuario.class);
			System.out.println(usuario);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return usuario;
	}

	private void mostrarMapaUsuarios() {
		ServletContext servletContext = this.getServletContext();
		Map<Integer, Usuario> mu = (Map<Integer, Usuario>) servletContext.getAttribute("er_mapa");
		Iterator<Integer> it = mu.keySet().iterator();
		Usuario usuario_aux = null;
		while (it.hasNext()) {
			usuario_aux = mu.get(it.next());
			log.debug(usuario_aux.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("LLEGÓ PETICIÓN POST");
		int status = 0;

		// mostrarMapaUsuarios ();

		Usuario usuario = obtenerUsuario(request);// si es !=null
		UsuarioService usuarioService = new UsuarioService();
		try {
			int num_dev = usuarioService.existeUsuario(usuario);
			switch (num_dev) {
			case 0:
				status = HttpURLConnection.HTTP_OK;// existe
				//VAMOS A CREAR LA SESIÓN PARA ESE USUARIO :)
				HttpSession session = request.getSession(true);//este es el saquito de la sesión
				//session.setMaxInactiveInterval(10);
				List<String> lista_actividad = new ArrayList<String>();
				
				log.debug("El ID de la sesión es " + session.getId());
				session.setAttribute("NOMBRE_USUARIO", usuario.getNombre());
				session.setAttribute("NVECES_JSP", 0);
				session.setAttribute("LISTA_ACTIVIDAD", lista_actividad);
				//obtnego la info de la sesión y la guardo
				SesionBean sesionBean = new SesionBean();
				sesionBean.setSesionhttp(session.getId());
				//sesionBean.setTinicio(new Date());
				sesionBean.setTinicio(new Date(System.currentTimeMillis()));
				sesionBean.setNombre_usuario(usuario.getNombre());
				session.setAttribute("INFO_SESION", sesionBean);
				
				//session.invalidate();
				
				ServletContext sc = this.getServletContext();// cojo la saca
				int nlogins = (int) sc.getAttribute("NUM_LOGINS");
				log.debug("Num logins = " + nlogins);
				nlogins = nlogins + 1;
				sc.setAttribute("NUM_LOGINS", nlogins);
				if (nlogins == 5) {
					status = HttpURLConnection.HTTP_MOVED_TEMP;
				}
				
				int numusuarios = (int)sc.getAttribute("NUM_USUARIOS");
				numusuarios = numusuarios+1;
				sc.setAttribute("NUM_USUARIOS", numusuarios);

				break;
			case 1:
				status = HttpURLConnection.HTTP_FORBIDDEN;// existe nombre pero pwd mal
				break;
			case 2:
				status = HttpURLConnection.HTTP_NO_CONTENT;// no existe nombre
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			status = HttpURLConnection.HTTP_INTERNAL_ERROR;
		}
		log.debug("DEVOLVEMOS STATUS " + status);
		/*
		 * if (redirigir) {
		 * //response.sendRedirect("https://euw.leagueoflegends.com/es-es/"); } else {
		 */

		response.setStatus(status);

	}

}
