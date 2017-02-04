package ma.hog.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ma.hog.dao.MainDAO;
import ma.hog.entities.User;
import ma.hog.utils.AppException;
import ma.hog.utils.Tools;

/**
 * Servlet implementation class Users
 * 
 */
@WebServlet(urlPatterns = {"/users/create", "/users/update", "/users/list", "/users/delete", "/users/show"})
public class UsersServlet extends BaseServlet {
	
	private static final long serialVersionUID = -3003511691726486046L;
    
    /**
	 * /users/create
	 * create user
     * @throws IOException 
     * @throws AppException 
	 */
	@Override
    protected String create(HttpServletRequest request, HttpServletResponse response) throws IOException, AppException {
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
    	
    	if( !user.isValid() ) {
    		throw new AppException(400, "Missing attributes", "Missing attributes");
    	}
    	
    	user.setAccessToken( Tools.generateAccessToken() );
    	
    	MainDAO dao = MainDAO.getInstance();
    	dao.insertUser(user);
    	
    	return user.toJSON();
    }

    /**
	 * /users/update
	 * update user
     * @throws IOException 
     * @throws AppException 
	 */
	@Override
    protected String update(HttpServletRequest request, HttpServletResponse response) throws IOException, AppException {
    	int id = Integer.parseInt( request.getParameter("id") );
    	String password = request.getParameter("password");
    	String email = request.getParameter("email");
    	String nom = request.getParameter("nom");
    	String prenom = request.getParameter("prenom");
    	
    	MainDAO dao = MainDAO.getInstance();
    	
    	User user = dao.findUser(id);
    	
    	if( user == null ) {
    		throw new AppException(400, "User not found", "User not found");
    	}
    	
    	user.setPassword(password);
    	user.setEmail(email);
    	user.setNom(nom);
    	user.setPrenom(prenom);
    	
    	dao.updateUser(user);
    	return user.toJSON();
    }
    
    /**
	 * /users/list
	 * list all users
	 */
	@Override
    protected String list(HttpServletRequest request, HttpServletResponse response) {
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
	 * temporarily disabled
	 */
	@Override
    protected String delete(HttpServletRequest request, HttpServletResponse response) {
    	int id = Integer.parseInt( request.getParameter("id") );
    	MainDAO dao = MainDAO.getInstance();
    	User user = dao.findUser(id);
    	dao.removeUser(user);
    	return "{ \"deleted\" : true }";
    }
    
    /**
	 * /users/show
	 * show user
     * @throws AppException 
	 */
	@Override
    protected String show(HttpServletRequest request, HttpServletResponse response) throws AppException {
    	int id = Integer.parseInt( request.getParameter("id") );
    	MainDAO dao = MainDAO.getInstance();
    	User user = dao.findUser(id);
    	if( user == null ) {
    		throw new AppException(400, "User not found", "User not found");
    	}
    	return user.toJSON();
    }
	
}
