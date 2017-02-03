package ma.hog.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ma.hog.dao.MainDAO;
import ma.hog.entities.User;

/**
 * Servlet implementation class Users
 * 
 */
@WebServlet(urlPatterns = {"/users/create", "/users/update", "/users/list", "/users/delete", "/users/show"})
public class Users extends HttpServlet {
	
	private static final long serialVersionUID = -3003511691726486046L;

	public Users() {
        super();
    }

	/**
	 * Handle GET request
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String servletPath = request.getServletPath().toString();
		String[] tokens = servletPath.split("/");
		String operation = tokens[ tokens.length - 1 ];
		String result = null;
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
			throw new ServletException("users servlet : operation not supported > "+operation);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	doGet(req, resp);
    }
    
    /**
	 * /users/create
	 * create user
     * @throws IOException 
	 */
    private String create(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String login = request.getParameter("login");
    	String password = request.getParameter("password");
    	String email = request.getParameter("email");
    	String nom = request.getParameter("nom");
    	String prenom = request.getParameter("prenom");
    	
    	User user = new User();
    	user.setLogin(login);
    	user.setPassword(password);
    	user.setEmail(email);
    	user.setNom(nom);
    	user.setPrenom(prenom);
    	
    	MainDAO dao = MainDAO.getInstance();
    	dao.insertUser(user);
    	
    	return user.toJSON();
    }

    /**
	 * /users/update
	 * update user
     * @throws IOException 
	 */
    private String update(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	int id = Integer.parseInt( request.getParameter("id") );
    	String password = request.getParameter("password");
    	String email = request.getParameter("email");
    	String nom = request.getParameter("nom");
    	String prenom = request.getParameter("prenom");
    	
    	MainDAO dao = MainDAO.getInstance();
    	
    	User user = dao.findUser(id);
    	user.setPassword(password);
    	user.setEmail(email);
    	user.setNom(nom);
    	user.setPrenom(prenom);
    	
    	dao.updateUser(user);
    	return user.toJSON();
    }
    
    /**
	 * /users/update
	 * list all users
	 */
    private String list(HttpServletRequest request, HttpServletResponse response) {
    	MainDAO dao = MainDAO.getInstance();
    	List<User> users = dao.findAllUsers();
    	
    	String rs = "[";
    	
    	for( User user : users ) {
    		rs += user.toJSON() + ",";
    	}
    	
    	if( users.size() > 0 ) {
    		rs = rs.substring(0, rs.length()-1);
    	}
    	
    	rs += "]";
    	return rs;
    }
    
    /**
	 * /users/delete
	 * delete user
	 */
    private String delete(HttpServletRequest request, HttpServletResponse response) {
    	int id = Integer.parseInt( request.getParameter("id") );
    	MainDAO dao = MainDAO.getInstance();
    	User user = dao.findUser(id);
    	dao.removeUser(user);
    	return "{ \"deleted\" : true }";
    }
    
    /**
	 * /users/show
	 * show user
	 */
    private String show(HttpServletRequest request, HttpServletResponse response) {
    	int id = Integer.parseInt( request.getParameter("id") );
    	MainDAO dao = MainDAO.getInstance();
    	User user = dao.findUser(id);
    	return user.toJSON();
    }
	
}
