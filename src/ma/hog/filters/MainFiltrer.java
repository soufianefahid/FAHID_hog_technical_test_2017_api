package ma.hog.filters;

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

import ma.hog.dao.MainDAO;
import ma.hog.entities.User;
import ma.hog.utils.AppException;
import ma.hog.utils.Tools;

/**
 * Servlet Filter implementation class MainFiltrer
 */
@WebFilter("/*")
public class MainFiltrer implements Filter {

    /**
     * Default constructor. 
     */
    public MainFiltrer() {
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
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		try {			
			String servletPath = request.getServletPath().toString();
			System.out.println(servletPath);
			
			// Check authentification
			if( !Tools.pathIsPublic(servletPath) ) {
				String accessToken = request.getParameter("token");
				if( Tools.stringIsEmpty(accessToken) ) {
					throw new AppException(403, "Missing access token", "no token provided");
				}
				MainDAO dao = MainDAO.getInstance();
				User user = dao.findUserByAccessToken(accessToken);
				if( user == null ) {
					throw new AppException(403, "invalid token", "invalid token");
				}
				request.setAttribute("user", user);
			}
			
			// pass the request along the filter chain
			chain.doFilter(request, response);
		} catch( ServletException e ) {
			Tools.renderError(e, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
