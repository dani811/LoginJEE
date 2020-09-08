package loginjee.controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CerrarSesion
 */
@WebServlet("/CerrarSesion")
public class CerrarSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CerrarSesion() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * PONED EN MENU.HTML UNA OPCIÓN DE CERRAR SESIÓN QUE VAYA A UN SERVLET, 
	 * CIERRE LA SESIÓN DEL USUARIO Y REDIRIJA A LA PÁGINA DEL LOGIN

	 */
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Cerrando la session . . .");
		HttpSession httpSession = request.getSession(false);
		httpSession.invalidate();//RUNTIME O UNCHECKED--> NO ME OBLIGA A CAPTURARLA
		System.out.println("Sesión invalidada ");
		response.sendRedirect("loginhtml.html");
		System.out.println("Redirección cursada");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
