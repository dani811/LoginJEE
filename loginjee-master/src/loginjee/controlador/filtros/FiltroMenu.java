package loginjee.controlador.filtros;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class FiltroMenu
 */

@WebFilter(urlPatterns = {"/busqueda.html","/menu.html", "/ListarUsuariosJSP", "/ListarUsuarioJSON", "/ListarUsuarioJSONMap", "/ListarUsuarioJSP","/ListarUsuariosJSON", "/CerrarSesion"}) 
public class FiltroMenu implements Filter {

    /**
     * Default constructor. 
     */
    public FiltroMenu() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		System.out.println("El cliente quiere la página de menu");
		//si tiene sesion, le dejo pasar
		//si no, le redirijo a loginhtml.html
		HttpServletRequest hr = (HttpServletRequest)request;
		HttpSession sesion = hr.getSession(false);
		if (sesion==null)
		{
			System.out.println("El usuario no tiene sesión. q no ha pasao por login");
			HttpServletResponse respuesta_http = (HttpServletResponse) response;
			respuesta_http.sendRedirect("loginhtml.html");
		}
		else {
			System.out.println("El usuario ya tiene sesión. Le dejamos pasar a menu");
			chain.doFilter(request, response);
			System.out.println("Se la damos");
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
