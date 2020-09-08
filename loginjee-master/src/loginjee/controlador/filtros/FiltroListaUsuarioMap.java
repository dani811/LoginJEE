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
 * Servlet Filter implementation class FiltroListaUsuarioMap
 */
@WebFilter("/ListarUsuarioJSONMap")
public class FiltroListaUsuarioMap implements Filter {

    /**
     * Default constructor. 
     */
    public FiltroListaUsuarioMap() {
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
		System.out.println("Pasa por FiltroUsuarioMap");
		long t1 = System.currentTimeMillis();//tomo el tiempo antes de entrar al servlet
		chain.doFilter(request, response);
		long t2 = System.currentTimeMillis();//tomo el tiempo a la salida del servlet
		long tiempototal = t2-t1;
		System.out.println("Tiempo consumido por FiltroUsuarioMap = " + tiempototal);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
