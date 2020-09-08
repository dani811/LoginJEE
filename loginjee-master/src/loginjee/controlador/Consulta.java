package loginjee.controlador;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import loginjee.bean.Usuario;
import loginjee.persistencia.BaseDeDatos;


/**
 * Servlet implementation class Consulta
 */
@WebServlet("/Consulta")
public class Consulta extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Consulta() {
        // TODO Auto-generated constructor stub
    }
//jdbc:mysql://localhost:3306/hedima?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		Usuario usuario_aux = null;
		List<Usuario> lu = new ArrayList<Usuario>();
		
		//persistencia--> capa de la base de datos: clases de java que interactúan con la base de datos
		//en nuestro caso, JDBC --> API Connection, DriverManager, Statement, ResultSet
		
		try {
			ResultSet rs = BaseDeDatos.getConnection().createStatement().executeQuery ("SELECT * FROM usuarios");
			while (rs.next())
			{
				//crear el usuario
				usuario_aux = new Usuario(rs);
				//add a la lista
				lu.add(usuario_aux);
			}
			System.out.println(lu);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		System.out.println("llamada a DOPOST");
		//request.getRequestDispatcher("/index.html").forward(request, response);
		//response.sendRedirect("index.html");
	}

}
