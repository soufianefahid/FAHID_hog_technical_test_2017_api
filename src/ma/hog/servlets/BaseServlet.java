package ma.hog.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ma.hog.utils.AppException;

/**
 * Servlet implementation class BaseServlet
 */
public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaseServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String result = handleRequest(request, response);
		if( result == null ) {
			throw new AppException("Unkown error", "Empty result");
		}
		
		response.setStatus(200);
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
	    response.addHeader("Access-Control-Allow-Methods", "GET, PUT, POST, OPTIONS, DELETE");
	    response.addHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.addHeader("Access-Control-Max-Age", "86400");
		response.getWriter().write(result);
	}

	/**
	 * Handle POST request
	 * delegate to doGet
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
    
    /**
     * It determines the action to be performed
     * Can be overrided in subclasses
     * @param request
     * @param response
     * @return JSON response
     * @throws ServletException
     * @throws IOException
     */
    protected String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String servletPath = request.getServletPath().toString();
    	String result = null;
    	String operation = null;
    	
    	try {
    		String[] tokens = servletPath.split("/");
    		operation = tokens[ tokens.length - 1 ];
    	} catch(Exception e) {
    		throw new AppException(400, "Request malformed", "Request malformed");
    	}
		
		switch (operation) {
		case "create":
			result = create(request, response);
			break;
		case "update":
			result = update(request, response);
			break;
		case "list":
			result = list(request, response);
			break;
		case "delete":
			result = delete(request, response);
			break;
		case "show":
			result = show(request, response);
			break;
		default:
			throw new AppException(400, "Operation not supported", "Operation not supported");
		}
		
		return result;
    }
    
    /**
     * Must be defined in subclasses
     * @param request
     * @param response
     * @return JSON response
     * @throws IOException
     * @throws AppException
     */
    protected String create(HttpServletRequest request, HttpServletResponse response) throws IOException, AppException {
    	throw new AppException(400, "Operation not supported", "Operation not supported");
    }
    
    /**
     * Must be defined in subclasses
     * @param request
     * @param response
     * @return JSON response
     * @throws IOException
     * @throws AppException
     */
    protected String update(HttpServletRequest request, HttpServletResponse response) throws IOException, AppException {
    	throw new AppException(400, "Operation not supported", "Operation not supported");
    }
    
    /**
     * Must be defined in subclasses
     * @param request
     * @param response
     * @return JSON response
     * @throws IOException
     * @throws AppException
     */
    protected String list(HttpServletRequest request, HttpServletResponse response) throws IOException, AppException {
    	throw new AppException(400, "Operation not supported", "Operation not supported");
    }
    
    /**
     * Must be defined in subclasses
     * @param request
     * @param response
     * @return JSON response
     * @throws IOException
     * @throws AppException
     */
    protected String delete(HttpServletRequest request, HttpServletResponse response) throws IOException, AppException {
    	throw new AppException(400, "Operation not supported", "Operation not supported");
    }
    
    /**
     * Must be defined in subclasses
     * @param request
     * @param response
     * @return JSON response
     * @throws IOException
     * @throws AppException
     */
    protected String show(HttpServletRequest request, HttpServletResponse response) throws IOException, AppException {
    	throw new AppException(400, "Operation not supported", "Operation not supported");
    }
}
