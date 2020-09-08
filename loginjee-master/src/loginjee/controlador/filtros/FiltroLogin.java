package loginjee.controlador.filtros;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class FiltroLogin
 */
@WebFilter("/Login")
public class FiltroLogin implements Filter {

    /**
     * Default constructor. 
     */
    public FiltroLogin() {
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
		// TODO CONTINUAMOS CON FILTROS Y TIEMPOS
		// place your code here

		// pass the request along the filter chain
		System.out.println("Antes de llamar a Login");
		//TOMAR TIEMPO T1
		long t1 = System.currentTimeMillis();
		//System system = new System
		chain.doFilter(request, response);
		System.out.println("A la vuelta de Login");
		long t2 = System.currentTimeMillis();
		//TOMAR TIEMPO T2
		//tardado en ejecutar es T2-T1
		long total = t2-t1;
		System.out.println("Login ha tardado en ejecutarse " + total + " ms ");
			
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
